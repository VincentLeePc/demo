package com.example.demo.view;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.handler.MessageHandler2;
import com.example.demo.model.req.PushReqModelBean;
import com.example.demo.model.req.UserReqModelBean;

/**
 * 與Line平台互傳訊息的入口
 * @author Vincent Lee
 * @date 2022年12月23日
 * @remark 是Client, 也是Server
 */
@RequestMapping("/robot2")
@RestController
public class RobotController2 {
    private static final Logger log = LoggerFactory.getLogger(RobotController2.class);

	@Value("${line.bot.channel-secret2}")
	private String LINE_SECRET;

	@Autowired
	private MessageHandler2 messageHandler;

	@GetMapping("/test")
	public ResponseEntity<String> test() {
	    return new ResponseEntity<String>("Hello World!", HttpStatus.OK);
	}

    @GetMapping("/testHealth")
    public ResponseEntity<String> testHealth() {
        return new ResponseEntity<String>("this a test Health page!", HttpStatus.OK);
    }

    @GetMapping("/testMoney")
    public ResponseEntity<String> testMoney() {
        return new ResponseEntity<String>("this a test Money page!", HttpStatus.OK);
    }
    
    @GetMapping("/testGetProfile")
    public ResponseEntity<String> testGetProfile(@RequestBody UserReqModelBean model) {
        messageHandler.doGetProfile(model);
        return new ResponseEntity<String>("testGetProfile End!", HttpStatus.OK);
    }

    @GetMapping("/testPush")
    public ResponseEntity<String> testPush(@RequestBody PushReqModelBean model) {
        messageHandler.doPush(model);
        return new ResponseEntity<String>("testPush End!", HttpStatus.OK);
    }

    @GetMapping("/testMulticast")
    public ResponseEntity<String> testMulticast(@RequestBody PushReqModelBean model) {
        messageHandler.doMulticast(model);
        return new ResponseEntity<String>("testMulticast End!", HttpStatus.OK);
    }

	@PostMapping("/messaging")
	/** Webhook URL, Line訊息入口 */
	public ResponseEntity<String> messagingAPI(@RequestHeader("X-Line-Signature") String X_Line_Signature,
			@RequestBody String requestBody) throws UnsupportedEncodingException, IOException {
	    log.info("messagingAPI, X_Line_Signature:" + X_Line_Signature);
	    log.info("messagingAPI, requestBody:" + requestBody);
		if (checkFromLine(requestBody, X_Line_Signature)) {
		    log.info("驗證通過");
			JSONObject object = new JSONObject(requestBody);
			for (int i = 0; i < object.getJSONArray("events").length(); i++) {
				if (object.getJSONArray("events").getJSONObject(i).getString("type").equals("message")) {
					messageHandler.doAction(object.getJSONArray("events").getJSONObject(i));
				}
			}
			return new ResponseEntity<String>("OK", HttpStatus.OK);
		}
		log.info("驗證不通過");
		return new ResponseEntity<String>("Not line platform", HttpStatus.BAD_GATEWAY);
	}

	/** 根㯫簽名檢查訊息是否來自Line平台, 使用HMAC-SHA256演算法 */
	public boolean checkFromLine(String requestBody, String X_Line_Signature) {
	    log.info("X_Line_Signature:" + X_Line_Signature);
	    log.info("requestBody:" + requestBody);
		SecretKeySpec key = new SecretKeySpec(LINE_SECRET.getBytes(), "HmacSHA256");
		Mac mac;
		try {
			mac = Mac.getInstance("HmacSHA256");
			mac.init(key);
			byte[] source = requestBody.getBytes("UTF-8");
			String signature = Base64.encodeBase64String(mac.doFinal(source));
			if (signature.equals(X_Line_Signature)) {
				return true;
			}
		} catch (NoSuchAlgorithmException | InvalidKeyException | UnsupportedEncodingException e) {
		    log.error("!! ERROR, checkFromLine Exception", e);
			//e.printStackTrace();
		}
		return false;
	}
}

package com.example.demo.view;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.req.UrlModelBean;
import com.example.demo.service.LineLoginService;
import com.linecorp.sample.login.infra.line.api.v2.response.AccessToken;
import com.linecorp.sample.login.infra.line.api.v2.response.IdToken;
import com.linecorp.sample.login.infra.utils.CommonUtils;

/**
 * 與Line Login入口
 * @author Vincent Lee
 * @date 2022年12月23日
 * @remark 是Client, 也是Server
 */
@RequestMapping("/linelogin")
@RestController
public class LineLoginController {
    private static final Logger log = LoggerFactory.getLogger(LineLoginController.class);
    

    private static final String LINE_WEB_LOGIN_STATE = "lineWebLoginState";
    static final String ACCESS_TOKEN = "accessToken";
    private static final String NONCE = "nonce";

	@Value("${line.login.channel-secret}")
	private String LINE_SECRET;
	
	@Autowired
	private LineLoginService lineLoginService;


    @GetMapping("/emailPolicy")
    public ResponseEntity<String> testHealth() {
        return new ResponseEntity<String>("讀取Email僅做為測試使用!", HttpStatus.OK);
    }
    
    /**
     * <p>Redirect to LINE Login Page</p>
     */
    @RequestMapping(value = "/gotoauthpage")
    public String goToAuthPage(HttpSession httpSession){
        final String state = CommonUtils.getToken();
        final String nonce = CommonUtils.getToken();
        httpSession.setAttribute(LINE_WEB_LOGIN_STATE, state);
        httpSession.setAttribute(NONCE, nonce);
        final String url = lineLoginService.getLineWebLoginUrl(state, nonce, Arrays.asList("openid", "profile", "email"));
        log.info("goToAuthPage url: " + url);
        return "redirect:" + url;
    }
    
    /**
     * Login callback URL
     * <p>Redirect Page from LINE Platform</p>
     * <p>Login Type is to log in on any desktop or mobile website
     */
    @RequestMapping("/auth")
    public String auth(
            HttpSession httpSession,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "errorCode", required = false) String errorCode,
            @RequestParam(value = "error_description", required = false) String errorMessage) {
    	//login 成功只有code state
        if (log.isDebugEnabled()) {
        	log.debug("-- parameter code : " + code);
        	log.debug("-- parameter state : " + state);
        	log.debug("-- parameter error : " + error);
        	log.debug("-- parameter errorCode : " + errorCode);
        	log.debug("-- parameter errorMessage : " + errorMessage);
        }

        if (error != null || errorCode != null || errorMessage != null){
            return "redirect:/loginCancel";
        }
        log.info("LINE_WEB_LOGIN_STATE:" + httpSession.getAttribute(LINE_WEB_LOGIN_STATE));
        if (!state.equals(httpSession.getAttribute(LINE_WEB_LOGIN_STATE))){
            return "redirect:/sessionError";
        }

        httpSession.removeAttribute(LINE_WEB_LOGIN_STATE);
        AccessToken token = lineLoginService.accessToken(code);
        if (log.isDebugEnabled()) {
        	log.debug("scope : " + token.scope);
        	log.debug("access_token : " + token.access_token);
        	log.debug("token_type : " + token.token_type);
        	log.debug("expires_in : " + token.expires_in);
        	log.debug("refresh_token : " + token.refresh_token);
        	log.debug("id_token : " + token.id_token);
        }
        httpSession.setAttribute(ACCESS_TOKEN, token);
        return "redirect:/loginOk";
    }

    @RequestMapping("/loginOk")
    public String loginOk(HttpSession httpSession, Model model) {
    	log.info("loginOk model:"+model);
    	
        AccessToken token = (AccessToken)httpSession.getAttribute(ACCESS_TOKEN);
        log.info("loginOk token:"+ token);
        if (token == null){
            return "redirect:/";
        }
        log.info("loginOk token.id_token:"+ token.id_token);
        
        if (!lineLoginService.verifyIdToken(token.id_token, (String) httpSession.getAttribute(NONCE))) {
            // verify failed
        	log.info("loginOk, but verify failed");
            return "redirect:/";
        }

        httpSession.removeAttribute(NONCE);
        IdToken idToken = lineLoginService.idToken(token.id_token);
        if (log.isDebugEnabled()) {
        	log.debug("userId : " + idToken.sub);
        	String[] amr = idToken.amr;
        	if (amr != null) {
        		for (int i = 0; i < amr.length; i++) {
        			log.debug("amr[" + i + "] : " + amr[i]);
        		}
        	}
        	log.debug("displayName : " + idToken.name);
        	log.debug("iss : " + idToken.iss);
        	log.debug("channel ID : " + idToken.aud);
            log.debug("pictureUrl : " + idToken.picture);
            log.debug("email : " + idToken.email);
        }
        model.addAttribute("idToken", idToken);
        return "user/success";
    }
    
  

    @PostMapping("/getUrl")
    public ResponseEntity<String> getUrl(@RequestBody UrlModelBean model) {
    	String result = lineLoginService.getLoginUrl(model.getUrl());
    	log.info("getUrl: " + result);
        return new ResponseEntity<String>(result , HttpStatus.OK);
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

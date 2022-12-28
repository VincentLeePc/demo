package com.example.demo.handler;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.demo.listener.MessageListener;
import com.example.demo.model.req.PushReqModelBean;
import com.example.demo.model.req.UserReqModelBean;
import com.example.demo.service.LineMsgService;
import com.linecorp.bot.model.Multicast;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.StickerMessage;
import com.linecorp.bot.model.message.TextMessage;



@Component
public class MessageHandler2 {
    private static final Logger log = LoggerFactory.getLogger(MessageHandler2.class);

    /** 自訂channel 的token */
    @Value("${line.bot.channel-token2}")
    private String LINE_TOKEN;

    @Autowired
    private LineMsgService lineMsgService;

    public void doAction(JSONObject event) {
        doAction(this.messageListener, event);
    }

    /** 對傳入訊息進行處理 */
    public void doAction(MessageListener messageListener, JSONObject event) {
        log.info("doAction...event:"+event);
        switch (event.getJSONObject("message").getString("type")) {
        case "text":
            messageListener.text(event.getString("replyToken"), event.getJSONObject("message").getString("text"));
            break;
        case "image":
            messageListener.image(event.getString("replyToken"), event.getJSONObject("message").getString("id"));
            break;
        case "video":
            messageListener.video(event.getString("replyToken"), event.getJSONObject("message").getString("id"));
            break;
        case "audio":
            messageListener.audio(event.getString("replyToken"), event.getJSONObject("message").getString("id"), event.getJSONObject("message").getLong("duration"));
            break;
        case "file":
            messageListener.file(event.getString("replyToken"), event.getJSONObject("message").getString("id"), event.getJSONObject("message").getString("fileName"), event.getJSONObject("message").getLong("fileSize"));
            break;
        case "location":
            messageListener.location(event.getString("replyToken"), event.getJSONObject("message").getString("title"), event.getJSONObject("message").getString("address"), event.getJSONObject("message").getDouble("latitude"), event.getJSONObject("message").getDouble("longitude"));
            break;
        case "sticker":
            messageListener.sticker(event.getString("replyToken"), event.getJSONObject("message").getString("packageId"), event.getJSONObject("message").getString("stickerId"));
            break;
        }
    }

    /** 推送訊息給 Udc7ea4ee3585ef411db2182acf751303 */
    public void doPush(PushReqModelBean model) {
        log.info("doPush model:" + model);
        String text = model.getText();
        List<Message> msgs = new ArrayList<Message>();
        if (StringUtils.isNotEmpty(model.getPackageId()) ) {
            msgs.add(new TextMessage(text));
            msgs.add(new StickerMessage(model.getPackageId(), model.getStickerId()));
        } else {
            msgs.add(new TextMessage(text));
        }
        lineMsgService.pushMessage(LINE_TOKEN, new PushMessage("Udc7ea4ee3585ef411db2182acf751303", msgs));
    }
    
    /** 取得 Udc7ea4ee3585ef411db2182acf751303 的profile */
    public void doGetProfile(UserReqModelBean model) {
        log.info("doGetProfile model:" + model);
        String userId = model.getUserId();
        lineMsgService.getProfile(LINE_TOKEN, userId);
    }

    /** 對多人發送訊息 */
    public void doMulticast(PushReqModelBean model) {
        log.info("doMulticase model:" + model);
        Set<String> to = new LinkedHashSet<String>();
        to.add("Udc7ea4ee3585ef411db2182acf751303");
        String text = model.getText();
        List<Message> msgs = new ArrayList<Message>();
        if (StringUtils.isNotEmpty(model.getPackageId()) ) {
            msgs.add(new TextMessage(text));
            msgs.add(new StickerMessage(model.getPackageId(), model.getStickerId()));
        } else {
            msgs.add(new TextMessage(text));
        }
        lineMsgService.multicastMessage(LINE_TOKEN, new Multicast(to, msgs));
    }

    /** 實作MessageListener */
    private MessageListener messageListener = new MessageListener() {

        @Override
        public void video(String replyToken, String id) {
            // TODO Auto-generated method stub
            System.out.printf("%s\t%s\n", replyToken, "video");
        }

        @Override
        public void text(String replyToken, String text) {
            // TODO Auto-generated method stub
            System.out.printf("%s\t%s\n", replyToken, "text");
            List<Message> msgs = new ArrayList<Message>();
            msgs.add(new TextMessage("看不懂~請在跟我說一次"));
            msgs.add(new StickerMessage("11538", "51626532"));
            lineMsgService.replyMessage(LINE_TOKEN, new ReplyMessage(replyToken, msgs));
        }

        @Override
        public void sticker(String replyToken, String packageId, String stickerId) {
            // TODO Auto-generated method stub
            System.out.printf("%s\t%s\n", replyToken, "sticker");
            List<Message> msgs = new ArrayList<Message>();
            msgs.add(new TextMessage("看不懂~請在跟我說一次"));
            msgs.add(new StickerMessage("11538", "51626532"));
            lineMsgService.replyMessage(LINE_TOKEN, new ReplyMessage(replyToken, msgs));
        }

        @Override
        public void location(String replyToken, String title, String address, double latitude, double longitude) {
            // TODO 未實作
            System.out.printf("%s\t%s\n", replyToken, "location");
        }

        @Override
        public void image(String replyToken, String id) {
            // TODO 未實作
            System.out.printf("%s\t%s\n", replyToken, "image");
        }

        @Override
        public void file(String replyToken, String id, String fileName, long fileSize) {
            // TODO 未實作
            System.out.printf("%s\t%s\n", replyToken, "file");
        }

        @Override
        public void audio(String replyToken, String id, long duration) {
            // TODO 未實作
            System.out.printf("%s\t%s\n", replyToken, "file");
        }
    };
}

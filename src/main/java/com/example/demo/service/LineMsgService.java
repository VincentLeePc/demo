package com.example.demo.service;

import com.linecorp.bot.model.Multicast;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.ReplyMessage;

public interface LineMsgService {
    /** 回應訊息 */
    void replyMessage(String LINE_TOKEN, ReplyMessage replyMessage);

    /** 針對單一對象推送訊息 */
    void pushMessage(String LINE_TOKEN, PushMessage pushMessage);

    /** 群發訊息 */
    void multicastMessage(String LINE_TOKEN, Multicast multicastMessage);
    
	/** 針對單一對象取得Profile */
    void getProfile(String LINE_TOKEN, String userId);
}

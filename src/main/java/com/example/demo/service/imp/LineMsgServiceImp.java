package com.example.demo.service.imp;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.demo.service.LineMsgService;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.Multicast;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.profile.UserProfileResponse;
import com.linecorp.bot.model.response.BotApiResponse;
@Service
public class LineMsgServiceImp implements LineMsgService {
    private static final Logger log = LoggerFactory.getLogger(LineMsgServiceImp.class);

    /** 回應訊息 */
	@Override
    public void replyMessage(String LINE_TOKEN, ReplyMessage replyMessage) {
	    final LineMessagingClient client = LineMessagingClient
	            .builder(LINE_TOKEN)
	            .build();


	    final BotApiResponse botApiResponse;
	    try {
	        botApiResponse = client.replyMessage(replyMessage).get();
	        log.info("replyMessage, botApiResponse:" + botApiResponse);
	        log.info("replyMessage END!");
	    } catch (InterruptedException | ExecutionException e) {
	        e.printStackTrace();
	        return;
	    }
	}

	/** 針對單一對象推送訊息 */
	@Override
    public void pushMessage(String LINE_TOKEN, PushMessage pushMessage) {
	    final LineMessagingClient client = LineMessagingClient
                .builder(LINE_TOKEN)
                .build();


        final BotApiResponse botApiResponse;
        try {
            botApiResponse = client.pushMessage(pushMessage).get();
            log.info("pushMessage, botApiResponse:" + botApiResponse);
            log.info("pushMessage END!");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return;
        }
	}
	
	/** 針對單一對象取得Profile */
	@Override
    public void getProfile(String LINE_TOKEN, String userId) {
	    final LineMessagingClient client = LineMessagingClient
                .builder(LINE_TOKEN)
                .build();
	    final UserProfileResponse userProfileResponse;
        try {
        	userProfileResponse = client.getProfile(userId).get();
            log.info("getProfile, userProfileResponse:" + userProfileResponse);
            log.info("getProfile END!");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return;
        }
	}

	/** 群發訊息 */
	@Override
    public void multicastMessage(String LINE_TOKEN, Multicast multicastMessage) {
	    final LineMessagingClient client = LineMessagingClient
                .builder(LINE_TOKEN)
                .build();

        final BotApiResponse botApiResponse;
        try {
            botApiResponse = client.multicast(multicastMessage).get();
            log.info("multicastMessage, botApiResponse:" + botApiResponse);
            log.info("multicastMessage END!");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return;
        }
	}
}

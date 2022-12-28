package com.example.demo.http;

import java.io.IOException;
import java.net.URLEncoder;

import com.linecorp.bot.model.PushMessage;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LineConnector {
	
	private final String LINE_LOGIN_API =  "https://access.line.me/oauth2/v2.1/authorize";
	private final String LINE_TOKEN_API = "https://api.line.me/oauth2/v2.1/token";

	private final String LINE_VERIFY_API = "https://api.line.me/oauth2/v2.1/verify";
	 

	private static LineConnector instance;
	private OKHttpExecuter executer;
	


	private LineConnector() {
		executer = new OKHttpExecuter();
	}

	public static LineConnector getInstance() {
		if (instance == null) {
			synchronized (LineConnector.class) {
				if (instance == null) {
					instance = new LineConnector();
				}
			}
		}
		return instance;
	}
	
	



	public void pushMessage(String LINE_TOKEN, PushMessage pushMessage) {
		executer.sendPostByJson(LINE_VERIFY_API, LINE_TOKEN, pushMessage.toString(), new Callback() {
			public void onResponse(Call call, Response response) throws IOException {
				System.out.println("發送成功");
				System.out.println(response.body().string());
			}

			public void onFailure(Call call, IOException e) {
				System.err.println("發送失敗");
				e.printStackTrace();
			}
		});
	}
	

}

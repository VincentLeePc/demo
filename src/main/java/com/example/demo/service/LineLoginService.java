package com.example.demo.service;

import java.util.List;

import com.linecorp.sample.login.infra.line.api.v2.response.AccessToken;
import com.linecorp.sample.login.infra.line.api.v2.response.IdToken;
import com.linecorp.sample.login.infra.line.api.v2.response.Verify;

public interface LineLoginService {
    /** 回應Line 登入網址 */
	String getLoginUrl(String redirectUrl);

	AccessToken accessToken(String code) ;
	
	Verify verify(final AccessToken accessToken);
	
	IdToken idToken(String id_token);
	
	boolean verifyIdToken(String id_token, String nonce);
	
	String getLineWebLoginUrl(String state, String nonce, List<String> scopes);
}

package com.example.demo.service.imp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.service.LineLoginService;
import com.linecorp.sample.login.infra.http.Client;
import com.linecorp.sample.login.infra.line.api.v2.LineAPI;
import com.linecorp.sample.login.infra.line.api.v2.response.AccessToken;
import com.linecorp.sample.login.infra.line.api.v2.response.IdToken;
import com.linecorp.sample.login.infra.line.api.v2.response.Verify;

import retrofit2.Call;
@Service
public class LineLoginServiceImp implements LineLoginService {
    private static final Logger log = LoggerFactory.getLogger(LineLoginServiceImp.class);
	
	private final String  loginUrl = "https://access.line.me/oauth2/v2.1/authorize?response_type=%s&client_id=%s&redirect_uri=%s&state=%s&scope=%s";
	
	/** 回傳 line authorization url */
	public String getLoginUrl(String redirectUrl){
		String uri = null;
		String clientId = channelId;
		try {
			// 根據想要得到的資訊填寫 scope
			String scope = "profile&openId";
			// 這個 state 是隨便打的
			String state = "1qazRTGFDY5ysg";
		
			uri = String.format(loginUrl, "code", clientId, URLEncoder.encode(redirectUrl, "UTF-8"), state, scope);
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return uri;
	}
	
	//copy from LineAPIService
    private static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";
    private static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";

    @Value("${line.login.channelId}")
    private String channelId;
    @Value("${line.login.channelSecret}")
    private String channelSecret;
    @Value("${line.login.callbackUrl}")
    private String callbackUrl;

    public AccessToken accessToken(String code) {
        return getClient(t -> t.accessToken(
                GRANT_TYPE_AUTHORIZATION_CODE,
                channelId,
                channelSecret,
                callbackUrl,
                code));
    }

    public AccessToken refreshToken(final AccessToken accessToken) {
        return getClient(t -> t.refreshToken(
                GRANT_TYPE_REFRESH_TOKEN,
                accessToken.refresh_token,
                channelId,
                channelSecret));
    }

    public Verify verify(final AccessToken accessToken) {
        return getClient(t -> t.verify(
                accessToken.access_token));
    }

    public void revoke(final AccessToken accessToken) {
        getClient(t -> t.revoke(
                accessToken.access_token,
                channelId,
                channelSecret));
    }

    public IdToken idToken(String id_token) {
        try {
            DecodedJWT jwt = JWT.decode(id_token);
            return new IdToken(
                    jwt.getClaim("iss").asString(),
                    jwt.getClaim("sub").asString(),
                    jwt.getClaim("aud").asString(),
                    jwt.getClaim("ext").asLong(),
                    jwt.getClaim("iat").asLong(),
                    jwt.getClaim("nonce").asString(),
                    jwt.getClaim("amr").asArray(String.class),
                    jwt.getClaim("name").asString(),
                    jwt.getClaim("picture").asString(),
                    jwt.getClaim("email").asString());
        } catch (JWTDecodeException e) {
            throw new RuntimeException(e);
        }
    }

    public String getLineWebLoginUrl(String state, String nonce, List<String> scopes) {
        final String encodedCallbackUrl;
        final String scope = String.join("%20", scopes);

        try {
            encodedCallbackUrl = URLEncoder.encode(callbackUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return "https://access.line.me/oauth2/v2.1/authorize?response_type=code"
                + "&client_id=" + channelId
                + "&redirect_uri=" + encodedCallbackUrl
                + "&state=" + state
                + "&scope=" + scope
                + "&nonce=" + nonce;
    }

    public boolean verifyIdToken(String id_token, String nonce) {
        try {
            JWT.require(
                Algorithm.HMAC256(channelSecret))
                .withIssuer("https://access.line.me")
                .withAudience(channelId)
                .withClaim("nonce", nonce)
                .acceptLeeway(60) // add 60 seconds leeway to handle clock skew between client and server sides.
                .build()
                .verify(id_token);
            return true;
        //} catch (UnsupportedEncodingException e) {
            //UTF-8 encoding not supported
        //    return false;
        } catch (JWTVerificationException e) {
            //Invalid signature/claims
            return false;
        }
    }

    private <R> R getClient(final Function<LineAPI, Call<R>> function) {
        return Client.getClient("https://api.line.me/", LineAPI.class, function);
    }

}

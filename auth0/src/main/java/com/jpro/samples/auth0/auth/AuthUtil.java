package com.jpro.samples.auth0.auth;


import com.auth0.client.auth.AuthAPI;
import com.auth0.json.auth.TokenHolder;
import com.auth0.json.auth.UserInfo;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.net.CustomRequest;
import com.auth0.net.TokenRequest;
import com.jpro.webapi.WebAPI;

public class AuthUtil {
    static String CLIENT_ID = "???";
    static String CLIENT_SECRET = "???";
    static String DOMAIN = "???";

    AuthAPI client = null;

    public AuthUtil() {
        JwkProvider jwkProvider = new JwkProviderBuilder(DOMAIN).build();
        client = new AuthAPI(DOMAIN,CLIENT_ID,CLIENT_SECRET);
    }

    public String authURL() {
        return client.authorizeUrl("http://localhost:8080/")
                .withScope("openid email")
                .build();
    }

    public String logoutURL() {
        return client.logoutUrl("http://localhost:8080/", true)
                .build();
    }

    public String verify(String code) {
        System.out.println("Got code: " + code);
        try {
            CustomRequest<TokenHolder> tokenRequest = client.exchangeCode(code,"http://localhost:8080/");
            TokenHolder token = tokenRequest.execute();

            UserInfo info = client.userInfo(token.getAccessToken()).execute();

            return (String) info.getValues().get("email");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

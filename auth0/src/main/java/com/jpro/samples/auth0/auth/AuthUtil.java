package com.jpro.samples.auth0.auth;

import com.auth0.client.auth.AuthAPI;

public class AuthUtil {
    static String CLIENT_ID = "???";
    static String CLIENT_SECRET = "???";
    static String DOMAIN = "???";

    AuthAPI client;

    public AuthUtil() {
        client = AuthAPI.newBuilder(DOMAIN, CLIENT_ID, CLIENT_SECRET).build();
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
            var tokenRequest = client.exchangeCode(code, "http://localhost:8080/");
            var token = tokenRequest.execute();
            var userInfo = client.userInfo(token.toString()).execute();

            return String.valueOf(userInfo.getBody().getValues().get("email"));
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}

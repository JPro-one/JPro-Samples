package com.jpro.samples.auth0;

import com.jpro.samples.auth0.auth.AuthUtil;
import com.jpro.samples.auth0.page.LandingPage;
import com.jpro.samples.auth0.page.LoggedInPage;
import com.jpro.web.WebApp;
import javafx.stage.Stage;

public class Auth0App extends WebApp {

    public Auth0App(Stage stage) {
        super(stage);

        getStylesheets().add("/com/jpro/samples/auth0/app.css");

        addRouteJava(s -> {
            if(s.contains("code=")) {
                String email = new AuthUtil().verify(s.substring(s.lastIndexOf("code=") + "code=".length()));
                return new LoggedInPage(email);
            } else {
                return null;
            }
        });

        addRouteJava(s -> {
            return new LandingPage();
        });
    }
}

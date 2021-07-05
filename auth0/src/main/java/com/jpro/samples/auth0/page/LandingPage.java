package com.jpro.samples.auth0.page;

import com.jpro.samples.auth0.auth.AuthUtil;
import com.jpro.web.Util;
import com.jpro.web.View;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class LandingPage extends View {
    @Override
    public String title() {
        return "START";
    }

    @Override
    public String description() {
        return "START";
    }

    @Override
    public Node content() {
        Label label = new Label("Hello! click below to login!");
        Button loginButton = new Button("Login");
        Util.setLink(loginButton, new AuthUtil().authURL());
        return new VBox(label, loginButton);
    }
}

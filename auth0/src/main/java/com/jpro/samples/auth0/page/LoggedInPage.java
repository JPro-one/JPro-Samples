package com.jpro.samples.auth0.page;

import com.jpro.samples.auth0.auth.AuthUtil;
import com.jpro.web.Util;
import com.jpro.web.View;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class LoggedInPage extends View {
    String email;

    public LoggedInPage(String email) {
        this.email = email;
    }

    @Override
    public String title() {
        return "";
    }

    @Override
    public String description() {
        return "";
    }

    @Override
    public Node content() {
        Label label = new Label("You are logged in as: " + email);
        Button logoutButton = new Button("Logout");
        Util.setLink(logoutButton, new AuthUtil().logoutURL());
        return new VBox(label, logoutButton);
    }
}

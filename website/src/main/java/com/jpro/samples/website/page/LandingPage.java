package com.jpro.samples.website.page;

import com.jpro.samples.website.component.Header;
import com.jpro.web.View;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class LandingPage extends View {

    @Override
    public String title() {
        return "Title: Hello JPro";
    }

    @Override
    public String description() {
        return "I'm the description. Google will find me!";
    }

    @Override
    public Node content() {
        VBox content = new VBox();
        content.getStyleClass().add("content");
        content.getChildren().add(new Header());
        Label label = new Label("Main Page");
        label.getStyleClass().add("content-label");
        content.getChildren().add(label);
        return content;
    }
}

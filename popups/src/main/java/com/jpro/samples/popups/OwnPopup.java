package com.jpro.samples.popups;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class OwnPopup extends StackPane {

    StackPane context = null;

    public OwnPopup(StackPane stackpane, String message) {
        context = stackpane;
        Button btn = new Button("Close");
        Label txt = new Label(message);
        VBox box = new VBox(txt,btn);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(40);
        box.setStyle("-fx-background-color: White; -fx-max-width: 300; -fx-max-height: 120;");
        getChildren().add(box);
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: #88888855;");
        btn.setOnAction((e) -> {
            context.getChildren().remove(this);
        });
    }

    public void show() {
        context.getChildren().add(this);
    }
}

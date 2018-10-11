package com.jpro.samples.htmljpro;

import com.jpro.webapi.JProApplication;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HTMLJProSample extends JProApplication {

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox content = new VBox();
        content.getStyleClass().add("vbox");

        content.getChildren().add(new Label("These controls are written in Java:"));

        Button popupButton = new Button("Show javascript-popup");
        popupButton.setOnAction(e -> {
            getWebAPI().executeScript(
                    "window.alert(\"This popup is triggered from Java and coded in JavaScript.\");"
            );
        });
        content.getChildren().add(popupButton);


        TextField field = new TextField();
        field.setMaxWidth(300);

        /*
         * Whenever the javafx-textfield is changed, we update the html-textfield of the html-page.
         * We have to be careful about the quotation-marks. We have to escape them.
         */
        field.textProperty().addListener((p,o,n) -> {
            getWebAPI().executeScript(
                    "document.getElementById(\"textfield\").value = \"" + n.replace("\"","\\\"") + "\""
            );
        });

        /*
         * We have to register a function in the browser, which can be used by the javascript-code.
         * The function can be accessed from js like this: jpro.setTextField("newString");
         * We have to be careful about the quotation-marks. We have to unescape them.
         */
        getWebAPI().registerJavaFunction("setTextField", s -> {
            field.setText(s.substring(1,s.length() - 1).replace("\\\"","\""));
        });
        content.getChildren().add(field);


        Scene scene = new Scene(content, Color.TRANSPARENT);
        scene.getStylesheets().add("/com/jpro/samples/htmljpro/htmljpro.css");

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

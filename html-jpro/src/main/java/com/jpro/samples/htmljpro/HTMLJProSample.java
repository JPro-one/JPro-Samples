package com.jpro.samples.htmljpro;

import com.jpro.webapi.HTMLView;
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
import org.apache.commons.text.StringEscapeUtils;

public class HTMLJProSample extends JProApplication {

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox content = new VBox();
        content.getStyleClass().add("vbox");

        Label headline = new Label("JPro");
        headline.getStyleClass().add("headline");
        content.getChildren().add(headline);

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


        /*
         * Let's add some HTML-Content
         */
        String content1 = "<h1>H1 with HTMLView!</h1>I have the same javascript and css-environment as the page.";
        HTMLView htmlContent1 = new HTMLView(content1);
        htmlContent1.setMinHeight(140);
        htmlContent1.getStyleClass().add("html-view");
        content.getChildren().add(htmlContent1);

        /*
         * Let's add some HTML-Content in a iframe
         */
        String content2 = "<h1>H1 in iframe!</h1>I'm inside an iframe. I have my own scope. I dont share the outer css files. I work more like WebView, because i behave more like an own browser window.";
        String contentEncoded = StringEscapeUtils.escapeHtml4(content2);
        String contentIframe = "<iframe frameborder=\"0\" style=\"width: 100%; height: 100%;\" srcdoc=\""+contentEncoded+"\"> </iframe>";
        HTMLView htmlContent2 = new HTMLView(content2);
        htmlContent2.setMinHeight(140);
        htmlContent2.getStyleClass().add("html-view");
        content.getChildren().add(htmlContent2);


        Scene scene = new Scene(content, Color.TRANSPARENT);
        scene.getStylesheets().add("/com/jpro/samples/htmljpro/htmljpro.css");

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

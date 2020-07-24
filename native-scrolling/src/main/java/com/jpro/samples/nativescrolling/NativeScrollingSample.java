package com.jpro.samples.nativescrolling;

import com.jpro.webapi.HTMLView;
import com.jpro.webapi.JProApplication;
import com.jpro.webapi.WebAPI;
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

public class NativeScrollingSample extends JProApplication {

    VBox container;

    @Override
    public void start(Stage primaryStage) throws Exception {

        container = new VBox() {
            @Override
            protected void layoutChildren() {
                if ((this.getScene() != null) && WebAPI.isBrowser()) {
                    getWebAPI().requestLayout(this.getScene());
                    super.layoutChildren();
                } else {
                    super.layoutChildren();
                }
            }
        };

        Button button = new Button("Add message");
        button.onActionProperty().set((e) -> {
            addField("I'm a new message!");
        });

        container.getChildren().add(button);



        Scene scene = new Scene(container);
        scene.getStylesheets().add("/com/jpro/samples/nativescrolling/native-scrolling.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void addField(String text) {
        Label label = new Label(text);
        container.getChildren().add(label);
    };

}

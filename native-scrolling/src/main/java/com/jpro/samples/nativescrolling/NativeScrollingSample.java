package com.jpro.samples.nativescrolling;

import com.jpro.webapi.JProApplication;
import com.jpro.webapi.WebAPI;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NativeScrollingSample extends JProApplication {

    VBox container;

    @Override
    public void start(Stage primaryStage) {

        container = new VBox() {
            @Override
            protected void layoutChildren() {
                if ((this.getScene() != null) && WebAPI.isBrowser()) {
                    getWebAPI().layoutRoot(this.getScene());
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
    }
}

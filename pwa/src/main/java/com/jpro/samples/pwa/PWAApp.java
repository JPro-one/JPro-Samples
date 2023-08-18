package com.jpro.samples.pwa;

import fr.brouillard.oss.cssfx.CSSFX;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PWAApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        PWAUtil pwa = new PWAUtil(primaryStage);
        VBox pin = new VBox();
        VBox info = new VBox();
        pin.getStyleClass().add("vbox");
        pin.getChildren().add(new Label("PWA Sample"));
        pin.getChildren().add(info);

        Button installButton = new Button("Install as PWA");
        installButton.onActionProperty().set((e) -> pwa.installPWA());

        Scene scene = new Scene(new StackPane(pin));
        scene.getStylesheets().add("/com/jpro/samples/pwa/app.css");
        primaryStage.setScene(scene);
        primaryStage.show();

        pwa.installed().addListener((p,o,n) -> {
            info.getChildren().clear();
            n.ifPresent(installed -> {
                if(installed) {
                    info.getChildren().add(new Label("Your browser doesn't support PWA, or the App is already installed"));
                } else {
                    info.getChildren().add(installButton);
                }
            });
            if(n.isEmpty()) {
                info.getChildren().add(new Label("Your browser doesn't support PWA!"));
            }
        });

        CSSFX.start();
    }
}

package com.jpro.samples.pwa;

import com.jpro.webapi.WebAPI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PWAApp extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception {
        PWAUtil pwa = new PWAUtil(primaryStage);
        VBox pin = new VBox(new Label("Hello!"));
        Button installButton = new Button("Install");
        installButton.onActionProperty().set((e) -> {
            pwa.installPWA();
        });
        installButton.setVisible(false);
        pin.getChildren().add(installButton);
        Scene scene = new Scene(pin);
        primaryStage.setScene(scene);
        primaryStage.show();

        pwa.installed().addListener((p,o,n) -> {
            installButton.setVisible(false);
            n.ifPresent(installed -> {
                if(installed) {
                    pin.getChildren().add(new Label("You are installed!"));
                } else {
                    installButton.setVisible(true);
                }
            });
        });
    }
}

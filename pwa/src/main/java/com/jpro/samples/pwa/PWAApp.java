package com.jpro.samples.pwa;

import com.jpro.webapi.WebAPI;
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
    public void start(Stage primaryStage) throws Exception {
        CSSFX.start();
        PWAUtil pwa = new PWAUtil(primaryStage);
        VBox pin = new VBox();
        pin.getStyleClass().add("vbox");
        Button installButton = new Button("Install as PWA");
        installButton.onActionProperty().set((e) -> {
            pwa.installPWA();
        });
        installButton.setVisible(false);
        pin.getChildren().add(new Label("PWA Sample"));
        pin.getChildren().add(installButton);
        Scene scene = new Scene(new StackPane(pin));
        scene.getStylesheets().add("/com/jpro/samples/pwa/app.css");
        primaryStage.setScene(scene);
        primaryStage.show();

        pwa.installed().addListener((p,o,n) -> {
            installButton.setVisible(false);
            n.ifPresent(installed -> {
                if(installed) {
                    pin.getChildren().add(new Label("The App is already installed!"));
                } else {
                    installButton.setVisible(true);
                }
            });
        });
    }
}

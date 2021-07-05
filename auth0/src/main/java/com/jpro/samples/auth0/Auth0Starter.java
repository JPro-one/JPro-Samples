package com.jpro.samples.auth0;

import com.jpro.web.sessionmanager.SessionManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Auth0Starter extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Auth0App app = new Auth0App(primaryStage);
        app.start(SessionManager.getDefault(app, primaryStage));
        primaryStage.setScene(new Scene(app));
        primaryStage.show();
    }

    public static void main(String args[]) {
        launch(args);
    }
}

package com.jpro.samples.website;

import javafx.application.Application;
import javafx.stage.Stage;
import one.jpro.platform.routing.sessionmanager.SessionManager;

public class HelloJProWeb extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        HelloJProApp app = new HelloJProApp();
        stage.setScene(app.getScene());
        stage.show();
        SessionManager.getDefault(app.getRouteNode(), stage).start();
        app.start(stage);
    }
}

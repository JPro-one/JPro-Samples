package com.jpro.samples.website;

import com.jpro.web.sessionmanager.SessionManager;
import com.jpro.webapi.JProApplication;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class HelloJProWeb extends Application {

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage)
    {
        HelloJProApp app = new HelloJProApp(stage);
        stage.setScene(new Scene(app));
        stage.show();
        app.start(SessionManager.getDefault(app,stage));
    }
}

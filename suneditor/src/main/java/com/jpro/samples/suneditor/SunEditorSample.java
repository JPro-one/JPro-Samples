package com.jpro.samples.suneditor;

import fr.brouillard.oss.cssfx.CSSFX;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SunEditorSample extends Application {

    @Override
    public void start(Stage stage) {
        Label title = new Label("Suneditor:");
        title.getStyleClass().add("title");
        SunEditor sunEditor = new SunEditor();
        VBox vbox = new VBox(title, sunEditor);
        VBox.setVgrow(sunEditor, Priority.ALWAYS);
        vbox.getStyleClass().add("vbox");
        StackPane root = new StackPane(vbox);
        root.getStyleClass().add("root");
        Scene scene = new Scene(root);
        root.getStylesheets().add(getClass().getResource("/com/jpro/samples/suneditor/css/suneditor.css").toString());
        stage.setScene(scene);
        stage.show();

        CSSFX.start();
    }
}

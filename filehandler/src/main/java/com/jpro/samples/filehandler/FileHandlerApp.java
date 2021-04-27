package com.jpro.samples.filehandler;

import com.jpro.webapi.JProApplication;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FileHandlerApp extends JProApplication {

    @Override public void start(Stage stage) {

        FileHandler fileHandler = new FileHandler(getWebAPI());


        Button downloadButton = new Button("Download");
        downloadButton.setDisable(true);
        downloadButton.setOnAction(event -> {
            try {
                getWebAPI().downloadURL(fileHandler.fileHandler.getUploadedFile().toURI().toURL());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        fileHandler.fileHandler.progressProperty().addListener((obs,oldV,newV) -> {
            if(newV.doubleValue() == 1.0) {
                downloadButton.setDisable(false);
            }
        });

        VBox root = new VBox(fileHandler, downloadButton);
        root.setSpacing(50);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 500, 500);
        root.getStylesheets().add(getClass().getResource("/com/jpro/samples/filehandler/filehandler.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}

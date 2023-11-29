package com.jpro.samples.filehandler;

import com.jpro.webapi.JProApplication;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FileHandlerApp extends JProApplication {

    @Override
    public void start(Stage stage) {
        FileHandlerNode fileHandlerNode = new FileHandlerNode();

        Button downloadButton = new Button("Download");
        downloadButton.setDisable(true);
        downloadButton.setOnAction(event -> {
            try {
                var url = fileHandlerNode.getFileHandler().getUploadedFile().toURI().toURL();
                getWebAPI().downloadURL(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        fileHandlerNode.getFileHandler().progressProperty().addListener((obs, oldV, newV) -> {
            if (newV.doubleValue() == 1.0) {
                downloadButton.setDisable(false);
            }
        });

        VBox root = new VBox(fileHandlerNode, downloadButton);
        root.setSpacing(50);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 500, 500);
        root.getStylesheets().add(getClass().getResource("/com/jpro/samples/filehandler/filehandler.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}

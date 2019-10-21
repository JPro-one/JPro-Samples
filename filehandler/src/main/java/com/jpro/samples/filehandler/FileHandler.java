package com.jpro.samples.filehandler;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jpro.webapi.WebAPI;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class FileHandler extends Label {

    public WebAPI.FileUploader fileHandler = null;

    FileHandler(WebAPI webAPI) {
        setText("Click or Drop File");
        getStyleClass().add("file-handler");
        setWrapText(true);

        fileHandler = webAPI.makeFileUploadNode(this);

        fileHandler.setSelectFileOnClick(true);
        fileHandler.setSelectFileOnDrop(true);
        fileHandler.fileDragOverProperty().addListener((o,oldV,newV) -> {
            if(newV) {
                getStyleClass().add("file-drag");
            } else {
                if(getStyleClass().contains("file-drag")) {
                    getStyleClass().remove("file-drag");
                }
            }
        });
        fileHandler.setOnFileSelected((file) -> {
            updateText();
            fileHandler.uploadFile();
        });

        fileHandler.progressProperty().addListener((obs,oldV,newV) -> {
            updateText();
        });
    }

    private void updateText() {
        String percentages = "";
        percentages = (int) (fileHandler.getProgress() * 100) + "%";
        setText(fileHandler.selectedFileProperty() + percentages);
    }

}

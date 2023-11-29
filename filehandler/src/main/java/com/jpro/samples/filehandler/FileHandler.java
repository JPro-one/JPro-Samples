package com.jpro.samples.filehandler;

import com.jpro.webapi.WebAPI;
import javafx.scene.control.Label;

public class FileHandler extends Label {

    public WebAPI.FileUploader fileHandler = null;

    FileHandler() {
        setText("Click or Drop File");
        getStyleClass().add("file-handler");
        setWrapText(true);

        fileHandler = WebAPI.makeFileUploadNodeStatic(this);

        fileHandler.setSelectFileOnClick(true);
        fileHandler.setSelectFileOnDrop(true);
        fileHandler.fileDragOverProperty().addListener((o, oldV, newV) -> {
            if (newV) {
                getStyleClass().add("file-drag");
            } else {
                getStyleClass().remove("file-drag");
            }
        });
        fileHandler.setOnFileSelected((file) -> {
            updateText();
            fileHandler.uploadFile();
        });
        fileHandler.uploadedFileProperty().addListener((p, oldV, newV) -> {
            System.out.println("Got uploaded file: " + newV);
        });

        fileHandler.progressProperty().addListener((obs, oldV, newV) -> {
            updateText();
        });
    }

    private void updateText() {
        String percentages = "";
        percentages = (int) (fileHandler.getProgress() * 100) + "%";
        setText(fileHandler.selectedFileProperty().getValue() + percentages);
    }

}

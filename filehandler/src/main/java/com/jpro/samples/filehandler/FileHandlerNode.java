package com.jpro.samples.filehandler;

import com.jpro.webapi.WebAPI;
import javafx.scene.control.Label;

public class FileHandlerNode extends Label {

    private WebAPI.FileUploader fileHandler = null;

    FileHandlerNode() {
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

    public WebAPI.FileUploader getFileHandler() {
        return fileHandler;
    }

    private void updateText() {
        String percentages = " " + (int) (fileHandler.getProgress() * 100) + "%";
        setText(fileHandler.selectedFileProperty().getValue() + percentages);
    }

}

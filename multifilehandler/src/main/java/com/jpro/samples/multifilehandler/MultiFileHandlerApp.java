package com.jpro.samples.multifilehandler;

import com.jpro.webapi.JProApplication;
import com.jpro.webapi.WebAPI;
import fr.brouillard.oss.cssfx.CSSFX;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class MultiFileHandlerApp extends JProApplication {

    @Override
    public void start(javafx.stage.Stage primaryStage) {
        CSSFX.start();
        Scene scene = new Scene(new MultiFileHandler());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    class MultiFileHandler extends VBox {

        List<WebAPI.JSFile> jsFiles = new ArrayList<>();

        public MultiFileHandler() {
            getStylesheets().add(getClass()
                    .getResource("/com/jpro/samples/multifilehandler/multifilehandler.css").toExternalForm());
            getStyleClass().add("vbox");

            Label label = new Label("MultiFileHandler");
            label.getStyleClass().add("mf-title");
            getChildren().add(label);

            Label label2 = new Label("Drop Files to upload them");
            label2.getStyleClass().add("mf-description");
            getChildren().add(label2);

            VBox filesVBox = new VBox();
            filesVBox.getStyleClass().add("mf-files");
            ScrollPane filesScrollPane = new ScrollPane(filesVBox);
            filesScrollPane.setFitToWidth(true);
            StackPane filesContainer = new StackPane(filesScrollPane);
            filesContainer.getStyleClass().add("mf-files-container");
            getChildren().add(filesContainer);


            Button downloadButton = new Button("Download as zip");
            downloadButton.getStyleClass().add("mf-download-button");
            getChildren().add(downloadButton);

            downloadButton.setOnAction(e -> {

                List<CompletableFuture<File>> filesF = jsFiles.stream()
                        .map(WebAPI.JSFile::getUploadedFileFuture).toList();
                CompletableFuture.allOf(filesF.toArray(new CompletableFuture[filesF.size()])).thenRun(() -> {
                    List<File> files = filesF.stream().map(CompletableFuture::join).collect(Collectors.toList());
                    // Create a zip file from the files
                    File zipFile = new File("download.zip");
                    try {
                        createZipFile(zipFile, files);
                        // Download the zip file
                        getWebAPI().downloadURL(zipFile.toURI().toURL());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                });
            });


            WebAPI.getWebAPI(this, webAPI -> {
                WebAPI.MultiFileUploader fuploader = webAPI.makeMultiFileUploadNode(filesContainer);
                fuploader.fileDragOverProperty().addListener((o, oldV, newV) -> {
                    if (newV) {
                        filesContainer.getStyleClass().add("file-drag");
                    } else {
                        filesContainer.getStyleClass().remove("file-drag");
                    }
                });
                fuploader.setSelectFileOnClick(true);
                fuploader.setSelectFileOnDrop(true);
                fuploader.setOnFilesSelected(files -> {
                    for (WebAPI.JSFile file : files) {
                        file.uploadFile();
                        JSFileInfoCell cell = new JSFileInfoCell(file);
                        filesVBox.getChildren().add(cell);
                        jsFiles.add(file);
                    }
                });
            });
        }
    }

    static class JSFileInfoCell extends HBox {
        JSFileInfoCell(WebAPI.JSFile jsfile) {
            getStyleClass().add("mf-file-cell");
            Label label = new Label(jsfile.getFilename());
            getChildren().add(label);

            ProgressBar progressBar = new ProgressBar();
            getChildren().add(progressBar);
            progressBar.progressProperty().bind(jsfile.progressProperty());

            // Size
            Label sizeLabel = new Label();
            getChildren().add(sizeLabel);
            long size = jsfile.getFileSize();
            // in kb or mb
            if (size > 1024 * 1024) {
                sizeLabel.setText(String.format("%.2f MB", size / (1024.0 * 1024.0)));
            } else if (size > 1024) {
                sizeLabel.setText(String.format("%.2f KB", size / (1024.0)));
            } else {
                sizeLabel.setText(String.format("%d B", size));
            }
        }
    }

    static void createZipFile(File zipFile, List<File> files) throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
            for (File file : files) {
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zos.putNextEntry(zipEntry);
                Files.copy(file.toPath(), zos);
                zos.closeEntry();
            }
        }
    }
}

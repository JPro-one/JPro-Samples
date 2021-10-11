package com.jpro.samples.gps;

import com.jpro.webapi.HTMLView;
import com.jpro.webapi.JProApplication;
import com.jpro.webapi.WebAPI;
import fr.brouillard.oss.cssfx.CSSFX;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GPSSample extends JProApplication {
    @Override
    public void start(Stage stage) throws Exception {

        Label header = new Label("Geolocation Sample");
        header.getStyleClass().add("header");
        Label requestLabel = new Label("Click here to load your current location");
        requestLabel.getStyleClass().add("request-label");

        VBox pin = new VBox(requestLabel);
        VBox root = new VBox(header, pin);

        root.getStyleClass().add("root");
        pin.getStyleClass().add("vbox");

        Scene scene = new Scene(root);
        root.getStylesheets().add(getClass().getResource("/com/jpro/samples/gps/css/gps.css").toString());
        stage.setScene(scene);
        stage.show();

        CSSFX.start();

        requestLabel.setOnMouseClicked(e -> {
            pin.getChildren().clear();
            pin.getChildren().add(new ProgressIndicator());
            new GPSUtil(this.getWebAPI()).requestData(result -> {
                if(result == null) {
                    Label errorLabel = new Label("User denied access to gps data!");
                    errorLabel.getStyleClass().add("error");
                    pin.getChildren().clear();
                    pin.getChildren().add(errorLabel);
                } else {
                    pin.getChildren().clear();
                    pin.getChildren().add(createMap(result.longitude,result.latitude));
                }
            });
        });
    }

    HTMLView createMap(double longitude, double latitude) {

        HTMLView view = new HTMLView(createContent(longitude,latitude));
        view.setPrefWidth(450);
        view.setPrefHeight(450);

        return view;
    }

    private String createContent(double longitude, double latitude) {
        double off = 0.002;
        return "<iframe style=\"width:100%; height:100%\" frameborder=\"0\" scrolling=\"no\" marginheight=\"0\" marginwidth=\"0\" src=\"" +
                "https://www.openstreetmap.org/export/embed.html?" +
                "bbox="+(longitude-off)+"%2C"+(latitude-off)+"%2C"+(longitude+off)+"%2C"+(latitude+off)+"&amp;layer=mapnik" +
                "&amp;marker="+latitude+"%2C" + longitude +
                "\" style=\"border: 1px solid black\"></iframe>";

    }
}

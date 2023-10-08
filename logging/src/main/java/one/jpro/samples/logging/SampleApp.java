package one.jpro.samples.logging;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Example application for custom logging configuration.
 *
 * @author Besmir Beqiri
 */
public class SampleApp extends Application {

    @Override
    public void start(Stage stage) {
        Label label = new Label("Hello JPro!");
        label.setFont(new Font(50));
        label.setAlignment(Pos.CENTER);
        stage.setScene(new Scene(label));
        stage.show();
    }

    /**
     * Application entry point.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}

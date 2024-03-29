package com.jpro.samples.popups;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jpro.webapi.JProApplication;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

public class PopupsApp extends JProApplication {

    StackPane stackpane = null;

    public static final String CONTENT_PANE = "ContentPane";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stackpane = new StackPane();
        JFXButton buttonJFoenix = new JFXButton("JFoenix");
        buttonJFoenix.setButtonType(JFXButton.ButtonType.RAISED);
        buttonJFoenix.setStyle("-fx-background-color:#5264AE;");

        JFXDialog jfxdialog = new JFXDialog();

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label("Dialoge"));
        layout.setBody(new Label("text text text\n"
                + "text text text\n"
                + "text text text"));
        JFXButton closeButton = new JFXButton("ACCEPT");
        closeButton.getStyleClass().add("dialog-accept");
        closeButton.setOnAction(event -> jfxdialog.close());
        layout.setActions(closeButton);

        jfxdialog.setContent(layout);

        //jfxdialog.setContent(new Label("I'm the content!"));
        buttonJFoenix.setOnAction(action -> {
            jfxdialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
            jfxdialog.show(stackpane);
        });

        Button stageAsTabButton = new Button("Open Stage as Tab");
        stageAsTabButton.setOnAction(e -> {
            getWebAPI().openStageAsTab(createTestStage());
        });

        Button stageAsPopupButton = new Button("Open Stage as Popup");
        stageAsPopupButton.setOnAction(e -> {
            getWebAPI().openStageAsPopup(createTestStage());
        });

        VBox vbox = new VBox(buttonJFoenix, controlsFXNotifications(), ownDialogButton(), stageAsTabButton, stageAsPopupButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);

        stackpane.getChildren().add(vbox);

        Scene scene = new Scene(stackpane, 500, 500);

        stage.setScene(scene);
        stage.show();
    }

    Button controlsFXNotifications() {
        Button button = new Button("ControlsFX");

        button.setOnAction(e -> {
            Notifications notificationBuilder = Notifications.create()
                    .title("Title Text")
                    .text("Some text")
                    .owner(button)
                    .position(Pos.CENTER);
            notificationBuilder.showConfirm();
        });

        return button;
    }

    Parent ownDialogButton() {
        Button btn = new Button("Based on StackPane");
        btn.setOnAction(e -> new OwnPopup(stackpane, "My own popup!").show());
        return btn;
    }

    Stage createTestStage() {
        Stage stage = new Stage();
        Label label = new Label("Test Stage!");
        label.setFont(new Font(36));
        stage.setScene(new Scene(label));
        return stage;
    }
}


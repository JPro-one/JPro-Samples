package com.jpro.samples.pwa;

import com.jpro.webapi.WebAPI;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Optional;

public class PWAUtil {

    private WebAPI webapi;
    private ObjectProperty<Optional<Boolean>> isInstalledProperty = new SimpleObjectProperty(Optional.empty());

    public PWAUtil(Stage stage) {
        this(WebAPI.getWebAPI(stage));
    }
    public PWAUtil(WebAPI webapi) {
        this.webapi = webapi;

        webapi.registerJavaFunction("setPWAInstalled", s -> {
            System.out.println("s: " + s);
            boolean isInstalled = s.equals("true");
            isInstalledProperty.set(Optional.of(isInstalled));
        });
        webapi.executeScript("jpro.setPWAInstalled(pwaIsInstalled);");
    }

    public ObjectProperty<Optional<Boolean>> installed() {
        return isInstalledProperty;
    }

    /*
     * InsOpens the popup to install the PWA.
     */
    public void installPWA() {
        webapi.executeScript("\n"
                + "deferredPrompt.prompt().then(function(v){jpro.setPWAInstalled(v.outcome == 'accepted')});");
    }

}

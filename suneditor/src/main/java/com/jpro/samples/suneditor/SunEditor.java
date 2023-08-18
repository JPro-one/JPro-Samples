package com.jpro.samples.suneditor;

import com.jpro.webapi.HTMLView;
import com.jpro.webapi.WebAPI;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.StackPane;
import org.json.JSONTokener;

import java.util.Objects;

public class SunEditor extends StackPane {

    static int idCounter = 0;
    static String getNewId() {
        idCounter += 1;
        return "suneditor_" + idCounter;
    }

    HTMLView htmlView = new HTMLView();

    private WebAPI webAPI = null;
    String id;

    StringProperty textProperty = new SimpleStringProperty("");

    private String elementContent = textProperty.get();

    SunEditor() {
        getChildren().add(htmlView);
        htmlView.setBlockKeyboardInput(true);
        id = getNewId();
        setOnMouseClicked(e -> requestFocus());
        WebAPI.getWebAPI(this, webAPI -> {
            this.webAPI = webAPI;
            webAPI.loadCSSFile(getClass().getResource("/com/jpro/samples/suneditor/http/css/suneditor.min.css"));
            webAPI.loadJSFile(getClass().getResource("/com/jpro/samples/suneditor/http/js/suneditor.min.js"));
            webAPI.loadJSFile(getClass().getResource("/com/jpro/samples/suneditor/http/js/en.js"));
            htmlView.setContent("<textarea id=\"" + id + "\">"+textProperty.get()+"</textarea>");
            Platform.runLater(() -> {
                webAPI.executeScript("var element = document.getElementById('"+id+"');" +
                        "console.log('element: ' + element);" +
                        "var editor = SUNEDITOR.create(element,{\n" +
                        "});" +
                        "jpro."+id+"=editor;");

                webAPI.registerJavaFunction("callback1_"+id, str -> {
                    JSONTokener tockener = new JSONTokener(str);
                    tockener.nextClean();
                    String str2 = tockener.nextString('"');
                    elementContent = str2;
                    textProperty.set(str2);

                    System.out.println("Got str: " + str2);
                });

                webAPI.executeScript("jpro."+id+".onChange = function(contents,core){" +
                        "  jpro.callback1_"+id+"(contents);" +
                        "};");

                textProperty.addListener((ps,os,ns) -> {
                    if(!Objects.equals(elementContent, ns)) {
                        String script = "jpro."+id+".setContents(\""+ns.replace("\\","\\\\").replace("\"","\\\"")+"\");";
                        webAPI.executeScript(script);
                    }
                });

                widthProperty().addListener((p,o,n) -> updateWH());
                heightProperty().addListener((p,o,n) -> updateWH());
                updateWH();
            });
        });
    }

    private void updateWH() {
        webAPI.executeScript("var element = document.getElementById('"+id+"');" +
                "jpro."+id+".setOptions({height: " + this.getHeight() + ", width: " + this.getWidth() + "});");
    }
}

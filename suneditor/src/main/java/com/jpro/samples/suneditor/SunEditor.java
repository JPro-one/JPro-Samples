package com.jpro.samples.suneditor;

import com.jpro.webapi.HTMLView;
import com.jpro.webapi.JSVariable;
import com.jpro.webapi.WebAPI;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.StackPane;
import org.json.JSONTokener;

public class SunEditor extends StackPane {


    HTMLView htmlView = new HTMLView();

    private WebAPI webAPI = null;

    StringProperty textProperty = new SimpleStringProperty("");

    private String elementContent = textProperty.get();

    JSVariable sunElem;
    JSVariable callback;

    SunEditor() {
        getChildren().add(htmlView);
        htmlView.setBlockKeyboardInput(true);
        setOnMouseClicked(e -> requestFocus());
        WebAPI.getWebAPI(this, webAPI -> {
            this.webAPI = webAPI;
            webAPI.loadCSSFile(getClass().getResource("/com/jpro/samples/suneditor/http/css/suneditor.min.css"));
            webAPI.loadJSFile(getClass().getResource("/com/jpro/samples/suneditor/http/js/suneditor.min.js"));
            webAPI.loadJSFile(getClass().getResource("/com/jpro/samples/suneditor/http/js/en.js"));

            htmlView.setContent("<textarea>" + textProperty.get() + "</textarea>");

            JSVariable textarea = webAPI.getHTMLViewElement(htmlView);

            sunElem = webAPI.executeScriptWithVariable("SUNEDITOR.create(" + textarea.getName() + ",{});");

            callback = webAPI.registerJavaFunction(str -> {
                JSONTokener tokener = new JSONTokener(str);
                tokener.nextClean();
                String str2 = tokener.nextString('"');
                elementContent = str2;
                textProperty.set(str2);
            });
            webAPI.executeScript(sunElem.getName() + ".onChange = function(contents,core){" +
                    "  " + callback.getName() + "(contents);" +
                    "};");
            textProperty.addListener((ps, os, ns) -> {
                if (elementContent != ns) {
                    String script = sunElem.getName() + ".setContents(\"" + ns.replace("\\", "\\\\").replace("\"", "\\\"") + "\");";
                    webAPI.executeScript(script);
                }
            });

            widthProperty().addListener((p, o, n) -> updateWH(sunElem));
            heightProperty().addListener((p, o, n) -> updateWH(sunElem));
            updateWH(sunElem);
        });
    }

    private void updateWH(JSVariable sunElem) {
        webAPI.executeScript(sunElem.getName() + ".setOptions({height: " + this.getHeight() + ", width: " + this.getWidth() + "});");
    }
}

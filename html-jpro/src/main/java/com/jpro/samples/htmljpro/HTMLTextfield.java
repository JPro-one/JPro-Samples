package com.jpro.samples.htmljpro;

import com.jpro.webapi.HTMLView;
import com.jpro.webapi.WebAPI;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.json.JSONObject;
import org.json.JSONString;
import org.json.JSONTokener;

public class HTMLTextfield extends HTMLView {

    static int counter = 0;
    int id = 1;

    StringProperty contentProperty = new SimpleStringProperty();

    public HTMLTextfield(String content) {
        counter += 1;
        id = counter;
        setContent("<input id=\""+id+"\" value=\""+content+"\" />");

        setFocusTraversable(true);


        sceneProperty().addListener((p,o,n) -> {
            Platform.runLater(() -> {
                if(n != null) {
                    WebAPI webapi = WebAPI.getWebAPI(n);
                    webapi.registerJavaFunction("textfield_" + id, str -> {
                        JSONTokener tockener = new JSONTokener(str);
                        tockener.nextClean();
                        String str2 = tockener.nextString('"');
                        contentProperty.set(str2);
                    });
                    webapi.executeScript("var element = document.getElementById('"+id+"');" +
                            "element.addEventListener(\"input\", function(){" +
                            "  jpro.textfield_"+id+"(element.value);" +
                            "})");
                    contentProperty.addListener((ps,os,ns) -> {
                        String script = "var element = document.getElementById('"+id+"');" +
                                "element.value = \""+ns.replace("\\","\\\\").replace("\"","\\\"")+"\"";

                        webapi.executeScript(script);
                    });
                }
            });
        });
    }
}


package com.jpro.samples.website;


import com.jpro.samples.website.page.LandingPage;
import com.jpro.samples.website.page.SubPage;
import com.jpro.web.Redirect;
import com.jpro.web.WebApp;
import javafx.stage.Stage;

public class HelloJProApp extends WebApp {
    HelloJProApp(Stage stage) {
        super(stage);

        getStylesheets().add("/com/jpro/samples/website/css/website.css");

        addRouteJava((s) -> {
            if(s.equals("") || s.equals("/")) {
                return new Redirect("/?page=main");
            } else if(s.equals("/?page=main")) {
                return new LandingPage();
            } else if(s.equals("/?page=sub")) {
                return new SubPage();
            } else {
                return null;
            }
        });
    }
}

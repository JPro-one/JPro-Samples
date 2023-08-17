package com.jpro.samples.website;


import com.jpro.samples.website.page.LandingPage;
import com.jpro.samples.website.page.SubPage;
import one.jpro.routing.Route;
import one.jpro.routing.RouteApp;

import static one.jpro.routing.RouteUtils.get;
import static one.jpro.routing.RouteUtils.redirect;

public class HelloJProApp extends RouteApp {

    @Override
    public Route createRoute() {
        getScene().getStylesheets().add("/com/jpro/samples/website/css/website.css");

        return Route.empty()
                .and(redirect("/", "/?page=main"))
                .and(get("/?page=main", request -> new LandingPage()))
                .and(get("/?page=sub", request -> new SubPage()));
    }
}

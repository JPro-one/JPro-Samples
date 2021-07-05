open module JPro.Samples.auth0.main {
    requires jpro.web.core;
    requires jpro.webapi;
    requires javafx.controls;
    requires com.fasterxml.jackson.databind;
    requires com.auth0.jwt;

    exports com.jpro.samples.auth0;
}
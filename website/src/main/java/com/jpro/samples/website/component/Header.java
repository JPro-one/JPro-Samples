package com.jpro.samples.website.component;

import com.jpro.web.Util;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Header extends HBox {

    public Header() {
        getStyleClass().add("header");
        getChildren().add(new HeaderLabel("Main", "/?page=main"));
        getChildren().add(new HeaderLabel("Sub", "/?page=sub"));
    }

    class HeaderLabel extends Label {
        public HeaderLabel(String x, String url) {
            super(x);
            Util.setLink(this,url);
            getStyleClass().add("header-label");

        }
    }

}

module one.jpro.samples.logging {
    requires javafx.controls;
    requires java.logging;
    requires org.slf4j;
    requires jul.to.slf4j;
    requires ch.qos.logback.core;
    requires ch.qos.logback.classic;

    exports one.jpro.samples.logging;
}
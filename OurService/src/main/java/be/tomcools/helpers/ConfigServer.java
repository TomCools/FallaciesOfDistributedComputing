package be.tomcools.helpers;

import io.vertx.core.Vertx;

public class ConfigServer {
    public static void main(String[] args) {
        Vertx.vertx().createHttpServer()
                .requestHandler(h -> {
                    h.response().end("fallback.text=FromConfigServer");
                })
                .listen(8081);
    }
}

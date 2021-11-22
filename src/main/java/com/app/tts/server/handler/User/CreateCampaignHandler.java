package com.app.tts.server.handler.User;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.web.RoutingContext;

public class CreateCampaignHandler implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
            	JsonObject json = rc.getBodyAsJson();
            	String id = json.getString("id");
            	String user_id = json.getString("user_id");
            	
		    } catch (Exception e) {
		        rc.fail(e);
		    }
		}, asyncResult -> {
		    if (asyncResult.succeeded()) {
		        rc.next();
		    } else {
		        rc.fail(asyncResult.cause());
		    }
        });
    }
}

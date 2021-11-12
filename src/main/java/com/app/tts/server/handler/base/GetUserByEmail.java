package com.app.tts.server.handler.base;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.app.tts.services.SubService;
import com.app.tts.session.redis.SessionStore;
import com.app.tts.util.AppParams;


public class GetUserByEmail implements Handler<RoutingContext>, SessionStore {
	@Override
    public void handle(RoutingContext routingContext) {
		routingContext.vertx().executeBlocking(future -> {
            try {
                JsonObject json = routingContext.getBodyAsJson();
                String email = json.getString("email");
                Map data = new HashMap();
                List<Map> User = SubService.getUserByEmail(email);
                LOGGER.info("Result: " + User);
//                JsonArray ListCusJson = new JsonArray(ListCus);
                data.put("User", User);

//                ListCusJson.toString();
                routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.OK.code());
                routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.OK.reasonPhrase());
                routingContext.put(AppParams.RESPONSE_DATA, data);
                future.complete();
            } catch (Exception e) {
            	routingContext.fail(e);
            }
        }, asyncResult -> {
            if (asyncResult.succeeded()) {
            	routingContext.next();
            } else {
            	routingContext.fail(asyncResult.cause());
            }
        });
    }

    private static final Logger LOGGER = Logger.getLogger(GetUserByEmail.class.getName());

}

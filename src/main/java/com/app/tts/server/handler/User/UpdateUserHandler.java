package com.app.tts.server.handler.User;

import com.app.tts.services.UserServices;
import com.app.tts.util.AppParams;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class UpdateUserHandler implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.vertx().executeBlocking(future -> {
            try{
                JsonObject jsonRequest = routingContext.getBodyAsJson();
                String email = jsonRequest.getString(AppParams.S_EMAIL);
                String password = jsonRequest.getString(AppParams.S_PASSWORD);
                String confirmPassword = jsonRequest.getString("confirmPassword");

                JsonObject data = new JsonObject();

                routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.BAD_REQUEST.code());
                routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.BAD_REQUEST.reasonPhrase());
                data.put("email", email);

                LOGGER.info("---email = " + email);
                Map user = UserServices.searchUserByEmail(email);

                boolean duplicate = false;
                if(user != null){
                    duplicate = true;
                }
                if(!password.equals(confirmPassword)){
//                    duplicate = true;
                    data.put("message", "update failed, password and confirm password are not matched");
                } else if(duplicate){
                    LOGGER.info("UPDATE START-----------");
                    List<Map> resultUpdate = update(email, password);
//                    data.put("message", "update successed");
                    LOGGER.info("resultUpdate -----" + resultUpdate);
                    data.put(AppParams.RESPONSE_DATA, resultUpdate);
                    routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.OK.code());
                    routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.OK.reasonPhrase());
                }else{
                    data.put("message", "update failed");
                }
                routingContext.response().end(Json.encodePrettily(data));
                future.complete();
            }catch (Exception e){
                routingContext.fail(e);
            }
        }, asyncResult -> {
            if(asyncResult.succeeded()){
                routingContext.next();
            }else {
                routingContext.fail(asyncResult.cause());
            }
        });
    }

    public List<Map> update(String email, String password) throws SQLException {
        List<Map> resultUpdate = UserServices.updateUser(email, password);
        return resultUpdate;
    }

    private static final Logger LOGGER = Logger.getLogger(UpdateUserHandler.class.getName());
}
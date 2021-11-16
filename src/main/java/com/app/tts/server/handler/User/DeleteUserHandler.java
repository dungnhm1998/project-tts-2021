package com.app.tts.server.handler.User;

import com.app.tts.services.UserService;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class DeleteUserHandler implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.vertx().executeBlocking(future -> {

            try{
                JsonObject jsonObject = routingContext.getBodyAsJson();
                String email = jsonObject.getString(AppParams.S_EMAIL);
                String password = jsonObject.getString(AppParams.S_PASSWORD);

                LOGGER.info("email -----------" + email + "---password-----------" + password);
                JsonObject data = new JsonObject();
                String message;

                routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.OK.code());
                routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.OK.reasonPhrase());

                List<Map> searchEmail = UserService.getUserByEmail(email);

                if(!searchEmail.isEmpty()){
                    Map searchMap = searchEmail.get(0);
                    String rsPassword = ParamUtil.getString(searchMap, "S_PASSWORD");
                    if(rsPassword.equals(password)){
                        Map resultDel = delete(email);
                        message = "Delete success";
                        data.put(AppParams.RESPONSE_DATA, resultDel);
                    }else{
                        message = "Password wrong";
                    }
                }else{
                    message = "Email is not exit";
                }
                data.put(AppParams.RESPONSE_MSG, message);
                routingContext.response().end(Json.encodePrettily(data));
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

    public static Map delete(String email) throws SQLException {
        List<Map> result = UserService.deleteUser(email);
        Map resultMap = result.get(0);
        return resultMap;
    }

    public static final Logger LOGGER = Logger.getLogger(DeleteUserHandler.class.getName());
}

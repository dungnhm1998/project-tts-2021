package com.app.tts.server.handler.User;

import com.app.tts.services.UserServices;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class LoginUserHandler implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.vertx().executeBlocking(future -> {
            try{
                JsonObject  jsonObject = routingContext.getBodyAsJson();
                String email = jsonObject.getString(AppParams.S_EMAIL);
                String password = jsonObject.getString(AppParams.S_PASSWORD);
                LOGGER.info("email json = " + email + " ; password json = " + password);
                routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.OK.code());
                routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.OK.reasonPhrase());
                String message = login(email, password);
                LOGGER.info("message = ---------" + message);
                routingContext.response().end(message);
                future.complete();
            }catch (Exception e){
                routingContext.fail(e);
            }
        }, asyncResult -> {
            if(asyncResult.succeeded()){
                routingContext.next();
            }else{
                routingContext.fail(asyncResult.cause());
            }
        });
    }

    public static String login(String email, String password) throws SQLException {
        List<Map> result = UserServices.loginUser(email);
        String message;
        if(!result.isEmpty()){
            Map resultMap = result.get(0);
            String rsPassword = ParamUtil.getString(resultMap, AppParams.S_PASSWORD);
            LOGGER.info("password = ---------------" + rsPassword);
            if(password.equals(rsPassword)){
                message = "Login success";
            }else{
                message = "wrong password";
            }
        }else {
            message = "Email does not exist";
        }
        return message;
    }

    private static final Logger LOGGER = Logger.getLogger(LoginUserHandler.class.getName());
}

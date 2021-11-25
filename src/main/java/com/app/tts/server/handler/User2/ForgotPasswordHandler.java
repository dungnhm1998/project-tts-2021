package com.app.tts.server.handler.User2;

import com.app.tts.services.UserService2;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;
import org.apache.commons.lang3.RandomStringUtils;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ForgotPasswordHandler implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.vertx().executeBlocking(future -> {
            try {
                Map jsonRequest = routingContext.getBodyAsJson().getMap();
                String email = ParamUtil.getString(jsonRequest, AppParams.EMAIL);
                String message = null;
                Map data = new LinkedHashMap();
                Boolean checkEmail = RegisterUserHandler2.checkEmail(email);
                if(checkEmail){
                    Map result = forgotPassword(email);
                    data.put(AppParams.MESSAGE, "recover password successfully");
                    data.put("recover_password", ParamUtil.getString(result, AppParams.S_PASSWORD));
                }else{
                    message = "Incorrect email";
                }
                if(data.get(AppParams.MESSAGE) == null){
                    data.put(AppParams.MESSAGE, message);
                }

                routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.BAD_REQUEST.code());
                routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.BAD_REQUEST.reasonPhrase());
                routingContext.put(AppParams.RESPONSE_DATA, data);

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

    public static Map forgotPassword(String email) throws SQLException{
        // tao chuoi ngau nhien co 6 ky tu bao gom chu va so
        String recoverPassword = RandomStringUtils.randomAlphanumeric(6);
        String passwordMd5 = RegisterUserHandler2.getMd5(recoverPassword);
        Map result = UserService2.updatePassword(email, passwordMd5);
        result.put(AppParams.S_PASSWORD, recoverPassword);
        return result;
    }

}

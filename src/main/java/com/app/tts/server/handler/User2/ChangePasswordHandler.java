package com.app.tts.server.handler.User2;

import com.app.tts.services.UserService2;
import com.app.tts.util.AppParams;
import com.app.tts.util.FormatUtil;
import com.app.tts.util.ParamUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ChangePasswordHandler implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.vertx().executeBlocking(future -> {
            try {
                Map jsonRequest = routingContext.getBodyAsJson().getMap();
                String email = ParamUtil.getString(jsonRequest, AppParams.EMAIL);
                String password = ParamUtil.getString(jsonRequest, AppParams.PASSWORD);
                String newPassword = ParamUtil.getString(jsonRequest, AppParams.NEW_PASSWORD);
                String confirmPassword = ParamUtil.getString(jsonRequest, AppParams.CONFIRM_PASSWORD);

                String message = null;
                Map data = new LinkedHashMap();

                String passwordMd5 = FormatUtil.getMd5(password);
                Map userDB = UserService2.getUserByEmail(email);
                String passwordDB = ParamUtil.getString(userDB, AppParams.S_PASSWORD);
                if (!passwordDB.isEmpty()) {
                    if (passwordMd5.equals(passwordDB)) {
                        if (newPassword.equals(confirmPassword)) {
                            changePassword(email, newPassword);
                            message = "change password successfully";
                        } else {
                            message = "confirm password is not correct";
                        }
                    } else {
                        message = "old Password is not correct";
                    }
                } else {
                    message = "email is not correct";
                }
                data.put(AppParams.MESSAGE, message);

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

    public static Map changePassword(String email, String password) throws SQLException {
        String passwordMd5 = FormatUtil.getMd5(password);
        Map result = UserService2.updatePassword(email, passwordMd5);
        return result;
    }
}

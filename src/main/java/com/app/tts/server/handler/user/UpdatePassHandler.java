package com.app.tts.server.handler.User;

import com.app.tts.services.UserService;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class UpdatePassHandler implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                Map jsonRequest = rc.getBodyAsJson().getMap();
                String email = ParamUtil.getString(jsonRequest, AppParams.EMAIL);
                String passwordold = ParamUtil.getString(jsonRequest, "passwordold");
                String password = ParamUtil.getString(jsonRequest, AppParams.PASSWORD);
                String confirmPassword = ParamUtil.getString(jsonRequest, "confirmPassword");


                Map data = new HashMap();

                List<Map> user = UserService.getPassByEmail(email);

                Map list = user.get(0);
                String pass = ParamUtil.getString(list, AppParams.S_PASSWORD);

                boolean duplicate = false;
                if (!user.isEmpty()) {
                    duplicate = true;
                }
                if (!pass.equals(passwordold)) {
                    data.put("message", "đăng ký thất bại! , mật khẩu cũ không đúng");
                } else if (!password.equals(confirmPassword)) {
                    data.put("message", "đăng ký thất bại! , 2 mật khẩu không trùng nhau");
                } else if (18 <= password.length() && password.length() <= 6) {
                    data.put("message", "đăng ký thất bại! , mật khẩu từ 6 đến 18 kí tự ");
                } else  {
                    UserService.updatePass(email, password);
                    data.put("message", "change password successfully");
                    rc.put(AppParams.RESPONSE_CODE, HttpResponseStatus.BAD_REQUEST.code());
                    rc.put(AppParams.RESPONSE_MSG, HttpResponseStatus.BAD_REQUEST.reasonPhrase());
                    rc.put(AppParams.RESPONSE_DATA, data);
                    future.complete();
                }


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

    private static final Logger LOGGER = Logger.getLogger(UpdatePassHandler.class.getName());
}

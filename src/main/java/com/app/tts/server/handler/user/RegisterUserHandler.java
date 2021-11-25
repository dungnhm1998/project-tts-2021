package com.app.tts.server.handler.user;

import com.app.tts.encode.Md5Code;

import com.app.tts.services.UserService;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;
import org.apache.commons.validator.routines.EmailValidator;
import redis.clients.jedis.params.Params;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class RegisterUserHandler implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                Map jsonRequest = rc.getBodyAsJson().getMap();
                String email = ParamUtil.getString(jsonRequest, AppParams.EMAIL);
                String password = ParamUtil.getString(jsonRequest, AppParams.PASSWORD);
                String confirmPassword = ParamUtil.getString(jsonRequest, "confirmPassword");
                String username = ParamUtil.getString(jsonRequest, AppParams.USERNAME);
                String address = ParamUtil.getString(jsonRequest, AppParams.ADDRESS);
                String phone = ParamUtil.getString(jsonRequest, AppParams.PHONE);

                Map data = new HashMap();


                List<Map> user = UserService.getUserByEmail1(email);


                boolean duplicate = false;
                if (!user.isEmpty()) {
                    duplicate = true;
                }
                if (!password.equals(confirmPassword)) {
                    data.put("message", "password and confirm password are not matched");
                } else if (18 < password.length() || password.length() < 6) {
                    data.put("message", "password must be between 6 and 18 characters ");
                } else if (!password.matches(".*[A-Z].*+")) {
    				data.put("message", "password must contain at least one uppercase character");
    			} else if (!password.matches(".*[0-9].*+")) {	
    				data.put("message", "password must contain at least one numeric character");
                } else if (phone.length() != 10) {
                    data.put("message", "phone must contain 10 numeric characters");
                } else if (!isValid(email)) {
                    data.put("message", "register failed, email is not valid");
                } else if (duplicate) {
                    data.put("message", "register failed! , email's been used");
                } else if (!duplicate && isValid(email)) {
                    // Đăng ký thành công
                    List<Map> userJson = UserService.insertUser(email, Md5Code.md5(password), username, address, phone, "active");
//                    data.put("userjson",userJson);
                    Map list = userJson.get(0);
                    String id = ParamUtil.getString(list, AppParams.ID);
                    data.put("id", id);
                    data.put("avatar", "");
                    data.put("message", "register successed");
                    data.put("email", email);
                    rc.put(AppParams.RESPONSE_CODE, HttpResponseStatus.CREATED.code());
                    rc.put(AppParams.RESPONSE_MSG, HttpResponseStatus.CREATED.reasonPhrase());
                } else {
                    data.put("message", "register failed");
                }
                rc.put(AppParams.RESPONSE_DATA, data);
                future.complete();
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

    public static boolean isValid(String email) {
        boolean valid = false;
        valid = EmailValidator.getInstance().isValid(email);
        return true;
    }

    private static final Logger LOGGER = Logger.getLogger(RegisterUserHandler.class.getName());
}

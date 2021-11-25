package com.app.tts.server.handler.user;

import com.app.tts.services.UserService;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;
import org.apache.commons.validator.routines.EmailValidator;

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
                Map emailuser = UserService.getUserByEmail(email);
                List<Map> user = UserService.getPassByEmail(email);

                Map list = user.get(0);
                String pass = ParamUtil.getString(list, AppParams.S_PASSWORD);

                boolean duplicate = false;
                if (!user.isEmpty() && !emailuser.isEmpty()) {
                    duplicate = true;
                }
                if (!pass.equals(passwordold)) {
                    data.put("message", "đăng ký thất bại! , mật khẩu cũ không đúng");
                } else if (!password.equals(confirmPassword)) {
                    data.put("message", "đăng ký thất bại! , 2 mật khẩu không trùng nhau");
                } else if (18 <= password.length() && password.length() <= 6) {
                    data.put("message", "đăng ký thất bại! , mật khẩu từ 6 đến 18 kí tự ");
                } else if (!isValid(email)) {
                    data.put("message", "sửa thất bại! , email không đúng định dạng");
                } else if (!duplicate) {
                    data.put("message", "sửa thất bại! , không tìm được email này" + email);
                } else if (duplicate && isValid(email)) {
                    UserService.updatePass(email, password);
                    data.put("message", "change password successfully");
                    rc.put(AppParams.RESPONSE_CODE, HttpResponseStatus.BAD_REQUEST.code());
                    rc.put(AppParams.RESPONSE_MSG, HttpResponseStatus.BAD_REQUEST.reasonPhrase());
                } else {
                    data.put("message", "sửa thất bại");
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

    private static final Logger LOGGER = Logger.getLogger(UpdatePassHandler.class.getName());
}

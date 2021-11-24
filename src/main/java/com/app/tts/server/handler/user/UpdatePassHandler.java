package com.app.tts.server.handler.User;

import com.app.tts.encode.Md5Code;
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
                String password = ParamUtil.getString(jsonRequest, "password");
                String new_password = ParamUtil.getString(jsonRequest, "new_password");
                String confirm_password = ParamUtil.getString(jsonRequest, "confirm_password");

                Map data = new HashMap();
                Map emailuser = UserService.getUserByEmail(email);
                List<Map> user = UserService.getPassByEmail(email);

                Map list = user.get(0);
                String pass = ParamUtil.getString(list, AppParams.S_PASSWORD);

                boolean duplicate = false;
                if (!user.isEmpty() && !emailuser.isEmpty()) {
                    duplicate = true;
                }
                if (!pass.equals(password)) {
                    data.put("message", "Login failed! , password is incorrect");
                } else if (!new_password.equals(confirm_password)) {
                    data.put("message", "New password and confirm password are not matched");
                } else if (18 < new_password.length() || new_password.length() < 6) {
                    data.put("message", "password must be between 6 and 18 characters ");
                } else if (!new_password.matches(".*[A-Z].*+")) {
    				data.put("message", "password must contain at least one uppercase character");
    			} else if (!new_password.matches(".*[0-9].*+")) {	
    				data.put("message", "password must contain at least one numeric character");
                } else if (!isValid(email)) {
                    data.put("message", "Email is not valid");
                } else if (!duplicate) {
                    data.put("message", "Email hasn't registered yet" + email);
                } else if (duplicate && isValid(email)) {
                    UserService.updatePass(email, Md5Code.md5(new_password));
                    data.put("message", "change password successfully");
                    rc.put(AppParams.RESPONSE_CODE, HttpResponseStatus.BAD_REQUEST.code());
                    rc.put(AppParams.RESPONSE_MSG, HttpResponseStatus.BAD_REQUEST.reasonPhrase());
                } else {
                    data.put("message", "failed to change password");
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

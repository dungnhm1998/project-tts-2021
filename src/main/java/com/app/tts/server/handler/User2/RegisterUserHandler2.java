package com.app.tts.server.handler.User2;

import com.app.tts.encode.Md5Code;
import com.app.tts.services.UserService2;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.web.RoutingContext;
import org.apache.commons.validator.routines.EmailValidator;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class RegisterUserHandler implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.vertx().executeBlocking(future -> {
            try{
                Map jsonRequest = routingContext.getBodyAsJson().getMap();
                String email = ParamUtil.getString(jsonRequest, AppParams.EMAIL);
                String password = ParamUtil.getString(jsonRequest, AppParams.PASSWORD);
                String confirm_password = ParamUtil.getString(jsonRequest, AppParams.CONFIRM_PASSWORD);
                String phone = ParamUtil.getString(jsonRequest, AppParams.PHONE);

                String message = null;
                JsonObject jsonObject = new JsonObject();

                if(confirm_password.equals(password)){
                    if(!checkEmail(email)){
                        if(validateEmail(email)){
                            String passwordMD5 = getMd5(password);
                            Map result = registerUser(email, passwordMD5, phone);
                            jsonObject.put(AppParams.ID, ParamUtil.getString(result, AppParams.ID));
                            jsonObject.put("avatar", "");
                            jsonObject.put(AppParams.MESSAGE, "register successed");
                            jsonObject.put(AppParams.EMAIL, ParamUtil.getString(result, AppParams.EMAIL));
                        }else{
                            message = "email do not validate";
                        }
                    }else{
                        message = "email existed";
                    }
                }else{
                    message = "confirm_password not equals password";
                }

                routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.BAD_REQUEST.code());
                routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.BAD_REQUEST.reasonPhrase());

                if(jsonObject.getString(AppParams.MESSAGE) == null) {
                    jsonObject.put(AppParams.MESSAGE, message);
                }

                routingContext.response().end(Json.encodePrettily(jsonObject));

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

    public static Map registerUser(String email, String password, String phone)throws SQLException{
        Map result = UserService2.registerUser(email, password, phone);
        return result;
    }

    public static boolean checkEmail(String email) throws SQLException {
        Map result = UserService2.getUserByEmail(email);
        if(result.isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    public static String getMd5(String input){
        String output = Md5Code.md5(input);
        return output;
    }

    public static boolean validateEmail(String email){
        Boolean isEmail = EmailValidator.getInstance().isValid(email);
        return isEmail;
    }
}

package com.app.tts.server.handler.User2;

import com.app.tts.services.UserService2;
import com.app.tts.session.redis.SessionStore;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import com.google.gson.Gson;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.Cookie;
import io.vertx.rxjava.ext.web.RoutingContext;
import io.vertx.rxjava.ext.web.Session;
import redis.clients.jedis.params.SetParams;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

public class LoginUserHandler2 implements Handler<RoutingContext>, SessionStore{
    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.vertx().executeBlocking(future -> {
            try {
                Map jsonRequest = routingContext.getBodyAsJson().getMap();
                Session session = routingContext.session();

                String email = ParamUtil.getString(jsonRequest, AppParams.EMAIL);
                String password = ParamUtil.getString(jsonRequest, AppParams.PASSWORD);

                String passwordMd5 = RegisterUserHandler2.getMd5(password);

                Gson gson = new Gson();
                Map user = UserService2.getUserByEmail(email);

                if(!user.isEmpty()){
                    if(ParamUtil.getString(user, AppParams.S_PASSWORD).equals(passwordMd5)){
                        if(session != null){
                            SetParams ttl = new SetParams();
                            ttl.ex(30 * 60);

                            jedis.set(session.id(), gson.toJson(user), ttl);

                            Cookie cookie = Cookie.cookie("sessionId", session.id());
                            routingContext.addCookie(cookie);
                        }else {
                            LOGGER.info("session is null");
                        }

                        Map data = new LinkedHashMap();
                        data.put(AppParams.ID, ParamUtil.getString(user, AppParams.S_ID));
                        data.put("avatar", "");
                        data.put(AppParams.MESSAGE, "login successfully");
                        data.put(AppParams.EMAIL, ParamUtil.getString(user, AppParams.S_EMAIL));

                        routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.OK.code());
                        routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.OK.reasonPhrase());
                        routingContext.put(AppParams.RESPONSE_DATA, data);
                    }
                }else {
                    routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.UNAUTHORIZED.code());
                    routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.UNAUTHORIZED.reasonPhrase());
                    routingContext.put(AppParams.RESPONSE_DATA, "{}");
                }
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

    private static final Logger LOGGER = Logger.getLogger(LoginUserHandler2.class.getName());
}

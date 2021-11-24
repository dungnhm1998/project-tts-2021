package com.app.tts.server.handler.User;

import com.app.tts.services.UserService;
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

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class LoginUserHandler implements Handler<RoutingContext>, SessionStore {

    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.vertx().executeBlocking(future -> {
            try {
                Map jsonRequest = routingContext.getBodyAsJson().getMap();
                Session session = routingContext.session();

                String email = ParamUtil.getString(jsonRequest, AppParams.EMAIL);
                String password = ParamUtil.getString(jsonRequest, AppParams.PASSWORD);
                // String password = Md5Code.md5(jsonRequest.getString("password"));
                Gson gson = new Gson();
                Map user =  UserService.getUserByEmail(email);
                String pass = ParamUtil.getString(user, AppParams.S_PASSWORD);
                Map data = new HashMap();
                if (!user.isEmpty()) {
                    if (pass.equals(password)) {
                        if (session != null) {
                            LOGGER.info("Connection to server sucessfully");
                            // Check server redis có chạy không
                            LOGGER.info("Server is running: " + jedis.ping());
                            // Set timout cho session
                            SetParams ttl = new SetParams();
                            ttl.ex(30 * 60);

                            // Lưu data của user vào session
                            jedis.set(session.id(), gson.toJson(user), ttl);
//							LOGGER.info("user-id = "+ user.get(AppParams.ID).toString());
                            // Lưu sessionId vào cookie
                            Cookie cookie = Cookie.cookie("sessionId", session.id());
                            routingContext.addCookie(cookie);
                            data.put(AppParams.ID, user.get(AppParams.S_ID).toString());
                            data.put("avatar", "....");
                            data.put("message", "login successfully");
                            data.put("email", email);
                        } else {
                            LOGGER.info("session is null");
                        }

                        // update last log in
//						userResult.setLastLogin(date);
//						clipServices.saveOrUpdate(userResult, Users.class, 0);
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

    private static final Logger LOGGER = Logger.getLogger(LoginUserHandler.class.getName());
}

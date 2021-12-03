/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.tts.server.vertical;

import com.app.tts.server.handler.User2.ForgotPasswordHandler;
import com.app.tts.server.handler.User2.LoginUserHandler2;
import com.app.tts.server.handler.User2.RegisterUserHandler2;
import com.app.tts.server.handler.campaign.AddProductHandler;
import com.app.tts.server.handler.order.GetListOrderProductHandler;
import com.app.tts.server.handler.order.GetOrderByIdHandler;
import com.app.tts.server.handler.order.InsertOrderShippingProductHandler;
import com.app.tts.server.handler.order.UpdateOrderShippingProductHandler;
import com.app.tts.server.handler.User2.ChangePasswordHandler;
import com.app.tts.server.handler.common.ExceptionHandler;
import com.app.tts.server.handler.common.RequestLoggingHandler;
import com.app.tts.server.handler.common.ResponseHandler;
import com.app.tts.server.handler.leagen.CreateCampaignHandler;
import com.app.tts.server.handler.leagen.GetCampaignHandler;
import com.app.tts.server.handler.leagen.Get_OrderHandler;
import com.app.tts.server.handler.leagen.getBaseHandler;
import com.app.tts.server.handler.option.OrderNotifyHandler;
import com.app.tts.server.handler.ucant.CreateCamHandler;
import com.app.tts.server.handler.ucant.RegisterHandler;
import com.app.tts.server.handler.user.LoginUserHandler;
import com.app.tts.server.handler.user.RecoverPasswordHandler;
import com.app.tts.server.handler.user.getBaseHandler1;
import com.app.tts.server.handler.order.UpdateOrderHandler;
import com.app.tts.util.StringPool;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.core.http.HttpClient;
import io.vertx.rxjava.core.http.HttpServer;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.handler.BodyHandler;
import io.vertx.rxjava.ext.web.handler.CookieHandler;
import io.vertx.rxjava.ext.web.handler.CorsHandler;
import io.vertx.rxjava.ext.web.handler.ResponseTimeHandler;
import io.vertx.rxjava.ext.web.handler.SessionHandler;
import io.vertx.rxjava.ext.web.handler.TimeoutHandler;
import io.vertx.rxjava.ext.web.sstore.LocalSessionStore;

import java.util.logging.Logger;

/**
 * @author hungdt
 */
public class TTSVertical extends AbstractVerticle {

    private String serverHost;
    private int serverPort;
    private boolean connectionKeepAlive;
    private long connectionTimeOut;
    private int connectionIdleTimeOut;
    private String apiPrefix;

    public static HttpClient httpClient;
    public static HttpClient httpsClient;

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public void setConnectionKeepAlive(boolean connectionKeepAlive) {
        this.connectionKeepAlive = connectionKeepAlive;
    }

    public void setConnectionTimeOut(long connectionTimeOut) {
        this.connectionTimeOut = connectionTimeOut;
    }

    public void setConnectionIdleTimeOut(int connectionIdleTimeOut) {
        this.connectionIdleTimeOut = connectionIdleTimeOut;
    }

    public void setApiPrefix(String apiPrefix) {
        this.apiPrefix = apiPrefix;
    }

    @Override
    public void start() throws Exception {

        LOGGER.info("[INIT] STARTING UP ORDER API SERVER...");

        httpClient = vertx.createHttpClient();
        httpsClient = vertx.createHttpClient(new HttpClientOptions().setSsl(true).setTrustAll(true));

        super.start();
        LOGGER.info("[INIT] STARTING UP ORDER API SERVER...");

        Router router = Router.router(vertx);
        router.route().handler(CookieHandler.create());
        router.route().handler(BodyHandler.create());
        router.route().handler(CorsHandler.create("*")
                .allowedMethod(HttpMethod.GET)
                .allowedMethod(HttpMethod.PUT)
                .allowedMethod(HttpMethod.POST)
                .allowedMethod(HttpMethod.OPTIONS)
                .allowedHeader("Access-Control-Request-Method")
                .allowedHeader("Access-Control-Allow-Credentials")
                .allowedHeader("Access-Control-Allow-Origin")
                .allowedHeader("Access-Control-Allow-Headers")
                .allowedHeader("Content-Type"));

        router.route().handler(ResponseTimeHandler.create());
        router.route().handler(TimeoutHandler.create(connectionTimeOut));
        router.route().handler(new RequestLoggingHandler());

        router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx, "test tts", 30000))
                .setCookieHttpOnlyFlag(true).setCookieSecureFlag(true));

        router.mountSubRouter(apiPrefix, initAPI());

        router.route().failureHandler(new ExceptionHandler());
        router.route().last().handler(new ResponseHandler());

        HttpServerOptions httpServerOptions = new HttpServerOptions();

        httpServerOptions.setHost(serverHost);
        httpServerOptions.setPort(serverPort);
        httpServerOptions.setTcpKeepAlive(connectionKeepAlive);
        httpServerOptions.setIdleTimeout(connectionIdleTimeOut);

        HttpServer httpServer = vertx.createHttpServer(httpServerOptions);

        httpServer.requestHandler(router);

        httpServer.listen(result -> {
            if (result.failed()) {
                LOGGER.info("[INIT] START TTS API ERROR " + result.cause());
            } else {
                LOGGER.info("[INIT] TTS SERVER STARTED AT " + StringPool.SPACE + serverHost + StringPool.COLON + serverPort);
            }
        });
    }

    private Router initAPI() {
        Router router = Router.router(vertx);


// xet uri de xem handler nao se bat login, handler nao khong bat login
        router.route(HttpMethod.POST, "/notifyOrder/:source").handler(new OrderNotifyHandler());

        //api user
        router.route(HttpMethod.POST, "/login").handler(new LoginUserHandler());
        router.route(HttpMethod.POST, "/recover").handler(new RecoverPasswordHandler());

//        router.route(HttpMethod.PUT, "/change-pass").handler(new UpdatePassHandler());

        router.route(HttpMethod.POST, "/add-product2").handler(new AddProductHandler());//ok
        router.route(HttpMethod.GET, "/get_order_product2").handler(new GetListOrderProductHandler());//ok
        router.route(HttpMethod.GET, "/get_order_by_id2").handler(new GetOrderByIdHandler());//ok
        router.route(HttpMethod.POST, "/insert_order_product_shipping2").handler(new InsertOrderShippingProductHandler());//ok
        router.route(HttpMethod.PUT, "/update-order2").handler(new UpdateOrderShippingProductHandler());//ok
        router.route(HttpMethod.PUT, "/change-pass2").handler(new ChangePasswordHandler());//ok
        router.route(HttpMethod.POST, "/recover2").handler(new ForgotPasswordHandler());//ok
        router.route(HttpMethod.POST, "/login2").handler(new LoginUserHandler2());//ok
        router.route(HttpMethod.POST, "/register2").handler(new RegisterUserHandler2());//ok
        //api

        router.route(HttpMethod.POST, "/register").handler(new RegisterHandler());
        router.route(HttpMethod.PUT, "/change-pass").handler(new ChangePasswordHandler());
        
        //api base

        router.route(HttpMethod.GET, "/base").handler(new getBaseHandler());
        router.route(HttpMethod.GET, "/base1").handler(new getBaseHandler1());


        router.route(HttpMethod.GET, "/get-order").handler(new Get_OrderHandler());
        router.route(HttpMethod.GET, "/list-campaign").handler(new GetCampaignHandler());
        router.route(HttpMethod.POST, "/create-campaign").handler(new CreateCampaignHandler());


        
        router.route(HttpMethod.POST, "/create-camp").handler(new CreateCamHandler());

        router.route(HttpMethod.POST, "/add-product").handler(new CreateCamHandler());

        router.route(HttpMethod.PUT, "/update-order").handler(new UpdateOrderHandler());



        return router;


    }
    private static final Logger LOGGER = Logger.getLogger(TTSVertical.class.getName());
}

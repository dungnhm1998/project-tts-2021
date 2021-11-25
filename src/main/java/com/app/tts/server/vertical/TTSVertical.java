/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.tts.server.vertical;

//<<<<<<< HEAD
import com.app.tts.server.handler.Order.GetListOrderProductHandler;
import com.app.tts.server.handler.Order.GetOrderByIdHandler;
//import com.app.tts.server.handler.User.DeleteUserHandler;
import com.app.tts.server.handler.User.LoginUserHandler;
//import com.app.tts.server.handler.User.GetAllUserHandler;
import com.app.tts.server.handler.User.RecoverPasswordHandler;
import com.app.tts.server.handler.User.RegisterUserHandler;
import com.app.tts.server.handler.User.UpdatePassHandler;
//=======

import com.app.tts.server.handler.Campaign.AddProductHandler;
import com.app.tts.server.handler.Campaign.CreateCampaignHandler;
import com.app.tts.server.handler.Order.InsertOrderShippingProductHandler;
import com.app.tts.server.handler.User2.ChangePasswordHandler;
import com.app.tts.server.handler.User2.ForgotPasswordHandler;
import com.app.tts.server.handler.User2.LoginUserHandler2;
import com.app.tts.server.handler.User2.RegisterUserHandler2;
//<<<<<<< HEAD
//>>>>>>> get_order
//import com.app.tts.server.handler.base.ListBaseHandler;
//import com.app.tts.server.handler.base.ListBaseHandler2;
import com.app.tts.server.handler.campaign.CreateProductHandler;
//=======
//import com.app.tts.server.handler.base.ListBaseGroupColorSizeHandler;
////>>>>>>> get_order
//import com.app.tts.server.handler.base.ListBaseHandler;
//import com.app.tts.server.handler.base.ListBaseHandler2;
//import com.app.tts.server.handler.campaign.CreateProductHandler;

import com.app.tts.server.handler.option.OptionHandler;
import com.app.tts.server.handler.option.OrderNotifyHandler;
import com.app.tts.server.handler.Ucant.ChangePassHandler;
import com.app.tts.server.handler.Ucant.CreateCamHandler;
import com.app.tts.server.handler.Ucant.LoginHandler;
import com.app.tts.server.handler.Ucant.RecoveryPassHandler;
import com.app.tts.server.handler.Ucant.RegisterHandler;

//>>>>>>> b74a7d997393c189d7608992467b9fa008b5da17
import com.app.tts.server.handler.common.ExceptionHandler;
import com.app.tts.server.handler.common.RequestLoggingHandler;
import com.app.tts.server.handler.common.ResponseHandler;
import com.app.tts.server.handler.option.OrderNotifyHandler;
import com.app.tts.util.LoggerInterface;
import com.app.tts.util.StringPool;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.core.http.HttpClient;
import io.vertx.rxjava.core.http.HttpServer;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.handler.*;
import io.vertx.rxjava.ext.web.sstore.LocalSessionStore;

/**
 *
 * @author hungdt
 */
public class TTSVertical extends AbstractVerticle implements LoggerInterface {

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

		logger.info("[INIT] STARTING UP ORDER API SERVER...");

		httpClient = vertx.createHttpClient();
		httpsClient = vertx.createHttpClient(new HttpClientOptions().setSsl(true).setTrustAll(true));

		super.start();

		Router router = Router.router(vertx);
		router.route().handler(CookieHandler.create());
		router.route().handler(BodyHandler.create());
		router.route().handler(io.vertx.rxjava.ext.web.handler.CorsHandler.create("*")
				.allowedMethod(io.vertx.core.http.HttpMethod.GET)
				.allowedMethod(io.vertx.core.http.HttpMethod.POST)
				.allowedMethod(io.vertx.core.http.HttpMethod.OPTIONS)
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
				logger.error("[INIT] START TTS API ERROR " + result.cause());
			} else {
				logger.info("[INIT] TTS SERVER STARTED AT " + StringPool.SPACE + serverHost + StringPool.COLON + serverPort);
			}
		});
	}

	private Router initAPI() {

		Router router = Router.router(vertx);
		
		// xet uri de xem handler nao se bat login, handler nao khong bat login
		router.route(HttpMethod.POST, "/notifyOrder/:source").handler(new OrderNotifyHandler());
//		router.route(HttpMethod.OPTIONS, "/login").handler(new OptionHandler());

		//api
		router.route(HttpMethod.POST, "/login").handler(new LoginUserHandler());
		router.route(HttpMethod.POST, "/recover").handler(new RecoverPasswordHandler());
		router.route(HttpMethod.POST, "/register").handler(new RegisterUserHandler());
		router.route(HttpMethod.PUT, "/change-pass").handler(new UpdatePassHandler());
		
//		router.route(HttpMethod.POST, "/create-product").handler(new CreateProductHandler());
//		router.route(HttpMethod.GET, "/list-base").handler(new ListBaseHandler());
//
//		router.route(HttpMethod.GET, "/list_base_test").handler(new ListBaseHandler2());
//		router.route(HttpMethod.GET, "/list-user").handler(new GetAllUserHandler());

//		router.route(HttpMethod.POST, "/user").handler(new RegisterUserHandler());
//		router.route(HttpMethod.DELETE, "/delete_user").handler(new DeleteUserHandler());

		router.route(HttpMethod.GET, "/get_order_by_id").handler(new GetOrderByIdHandler());
		router.route(HttpMethod.GET, "/get_order_product").handler(new GetListOrderProductHandler());

		router.route(HttpMethod.POST, "/insert_order_shipping_product").handler(new InsertOrderShippingProductHandler());

		router.route(HttpMethod.POST, "/register2").handler(new RegisterUserHandler2());
		router.route(HttpMethod.POST, "/login2").handler(new LoginUserHandler2());
		router.route(HttpMethod.POST, "/recover2").handler(new ForgotPasswordHandler());
		router.route(HttpMethod.PUT, "/change-pass2").handler(new ChangePasswordHandler());

		router.route(HttpMethod.POST, "/create-camp2").handler(new CreateCampaignHandler());
		router.route(HttpMethod.POST, "/add-product2").handler(new AddProductHandler());

		router.route(HttpMethod.POST, "/register1").handler(new RegisterHandler());
		router.route(HttpMethod.POST, "/login1").handler(new LoginHandler());
		router.route(HttpMethod.POST, "/recover1").handler(new RecoveryPassHandler());
		router.route(HttpMethod.PUT, "/change-pass1").handler(new ChangePassHandler());
		router.route(HttpMethod.POST, "/create-camp1").handler(new CreateCamHandler());
		return router;
	}
}

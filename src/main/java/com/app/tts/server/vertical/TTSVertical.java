/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.tts.server.vertical;


import com.app.tts.server.handler.User.*;
import com.app.tts.server.handler.Order.GetListOrderProductHandler;
import com.app.tts.server.handler.Order.GetOrderByIdHandler;
import com.app.tts.server.handler.User.GetAllUserHandler;
import com.app.tts.server.handler.base.ListBaseGroupColorSizeHandler;
import com.app.tts.server.handler.base.ListBaseHandler;
import com.app.tts.server.handler.base.ListBaseHandler2;
import com.app.tts.server.handler.common.ExceptionHandler;
import com.app.tts.server.handler.common.RequestLoggingHandler;
import com.app.tts.server.handler.common.ResponseHandler;
import com.app.tts.server.handler.leagen.GetCampaignHandler;
import com.app.tts.server.handler.leagen.Get_OrderHandler;
import com.app.tts.server.handler.leagen.CreateCampaignHandler;
import com.app.tts.server.handler.leagen.getBaseHandler;
import com.app.tts.server.handler.option.OptionHandler;
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
				logger.error("[INIT] START TTS API ERROR " + result.cause() + "\uD83D\uDE02");
			} else {
				logger.info("[INIT] TTS SERVER STARTED AT " + " \uD83D\uDE02" + StringPool.SPACE + serverHost + StringPool.COLON + serverPort + " \uD83D\uDE02 ");
			}
		});
	}

	private Router initAPI() {

		Router router = Router.router(vertx);
		
		// xet uri de xem handler nao se bat login, handler nao khong bat login
		router.route(HttpMethod.POST, "/notifyOrder/:source").handler(new OrderNotifyHandler());
		router.route(HttpMethod.OPTIONS, "/login").handler(new OptionHandler());

		//api
		router.route(HttpMethod.GET, "/list-base").handler(new getBaseHandler());
		router.route(HttpMethod.GET, "/list_base_test").handler(new ListBaseGroupColorSizeHandler());
		router.route(HttpMethod.GET, "/list-user").handler(new GetAllUserHandler());
//		router.route(HttpMethod.POST, "/user").handler(new RegisterUserHandler());
		router.route(HttpMethod.DELETE, "/delete_user").handler(new DeleteUserHandler());
		router.route(HttpMethod.GET, "/get-order").handler(new Get_OrderHandler());
		router.route(HttpMethod.GET, "/list-campaign").handler(new GetCampaignHandler());
		router.route(HttpMethod.POST, "/create-campaign").handler(new CreateCampaignHandler());
		router.route(HttpMethod.GET, "/list-base").handler(new ListBaseHandler());

		router.route(HttpMethod.GET, "/list_base_test").handler(new ListBaseHandler2());
		router.route(HttpMethod.GET, "/list-user").handler(new GetAllUserHandler());

//		router.route(HttpMethod.POST, "/user").handler(new RegisterUserHandler());
		router.route(HttpMethod.DELETE, "/delete_user").handler(new DeleteUserHandler());

		router.route(HttpMethod.GET, "/get_order_by_id").handler(new GetOrderByIdHandler());
		router.route(HttpMethod.GET, "/get_order_product").handler(new GetListOrderProductHandler());

		return router;
	}
}

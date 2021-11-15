package com.app.tts.server.handler.base;

import com.app.tts.session.redis.SessionStore;

import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

public class LoginHandler implements Handler<RoutingContext>, SessionStore{
	@Override
	public void handle(RoutingContext routingContext) {

		routingContext.vertx().executeBlocking(future -> {
			try {
				
			}
		});
	}
}

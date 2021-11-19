package com.app.tts.server.handler.User;

import java.util.Map;

import com.app.tts.session.redis.SessionStore;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

public class ListBaseHandler implements Handler<RoutingContext>, SessionStore {
	@Override
	public void handle(RoutingContext routingContext) {

		routingContext.vertx().executeBlocking(future ->{
			 try{
				 Map jsonRequest = routingContext.getBodyAsJson().getMap();
				 
				 String base_group_id = ParamUtil.getString(jsonRequest, AppParams.BASE_GROUP_ID);
				 String base_name = ParamUtil.getString(jsonRequest, AppParams.BASE_NAME);
				 
				 List<Map> sizes = ParamUtil.getListData(jsonRequest, AppParams.SIZES);

	                future.complete();
	            }catch (Exception e){
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
}

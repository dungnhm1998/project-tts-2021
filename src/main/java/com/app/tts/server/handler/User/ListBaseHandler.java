package com.app.tts.server.handler.User;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.app.tts.data.type.RedisKeyEnum;
import com.app.tts.services.ListService;
import com.app.tts.services.OrderService;
import com.app.tts.services.RedisService;
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
				 Map listBaseRedis = RedisService.getMap(RedisKeyEnum.BASES_MAP);
	                if (listBaseRedis == null || listBaseRedis.isEmpty()) {
	                    listBaseRedis = getListBaseFromDB();
	                    routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.OK.code());
	                    routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.OK.reasonPhrase());
	                    routingContext.put(AppParams.RESPONSE_DATA, listBaseRedis);
	                    future.complete();
	                }
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
	public static Map getListBaseFromDB() throws SQLException {
        Map listBaseDB = new HashMap();	
        List<Map> listBaseAndGroup = ListService.getBaseSize();
        List<Map> listBaseColor = ListService.getBaseColor();
        List<Map> listBaseSize = ListService.getBaseGroup();
	}
}

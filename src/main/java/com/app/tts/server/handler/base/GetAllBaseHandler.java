package com.app.tts.server.handler.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import com.app.tts.services.BaseService;
import com.app.tts.services.ColorService;
import com.app.tts.services.SizeService;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

public class GetAllBaseHandler implements Handler<RoutingContext> {

	@Override
	public void handle(RoutingContext rc) {
		rc.vertx().executeBlocking(future -> {
			try {
				Map listBaseDB = new HashMap();


		        List<Map> listBaseAndGroup = BaseService.getListBases();
		        List<Map>  listBaseColor = ColorService.getListColor();
		        List<Map> listBaseSize = SizeService.getListSize();
		        
		        Set<String> listBaseGroupId = new HashSet();
		        for (Map baseAndGroup : listBaseAndGroup) {
		            //get base id
		            String baseGroupId = ParamUtil.getString(baseAndGroup, AppParams.GROUP_ID);
		            listBaseGroupId.add(baseGroupId);
		        }

		        //list base group
		        for (String groupId : listBaseGroupId) {
		            List<Map> listBaseGroup1 = new ArrayList();
		            String baseGroupName1 = "";

		            for (Map baseAndGroup : listBaseAndGroup) {
		                String based = ParamUtil.getString(baseAndGroup, "id");
		                //ghep theo color
		                List<Map> listColorBase = new ArrayList<>();

		                for (Map colors : listBaseColor) {
		                    String baseColorId = ParamUtil.getString(colors, "base");
		                    if (based.equals(baseColorId)) {
		                        listColorBase.add(colors);
		                    }
		                }

		                baseAndGroup.put("colors", listColorBase);

		                //ghep theo size
		                List<Map> listSizeBase = new ArrayList<>();

		                for (Map sizes : listBaseSize) {
		                    String baseSizeId = ParamUtil.getString(sizes, "base");
		                    if (based.equals(baseSizeId)) {
		                        listSizeBase.add(sizes);
		                    }
		                }
		                baseAndGroup.put("sizes", listSizeBase);

		                String baseGroupId = ParamUtil.getString(baseAndGroup, AppParams.GROUP_ID);
		                if (groupId.equals(baseGroupId)) {
		                    listBaseGroup1.add(baseAndGroup);
		                    baseGroupName1 = ParamUtil.getString(baseAndGroup, AppParams.GROUP_NAME);
		                }
		            }
		            listBaseDB.put(baseGroupName1, listBaseGroup1);
		        }
				
				rc.put(AppParams.RESPONSE_CODE, HttpResponseStatus.OK.code());
				rc.put(AppParams.RESPONSE_MSG, HttpResponseStatus.OK.reasonPhrase());
				rc.put(AppParams.RESPONSE_DATA, listBaseDB);
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

	private static final Logger LOGGER = Logger.getLogger(GetAllBaseHandler.class.getName());
}
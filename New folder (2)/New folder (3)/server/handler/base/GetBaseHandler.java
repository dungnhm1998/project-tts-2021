package com.app.tts.server.handler.base;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import com.app.tts.data.type.RedisKeyEnum;
import com.app.tts.services.BaseService;
import com.app.tts.services.ColorService;
import com.app.tts.services.RedisService;
import com.app.tts.services.SizeService;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

public class GetBaseHandler implements Handler<RoutingContext> {

	@Override
	public void handle(RoutingContext rc) {
		rc.vertx().executeBlocking(future -> {
			try {
				
				Map listBaseRedis = RedisService.getMap(RedisKeyEnum.BASES_MAP);
                if (listBaseRedis == null || listBaseRedis.isEmpty()) {
                    listBaseRedis = getList();
                }
				
                System.out.println(listBaseRedis);
				
				rc.put(AppParams.RESPONSE_CODE, HttpResponseStatus.OK.code());
				rc.put(AppParams.RESPONSE_MSG, HttpResponseStatus.OK.reasonPhrase());
				rc.put(AppParams.RESPONSE_DATA, listBaseRedis);
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
	
	public static Map getList() throws SQLException{
		Map listBaseDB = new HashMap();
        Map data = new HashMap();

		int total = 0;
        List<Map> listBaseAndGroup = BaseService.getBase();
        List<Map>  listBaseColor = ColorService.getListColor();
        List<Map> listBaseSize = SizeService.getListSize();
        
        Set<String> listBaseGroupId = new HashSet();
        for (Map baseAndGroup : listBaseAndGroup) {
            //get base id
            String baseGroupId = ParamUtil.getString(baseAndGroup, AppParams.GROUP_ID);
      
            listBaseGroupId.add(baseGroupId);
        }
        System.out.println(listBaseGroupId);

        //list base group
        for (String groupId : listBaseGroupId) {
            List<Map> listBaseGroup1 = new ArrayList();
            String baseGroupName1 = "";
            total++;
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
            data.put("total", total);
            data.put("data", listBaseDB);
        }
		return data;
        
	}

	private static final Logger LOGGER = Logger.getLogger(GetBaseHandler.class.getName());
}
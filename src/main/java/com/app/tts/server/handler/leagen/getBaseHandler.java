package com.app.tts.server.handler.leagen;

import com.app.tts.data.type.RedisKeyEnum;
import com.app.tts.services.GetBaseService;
import com.app.tts.services.RedisService;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class getBaseHandler implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.vertx().executeBlocking(future -> {
            try {
                Map listBaseRedis = RedisService.getMap(RedisKeyEnum.BASES_MAP);
                if (listBaseRedis == null || listBaseRedis.isEmpty()) {
                    listBaseRedis = getListBaseFromDB();
                }
                routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.OK.code());
                routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.OK.reasonPhrase());
                routingContext.put(AppParams.RESPONSE_DATA, listBaseRedis);
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

    public static Map getListBaseFromDB() throws SQLException {
        Map listBaseDB = new HashMap();


        List<Map> listBaseAndGroup = GetBaseService.getBaseService();
        List<Map>  listBaseColor = GetBaseService.getBaseColor();
        List<Map> listBaseSize = GetBaseService.getBaseSize();





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
        return listBaseDB;
    }

    private static final Logger LOGGER = Logger.getLogger(getBaseHandler.class.getName());
}

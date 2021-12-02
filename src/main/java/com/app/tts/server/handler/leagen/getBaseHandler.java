package com.app.tts.server.handler.leagen;

import com.app.tts.services.GetBaseService;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class getBaseHandler implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.vertx().executeBlocking(future -> {
            try {
                Map listBaseDB = new HashMap();
                int count =-1;
                String baseid = "";
                String color = "";
                String size = "";
                List<Map> listBaseAndGroup = GetBaseService.getBaseService();
                for (Map lisId : listBaseAndGroup) {
                    baseid = ParamUtil.getString(lisId, "id");
                    color = ParamUtil.getString(lisId, "colors");
                    size = ParamUtil.getString(lisId, "sizes");
                }
//                Map listBaseId = listBaseAndGroup.get(0);


                List<Map> listBaseColor = GetBaseService.getBaseColor(baseid);
                List<Map> listBaseSize = GetBaseService.getBaseSize(baseid);
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

                        // list id color
                        List<String> listIdColor = Arrays.asList(color.split(","));

                        //ghep theo color
                        List<Map> listColorBase = new ArrayList<>();
                        if (!color.isEmpty()) {
                            for (String idColor : listIdColor) {
                                count ++;
                                Map colorMap = new LinkedHashMap();
                                for (Map colors : listBaseColor) {
                                    String idColorInList = ParamUtil.getString(colors, "id");
                                    if (idColorInList.equals(idColor)) {
                                        listColorBase.add(colors);
                                        break;
                                    }
                                }
                            }
                        }
                        baseAndGroup.put("colors", listColorBase);
                        List<String> listIdSize = Arrays.asList(size.split(","));

                        //ghep theo color
                        List<Map> listSizeBase = new ArrayList<>();
                        if (!color.isEmpty()) {
                            for (String idSize : listIdSize) {
                                Map colorMap = new LinkedHashMap();
                                for (Map sizes : listBaseSize) {
                                    String idSizeInList = ParamUtil.getString(sizes, "id");
                                    if (idSizeInList.equals(idSize)) {
                                        listSizeBase.add(sizes);
                                        break;
                                    }
                                }
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

//		RedisService.persistMap(RedisKeyEnum.BASES_MAP, listBaseDB);


                routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.OK.code());
                routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.OK.reasonPhrase());
                routingContext.put(AppParams.RESPONSE_DATA, listBaseDB);
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

//    public static Map getListBaseFromDB() throws SQLException {
//
//    }

    private static final Logger LOGGER = Logger.getLogger(getBaseHandler.class.getName());
}
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
                String baseId = "";
                List<Map> listBaseColor = new ArrayList<>();
                List<Map> listBaseSize = new ArrayList<>();
                List<Map> listBaseAndGroup = GetBaseService.getBaseService();

                for (Map map : listBaseAndGroup) {
                    baseId = ParamUtil.getString(map, "group_id");

                    listBaseColor = GetBaseService.getBaseColor(baseId);
                    listBaseSize = GetBaseService.getBaseSize(baseId);
                    break;
                }


                Map lisId = listBaseAndGroup.get(0);
                String color = ParamUtil.getString(lisId, "colors");
                String size = ParamUtil.getString(lisId, "sizes");


                Set<String> list = new HashSet();
                for (Map baseAndColor : listBaseColor) {
                    //get base id
                    String baseColorId = ParamUtil.getString(baseAndColor, "id");
                    list.add(baseColorId);
                }

                Set<String> list1 = new HashSet();
                for (Map baseAndSize : listBaseSize) {
                    //get base id
                    String baseSizeId = ParamUtil.getString(baseAndSize, "id");
                    list1.add(baseSizeId);
                }


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
//                        List<String> listIdColor = Arrays.asList(color.split(","));
//
//                        //ghep theo color
//                        List<Map> listColorBase = new ArrayList<>();
//                        if (!color.isEmpty()) {
//                            for (String idSize : listIdColor) {
//                                for (Map sizes : listBaseColor) {
//                                    String idSizeInList = ParamUtil.getString(sizes, "id");
//                                    if (idSizeInList.equals(idSize)) {
//                                        listColorBase.add(sizes);
//                                        break;
//                                    }
//                                }
//                            }
//                        }
                        List<Map> listBaseColor1 = new ArrayList();
                        for (String ColorId : list) {
                            for (Map baseAndColor : listBaseColor) {
                                String baseColorId = ParamUtil.getString(baseAndColor, "id");
                                if (ColorId.equals(baseColorId)) {
                                    listBaseColor1.add(baseAndColor);
                                }
                            }
                        }

                        baseAndGroup.put("colors", listBaseColor1);


                        List<String> listIdSize = Arrays.asList(size.split(","));


//                        //ghep theo size
//                        List<Map> listSizeBase = new ArrayList<>();
//                        if (!color.isEmpty()) {
//                            for (String idSize : listIdSize) {
//                                for (Map sizes : listBaseSize) {
//                                    String idSizeInList = ParamUtil.getString(sizes, "id");
//                                    if (idSizeInList.equals(idSize)) {
//                                        listSizeBase.add(sizes);
//                                        break;
//                                    }
//                                }
//                            }
//                        }
                        List<Map> listSizeBase = new ArrayList();
                        for (String SizeId : list) {
                            for (Map baseAndSize : listBaseSize) {
                                String baseSizeId = ParamUtil.getString(baseAndSize, "id");
                                if (SizeId.equals(baseSizeId)) {
                                    listSizeBase.add(baseAndSize);
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

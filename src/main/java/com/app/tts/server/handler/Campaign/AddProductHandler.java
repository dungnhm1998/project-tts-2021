package com.app.tts.server.handler.campaign;

import com.app.tts.services.CampaignService;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.sql.SQLException;
import java.util.*;

public class AddProductHandler implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.vertx().executeBlocking(future -> {
            try {
                Map jsonRequest = routingContext.getBodyAsJson().getMap();
                String campaignId = ParamUtil.getString(jsonRequest, AppParams.CAMPAIGN_ID);//
                List<Map> listProduct = ParamUtil.getListData(jsonRequest, "products");

                Map data = new LinkedHashMap();

                for (Map productMap : listProduct) {
                    int inDefautl = ParamUtil.getBoolean(productMap, AppParams.DEFAULT) ? 1 : 0;//
                    String baseId = ParamUtil.getString(productMap, AppParams.BASE_ID);//

                    List<Map> colorList = ParamUtil.getListData(productMap, AppParams.COLORS);
                    String colorId = null;//
                    String defaultColor = null;//       chua biet de gia tri nhu the nao
                    for (Map colorMap : colorList) {
                        String colorIdSub = ParamUtil.getString(colorMap, AppParams.ID);
                        if (colorId != null) {
                            colorId = colorId + ",";
                        }
                        if (colorId != null) {
                            colorId = colorId + colorIdSub;
                        } else {
                            colorId = colorIdSub;
                        }
                    }

                    List<Map> priceList = ParamUtil.getListData(productMap, AppParams.PRICES);
                    String sizeId = null; //
                    String price = null; //

                    for (Map priceMap : priceList) {
                        String sizeIdSub = ParamUtil.getString(priceMap, AppParams.SIZE_ID);
                        String priceSub = ParamUtil.getString(priceMap, AppParams.PRICE);
                        if (sizeId != null) {
                            sizeId = sizeId + ",";
                        }
                        if (price != null) {
                            price = price + ",";
                        }

                        if (sizeId != null) {
                            sizeId = sizeId + sizeIdSub;
                        } else {
                            sizeId = sizeIdSub;
                        }

                        if (price != null) {
                            price = price + priceSub;
                        } else {
                            price = priceSub;
                        }
                    }

                    // khong ro mockups chua gi, tam de = null
                    String mockups = null;//

                    data = addProduct(campaignId,
                            inDefautl, baseId,
                            colorId, defaultColor,
                            sizeId, price,
                            mockups);
                }

                routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.CREATED.code());
                routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.CREATED.reasonPhrase());
                routingContext.put(AppParams.RESPONSE_DATA, data);

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

    public static Map addProduct(String campaignId,
                                 int defaultN, String baseId,
                                 String colorId, String defaultColor,
                                 String sizeId, String price,
                                 String mockups) throws SQLException {
        Map result = CampaignService.addProduct(campaignId,
                defaultN, baseId,
                colorId, defaultColor,
                sizeId, price,
                mockups);
        return result;
    }
}

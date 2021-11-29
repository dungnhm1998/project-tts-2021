package com.app.tts.server.handler.campaign;

import com.app.tts.services.CreateProServices;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddProHandler implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.vertx().executeBlocking(future -> {
            try {
                JsonObject jsonRequest = routingContext.getBodyAsJson();
                Map mapRequest = jsonRequest.getMap();
                Map result = inputData(mapRequest);
                routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.OK.code());
                routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.CREATED.reasonPhrase());
                routingContext.put(AppParams.RESPONSE_DATA, result);
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

    public static Map inputData(Map mapRequest) throws SQLException {
        String campaign_id = ParamUtil.getString(mapRequest, AppParams.CAMPAIGN_ID);
        String baseId = "", colorId = "", sizeId = "", designs = "", mockup = "", priceid = "";
        List<Map> items = ParamUtil.getListData(mapRequest, "products");

        for (Map products : items) {

            baseId = ParamUtil.getString(products, AppParams.BASE_ID);

            List<Map> colors = ParamUtil.getListData(products, AppParams.COLORS);
            for (Map color : colors) {
                String ColorId = ParamUtil.getString(color, AppParams.ID);

                if (!colorId.isEmpty()) {
                    colorId = colorId + ",";
                }
                if (!colorId.isEmpty()) {
                    colorId = colorId + ColorId;
                } else {
                    colorId = ColorId;
                }
            }

            List<Map> prices = ParamUtil.getListData(products, AppParams.PRICES);
            for (Map price : prices) {
                String SizeId = ParamUtil.getString(price, AppParams.SIZE);
                String PriceId = ParamUtil.getString(price, AppParams.PRICE);
                if (!sizeId.isEmpty()) {
                    sizeId = sizeId + ",";
                }
                if (!priceid.isEmpty()) {
                    priceid = priceid + ",";
                }

                if (!sizeId.isEmpty()) {
                    sizeId = sizeId + SizeId;
                } else {
                    sizeId = SizeId;
                }

                if (!sizeId.isEmpty()) {
                    priceid = priceid + PriceId;
                } else {
                    priceid = PriceId;
                }

            }

            designs = ParamUtil.getString(products, AppParams.DESIGN);

            List<Map> mockups = ParamUtil.getListData(products, AppParams.MOCKUPS);
            for (Map mockupss : mockups) {
                mockup = ParamUtil.getString(mockupss, AppParams.MOCKUPS);
            }
            List<Map> getcampaign = new ArrayList<>();
            getcampaign = addProduct(campaign_id, baseId, colorId, sizeId, priceid, designs, mockup);
            List<Map> getProduct = CreateProServices.getPoduct(campaign_id);
            List<Map> getcolor = CreateProServices.get_color(campaign_id);
            List<Map> getsize = CreateProServices.get_size(campaign_id);

            Map result = CreateProServices.format(getcampaign, getProduct, getcolor, getsize);
            return result;
        }



        return null;
    }

    private static List<Map> addProduct(String campaign_id, String baseId, String colorId, String sizeId, String priceid, String designs, String mockup) throws SQLException {
        List<Map> resultMap = CreateProServices.createProduct(campaign_id, baseId, colorId, sizeId, priceid, designs, mockup);

        return resultMap;

    }


}

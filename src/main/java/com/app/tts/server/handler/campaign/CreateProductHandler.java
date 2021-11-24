package com.app.tts.server.handler.campaign;

import com.app.tts.services.CreateProductServices;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class CreateProductHandler implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                Map jsonrequest = rc.getBodyAsJson().getMap();
                String campaign_id = ParamUtil.getString(jsonrequest, AppParams.CAMPAIGN_ID);
                String base_id = "", color_id = "", size_id = "", designs = "", mockup = "";
                List<Map> items = ParamUtil.getListData(jsonrequest, AppParams.PRODUCTS);

                for (Map products : items) {

                    base_id = ParamUtil.getString(products, AppParams.BASE_ID);

                    List<Map> colors = ParamUtil.getListData(products, AppParams.COLORS);
                    for (Map color : colors) {
                        color_id = ParamUtil.getString(color, AppParams.ID);
                    }

                    List<Map> prices = ParamUtil.getListData(products, AppParams.PRICES);
                    for (Map price : prices) {
                        size_id = ParamUtil.getString(price, AppParams.SIZE);
                    }

                    designs = ParamUtil.getString(products, AppParams.DESIGN);

                    List<Map> mockups = ParamUtil.getListData(products, AppParams.MOCKUPS);
                    for (Map mockupss : mockups) {
                        mockup = ParamUtil.getString(mockupss, AppParams.MOCKUPS);
                    }

                }

                Map data = new HashMap();

                List<Map> jsonProduct = CreateProductServices.createProduct(campaign_id, base_id, color_id, size_id, designs, mockup);

                for (Map map : jsonProduct) {
                    map.put("campaign_id", ParamUtil.getString(map, "s_campaign_id"));
                    map.put("user_id", ParamUtil.getString(map, "s_user_id"));
                    for (Map products : jsonProduct){
                        products.put("product_id", ParamUtil.getString(map, "s_id"));
                        for (Map colors : jsonProduct){
                            colors.put("color_id", ParamUtil.getString(map, "s_colors"));
                        }
                        for (Map sizes : jsonProduct){
                            sizes.put("color_id", ParamUtil.getString(map, "s_sizes"));
                        }
                    }
                }

                data.put("json",jsonProduct);


                rc.put(AppParams.RESPONSE_CODE, HttpResponseStatus.CREATED.code());
                rc.put(AppParams.RESPONSE_MSG, HttpResponseStatus.CREATED.reasonPhrase());
                rc.put(AppParams.RESPONSE_DATA, data);
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

    private static final Logger LOGGER = Logger.getLogger(CreateProductHandler.class.getName());
}

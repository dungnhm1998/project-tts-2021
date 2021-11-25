package com.app.tts.server.handler.campaign;

import com.app.tts.services.CreateProductServices;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.util.*;
import java.util.logging.Logger;

public class CreateProductHandler implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                Map jsonrequest = rc.getBodyAsJson().getMap();
                String campaign_id = ParamUtil.getString(jsonrequest, AppParams.CAMPAIGN_ID);
                String baseId = "", colorId = "", sizeId = "", designs = "", mockup = "";
                List<Map> items = ParamUtil.getListData(jsonrequest, AppParams.PRODUCTS);

                for (Map products : items) {

                    baseId = ParamUtil.getString(products, AppParams.BASE_ID);

                    List<Map> colors = ParamUtil.getListData(products, AppParams.COLORS);
                    for (Map color : colors) {
                        colorId = ParamUtil.getString(color, AppParams.ID);
                    }

                    List<Map> prices = ParamUtil.getListData(products, AppParams.PRICES);
                    for (Map price : prices) {
                        sizeId = ParamUtil.getString(price, AppParams.SIZE);
                    }

                    designs = ParamUtil.getString(products, AppParams.DESIGN);

                    List<Map> mockups = ParamUtil.getListData(products, AppParams.MOCKUPS);
                    for (Map mockupss : mockups) {
                        mockup = ParamUtil.getString(mockupss, AppParams.MOCKUPS);
                    }

                }

                Map data = new HashMap();
                Map result = new LinkedHashMap();
                List<Map> jsonProduct = CreateProductServices.createProduct(campaign_id, baseId, colorId, sizeId, designs, mockup);

                List<Map> getProduct = CreateProductServices.getPoduct(campaign_id);

                Map ListId = getProduct.get(0);
//                List<Map> listid = ParamUtil.getListData(ListId, "product_id");
                String productId = ParamUtil.getString(ListId, "id");





                List<Map> getcolor = CreateProductServices.get_color(productId);
                List<Map> getsize = CreateProductServices.get_size(productId);



//                List<Map> sizes = CreateProductServices.get_size(base_id);
                List<Map> camAndPro = new ArrayList();
                String title = "";
                for (Map map : jsonProduct) {
                    String campaignId = ParamUtil.getString(map, AppParams.CAMPAIGN_ID);
                    camAndPro.add(map);
                    title = ParamUtil.getString(map, AppParams.TITLE);
                    data.put(title, jsonProduct);

                    List<Map> colorandsizes = new ArrayList<>();
                    for (Map colorAndSize : getProduct) {

                        Map resultProduct = new LinkedHashMap();
                        resultProduct.put(AppParams.ID, ParamUtil.getString(colorAndSize, AppParams.PRODUCTS));

                        List<Map> listColor = new ArrayList<>();
                        for (Map colors : getcolor) {
                            String ColorId = ParamUtil.getString(colors, "product_id");
//                            resultColor.put(AppParams.ID, ParamUtil.getString(colors, AppParams.COLORS));
                            if (productId.equals(ColorId)) {
                                listColor.add(colors);
                            }

                        }
                        colorAndSize.put(AppParams.COLORS, listColor);

                        List<Map> listSizes = new ArrayList<>();
                        for (Map price : getsize) {
                            String SizeId = ParamUtil.getString(price, "product_id");
                            if (productId.equals(SizeId)) {
                                listSizes.add(price);
                            }
                        }

                        colorAndSize.put(AppParams.SIZES, listSizes);


                        colorandsizes.add(colorAndSize);
                    }
                    map.put(AppParams.PRODUCTS, colorandsizes);

                }

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

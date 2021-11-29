package com.app.tts.server.handler.user;

import com.app.tts.services.CreateProServices;
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
                Map json = rc.getBodyAsJson().getMap();

                String campaignId = ParamUtil.getString(json, AppParams.CAMPAIGN_ID);
                String userId = ParamUtil.getString(json, AppParams.USER_ID);
                String baseId = "", colorId = "", name = "", size_name = "", price = "", designs = "", mockups = "",
                        sizeId = "";
                // get body
                List<Map> getproduct = ParamUtil.getListData(json, "products");
                Map data = new LinkedHashMap();
                for (Map products : getproduct) {
                    baseId = ParamUtil.getString(products, AppParams.BASE_ID);
                    designs = ParamUtil.getString(products, AppParams.DESIGNS);

                    StringJoiner colorSub = new StringJoiner(",");
                    StringJoiner sizeSub = new StringJoiner(",");
                    StringJoiner priceSub = new StringJoiner(",");


                    List<Map> getcolors = ParamUtil.getListData(products, "colors");
                    for (Map colors : getcolors) {
                        String colorid = ParamUtil.getString(colors, AppParams.ID);
                        // chèn dấu ' khi lấy nhiều id
                        if (!colorid.isEmpty()) {
                            colorSub.add(colorid);
                        }
                        colorId = colorSub.toString();
                    }

                    List<Map> getprices = ParamUtil.getListData(products, "prices");
                    for (Map prices : getprices) {

                        String sizeid = ParamUtil.getString(prices, AppParams.SIZE_ID);
                        // chèn dấu ' khi lấy nhiều id
                        if (!sizeid.isEmpty()) {
                            sizeSub.add(sizeid);
                        }
                        sizeId = sizeSub.toString();
                        String saleprice = ParamUtil.getString(prices, AppParams.PRICE);
                        if (!saleprice.isEmpty()) {
                            priceSub.add(saleprice);
                        }
                        price = priceSub.toString();
                    }


                    List<Map> getmockup = ParamUtil.getListData(products, "mockups");
                    for (Map mockup : getmockup) {
                        mockups = ParamUtil.getString(mockup, AppParams.MOCKUP_IMG_URL);
                    }

                    //get Campaign
                    List<Map> getCampaign = CreateProServices.createProduct(campaignId, baseId, colorId, sizeId, price, designs, mockups);
                    // get product theo id campaign
                    List<Map> getProduct = CreateProServices.getPoduct(campaignId);
                    //getColor theo id campaign
                    List<Map> getColor = CreateProServices.get_color(campaignId);
                    //getSize theo id campaign
                    List<Map> getSize = CreateProServices.get_size(campaignId);


                    // fomart
                    data = CreateProServices.format(getCampaign, getProduct, getColor, getSize);
                    LOGGER.info( "result: "+ data);
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

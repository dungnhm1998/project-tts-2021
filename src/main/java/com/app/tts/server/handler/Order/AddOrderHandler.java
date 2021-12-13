package com.app.tts.server.handler.Order;

import com.app.tts.services.AddOrderServices;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AddOrderHandler implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.vertx().executeBlocking(future -> {
            try {
                Map jsonObject = routingContext.getBodyAsJson().getMap();
                List<Map> insertOrderProduct = new ArrayList<>();
                //random
                Random rand = new Random();
                String ord = String.valueOf(rand.nextInt(100000));
                String orderId = "-" + "CT" + "-" + ord;
                String source = ParamUtil.getString(jsonObject, "source");
                String currency = ParamUtil.getString(jsonObject, "currency");
                String note = ParamUtil.getString(jsonObject, "note");
                String storeId = ParamUtil.getString(jsonObject, "store_id");
                String reference_id = ParamUtil.getString(jsonObject, "reference_id");
                String state = ParamUtil.getString(jsonObject, "state");
                String shippingMethod = ParamUtil.getString(jsonObject, "shipping_method");

                Map shipping = ParamUtil.getMapData(jsonObject, "shipping");
                //random
                String sipId = String.valueOf(rand.nextInt(100000));
                String shippingId = "-" + "CT" + "-" + sipId;
                System.out.println(shippingId);
                String email = ParamUtil.getString(shipping, "email");
                String name = ParamUtil.getString(shipping, "name");
                String phone = ParamUtil.getString(shipping, "phone");
                String phone_ext = ParamUtil.getString(shipping, "phone_ext");

                Map address = ParamUtil.getMapData(shipping, "address");

                String line1 = ParamUtil.getString(address, "line1");
                String line2 = ParamUtil.getString(address, "line2");
                String city = ParamUtil.getString(address, "city");
                String states = ParamUtil.getString(address, "state");
                String postal_code = ParamUtil.getString(address, "postal_code");
                String country = ParamUtil.getString(address, "country");
                String country_name = ParamUtil.getString(address, "country_name");
                int addrVerified = ParamUtil.getBoolean(address, "addr_verified") ? 1 : 0;
                String addrVerifiedNote = ParamUtil.getString(address, "addr_verified_note");
                String extra_fee = ParamUtil.getString(jsonObject, "extra_fee");
                List<Map> items = ParamUtil.getListData(jsonObject, "items");

                String taxAmount = ParamUtil.getString(jsonObject, "tax_amount");
                String iossNumber = ParamUtil.getString(jsonObject, "ioss_number");

                Map  insertOrder = AddOrderServices.insertOrder(orderId, currency, state, note, source, storeId, reference_id, addrVerified,
                        addrVerifiedNote, shippingMethod, taxAmount, iossNumber);

                for (Map item : items) {
                    String orProduct = ParamUtil.getString(item, "id");
                    int isEdit = ParamUtil.getBoolean(item, "isEdit") ? 1 : 0;
                    String baseId = ParamUtil.getString(item, "base_id");
                    String baseName = ParamUtil.getString(item, "base_name");
                    String value = ParamUtil.getString(item, "color");
                    String colorId = ParamUtil.getString(item, "color_id");
                    String colorName = ParamUtil.getString(item, "color_name");
                    String sizeId = ParamUtil.getString(item, "size_id");
                    String sizeName = ParamUtil.getString(item, "size_name");
                    int quantity = ParamUtil.getInt(item, "quantity");
                    String price = ParamUtil.getString(item, "price");

                    Map design = ParamUtil.getMapData(item, "designs");
                    String designFrontUrl = ParamUtil.getString(design, "design_front_url");
                    String designFrontUrlMd5 = ParamUtil.getString(design, "design_front_url_md5");
                    String designBackUrl = ParamUtil.getString(design, "design_back_url");
                    String designBackUrlMd5 = ParamUtil.getString(design, "design_back_url_md5");
                    String mockupFrontUrl = ParamUtil.getString(design, "mockup_front_url");
                    String mockupBackUrl = ParamUtil.getString(design, "mockup_back_url");

                    String variantName = ParamUtil.getString(item, "variant_name");
                    String unitAmount = ParamUtil.getString(item, "unit_amount");
                    int shippingExpress = ParamUtil.getBoolean(item, "shippingExpress") ? 1 : 0;

                    insertOrderProduct = AddOrderServices.insertOrderProduct(orProduct, orderId, sizeId, price, quantity,
                            variantName, baseId, mockupFrontUrl, mockupBackUrl, colorId,
                            value, colorName, sizeName, unitAmount, designBackUrl,
                            designFrontUrl);
                }


                Map insertShipping = AddOrderServices.insertShipping(shippingId, email, name, phone, line1, line2, city, states,
                        postal_code, country, country_name);

//                Map data = new HashMap();
                Map data = AddOrderServices.formatInsertOrder(insertOrder, insertShipping, insertOrderProduct);

                routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.CREATED.code());
                routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.CREATED.reasonPhrase());
                routingContext.put(AppParams.RESPONSE_DATA, data);
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
}






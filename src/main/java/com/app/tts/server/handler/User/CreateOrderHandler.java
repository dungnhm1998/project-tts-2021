package com.app.tts.server.handler.User;

import com.app.tts.services.OrderService;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.util.*;
import java.util.logging.Logger;

public class CreateOrderHandler implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                Map jsonRequest = rc.getBodyAsJson().getMap();

                String source = ParamUtil.getString(jsonRequest, AppParams.SOURCE);
                String currency = ParamUtil.getString(jsonRequest, AppParams.CURRENCY);
                String note = ParamUtil.getString(jsonRequest, AppParams.NOTE);
                String store_id = ParamUtil.getString(jsonRequest, AppParams.STORE_ID);
                String reference_id = ParamUtil.getString(jsonRequest, AppParams.REFERENCE_ID);
                String shipping_method = ParamUtil.getString(jsonRequest, AppParams.SHIPPING_METHOD);
                String tax_amount = ParamUtil.getString(jsonRequest, AppParams.TAX_AMOUNT);
                String ioss_number = ParamUtil.getString(jsonRequest, AppParams.IOSS_NUMBER);

                Map shipping = ParamUtil.getMapData(jsonRequest, AppParams.SHIPPING);
                String email = ParamUtil.getString(shipping, AppParams.EMAIL);
                String name = ParamUtil.getString(shipping, AppParams.NAME);
                String phone = ParamUtil.getString(shipping, AppParams.PHONE);
                String phone_ext = ParamUtil.getString(shipping, AppParams.PHONE_EXT);

                Map address = ParamUtil.getMapData(shipping, AppParams.ADDRESS);
                String line1 = ParamUtil.getString(address, AppParams.LINE1);
                String line2 = ParamUtil.getString(address, AppParams.LINE2);
                String city = ParamUtil.getString(address, AppParams.CITY);
                String state = ParamUtil.getString(address, AppParams.STATE);
                String postal_code = ParamUtil.getString(address, AppParams.POSTAL_CODE);
                String country = ParamUtil.getString(address, AppParams.COUNTRY);
                String country_name = ParamUtil.getString(address, AppParams.COUNTRY_NAME);
                int addr_verified = ParamUtil.getBoolean(address, AppParams.ADDR_VERIFIED) ? 1 : 0;
                String addr_verified_note = ParamUtil.getString(address, AppParams.ADDR_VERIFIED_NOTE);

                String extra_fee = ParamUtil.getString(jsonRequest, AppParams.EXTRA_FEE);

                List<Map> items = ParamUtil.getListData(jsonRequest, AppParams.ITEMS);

                String id = "", order_id = "", base_id = "", base_name = "", color = "", color_id = "", color_name = "",
                        size_id = "", size_name = "" , price = "", design_front_url = "", design_front_url_md5 = "",
                        design_back_url = "", design_back_url_md5 = "", variant_name = "", unit_amount = "";
                int quantity = 1;

                for (Map map : items) {
                    id = ParamUtil.getString(map, AppParams.ID);
                    base_id = ParamUtil.getString(map, AppParams.BASE_ID);
                    base_name = ParamUtil.getString(map, AppParams.BASE_NAME);
                    color = ParamUtil.getString(map, AppParams.COLOR);
                    color_id = ParamUtil.getString(map, AppParams.COLOR_ID);
                    color_name = ParamUtil.getString(map, AppParams.COLOR_NAME);
                    size_name = ParamUtil.getString(map, AppParams.SIZE_NAME);
                    quantity = ParamUtil.getInt(map, AppParams.QUANTITY);
                    price = ParamUtil.getString(map, AppParams.PRICE);
                    variant_name = ParamUtil.getString(map, AppParams.VARIANT_NAME);
                    unit_amount = ParamUtil.getString(map, AppParams.UNIT_AMOUNT);

                    Map designs = ParamUtil.getMapData(map, "designs");
                    design_front_url = ParamUtil.getString(designs, AppParams.DESIGN_FRONT_URL);
                    design_front_url_md5 = ParamUtil.getString(designs, AppParams.DESIGN_FRONT_URL_MD5);
                    design_back_url = ParamUtil.getString(designs, AppParams.DESIGN_BACK_URL);
                    design_back_url_md5 = ParamUtil.getString(designs, AppParams.DESIGN_BACK_URL_MD5);
                }

                Map data = new HashMap();
                LOGGER.info("Data" + jsonRequest);
                String ship_ID = UUID.randomUUID().toString().substring(0, 24);
                String dropship_ID = UUID.randomUUID().toString().substring(0, 24);
                id = UUID.randomUUID().toString().substring(0, 24);
                LOGGER.info("ship_ID" + ship_ID);
                LOGGER.info("dropship_ID" + dropship_ID);
                
                Map result = new LinkedHashMap();
                List<Map> ship = OrderService.insertShipping(ship_ID, email, name, phone, line1, line2, city,
                        state, postal_code, country, country_name);

                List<Map> order = OrderService.insertDropshipOrder(dropship_ID, ship_ID, source, currency, note, store_id, reference_id,
                        state, shipping_method, addr_verified, addr_verified_note, extra_fee, tax_amount, ioss_number);

                List<Map> orderProduct = OrderService.insertDropshipOrderProduct(
                        order_id,
                        id,
                        base_id,
                        color,
                        color_id,
                        color_name,
                        size_id,
                        size_name,
                        quantity,
                        price,
                        design_front_url,
                        design_front_url_md5,
                        design_back_url,
                        design_back_url_md5,
                        variant_name,
                        unit_amount);

                result.put("order", order);
                LOGGER.info("order" + order);
                
                result.put("shipping", ship);
                LOGGER.info("tb_ship" + ship);

                result.put("items", orderProduct);
                LOGGER.info("order product" + orderProduct);

                LOGGER.info("result" + result);
                rc.put(AppParams.MESSAGE, "Dat hang thanh cong");
                data.put("data", result);
                rc.put(AppParams.RESPONSE_CODE, HttpResponseStatus.OK.code());
                rc.put(AppParams.RESPONSE_MSG, HttpResponseStatus.OK.reasonPhrase());
                rc.put(AppParams.RESPONSE_DATA, data);
//                rc.put(AppParams.MESSAGE, "Dat hang thanh cong");
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

    private static final Logger LOGGER = Logger.getLogger(CreateOrderHandler.class.getName());

}

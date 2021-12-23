package com.app.tts.server.handler.importFile;

import com.app.tts.services.importFileService.AddOrderServiceImport;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class insertOrder implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.vertx().executeBlocking(future -> {
            try {

                Random rand = new Random();
                List<Map> listVariant = AddOrderServiceImport.getSkuByFileId();


                Set<String> Var = new HashSet();
                Set<String> Var1 = new HashSet();
                String ord = "";
                String name = "";
                String email = "";
                String financialStatus = "";
                String dateAt = "";

                String state = "";
                String lineitemQuantity = "";
                String lineitemName = "";

                String shippingName = "";
                String shippingStreet = "";
                String shippingAddress1 = "";
                String shippingAddress2 = "";
                String shippingCompany = "";
                String shippingCity = "";
                String shippingZip = "";
                String shippingProvince = "";
                String shippingCountry = "";
                String shippingPhone = "";
                String shippingMethod = "";
                String notes = "";
                String designFrontUrl = "";
                String designBackUrl = "";
                String mockupFrontUrl = "";
                String mockupBackUrl = "";
                String currency = "";
                String unitAmount = "";
                String location = "";
                String store = "";
                String source = "";
                String note = "";
                String country = "";
                String variantId = "";
                int checkValidAddress = 0;
                Map data = new LinkedHashMap();
                String userId = "";
                //  order
                int quantity = 0;

                String country_name = "";
                String reference_id = "";
                String stateOr = "created";
                String extra_fee = "";
                String taxAmount = "";
                String iossNumber = "";
                // order prodduct
                String price = "";
                String sizeId = "";
                String sizeName = "";
                // shipping
                String postalCode = "";
                String pState = "approved";
                String redex = "^(.*[a-zA-Z0-9].*)[|](.*[a-zA-Z0-9].*)$";


                for (Map s : listVariant) {
                    String sku = ParamUtil.getString(s, "SKU");
                    if (sku.matches(redex)) {
                        String[] parts = sku.split("\\|");
                        variantId = parts[0];
                        sizeId = parts[1];
                        userId = ParamUtil.getString(s, "S_USER_ID");

                        String order = String.valueOf(rand.nextInt(100000));
                        String orderId = userId + "-" + "CT" + "-" + order;
                        String sipId = String.valueOf(rand.nextInt(100000));
                        String shippingId = userId + "-" + "CT" + "-" + sipId;
                        String orDrId = String.valueOf(rand.nextInt(100000));
                        String orDrId1 = userId + "-" + "CT" + "-" + orDrId;


                        ord = ParamUtil.getString(s, "S_ID");
                        name = ParamUtil.getString(s, "S_REFERENCE_ORDER");
                        email = ParamUtil.getString(s, "S_EMAIL");
                        financialStatus = ParamUtil.getString(s, "S_FINANCIAL_STATUS");
                        dateAt = ParamUtil.getString(s, "D_CREATE");
//
                        state = ParamUtil.getString(s, "S_STATE");
                        lineitemQuantity = ParamUtil.getString(s, "S_LINEITEM_QUANTITY");
                        quantity = Integer.parseInt(lineitemQuantity);
                        lineitemName = ParamUtil.getString(s, "S_LINEITEM_NAME");

                        shippingName = ParamUtil.getString(s, "S_SHIPPING_NAME");
                        shippingStreet = ParamUtil.getString(s, "S_SHIPPING_STREET");
                        shippingAddress1 = ParamUtil.getString(s, "S_SHIPPING_ADDRESS1");
                        shippingAddress2 = ParamUtil.getString(s, "S_SHIPPING_ADDRESS2");
                        shippingCompany = ParamUtil.getString(s, "S_SHIPPING_COMPANY");
                        shippingCity = ParamUtil.getString(s, "S_SHIPPING_CITY");
                        shippingZip = ParamUtil.getString(s, "S_SHIPPING_ZIP");
                        shippingProvince = ParamUtil.getString(s, "S_SHIPPING_PROVINCE");
                        shippingCountry = ParamUtil.getString(s, "S_SHIPPING_COUNTRY");
                        shippingPhone = ParamUtil.getString(s, "S_SHIPPING_PHONE");
                        shippingMethod = ParamUtil.getString(s, "S_SHIPPING_METHOD");
                        notes = ParamUtil.getString(s, "S_NOTES");
                        designFrontUrl = ParamUtil.getString(s, "S_DESIGN_FRONT_URL");
                        designBackUrl = ParamUtil.getString(s, "S_DESIGN_BACK_URL");
                        mockupFrontUrl = ParamUtil.getString(s, "S_MOCKUP_FRONT_URL");
                        mockupBackUrl = ParamUtil.getString(s, "S_MOCKUP_BACK_URL");
                        checkValidAddress = ParamUtil.getBoolean(s, "S_BY_PASS_CHECK_ADRESS") ? 0 : 1;
                        currency = ParamUtil.getString(s, "S_CURRENCY");
                        unitAmount = ParamUtil.getString(s, "S_UNIT_AMOUNT");
                        location = ParamUtil.getString(s, "S_FULFILLMENT_LOCATION");
                        store = ParamUtil.getString(s, "S_STORE_ID");
                        source = ParamUtil.getString(s, "S_SOURCE");
                        note = ParamUtil.getString(s, "S_NOTES");
                        country = ParamUtil.getString(s, "S_SHIPPING_COUNTRY");


                        Map get2 = AddOrderServiceImport.getVarById(variantId);
                        String idm = ParamUtil.getString(get2, "S_VAR_ID");
                        boolean dup = false;

                        if (!get2.isEmpty()) {
                            dup = true;
                        }
                        if (dup) {
                            Map get1 = AddOrderServiceImport.getVariantId(idm);

                            String imageId = ParamUtil.getString(get1, "S_IMAGE_ID");
                            String color = ParamUtil.getString(get1, "S_COLOR_ID");
                            Map colorId = AddOrderServiceImport.getColorById(color);
                            String nameColor = ParamUtil.getString(colorId, "S_NAME");
                            Map size = AddOrderServiceImport.getSize(sizeId);
                            String nameSize = ParamUtil.getString(size, "NAME_SIZE");
                            String dropshipPrice = ParamUtil.getString(size, "PRICE");


                            String colorValue = ParamUtil.getString(get1, "S_COLOR_VALUE");
                            String baseId = ParamUtil.getString(get1, "S_BASE_ID");
                            String FRONT_IMG_URL = ParamUtil.getString(get1, "S_FRONT_IMG_URL");
                            String BACK_IMG_URL = ParamUtil.getString(get1, "S_BACK_IMG_URL");
                            Map get = AddOrderServiceImport.getUrlImage(imageId);


                            Map Order = AddOrderServiceImport.insertOrder(orderId, currency, stateOr, shippingId, notes, source, store, reference_id,
                                    checkValidAddress, note, shippingMethod, taxAmount, unitAmount);

                            Map shipping = AddOrderServiceImport.insertShipping(shippingId, email, shippingName, shippingPhone, shippingAddress1,
                                    shippingAddress2, shippingCity, stateOr, postalCode, country, country_name);


                            List<Map> orderProduct = AddOrderServiceImport.insertOrderProduct(orDrId1, orderId, sizeId, dropshipPrice, quantity, lineitemName,
                                    baseId, FRONT_IMG_URL, BACK_IMG_URL, color, colorValue, nameColor, nameSize, unitAmount, designBackUrl, designFrontUrl);

                            List<Map> updateRows = AddOrderServiceImport.updateRows(pState, ord);

                            data.put("Order", updateRows);

                        }

                    } else {
                        Map getSku = AddOrderServiceImport.getSkuBySku(sku);

                        String sizeId1 = ParamUtil.getString(getSku, "S_SIZE_ID");
                        String sizeName1 = ParamUtil.getString(getSku, "S_SIZE_NAME");
                        String colorId1 = ParamUtil.getString(getSku, "S_COLOR_ID");
                        String colorName1 = ParamUtil.getString(getSku, "S_COLOR_NAME");
                        String colorValue1 = ParamUtil.getString(getSku, "S_COLOR_VALUE");
                        String price1 = ParamUtil.getString(getSku, "S_PRICE");
                        String baseID1 = ParamUtil.getString(getSku, "S_BASE_ID");

                        userId = ParamUtil.getString(s, "S_USER_ID");


                        String order = String.valueOf(rand.nextInt(100000));
                        String orderId = userId + "-" + "CT" + "-" + order;
                        String sipId = String.valueOf(rand.nextInt(100000));
                        String shippingId = userId + "-" + "CT" + "-" + sipId;
                        String orDrId = String.valueOf(rand.nextInt(100000));
                        String orDrId1 = userId + "-" + "CT" + "-" + orDrId;


                        ord = ParamUtil.getString(s, "S_ID");
                        name = ParamUtil.getString(s, "S_REFERENCE_ORDER");
                        email = ParamUtil.getString(s, "S_EMAIL");
                        financialStatus = ParamUtil.getString(s, "S_FINANCIAL_STATUS");
                        dateAt = ParamUtil.getString(s, "D_CREATE");
//
                        state = ParamUtil.getString(s, "S_STATE");
                        lineitemQuantity = ParamUtil.getString(s, "S_LINEITEM_QUANTITY");
                        quantity = Integer.parseInt(lineitemQuantity);
                        lineitemName = ParamUtil.getString(s, "S_LINEITEM_NAME");

                        shippingName = ParamUtil.getString(s, "S_SHIPPING_NAME");
                        shippingStreet = ParamUtil.getString(s, "S_SHIPPING_STREET");
                        shippingAddress1 = ParamUtil.getString(s, "S_SHIPPING_ADDRESS1");
                        shippingAddress2 = ParamUtil.getString(s, "S_SHIPPING_ADDRESS2");
                        shippingCompany = ParamUtil.getString(s, "S_SHIPPING_COMPANY");
                        shippingCity = ParamUtil.getString(s, "S_SHIPPING_CITY");
                        shippingZip = ParamUtil.getString(s, "S_SHIPPING_ZIP");
                        shippingProvince = ParamUtil.getString(s, "S_SHIPPING_PROVINCE");
                        shippingCountry = ParamUtil.getString(s, "S_SHIPPING_COUNTRY");
                        shippingPhone = ParamUtil.getString(s, "S_SHIPPING_PHONE");
                        shippingMethod = ParamUtil.getString(s, "S_SHIPPING_METHOD");
                        notes = ParamUtil.getString(s, "S_NOTES");
                        designFrontUrl = ParamUtil.getString(s, "S_DESIGN_FRONT_URL");
                        designBackUrl = ParamUtil.getString(s, "S_DESIGN_BACK_URL");
                        mockupFrontUrl = ParamUtil.getString(s, "S_MOCKUP_FRONT_URL");
                        mockupBackUrl = ParamUtil.getString(s, "S_MOCKUP_BACK_URL");
                        checkValidAddress = ParamUtil.getBoolean(s, "S_BY_PASS_CHECK_ADRESS") ? 0 : 1;
                        currency = ParamUtil.getString(s, "S_CURRENCY");
                        unitAmount = ParamUtil.getString(s, "S_UNIT_AMOUNT");
                        location = ParamUtil.getString(s, "S_FULFILLMENT_LOCATION");
                        store = ParamUtil.getString(s, "S_STORE_ID");
                        source = ParamUtil.getString(s, "S_SOURCE");
                        note = ParamUtil.getString(s, "S_NOTES");
                        country = ParamUtil.getString(s, "S_SHIPPING_COUNTRY");

                        Map Order = AddOrderServiceImport.insertOrder(orderId, currency, stateOr, shippingId, notes, source, store, reference_id,
                                checkValidAddress, note, shippingMethod, taxAmount, unitAmount);

                        Map shipping = AddOrderServiceImport.insertShipping(shippingId, email, shippingName, shippingPhone, shippingAddress1,
                                shippingAddress2, shippingCity, stateOr, postalCode, country, country_name);


                        List<Map> orderProduct = AddOrderServiceImport.insertOrderProduct(orDrId1, orderId, sizeId1, price1, quantity, lineitemName,
                                baseID1, designFrontUrl, designBackUrl, colorId1, colorValue1, colorName1, sizeName1, unitAmount, designBackUrl, designFrontUrl);

                        List<Map> updateRows = AddOrderServiceImport.updateRows(pState, ord);

                        data.put("Order", updateRows);

                    }

                }


                routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.OK.code());
                routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.OK.reasonPhrase());
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
}


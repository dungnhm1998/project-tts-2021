package com.app.tts.server.handler.importFile;

import com.app.tts.services.importFileService.AddOrderServiceImport;
import com.app.tts.util.ParamUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

public class JobB extends QuartzJobBean {


    public static Map readOneLine() {
        Map data = new LinkedHashMap();

        try {
            Random rand = new Random();
            Set<String> Var = new HashSet();


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
            List<Map> getfile = AddOrderServiceImport.getFile();
            for (Map m : getfile) {
                String sku1 = ParamUtil.getString(m, "S_LINEITEM_SKU");
                Var.add(sku1);
            }
            Map m1 = getfile.get(0);
            currency = ParamUtil.getString(m1, "S_CURRENCY");
            notes = ParamUtil.getString(m1, "S_NOTES");
            source = ParamUtil.getString(m1, "S_SOURCE");
            checkValidAddress = ParamUtil.getBoolean(m1, "S_BY_PASS_CHECK_ADRESS") ? 0 : 1;
            note = ParamUtil.getString(m1, "S_NOTES");
            shippingMethod = ParamUtil.getString(m1, "S_SHIPPING_METHOD");
            unitAmount = ParamUtil.getString(m1, "S_UNIT_AMOUNT");
            email = ParamUtil.getString(m1, "S_EMAIL");
            shippingName = ParamUtil.getString(m1, "S_SHIPPING_NAME");
            shippingPhone = ParamUtil.getString(m1, "S_SHIPPING_PHONE");
            shippingAddress1 = ParamUtil.getString(m1, "S_SHIPPING_ADDRESS1");
            shippingAddress2 = ParamUtil.getString(m1, "S_SHIPPING_ADDRESS2");
            shippingCity = ParamUtil.getString(m1, "S_SHIPPING_CITY");
            country = ParamUtil.getString(m1, "S_SHIPPING_COUNTRY");
            userId = ParamUtil.getString(m1, "S_USER_ID");


            String order = String.valueOf(rand.nextInt(100000));
            String orderId = userId + "-" + "CT" + "-" + order;
            String sipId = String.valueOf(rand.nextInt(100000));
            String shippingId = userId + "-" + "CT" + "-" + sipId;


            Map Order = AddOrderServiceImport.insertOrder(orderId, currency, stateOr, shippingId, notes, source, store, reference_id,
                    checkValidAddress, note, shippingMethod, taxAmount, unitAmount);

            Map shipping = AddOrderServiceImport.insertShipping(shippingId, email, shippingName, shippingPhone, shippingAddress1,
                    shippingAddress2, shippingCity, stateOr, postalCode, country, country_name);


            for (String groupfile : Var) {
                for (Map s : getfile) {
                    if (groupfile.matches(redex)) {
                        String[] parts = groupfile.split("\\|");
                        variantId = parts[0];
                        sizeId = parts[1];


                        String orDrId = String.valueOf(rand.nextInt(100000));
                        String orDrId1 = userId + "-" + "CT" + "-" + orDrId;


                        ord = ParamUtil.getString(s, "S_ID");
                        name = ParamUtil.getString(s, "S_REFERENCE_ORDER");
                        financialStatus = ParamUtil.getString(s, "S_FINANCIAL_STATUS");
                        dateAt = ParamUtil.getString(s, "D_CREATE");
//
                        state = ParamUtil.getString(s, "S_STATE");
                        lineitemQuantity = ParamUtil.getString(s, "S_LINEITEM_QUANTITY");
                        quantity = Integer.parseInt(lineitemQuantity);
                        lineitemName = ParamUtil.getString(s, "S_LINEITEM_NAME");

                        shippingStreet = ParamUtil.getString(s, "S_SHIPPING_STREET");
                        shippingCompany = ParamUtil.getString(s, "S_SHIPPING_COMPANY");
                        shippingZip = ParamUtil.getString(s, "S_SHIPPING_ZIP");
                        shippingProvince = ParamUtil.getString(s, "S_SHIPPING_PROVINCE");
                        shippingCountry = ParamUtil.getString(s, "S_SHIPPING_COUNTRY");
                        designFrontUrl = ParamUtil.getString(s, "S_DESIGN_FRONT_URL");
                        designBackUrl = ParamUtil.getString(s, "S_DESIGN_BACK_URL");
                        mockupFrontUrl = ParamUtil.getString(s, "S_MOCKUP_FRONT_URL");
                        mockupBackUrl = ParamUtil.getString(s, "S_MOCKUP_BACK_URL");
                        location = ParamUtil.getString(s, "S_FULFILLMENT_LOCATION");
                        store = ParamUtil.getString(s, "S_STORE_ID");


                        Map get2 = AddOrderServiceImport.getVarById(variantId);
                        String idm = ParamUtil.getString(get2, "S_VAR_ID");
                        boolean dup = false;

                        if (idm.equals(variantId)) {

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


                            List<Map> orderProduct = AddOrderServiceImport.insertOrderProduct(orDrId1, orderId, sizeId, dropshipPrice, quantity, lineitemName,
                                    baseId, FRONT_IMG_URL, BACK_IMG_URL, color, colorValue, nameColor, nameSize, unitAmount, designBackUrl, designFrontUrl);

                            List<Map> updateRows = AddOrderServiceImport.updateRows(pState, ord);

                            data.put("Order", updateRows);

                        } else  {
                            LOGGER.info("khoong co variant" + variantId);
                            AddOrderServiceImport.deleteOr(orderId);
                            AddOrderServiceImport.deleteShipping(shippingId);
                        }

                    } else {
                        Map getSku = AddOrderServiceImport.getSkuBySku(groupfile);

                        String sizeId1 = ParamUtil.getString(getSku, "S_SIZE_ID");
                        String sizeName1 = ParamUtil.getString(getSku, "S_SIZE_NAME");
                        String colorId1 = ParamUtil.getString(getSku, "S_COLOR_ID");
                        String colorName1 = ParamUtil.getString(getSku, "S_COLOR_NAME");
                        String colorValue1 = ParamUtil.getString(getSku, "S_COLOR_VALUE");
                        String price1 = ParamUtil.getString(getSku, "S_PRICE");
                        String baseID1 = ParamUtil.getString(getSku, "S_BASE_ID");

                        userId = ParamUtil.getString(s, "S_USER_ID");


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

//                            Map Order = AddOrderServiceImport.insertOrder(orderId, currency, stateOr, shippingId, notes, source, store, reference_id,
//                                    checkValidAddress, note, shippingMethod, taxAmount, unitAmount);
//
//                            Map shipping = AddOrderServiceImport.insertShipping(shippingId, email, shippingName, shippingPhone, shippingAddress1,
//                                    shippingAddress2, shippingCity, stateOr, postalCode, country, country_name);
                        String orDrId = String.valueOf(rand.nextInt(100000));
                        String orDrId1 = userId + "-" + "CT" + "-" + orDrId;

                        List<Map> orderProduct = AddOrderServiceImport.insertOrderProduct(orDrId1, orderId, sizeId1, price1, quantity, lineitemName,
                                baseID1, designFrontUrl, designBackUrl, colorId1, colorValue1, colorName1, sizeName1, unitAmount, designBackUrl, designFrontUrl);

                        List<Map> updateRows = AddOrderServiceImport.updateRows(pState, ord);

                        data.put("Order", updateRows);

                    }

                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


        return data;
    }


    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println(readOneLine());
    }
    private static final Logger LOGGER = Logger.getLogger(JobB.class.getName());

}

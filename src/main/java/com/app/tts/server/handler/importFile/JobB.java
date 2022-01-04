package com.app.tts.server.handler.importFile;

import com.app.tts.services.importFileService.AddOrderServiceImport;
import com.app.tts.util.ParamUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

public class JobB extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        Map data = new LinkedHashMap();

        try {

            Random rand = new Random();
            Set<String> Var = new HashSet();
            Set<String> Var1 = new HashSet();
            Set<String> Var2 = new HashSet();



            String ord = "", name = "", email = "", financialStatus = "", dateAt = "",
                    state = "", lineitemQuantity = "", lineitemName = "", shippingName = "", shippingStreet = "",
                    shippingAddress1 = "", shippingAddress2 = "", shippingCompany = "", shippingCity = "", shippingZip = "",
                    shippingProvince = "", shippingCountry = "", shippingPhone = "", shippingMethod = "",
                    notes = "", designFrontUrl = "", designBackUrl = "", mockupFrontUrl = "", mockupBackUrl = "",
                    currency = "", unitAmount = "", location = "", store = "", source = "",
                    note = "", country = "", variantId = "", userId = "";
            int checkValidAddress = 0;

            //  order
            int quantity = 0;

            String reference_id = "", stateOr = "created", extra_fee = "", taxAmount = "", iossNumber = "", pState1 = "fail";
            // order prodduct
            String price = "", sizeId = "", sizeName = "";
            // shipping
            String postalCode = "", pState = "approved", redex = "^(.*[a-zA-Z0-9].*)[|](.*[a-zA-Z0-9].*)$", redex1 = "^[0-9]*[-][0-9]*[-][0-9]*$";
            List<Map> getRows = new LinkedList<>();
            getRows.clear();
            getRows = AddOrderServiceImport.getFile();
            Map s = getRows.get(0);
            String sku1 = "";
            String file_id = "";
            String rowsId = "";
            for (Map m : getRows) {
                sku1 = ParamUtil.getString(m, "S_LINEITEM_SKU");
                file_id = ParamUtil.getString(m, "S_FILE_ID");
                rowsId = ParamUtil.getString(m, "S_ID");
                String groupColumn = ParamUtil.getString(m, "S_GROUP_COLUMN");
                Var.add(sku1);
                Var1.add(rowsId);
                Var2.add(groupColumn);

            }

            LOGGER.info("getRows" + " : " + getRows.size() + " | " + "file_id" + " : " + file_id + " | " + " " + "[ROWS ID: " + Var1 + " ]");
            // order and shipping
            Map m1 = getRows.get(0);
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
            shippingCountry = ParamUtil.getString(m1, "S_SHIPPING_COUNTRY");


//            List<Map> getColumnById1 = AddOrderServiceImport.getCloumn();


            if (currency.isEmpty()) {
                LOGGER.info("rows id" + ": " + ord + " | " + " không có currency");
                AddOrderServiceImport.updateRows(pState1, ord);
            } else {
                String order = String.valueOf(rand.nextInt(100000));
                String orderId = userId + "-" + "CT" + "-" + order;
                String sipId = String.valueOf(rand.nextInt(100000));
                String shippingId = userId + "-" + "CT" + "-" + sipId;


                AddOrderServiceImport.insertOrder(orderId, currency, stateOr, shippingId, notes, source, store, reference_id,
                        checkValidAddress, note, shippingMethod, taxAmount, unitAmount);
                AddOrderServiceImport.insertShipping(shippingId, email, shippingName, shippingPhone, shippingAddress1,
                        shippingAddress2, shippingCity, stateOr, postalCode, shippingCountry, country);


                for (String groupfile : Var) {

                    if (groupfile.matches(redex)) {
                        String[] lineSku = groupfile.split("\\|");
                        variantId = lineSku[0];
                        sizeId = lineSku[1];
                        if (lineSku.length >= 2) {
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
                            designFrontUrl = ParamUtil.getString(s, "S_DESIGN_FRONT_URL");
                            designBackUrl = ParamUtil.getString(s, "S_DESIGN_BACK_URL");
                            mockupFrontUrl = ParamUtil.getString(s, "S_MOCKUP_FRONT_URL");
                            mockupBackUrl = ParamUtil.getString(s, "S_MOCKUP_BACK_URL");
                            location = ParamUtil.getString(s, "S_FULFILLMENT_LOCATION");
                            store = ParamUtil.getString(s, "S_STORE_ID");


                            Map get2 = AddOrderServiceImport.getVarById(variantId);
                            String idm = ParamUtil.getString(get2, "S_VAR_ID");
                            if (variantId.equals(idm)) {

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
                                String orDrId = String.valueOf(rand.nextInt(100000));
                                String orDrId1 = userId + "-" + "CT" + "-" + orDrId;

                                AddOrderServiceImport.insertOrderProduct(orDrId1, orderId, sizeId, dropshipPrice, quantity, stateOr,lineitemName,
                                        baseId, FRONT_IMG_URL, BACK_IMG_URL, color, colorValue, nameColor, nameSize, unitAmount, designBackUrl, designFrontUrl);

                                AddOrderServiceImport.updateRows(pState, rowsId);
                                LOGGER.info("update Rows" + " | " + rowsId + " ; " + "State" + ": " + pState + "|");
                            } else {
                                AddOrderServiceImport.updateRows(pState1, rowsId);
                                LOGGER.info("{" + rowsId + "}" + " | " + "variant in valid" + ": " + variantId);
                                //do tạo order trước nhưng do variant k có lên delete or and shipping
                                AddOrderServiceImport.deleteOr(orderId);
                                AddOrderServiceImport.deleteShipping(shippingId);
                            }
                        }
                    } else {
                        if (!sku1.isEmpty() && groupfile.matches(redex1)) {


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
                            String orDrId = String.valueOf(rand.nextInt(100000));
                            String orDrId1 = userId + "-" + "CT" + "-" + orDrId;
                            AddOrderServiceImport.insertOrderProduct(orDrId1, orderId, sizeId1, price1, quantity, stateOr,lineitemName,
                                    baseID1, designFrontUrl, designBackUrl, colorId1, colorValue1, colorName1, sizeName1, unitAmount, designBackUrl, designFrontUrl);

                            List<Map> updateRows = AddOrderServiceImport.updateRows(pState, ord);
                            LOGGER.info("update Rows" + " | " + rowsId + " ; " + "State" + ": " + pState + "|");


                        } else {
                            AddOrderServiceImport.updateRows(pState1, rowsId);

                            LOGGER.info("{" + rowsId + "}" + " | " + "variant in valid" + ": " + variantId);
                            //do tạo order trước nhưng do variant k có lên delete or and shipping
                            AddOrderServiceImport.deleteOr(orderId);
                            AddOrderServiceImport.deleteShipping(shippingId);
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e2) {
            LOGGER.info("=> e2" + ": " + e2.getMessage());
            e2.printStackTrace();
        } catch (Exception e1) {
            LOGGER.info("************************" + " " + e1.getMessage() + " " + "************************");

        }

        LOGGER.info("************************" + "DONE" + "************************");

    }

    private static final Logger LOGGER = Logger.getLogger(JobB.class.getName());

}

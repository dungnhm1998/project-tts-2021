package com.app.tts.main.quartz;

import com.app.tts.services.OrderService;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import org.apache.commons.csv.CSVRecord;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.app.tts.services.OrderService.insertOrder;

public class QuartzJob2 /*implements Job*/ extends QuartzJobBean {
//    @Override
//    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        System.out.println("------------QuartzJob2 - CreateOrder---------------");
//        insertOrder2();
//        System.out.println("--------------------------------------");
//        System.out.println("--------------------------------------");
//    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("------------QuartzJob2 - c2 - CreateOrder---------------");
//        insertOrder2();
        checkSKU();
        System.out.println("--------------------------------------");
        System.out.println("--------------------------------------");
    }
    public static String orderId;
    public static int countProduct;

    public void checkSKU(){
        try {
            List<Map> listFileRowsRecord = OrderService.getFileRows();
//            System.out.println("listFileRowsRecord = " + listFileRowsRecord);
            List<Map> listCampRecord = new LinkedList<>();
            List<Map> listCustomRecord = new LinkedList<>();
            if(!listFileRowsRecord.isEmpty()) {
                for (Map fileRowsRecord : listFileRowsRecord) {
                    String sku = ParamUtil.getString(fileRowsRecord, "S_LINEITEM_SKU");
                    int index = sku.indexOf("|");
                    if (index >= 0) {
                        listCampRecord.add(fileRowsRecord);
                    } else {
                        listCustomRecord.add(fileRowsRecord);
                    }
                    System.out.println("fileId = " + ParamUtil.getString(fileRowsRecord, "S_FILE_ID"));
                }
                if (!listCampRecord.isEmpty()) {
                    System.out.println("===========have Camp===================");
                    insertOrderHaveCamp(listCampRecord);
                    System.out.println("===========have Camp========== end===================");
                }
                if (!listCustomRecord.isEmpty()) {
                    System.out.println("===========do Not Have Camp===================");
//                    System.out.println("listCustomRecord = " + listCustomRecord);
                    insertOrderDoNotHaveCamp(listCustomRecord);
                    System.out.println("===========do Not Have Camp============ end===================");
                }
            }
        } catch (SQLException throwables) {
            System.out.println("==================khong co ban ghi o trang thai Created=================");
        }
    }

    public void insertOrderDoNotHaveCamp(List<Map> listCustomRecord){
        int countOrderCustom = 0;
        for(Map fileRowsRecord: listCustomRecord) {
            String idSku = ParamUtil.getString(fileRowsRecord, "S_LINEITEM_SKU");
            System.out.println("s_sku====================" + idSku);
            try {
                Map skuRecord = OrderService.getSkuById(idSku);
                String message = "fail";
                if (!skuRecord.isEmpty()) {
                    //lay base
                    String sBaseId = ParamUtil.getString(skuRecord, "S_BASE_ID");
                    Map baseRecord = OrderService.getBaseByIdInVariant(sBaseId);
                    //lay color
                    String sColorId = ParamUtil.getString(skuRecord, "S_COLOR_ID");
                    Map colorRecord = OrderService.getColorByIdInVariant(sColorId);
                    //lay size
                    String sSizeId = ParamUtil.getString(skuRecord, "S_SIZE_ID");
                    Map sizeRecord = OrderService.getSizeById(sSizeId);
                    if(countOrderCustom == 0) {
                        orderId = insertOrderCustom(fileRowsRecord);
                        countProduct = 0;
                        countOrderCustom++;
                    }
                    message = insertProductCustom(fileRowsRecord, skuRecord, baseRecord, colorRecord, sizeRecord);
                }
                OrderService.updateFileRowsState(ParamUtil.getString(fileRowsRecord, AppParams.S_ID), message);
                System.out.println("orderId = " + orderId + "; countProduct = " + countProduct);
                System.out.println("message=============================================" + message);
                System.out.println("================================================================");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public String insertProductCustom(Map fileRowsRecord, Map skuRecord, Map baseRecord,
                                      Map colorRecord, Map sizeRecord){
        Random rand = new Random();
        String productId = String.valueOf(rand.nextInt(1000000000));
        String baseId = ParamUtil.getString(baseRecord, AppParams.S_ID);
        String color = ParamUtil.getString(colorRecord, AppParams.S_VALUE);// tuong ung cot ?
        String colorId = ParamUtil.getString(colorRecord, AppParams.S_ID);
        String colorName = ParamUtil.getString(colorRecord, AppParams.S_NAME);
        String sizeId = ParamUtil.getString(sizeRecord, AppParams.S_ID);
        String sizeName = ParamUtil.getString(sizeRecord, AppParams.S_NAME);
        int   quantity = Integer.parseInt(ParamUtil.getString(fileRowsRecord, "S_LINEITEM_QUANTITY"));
        String price = ParamUtil.getString(skuRecord, "S_PRICE");

        String designFrontUrl = ParamUtil.getString(fileRowsRecord, "S_DESIGN_FRONT_URL");
        String variantFrontUrl = ParamUtil.getString(fileRowsRecord, "S_MOCKUP_FRONT_URL");
        String designBackUrl = ParamUtil.getString(fileRowsRecord, "S_DESIGN_BACK_URL");
        String variantBackUrl = ParamUtil.getString(fileRowsRecord, "S_MOCKUP_BACK_URL");

        String variantName = null; // khong tim thay o cac bang input
        String unitAmount = ParamUtil.getString(fileRowsRecord, AppParams.S_UNIT_AMOUNT);

        String message = "fail";
        try {
            Map productResultMap = OrderService.insertProduct(orderId,
                    productId, baseId, color, colorId, colorName, sizeId, sizeName, quantity, price,
                    designFrontUrl, variantFrontUrl, designBackUrl, variantBackUrl,
                    variantName, unitAmount
            );

            if(!productResultMap.isEmpty()){//Map order, product, shipping # null
                message = "done";
                countProduct++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return message;
    }

    public String insertOrderCustom(Map fileRowsRecord){
        //order
        Random rand = new Random();
//         orderId = ParamUtil.getString(fileRowsRecord, "S_ORDER_ID");
//        if(orderId.isEmpty()) {
            orderId = String.valueOf(rand.nextInt(1000000000));
//        }
        String source = ParamUtil.getString(fileRowsRecord, AppParams.S_SOURCE);
        if(source == null){
            source = "import file";
        }
        String currency = ParamUtil.getString(fileRowsRecord, AppParams.S_CURRENCY);
        String note = ParamUtil.getString(fileRowsRecord, "S_NOTES");

        String storeId = ParamUtil.getString(fileRowsRecord, AppParams.S_STORE_ID);
        String referenceId = ParamUtil.getString(fileRowsRecord, AppParams.S_REFERENCE_ORDER);// tuong ung cot ?
        String state = ParamUtil.getString(fileRowsRecord, AppParams.S_STATE);
        String shippingMethod = ParamUtil.getString(fileRowsRecord, AppParams.S_SHIPPING_METHOD);

        String extraFee = null; // khong co cot nay trong fileRow

        String taxAmount = null; // khong co cot nay trong fileRow
        String iossNumber = ParamUtil.getString(fileRowsRecord, AppParams.S_IOSS_NUMBER);

        int addrVerified = 0; // khong co cot nay trong fileRow
        String addrVerifiedNote = null; // khong co cot nay trong fileRow

        //shipping
        String shippingId = String.valueOf(rand.nextInt(1000000000));

        String email = ParamUtil.getString(fileRowsRecord, AppParams.S_EMAIL);
        String nameShipping = ParamUtil.getString(fileRowsRecord, "S_SHIPPING_NAME");
        String phone = ParamUtil.getString(fileRowsRecord, "S_SHIPPING_PHONE");

        String line1 = ParamUtil.getString(fileRowsRecord, "S_SHIPPING_ADDRESS1");
        String line2 = ParamUtil.getString(fileRowsRecord, "S_SHIPPING_ADDRESS1");
        String city = ParamUtil.getString(fileRowsRecord, "S_SHIPPING_CITY");
        String stateShipping = "created";// khong co cot nay trong fileRow
        String postalCode = ParamUtil.getString(fileRowsRecord, "S_SHIPPING_ZIP");
        String country = ParamUtil.getString(fileRowsRecord, "S_SHIPPING_COUNTRY");
        String countryName = null; // khong co cot nay trong fileRow

//        String productId = String.valueOf(rand.nextInt(1000000000));
//        String baseId = ParamUtil.getString(baseRecord, AppParams.S_ID);
//        String color = ParamUtil.getString(colorRecord, AppParams.S_VALUE);// tuong ung cot ?
//        String colorId = ParamUtil.getString(colorRecord, AppParams.S_ID);
//        String colorName = ParamUtil.getString(colorRecord, AppParams.S_NAME);
//        String sizeId = ParamUtil.getString(sizeRecord, AppParams.S_ID);
//        String sizeName = ParamUtil.getString(sizeRecord, AppParams.S_NAME);
//        int   quantity = Integer.parseInt(ParamUtil.getString(fileRowsRecord, "S_LINEITEM_QUANTITY"));
//        String price = ParamUtil.getString(skuRecord, "S_PRICE");
//
//        String designFrontUrl = ParamUtil.getString(fileRowsRecord, "S_DESIGN_FRONT_URL");
//        String variantFrontUrl = ParamUtil.getString(fileRowsRecord, "S_MOCKUP_FRONT_URL");
//        String designBackUrl = ParamUtil.getString(fileRowsRecord, "S_DESIGN_BACK_URL");
//        String variantBackUrl = ParamUtil.getString(fileRowsRecord, "S_MOCKUP_BACK_URL");
//
//        String variantName = null; // khong tim thay o cac bang input
//        String unitAmount = ParamUtil.getString(fileRowsRecord, AppParams.S_UNIT_AMOUNT);

//        String message = "fail";
        try {
//            Map productResultMap = OrderService.insertProduct(orderId,
//                    productId, baseId, color, colorId, colorName, sizeId, sizeName, quantity, price,
//                    designFrontUrl, variantFrontUrl, designBackUrl, variantBackUrl,
//                    variantName, unitAmount
//            );
            List<Map> orderResultList = OrderService.insertOrder(orderId, source, currency, note,
                    storeId, referenceId, state, shippingMethod,
                    shippingId, extraFee, taxAmount, iossNumber,
                    addrVerified, addrVerifiedNote);
//            Map orderResultMap = orderResultList.get(0);

            List<Map> shippingResultList = OrderService.insertShipping(shippingId,
                    email, nameShipping, phone,
                    line1, line2, city, stateShipping, postalCode, country, countryName);
//            Map shippingResultMap = shippingResultList.get(0);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return orderId;
    }

    public void insertOrderHaveCamp(List<Map> listCampRecord){
        int countProductCamp = 0;
        for(Map fileRowsRecord : listCampRecord) {
            String sku = ParamUtil.getString(fileRowsRecord, "S_LINEITEM_SKU");
            int index = sku.indexOf("|");
            String variantId = sku.substring(0, index);
            String sizeId = sku.substring(index + 1);
            System.out.println("variantId =====================" + variantId);
            System.out.println("sizeId=====================" + sizeId);

            countProductCamp = checkVariantId(variantId, sizeId, fileRowsRecord, countProductCamp);
        }
    }

    public int checkVariantId(String variantId, String sizeId, Map fileRowsRecord, int countProductCamp ){
        try {
            Map proVariantRecord = OrderService.getProVariantById(variantId);
            String message;
            if(!proVariantRecord.isEmpty()){
                //lay thong tu cac bang lien quan
                //lay design
                String sFrontDesignId = ParamUtil.getString(proVariantRecord, "S_FRONT_DESIGN_ID");
                Map designRecord = OrderService.getDesignByIdInVariant(sFrontDesignId);
                //lay image
                String sImageId = ParamUtil.getString(designRecord, "S_IMAGE_ID");
                Map imageRecord = OrderService.getImageByIdInDesign(sImageId);
                //lay base
                String sBaseId = ParamUtil.getString(proVariantRecord, "S_BASE_ID");
                Map baseRecord = OrderService.getBaseByIdInVariant(sBaseId);
                //lay color
                String sColorId = ParamUtil.getString(proVariantRecord, "S_COLOR_ID");
                Map colorRecord = OrderService.getColorByIdInVariant(sColorId);
                //lay size
                Map sizeRecord = OrderService.getSizeById(sizeId);
                // lay price
                Map priceRecord = OrderService.getPriceByIdBaseSize(sBaseId, sizeId);

                // them order neu la san pham dau tien
                if(countProductCamp == 0) {
                    orderId = insertOrderFromFileRows(fileRowsRecord);
                    countProduct = 0;
                    countProductCamp++;
                }
                // them product
                message = insertProductFromFileRows(fileRowsRecord, imageRecord,
                        baseRecord, colorRecord, sizeRecord, priceRecord, proVariantRecord);
            }else{
                message = "fail";
            }
            // cap nhat bang file row
            OrderService.updateFileRowsState(ParamUtil.getString(fileRowsRecord, AppParams.S_ID), message);
            System.out.println("orderId = " + orderId + "; countProduct = " + countProduct);
            System.out.println("message=============================================" + message);
            System.out.println("================================================================");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return countProductCamp;
    }

    public String insertProductFromFileRows(Map fileRowsRecord, Map imageRecord,
                                            Map baseRecord, Map colorRecord, Map sizeRecord, Map priceRecord,Map proVariantRecord){
        Random rand = new Random();
        String productId = String.valueOf(rand.nextInt(1000000000));
        String baseId = ParamUtil.getString(baseRecord, AppParams.S_ID);
        String color = ParamUtil.getString(colorRecord, AppParams.S_VALUE);// tuong ung cot ?
        String colorId = ParamUtil.getString(colorRecord, AppParams.S_ID);
        String colorName = ParamUtil.getString(colorRecord, AppParams.S_NAME);
        String sizeId = ParamUtil.getString(sizeRecord, AppParams.S_ID);
        String sizeName = ParamUtil.getString(sizeRecord, AppParams.S_NAME);
        int   quantity = Integer.parseInt(ParamUtil.getString(fileRowsRecord, "S_LINEITEM_QUANTITY"));
        String price = ParamUtil.getString(priceRecord, "S_DROPSHIP_PRICE");

        String designFrontUrl = ParamUtil.getString(imageRecord, "S_URL");
        String variantFrontUrl = ParamUtil.getString(proVariantRecord, "S_FRONT_IMG_URL");
        String designBackUrl = null; // cot nay cung lay theo url cua bang image nhung backid trong bang variant de trong nen = null
        String variantBackUrl = ParamUtil.getString(proVariantRecord, "S_BACK_IMG_URL");

        String variantName = ParamUtil.getString(proVariantRecord, AppParams.S_NAME);
        String unitAmount = ParamUtil.getString(fileRowsRecord, AppParams.S_UNIT_AMOUNT);

        String message = "fail";
        try {
            Map productResultMap = OrderService.insertProduct(orderId,
                    productId, baseId, color, colorId, colorName, sizeId, sizeName, quantity, price,
                    designFrontUrl, variantFrontUrl, designBackUrl, variantBackUrl,
                    variantName, unitAmount
            );
//            List<Map> orderResultList = OrderService.insertOrder(orderId, source, currency, note,
//                    storeId, referenceId, state, shippingMethod,
//                    shippingId, extraFee, taxAmount, iossNumber,
//                    addrVerified, addrVerifiedNote);
//            Map orderResultMap = orderResultList.get(0);
//
//            List<Map> shippingResultList = OrderService.insertShipping(shippingId,
//                    email, nameShipping, phone,
//                    line1, line2, city, stateShipping, postalCode, country, countryName);
//            Map shippingResultMap = shippingResultList.get(0);

            if(!productResultMap.isEmpty()){// && !orderResultMap.isEmpty() && !shippingResultMap.isEmpty()){//Map order, product, shipping # null
                message = "done";
                countProduct++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return message;
    }

    public String insertOrderFromFileRows(Map fileRowsRecord ){
//        , Map designRecord, Map imageRecord,
//                                        Map baseRecord, Map colorRecord, Map sizeRecord, Map priceRecord, Map proVariantRecord){
        //order
        Random rand = new Random();
         orderId = ParamUtil.getString(fileRowsRecord, "S_ORDER_ID");
        if(orderId.isEmpty()) {
            orderId = String.valueOf(rand.nextInt(1000000000));
        }
        String source = ParamUtil.getString(fileRowsRecord, AppParams.S_SOURCE);
        if(source == null){
            source = "import file";
        }
        String currency = ParamUtil.getString(fileRowsRecord, AppParams.S_CURRENCY);
        String note = ParamUtil.getString(fileRowsRecord, "S_NOTES");

        String storeId = ParamUtil.getString(fileRowsRecord, AppParams.S_STORE_ID);
        String referenceId = ParamUtil.getString(fileRowsRecord, AppParams.S_REFERENCE_ORDER);// tuong ung cot ?
        String state = ParamUtil.getString(fileRowsRecord, AppParams.S_STATE);
        String shippingMethod = ParamUtil.getString(fileRowsRecord, AppParams.S_SHIPPING_METHOD);

        String extraFee = null; // khong co cot nay trong fileRow

        String taxAmount = null; // khong co cot nay trong fileRow
        String iossNumber = ParamUtil.getString(fileRowsRecord, AppParams.S_IOSS_NUMBER);

        int addrVerified = 0; // khong co cot nay trong fileRow
        String addrVerifiedNote = null; // khong co cot nay trong fileRow

        //shipping
        String shippingId = String.valueOf(rand.nextInt(1000000000));

        String email = ParamUtil.getString(fileRowsRecord, AppParams.S_EMAIL);
        String nameShipping = ParamUtil.getString(fileRowsRecord, "S_SHIPPING_NAME");
        String phone = ParamUtil.getString(fileRowsRecord, "S_SHIPPING_PHONE");

        String line1 = ParamUtil.getString(fileRowsRecord, "S_SHIPPING_ADDRESS1");
        String line2 = ParamUtil.getString(fileRowsRecord, "S_SHIPPING_ADDRESS1");
        String city = ParamUtil.getString(fileRowsRecord, "S_SHIPPING_CITY");
        String stateShipping = "created"; // khong co cot nay trong fileRow
        String postalCode = ParamUtil.getString(fileRowsRecord, "S_SHIPPING_ZIP");
        String country = ParamUtil.getString(fileRowsRecord, "S_SHIPPING_COUNTRY");
        String countryName = null; // khong co cot nay trong fileRow
//        //product
//        String productId = String.valueOf(rand.nextInt(1000000000));
//        String baseId = ParamUtil.getString(baseRecord, AppParams.S_ID);
//        String color = ParamUtil.getString(colorRecord, AppParams.S_VALUE);// tuong ung cot ?
//        String colorId = ParamUtil.getString(colorRecord, AppParams.S_ID);
//        String colorName = ParamUtil.getString(colorRecord, AppParams.S_NAME);
//        String sizeId = ParamUtil.getString(sizeRecord, AppParams.S_ID);
//        String sizeName = ParamUtil.getString(sizeRecord, AppParams.S_NAME);
//         int   quantity = Integer.parseInt(ParamUtil.getString(fileRowsRecord, "S_LINEITEM_QUANTITY"));
//        String price = ParamUtil.getString(priceRecord, "S_DROPSHIP_PRICE");
//
//        String designFrontUrl = ParamUtil.getString(imageRecord, "S_URL");
//        String variantFrontUrl = ParamUtil.getString(proVariantRecord, "S_FRONT_IMG_URL");
//        String designBackUrl = null; // cot nay cung lay theo url cua bang image nhung backid trong bang variant de trong nen = null
//        String variantBackUrl = ParamUtil.getString(proVariantRecord, "S_BACK_IMG_URL");
//
//        String variantName = ParamUtil.getString(proVariantRecord, AppParams.S_NAME);
//        String unitAmount = ParamUtil.getString(fileRowsRecord, AppParams.S_UNIT_AMOUNT);

//        String message = "fail";
        try {
//            Map productResultMap = OrderService.insertProduct(orderId,
//                    productId, baseId, color, colorId, colorName, sizeId, sizeName, quantity, price,
//                    designFrontUrl, variantFrontUrl, designBackUrl, variantBackUrl,
//                    variantName, unitAmount
//            );
            List<Map> orderResultList = OrderService.insertOrder(orderId, source, currency, note,
                    storeId, referenceId, state, shippingMethod,
                    shippingId, extraFee, taxAmount, iossNumber,
                    addrVerified, addrVerifiedNote);
            Map orderResultMap = orderResultList.get(0);

            List<Map> shippingResultList = OrderService.insertShipping(shippingId,
                    email, nameShipping, phone,
                    line1, line2, city, stateShipping, postalCode, country, countryName);
            Map shippingResultMap = shippingResultList.get(0);

//            if(!productResultMap.isEmpty() &&
//                    if(!orderResultMap.isEmpty() && !shippingResultMap.isEmpty()){//Map order, product, shipping # null
//                message = "done";
//            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return orderId;
    }

    public void insertOrder2(){
        int countQuartz = ReadFile.count2;
        List<CSVRecord> listQuartz = ReadFile.listData;

        if(countQuartz < listQuartz.size()){
            CSVRecord csvRecord = listQuartz.get(countQuartz);
            ReadFile.count2 += 2;

            try {
                Random rand = new Random();
                String orderId = String.valueOf(rand.nextInt(1000000000));
                String source = csvRecord.get(0);
                String currency = csvRecord.get(1);
                String note = csvRecord.get(2);
                String shippingId = csvRecord.get(3);
                String storeId = "1";
                String referenceId = "1";
                String state = "1";
                String shippingMethod = "1";
                String extraFee = "1";
                String taxAmount = "1";
                String iossNumber = "1";
                int addrVerified = 0;
                String addrVerifiedNote = "1";

                List<Map> orderResultList = insertOrder(orderId, source, currency, note,
                        storeId, referenceId, state, shippingMethod,
                        shippingId, extraFee, taxAmount, iossNumber,
                        addrVerified, addrVerifiedNote);
                System.out.println("------------insert-------------");
                System.out.println(orderResultList);
                System.out.println("-------------------------------");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}

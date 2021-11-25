package com.app.tts.server.handler.campaign;

import com.app.tts.services.CampaignService;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.sql.SQLException;
import java.util.*;

public class AddProductHandler implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.vertx().executeBlocking(future -> {
            try{
                Map jsonRequest = routingContext.getBodyAsJson().getMap();
                String campaign_id = ParamUtil.getString(jsonRequest, AppParams.CAMPAIGN_ID);//
                List<Map> listProduct = ParamUtil.getListData(jsonRequest, "products");

                Map data = new LinkedHashMap();

                for(Map productMap : listProduct){
                    int in_defautl = ParamUtil.getBoolean(productMap, AppParams.DEFAULT) ? 1 : 0;//
                    String base_id = ParamUtil.getString(productMap, AppParams.BASE_ID);//

                    List<Map> colorList = ParamUtil.getListData(productMap, AppParams.COLORS);
                    String color_id = null;//
                    String default_color = null;//       chua biet de gia tri nhu the nao
                    for(Map colorMap : colorList){
                        String colorIdSub = ParamUtil.getString(colorMap, AppParams.ID);
                        if(color_id != null){
                            color_id = color_id + ",";
                        }
                        if(color_id != null) {
                            color_id = color_id + colorIdSub;
                        }else{
                            color_id = colorIdSub;
                        }
                    }

                    List<Map> priceList = ParamUtil.getListData(productMap, AppParams.PRICES);
                    String size_id = null; //
                    String price = null; //

                    for(Map priceMap : priceList){
                        String sizeIdSub = ParamUtil.getString(priceMap, AppParams.SIZE_ID);
                        String priceSub = ParamUtil.getString(priceMap, AppParams.PRICE);
                        if(size_id != null){
                            size_id = size_id + ",";
                        }
                        if(price != null){
                            price = price + ",";
                        }

                        if(size_id != null) {
                            size_id = size_id + sizeIdSub;
                        }else{
                            size_id = sizeIdSub;
                        }

                        if(price != null) {
                            price = price + priceSub;
                        }else{
                            price = priceSub;
                        }
                    }

                    // khong ro mockups chua gi, tam de = null
                    String mockups = null;//

                    data = addProduct(campaign_id,
                            in_defautl, base_id,
                            color_id, default_color,
                            size_id, price,
                            mockups);
                }

                routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.CREATED.code());
                routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.CREATED.reasonPhrase());
                routingContext.put(AppParams.RESPONSE_DATA, data);

                future.complete();
            }catch (Exception e){
                routingContext.fail(e);
            }
        }, asyncResult -> {
            if(asyncResult.succeeded()){
                routingContext.next();
            }else{
                routingContext.fail(asyncResult.cause());
            }
        });
    }

    public static Map addProduct(String campaign_id,
                                       int default_n, String base_id,
                                       String color_id, String default_color,
                                       String size_id, String price,
                                       String mockups) throws SQLException {
        Map result = CampaignService.addProduct(campaign_id,
                default_n, base_id,
                color_id, default_color,
                size_id, price,
                mockups);

//        List<Map> listProduct = ParamUtil.getListData(result, AppParams.RESULT_DATA);
//        List<Map> listCampaign = ParamUtil.getListData(result, AppParams.RESULT_DATA_2);
//        List<Map> listColor = ParamUtil.getListData(result, AppParams.RESULT_DATA_3);
//        List<Map> listSize = ParamUtil.getListData(result, AppParams.RESULT_DATA_4);
//
//        Map resultMap = format(listProduct, listCampaign, listColor, listSize);
        return result;
    }

//    public static Map format(List<Map> productInput, List<Map> listCampaign, List<Map> colorInput, List<Map> sizeInput){
//        Map campaignMap = new LinkedHashMap();
//
//        Map campaign = listCampaign.get(0);
//
//        campaignMap.put(AppParams.ID, ParamUtil.getString(campaign, AppParams.S_ID_2));
//        campaignMap.put(AppParams.USER_ID, ParamUtil.getString(campaign, AppParams.S_USER_ID));
//        campaignMap.put(AppParams.TITLE, ParamUtil.getString(campaign, AppParams.S_TITLE));
//        campaignMap.put(AppParams.DESC, ParamUtil.getString(campaign, AppParams.S_DESC));
//        campaignMap.put(AppParams.CATEGORY_IDS, ParamUtil.getString(campaign, AppParams.S_CATEGORY_IDS));
//        campaignMap.put(AppParams.TAGS, ParamUtil.getString(campaign, AppParams.S_TAGS));
//        campaignMap.put(AppParams.START, ParamUtil.getString(campaign, AppParams.D_START));
//        campaignMap.put(AppParams.END, ParamUtil.getString(campaign, AppParams.D_END));
//        campaignMap.put(AppParams.RELAUNCH, ParamUtil.getString(campaign, AppParams.N_AUTO_RELAUNCH));
//        campaignMap.put(AppParams.PRIVATE, ParamUtil.getString(campaign, AppParams.N_PRIVATE));
//        campaignMap.put(AppParams.FB_PIXEL, ParamUtil.getString(campaign, AppParams.S_FB_PIXEL));
//        campaignMap.put(AppParams.GG_PIXEL, ParamUtil.getString(campaign, AppParams.S_GG_PIXEL));
//        campaignMap.put(AppParams.CREATE, ParamUtil.getString(campaign, AppParams.D_CREATE));
//        campaignMap.put(AppParams.UPDATE, ParamUtil.getString(campaign, AppParams.D_UPDATE));
//        campaignMap.put(AppParams.STATE, ParamUtil.getString(campaign, AppParams.S_STATE_2));
//        campaignMap.put(AppParams.LENGTH, ParamUtil.getString(campaign, AppParams.N_LENGTH));
//        campaignMap.put(AppParams.SALE_PRICE, ParamUtil.getString(campaign, AppParams.S_SALE_PRICE));
//        campaignMap.put(AppParams.FAVORITE, ParamUtil.getString(campaign, AppParams.N_FAVORITE));
//        campaignMap.put(AppParams.ARCHIVED, ParamUtil.getString(campaign, AppParams.N_ARCHIVED));
//        campaignMap.put(AppParams.DESIGN_FRONT_URL, ParamUtil.getString(campaign, AppParams.S_DESIGN_FRONT_URL));
//        campaignMap.put(AppParams.DESIGN_BACK_URL, ParamUtil.getString(campaign, AppParams.S_DESIGN_BACK_URL));
//        campaignMap.put(AppParams.DOMAIN_ID, ParamUtil.getString(campaign, AppParams.S_DOMAIN_ID));
//        campaignMap.put(AppParams.DOMAIN, ParamUtil.getString(campaign, AppParams.S_DOMAIN));
//        campaignMap.put(AppParams.ART_IDS, ParamUtil.getString(campaign, AppParams.S_ART_IDS));
//        campaignMap.put(AppParams.BASE_GROUP_ID, ParamUtil.getString(campaign, AppParams.S_BASE_GROUP_ID));
//        campaignMap.put(AppParams.BACK_VIEW, ParamUtil.getString(campaign, AppParams.N_BACK_VIEW));
//        campaignMap.put(AppParams.AS_TM, ParamUtil.getString(campaign, AppParams.N_AS_TM));
//        campaignMap.put(AppParams.AD_TAGS, ParamUtil.getString(campaign, AppParams.S_AD_TAGS));
//        campaignMap.put(AppParams.SEO_TITLE, ParamUtil.getString(campaign, AppParams.S_SEO_TITLE));
//        campaignMap.put(AppParams.SEO_DESC, ParamUtil.getString(campaign, AppParams.S_SEO_DESC));
//        campaignMap.put(AppParams.SEO_IMAGE_COVER, ParamUtil.getString(campaign, AppParams.S_SEO_IMAGE_COVER));
//        campaignMap.put(AppParams.DESIGN_CHECK, ParamUtil.getString(campaign, AppParams.N_DESIGN_CHECK));
//        campaignMap.put(AppParams.DESIGN_VERSION, ParamUtil.getString(campaign, AppParams.S_DESIGN_VERSION));
//        campaignMap.put(AppParams.LEFT_CHEST, ParamUtil.getString(campaign, AppParams.N_LEFT_CHEST));
//        campaignMap.put(AppParams.SUB_STATE, ParamUtil.getString(campaign, AppParams.S_SUB_STATE));
//        campaignMap.put(AppParams.MODIFIED_AT, ParamUtil.getString(campaign, "MODIFIED_AT"));
//        campaignMap.put(AppParams.OLD_TAGS, ParamUtil.getString(campaign, "OLD_TAGS"));
//
//        List<Map> listProduct = new LinkedList<>();
//
//        for(Map productMap : productInput){
//            Map resultProduct = new LinkedHashMap();
//
//            resultProduct.put(AppParams.ID, ParamUtil.getString(productMap, AppParams.S_ID_2));
//            resultProduct.put(AppParams.BASE_ID, ParamUtil.getString(productMap, AppParams.S_BASE_ID));
//            resultProduct.put(AppParams.NAME, ParamUtil.getString(productMap, AppParams.S_NAME));
//            resultProduct.put(AppParams.DESC, ParamUtil.getString(productMap, AppParams.S_DESC));
//            resultProduct.put(AppParams.BASE_COST, ParamUtil.getString(productMap, AppParams.S_BASE_COST));
//            resultProduct.put(AppParams.SALE_PRICE, ParamUtil.getString(productMap, AppParams.S_SALE_PRICE));
//            resultProduct.put(AppParams.CURRENCY, ParamUtil.getString(productMap, AppParams.S_CURRENCY));
//            resultProduct.put(AppParams.BACK_VIEW, ParamUtil.getString(productMap, AppParams.N_BACK_VIEW));
//            resultProduct.put(AppParams.MOCKUP_IMG_URL, ParamUtil.getString(productMap, AppParams.S_MOCKUP_IMG_URL));
//            resultProduct.put(AppParams.POSITION, ParamUtil.getString(productMap, AppParams.N_POSITION));
//            resultProduct.put(AppParams.STATE, ParamUtil.getString(productMap, AppParams.S_STATE_2));
//            resultProduct.put(AppParams.CREATE_AT, ParamUtil.getString(productMap, AppParams.D_CREATE));
//            resultProduct.put(AppParams.CAMPAIGN_ID, ParamUtil.getString(productMap, AppParams.S_CAMPAIGN_ID));
//            resultProduct.put(AppParams.DEFAULT, ParamUtil.getString(productMap, AppParams.N_DEFAULT));
//            resultProduct.put(AppParams.DEFAULT_COLOR_ID, ParamUtil.getString(productMap, AppParams.S_DEFAULT_COLOR_ID));
//            resultProduct.put(AppParams.DOMAIN, ParamUtil.getString(productMap, AppParams.S_DOMAIN));
//            resultProduct.put(AppParams.IMG_URL, ParamUtil.getString(productMap, AppParams.S_IMG_URL));
//            resultProduct.put(AppParams.DESIGN, ParamUtil.getString(productMap, AppParams.S_DESIGN_JSON));
//
//            List<Map> listColor = new LinkedList<>();
//
//            // danh sach id color co trong product
//            String color = ParamUtil.getString(productMap, AppParams.S_COLORS);
//            // list id color
//            List<String> listIdColor = Arrays.asList(color.split(","));
//            if(color != null){
//                for(String idColor : listIdColor){
//                    Map colorMap = new LinkedHashMap();
//
//                    for(Map colorMapList : colorInput) {
//                        String idColorInList = ParamUtil.getString(colorMapList, AppParams.S_ID_2);
//                        if(idColorInList.equals(idColor)) {
//                            colorMap.put(AppParams.ID, ParamUtil.getString(colorMapList, AppParams.S_ID_2));
//                            colorMap.put(AppParams.NAME, ParamUtil.getString(colorMapList, AppParams.S_NAME));
//                            colorMap.put(AppParams.VALUE, ParamUtil.getString(colorMapList, AppParams.S_VALUE));
//                            colorMap.put(AppParams.POSITION, ParamUtil.getString(colorMapList, AppParams.N_POSITION));
//                            break;
//                        }
//                    }
//                    // them color da format vao listColor
//                    listColor.add(colorMap);
//                }
//            }
//
//            resultProduct.put(AppParams.COLORS, listColor);
//            //
//            List<Map> listSize = new LinkedList<>();
//
//            // danh sach id size co trong product
//            String size = ParamUtil.getString(productMap, AppParams.S_SIZES);
//
//            String price = ParamUtil.getString(productMap, AppParams.S_SALE_PRICE);
//            // list id color
//            List<String> listIdSize = Arrays.asList(color.split(","));
//            List<String> listPrice = Arrays.asList(price.split(","));
//            int count = -1;
//            if(size != null){
//                for(String idSize : listIdSize){
//                    count++;
//                    Map sizeMap = new LinkedHashMap();
//
//                    for(Map sizeMapList : sizeInput) {
//                        String idSizeInList = ParamUtil.getString(sizeMapList, AppParams.S_ID_2);
//                        if(idSizeInList.equals(idSize)) {
//                            sizeMap.put(AppParams.ID, ParamUtil.getString(sizeMapList, AppParams.S_ID_2));
//                            sizeMap.put(AppParams.NAME, ParamUtil.getString(sizeMapList, AppParams.S_NAME));
//                            sizeMap.put(AppParams.PRICE, listPrice.get(count));
//                            sizeMap.put(AppParams.STATE, ParamUtil.getString(sizeMapList, AppParams.S_STATE_2));
//                            break;
//                        }
//                    }
//                    // them size da format vao listSize
//                    listSize.add(sizeMap);
//                }
//            }
//
//            resultProduct.put(AppParams.SIZES, listSize);
//
//            listProduct.add(resultProduct);
//        }
//
//        campaignMap.put("PRODUCTS", listProduct);
//
//
//        return campaignMap;
//    }
}

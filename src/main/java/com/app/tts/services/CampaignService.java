package com.app.tts.services;

import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

import java.sql.SQLException;
import java.util.*;

public class CampaignService extends MasterService {
    private static final String UPDATE_CAMPAIGN = "{call PKG_BQP.UPDATE_CAMPAIGN(?,?,?,?,?, ?,?,?)}";
    private static final String CREATE_CAMPAIGN = "{call PKG_DROPSHIP_ORDER_PHUONG.CREATE_CAMPAIGN(?, ?,?,?)}";
    private static final String ADD_PRODUCT = "{call PKG_DROPSHIP_ORDER_PHUONG.ADD_PRODUCT(?, ?,?, ?,?, ?,?, ?, ?,?, ?,?,?,?)}";

    public static Map addProduct(String campaignId,
                                 int defaultN, String baseId,
                                 String colorId, String defaultColor,
                                 String sizeId, String price,
                                 String mockups) throws SQLException {
        Map result = excuteQuery4OutPut(ADD_PRODUCT, new Object[]{campaignId,
                defaultN, baseId,
                colorId, defaultColor,
                sizeId, price,
                mockups});

        List<Map> listProduct = ParamUtil.getListData(result, AppParams.RESULT_DATA);
        List<Map> listCampaign = ParamUtil.getListData(result, AppParams.RESULT_DATA_2);
        List<Map> listColor = ParamUtil.getListData(result, AppParams.RESULT_DATA_3);
        List<Map> listSize = ParamUtil.getListData(result, AppParams.RESULT_DATA_4);

        Map resultMap = format(listProduct, listCampaign, listColor, listSize);//s
        return resultMap;
    }

    public static Map format(List<Map> productInput, List<Map> listCampaign, List<Map> colorInput, List<Map> sizeInput) {
        Map campaignMap = new LinkedHashMap();

        Map campaign = listCampaign.get(0);

        campaignMap.put(AppParams.ID, ParamUtil.getString(campaign, AppParams.S_ID));
        campaignMap.put(AppParams.USER_ID, ParamUtil.getString(campaign, AppParams.S_USER_ID));
        campaignMap.put(AppParams.TITLE, ParamUtil.getString(campaign, AppParams.S_TITLE));
        campaignMap.put(AppParams.DESC, ParamUtil.getString(campaign, AppParams.S_DESC));
        campaignMap.put(AppParams.CATEGORY_IDS, ParamUtil.getString(campaign, AppParams.S_CATEGORY_IDS));
        campaignMap.put(AppParams.TAGS, ParamUtil.getString(campaign, AppParams.S_TAGS));
        campaignMap.put(AppParams.START, ParamUtil.getString(campaign, AppParams.D_START));
        campaignMap.put(AppParams.END, ParamUtil.getString(campaign, AppParams.D_END));
        campaignMap.put(AppParams.RELAUNCH, ParamUtil.getString(campaign, AppParams.N_AUTO_RELAUNCH));
        campaignMap.put(AppParams.PRIVATE, ParamUtil.getString(campaign, AppParams.N_PRIVATE));
        campaignMap.put(AppParams.FB_PIXEL, ParamUtil.getString(campaign, AppParams.S_FB_PIXEL));
        campaignMap.put(AppParams.GG_PIXEL, ParamUtil.getString(campaign, AppParams.S_GG_PIXEL));
        campaignMap.put(AppParams.CREATE, ParamUtil.getString(campaign, AppParams.D_CREATE));
        campaignMap.put(AppParams.UPDATE, ParamUtil.getString(campaign, AppParams.D_UPDATE));
        campaignMap.put(AppParams.STATE, ParamUtil.getString(campaign, AppParams.S_STATE));
        campaignMap.put(AppParams.LENGTH, ParamUtil.getString(campaign, AppParams.N_LENGTH));
        campaignMap.put(AppParams.SALE_PRICE, ParamUtil.getString(campaign, AppParams.S_SALE_PRICE));
        campaignMap.put(AppParams.FAVORITE, ParamUtil.getString(campaign, AppParams.N_FAVORITE));
        campaignMap.put(AppParams.ARCHIVED, ParamUtil.getString(campaign, AppParams.N_ARCHIVED));
        campaignMap.put(AppParams.DESIGN_FRONT_URL, ParamUtil.getString(campaign, AppParams.S_DESIGN_FRONT_URL));
        campaignMap.put(AppParams.DESIGN_BACK_URL, ParamUtil.getString(campaign, AppParams.S_DESIGN_BACK_URL));
        campaignMap.put(AppParams.DOMAIN_ID, ParamUtil.getString(campaign, AppParams.S_DOMAIN_ID));
        campaignMap.put(AppParams.DOMAIN, ParamUtil.getString(campaign, AppParams.S_DOMAIN));
        campaignMap.put(AppParams.ART_IDS, ParamUtil.getString(campaign, AppParams.S_ART_IDS));
        campaignMap.put(AppParams.BASE_GROUP_ID, ParamUtil.getString(campaign, AppParams.S_BASE_GROUP_ID));
        campaignMap.put(AppParams.BACK_VIEW, ParamUtil.getString(campaign, AppParams.N_BACK_VIEW));
        campaignMap.put(AppParams.AS_TM, ParamUtil.getString(campaign, AppParams.N_AS_TM));
        campaignMap.put(AppParams.AD_TAGS, ParamUtil.getString(campaign, AppParams.S_AD_TAGS));
        campaignMap.put(AppParams.SEO_TITLE, ParamUtil.getString(campaign, AppParams.S_SEO_TITLE));
        campaignMap.put(AppParams.SEO_DESC, ParamUtil.getString(campaign, AppParams.S_SEO_DESC));
        campaignMap.put(AppParams.SEO_IMAGE_COVER, ParamUtil.getString(campaign, AppParams.S_SEO_IMAGE_COVER));
        campaignMap.put(AppParams.DESIGN_CHECK, ParamUtil.getString(campaign, AppParams.N_DESIGN_CHECK));
        campaignMap.put(AppParams.DESIGN_VERSION, ParamUtil.getString(campaign, AppParams.S_DESIGN_VERSION));
        campaignMap.put(AppParams.LEFT_CHEST, ParamUtil.getString(campaign, AppParams.N_LEFT_CHEST));
        campaignMap.put(AppParams.SUB_STATE, ParamUtil.getString(campaign, AppParams.S_SUB_STATE));
        campaignMap.put(AppParams.MODIFIED_AT, ParamUtil.getString(campaign, "MODIFIED_AT"));
        campaignMap.put(AppParams.OLD_TAGS, ParamUtil.getString(campaign, "OLD_TAGS"));

        List<Map> listProduct = new ArrayList<>();

        for (Map productMap : productInput) {
            Map resultProduct = new LinkedHashMap();

            resultProduct.put(AppParams.ID, ParamUtil.getString(productMap, AppParams.S_ID));
            String baseId = ParamUtil.getString(productMap, AppParams.S_BASE_ID);//s
            resultProduct.put(AppParams.BASE_ID, baseId);
            resultProduct.put(AppParams.NAME, ParamUtil.getString(productMap, AppParams.S_NAME));
            resultProduct.put(AppParams.DESC, ParamUtil.getString(productMap, AppParams.S_DESC));
            resultProduct.put(AppParams.BASE_COST, ParamUtil.getString(productMap, AppParams.S_BASE_COST));
            resultProduct.put(AppParams.SALE_PRICE, ParamUtil.getString(productMap, AppParams.S_SALE_PRICE));
            resultProduct.put(AppParams.CURRENCY, ParamUtil.getString(productMap, AppParams.S_CURRENCY));
            resultProduct.put(AppParams.BACK_VIEW, ParamUtil.getString(productMap, AppParams.N_BACK_VIEW));
            resultProduct.put(AppParams.MOCKUP_IMG_URL, ParamUtil.getString(productMap, AppParams.S_MOCKUP_IMG_URL));
            resultProduct.put(AppParams.POSITION, ParamUtil.getString(productMap, AppParams.N_POSITION));
            resultProduct.put(AppParams.STATE, ParamUtil.getString(productMap, AppParams.S_STATE));
            resultProduct.put(AppParams.CREATE_AT, ParamUtil.getString(productMap, AppParams.D_CREATE));
            resultProduct.put(AppParams.CAMPAIGN_ID, ParamUtil.getString(productMap, AppParams.S_CAMPAIGN_ID));
            //default chua gia tri boolean nhung dau ra de so
            resultProduct.put(AppParams.DEFAULT, ParamUtil.getInt(productMap, AppParams.N_DEFAULT));
            resultProduct.put(AppParams.DEFAULT_COLOR_ID, ParamUtil.getString(productMap, AppParams.S_DEFAULT_COLOR_ID));
            resultProduct.put(AppParams.DOMAIN, ParamUtil.getString(productMap, AppParams.S_DOMAIN));
            resultProduct.put(AppParams.IMG_URL, ParamUtil.getString(productMap, AppParams.S_IMG_URL));
            resultProduct.put(AppParams.DESIGN, ParamUtil.getString(productMap, AppParams.S_DESIGN_JSON));

            List<Map> listColor = new ArrayList<>();

            // danh sach id color co trong product
            String color = ParamUtil.getString(productMap, AppParams.S_COLORS);
            // list id color
            List<String> listIdColor = Arrays.asList(color.split(","));
            if (!color.isEmpty()) {
                for (String idColor : listIdColor) {
                    Map colorMap = new LinkedHashMap();

                    for (Map colorMapList : colorInput) {
                        String idColorInList = ParamUtil.getString(colorMapList, AppParams.S_ID);
                        if (idColorInList.equals(idColor)) {
                            colorMap.put(AppParams.ID, ParamUtil.getString(colorMapList, AppParams.S_ID));
                            colorMap.put(AppParams.NAME, ParamUtil.getString(colorMapList, AppParams.S_NAME));
                            colorMap.put(AppParams.VALUE, ParamUtil.getString(colorMapList, AppParams.S_VALUE));
                            colorMap.put(AppParams.POSITION, ParamUtil.getString(colorMapList, AppParams.N_POSITION));
                            break;
                        }
                    }
                    // them color da format vao listColor
                    if (!colorMap.isEmpty()) {
                        listColor.add(colorMap);
                    }
                }
            }

            resultProduct.put(AppParams.COLORS, listColor);
            //
            List<Map> listSize = new ArrayList<>();

            // danh sach id size co trong product
            String size = ParamUtil.getString(productMap, AppParams.S_SIZES);

            String price = ParamUtil.getString(productMap, AppParams.S_SALE_PRICE);
            // list id color
            List<String> listIdSize = Arrays.asList(size.split(","));
            List<String> listPrice = Arrays.asList(price.split(","));
            int count = -1;

            // c1
//            List<String> listIdBasePrice = new ArrayList<>();
//            for(Map sizePriceMap : sizeInput){
//                String baseIdSub = ParamUtil.getString(sizePriceMap, AppParams.S_BASE_ID);
//                listIdBasePrice.add(baseIdSub);
//            }

            ///c2
            List<Map> listDropShipPrice = sizeInput;

            if (!size.isEmpty()) {
                for (String idSize : listIdSize) {
                    count++;
                    Map sizeMap = new LinkedHashMap();

                    for (Map sizeMapList : sizeInput) {
                        String idSizeInList = ParamUtil.getString(sizeMapList, AppParams.S_ID);
                        if (idSizeInList.equals(idSize)) {
//                            String sizeSId = ParamUtil.getString(sizeMapList, AppParams.S_ID_2);//s
                            sizeMap.put(AppParams.ID, ParamUtil.getString(sizeMapList, AppParams.S_ID));
                            sizeMap.put(AppParams.NAME, ParamUtil.getString(sizeMapList, AppParams.S_NAME));
                            String priceInSize = null;
                            if (count < listPrice.size()) {
                                priceInSize = listPrice.get(count);
                            }
                            sizeMap.put(AppParams.PRICE, priceInSize);
                            sizeMap.put(AppParams.STATE, ParamUtil.getString(sizeMapList, AppParams.S_STATE));

                            //price s
                            // BASE_ID o tren cho duyet product, size_id ngay ben tren -> xac dinh duy nhat 1 Map chua gia dropship

                            String dropshipPrice = "0";
                            String secondSidePrice = "0";
                            //c1 ra ket qua sai khi cung idSize, khac idBase, k kiem tra dc idBase
//                            if(listIdBasePrice.contains(baseId)){
//                                    dropshipPrice = ParamUtil.getString(sizeMapList, AppParams.S_DROPSHIP_PRICE);
//                                    secondSidePrice = ParamUtil.getString(sizeMapList, AppParams.S_SECOND_SIDE_PRICE);
//                                }

                            //c2
                            for (Map dropShipPriceMap : listDropShipPrice) {
                                String baseIdSub = ParamUtil.getString(dropShipPriceMap, AppParams.S_BASE_ID);

                                String sizeSId = ParamUtil.getString(dropShipPriceMap, AppParams.S_ID);
                                if(baseIdSub.equals(baseId) && sizeSId.equals(idSize)){

                                    dropshipPrice = ParamUtil.getString(dropShipPriceMap, AppParams.S_DROPSHIP_PRICE);
                                    secondSidePrice = ParamUtil.getString(dropShipPriceMap, AppParams.S_SECOND_SIDE_PRICE);
                                }
                            }

                            sizeMap.put(AppParams.DROPSHIP_PRICE, dropshipPrice);
                            sizeMap.put(AppParams.SECOND_SIDE_PRICE, secondSidePrice);

                            break;
                        }
                    }
                    // them size da format vao listSize
                    if (!sizeMap.isEmpty()) {
                        listSize.add(sizeMap);
                    }
                }
            }

            resultProduct.put(AppParams.SIZES, listSize);

            listProduct.add(resultProduct);
        }

        if (!listProduct.isEmpty()) {
            campaignMap.put("PRODUCTS", listProduct);
        }


        return campaignMap;
    }

    public static Map createCampaign(String userId) throws SQLException {
        List<Map> result = excuteQuery(CREATE_CAMPAIGN, new Object[]{userId});
        Map resultMap = result.get(0);
        return resultMap;
    }

    public static List<Map> updateCampaign(String idCampaign, String title, String descIn,
                                           String designFrontUrl, String designBackUrl) throws SQLException {
        List<Map> resultMap = excuteQuery(UPDATE_CAMPAIGN, new Object[]{idCampaign, title, descIn,
                designFrontUrl, designBackUrl});

        return resultMap;
    }
}

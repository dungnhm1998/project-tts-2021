package com.app.tts.services;

import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;

public class CreateProServices extends MasterService {
    public static final String CREATE_PRODUCT = "{call PKG_QUY.create_product(?,?,?,?,?,?,?,?,?,?)}";
    public static final String GET_PRODUCT = "{call PKG_QUY.get_product(?,?,?,?)}";
    public static final String GET_COLORS = "{call PKG_QUY.get_color3(?,?,?,?)}";
    public static final String GET_SIZES = "{call PKG_QUY.get_color4(?,?,?,?)}";


    public static List<Map> createProduct(String p_campaign_id, String p_base_id, String p_colors, String p_sizes, String priceid, String p_design_json, String p_mockup_img_url) throws SQLException {

        List<Map> createProduct = excuteQuery(CREATE_PRODUCT, new Object[]{p_campaign_id, p_base_id, p_colors, p_sizes, priceid, p_design_json, p_mockup_img_url});

        LOGGER.info("Create Product" + createProduct);

        return createProduct;
    }


    public static List<Map> getPoduct(String p_id) throws SQLException {
        List<Map> get_color = excuteQuery(GET_PRODUCT, new Object[]{p_id});
        return get_color;
    }

    public static List<Map> get_color(String p_id) throws SQLException {
        List<Map> get_color3 = excuteQuery(GET_COLORS, new Object[]{p_id});
        return get_color3;
    }

    public static List<Map> get_size(String p_id) throws SQLException {
        List<Map> get_color4 = excuteQuery(GET_SIZES, new Object[]{p_id});

        return get_color4;
    }

    private static final Logger LOGGER = Logger.getLogger(CreateProductServices.class.getName());


    public static Map format(List<Map> getCampaign, List<Map> getProduct, List<Map> getColor, List<Map> getSize) {
        Map campaignMap = new LinkedHashMap();

        Map campaign = getCampaign.get(0);

        campaignMap.put(AppParams.ID, ParamUtil.getString(campaign, AppParams.S_ID_2));
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
        campaignMap.put(AppParams.STATE, ParamUtil.getString(campaign, AppParams.S_STATE_2));
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

        for (Map productMap : getProduct) {
            Map resultProduct = new LinkedHashMap();

            resultProduct.put(AppParams.ID, ParamUtil.getString(productMap, AppParams.S_ID_2));
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
            resultProduct.put(AppParams.STATE, ParamUtil.getString(productMap, AppParams.S_STATE1));
            resultProduct.put(AppParams.CREATE_AT, ParamUtil.getString(productMap, AppParams.D_CREATE));
            resultProduct.put(AppParams.CAMPAIGN_ID, ParamUtil.getString(productMap, AppParams.S_CAMPAIGN_ID));

            resultProduct.put(AppParams.DEFAULT, ParamUtil.getInt(productMap, AppParams.N_DEFAULT));
            resultProduct.put(AppParams.DEFAULT_COLOR_ID, ParamUtil.getString(productMap, AppParams.S_DEFAULT_COLOR_ID));
            resultProduct.put(AppParams.DOMAIN, ParamUtil.getString(productMap, AppParams.S_DOMAIN));
            resultProduct.put(AppParams.IMG_URL, ParamUtil.getString(productMap, AppParams.S_IMG_URL));
            resultProduct.put(AppParams.DESIGN, ParamUtil.getString(productMap, AppParams.S_DESIGN_JSON));



            List<Map> listColor = new ArrayList<>();

            // danh sach id color co trong product
            String color = ParamUtil.getString(productMap, "S_COLORS");
            // list id color
            List<String> listIdColor = Arrays.asList(color.split(","));
            if (!color.isEmpty()) {
                for (String idColor : listIdColor) {
                    Map colorMap = new LinkedHashMap();

                    for (Map colorMapList : getColor) {
                        String idColorInList = ParamUtil.getString(colorMapList, AppParams.S_COLORS);
                        if (idColorInList.equals(idColor)) {
                            colorMap.put(AppParams.ID, ParamUtil.getString(colorMapList, AppParams.S_COLORS));
                            colorMap.put(AppParams.NAME, ParamUtil.getString(colorMapList, AppParams.NAME_COLOR));
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

            List<String> listIdBasePrice = new ArrayList<>();
            for(Map sizePriceMap : getSize){
                String baseIdSub = ParamUtil.getString(sizePriceMap, AppParams.S_BASE_ID);
                listIdBasePrice.add(baseIdSub);
            }

            if (!size.isEmpty()) {
                for (String idSize : listIdSize) {
                    count++;
                    Map sizeMap = new LinkedHashMap();

                    for (Map sizeMapList : getSize) {
                        String idSizeInList = ParamUtil.getString(sizeMapList, AppParams.S_ID_2);
                        if (idSizeInList.equals(idSize)) {
//                            String sizeSId = ParamUtil.getString(sizeMapList, AppParams.S_ID_2);//s
                            sizeMap.put(AppParams.SIZES, ParamUtil.getString(sizeMapList, AppParams.S_SIZES));
                            sizeMap.put(AppParams.NAME, ParamUtil.getString(sizeMapList, "SIZE_NAME"));
                            String priceInSize = null;
                            if(count < listPrice.size()){
                                priceInSize = listPrice.get(count);
                            }
                            sizeMap.put(AppParams.PRICE, priceInSize);
                            sizeMap.put(AppParams.STATE, ParamUtil.getString(sizeMapList, AppParams.S_STATE_2));

                            //price s
                            // BASE_ID o tren cho duyet product, size_id ngay ben tren -> xac dinh duy nhat 1 Map chua gia dropship

                            String dropshipPrice = "0";
                            String secondSidePrice = "0";
                            if(listIdBasePrice.contains(baseId)){
                                dropshipPrice = ParamUtil.getString(sizeMapList, AppParams.S_DROPSHIP_PRICE);
                                secondSidePrice = ParamUtil.getString(sizeMapList, AppParams.S_SECOND_SIDE_PRICE);
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


        return  campaignMap;
    }
}

package com.app.tts.services;

import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class GetBaseService1 extends MasterService {
    public static final String GET_LIST_BASE = "{call PKG_QUY.getallbase(?,?,?)}";
    public static final String GET_LIST_COLOR = "{call PKG_QUY.get_color1(?,?,?)}";
    public static final String GET_LIST_SIZE = "{call PKG_QUY.get_size(?,?,?)}";


    public static List<Map> getBaseService() throws SQLException {
        List<Map> resultDataList = excuteQuery(GET_LIST_BASE, new Object[]{});
        return resultDataList;
    }

    public static List<Map> getBaseColor() throws SQLException {
        List<Map> resultDataList = excuteQuery(GET_LIST_COLOR, new Object[]{});
        return resultDataList;
    }

    public static List<Map> getBaseSize() throws SQLException {
        List<Map> resultDataList = excuteQuery(GET_LIST_SIZE, new Object[]{});
        return resultDataList;
    }

    public static Map format(List<Map> basAndG, List<Map> colorin, List<Map> sizein) {
        Map resultMap = new LinkedHashMap<>();


        Set<String> listBaseGroupId = new HashSet();
        for (Map baseAndGroup : basAndG) {
            //get base id
            String baseGroupId = ParamUtil.getString(baseAndGroup, AppParams.S_GROUP_ID);
            listBaseGroupId.add(baseGroupId);
        }

        for (String groupId : listBaseGroupId) {
            List<Map> listBaseGroup = new ArrayList();
            String baseGroupName = "";

            for (Map baseAndGroup : basAndG) {
                String baseGroupId = ParamUtil.getString(baseAndGroup, AppParams.S_GROUP_ID);
                Map baseAG = new LinkedHashMap();
                Map printTable = new LinkedHashMap<>();
                Map image = new LinkedHashMap<>();
                if (groupId.equals(baseGroupId)) {


                    baseAG.put(AppParams.ID, ParamUtil.getString(baseAndGroup, AppParams.S_BASE_ID));
                    baseAG.put(AppParams.TYPE_ID, ParamUtil.getString(baseAndGroup, AppParams.S_TYPE_ID));
                    baseAG.put(AppParams.NAME, ParamUtil.getString(baseAndGroup, "S_BASE_NAME"));
                    baseAG.put(AppParams.GROUP_ID, ParamUtil.getString(baseAndGroup, AppParams.S_GROUP_ID));
                    baseAG.put(AppParams.GROUP_NAME, ParamUtil.getString(baseAndGroup, AppParams.S_GROUP_NAME));

                    //printable
                    printTable.put("front_top", ParamUtil.getString(baseAndGroup, AppParams.S_PRINTABLE_FRONT_TOP));
                    printTable.put("front_left", ParamUtil.getString(baseAndGroup, AppParams.S_PRINTABLE_FRONT_LEFT));
                    printTable.put("front_width", ParamUtil.getString(baseAndGroup, AppParams.S_PRINTABLE_FRONT_WIDTH));
                    printTable.put("front_height", ParamUtil.getString(baseAndGroup, AppParams.S_PRINTABLE_FRONT_HEIGHT));
                    printTable.put("back_top", ParamUtil.getString(baseAndGroup, AppParams.S_PRINTABLE_BACK_TOP));
                    printTable.put("back_left", ParamUtil.getString(baseAndGroup, AppParams.S_PRINTABLE_BACK_LEFT));
                    printTable.put("back_width", ParamUtil.getString(baseAndGroup, AppParams.S_PRINTABLE_BACK_WIDTH));
                    printTable.put("back_height", ParamUtil.getString(baseAndGroup, AppParams.S_PRINTABLE_BACK_HEIGHT));
                    //image
                    image.put("icon_url", ParamUtil.getString(baseAndGroup, AppParams.S_ICON_IMG_URL));
                    image.put("front_url", ParamUtil.getString(baseAndGroup, AppParams.S_FRONT_IMG_URL));
                    image.put("front_width", ParamUtil.getString(baseAndGroup, AppParams.S_FRONT_IMG_WIDTH));
                    image.put("front_height", ParamUtil.getString(baseAndGroup, AppParams.S_FRONT_IMG_HEIGHT));
                    image.put("back_url", ParamUtil.getString(baseAndGroup, AppParams.S_BACK_IMG_URL));
                    image.put("back_width", ParamUtil.getString(baseAndGroup, AppParams.S_BACK_IMG_WIDTH));
                    image.put("back_height", ParamUtil.getString(baseAndGroup, AppParams.S_BACK_IMG_HEIGHT));

                    baseGroupName = ParamUtil.getString(baseAndGroup, AppParams.S_GROUP_NAME);
                    listBaseGroup.add(baseAG);
                }
                String base = ParamUtil.getString(baseAndGroup, AppParams.S_BASE_ID);
                //colors
                List<Map> lstc = new ArrayList<>();

                for (Map colr : colorin) {
                    String basecls = ParamUtil.getString(colr, "BASEID");
                    Map cls = new LinkedHashMap();
                    if (base.equals(basecls)) {
                        cls.put("id", ParamUtil.getString(colr, AppParams.S_ID));
                        cls.put("name", ParamUtil.getString(colr, AppParams.S_NAME_COLOR));
                        cls.put("value", ParamUtil.getString(colr, AppParams.S_VALUE));
                        cls.put("position", ParamUtil.getString(colr, AppParams.N_POSITION));
                        lstc.add(cls);
                    }
                }
                baseAG.put("colors", lstc);

                //sizes
                List<Map> sizes = new ArrayList<>();
                for (Map siz : sizein) {
                    Map sizs = new LinkedHashMap();
                    String basesize = ParamUtil.getString(siz, "BASEID");
                    if (base.equals(basesize)) {
                        sizs.put("id", ParamUtil.getString(siz, AppParams.SIZE_ID));
                        sizs.put("name", ParamUtil.getString(siz, AppParams.S_SIZE_NAME));
                        sizs.put("price", ParamUtil.getString(siz, AppParams.S_PRICE));
                        sizs.put("state", ParamUtil.getString(siz, AppParams.S_STATE));
                        sizs.put("dropship_price", ParamUtil.getString(siz, AppParams.S_DROPSHIP_PRICE));
                        sizs.put("second_side_price", ParamUtil.getString(siz, AppParams.S_SECOND_SIDE_PRICE));
                        sizes.add(sizs);
                    }

                }
                baseAG.put("sizes", sizes);

                baseAG.put(AppParams.IMAGE, image);
                baseAG.put(AppParams.PRINTABLE, printTable);
            }
            resultMap.put(baseGroupName, listBaseGroup);

        }
        return resultMap;

    }


    private static final Logger LOGGER = Logger.getLogger(GetBaseService1.class.getName());
}

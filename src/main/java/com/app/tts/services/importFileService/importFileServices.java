package com.app.tts.services.importFileService;

import com.app.tts.services.MasterService;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class importFileServices extends MasterService {
    public static final String IMPORT_FILE_ROWS = "{call PKG_IMPORT_FILE.IMPORT_ORDER(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

    public List<Map>  importFileRows(
         String  reference_order,String  user_id,String  file_name,String  email,String  financial_status,
         Date    create,String  storeid,String  lineitem_quantity,String  lineitem_name,String  lineitem_sku,
         String  shipping_name,String  shipping_street, String  shipping_address2,String  shipping_company,
         String  shipping_city,String  shipping_zip, String  shipping_province,String  shipping_country,
         String  shipping_phone,String  shipping_method,String  note,String  id,String  design_front_url,
         String  design_back_url,String  mockup_front_url,String  mockup_back_url,String  by_pass_check_adress,
         String  currency,String  unit_amount,String  fulfillment_location
    ) throws SQLException {

        List<Map> importFile = excuteQuery(IMPORT_FILE_ROWS, new Object[]{
                reference_order,user_id,file_name,email,
                financial_status,create,storeid,lineitem_quantity,
                lineitem_name,lineitem_sku,
                shipping_name,shipping_street,
                shipping_address2,shipping_company,shipping_city,
                shipping_zip,shipping_province,
                shipping_country,
                shipping_phone,
                shipping_method,
                note,
                id,
                design_front_url,
                design_back_url,
                mockup_front_url,
                mockup_back_url,
                by_pass_check_adress,
                currency,
                unit_amount,
                fulfillment_location
        });

        return importFile;
    }
}

























                                
                                
                                
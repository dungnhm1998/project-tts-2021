package com.app.tts.server.handler.importFile;

import com.app.tts.encode.Md5Code;
import com.app.tts.server.handler.importFile.utilGoogle.DownloadFile;
import com.app.tts.services.importFileService.AddOrderServiceImport;
import com.app.tts.util.ParamUtil;
import org.apache.commons.io.FilenameUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

public class JobA extends QuartzJobBean {


    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {

            Map getFile = AddOrderServiceImport.getFile1();
            String id = ParamUtil.getString(getFile, "S_ID");
            String url = ParamUtil.getString(getFile, "S_URL");
            String userId = ParamUtil.getString(getFile, "S_USER_ID");
            String storeId = ParamUtil.getString(getFile, "S_STORE_ID");

            String prefix = id + "-" + "quy.csv";
            String file_name = "D://react//jarr/" + prefix;
            String type = FilenameUtils.getExtension(file_name);

            System.out.println("---------");
            System.out.println(getFile);

            DownloadFile.Download(url, file_name);

            Readfile.readFile(file_name);
            Random rand = new Random();



            List<Map> lst = Readfile.listMapData;
            Files.deleteIfExists(Paths.get(file_name));
            for (Map s : lst) {
                String ord = String.valueOf(rand.nextInt(100000));
                String name = ParamUtil.getString(s, "Name");
                String email = ParamUtil.getString(s, "Email");
                String financialStatus = ParamUtil.getString(s, "Financial Status");
                String state = "created";
                String lineitemQuantity = ParamUtil.getString(s, "Lineitem quantity");
                String lineitemName = ParamUtil.getString(s, "Lineitem name");
                String lineitemSku = ParamUtil.getString(s, "Lineitem sku");
                String shippingName = ParamUtil.getString(s, "Shipping Name");
                String shippingStreet = ParamUtil.getString(s, "Shipping Street");
                String shippingAddress1 = ParamUtil.getString(s, "Shipping Address1");
                String shippingAddress2 = ParamUtil.getString(s, "Shipping Address2");
                String shippingCompany = ParamUtil.getString(s, "Shipping Company");
                String shippingCity = ParamUtil.getString(s, "Shipping City");
                String shippingZip = ParamUtil.getString(s, "Shipping Zip");
                String shippingProvince = ParamUtil.getString(s, "Shipping Province");
                String shippingCountry = ParamUtil.getString(s, "Shipping Country");
                String shippingPhone = ParamUtil.getString(s, "Shipping Phone");
                String shippingMethod = ParamUtil.getString(s, "Shipping method");
                String notes = ParamUtil.getString(s, "Notes");
                String designFrontUrl = ParamUtil.getString(s, "Design front url");
                String designBackUrl = ParamUtil.getString(s, "Design back url");
                String mockupFrontUrl = ParamUtil.getString(s, "Mockup front url");
                String mockupBackUrl = ParamUtil.getString(s, "Design back url");
                String checkValidAddress = ParamUtil.getString(s, "Check vaild adress");
                String currency = ParamUtil.getString(s, "Currency");
                String unitAmount = ParamUtil.getString(s, "Unit amount");
                String location = ParamUtil.getString(s, "Location");
                String groupColumn = Md5Code.md5(id + storeId + userId + name);
                AddOrderServiceImport.importFileRows(name, id, userId, prefix, email, financialStatus, state, groupColumn, type,
                        storeId, lineitemQuantity, lineitemName, lineitemSku, shippingName, shippingStreet, shippingAddress1, shippingAddress2, shippingCompany, shippingCity,
                        shippingZip, shippingProvince, shippingCountry, shippingPhone, shippingMethod, notes,
                        ord, designFrontUrl, designBackUrl, mockupFrontUrl, mockupBackUrl,
                        checkValidAddress, currency, unitAmount, location);

                AddOrderServiceImport.updateFile1("done", id);

                System.out.println("map" + s);
            }
            lst.clear();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e2) {
            LOGGER.info("=> e2" + ": " + e2.getMessage());
            e2.printStackTrace();
        } catch (Exception e1) {
            LOGGER.info("************************" + " " + e1.getMessage() + " " + "************************");
        }
    }
    private static final Logger LOGGER = Logger.getLogger(JobA.class.getName());
}


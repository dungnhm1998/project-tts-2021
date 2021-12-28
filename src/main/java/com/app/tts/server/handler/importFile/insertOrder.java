package com.app.tts.server.handler.importFile;

import com.app.tts.server.handler.importFile.utilGoogle.DownloadFile;
import com.app.tts.server.handler.leagen.getBaseHandler;
import com.app.tts.services.importFileService.AddOrderServiceImport;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

public class insertOrder implements Handler<RoutingContext> {
    private static int count = 0;
    private static List<CSVRecord> listData = new LinkedList();
    private static List<Map> listMapData = new LinkedList<>();
    public static List<String> nameColumnList = new LinkedList<>();
    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.vertx().executeBlocking(future -> {
            try {

                Map getFile = AddOrderServiceImport.getFile1();
                String id = ParamUtil.getString(getFile, "S_ID");
                String url = ParamUtil.getString(getFile, "S_URL");
                String type = ParamUtil.getString(getFile, "S_TYPE");
                String userId = ParamUtil.getString(getFile, "S_USER_ID");
                String storeId = ParamUtil.getString(getFile, "S_STORE_ID");
                String file_name = "D://react//jarr" + userId;

                DownloadFile.Download(url, file_name);


                try (
                        Reader reader = new FileReader(file_name);

                        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);

                ) {
                    Iterable<CSVRecord> csvRecords = csvParser.getRecords();

                    for (CSVRecord csvRecord : csvRecords) {
                        listData.add(csvRecord);
                    }

                    CSVRecord columnName = listData.get(0);
                    int countEmptyNameColumn = 0;

                    for (String key : columnName) {
                        if (key.equals("")) {
                            countEmptyNameColumn++;
                        }
                        // loai bo het dau cach thua; trim() loai bo dau cach dau, cuoi
                        nameColumnList.add(key.replaceAll("\\s\\s+", " ").trim());
                    }
                    System.out.println("co " + countEmptyNameColumn + " cot khong co ten cot");

                    List<String> nameColumnOutput = nameColumnOutputA2075();


                    boolean checkFullColumn = true;
                    for (String nameColumn : nameColumnOutput) {
                        if (!nameColumnList.contains(nameColumn)) {
                            System.out.println("thieu cot: " + nameColumn);
                            checkFullColumn = false;
                        }
                    }
                    if (checkFullColumn) {
                        for (int number = 1; number < listData.size(); number++) {
                            CSVRecord csvRecord = listData.get(number);
                            Map<String, String> mapOneLine = new LinkedHashMap<>();
                            int countColumn = 0;
                            for (String key : nameColumnList) { //columnName) {
                                // them vao map key, value tuong ung
                                mapOneLine.put(key, csvRecord.get(countColumn));
                                countColumn++;
                            }
                            listMapData.add(mapOneLine);
                        }
                    }
                    System.out.println("---------------loading...");


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                Map data = new HashMap();
                List<Map> importFile = new LinkedList<>();
                Random rand = new Random();

                for (Map s : listMapData) {
                    String ord = String.valueOf(rand.nextInt(100000));
                    String name = ParamUtil.getString(s, "Name");
                    String email = ParamUtil.getString(s, "Email");
                    String financialStatus = ParamUtil.getString(s, "Financial Status");
                    String dateAt = ParamUtil.getString(s, "Created at");
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                    Date strDate = dateFormat.parse(dateAt);
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

                    importFile = AddOrderServiceImport.importFileRows(name, id, userId, file_name, email, financialStatus, strDate, state,
                            storeId, lineitemQuantity, lineitemName, lineitemSku, shippingName, shippingStreet, shippingAddress1, shippingAddress2, shippingCompany, shippingCity,
                            shippingZip, shippingProvince, shippingCountry, shippingPhone, shippingMethod, notes,
                            ord, designFrontUrl, designBackUrl, mockupFrontUrl, mockupBackUrl,
                            checkValidAddress, currency, unitAmount, location);
                    String stateFile = "done";
                    AddOrderServiceImport.updateFile1("done", id);

                    System.out.println("map" + s);
                }


                data.put("import file rows", "success");


                routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.OK.code());
                routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.OK.reasonPhrase());
                routingContext.put(AppParams.RESPONSE_DATA, getFile);
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

    private static final Logger LOGGER = Logger.getLogger(getBaseHandler.class.getName());

    public static List<String> nameColumnOutputA2075() {
        List<String> nameColumnOutput = new LinkedList<>();
        nameColumnOutput.add("Name");
        nameColumnOutput.add("Email");
        nameColumnOutput.add("Financial Status");
        nameColumnOutput.add("Paid at");
        nameColumnOutput.add("Created at");
        nameColumnOutput.add("Lineitem quantity");
        nameColumnOutput.add("Lineitem name");
        nameColumnOutput.add("Lineitem sku");
        nameColumnOutput.add("Shipping Name");
        nameColumnOutput.add("Shipping Street");
        nameColumnOutput.add("Shipping Address1");
        nameColumnOutput.add("Shipping Address2");
        nameColumnOutput.add("Shipping Company");
        nameColumnOutput.add("Shipping City");
        nameColumnOutput.add("Shipping Zip");
        nameColumnOutput.add("Shipping Province");
        nameColumnOutput.add("Shipping Country");
        nameColumnOutput.add("Shipping Phone");
        nameColumnOutput.add("Notes");
        nameColumnOutput.add("Id");
        nameColumnOutput.add("Design front url");
        nameColumnOutput.add("Design back url");
        nameColumnOutput.add("Mockup front url");
        nameColumnOutput.add("Mockup back url");
        nameColumnOutput.add("Check vaild adress");
        nameColumnOutput.add("Currency");
        nameColumnOutput.add("Unit amount");
        nameColumnOutput.add("Location");
        return nameColumnOutput;
    }
}


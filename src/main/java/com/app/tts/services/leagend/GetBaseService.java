package com.app.tts.services.leagend;

import com.app.tts.services.MasterService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetBaseService extends MasterService {

    public JSONArray bbJsonArray;
    public JSONObject bbJsonObject;


//    public static String createAnnotations(String boxname) throws UnirestException {
////
////        Map<String, String> headers = new HashMap<>();
////
////        try {
////
//////            HttpResponse<JsonNode> jsonResponse = Unirest.post("https://pro.30usd.com/pspfulfill/api/v1/dropship-api/order/v2")
//////                    .header("accept", "application/json")
//////                    .body()
//////                    .asJson();
//////
//////            JSONObject output = jsonResponse.getBody().getObject();
////
//////            String status = output.getString("status");
//////            System.out.println(jsonResponse.getBody());
//////            return status;
////
////        } catch (UnirestException e) {
////            return "error";
////        }
//    }
}

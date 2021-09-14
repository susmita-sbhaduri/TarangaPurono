/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bhaduri.fractalwave;

/**
 *
 * @author susmita
 * https://stackoverflow.com/questions/47164316/parsing-string-array-from-json-object
 * https://stackoverflow.com/questions/15951032/jsonobject-classnotfoundexception
 * https://stackoverflow.com/questions/2702980/java-loop-every-minute
 */
import java.io.IOException;
import java.security.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

public class PerMinuteResposeOfNSE {

    public void rapidAPIRestCall() {
        HttpGet request = new HttpGet("https://nse-data1.p.rapidapi.com/nifty_fifty_indices_data");
        // add request headers
        request.addHeader("x-rapidapi-key", "7f737cfca9msh84109244b6b532ap1d491ejsne997fa69f709");
        request.addHeader("x-rapidapi-host", "nse-data1.p.rapidapi.com");

        // TODO code application logic here
        CloseableHttpClient httpClient = HttpClients.createDefault();
        int count = 0;
        try {
            while (count < 1) {
                CloseableHttpResponse response = httpClient.execute(request);

                // Get HttpResponse Status
                System.out.println(response.getProtocolVersion());              // HTTP/1.1
                System.out.println(response.getStatusLine().getStatusCode());   // 200
                System.out.println(response.getStatusLine().getReasonPhrase()); // OK
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // return it as a String
                    String n50Resp = EntityUtils.toString(entity);

                    JSONObject perMinResp = new JSONObject(n50Resp);
 //                   System.out.println(resultObject.get("body"));
                    JSONObject bodyJsonObj = perMinResp.getJSONObject("body");
 //                   JSONArray tempArray = resultObject.getJSONArray("body");
 //                   System.out.println(tempArray);
                    JSONArray dataArray = bodyJsonObj.getJSONArray("data");
                    for (int i = 0; i < dataArray.length(); i++) {
                                                
                        JSONObject scripObj = dataArray.getJSONObject(i);                        
                        ScripData scripData = loadScripData(scripObj);
                        
                        System.out.println(scripData.toString());
                        
  //                      System.out.println(object.get("symbol"));
                    }
  //                System.out.println(n50Resp);
                }
                Thread.sleep(60 * 1000);
                count = count + 1;
            }
        } catch (IOException e) {
            System.out.println("IOException has occurred");
        } catch (InterruptedException ex) {
            Logger.getLogger(PerMinuteResposeOfNSE.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (JSONException ex) {
            Logger.getLogger(PerMinuteResposeOfNSE.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private ScripData loadScripData(JSONObject scripObj) {
        ScripData scripData = new ScripData();
        try {
            
            
            String sym = scripObj.get("identifier").toString();
            scripData.setScripId(sym);
            
            Double openPrice = (Double) scripObj.get("open");
            scripData.setOpenPrice(openPrice);
            
            Double dayHighPrice = (Double) scripObj.get("dayHigh");
            scripData.setDayHighPrice(dayHighPrice);
            
            Double dayLowPrice = (Double) scripObj.get("dayLow");
            scripData.setDayLowPrice(dayLowPrice);
            
            Integer dayLastPrice = (Integer) scripObj.get("lastPrice");
            scripData.setDayLastPrice(dayLastPrice);
            
            Double prevClosePrice = (Double) scripObj.get("previousClose");
            scripData.setPrevClosePrice(prevClosePrice);
            
            Integer totalTradedVolume = (Integer) scripObj.get("totalTradedVolume");
            scripData.setTotalTradedVolume(totalTradedVolume);
            
            String lastUpdateTime = scripObj.get("lastUpdateTime").toString();
            scripData.setLastUpdateTime(lastUpdateTime);
            
            
        } catch (JSONException ex) {
            Logger.getLogger(PerMinuteResposeOfNSE.class.getName()).log(Level.SEVERE, null, ex);
        }
        return scripData;

    }

}

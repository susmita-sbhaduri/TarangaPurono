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
 * https://stackoverflow.com/questions/20212608/how-to-display-only-current-time-using-dateformat-getdatetimeinstance-in-andro
 */
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import  java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.bhaduri.tarangadb.DA.MinutedataDA;
import org.bhaduri.tarangadb.JPA.exceptions.PreexistingEntityException;
import org.bhaduri.tarangadb.entities.*;

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
        String startTimeStr = "09:16:00";
        Date startDateTime;
        try {
            startDateTime = new SimpleDateFormat("HH:mm:ss").parse(startTimeStr);
            SimpleDateFormat finalSdf = new SimpleDateFormat("hh:mm a");
            System.out.println("current time " + finalSdf.format(startDateTime));
        } catch (ParseException ex) {
            Logger.getLogger(PerMinuteResposeOfNSE.class.getName()).log(Level.SEVERE, null, ex);
        }

        

        int count = 0;
        try {
            while (count < 2) {
                CloseableHttpResponse response = httpClient.execute(request);

                // Get HttpResponse Status
//                System.out.println(response.getProtocolVersion());              // HTTP/1.1
//                System.out.println(response.getStatusLine().getStatusCode());   // 200
//                System.out.println(response.getStatusLine().getReasonPhrase()); // OK
//                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK
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
//                          saveSripData(scripData);
//                        System.out.println("symbol" + scripData.getScripId());
//                        System.out.println("open" + scripData.getOpenPrice());
//                        System.out.println("day high" + scripData.getDayHighPrice());
//                        System.out.println("day low" + scripData.getDayLowPrice());
//                        System.out.println("day current" + scripData.getDayLastPrice());
//                        System.out.println("prev close" + scripData.getPrevClosePrice());
//                        System.out.println("trade volume" + scripData.getTotalTradedVolume());
//                        System.out.println("last update" + scripData.getLastUpdateTime());
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
        } catch (JSONException ex) {
            Logger.getLogger(PerMinuteResposeOfNSE.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ScripData loadScripData(JSONObject scripObj) {
        ScripData scripData = new ScripData();
        try {

            String sym = scripObj.get("identifier").toString();
            scripData.setScripId(sym);

            Double openPrice = Double.valueOf(scripObj.get("open").toString());
            scripData.setOpenPrice(openPrice);

            Double dayHighPrice = Double.valueOf(scripObj.get("dayHigh").toString());
            scripData.setDayHighPrice(dayHighPrice);

            Double dayLowPrice = Double.valueOf(scripObj.get("dayLow").toString());
            scripData.setDayLowPrice(dayLowPrice);

            Double dayLastPrice = Double.valueOf(scripObj.get("lastPrice").toString());
            scripData.setDayLastPrice(dayLastPrice);

            Double prevClosePrice = Double.valueOf(scripObj.get("previousClose").toString());
            scripData.setPrevClosePrice(prevClosePrice);

            Double totalTradedVolume = Double.valueOf(scripObj.get("totalTradedVolume").toString());
            scripData.setTotalTradedVolume(totalTradedVolume);

            SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
            Date lastUpdateTime = inputDateFormat.parse(scripObj.get("lastUpdateTime").toString());

            SimpleDateFormat tempDateFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            String tempOutput = tempDateFormatter.format(lastUpdateTime);
            Date finalDate = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(tempOutput);

            scripData.setLastUpdateTime(finalDate);

        } catch (JSONException ex) {
            Logger.getLogger(PerMinuteResposeOfNSE.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(PerMinuteResposeOfNSE.class.getName()).log(Level.SEVERE, null, ex);
        }
        return scripData;

    }

    private void saveSripData(ScripData scripData) {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("org.bhaduri_Taranga_jar_1.0-SNAPSHOTPU");
            MinutedataDA minutedataDA = new MinutedataDA(emf);

            MinutedataPK minDataPk = new MinutedataPK();
            Minutedata minDataRecord = new Minutedata();

            minDataPk.setScripid(scripData.getScripId());
            minDataPk.setLastupdateminute(scripData.getLastUpdateTime());

            minDataRecord.setMinutedataPK(minDataPk);
            minDataRecord.setDayhighprice(scripData.getDayHighPrice());
            minDataRecord.setDaylastprice(scripData.getDayLastPrice());
            minDataRecord.setDaylowprice(scripData.getDayLowPrice());
            minDataRecord.setOpenprice(scripData.getOpenPrice());
            minDataRecord.setPrevcloseprice(scripData.getPrevClosePrice());
            minDataRecord.setTotaltradedvolume(scripData.getTotalTradedVolume());

            minutedataDA.create(minDataRecord);
        } catch (PreexistingEntityException preexistingEntityException) {
            System.out.println("data exists"+scripData.getScripId()+scripData.getLastUpdateTime());
        }
        catch (Exception exception) {
            Logger.getLogger(PerMinuteResposeOfNSE.class.getName()).log(Level.SEVERE, null, exception);
        }
    }
}

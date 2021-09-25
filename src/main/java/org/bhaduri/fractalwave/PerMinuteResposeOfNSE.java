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
 * http://tutorials.jenkov.com/java-internationalization/simpledateformat.html
 * -->java with milliseconds
 * https://stackoverflow.com/questions/13344994/mysql-5-6-datetime-doesnt-accept-milliseconds-microseconds
 * -->mySQL
 * https://www.geeksforgeeks.org/localtime-compareto-method-in-java-with-examples/
 * -->comparing java LocalTimes
 */
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalTime;
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

import org.bhaduri.taranga.service.*;
//import org.bhaduri.taranga.service.MasterDataService;
//import org.bhaduri.tarangadb.DA.MinutedataDA;
//import org.bhaduri.tarangadb.JPA.exceptions.PreexistingEntityException;
//import org.bhaduri.tarangadb.entities.*;

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
        //date update for scheduling

        LocalTime startTime = LocalTime.parse("09:15:00"); //Hour-1:24, min, sec       

        SimpleDateFormat formatCurrent = new SimpleDateFormat("HH:mm:ss");
        String strResult = formatCurrent.format(new Date());
        LocalTime currentTime = LocalTime.parse(strResult);

        LocalTime endTime = LocalTime.parse("15:30:00"); //Hour-1:24, min, sec
//        LocalTime endTime = LocalTime.parse("18:31:00"); //Hour-1:24, min, sec

        int flag = 0;

        if (currentTime.compareTo(startTime) > 0 && endTime.compareTo(currentTime) > 0) {
            flag = 1;
            System.out.println("startTime 1" + startTime);
            System.out.println("Time 1= " + currentTime);
            System.out.println("endTime 1" + endTime);
        }
        int count = 0;
        
//        while (flag == 1) {
            while (count < 5) {
            CloseableHttpResponse response = null;
            try {
                response = httpClient.execute(request);
            } catch (IOException ex) {
                System.out.println("IOException with response"+response);
//                Logger.getLogger(PerMinuteResposeOfNSE.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Get HttpResponse Status
//                System.out.println(response.getProtocolVersion());              // HTTP/1.1
//                System.out.println(response.getStatusLine().getStatusCode());   // 200
//                System.out.println(response.getStatusLine().getReasonPhrase()); // OK
//                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // return it as a String
                String n50Resp = "";
                try {
                    n50Resp = EntityUtils.toString(entity);
                } catch (IOException ex) {
                    System.out.println("IOException with n50Resp"+n50Resp);
//                    Logger.getLogger(PerMinuteResposeOfNSE.class.getName()).log(Level.SEVERE, null, ex);
                } catch (org.apache.http.ParseException ex) {
                    System.out.println("ParseException with n50Resp"+n50Resp);
//                    Logger.getLogger(PerMinuteResposeOfNSE.class.getName()).log(Level.SEVERE, null, ex);
                }
                catch (Exception exception) {
                    System.out.println(exception + " has occurred in n50Resp."+n50Resp);
                }
                JSONObject perMinResp = null;
                try {
                    perMinResp = new JSONObject(n50Resp);
                } catch (JSONException ex) {
                    System.out.println("Problem with perMinResp"+perMinResp);
//                    Logger.getLogger(PerMinuteResposeOfNSE.class.getName()).log(Level.SEVERE, null, ex);
                }catch (Exception exception) {
                    System.out.println(exception + " has occurred in perMinResp."+perMinResp);
                }
                //                   System.out.println(resultObject.get("body"));
                JSONObject bodyJsonObj = new JSONObject();
                try {
                    bodyJsonObj = perMinResp.getJSONObject("body");
                } catch (JSONException ex) {
                    System.out.println("Problem with bodyJsonObj"+bodyJsonObj);
//                    Logger.getLogger(PerMinuteResposeOfNSE.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception exception) {
                    System.out.println(exception + " has occurred in bodyJsonObj."+bodyJsonObj);
                }
                //                   JSONArray tempArray = resultObject.getJSONArray("body");
                //                   System.out.println(tempArray);
                JSONArray dataArray = null;
                try {
                    dataArray = bodyJsonObj.getJSONArray("data");
                } catch (JSONException ex) {
                    System.out.println("Problem with dataArray"+dataArray); 
//                    Logger.getLogger(PerMinuteResposeOfNSE.class.getName()).log(Level.SEVERE, null, ex);
                }catch (Exception exception) {
                    System.out.println(exception + " has occurred in dataArray."+dataArray);
                }

                SimpleDateFormat inputCurrentDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                String strLastUpdTime = inputCurrentDateFormat.format(new Date());
                Date lastUpdateTime = null;
                try {
                    lastUpdateTime = inputCurrentDateFormat.parse(strLastUpdTime);
                } catch (ParseException ex) {
                    System.out.println("ParseException with lastUpdateTime"+lastUpdateTime);
//                    Logger.getLogger(PerMinuteResposeOfNSE.class.getName()).log(Level.SEVERE, null, ex);
                }
                for (int i = 0; i < dataArray.length(); i++) {

                    JSONObject scripObj = null;
                    try {
                        scripObj = dataArray.getJSONObject(i);
                    } catch (JSONException ex) {
                        System.out.println("Problem with scripObj"+scripObj); 
//                        Logger.getLogger(PerMinuteResposeOfNSE.class.getName()).log(Level.SEVERE, null, ex);
                    }catch (Exception exception) {
                        System.out.println(exception + " has occurred in scripObj."+scripObj);
                    }

                    ScripData scripData = loadScripData(scripObj, lastUpdateTime);
                    MasterDataService masterDataService = new MasterDataService();
                    masterDataService.insertIntoMinutedata(scripData);
//                    saveSripData(scripData);
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
            try {
                Thread.sleep(2 * 1000); //seconds * mulliseconds
            } catch (InterruptedException ex) {
                System.out.println("InterruptedException with entity"+entity);
//                Logger.getLogger(PerMinuteResposeOfNSE.class.getName()).log(Level.SEVERE, null, ex);
            }
            count = count + 1;

            strResult = formatCurrent.format(new Date());
            currentTime = LocalTime.parse(strResult);
            if (currentTime.compareTo(startTime) < 0 || endTime.compareTo(currentTime) < 0) {
                flag = 0;
                System.out.println("startTime 0" + startTime);
                System.out.println("Time 0= " + currentTime);
                System.out.println("endTime 0" + endTime);
            }
        }
    }
        
    

    

    private ScripData loadScripData(JSONObject scripObj, Date lastUpdateTime) {
        ScripData scripData = new ScripData();
        try {

            String sym = scripObj.get("symbol").toString();
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

//            SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
//            Date lastUpdateTime = inputDateFormat.parse(scripObj.get("lastUpdateTime").toString());
//
//            SimpleDateFormat tempDateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//            String tempOutput = tempDateFormatter.format(lastUpdateTime);
//            Date finalDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(tempOutput);
            scripData.setLastUpdateTime(lastUpdateTime);

        } catch (JSONException ex) {
            System.out.println("JSONException has occurred in loadScripData");
        } 
        return scripData;

    }

//    private void saveSripData(ScripData scripData) {
//        try {
//            EntityManagerFactory emf = Persistence.createEntityManagerFactory("org.bhaduri_Taranga_jar_1.0-SNAPSHOTPU");
//            MinutedataDA minutedataDA = new MinutedataDA(emf);
//
//            MinutedataPK minDataPk = new MinutedataPK();
//            Minutedata minDataRecord = new Minutedata();
//
//            minDataPk.setScripid(scripData.getScripId());
//            minDataPk.setLastupdateminute(scripData.getLastUpdateTime());
//
//            minDataRecord.setMinutedataPK(minDataPk);
//            minDataRecord.setDayhighprice(scripData.getDayHighPrice());
//            minDataRecord.setDaylastprice(scripData.getDayLastPrice());
//            minDataRecord.setDaylowprice(scripData.getDayLowPrice());
//            minDataRecord.setOpenprice(scripData.getOpenPrice());
//            minDataRecord.setPrevcloseprice(scripData.getPrevClosePrice());
//            minDataRecord.setTotaltradedvolume(scripData.getTotalTradedVolume());
//
//            minutedataDA.create(minDataRecord);
//        } catch (PreexistingEntityException preexistingEntityException) {
//            System.out.println("data exists" + scripData.getScripId() + scripData.getLastUpdateTime());
//        } catch (Exception exception) {
//            System.out.println(exception + " has occurred in saveSripData.");
//        }
//    }
}

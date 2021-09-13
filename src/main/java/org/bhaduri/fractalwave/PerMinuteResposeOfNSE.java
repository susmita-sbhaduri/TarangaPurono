/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bhaduri.fractalwave;

/**
 *
 * @author susmita
 */
import java.io.IOException;
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
                    String result = EntityUtils.toString(entity);
//                    String[] tokens = result.split(",");
//
//                        System.out.println(tokens);
                    JSONObject resultObject = new JSONObject(result);
                    
                    JSONObject bodyObject = new JSONObject(resultObject.getJSONObject("body"));
                    System.out.println(bodyObject.get("data"));
                    JSONArray dataArray = bodyObject.getJSONArray("data");
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject object = dataArray.getJSONObject(i);
                        System.out.println(object.getString("symbol"));
                    }
                  System.out.println(result);
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

}

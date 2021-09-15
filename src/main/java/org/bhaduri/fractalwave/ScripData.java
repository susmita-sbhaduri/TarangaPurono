/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bhaduri.fractalwave;

import java.util.Date;

/**
 *
 * @author susmita
 */
public class ScripData {
    private String scripId;
    private Date lastUpdateTime;
    private Double openPrice; 
    private Double dayLastPrice;
    private Double dayHighPrice;
    private Double dayLowPrice;   
    private Double prevClosePrice;
    private Double totalTradedVolume;
    
    public String getScripId() {
        return scripId;
    }

    public void setScripId(String scripId) {
        this.scripId = scripId;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Double getDayLastPrice() {
        return dayLastPrice;
    }

    public void setDayLastPrice(Double dayLastPrice) {
        this.dayLastPrice = dayLastPrice;
    }

    public Double getTotalTradedVolume() {
        return totalTradedVolume;
    }

    public void setTotalTradedVolume(Double totalTradedVolume) {
        this.totalTradedVolume = totalTradedVolume;
    }

    public Double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(Double openPrice) {
        this.openPrice = openPrice;
    }

    public Double getDayHighPrice() {
        return dayHighPrice;
    }

    public void setDayHighPrice(Double dayHighPrice) {
        this.dayHighPrice = dayHighPrice;
    }

    public Double getDayLowPrice() {
        return dayLowPrice;
    }

    public void setDayLowPrice(Double dayLowPrice) {
        this.dayLowPrice = dayLowPrice;
    }

    public Double getPrevClosePrice() {
        return prevClosePrice;
    }

    public void setPrevClosePrice(Double prevClosePrice) {
        this.prevClosePrice = prevClosePrice;
    }

   

   
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bhaduri.taranga.service;

import org.bhaduri.tarangadb.DA.MinutedataDA;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.bhaduri.fractalwave.ScripData;
import org.bhaduri.tarangadb.JPA.exceptions.PreexistingEntityException;
import org.bhaduri.tarangadb.entities.Minutedata;
import org.bhaduri.tarangadb.entities.MinutedataPK;

/**
 *
 * @author susmita
 */
public class MasterDataService {
    private EntityManagerFactory emf;
    
    public MasterDataService() {        
           emf = Persistence.createEntityManagerFactory("org.bhaduri_Taranga_jar_1.0-SNAPSHOTPU");        
    }
    
    public void insertIntoMinutedata(ScripData scripData) {
        MinutedataDA minutedataDA = new MinutedataDA(emf);
        try {
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
//            return HedwigResponseCode.SUCCESS;
        }  catch (PreexistingEntityException preexistingEntityException) {
            System.out.println("data exists" + scripData.getScripId() + scripData.getLastUpdateTime());
        } catch (Exception exception) {
            System.out.println(exception + " has occurred in saveSripData.");
        }
    }
    
//     public void getDvripID(String scriptSym) {
//        MinutedataDA minutedataDA = new MinutedataDA(emf);
//        try {
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
////            return HedwigResponseCode.SUCCESS;
//        }  catch (PreexistingEntityException preexistingEntityException) {
//            System.out.println("data exists" + scripData.getScripId() + scripData.getLastUpdateTime());
//        } catch (Exception exception) {
//            System.out.println(exception + " has occurred in saveSripData.");
//        }
//    }
}

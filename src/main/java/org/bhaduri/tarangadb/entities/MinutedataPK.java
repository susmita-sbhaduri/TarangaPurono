/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bhaduri.tarangadb.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author susmita
 */
@Embeddable
public class MinutedataPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "scripid")
    private String scripid;
    @Basic(optional = false)
    @Column(name = "lastupdateminute")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastupdateminute;

    public MinutedataPK() {
    }

    public MinutedataPK(String scripid, Date lastupdateminute) {
        this.scripid = scripid;
        this.lastupdateminute = lastupdateminute;
    }

    public String getScripid() {
        return scripid;
    }

    public void setScripid(String scripid) {
        this.scripid = scripid;
    }

    public Date getLastupdateminute() {
        return lastupdateminute;
    }

    public void setLastupdateminute(Date lastupdateminute) {
        this.lastupdateminute = lastupdateminute;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (scripid != null ? scripid.hashCode() : 0);
        hash += (lastupdateminute != null ? lastupdateminute.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MinutedataPK)) {
            return false;
        }
        MinutedataPK other = (MinutedataPK) object;
        if ((this.scripid == null && other.scripid != null) || (this.scripid != null && !this.scripid.equals(other.scripid))) {
            return false;
        }
        if ((this.lastupdateminute == null && other.lastupdateminute != null) || (this.lastupdateminute != null && !this.lastupdateminute.equals(other.lastupdateminute))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bhaduri.tarangadb.entities.MinutedataPK[ scripid=" + scripid + ", lastupdateminute=" + lastupdateminute + " ]";
    }
    
}

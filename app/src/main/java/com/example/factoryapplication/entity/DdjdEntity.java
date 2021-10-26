package com.example.factoryapplication.entity;

import java.util.List;

public class DdjdEntity {

 private String   ddbh                                      ;
 private String           cpbh                              ;
 private String   cpmc                                      ;
 private String           cpth                              ;
 private String   cpcz                                      ;
 private String  jhrq,pcsl ,cptp;
 private List<departmentProgressList> departmentProgressList            ;

    public String getCptp() {
        return cptp;
    }

    public void setCptp(String cptp) {
        this.cptp = cptp;
    }

    public String getJhrq() {
        return jhrq;
    }

    public void setJhrq(String jhrq) {
        this.jhrq = jhrq;
    }

    public String getPcsl() {
        return pcsl;
    }

    public void setPcsl(String pcsl) {
        this.pcsl = pcsl;
    }

    public String getDdbh() {
        return ddbh;
    }

    public void setDdbh(String ddbh) {
        this.ddbh = ddbh;
    }

    public String getCpbh() {
        return cpbh;
    }

    public void setCpbh(String cpbh) {
        this.cpbh = cpbh;
    }

    public String getCpmc() {
        return cpmc;
    }

    public void setCpmc(String cpmc) {
        this.cpmc = cpmc;
    }

    public String getCpth() {
        return cpth;
    }

    public void setCpth(String cpth) {
        this.cpth = cpth;
    }

    public String getCpcz() {
        return cpcz;
    }

    public void setCpcz(String cpcz) {
        this.cpcz = cpcz;
    }

    public List<com.example.factoryapplication.entity.departmentProgressList> getDepartmentProgressList() {
        return departmentProgressList;
    }

    public void setDepartmentProgressList(List<com.example.factoryapplication.entity.departmentProgressList> departmentProgressList) {
        this.departmentProgressList = departmentProgressList;
    }
}

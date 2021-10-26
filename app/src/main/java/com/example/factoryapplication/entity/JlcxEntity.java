package com.example.factoryapplication.entity;

import java.util.List;

public class JlcxEntity {


  private String  ddbh                      ;
  private String          cpbh              ;
  private String  cpmc                      ;
  private String          cpth              ;
  private String  cpcz                      ;
  private String          jhrq              ;
  private String  pcsl                      ;
  private String          bmmc              ;
  private String  ryxm                      ;
  private String          bmbh              ;
  private String  rybh                      ;

    List<JlcxGxEntity> orderProcessList ;
    List< JlcxGxItemEntity> jobProcessList ;

    public List<JlcxGxItemEntity> getJobProcessList() {
        return jobProcessList;
    }

    public void setJobProcessList(List<JlcxGxItemEntity> jobProcessList) {
        this.jobProcessList = jobProcessList;
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

    public String getBmmc() {
        return bmmc;
    }

    public void setBmmc(String bmmc) {
        this.bmmc = bmmc;
    }

    public String getRyxm() {
        return ryxm;
    }

    public void setRyxm(String ryxm) {
        this.ryxm = ryxm;
    }

    public String getBmbh() {
        return bmbh;
    }

    public void setBmbh(String bmbh) {
        this.bmbh = bmbh;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public List<JlcxGxEntity> getOrderProcessList() {
        return orderProcessList;
    }

    public void setOrderProcessList(List<JlcxGxEntity> orderProcessList) {
        this.orderProcessList = orderProcessList;
    }
}

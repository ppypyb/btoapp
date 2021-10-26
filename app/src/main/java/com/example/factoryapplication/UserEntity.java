package com.example.factoryapplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dyc on 2016/11/22.
 */
public class UserEntity implements Serializable {
    private static final long serialVersionUID = -1309102171L;
    private long userID;
    private String userAccount = "";

    private String code ;
    private String data;
    private String rybh;
    private String ryxm;
    private String bmbh;
    private String bmmc;
    private String ryzw;
    private String rylxdh;
    private String token;

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
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

    public String getBmmc() {
        return bmmc;
    }

    public void setBmmc(String bmmc) {
        this.bmmc = bmmc;
    }

    public String getRyzw() {
        return ryzw;
    }

    public void setRyzw(String ryzw) {
        this.ryzw = ryzw;
    }

    public String getRylxdh() {
        return rylxdh;
    }

    public void setRylxdh(String rylxdh) {
        this.rylxdh = rylxdh;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

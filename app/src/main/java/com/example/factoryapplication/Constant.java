package com.example.factoryapplication;


import com.example.factoryapplication.common.utils.SPUtils;

/**
 * Created by dyc on 2016/11/8.
 */
public class Constant {

    public static UserEntity user ;

//    public static String IP = "" ;

    public static String IP = "39.103.224.109:8080" ;
//public static String IP = "z3835r6545.qicp.vip:19771" ;

    public static String WEB_APP = "http://"+IP+"/btoweb/btoweb" ;
//    private static String WEB_APP = "http://39.103.224.109:8080/btoweb/btoweb" ;
//    private static String WEB_APP = "http://192.168.1.133:8080/endToEndGanSu/" ;
//    private static String WEB_APP = "http://60.247.77.195:50002/endToEndGanSu/" ;


    public static String BUSINESS_REG = WEB_APP + "/verifyRegistrationCode" ;

//    登录
    public static String BUSINESS_LOGIN = WEB_APP + "/login" ;
    public static String BUSINESS_DDHCX = WEB_APP + "/queryOrderList" ;
    public static String BUSINESS_DDJDCX = WEB_APP + "/queryOrderProgressInformation" + "" ;
    public static String BUSINESS_GXMX = WEB_APP + "/queryProcessDetailList" ;

    public static String BUSINESS_JLCX = WEB_APP + "/queryOrderRecordInfo"  ;

    public static String BUSINESS_GWGX = WEB_APP + "/queryJobProcessInfo" + "" ;

    public static String BUSINESS_DDXZ = WEB_APP + "/queryOrderList" ;
    public static String BUSINESS_KHLB = WEB_APP + "/queryCustomerList" + "" ;

//    TODO
    public static String BUSINESS_SCDJ = WEB_APP + "/queryProductionRegistrationOrderInfo"  ;

    public static String BUSINESS_FCPDJ = WEB_APP + "/scrapRegister" + "" ;
//    跟踪单号
    public static String BUSINESS_GZDH = WEB_APP + "/queryTrackAndHandoverNumberList" + "" ;

    public static String BUSINESS_GZD_SL = WEB_APP + "/queryTrackAndHandoverOrderAmount" + "" ;
//    更新订单
    public static String BUSINESS_UPDATEPRODUCTION = WEB_APP + "/updateProductionRegistrationOrderInfo" + "" ;
//查询交接单
    public static String BUSINESS_JJD = WEB_APP + "/queryHandoverNumberList?cpbh=1&ddbh=1&gxbh=1" + "" ;
    //查询交接单 数量
    public static String BUSINESS_JJD_SL = WEB_APP + "/queryHandoverOrderAmount?cpbh=1&ddbh=1&gxbh=1&gzbh=1" + "" ;
//取消登记
    public static String BUSINESS_QXDJ = WEB_APP + "/cancelProductionRegistration" + "" ;

    public static String BUSINESS_VERIFY_GZDH = WEB_APP + "/verifyGzdh" + "";

    public static String BUSINESS_FPYY = WEB_APP + "/queryScrapReasonList" + "" ;


    public static String BUSINESS_IMG = WEB_APP + "/images/"  ;

    public static String BUSINESS_OCR = WEB_APP + "/ocrNumbers"  ;

    public static String BUSINESS_JOB_PROCESS = WEB_APP + "/queryJobProcessList" + "" ;

    public static void resetUrl(){
      BUSINESS_REG = WEB_APP + "/verifyRegistrationCode" ;

//
      BUSINESS_LOGIN = WEB_APP + "/login" ;
      BUSINESS_DDHCX = WEB_APP + "/queryOrderList" ;
      BUSINESS_DDJDCX = WEB_APP + "/queryOrderProgressInformation" + "" ;
      BUSINESS_GXMX = WEB_APP + "/queryProcessDetailList" ;

      BUSINESS_JLCX = WEB_APP + "/queryOrderRecordInfo"  ;

      BUSINESS_GWGX = WEB_APP + "/queryJobProcessInfo" + "" ;

      BUSINESS_DDXZ = WEB_APP + "/queryOrderList" ;
      BUSINESS_KHLB = WEB_APP + "/queryCustomerList" + "" ;

//
      BUSINESS_SCDJ = WEB_APP + "/queryProductionRegistrationOrderInfo"  ;

      BUSINESS_FCPDJ = WEB_APP + "/scrapRegister" + "" ;
//
      BUSINESS_GZDH = WEB_APP + "/queryTrackAndHandoverNumberList" + "" ;

      BUSINESS_GZD_SL = WEB_APP + "/queryTrackAndHandoverOrderAmount" + "" ;
//
      BUSINESS_UPDATEPRODUCTION = WEB_APP + "/updateProductionRegistrationOrderInfo" + "" ;
//查询交接
      BUSINESS_JJD = WEB_APP + "/queryHandoverNumberList?cpbh=1&ddbh=1&gxbh=1" + "" ;

      BUSINESS_JJD_SL = WEB_APP + "/queryHandoverOrderAmount?cpbh=1&ddbh=1&gxbh=1&gzbh=1" + "" ;
//取消登记
      BUSINESS_QXDJ = WEB_APP + "/cancelProductionRegistration" + "" ;
      //验证跟踪单号
      BUSINESS_VERIFY_GZDH = WEB_APP + "/verifyGzdh" + "" ;

      BUSINESS_FPYY = WEB_APP + "/queryScrapReasonList" + "" ;


      BUSINESS_IMG = WEB_APP + "/images/"  ;

      BUSINESS_OCR = WEB_APP + "/ocrNumbers"  ;

      BUSINESS_JOB_PROCESS = WEB_APP + "/queryJobProcessList" + "" ;
    }
}

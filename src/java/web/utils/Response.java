/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.utils;

import com.alibaba.fastjson.JSONObject;

/**
 *
 * @author 
 */
public class Response {

    public String errCode;
    public String errMsg;
    public String errDetail;
    public static final String ERR_EMPTY_FILED = "Empty filed in request";
    public static final String ERR_WRONG_TYPE = "Wrong filed type";
    public static final String ERR_BAD_HEADERS = "Invalid http headers";
    public static final String CHECK_SUCCESS = "00";

    public Response() {
        this.errCode = "";
        this.errMsg = "";
        this.errDetail = "";
    }

    public Response(String detail) {
        this.errCode = "";
        this.errMsg = "";
        if (!detail.isEmpty()) {
            this.errDetail = detail;
        } else {
            this.errDetail = "";
        }
    }
    
    public JSONObject success() {
        this.errCode = "00";
        JSONObject msg = new JSONObject();
        msg.put("errCode", errCode);
        msg.put("errMsg", errMsg);
        msg.put("errDetail", errDetail);
        return msg;
    }

    public JSONObject invalidRequest(String detail) {
        this.errCode = "101";
        this.errMsg = "Invalid Request";
        JSONObject msg = new JSONObject();
        msg.put("errCode", errCode);
        msg.put("errMsg", errMsg);
        msg.put("errDetail", detail);
        return msg;
    }

    public JSONObject internalServerErr(String detail) {
        this.errCode = "102";
        this.errMsg = "Internal Server Error";
        JSONObject msg = new JSONObject();
        msg.put("errCode", errCode);
        msg.put("errMsg", errMsg);
        msg.put("errDetail", detail);
        return msg;
    }
}

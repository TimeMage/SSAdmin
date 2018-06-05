/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author 
 */
public class CheckRequest {

    public static final String JSONOBJECT_TYPE = "com.alibaba.fastjson.JSONObject";
    public static final String JSONOARRAY_TYPE = "com.alibaba.fastjson.JSONArray";
    public static final String INTEGER_TYPE = "java.lang.Integer";
    public static final String STRING_TYPE = "java.lang.String";
    public static final String DOUBLE_TYPE = "java.lang.Double";

    public static JSONObject checkBody(JSONObject jsonReq, Object valid) {
        JSONObject msg = new JSONObject();
        Response mResponse = new Response();
        String checkParam = isValidParams(jsonReq, valid);
        if (!checkParam.equals(Response.CHECK_SUCCESS)) {
            msg = mResponse.invalidRequest(checkParam);
            return msg;
        }
        return msg;
    }

    public static String isValidParams(Object body, Object valid) {
        JSONObject jsonValid = (JSONObject) valid;
        JSONObject jsonBody = (JSONObject) body;
        String res;
        
        Set<Entry<String, Object>> set = jsonValid.entrySet();
        Iterator<Entry<String, Object>> it_valid = set.iterator();
        while (it_valid.hasNext()) {
            Entry<String, Object> entry = it_valid.next();
            String key = entry.getKey();
            Object value = entry.getValue(); 
            
            if(!jsonBody.containsKey(key)){
                res = Response.ERR_EMPTY_FILED;
                return res;
            }
            
            if (value.getClass() != jsonBody.get(key).getClass()) {
                res = Response.ERR_WRONG_TYPE;
                return res;
            }
            switch (value.getClass().getName()) {
                case JSONOBJECT_TYPE:
                    JSONObject obj = (JSONObject) jsonBody.get(key);
                    int len = obj.size();
                    if (len == 0) {
                        res = Response.ERR_EMPTY_FILED;
                        return res;
                    } else {
                        String ob = isValidParams(jsonBody.get(key), jsonValid.get(key));
                        if (!ob.equals(Response.CHECK_SUCCESS)) {
                            return ob;
                        }
                    }   
                    break;
                case JSONOARRAY_TYPE:
                    JSONArray ja = (JSONArray) jsonBody.get(key);
                    if (ja.isEmpty()) {
                        res = Response.ERR_EMPTY_FILED;
                        return res;
                    }   
                    break;
                case STRING_TYPE:
                    String str = (String) jsonBody.get(key);
                    if ("".equals(str.trim())) {
                        res = Response.ERR_EMPTY_FILED;
                        return res;
                    }   
                    break;
                default:
                    break;
            }
        }
        res = Response.CHECK_SUCCESS;
        return res;
    }
}

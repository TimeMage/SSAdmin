/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import web.utils.PropertyUtil;

/**
 *
 * @author pei-qiang.pang
 */
@CrossOrigin
@Controller
@RequestMapping("/rest/configuration")
public class Configuration {

    private static final String CONFIG_PATH = PropertyUtil.getProperty("config.path");

    @RequestMapping(value = "setConfig", method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String setConfig(@RequestBody String params) {

        JSONObject requestObject = JSONObject.parseObject(params);
        JSONObject responseMsg = new JSONObject();

        File ConfigPath = new File(CONFIG_PATH);
        if (!ConfigPath.exists()) {
            ConfigPath.mkdirs();
        }

        if (!ConfigPath.isDirectory()) {
            responseMsg.put("errCode", "201");
            responseMsg.put("errMsg", "config path error");
            responseMsg.put("errDetail", "Not such directory");
            return responseMsg.toJSONString();
        }

        File file = new File(CONFIG_PATH, requestObject.getString("name") + ".json");
        if (!file.exists()) {
            responseMsg.put("errCode", "301");
            responseMsg.put("errMsg", "Not such server");
            responseMsg.put("errDetail", "Not such server");
            return responseMsg.toJSONString();
        }

        PrintStream ps;
        try {
            ps = new PrintStream(new FileOutputStream(file));
            ps.print(JSON.toJSONString(requestObject.getJSONObject("config"), true));
            ps.flush();
            ps.close();
            
            responseMsg.put("errCode", "00");
            responseMsg.put("errMsg", "");
            responseMsg.put("errDetail", "");
            return responseMsg.toJSONString();
            
        } catch (FileNotFoundException ex) {
            responseMsg.put("errCode", "302");
            responseMsg.put("errMsg", "Save server config fail.");
            responseMsg.put("errDetail", ex.getMessage());
            return responseMsg.toJSONString();
        }
    }

}

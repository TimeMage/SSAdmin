/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
@RequestMapping("/rest/server")
public class Server {

    private static final String CONFIG_PATH = PropertyUtil.getProperty("config.path");

    @RequestMapping(value = "addServer", method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String addServer(@RequestBody String params) {

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
            try {
                file.createNewFile();
            } catch (IOException ex) {
                responseMsg.put("errCode", "202");
                responseMsg.put("errMsg", "Add configuration file fail.");
                responseMsg.put("errDetail", ex.getMessage());
                return responseMsg.toJSONString();
            }

            PrintStream ps;
            try {
                ps = new PrintStream(new FileOutputStream(file));
                ps.print(JSON.toJSONString(requestObject.getJSONObject("config"), true));
                ps.flush();
                ps.close();
            } catch (FileNotFoundException ex) {
                responseMsg.put("errCode", "203");
                responseMsg.put("errMsg", "Save server config fail.");
                responseMsg.put("errDetail", ex.getMessage());
                return responseMsg.toJSONString();
            }

            responseMsg.put("errCode", "00");
            responseMsg.put("errMsg", "");
            responseMsg.put("errDetail", "");
            return responseMsg.toJSONString();
        } else {
            responseMsg.put("errCode", "204");
            responseMsg.put("errMsg", "The server has already existed");
            responseMsg.put("errDetail", "The server has already existed");
            return responseMsg.toJSONString();
        }

    }

    @RequestMapping(value = "getServerList", method = {RequestMethod.GET}, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getServerList() {

        JSONObject responseMsg = new JSONObject();

        File ConfigPath = new File(CONFIG_PATH);
//        if (!ConfigPath.exists()) {
//            ConfigPath.mkdirs();
//        }

        JSONArray userList = new JSONArray();
        String[] fileList = ConfigPath.list();
        if (fileList != null && fileList.length > 0) {
            for (String fileName : fileList) {
                String name = fileName.substring(0, fileName.lastIndexOf("."));
                userList.add(name);
            }
        }

        responseMsg.put("errCode", "00");
        responseMsg.put("errMsg", "");
        responseMsg.put("errDetail", "");
        responseMsg.put("userList", userList);

        return responseMsg.toJSONString();
    }

    @RequestMapping(value = "deleteServer", method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deleteServer(@RequestBody String params) {

        JSONObject requestObject = JSONObject.parseObject(params);
        JSONObject responseMsg = new JSONObject();

        File file = new File(CONFIG_PATH, requestObject.getString("name")+".json");
        if (file.exists() && file.isFile()) {
            file.delete();
            responseMsg.put("errCode", "00");
            responseMsg.put("errMsg", "");
            responseMsg.put("errDetail", "");
        } else {
            responseMsg.put("errCode", "205");
            responseMsg.put("errMsg", "Not such server");
            responseMsg.put("errDetail", "Not such server");
        }
        return responseMsg.toJSONString();
    }
    
    @RequestMapping(value = "renameServer", method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String renameServer(@RequestBody String params) {
        
        JSONObject requestObject = JSONObject.parseObject(params);
        JSONObject responseMsg = new JSONObject();
    
        File file = new File(CONFIG_PATH, requestObject.getString("name")+".json");
        if (file.exists() && file.isFile()) {
            boolean isSuccess = file.renameTo( new File( CONFIG_PATH, requestObject.getString("newName") + ".json" ) );
            if( isSuccess ) {
                responseMsg.put("errCode", "00");
                responseMsg.put("errMsg", "");
                responseMsg.put("errDetail", "");
            } else {
                responseMsg.put("errCode", "401");
                responseMsg.put("errMsg", "rename failed");
                responseMsg.put("errDetail", "rename failed");
            }
            return responseMsg.toJSONString();
        }
        
        responseMsg.put("errCode", "402");
        responseMsg.put("errMsg", "Not such server");
        responseMsg.put("errDetail", "Not such server");
        return responseMsg.toJSONString();
    }
    

}

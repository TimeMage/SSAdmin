/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.rest;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import system.shell.RunShellUtil;

/**
 *
 * @author 
 */

@CrossOrigin
@Controller
@RequestMapping("/rest/service")
public class Service {
    
    @RequestMapping(value = "startSS", method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String startSS(@RequestBody String params) {
        JSONObject requestObject = JSONObject.parseObject(params);
        JSONObject responseMsg = new JSONObject();
        responseMsg.put("result", RunShellUtil.runScript("systemctl start shadowsocks-libev-server@"+requestObject.getString("serverName")));
        return responseMsg.toJSONString();
    }
    
    
    @RequestMapping(value = "stopSS", method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String stopSS(@RequestBody String params) {
        JSONObject requestObject = JSONObject.parseObject(params);
        JSONObject responseMsg = new JSONObject();
        responseMsg.put("result", RunShellUtil.runScript("systemctl stop shadowsocks-libev-server@"+requestObject.getString("serverName")));
        return responseMsg.toJSONString();
    }
}

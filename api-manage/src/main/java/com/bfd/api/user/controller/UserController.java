package com.bfd.api.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bfd.api.common.BaseController;
import com.bfd.api.common.vo.Result;
import com.bfd.api.user.domain.User;
import com.bfd.api.user.service.UserService;
import com.bfd.api.utils.Encrypt;
import com.bfd.api.utils.LogType;
import com.bfd.api.utils.LogUtil;
import com.bfd.api.utils.ReturnJsonUtils;

@RestController
@RequestMapping(value = "/user", produces = {"application/json;charset=UTF-8"})
@ResponseBody
public class UserController extends BaseController {
    
    @Autowired
    public UserService userService;
    
    @RequestMapping(value = "/info")
    public Result<Object> info(@RequestParam(value = "id", required = true) Long id) {
        LogUtil.getLogger(LogType.Run).info("id=" + id + " 查询用户信息");
        try {
            User user = userService.info(id);
            return ReturnJsonUtils.getSuccessJson("000000", user);
        }
        catch (Exception e) {
            LogUtil.getLogger(LogType.Run).error("id=" + id + "查询用户信息失败", e);
            return ReturnJsonUtils.getFailedJson("111111", "查询用户信息失败", e.getMessage());
        }
    }
    
    /**
     * 查询所有用户
     * 
     * @return
     */
    @RequestMapping(value = "/getAllUser")
    public Object getAllUser(@RequestParam(value = "random", required = false) String random) {
        LogUtil.getLogger(LogType.Run).info("查询所有用户信息");
        List<User> list = null;
        try {
            list = userService.getAllUser();
            for (User user : list) {
                String pwd=user.getPwd();
                System.out.println(pwd);
                user.setPwd(Encrypt.decrypt(pwd));
            }
        }
        catch (Exception e) {
            LogUtil.getLogger(LogType.Run).error("查询所有用户信息失败", e);
            return ReturnJsonUtils.getFailedJson("111111", "查询用户信息失败", null);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("totalList", list);
        
        return ReturnJsonUtils.getSuccessJson("000000", "success", result);
    }
    
    
    
    @RequestMapping(value = "/delUserByid")
    public Object delUserByid(@RequestParam(value = "id", required = true) Long id) {
        LogUtil.getLogger(LogType.Run).info("id=" + id + "删除用户信息");
        try {
            userService.delUserByid(id);
        }
        catch (Exception e) {
            LogUtil.getLogger(LogType.Run).error("删除用户信息失败", e);
            return ReturnJsonUtils.getFailedJson("111111", "删除用户信息失败", null);
        }
        return ReturnJsonUtils.getSuccessJson("000000", "success", null);
    }
    
    @RequestMapping(value = "/updateUserByid")
    public Object updateUserByid(User user) {
        LogUtil.getLogger(LogType.Run).info("id=" + user.getId() + "更新用户信息");
        try {
            String pwd = user.getPwd();
            if (pwd != null && !pwd.equals("")) {
                System.out.println(pwd);
                // 加密处理密码
                user.setPwd(Encrypt.encrypt(pwd));
            }
            if(user.getType()==1){
                user.setDescr("超级管理员");
            }else if(user.getType()==2){
                user.setDescr("访问用户");
            }else if(user.getType()==3){
                user.setDescr("管理员");
            }
            
            userService.updateUserById(user);
        }
        catch (Exception e) {
            LogUtil.getLogger(LogType.Run).error("更新用户信息失败", e);
            return ReturnJsonUtils.getFailedJson("111111", "修改用户信息失败", null);
        }
        return ReturnJsonUtils.getSuccessJson("000000", "success", null);
    }
    
    @RequestMapping(value = "/addUser")
    public Object addUser(User user) {
        
        LogUtil.getLogger(LogType.Run).info("添加用户");
        try {
            String pwd = user.getPwd();
            if (pwd != null && !pwd.equals("")) {
                System.out.println(pwd);
                // 加密处理密码
                user.setPwd(Encrypt.encrypt(pwd));
            }
            if(user.getType()==1){
                user.setDescr("超级管理员");
            }else if(user.getType()==2){
                user.setDescr("访问用户");
            }else if(user.getType()==3){
                user.setDescr("管理员");
            }
            userService.addUser(user);
        }
        catch (Exception e) {
            LogUtil.getLogger(LogType.Run).error("添加用户失败", e);
            return ReturnJsonUtils.getFailedJson("111111", "添加用户信息失败", null);
        }
        return ReturnJsonUtils.getSuccessJson("000000", "success", null);
    }
}

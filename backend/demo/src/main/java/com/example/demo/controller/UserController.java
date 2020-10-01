package com.example.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.UserRepository;
import com.example.demo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import java.util.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/getAllUser")
    @ResponseBody
    public List<User> findAll() {
        List<User> list = new ArrayList<User>();
        list = userRepository.findAll();
        return list;
    }

    @RequestMapping("/getByUserName")
    @ResponseBody
    public HashMap<String, String> getByUserName(String userName) {
        HashMap<String, String> map = new HashMap<>();
        User user = userRepository.findByUserName(userName);
        map.put(user.getPassWord(),user.getUserName());
        return map;
    }

//    @PostMapping(value = "/userLogin")
//    @ResponseBody
//    public Map<String, Object> getPerson(@RequestBody User user) {
//        Map<String, Object> param = new HashMap<>();
//        String s = person.getPhones().toString();
//        System.out.println(s);
//        param.put("person", person);
//        return param;
//    }
//}

//    @ResponseBody
//    @RequestMapping(value = "/getUserByJSON", method= RequestMethod.POST, produces = "application/json;charset=UTF-8")
//    public String getUserByJSON(@RequestBody JSONObject user) {
//        // 直接将json信息打印出来
//        System.out.println(user.toJSONString());
//
//        // 将获取的json数据封装一层，然后在给返回
//        JSONObject result = new JSONObject();
//        result.put("msg", "ok");
//        result.put("method", "json");
//        result.put("data", user);
//        return result.toJSONString();
//    }


    @ResponseBody
    @RequestMapping(value = "/getUserByRequest", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject getByRequest(HttpServletRequest request) {

        //获取到JSONObject
        JSONObject jsonParam = getJSONParam(request);

        // 将获取的json数据封装一层，然后在给返回
//        JSONObject result = new JSONObject();
//        result.put("data", jsonParam);
//        return result.toJSONString();
        System.out.println(jsonParam);
        return jsonParam;
    }

    public JSONObject getJSONParam(HttpServletRequest request){
        JSONObject jsonParam = null;
        try {
            // 获取输入流
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));

            // 写入数据到Stringbuilder
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = streamReader.readLine()) != null) {
                sb.append(line);
            }
            jsonParam = JSONObject.parseObject(sb.toString());
            // 直接将json信息打印出来
            System.out.println(jsonParam.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonParam;
    }




    @RequestMapping("hello")
    @ResponseBody
    public String hello() {
        return "Hello World!";
    }
}

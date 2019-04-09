package com.bcx.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther: mafx
 * @Date: 2019/4/8 10:55
 * @Description: TODO
 */
@Controller
@RequestMapping(value = "/hello")
public class HelloController {
    @RequestMapping("/index")
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("message", "Hello Spring MVC");
        return mav;
    }
    @RequestMapping("/test")
    public ModelAndView test(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        ModelAndView mav = new ModelAndView("/hello/test");
        mav.addObject("message", "Hello Spring MVC");
        return mav;
    }
    @RequestMapping("/param")
    public ModelAndView getParam(HttpServletRequest request,HttpServletResponse response) {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        System.out.println(userName);
        System.out.println(password);
        return null;
    }
    @RequestMapping("/param1")
    public ModelAndView getParam(String userName, String password, @RequestParam("userName") String name) {
        System.out.println(userName);
        System.out.println(password);
        System.out.println(name);
        ModelAndView mav = new ModelAndView("/hello/test2");
        mav.addObject("message", "成功！");
        return mav;
    }
    @RequestMapping("/param2")
    public String getParam(String userName, String password, @RequestParam("userName") String name, Model model) {
        System.out.println(userName);
        System.out.println(password);
        System.out.println(name);
        model.addAttribute("message", "成功！");
        return "/hello/test2";
    }
    @RequestMapping("/jump")
    public ModelAndView jump() {
        ModelAndView mav = new ModelAndView("redirect:/hello/test");
        return mav;
    }
/*    @RequestMapping("/jump")
    public String jump() {
        return "redirect: /hello/test";
    }*/

    @RequestMapping(value = "/upload" , method = RequestMethod.GET)
    public ModelAndView upload() {
        return new ModelAndView("/hello/upload");
    }
    @RequestMapping(value = "/upload" , method = RequestMethod.POST)
    public void upload(@RequestParam MultipartFile picture,HttpServletRequest req) throws Exception {
        System.out.println(picture.getOriginalFilename());
        MultipartHttpServletRequest mreq = (MultipartHttpServletRequest)req;
        MultipartFile file = mreq.getFile("picture");
        String fileName = file.getOriginalFilename();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String path =  req.getSession().getServletContext().getRealPath("/")+
                "upload/"+sdf.format(new Date())+fileName.substring(fileName.lastIndexOf('.'));
        File newFile = new File(path);
        if (!newFile.getParentFile().exists()) {
            boolean result = newFile.getParentFile().mkdirs();
            if (!result) {
                System.out.println("创建失败");
            }
        }
        //上传
        file.transferTo(newFile);
    }

    @RequestMapping(value="/json")
    @ResponseBody
    public String json() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name","小马");
        jsonObject.put("mobile","177****1234");
        return jsonObject.toJSONString();
    }

    @RequestMapping(value="/jsonObj")
    @ResponseBody
    public Object jsonObj() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name","小马");
        jsonObject.put("mobile","177****1234");
        return jsonObject;
    }
}
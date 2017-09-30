package com.gooseeker.fss.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.aspectj.apache.bcel.classfile.Field;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gooseeker.fss.entity.MyUser;
import com.gooseeker.fss.mapper.UserMapper;

@Controller
@RequestMapping("standards/test")
public class JsonTestController
{
    @Resource(name = "userMapper")
    private UserMapper userMapper;
    
//    /**
//     * 对应testExceptionHandler请求方法先执行handlerTestException 方法异常处理
//     * @param ex
//     * @return
//     */
//    @ExceptionHandler({RuntimeException.class})
//    public ModelAndView handlerTestException2(Exception ex)
//    {
//        ModelAndView mv = new ModelAndView("test/error");
//        mv.addObject("exception", ex);
//        return mv;
//    }
//    
//    @ExceptionHandler({ArithmeticException.class})
//    public ModelAndView handlerTestException(Exception ex)
//    {
//        ModelAndView mv = new ModelAndView("test/error");
//        mv.addObject("exception", ex);
//        return mv;
//    }
    
    @RequestMapping("/testExceptionHandler.html")
    public String testExceptionHandler(@RequestParam("i") int i)
    {
        System.out.println("result: " + (10 / i));
        return "test/success";
    }
    
    @RequestMapping("/jsonView.html")
    public String jsonView()
    {
        return "test/jsonTest";
    }
    
    //-----------------test------------------
    @ResponseBody
    @RequestMapping("/testJson.html")
    public List<MyUser> testJson()
    {
        return userMapper.selectByExample(null);
    }
    
    
    @RequestMapping("/testJson2.html")
    public void testJson2(@RequestBody MyUser myUser)
    {
        System.out.println(myUser.toString());
    }
    
    /**
     * 下载
     * @param session
     * @return
     * @throws IOException
     */
    @RequestMapping("/testResponseEntity.html")
    public ResponseEntity<byte[]> testResponseEntity(HttpSession session) throws IOException {
        byte[] body=null;
        File file = new File("F:/fss/standards/pdf/GB 2761-2011.pdf");
        InputStream inputStream = new FileInputStream(file);
        //ServletContext sc=session.getServletContext();
        //InputStream in=sc.getResourceAsStream("/files/test.txt");
        body=new byte[inputStream.available()];
        inputStream.read(body);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition","attachment;filename=11.txt");

        HttpStatus statusCode = HttpStatus.OK;

        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(body, headers, statusCode);
        return response;
    }
}

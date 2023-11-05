package com.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 基于反射的思想
 * 替换HttpServlet,根据请求的最后一段资源路径来进行方法分发
 */
public class BaseServlet extends HttpServlet {

    //根据请求的最后一段资源路径来进行方法分发
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求路径
        String uri = req.getRequestURI();  //  uri: /Brand-Case/brand/selectAll
        //System.out.println(uri);

        //2.获取最后一段路径,方法名
        //从后面开始找,找到第一个斜线 / 字符的下标
        int index=uri.lastIndexOf("/");
        //切割后返回的内容,就是最后一段资源路径,也就是方法名称
        String methodName = uri.substring(index+1);  //因为是 /selectAll ,所以获取方法名index要 +1
        //System.out.println(methodName);     //测试   是/selectAll? 还是 selectAll?

        //3.执行方法
        //3.1 获取BrandServlet | UserServlet 字节码对象 Class
        //谁调用我(this 所在的方法),我(this)就代表谁
        //这里的this代表BaseServlet的子类
        //System.out.println(this);  //BrandServlet? HttpServlet?   其实这里是BrandServlet!

        //这个cls就是对应的字节码对象
        Class<? extends BaseServlet> cls = this.getClass();

        //3.2 获取方法Method对象
        try {
            Method method = cls.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            //3.3执行方法
            method.invoke(this,req,resp);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

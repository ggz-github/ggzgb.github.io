package com.web.servlet;

import com.alibaba.fastjson.JSON;
import com.pojo.Brand;
import com.pojo.PageBean;
import com.service.BrandService;
import com.service.impl.BrandServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/brand/*")
public class BrandServlet extends BaseServlet{
    private BrandService brandService=new BrandServiceImpl();
    public void selectAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //调用service查询
        List<Brand> brands = brandService.selectAll();
        //转为JSON
        String jsonString = JSON.toJSONString(brands);
        //写数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    public void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        String params = reader.readLine();
        Brand brand = JSON.parseObject(params, Brand.class);
        brandService.add(brand);
        response.getWriter().write("success");
    }

    public void update(HttpServletRequest request,HttpServletResponse response) throws IOException {
        //1.接收修改后的品牌数据
        BufferedReader br = request.getReader();
        String params=br.readLine();  //json字符串

        //将接收过来的id转为int型
        //String _id=request.getParameter("id");
        //int id=Integer.parseInt(_id);

        //将接收过来的JSON数据e对象,转为Brand对象
        Brand _brand = JSON.parseObject(params, Brand.class);

        //将需要修改的内容封装为一个brand对象
        Brand brand=new Brand();
        //brand.setId(id);
        brand.setId(_brand.getId());
        brand.setBrandName(_brand.getBrandName());
        brand.setCompanyName(_brand.getCompanyName());
        brand.setOrdered(_brand.getOrdered());
        brand.setDescription(_brand.getDescription());
        brand.setStatus(_brand.getStatus());

        //2.调用service方法进行修改
        brandService.update(brand);

        //3.响应修改成功的标识
        response.getWriter().write("success");

    }


    public void deleteId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        String params = reader.readLine();
        int id = JSON.parseObject(params, int.class);
        brandService.deleteId(id);
        response.getWriter().write("success");
    }

    public void deleteIds(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        String params = reader.readLine();
        int[] ids = JSON.parseObject(params, int[].class);
        brandService.deleteIds(ids);
        response.getWriter().write("success");
    }

    public void selectByPageAndCondition(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.接收 当前页码 和 每页展示条数    通过  url?currentPage=1&pageSize=5  的形式传过来
        String _currentPage = request.getParameter("currentPage");
        String _pageSize = request.getParameter("pageSize");

        int currentPage = Integer.parseInt(_currentPage);
        int pageSize = Integer.parseInt(_pageSize);

        //获取查询条件对象
        BufferedReader br = request.getReader();
        String params=br.readLine();  //json字符串

        //转为  Brand
        Brand brand = JSON.parseObject(params, Brand.class);


        //2.调用service查询,将查询结果封装为pageBean对象
        PageBean<Brand> pageBean = brandService.selectByPageAndCondition(currentPage, pageSize,brand);

        //3.pageBean对象转为JSON字符串 传回给页面
        String jsonString= JSON.toJSONString(pageBean);

        //4.写数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);

    }
}


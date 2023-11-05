package com.service.impl;

import com.mapper.BrandMapper;
import com.pojo.Brand;
import com.pojo.PageBean;
import com.service.BrandService;
import com.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class BrandServiceImpl implements BrandService {
   SqlSessionFactory factory=SqlSessionFactoryUtils.getSqlSessionFactory();

    public List<Brand> selectAll() {
        SqlSession sqlSession = factory.openSession();
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);
        List<Brand> brands = mapper.selectAll();

        sqlSession.close();
        return brands;
    }


    @Override
    public void add(Brand brand) {
        SqlSession sqlSession = factory.openSession(true);
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);
        mapper.add(brand);
        sqlSession.close();
    }

    @Override
    public void update(Brand brand) {
        SqlSession sqlSession = factory.openSession(true);
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);
        mapper.update(brand);
        sqlSession.close();
    }


    @Override
    public void deleteId(int id) {
        SqlSession sqlSession = factory.openSession(true);
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);
        mapper.deleteId(id);
        sqlSession.close();
    }

    @Override
    public void deleteIds(int[] ids) {
        SqlSession sqlSession = factory.openSession(true);
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);
        mapper.deleteIds(ids);
        sqlSession.close();
    }

    @Override
    public PageBean<Brand> selectByPageAndCondition(int currentPage, int pageSize, Brand brand) {
        //2.获取SqlSession对象
        SqlSession sqlSession=factory.openSession();
        //3.获取BrandMapper
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);

        //4.计算开始索引
        int begin=(currentPage-1)*pageSize;
        //5.计算查询条目数
        int size=pageSize;

        //处理brand条件,模糊表达式   (其实这里的字符串拼接可以直接在SQL语句里面操作,效果更好点)
        String brandName = brand.getBrandName();
        if(brandName!=null&&brandName.length()>0){
            brand.setBrandName("%"+brandName+"%");
        }
        String companyName = brand.getCompanyName();
        if(companyName!=null&&companyName.length()>0){
            brand.setCompanyName("%"+companyName+"%");
        }

        //6.查询当前页数据    limit 开始索引位置,每页显示条数   -->  获取到的就是当前页的数据
        List<Brand> rows = mapper.selectByPageAndCondition(begin,size,brand);

        //7.查询总记录数
        int totalCount=mapper.selectTotalCountByCondition(brand);

        //8.封装PageBean对象
        PageBean<Brand> pageBean=new PageBean<>();
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);

        //9.释放资源
        sqlSession.close();

        return pageBean;
    }


}

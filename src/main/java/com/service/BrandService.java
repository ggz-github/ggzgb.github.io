package com.service;

import com.mapper.BrandMapper;
import com.pojo.Brand;
import com.pojo.PageBean;
import com.util.SqlSessionFactoryUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public interface BrandService {
    List<Brand> selectAll();

    void add(Brand brand);

    void update(Brand brand);

    void deleteId(int id);

    void deleteIds(int[] ids);

    /**
     * 分页查询
     * @param currentPage 当前页码
     * @param pageSize    每页数目
     * @return
     */
    PageBean<Brand> selectByPageAndCondition(int currentPage,int pageSize,Brand brand);

}

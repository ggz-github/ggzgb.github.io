package com.mapper;

import com.pojo.Brand;
import com.pojo.PageBean;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.lang.annotation.Repeatable;
import java.util.List;

public interface BrandMapper {
    List<Brand> selectAll();

    void add(Brand brand);


    void update(Brand brand);

    void deleteId(int id);

    void deleteIds(@Param("ids") int[] ids);

    //分页查询

    List<Brand> selectByPageAndCondition(@Param("begin") int begin,@Param("size") int size,@Param("brand") Brand brand);

    //查询总记录数
    Integer selectTotalCountByCondition(Brand brand);

}

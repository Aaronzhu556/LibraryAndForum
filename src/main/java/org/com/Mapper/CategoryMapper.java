package org.com.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.com.Entity.Category;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Select("select * from t_category")
    List<Category> SearchAllCategory();

    @Select("select * from t_category where category_name=#{category_name}")
    List<Category> SearchByName(String category_name);

    @Update("update t_category set category_status=#{category_status} where category_id=#{category_id}")
    Integer UpdateStatusById(String category_status,int category_id);

    @Select("select * from t_category where category_level!=#{category_level}")
    List<Category> GetALlParent(String category_level);

    @Insert("INSERT INTO t_category( category_name,category_parent,category_level) VALUES(#{category_name},#{category_parent},#{category_level})")
    Integer Insert(Category category);

    @Select("select category_name from t_category where category_id=#{category_id}")
    String QueryNameByID(int category_id);

    @Select("select * from t_category where category_id=#{category_id}")
    Category QueryCateById(int category_id);

    @Select("select category_parent from t_category where category_id=#{category_id}")
    Integer QueryParentById(int category_id);

    @Select("select * from t_category where category_name=#{category_name}")
    Integer QueryIDByName(String category_name);

    @Select("select category_id from t_category where category_parent=#{category_parent}")
    List<Integer> QueryIDByParent(int category_parent);
}

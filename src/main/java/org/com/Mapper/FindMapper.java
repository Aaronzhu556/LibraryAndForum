package org.com.Mapper;

import org.apache.ibatis.annotations.*;
import org.com.Entity.Find;
import org.com.Entity.Lost;

import java.util.List;

@Mapper
public interface FindMapper {

    @Select("select * from t_find")
    public List<Find> GetAllFind();
    @Select("select * from t_find where find_name like '%' #{find_name} '%' ")
    public List<Find> GetFindByName(String find_name);
    @Insert("insert into t_find(find_name,find_details,find_time,find_img,find_level) values(#{find_name},#{find_details},#{find_time},#{find_img},#{find_level})")
    public int AddFind(Find find);
    @Update("update t_find set find_status=#{find_status} where find_id=#{find_id}")
    public int UpdateFindStatus(String find_status,int find_id);
    @Delete("delete from t_find where find_id=#{find_id}")
    public int DeleteFind(int find_id);
}

package org.com.Mapper;

import org.apache.ibatis.annotations.*;
import org.com.Entity.Lost;

import java.util.List;

@Mapper
public interface LostMapper {
    @Select("select * from t_lost")
    public List<Lost> GetAllLost();
    @Select("select * from t_lost where lost_name like '%' #{lost_name} '%' ")
    public List<Lost> GetLostByName(String lost_name);
    @Insert("insert into t_lost(lost_name,lost_details,lost_time,lost_img) values(#{lost_name},#{lost_details},#{lost_time},#{lost_img})")
    public int AddLost(Lost lost);
    @Update("update t_lost set lost_status=#{lost_status} where lost_id=#{lost_id}")
    public int UpdateLostStatus(String lost_status,int lost_id);
    @Delete("delete from t_lost where lost_id=#{lost_id}")
    public int DeleteLost(int lost_id);

}

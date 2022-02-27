package org.com.Mapper;

import lombok.Data;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.com.Entity.Manager;

@Mapper
public interface ManagerMapper {
    @Select("select * from t_manager where manager_name=#{manager_name}")
    public Manager QueryByName(String manager_name);

}

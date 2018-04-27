package com.example.demo.persistence;

import com.example.demo.entity.Module;
import com.example.demo.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

@Mapper
public interface ModuleMapper {
    /**
     * 根据用户id查询角色
     * @param rid
     * @return
     */
    @Select(value = {"SELECT m.* from module m INNER JOIN module_role mr on mr.mid = m.mid WHERE mr.rid = #{rid}"})
    @Results({
            @Result(id = true,property="mid",column="mid"),
            @Result(property = "mname",column = "mname")
    })
   public Set<Module> getModuleByRoleId(int rid);
}

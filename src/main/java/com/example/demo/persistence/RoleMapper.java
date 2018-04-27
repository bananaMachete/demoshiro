package com.example.demo.persistence;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

@Mapper
public interface RoleMapper {
    /**
     * 根据用户id查询角色
     * @param uid
     * @return
     */
    @Select(value = {"SELECT r.* from role r INNER JOIN user_role ur on ur.rid = r.rid WHERE ur.uid = #{uid}"})
    @Results({
            @Result(id = true,property="rid",column="rid"),
            @Result(property = "rname",column = "rname")
    })
   public Set<Role> getRolesByUserId(int uid);
}

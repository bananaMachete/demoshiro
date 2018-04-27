package com.example.demo.persistence;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * @author tim
     * @param username
     * @return
     */
    @Select(value = {"SELECT u.* FROM user u WHERE u.username = #{username}"})
    @Results({
            @Result(id=true,property="id",column="uid"),
            @Result(property="username",column="username"),
            @Result(property = "password",column = "password"),
            @Result(property="roles",column="rid",javaType=List.class,
                    many=@Many(select="com.example.demo.persistence.RoleMapper.getRolesByUserId", fetchType = FetchType.DEFAULT)
            )
    })
    public User getUserByUserName(String username);

    @Select(value = { "SELECT * FROM user" })
    public List<User> findAllUsers();
}

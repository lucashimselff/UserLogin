package com.lyc.system.demoLogin.userMapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyc.system.demoLogin.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {


}


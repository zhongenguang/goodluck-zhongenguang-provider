package com.bw.zhongenguang.entity.dao;

import com.bw.zhongenguang.entity.bean.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by zhongenguang on 2017/7/28.
 */
public interface DaoMapper extends JpaRepository<UserTable,Integer>{
    //Id查询 用户修改的 回显
   UserTable findUserTableById(Integer id);

     //用户登录
    List<UserTable> findUserTableByNameAndPassword(String name, String password);

}

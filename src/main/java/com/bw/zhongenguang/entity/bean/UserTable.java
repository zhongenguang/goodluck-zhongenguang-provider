package com.bw.zhongenguang.entity.bean;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by zhongenguang on 2017/7/28.
 */
@Data
@Entity
public class UserTable {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Integer  id;//列表DI主键

        private  String name;//用户名

        private  String password;//密码

        private  String  age;//年龄

        private  String  sex;//性别

        public   String   tu;//头像地址

        public   String   hobby;//爱好





}

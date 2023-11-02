package com.imooc.bilibili.domain;

import java.util.Date;

// 对应数据库表，有点类似 mybatis-plus 自动生成的，现在手写
// Alt + Insert 快速生成 get 和 set
public class User {
       public Long id;
       public String phone;
       public String email;
       public String password;
       public String salt;
       public Date createTime;
       public Date updateTime;
       public UserInfo userInfo;



       public Long getId() {
              return id;
       }

       public void setId(Long id) {
              this.id = id;
       }

       public String getPhone() {
              return phone;
       }

       public void setPhone(String phone) {
              this.phone = phone;
       }

       public String getEmail() {
              return email;
       }

       public void setEmail(String email) {
              this.email = email;
       }

       public String getPassword() {
              return password;
       }

       public void setPassword(String password) {
              this.password = password;
       }

       public String getSalt() {
              return salt;
       }

       public void setSalt(String salt) {
              this.salt = salt;
       }

       public Date getCreateTime() {
              return createTime;
       }

       public void setCreateTime(Date createTime) {
              this.createTime = createTime;
       }

       public Date getUpdateTime() {
              return updateTime;
       }

       public void setUpdateTime(Date updateTime) {
              this.updateTime = updateTime;
       }

       public UserInfo getUserInfo() {
              return userInfo;
       }

       public void setUserInfo(UserInfo userInfo) {
              this.userInfo = userInfo;
       }
}

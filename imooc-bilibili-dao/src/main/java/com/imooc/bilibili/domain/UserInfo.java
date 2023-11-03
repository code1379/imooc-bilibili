package com.imooc.bilibili.domain;

import java.util.Date;

public class UserInfo {
       public Long id;
       public Long userId;
       public String nick;
       public String avatar;
       public String sign;
       public String gender;
       public String birth;
       public Date createTime;
       public Date updateTime;
       // 代表我是否关注了这个人（互相关注）
       private Boolean followed;

       public Long getId() {
              return id;
       }

       public void setId(Long id) {
              this.id = id;
       }

       public Long getUserId() {
              return userId;
       }

       public void setUserId(Long userId) {
              this.userId = userId;
       }

       public String getNick() {
              return nick;
       }

       public void setNick(String nick) {
              this.nick = nick;
       }

       public String getAvatar() {
              return avatar;
       }

       public void setAvatar(String avatar) {
              this.avatar = avatar;
       }

       public String getSign() {
              return sign;
       }

       public void setSign(String sign) {
              this.sign = sign;
       }

       public String getGender() {
              return gender;
       }

       public void setGender(String gender) {
              this.gender = gender;
       }

       public String getBirth() {
              return birth;
       }

       public void setBirth(String birth) {
              this.birth = birth;
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

       public Boolean getFollowed() {
              return followed;
       }

       public void setFollowed(Boolean followed) {
              this.followed = followed;
       }
}

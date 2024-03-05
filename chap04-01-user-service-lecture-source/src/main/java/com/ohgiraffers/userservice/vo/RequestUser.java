package com.ohgiraffers.userservice.vo;

import lombok.Data;

/* 설명. 회원가입을 위해 넘어온 사용자 정보를 받아줄 Command 객체용 VO 생성 */
@Data
public class RequestUser {

    private String name;    // 사용자 이름
    private String email;   // 사용자 이메일
    private String pwd;     // 사용자 비밀번호

//    public RequestUser() {
//    }
//
//    public RequestUser(String name, String email, String pwd) {
//        this.name = name;
//        this.email = email;
//        this.pwd = pwd;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPwd() {
//        return pwd;
//    }
//
//    public void setPwd(String pwd) {
//        this.pwd = pwd;
//    }
//
//    @Override
//    public String toString() {
//        return "RequestUser{" +
//                "name='" + name + '\'' +
//                ", email='" + email + '\'' +
//                ", pwd='" + pwd + '\'' +
//                '}';
//    }
}

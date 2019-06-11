package com.dx.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Administrator
 *
 * @Date 2019年6月5日
 *
 * 项目名 FaceRecongnition
 *
 *@version 1.0
 */
@Getter@Setter
public class FaceLogAndZkzResult {
   private Long id;
   private String name;//姓名
   private String sex;//性别
   private String cardNum;//身份证号
   private String course;//场次
   private String subject;//科目
   private String level;//级别
   private String examRoom;//考场
   private String seatNu;//座号
   private String zkzNum;//准考证号
   private String  rzTime;//认证时间
   private String  rzType;//认证结果
   private String rzCount;//识别次数
   private String facePhoto;//现场人脸照
   private String sfzPhoto;//身份证照片地址
   
   
}

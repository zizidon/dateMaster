package com.example.demo.entity;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    private Long id; //自動採番
    private String name; //ユーザー名
    private String password; //パスワード
    private Long partner; // パートナー名
    private Long applicant; // 申請者名
    private Long coaching_id; // コーチングの問題
    private int diagnosis; // 自己診断結果
    private String question; // 秘密の質問の番号
    private String answer; // 秘密の質問の答え
    private Long date_share; // プラン作成者が最近作成したプランのID
}
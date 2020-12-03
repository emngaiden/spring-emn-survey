/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emnsoft.emnsurvey.config;

import com.emnsoft.emnsurvey.domain.User;

import org.bson.types.ObjectId;

/**
 *
 * @author Quarksoft
 */
public interface Constants {
    public static final int A_TOKEN_LENGTH = 60;
    public static final String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";
    public static final String PASSWORD_REGEX = "^((?=\\S*?[A-Z])(?=\\S*?[a-z])(?=\\S*?[0-9]).{1,})\\S$";
    public static final ObjectId ADMIN_USER_ID = new ObjectId("ad1002784c92240008962c77");
    public static final String ADMIN_USER_FIRST_NAME = "Ricardo";
    public static final String ADMIN_USER_LAST_NAME = "Montes";
    public static final String ADMIN_USER_EMAIL = "emngaiden@hotmail.com";
    public static final String ADMIN_USER_LOGIN = "emngaiden";
    public static final String ADMIN_USER_PASSWORD = "$2a$10$ozF5xoKVj.P5g3DAE9dc.eSpVTiot5owB/yVEvCCWwxr.DaFpu1i6";
    public static final String ADMIN_USER_LANG_KEY = "en";
    public static final User ADMIN_USER = new User(ADMIN_USER_ID.toString(), ADMIN_USER_FIRST_NAME, ADMIN_USER_LAST_NAME, ADMIN_USER_PASSWORD, ADMIN_USER_LOGIN, ADMIN_USER_EMAIL, ADMIN_USER_LANG_KEY);
}

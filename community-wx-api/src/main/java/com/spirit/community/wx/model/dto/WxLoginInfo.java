package com.spirit.community.wx.model.dto;

import com.spirit.community.common.util.JsonUtils;

import javax.validation.constraints.NotBlank;

public class WxLoginInfo {
    @NotBlank(message = "航空公司二字码不能为空")
    private String code;
    private UserInfo userInfo;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public static void main(String[] args) {
        WxLoginInfo wxLoginInfo = new WxLoginInfo();
        wxLoginInfo.setCode("123");
        UserInfo user = new UserInfo();
        user  .setCity("123");

        wxLoginInfo.setUserInfo(user);
        System.out.println( JsonUtils.toJsonString(wxLoginInfo));
    }
}

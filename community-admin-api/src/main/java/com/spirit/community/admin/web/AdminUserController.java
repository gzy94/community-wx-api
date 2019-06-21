package com.spirit.community.admin.web;

import com.spirit.community.wx.domain.LitemallUser;
import com.spirit.community.wx.service.LitemallUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.spirit.community.admin.annotation.RequiresPermissionsDesc;
import com.spirit.community.common.util.ResponseUtil;
import com.spirit.community.common.validator.Order;
import com.spirit.community.common.validator.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/user")
@Validated
public class AdminUserController {
    @Autowired
    private LitemallUserService userService;

    @RequiresPermissions("admin:user:list")
    @RequiresPermissionsDesc(menu={"用户管理" , "会员管理"}, button="查询")
    @GetMapping("/list")
    public Object list(String username, String mobile,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<LitemallUser> userList = userService.querySelective(username, mobile, page, limit, sort, order);
        return ResponseUtil.okList(userList);
    }
}

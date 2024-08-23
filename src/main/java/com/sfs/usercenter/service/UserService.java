package com.sfs.usercenter.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sfs.usercenter.common.BaseResponse;
import com.sfs.usercenter.model.domain.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 史方树
 * @description 针对表【user(用户表)】的数据库操作Service
 * @createDate 2024-01-21 18:25:17
 */
public interface UserService extends IService<User> {
    /**
     * 注册账号
     */

    String USER_LOGIN_STATE="userLogin";
    BaseResponse<Long> userRegister(String userAccount, String UserPassword, String checkPassword, String planetCode);

    BaseResponse<User> doRegister(String userAccount, String UserPassword, HttpServletRequest request);

    public User getSelfUser(User user);

    BaseResponse<Integer> userLogout(HttpServletRequest request);

    /**
     * 用sql方法搜索
     * @param tagNames
     * @return
     */
    List<User> searchUsersByTagsBySql(List<String> tagNames);

    List<User> searchUsersByTagsByMemory(List<String> tagNames);


}

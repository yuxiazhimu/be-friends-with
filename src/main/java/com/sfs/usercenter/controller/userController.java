package com.sfs.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sfs.usercenter.common.BaseResponse;
import com.sfs.usercenter.common.ErrorCode;

import com.sfs.usercenter.exception.BusinessException;
import com.sfs.usercenter.model.domain.Requset.UserLoginRequest;
import com.sfs.usercenter.model.domain.Requset.UserRegisterRequest;
import com.sfs.usercenter.model.domain.User;
import com.sfs.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户接口
 *
 * @author 史方树
 * */
@RestController
@RequestMapping("/user")
public class userController {
    @Resource
    private UserService userService;


    @GetMapping("/current")
    public User getCurrent(HttpServletRequest request){
        Object object = request.getSession().getAttribute(UserService.USER_LOGIN_STATE);
        User userCurrent=(User) object;
        if (userCurrent==null){
            return null;
        }
        Long id = userCurrent.getId();
        User user = userService.getById(id);
        User selfUser = userService.getSelfUser(user);
        return selfUser;
    }

    @PostMapping("/register")
    public BaseResponse<Long> userRegin(@RequestBody UserRegisterRequest userRegisterRequest){

        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode=userRegisterRequest.getPlanetCode();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword,planetCode)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return userService.userRegister(userAccount, userPassword, checkPassword,planetCode );
    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest,HttpServletRequest request){
        if (userLoginRequest == null) {
            throw  new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw  new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        return userService.doRegister(userAccount, userPassword, request);

    }

    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request){
        if (request == null) {
            return null;
        }

        return userService.userLogout( request );
    }

    @GetMapping("/search")
    public List<User> searchUsers(String username,HttpServletRequest request){
        Object object = request.getSession().getAttribute(UserService.USER_LOGIN_STATE);
        User user=(User)object;
        if (user.getUserRole()==1){
            return null;
        }
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        if (!StringUtils.isAnyBlank(username)){
            queryWrapper.like("username",username);
        }
        return userService.list(queryWrapper);
    }

    @PostMapping("/deleter")
    public boolean deleterUser(@RequestBody long id,HttpServletRequest request){
        Object object = request.getSession().getAttribute(UserService.USER_LOGIN_STATE);
        User user=(User)object;
        if (user.getUserRole()==1){
            return false;
        }
        if (id<=0){
            return false;
        }
        return userService.removeById(id);
    }

    @GetMapping("/searchByTags")
    public List<User> searchUserByTags( @RequestParam(required = false) List<String> tagNames){
        if (CollectionUtils.isEmpty(tagNames)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return userService.searchUsersByTagsBySql(tagNames);
    }




}

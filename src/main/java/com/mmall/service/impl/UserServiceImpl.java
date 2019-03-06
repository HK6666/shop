package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);
        if (resultCount == 0)
        {
            return ServerResponse.createByErrorMessage("用户名不存在");
        }

        //todo 密码登陆MD5
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.selectLogin(username,password);
        if(user == null)
        {
            return ServerResponse.createByErrorMessage("密码错啦");
        }
           return ServerResponse.createBySuccess("登陆成功",user);


    }
    public ServerResponse<String> register(User user){
        ServerResponse vaildResponse = this.checkValid(user.getUsername(),Const.USERNAME);
        if(!vaildResponse.isSuccess()){
            return vaildResponse;
        }
        vaildResponse = this.checkValid(user.getEmail(),Const.EMAIL);
        if(!vaildResponse.isSuccess()){
            return vaildResponse;
        }

        user.setRole(Const.Role.ROLE_CUSTOMER);
        //MD5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));

        int resultCount = userMapper.insert(user);

        if(resultCount==0)
        {
            return  ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createByErrorMessage("注册成功");

    }
    public  ServerResponse<String> checkValid(String str,String type){
        if(org.apache.commons.lang3.StringUtils.isNoneBlank(str)){
            //开始检验
            if(Const.USERNAME.equals(type)){
                int resultCount = userMapper.checkUsername(str);
                if(resultCount > 0){
                    return ServerResponse.createByErrorMessage("用户名已存在");
                }
            }
            if(Const.EMAIL.equals(type)){
               int resultCount = userMapper.checkEmail(str);

                if(resultCount > 0){
                    return ServerResponse.createByErrorMessage("Email已存在");
                }

            }
        }else{
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return  ServerResponse.createBySuccessMessage("校验成功");

    }
    public ServerResponse selectQuestion(String username){
        ServerResponse validResponse = this.checkValid(username,Const.USERNAME);
        if(validResponse.isSuccess())
        {
            //用户不存在
            return ServerResponse.createByErrorMessage("用户不存在");

        }
        String question = userMapper.selsectQuestionByUsername(username);
        if(org.apache.commons.lang3.StringUtils.isNoneBlank(question)){
            return ServerResponse.createBySuccess(question);
        }
        return ServerResponse.createByErrorMessage("找回密码的问题是空的");

    }

    public ServerResponse<String> checkAnswer(String username,String question,String answer){
        int resultCount = userMapper.checkAnswer(username,question,answer);
        if(resultCount>0)
        {
            //说明是对的
            String forgetToken = UUID.randomUUID().toString();

        }
        return  null;
    }


}

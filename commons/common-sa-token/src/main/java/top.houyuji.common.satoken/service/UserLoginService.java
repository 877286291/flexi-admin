package top.houyuji.common.satoken.service;

import top.houyuji.common.satoken.domain.dto.UserInfoDTO;


public interface UserLoginService {
    /**
     * 根据用户名获取用户信息,管理端登录
     *
     * @param username 用户名
     * @return 用户信息
     */
    UserInfoDTO findByUsername(String username);
}
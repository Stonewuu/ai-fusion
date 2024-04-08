package com.stonewu.aifusion.ssodemo.controller;

import com.stonewu.aifusion.ssodemo.client.UserClient;
import com.stonewu.aifusion.ssodemo.client.dto.CommonResult;
import com.stonewu.aifusion.ssodemo.client.dto.user.UserInfoRespDTO;
import com.stonewu.aifusion.ssodemo.client.dto.user.UserUpdateReqDTO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserClient userClient;

    /**
     * 获得当前登录用户的基本信息
     *
     * @return 用户信息；注意，实际项目中，最好创建对应的 ResponseVO 类，只返回必要的字段
     */
    @GetMapping("/get")
    public CommonResult<UserInfoRespDTO> getUser() {
        return userClient.getUser();
    }

    /**
     * 更新当前登录用户的昵称
     *
     * @param nickname 昵称
     * @return 成功
     */
    @PutMapping("/update")
    public CommonResult<Boolean> updateUser(@RequestParam("nickname") String nickname) {
        UserUpdateReqDTO updateReqDTO = new UserUpdateReqDTO(nickname, null, null, null);
        return userClient.updateUser(updateReqDTO);
    }

}

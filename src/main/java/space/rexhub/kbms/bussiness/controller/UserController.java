package space.rexhub.kbms.bussiness.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import space.rexhub.kbms.bussiness.model.form.CreateUserForm;
import space.rexhub.kbms.bussiness.model.form.RePasswordForm;
import space.rexhub.kbms.bussiness.model.form.RegisterForm;
import space.rexhub.kbms.bussiness.model.form.UserPageForm;
import space.rexhub.kbms.bussiness.model.vo.UserDetailVO;
import space.rexhub.kbms.bussiness.model.vo.UserPageVO;
import space.rexhub.kbms.bussiness.service.UserService;
import space.rexhub.kbms.common.model.vo.Result;

/**
 * Description: 用户Api
 *
 * @author Rex
 * @date 2023-07-03
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("test")
    public Result<String> test(){
        return Result.success("Hello");
    }

    @GetMapping("teststu")
    @PreAuthorize("hasRole('USER')")
    public Result<String> testUser(){
        return Result.success("i am a user");
    }

    @GetMapping("testadmin")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Result<String> testAdmin(){
        return Result.success("i am a administrator");
    }

    /**
     * 获取当前登陆用户信息
     * @return 当前登陆用户信息
     */
    @GetMapping("/user/current")
    public Result<UserDetailVO> getCurrentUser(){
        return Result.success(userService.getCurrentUser());
    }

    /**
     * 注册
     * @param registerForm 注册表单
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result<Void> register(@Validated @RequestBody RegisterForm registerForm){
        userService.register(registerForm);
        return Result.success();
    }
    /**
     * 创建用户
     * @param createUser 注册表单
     * @return 注册结果
     */
    @PostMapping("/admin/user")
    public Result<Void> createUser(@Validated @RequestBody CreateUserForm createUser){
        userService.createUser(createUser);
        return Result.success();
    }
    /**
     * 获取所有用户信息
     * @return 用户信息
     */
    @GetMapping("/admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<IPage<UserPageVO>> getUserPage(@Validated UserPageForm userPageForm){
        return Result.success(userService.getUserPage(userPageForm));
    }

    /**
     * 修改密码
     * @return 用户信息
     */
    @PutMapping ("/repassword")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Result<Void> rePassword(@Validated @RequestBody RePasswordForm form){
        userService.rePassword(form);
        return Result.success();
    }

    /**
     * 重置用户密码
     * @return 用户信息
     */
    @PutMapping ("/admin/resetpwd")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> resetPwd(Long userId){
        userService.resetPwd(userId);
        return Result.success();
    }

}

package space.rexhub.kbms.bussiness.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import space.rexhub.kbms.bussiness.model.entity.UserEntity;
import space.rexhub.kbms.bussiness.model.form.CreateUserForm;
import space.rexhub.kbms.bussiness.model.form.RePasswordForm;
import space.rexhub.kbms.bussiness.model.form.RegisterForm;
import space.rexhub.kbms.bussiness.model.form.UserPageForm;
import space.rexhub.kbms.bussiness.model.vo.UserDetailVO;
import space.rexhub.kbms.bussiness.model.vo.UserPageVO;

import java.util.List;

/**
 * Description: 用户服务类
 *
 * @author Rex
 * @date 2023-07-03
 */
public interface UserService extends IService<UserEntity> {

    /**
     * 获取用户的角色信息
     * @param userId 用户id
     * @return 角色信息
     */
    String getUserAuthorityInfo(Long userId);

    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 用户信息
     */
    UserDetailVO getByUsername(String username);

    /**
     * 获取用户的token
     * @param token 用户token
     * @return 用户id
     */
    Long getUidByToken(String token);

    /**
     * 用户登出
     * @param token 用户token
     */
    void logout(String token);

    /**
     * 根据用户id获取
     * @param userId 用户id
     * @return 用户信息
     */
    UserDetailVO getUserDetail(Long userId);

    /**
     * 用户登陆
     * @param userId 用户id
     * @return token
     */
    String login(Long userId);

    /**
     * 获取当前登陆的用户信息
     * @return  当前登陆的用户信息
     */
    UserDetailVO getCurrentUser();

    /**
     * 用户名注册
     * @param registerForm 注册表单
     */
    void register(RegisterForm registerForm);

    /**
     * 获取用户分页
     * @param form 用户分页表单
     * @return 用户分页数据
     */
    IPage<UserPageVO> getUserPage(UserPageForm form);


    /**
     * 重置用户密码
     * @param userId
     */
    void resetPwd(Long userId);

    /**
     * 修改密码
     * @param form 修改密码表单
     */
    void rePassword(RePasswordForm form);

    /**
     * 创建用户
     * @param createUser 创建用户表单
     */
    void createUser(CreateUserForm createUser);
}

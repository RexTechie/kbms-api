package space.rexhub.kbms.bussiness.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import space.rexhub.kbms.bussiness.dao.UserMapper;
import space.rexhub.kbms.bussiness.model.entity.UserEntity;
import space.rexhub.kbms.bussiness.model.form.CreateUserForm;
import space.rexhub.kbms.bussiness.model.form.RePasswordForm;
import space.rexhub.kbms.bussiness.model.form.RegisterForm;
import space.rexhub.kbms.bussiness.model.form.UserPageForm;
import space.rexhub.kbms.bussiness.model.vo.UserDetailVO;
import space.rexhub.kbms.bussiness.model.vo.UserPageVO;
import space.rexhub.kbms.bussiness.service.UserService;
import space.rexhub.kbms.common.constant.CommonStatus;
import space.rexhub.kbms.common.constant.Constant;
import space.rexhub.kbms.common.exception.CommonException;
import space.rexhub.kbms.common.utils.ConvertUtil;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Description: 用户服务实现类
 *
 * @author Rex
 * @date 2023-07-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 获取用户的角色信息
     * @param userId 用户id
     * @return 角色信息
     */
    @Override
    public String getUserAuthorityInfo(Long userId) {
        StringBuilder stringBuilder = new StringBuilder("ROLE_");
        UserEntity user = getById(userId);
        return stringBuilder.append(baseMapper.getRoleName(user.getRoleId())).toString();
    }

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    @Override
    public UserDetailVO getByUsername(String username) {
        UserEntity user = baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("username", username));
        UserDetailVO userDetailVO = ConvertUtil.copyProperties(user, UserDetailVO.class);
        if (userDetailVO != null) {
            userDetailVO.setRole("ROLE_" + baseMapper.getRoleName(user.getRoleId()));
        }
        return userDetailVO;
    }

    /**
     * 获取用户的token
     *
     * @param token 用户token
     * @return 用户id
     */
    @Override
    public Long getUidByToken(String token) {
        UserEntity user = baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("token", token));
        return user.getId();
    }

    /**
     * 用户登出
     * @param token 用户token
     */
    @Override
    public void logout(String token) {
        UserEntity user = baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("token", token));
        user.setToken("");
        updateById(user);
    }

    /**
     * 根据用户id获取
     *
     * @param userId 用户id
     * @return 用户信息
     */
    @Override
    public UserDetailVO getUserDetail(Long userId) {
        UserEntity user = getById(userId);
        UserDetailVO userDetailVO = ConvertUtil.copyProperties(user, UserDetailVO.class);
        userDetailVO.setRole(baseMapper.getRoleName(user.getRoleId()));
        return userDetailVO;
    }

    /**
     * 用户登陆
     *
     * @param userId 用户id
     * @return token
     */
    @Override
    public String login(Long userId) {
        UserEntity user = getById(userId);
        String token = UUID.randomUUID().toString();
        user.setToken(token);
        user.setLoginTime(new Date());
        updateById(user);
        return token;
    }

    /**
     * 获取当前登陆的用户信息
     *
     * @return 当前登陆的用户信息
     */
    @Override
    public UserDetailVO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return null;
        }
        UserDetailVO user = getByUsername(authentication.getName());
        user.setPassword(null);
        return user;
    }

    /**
     * 用户名注册
     *
     * @param registerForm 注册表单
     */
    @Override
    public void register(RegisterForm registerForm) {
        UserEntity userEntity = new UserEntity();
        String username = registerForm.getUsername();
        UserDetailVO user = getByUsername(username);
        if (user != null) {
            throw new CommonException(CommonStatus.NAME_EXISTS);
        }
        userEntity.setUsername(username);
        String password = bCryptPasswordEncoder.encode(registerForm.getPassword());
        userEntity.setPassword(password);
        // 用户注册均为普通用户
        userEntity.setRoleId(1);
        save(userEntity);
    }

    /**
     * 获取用户分页
     *
     * @param form 用户分页表单
     * @return 用户分页数据
     */
    @Override
    public IPage<UserPageVO> getUserPage(UserPageForm form) {
        IPage<UserEntity> page = form.toPage();
        String username = form.getUsername();
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<UserEntity>()
                .like(StringUtils.hasText(username), "username", username)
                .eq(form.getRoleId() != null, "role_id", form.getRoleId())
                .orderByAsc("role_id");
        IPage<UserEntity> userPage = page(page, queryWrapper);
        return userPage.convert(user -> ConvertUtil.copyProperties(user, UserPageVO.class));
    }


    /**
     * 重置用户密码
     *
     * @param userId 用户id
     */
    @Override
    public void resetPwd(Long userId) {
        UserEntity userEntity = getById(userId);
        if (userEntity == null) {
            throw new CommonException(CommonStatus.USER_NOT_EXIST);
        }
        userEntity.setPassword(bCryptPasswordEncoder.encode(Constant.DEFAULT_PASSWORD));
        updateById(userEntity);
    }

    /**
     * 修改密码
     *
     * @param form 修改密码表单
     */
    @Override
    public void rePassword(RePasswordForm form) {
        UserDetailVO user = getCurrentUser();
        UserEntity currentUser = getById(user.getId());
        boolean match = bCryptPasswordEncoder.matches(form.getOldPassword(), currentUser.getPassword());
        if (!match){
            throw new CommonException(CommonStatus.OLD_PASSWORD_ERROR);
        }else{
            currentUser.setPassword(bCryptPasswordEncoder.encode(form.getNewPassword()));
            updateById(currentUser);
        }
    }

    /**
     * 创建用户
     *
     * @param createUser 创建用户表单
     */
    @Override
    public void createUser(CreateUserForm createUser) {
        UserEntity userEntity = new UserEntity();
        String username = createUser.getUsername();
        UserDetailVO user = getByUsername(username);
        if (user != null) {
            throw new CommonException(CommonStatus.NAME_EXISTS);
        }
        userEntity.setUsername(username);
        userEntity.setNickName(createUser.getNickName());
        String password = bCryptPasswordEncoder.encode("88888888");
        userEntity.setPassword(password);
        // 用户注册均为学生
        userEntity.setRoleId(createUser.getRole());
        save(userEntity);
    }
}

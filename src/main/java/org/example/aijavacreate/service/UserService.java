package org.example.aijavacreate.service;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import jakarta.servlet.http.HttpServletRequest;
import org.example.aijavacreate.model.dto.user.UserQueryRequest;
import org.example.aijavacreate.model.entity.User;
import org.example.aijavacreate.model.vo.LoginUserVO;
import org.example.aijavacreate.model.vo.UserVO;

import java.util.List;

/**
 * 用户 服务层。
 *
 * @author <a href="https://gitee.com/Sszday">Sszday</a>
 */
public interface UserService extends IService<User> {
    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);
    /**
     * 获取加密密码
     *
     * @param userPassword
     * @return
     */
    String getEncryptPassword(String userPassword);

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 获取当前脱敏的登录用户
     *
     * @param user 用户
     * @return 脱敏后的用户信息
     */
    LoginUserVO getLoginUserVO(User user);
    /**
     * 获取当前登录用户
     *
     * @param request 请求对象
     * @return 用户
     */
    User getLoginUser(HttpServletRequest request);
    /**
     * 用户注销(退出登录)
     * @param request 请求对象
     * @return 注销结果
     */
    boolean userLogout(HttpServletRequest request);

    /**
     * 获取脱敏后的用户信息
     *
     * @param user 用户
     * @return 用户
     */
    UserVO getUserVO(User user);

    /**
     * 获取脱敏后的用户信息（分页查询）
     *
     * @param userList 用户列表
     * @return 用户
     */
    List<UserVO> getUserVOList(List<User> userList);

    /**
     * 根据数据查询查询条件构造查询参数
     *
     * @param userQueryRequest 用户查询条件
     * @return 查询条件
     */
    QueryWrapper getQueryWrapper(UserQueryRequest userQueryRequest);
}

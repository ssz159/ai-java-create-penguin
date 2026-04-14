package org.example.aijavacreate.service;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import org.example.aijavacreate.model.entity.App;
import org.example.aijavacreate.model.entity.User;
import org.example.aijavacreate.model.vo.AppQueryRequest;
import org.example.aijavacreate.model.vo.AppVO;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * 应用 服务层。
 *
 * @author <a href="https://gitee.com/Sszday">Sszday</a>
 */
public interface AppService extends IService<App> {
    /**
     * 获取应用VO
     *
     * @param app 应用
     * @return 应用VO
     */
    AppVO getAppVO(App app);

    /**
     * 获取应用VO列表
     *
     * @param appList 应用列表
     * @return 应用VO列表
     */
    List<AppVO> getAppVOList(List<App> appList);

    /**应用部署列表
     * @param appId 应用id
     * @param loginUser 登录用户
     * @return 返回应用部署地址
     */
     String deployApp(Long appId, User loginUser);

    /**
     * 获取查询条件
     *
     * @param appQueryRequest 应用查询条件
     * @return 查询条件
     */
     QueryWrapper getQueryWrapper(AppQueryRequest appQueryRequest);


    /**
     * 聊天生成代码
     * @param appId 应用id
     * @param message 消息内容
     * @param loginUser 登录用户
     * @return 代码流
     */
     Flux<String> chatToGenCode(Long appId, String message, User loginUser);



}

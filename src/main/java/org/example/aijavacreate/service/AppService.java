package org.example.aijavacreate.service;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import org.example.aijavacreate.model.entity.App;
import org.example.aijavacreate.model.vo.AppQueryRequest;
import org.example.aijavacreate.model.vo.AppVO;

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
    public AppVO getAppVO(App app);

    /**
     * 获取应用VO列表
     *
     * @param appList 应用列表
     * @return 应用VO列表
     */
    List<AppVO> getAppVOList(List<App> appList);

    /**
     * 获取查询条件
     *
     * @param appQueryRequest 应用查询条件
     * @return 查询条件
     */
     QueryWrapper getQueryWrapper(AppQueryRequest appQueryRequest);



}

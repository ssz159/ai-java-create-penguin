package org.example.aijavacreate.service.impl;

import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.example.aijavacreate.constant.UserConstant;
import org.example.aijavacreate.exception.ErrorCode;
import org.example.aijavacreate.exception.ThrowUtils;
import org.example.aijavacreate.model.dto.chathistory.ChatHistoryQueryRequest;
import org.example.aijavacreate.model.entity.App;
import org.example.aijavacreate.model.entity.ChatHistory;
import org.example.aijavacreate.mapper.ChatHistoryMapper;
import org.example.aijavacreate.model.entity.User;
import org.example.aijavacreate.model.enums.ChatHistoryMessageTypeEnum;
import org.example.aijavacreate.service.AppService;
import org.example.aijavacreate.service.ChatHistoryService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 对话历史 服务层实现。
 *
 * @author <a href="https://gitee.com/Sszday">Sszday</a>
 */
@Service
public class ChatHistoryServiceImpl extends ServiceImpl<ChatHistoryMapper, ChatHistory>  implements ChatHistoryService{

    @Resource
    protected AppService appService;
    /**
     * 添加对话消息
     *
     * @param appId     应用ID
     * @param message   消息内容
     * @param messageType 消息类型
     * @param userId    用户ID
     * @return 是否添加成功
     */
    @Override
    public boolean addChatMessage(Long appId, String message, String messageType, Long userId) {
        // 验证参数
        ThrowUtils.throwIf(appId == null || appId <= 0, ErrorCode.PARAMS_ERROR, "应用ID不能为空");
        ThrowUtils.throwIf(StrUtil.isBlank(message), ErrorCode.PARAMS_ERROR, "消息内容不能为空");
        ThrowUtils.throwIf(StrUtil.isBlank(messageType), ErrorCode.PARAMS_ERROR, "消息类型不能为空");
        ThrowUtils.throwIf(userId == null || userId <= 0, ErrorCode.PARAMS_ERROR, "用户ID不能为空");
        // 验证消息类型是否有效
        ChatHistoryMessageTypeEnum messageTypeEnum = ChatHistoryMessageTypeEnum.getEnumByValue(messageType);
        ThrowUtils.throwIf(messageTypeEnum == null, ErrorCode.PARAMS_ERROR, "不支持的消息类型: " + messageType);
        // 创建 ChatHistory 对象
        ChatHistory chatHistory = ChatHistory.builder()
                .appId(appId)
                .message(message)
                .messageType(messageType)
                .userId(userId)
                .build();
        return this.save(chatHistory);
    }
    /**
     * 根据应用ID删除对话消息
     *
     * @param appId 应用ID
     * @return 是否删除成功
     */
    @Override
    public boolean deleteByAppId(Long appId) {
        ThrowUtils.throwIf(appId == null || appId <= 0, ErrorCode.PARAMS_ERROR, "应用ID不能为空");
        QueryWrapper queryWrapper = QueryWrapper.create()
                .eq("appId", appId);
        return this.remove(queryWrapper);
    }
    //游标查询对话消息
    /**
     * 获取查询包装类
     *
     * @param chatHistoryQueryRequest
     * @return
     */
    @Override
    public QueryWrapper getQueryWrapper(ChatHistoryQueryRequest chatHistoryQueryRequest) {
        QueryWrapper queryWrapper = QueryWrapper.create();//创建查询条件对象 queryWrapper
        if (chatHistoryQueryRequest == null) {//如果查询参数为空，则返回所有对话消息
            return queryWrapper;
        }
        Long id = chatHistoryQueryRequest.getId();//查询对话消息ID
        String message = chatHistoryQueryRequest.getMessage();//查询对话消息内容
        String messageType = chatHistoryQueryRequest.getMessageType();//查询对话消息类型
        Long appId = chatHistoryQueryRequest.getAppId();//查询应用ID
        Long userId = chatHistoryQueryRequest.getUserId();//查询用户ID
        LocalDateTime lastCreateTime = chatHistoryQueryRequest.getLastCreateTime();//查询游标时间
        String sortField = chatHistoryQueryRequest.getSortField();//查询排序字段
        String sortOrder = chatHistoryQueryRequest.getSortOrder();//查询排序顺序
        // 拼接查询条件
        queryWrapper.eq("id", id)
                .like("message", message)
                .eq("messageType", messageType)
                .eq("appId", appId)
                .eq("userId", userId);
        // 游标查询逻辑 - 只使用 createTime 作为游标
        if (lastCreateTime != null) {
            queryWrapper.lt("createTime", lastCreateTime);
        }
        // 排序
        if (StrUtil.isNotBlank(sortField)) {
            queryWrapper.orderBy(sortField, "ascend".equals(sortOrder));
        } else {
            // 默认按创建时间降序排列
            queryWrapper.orderBy("createTime", false);
        }
        return queryWrapper;
    }
    /**
     * 分页查询应用对话历史
     *
     * @param appId 应用ID
     * @param pageSize 页面大小
     * @param lastCreateTime 最后创建时间
     * @param loginUser 登录用户
     * @return 分页结果
     */
    @Override
    public Page<ChatHistory> listAppChatHistoryByPage(Long appId, int pageSize,
                                                      LocalDateTime lastCreateTime,
                                                      User loginUser) {
        ThrowUtils.throwIf(appId == null || appId <= 0, ErrorCode.PARAMS_ERROR, "应用ID不能为空");
        ThrowUtils.throwIf(pageSize <= 0 || pageSize > 50, ErrorCode.PARAMS_ERROR, "页面大小必须在1-50之间");
        ThrowUtils.throwIf(loginUser == null, ErrorCode.NOT_LOGIN_ERROR);
        // 验证权限：只有应用创建者和管理员可以查看
        App app = appService.getById(appId);
        ThrowUtils.throwIf(app == null, ErrorCode.NOT_FOUND_ERROR, "应用不存在");
        boolean isAdmin = UserConstant.ADMIN_ROLE.equals(loginUser.getUserRole());
        boolean isCreator = app.getUserId().equals(loginUser.getId());
        ThrowUtils.throwIf(!isAdmin && !isCreator, ErrorCode.NO_AUTH_ERROR, "无权查看该应用的对话历史");
        // 构建查询条件
        ChatHistoryQueryRequest queryRequest = new ChatHistoryQueryRequest();
        queryRequest.setAppId(appId);
        queryRequest.setLastCreateTime(lastCreateTime);
        QueryWrapper queryWrapper = this.getQueryWrapper(queryRequest);
        // 查询数据
        return this.page(Page.of(1, pageSize), queryWrapper);
    }
}

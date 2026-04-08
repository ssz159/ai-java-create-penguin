package org.example.aijavacreate.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.example.aijavacreate.model.entity.App;
import org.example.aijavacreate.mapper.AppMapper;
import org.example.aijavacreate.service.AppService;
import org.springframework.stereotype.Service;

/**
 * 应用 服务层实现。
 *
 * @author <a href="https://gitee.com/Sszday">Sszday</a>
 */
@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App>  implements AppService{

}

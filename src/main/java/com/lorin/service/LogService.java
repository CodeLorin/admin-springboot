package com.lorin.service;

import com.lorin.entity.Log;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lorin
 * @since 2021-12-17
 */
public interface LogService extends IService<Log> {
    boolean insertLog(Log log);
}

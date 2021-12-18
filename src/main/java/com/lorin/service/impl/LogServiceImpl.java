package com.lorin.service.impl;

import com.lorin.entity.Log;
import com.lorin.mapper.LogMapper;
import com.lorin.service.LogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lorin
 * @since 2021-12-17
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {
    @Autowired
    LogMapper logMapper;

    @Override
    public boolean insertLog(Log log) {
        int insert = logMapper.insert(log);
        return insert > 0;
    }
}

package com.lorin.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * TODO
 *
 * @author lorin
 * @date 2021/12/17 21:44
 */

@Component
public class PageUtil {
    @Autowired
    HttpServletRequest req;

    /**
     * 获取页码
     */
    public Page getPage() {
        int current = ServletRequestUtils.getIntParameter(req, "pageNum", 1);
        int size = ServletRequestUtils.getIntParameter(req, "pageSize", 10);
        return new Page(current, size);
    }
    /**
     * 获取页码
     */
}

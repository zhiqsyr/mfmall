package org.zhiqsyr.mfmall.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zhiqsyr.mfmall.service.ForumService;

/**
 * @author zhiqsyr
 * @since 2017/6/19
 */
@RestController
@RequestMapping("forum")
public class ForumController {

    @Autowired private ForumService forumService;

    /**
     * 获取论坛信息
     *
     * @return forum
     */
    @GetMapping("get")
    public Object get() {
        return forumService.get();
    }

}

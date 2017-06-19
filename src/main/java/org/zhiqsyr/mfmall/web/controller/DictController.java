package org.zhiqsyr.mfmall.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zhiqsyr.mfmall.domain.entity.Dict;
import org.zhiqsyr.mfmall.service.DictService;

/**
 * @author zhiqsyr
 * @since 2017/6/19
 */
@RestController
@RequestMapping("dict")
public class DictController {

    @Autowired private DictService dictService;

    /**
     * 添加版块类型
     *
     * @param dict {"name":"版块名称", "index":选传, "userId":"创建人ID"}
     * @return dict
     */
    @PostMapping("add/board/category")
    public Object addBoardCategory(@RequestBody Dict dict) {
        return dictService.create(dict, "10000");
    }

    /**
     * 修改<br>
     * 支持：1）修改name、index；2）逻辑删除
     *
     * @param dict
     * @return
     */
    @PutMapping("update")
    public Object update(@RequestBody Dict dict) {
        dictService.modify(dict);
        return null;
    }

}

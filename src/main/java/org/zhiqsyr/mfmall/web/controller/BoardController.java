package org.zhiqsyr.mfmall.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zhiqsyr.mfmall.domain.entity.Board;
import org.zhiqsyr.mfmall.service.BoardService;

/**
 * @author zhiqsyr
 * @since 2017/6/19
 */
@RestController
@RequestMapping("board")
public class BoardController {

    @Autowired private BoardService boardService;

    /**
     * 新增版块
     *
     * @param board
     * @return id
     */
    @PostMapping("add")
    public Object add(@RequestBody Board board) {
        return boardService.create(board);
    }

    /**
     * 修改版块信息<br>
     * 支持：1）修改name、brief等；2）逻辑删除
     *
     * @param board
     * @return
     */
    @PutMapping("update")
    public Object update(@RequestBody Board board) {
        boardService.modify(board);
        return null;
    }

    /**
     * 排序：交换 index
     *
     * @param id
     * @param withId
     * @return
     */
    @PutMapping("exchange/index")
    public Object exchangeIndex(@RequestParam("id") Integer id, @RequestParam("withId") Integer withId) {
        boardService.exchangeIndex(Board.class, id, withId);
        return null;
    }

    /**
     * 排序：拖拽
     *
     * @param id        被拖拽的版块ID
     * @param betweenId 被拖拽到了betweenId和andId之间
     * @param andId
     * @return
     */
    @PutMapping("drag")
    public Object drag(@RequestParam("id") Integer id, @RequestParam("betweenId") Integer betweenId, @RequestParam("andId") Integer andId) {
        boardService.drag(Board.class, id, betweenId, andId);
        return null;
    }

    /**
     * 排序：置顶
     *
     * @param id
     * @return
     */
    @PutMapping("top")
    public Object top(@RequestParam("id") Integer id) {
        boardService.top(Board.class, id);
        return null;
    }

}

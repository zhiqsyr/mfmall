package org.zhiqsyr.mfmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.zhiqsyr.mfmall.dao.BoardRepository;
import org.zhiqsyr.mfmall.domain.entity.Board;
import org.zhiqsyr.mfmall.utils.BeanUtils;

/**
 * @author zhiqsyr
 * @since 2017/6/19
 */
@Service
@Transactional
public class BoardService extends BaseService {

    @Autowired private BoardRepository boardRepository;

    /**
     * 创建版块
     *
     * @param board
     * @return board.id
     */
    public Integer create(Board board) {
        Assert.noNullElements(new Object[]{board.getName(), board.getCategoryId(), board.getCategoryName(), board.getIndex(), board.getUserId(), board.getUserName()}, "Some properties shouldn't be null.");

        boardRepository.save(board);
        return board.getId();
    }

    /**
     * 修改版块
     *
     * @param edit
     */
    public void modify(Board edit) {
        Assert.notNull(edit.getId(), "board.id shouldn't be null.");

        Board board = boardRepository.findOne(edit.getId());
        BeanUtils.copyProperties(edit, board, true);
        boardRepository.save(board);
    }

}

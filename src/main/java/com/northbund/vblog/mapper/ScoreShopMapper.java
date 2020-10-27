package com.northbund.vblog.mapper;

import com.northbund.vblog.pojo.entity.ScoreShop;
import com.northbund.vblog.pojo.param.ScoreShopParam;
import com.northbund.vblog.pojo.vo.ScoreShopResult;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户mapper
 * @author lk
 */
@Repository
public interface ScoreShopMapper {

    List<ScoreShopResult> findAllByParam(ScoreShopParam scoreShopParam);

    Long insert(ScoreShop scoreShop);

    int update(ScoreShop scoreShop);

    int delete(Long id);

    ScoreShop findById(Long id);

}

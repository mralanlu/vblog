package com.northbund.vblog.mapper;

import com.northbund.vblog.pojo.entity.FastNews;
import com.northbund.vblog.pojo.param.FastNewsParam;
import com.northbund.vblog.pojo.vo.FastNewsResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 用户mapper
 * @author lk
 */
@Repository
public interface FastNewsMapper {

    List<FastNewsResult> findAllByParam(FastNewsParam fastNewsParam);

    List<FastNewsResult> getLastDisplayDateFastNews(@Param("displayTime") Date displayTime);

    Long insert(FastNews fastNews);

    int update(FastNews fastNews);

    int delete(Long id);

}

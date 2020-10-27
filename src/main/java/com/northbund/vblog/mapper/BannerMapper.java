package com.northbund.vblog.mapper;

import com.northbund.vblog.pojo.entity.Banner;
import com.northbund.vblog.pojo.param.BannerParam;
import com.northbund.vblog.pojo.vo.BannerResult;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户mapper
 * @author lk
 */
@Repository
public interface BannerMapper {

    List<BannerResult> findAllByParam(BannerParam bannerParam);

    Long insert(Banner banner);

    int update(Banner banner);

    int delete(Long id);

}

package com.northbund.vblog.mapper;

import com.northbund.vblog.pojo.entity.SearchTag;
import com.northbund.vblog.pojo.param.SearchTagParam;
import com.northbund.vblog.pojo.vo.FastNewsResult;
import com.northbund.vblog.pojo.vo.ImageCalendarResult;
import com.northbund.vblog.pojo.vo.SearchTagResult;
import com.northbund.vblog.pojo.vo.WorksCollectionResult;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户mapper
 * @author lk
 */
@Repository
public interface SearchTagMapper {

    List<SearchTagResult> findAllByParam(SearchTagParam searchTagParam);

    Long insert(SearchTag searchTag);

    int update(SearchTag searchTag);

    int delete(Long id);

    /**
     * 搜索影像部落
     * @param searchTag
     * @return
     */
    List<WorksCollectionResult> getSearchResult1(String searchTag);

    /**
     * 搜索快讯
     * @param searchTag
     * @return
     */
    List<FastNewsResult> getSearchResult2(String searchTag);

    /**
     * 搜索影像日历
     * @param searchTag
     * @return
     */
    List<ImageCalendarResult> getSearchResult3(String searchTag);


}

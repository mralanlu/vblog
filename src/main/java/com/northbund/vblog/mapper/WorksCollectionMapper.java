package com.northbund.vblog.mapper;

import com.northbund.vblog.pojo.entity.WorksCollection;
import com.northbund.vblog.pojo.entity.WorksLikesRecord;
import com.northbund.vblog.pojo.param.WorksCollectionParam;
import com.northbund.vblog.pojo.param.WorksLikesRecordParam;
import com.northbund.vblog.pojo.vo.WorksCollectionResult;
import com.northbund.vblog.pojo.vo.WorksLikesRecordResult;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户mapper
 * @author lk
 */
@Repository
public interface WorksCollectionMapper {

    List<WorksCollectionResult> findAllByParam(WorksCollectionParam worksCollectionParam);

    Long insert(WorksCollection worksCollection);

    int update(WorksCollection worksCollection);

    int delete(Long id);

    Long insertLikesRecord(WorksLikesRecord worksLikesRecord);

    int updateLikesRecord(WorksLikesRecord worksLikesRecord);

    List<WorksLikesRecordResult> getLikesRecord(WorksLikesRecordParam worksLikesRecordParam);

    List<WorksCollectionResult> getRecommendedWorks(WorksCollectionParam worksCollectionParam);

}

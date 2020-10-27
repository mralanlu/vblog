package com.northbund.vblog.mapper;

import com.northbund.vblog.pojo.entity.ImageCalendar;
import com.northbund.vblog.pojo.entity.ImageCalendarDetail;
import com.northbund.vblog.pojo.param.ImageCalendarDetailParam;
import com.northbund.vblog.pojo.param.ImageCalendarParam;
import com.northbund.vblog.pojo.vo.ImageCalendarDetailResult;
import com.northbund.vblog.pojo.vo.ImageCalendarResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 用户mapper
 * @author lk
 */
@Repository
public interface ImageCalendarMapper {

    List<ImageCalendarResult> findAllImageCalendarByParam(ImageCalendarParam imageCalendarParam);

    List<ImageCalendarResult> getLastDisplayDateImageCalendar(@Param("displayTime") Date displayTime);

    List<ImageCalendarDetailResult> findAllImageCalendarDetailByParam(ImageCalendarDetailParam imageCalendarDetailParam);

    Long insertImageCalendar(ImageCalendar imageCalendar);

    Long insertImageCalendarDetail(ImageCalendarDetail imageCalendarDetail);

    int updateImageCalendar(ImageCalendar imageCalendar);

    int updateImageCalendarDetail(ImageCalendarDetail imageCalendarDetail);

    int deleteImageCalendar(Long id);

    int deleteImageCalendarDetail(Long id);


}

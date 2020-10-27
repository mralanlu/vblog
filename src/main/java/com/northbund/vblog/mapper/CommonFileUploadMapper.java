package com.northbund.vblog.mapper;

import com.northbund.vblog.pojo.entity.CommonFileUpload;
import com.northbund.vblog.pojo.param.CommonFileUploadParam;
import com.northbund.vblog.pojo.vo.CommonFileUploadResult;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 由GenJavaCode类自动生成
 * <p>
 * mapper层 
 * </p>
 * @date 2019-08-27 15:02
 * @author D1M
 */ 
@Repository
public interface CommonFileUploadMapper {


     List<CommonFileUploadResult> findAllByParams(CommonFileUploadParam commonFileUploadParam);

     List<CommonFileUpload> findEntityListByParams(Long relId, Integer attachmentType);

     void deleteByRelIdAndAttachmentType(Long relId, int attachmentType);

     Long insert(CommonFileUpload commonFileUpload);

     void deleteById(Long id);

     Integer update(CommonFileUpload commonFileUpload);

}


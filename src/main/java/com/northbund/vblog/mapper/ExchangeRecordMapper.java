package com.northbund.vblog.mapper;

import com.northbund.vblog.pojo.entity.ExchangeRecord;
import com.northbund.vblog.pojo.param.ExchangeRecordParam;
import com.northbund.vblog.pojo.vo.ExchangeRecordResult;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户mapper
 * @author lk
 */
@Repository
public interface ExchangeRecordMapper {

    List<ExchangeRecordResult> findAllByParam(ExchangeRecordParam exchangeRecordParam);

    Long insert(ExchangeRecord exchangeRecord);

    int update(ExchangeRecord exchangeRecord);

    int delete(Long id);

}

package com.northbund.vblog.schedule;

import com.northbund.vblog.mapper.UserMapper;
import com.northbund.vblog.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class UserSchedule {

    @Autowired
    UserMapper userMapper;

    @Scheduled(cron = "1 0 0 * * ?")
    //@Scheduled(initialDelay = 2000, fixedRate = 1000 * 60 * 60 * 24)
    public void clearContinueSignIn(){
        log.info("开始处理清空未连续签到用户的签到天数！");
        String yesterday = DateUtil.getYesterdayStr();
        String today = DateUtil.formatYYYYMMDD(new Date());
        userMapper.clearContinueSignIn(yesterday,today);
    }
}

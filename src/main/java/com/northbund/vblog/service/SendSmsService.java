package com.northbund.vblog.service;

import com.northbund.vblog.common.conf.SmsConfigure;
import com.northbund.vblog.common.enums.ResultCodeEnum;
import com.northbund.vblog.common.exceptions.CommonException;
import com.northbund.vblog.common.httpClient.CommonHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class SendSmsService {

    @Autowired
    private SmsConfigure smsConfigure;

    /**
     * 发送短信
     * @param msg
     * @param mobile
     * @return
     */
    public String sendSms(String msg,String mobile){
        try{
            String url = smsConfigure.getBaseUrl()+"HttpSendSM";
            Map<String, String> sendParam = new HashMap<>();
            sendParam.put("acsrccode",smsConfigure.getSubAccount());
            sendParam.put("pswd",smsConfigure.getPassword());
            sendParam.put("mobile",mobile);
            sendParam.put("needstatus","true");
            sendParam.put("msg",msg);
            String result = CommonHttpClient.doGet(url,sendParam);
            log.info("短信发送返回数据："+result.toString());
            String [] resultArray = result.trim().split(",");
            String [] status_msgId = resultArray[1].split("\n");
            String status = status_msgId[0];
            String msgId = status_msgId[1];
            if("0".equals(status)){
                return msgId;
            }else {
                throw new CommonException(ResultCodeEnum.GET_VERIFICATION_UNKNOWN_ERROR);
            }
        }catch (Exception e){
            throw new CommonException(ResultCodeEnum.GET_VERIFICATION_UNKNOWN_ERROR);
        }

    }

    /**
     * 查看短信状态
     * @param msgId
     * @param flag  取值1表示按msgid查询，2表示按时间段查询
     * @param timeStart  格式yyyymmddhhmmss
     * @param timeEnd  格式yyyymmddhhmmss
     * @return
     */
    public String getSmsStatus(String msgId,String flag,String timeStart,String timeEnd){
        try {
            String url = smsConfigure.getBaseUrl()+"HttpQueryReport";
            Map<String, String> sendParam = new HashMap<>();
            sendParam.put("acsrccode",smsConfigure.getSubAccount());
            sendParam.put("pswd",smsConfigure.getPassword());
            sendParam.put("msgid",msgId);
            sendParam.put("start",timeStart);
            sendParam.put("end",timeEnd);
            sendParam.put("flag",flag);
            String result = CommonHttpClient.doGet(url,sendParam);
            log.info("短信返回数据："+result.toString());
            return result;
        }catch (Exception e){
            throw new CommonException(ResultCodeEnum.GET_VERIFICATION_STATUS_UNKNOWN_ERROR);
        }
    }

}

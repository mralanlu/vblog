package com.northbund.vblog.utils.mybatis;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by d1m on 2016/10/19.
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
public class JsonTypeHandler extends BaseTypeHandler<JSONObject> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, JSONObject jsonObject, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, jsonObject.toJSONString());
    }

    @Override
    public JSONObject getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return JSONObject.parseObject(resultSet.getString(s));
    }

    @Override
    public JSONObject getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return JSONObject.parseObject(resultSet.getString(i));
    }

    @Override
    public JSONObject getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return JSONObject.parseObject(callableStatement.getString(i));
    }
}

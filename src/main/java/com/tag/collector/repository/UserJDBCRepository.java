package com.tag.collector.repository;

import com.tag.collector.data.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Slf4j
@RequiredArgsConstructor
@Repository
public class UserJDBCRepository implements UserRepository{
    @Autowired
    private JdbcTemplate crmJdbcTemplate;

    @Override
    public Boolean joinMember(String userId, String username, String password) {
        return ((crmJdbcTemplate.update("INSERT INTO COLLECTOR_USER(USER_ID, USER_NAME, PASSWORD) VALUES(?,?,?)", userId, username, password) == 0) ? false : true);
    }

    @Override
    public Boolean login(String userId, String password) {
        String qry = " SELECT COUNT(*) FROM COLLECTOR_USER WHERE USER_ID = ? AND PASSWORD = ? ";
        int cnt = crmJdbcTemplate.queryForObject(qry, new Object[]{userId, password}, Integer.class);

        if(cnt > 0){
            return true;
        }
        return false;
    }

    @Override
    public Boolean findById(String userId) {
        String qry = " SELECT COUNT(*) FROM COLLECTOR_USER WHERE USER_ID = ? ";
//        System.out.println(":::::::::::::::::::::::" + userId);
        int cnt = crmJdbcTemplate.queryForObject(qry, new String[]{userId}, Integer.class);
        if(cnt > 0){
            return true;
        }
        return false;
    }

    private RowMapper<UserInfo> userRowMapper() {
        return (rs, rowNum) -> {
            UserInfo userInfo = new UserInfo();

            userInfo.setUserId(rs.getString("USER_ID"));
            userInfo.setUserName(rs.getString("USER_NAME"));
            userInfo.setPassword(rs.getString("PASSWORD"));

            return userInfo;
        };
    }
}

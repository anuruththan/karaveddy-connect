package com.example.karaveddy_connect.repository.userAuth.impl;

import com.example.karaveddy_connect.dto.request.UserAuthLoginReq;
import com.example.karaveddy_connect.dto.response.UserAuthLoginResponse;
import com.example.karaveddy_connect.enums.Roles;
import com.example.karaveddy_connect.repository.userAuth.UserAuthConstant;
import com.example.karaveddy_connect.repository.userAuth.UserAuthDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Objects;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserAuthDaoImpl implements UserAuthDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public UserAuthLoginResponse getUserAuthByEmail(String email) {
        UserAuthLoginResponse userAuthLoginResponse = new UserAuthLoginResponse();
        try (Connection connection = DataSourceUtils.getConnection(Objects.requireNonNull(jdbcTemplate.getDataSource())); CallableStatement callableStatement = connection.prepareCall(UserAuthConstant.GET_USER_AUTH_BY_EMAIL)) {
            callableStatement.setString(1, email);

            ResultSet resultSet = callableStatement.executeQuery();

            if (resultSet.next()) {
                userAuthLoginResponse.setUsername(resultSet.getString("rUsername"));
                userAuthLoginResponse.setPassword(resultSet.getString("rPassword"));
                userAuthLoginResponse.setRole(Roles.valueOf(resultSet.getString("rRoles")));
            }
        }

        catch (Exception e) {
            log.error("Error while getting user auth by email {}", e.getMessage());
        }
        return userAuthLoginResponse;
    }

}

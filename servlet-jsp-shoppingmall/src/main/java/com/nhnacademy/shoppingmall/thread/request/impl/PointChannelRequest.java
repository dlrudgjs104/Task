package com.nhnacademy.shoppingmall.thread.request.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.thread.request.ChannelRequest;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Slf4j
public class PointChannelRequest extends ChannelRequest {
    private final String userId;
    private final int userPoint;

    public PointChannelRequest(String userId, int userPoint) {
        this.userId = userId;
        this.userPoint = userPoint;
    }

    @Override
    public void execute() {
        DbConnectionThreadLocal.initialize();
        //todo#14-5 포인트 적립구현, connection은 point적립이 완료되면 반납합니다.
        UserService userService = new UserServiceImpl(new UserRepositoryImpl());
        if(userService.getUser(userId) != null){
            String sql = "update users set user_point = user_point + ? where user_id = ?";
            log.debug("update:{}", sql);
            try(PreparedStatement psmt = DbConnectionThreadLocal.getConnection().prepareStatement(sql)){
                psmt.setInt(1, userPoint);
                psmt.setString(2, userId);
            }catch (SQLException e){
                throw new RuntimeException(e);
            }

            log.debug("pointChannel execute");
        }
        DbConnectionThreadLocal.reset();
    }
}

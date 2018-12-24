package com.his.clinic.repository.mapper;

import com.his.clinic.repository.model.User;

public interface UserMapper {
    int insert(User record);

	User selectByUserId(long userId);
}
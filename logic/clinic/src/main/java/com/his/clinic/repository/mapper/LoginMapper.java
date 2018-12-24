package com.his.clinic.repository.mapper;

import com.his.clinic.repository.model.Login;

public interface LoginMapper {
    int insert(Login record);

	Login selectByAccount(String account);
}
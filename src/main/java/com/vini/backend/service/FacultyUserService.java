package com.vini.backend.service;

import java.util.List;

import com.vini.backend.exception.UserException;
import com.vini.backend.models.Faculty;
import com.vini.backend.response.FacultyResponseDto;

public interface FacultyUserService {

    Faculty findUserById(String userId) throws UserException;
    FacultyResponseDto findUserProfileByJwt(String jwt) throws UserException;
    List<Faculty> findAllUsers();
    FacultyResponseDto findUserProfileByUid(String facultyUid) throws UserException;
}


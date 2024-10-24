package com.vini.backend.service;

import java.util.List;

import com.vini.backend.exception.UserException;
import com.vini.backend.models.Faculty;

public interface FacultyUserService {

    public Faculty findUserById(String userId) throws UserException;

    public Faculty findUserProfileByJwt(String jwt) throws UserException;

    public List<Faculty> findAllUsers();

}


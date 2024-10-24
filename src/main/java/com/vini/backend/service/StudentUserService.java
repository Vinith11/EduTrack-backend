package com.vini.backend.service;

import com.vini.backend.exception.UserException;
import com.vini.backend.models.Student;

import java.util.List;

public interface StudentUserService {

    public Student findUserById(String userId) throws UserException;

    public Student findUserProfileByJwt(String jwt) throws UserException;

    public List<Student> findAllUsers();

}


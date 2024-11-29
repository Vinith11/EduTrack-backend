package com.vini.backend.service;

import com.vini.backend.dto.StudentResponseDto;
import com.vini.backend.exception.UserException;
import com.vini.backend.models.Student;

import java.util.List;

public interface StudentUserService {

    Student findUserById(String userId) throws UserException;

    Student findUserProfileByJwt(String jwt) throws UserException;

    List<Student> findAllUsers();

    List<StudentResponseDto> getStudentsByBatch(String batch);

}


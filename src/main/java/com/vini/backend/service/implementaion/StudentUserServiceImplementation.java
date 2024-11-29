package com.vini.backend.service.implementaion;

import com.vini.backend.config.JwtTokenProvider;
import com.vini.backend.dto.StudentResponseDto;
import com.vini.backend.exception.UserException;
import com.vini.backend.models.Student;
import com.vini.backend.repositories.StudentRepository;
import com.vini.backend.service.StudentUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentUserServiceImplementation implements StudentUserService {

    private final StudentRepository studentRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public StudentUserServiceImplementation(StudentRepository studentRepository, JwtTokenProvider jwtTokenProvider) {
        this.studentRepository = studentRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public Student findUserById(String userId) throws UserException {
        Optional<Student> student = studentRepository.findById(userId);
        if(student.isPresent()){
            return student.get();
        }
        throw new UserException("user not found with id "+userId);
    }

    @Override
    public Student findUserProfileByJwt(String jwt) throws UserException {

        String email = jwtTokenProvider.getEmailFromJwtToken(jwt);
        Student student = studentRepository.findByStudentEmail(email);
        if(student == null){
            throw new UserException("user not exist with email "+email);
        }
        System.out.println("email user"+student.getStudentEmail());
        return student;
    }

    @Override
    public List<Student> findAllUsers() {
        return studentRepository.findAllStudentByOrderByUsn();
    }

    public List<StudentResponseDto> getStudentsByBatch(String batch) {
        List<Student> students = studentRepository.findByStudentBatch(batch);
        return students.stream()
                .map(student -> new StudentResponseDto(
                        student.getUsn(),
                        student.getStudentName(),
                        student.getStudentPhone(),
                        student.getStudentEmail(),
                        student.getStudentBatch(),
                        student.getProjectId()))
                .collect(Collectors.toList());
    }
}

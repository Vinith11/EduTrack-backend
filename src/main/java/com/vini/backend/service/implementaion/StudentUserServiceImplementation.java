package com.vini.backend.service.implementaion;

import com.vini.backend.config.JwtTokenProvider;
import com.vini.backend.response.StudentResponseDto;
import com.vini.backend.exception.UserException;
import com.vini.backend.models.Student;
import com.vini.backend.repositories.StudentRepository;
import com.vini.backend.service.StudentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentUserServiceImplementation implements StudentUserService {

    private final StudentRepository studentRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Student findUserById(String userId) throws UserException {
        Optional<Student> student = studentRepository.findById(userId);
        if(student.isPresent()){
            return student.get();
        }
        throw new UserException("user not found with id "+userId);
    }

    @Override
    public StudentResponseDto findUserProfileByJwt(String jwt) throws UserException {
        String email = jwtTokenProvider.getEmailFromJwtToken(jwt);
        Student student = studentRepository.findByStudentEmail(email)
                .orElseThrow(() -> new UserException("User does not exist with email " + email));

        // Convert Student to StudentResponseDto
        StudentResponseDto studentResponseDto = new StudentResponseDto();
        studentResponseDto.setUsn(student.getUsn());
        studentResponseDto.setStudentName(student.getStudentName());
        studentResponseDto.setStudentPhone(student.getStudentPhone());
        studentResponseDto.setStudentBatch(student.getStudentBatch());
        studentResponseDto.setStudentEmail(student.getStudentEmail());

        return studentResponseDto;
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

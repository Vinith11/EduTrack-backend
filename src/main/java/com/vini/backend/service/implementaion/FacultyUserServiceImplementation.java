package com.vini.backend.service.implementaion;

import com.vini.backend.config.JwtTokenProvider;
import com.vini.backend.exception.UserException;
import com.vini.backend.models.Faculty;
import com.vini.backend.repositories.FacultyRepository;
import com.vini.backend.service.FacultyUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacultyUserServiceImplementation implements FacultyUserService {

    private FacultyRepository facultyRepository;
    private JwtTokenProvider jwtTokenProvider;

    public FacultyUserServiceImplementation(FacultyRepository facultyRepository,JwtTokenProvider jwtTokenProvider) {

        this.facultyRepository=facultyRepository;
        this.jwtTokenProvider=jwtTokenProvider;

    }

    @Override
    public Faculty findUserById(String userId) throws UserException {
        Optional<Faculty> faculty=facultyRepository.findById(userId);

        if(faculty.isPresent()){
            return faculty.get();
        }
        throw new UserException("user not found with id "+userId);
    }

    @Override
    public Faculty findUserProfileByJwt(String jwt) throws UserException {
        System.out.println("user service");
        String email=jwtTokenProvider.getEmailFromJwtToken(jwt);

        System.out.println("email"+email);

        Faculty faculty=facultyRepository.findByFacultyEmail(email);

        if(faculty==null) {
            throw new UserException("user not exist with email "+email);
        }
        System.out.println("email user"+faculty.getFacultyEmail());
        return faculty;
    }

    @Override
    public List<Faculty> findAllUsers() {
        return facultyRepository.findAllFacultyByOrderByFacultyUid();
    }
}

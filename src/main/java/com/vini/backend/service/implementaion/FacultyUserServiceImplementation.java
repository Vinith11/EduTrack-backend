package com.vini.backend.service.implementaion;

import com.vini.backend.config.JwtTokenProvider;
import com.vini.backend.exception.UserException;
import com.vini.backend.models.Faculty;
import com.vini.backend.repositories.FacultyRepository;
import com.vini.backend.response.FacultyResponseDto;
import com.vini.backend.service.FacultyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FacultyUserServiceImplementation implements FacultyUserService {

    private final FacultyRepository facultyRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Faculty findUserById(String userId) throws UserException {
        Optional<Faculty> faculty=facultyRepository.findById(userId);

        if(faculty.isPresent()){
            return faculty.get();
        }
        throw new UserException("user not found with id "+userId);
    }

    @Override
    public FacultyResponseDto findUserProfileByJwt(String jwt) throws UserException {
        System.out.println("user service");
        String email=jwtTokenProvider.getEmailFromJwtToken(jwt);

        System.out.println("email"+email);

        Faculty faculty=facultyRepository.findByFacultyEmail(email)
                .orElseThrow(() -> new UserException("user not found with email "+email));

        FacultyResponseDto facultyResponseDto=new FacultyResponseDto();
        facultyResponseDto.setFacultyEmail(faculty.getFacultyEmail());
        facultyResponseDto.setFacultyName(faculty.getFacultyName());
        facultyResponseDto.setFacultyPhone(faculty.getFacultyPhone());
        facultyResponseDto.setFacultyUid(faculty.getFacultyUid());

        System.out.println("email user"+faculty.getFacultyEmail());
        return facultyResponseDto;
    }

    @Override
    public List<Faculty> findAllUsers() {
        return facultyRepository.findAllFacultyByOrderByFacultyUid();
    }
}

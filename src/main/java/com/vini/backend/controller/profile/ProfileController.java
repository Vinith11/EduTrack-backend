package com.vini.backend.controller.profile;


import com.vini.backend.exception.UserException;
import com.vini.backend.response.FacultyResponseDto;
import com.vini.backend.service.FacultyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProfileController {
    private final FacultyUserService facultyUserService;

    @GetMapping("/faculty-profile/{facultyUid}")
    public ResponseEntity<FacultyResponseDto> getFcaultyProfile(@RequestHeader("Authorization") String jwt, @PathVariable String facultyUid) throws UserException {
        System.out.println("/api/faculty/users/profile");
        FacultyResponseDto faculty = facultyUserService.findUserProfileByUid(facultyUid);
        return new ResponseEntity<>(faculty, HttpStatus.OK);
    }


}

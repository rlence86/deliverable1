package liveproject.m2k8s.web;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import liveproject.m2k8s.Profile;
import liveproject.m2k8s.service.ProfileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private ProfileService profileService;

    @GetMapping(value = "/{username}")
    public Profile showProfile(@PathVariable String username) {
        log.debug("Reading model for: " + username);
        Profile profileFound = profileService.getProfile(username);
        if (profileFound != null) {
            return profileFound;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "");
        }
    }

    @PutMapping(value = "/{username}")
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public void updateProfile(@PathVariable String username, @RequestBody @Valid Profile profile) {
        if (!username.equals(profile.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot change username for Profile");
        }
        log.debug("Updating model for: " + username);
        profileService.update(profile);
    }

    @PostMapping(value = "/{username}")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Transactional
    public void processRegistration(@PathVariable String username, @RequestBody @Valid Profile profile) {
        if (!username.equals(profile.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username in URL and in payload don't match");
        }
        profileService.save(profile);
    }

}

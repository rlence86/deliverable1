package liveproject.m2k8s.service;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import liveproject.m2k8s.Profile;
import liveproject.m2k8s.data.ProfileRepository;
import liveproject.m2k8s.exceptions.ProfileAlreadyExistingException;
import liveproject.m2k8s.exceptions.ProfileNotFoundException;

@Service
public class ProfileService {
    private ProfileRepository profileRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile getProfile(String username) {
        return profileRepository.findByUsername(username);
    }

    @Transactional
    public void save(@Valid Profile profile) {
        Profile dbProfile = profileRepository.findByUsername(profile.getUsername());
        if (dbProfile != null) {
            throw new ProfileAlreadyExistingException();
        }
        profileRepository.save(profile);
    }

    @Transactional
    public void update(@Valid Profile profile) {
        Profile dbProfile = profileRepository.findByUsername(profile.getUsername());
        if (dbProfile == null) {
            throw new ProfileNotFoundException();
        }
        dbProfile.setUsername(profile.getUsername());
        dbProfile.setPassword(profile.getPassword());
        dbProfile.setEmail(profile.getEmail());
        dbProfile.setFirstName(profile.getFirstName());
        dbProfile.setLastName(profile.getLastName());
    }
}

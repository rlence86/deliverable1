package liveproject.m2k8s.service;

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

    public void save(Profile profile) {
        Profile dbProfile = profileRepository.findByUsername(profile.getUsername());
        if (dbProfile != null) {
            throw new ProfileAlreadyExistingException();
        }
        profileRepository.save(profile);
    }

    public void update(Profile profile) {
        Profile dbProfile = profileRepository.findByUsername(profile.getUsername());
        if (dbProfile == null) {
            throw new ProfileNotFoundException();
        }
        boolean dirty = false;
        if (!StringUtils.isEmpty(profile.getEmail())
                && !profile.getEmail().equals(dbProfile.getEmail())) {
            dbProfile.setEmail(profile.getEmail());
            dirty = true;
        }
        if (!StringUtils.isEmpty(profile.getFirstName())
                && !profile.getFirstName().equals(dbProfile.getFirstName())) {
            dbProfile.setFirstName(profile.getFirstName());
            dirty = true;
        }
        if (!StringUtils.isEmpty(profile.getLastName())
                && !profile.getLastName().equals(dbProfile.getLastName())) {
            dbProfile.setLastName(profile.getLastName());
            dirty = true;
        }
        if (dirty) {
            profileRepository.save(profile);
        }
    }
}

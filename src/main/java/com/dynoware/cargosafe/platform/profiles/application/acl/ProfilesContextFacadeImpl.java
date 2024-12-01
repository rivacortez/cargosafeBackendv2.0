package com.dynoware.cargosafe.platform.profiles.application.acl;

import com.dynoware.cargosafe.platform.profiles.domain.model.commands.CreateProfileCommand;
import com.dynoware.cargosafe.platform.profiles.domain.model.queries.GetProfileByEmailQuery;
import com.dynoware.cargosafe.platform.profiles.domain.model.valueobjects.EmailAddress;
import com.dynoware.cargosafe.platform.profiles.domain.services.ProfileCommandService;
import com.dynoware.cargosafe.platform.profiles.domain.services.ProfileQueryService;
import com.dynoware.cargosafe.platform.profiles.interfaces.acl.ProfilesContextFacade;
import org.springframework.stereotype.Service;

@Service
public class ProfilesContextFacadeImpl implements ProfilesContextFacade {
    private final ProfileCommandService profileCommandService;
    private final ProfileQueryService profileQueryService;

    public ProfilesContextFacadeImpl(ProfileCommandService profileCommandService, ProfileQueryService profileQueryService) {
        this.profileCommandService = profileCommandService;
        this.profileQueryService = profileQueryService;
    }

    public Long createProfile(
            String firstName,
            String lastName,
            String email,
            String street,
            String phone,
            String city,
            String country,
            String biography,
            String profileImageUrl) {
        var createProfileCommand = new CreateProfileCommand(
                firstName,
                lastName,
                email,
                street,
                phone,
                city,
                country,
                biography,
                profileImageUrl);
        var profile = profileCommandService.handle(createProfileCommand);
        return profile.isEmpty() ? Long.valueOf(0L) : profile.get().getId();
    }

    public Long fetchProfileIdByEmail(String email) {
        var getProfileByEmailQuery = new GetProfileByEmailQuery(new EmailAddress(email));
        var profile = profileQueryService.handle(getProfileByEmailQuery);
        return profile.isEmpty() ? Long.valueOf(0L) : profile.get().getId();
    }
}
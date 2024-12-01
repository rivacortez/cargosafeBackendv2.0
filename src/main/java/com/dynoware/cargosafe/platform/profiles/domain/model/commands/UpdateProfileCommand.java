package com.dynoware.cargosafe.platform.profiles.domain.model.commands;

public record UpdateProfileCommand(Long profileId,
                                   String firstName,
                                   String lastName,
                                   String email,
                                   String street,
                                   String phone,
                                   String city,
                                   String country,
                                   String biography,
                                   String profileImageUrl) {
}
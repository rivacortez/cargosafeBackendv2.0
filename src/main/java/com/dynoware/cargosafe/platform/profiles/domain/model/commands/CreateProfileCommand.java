package com.dynoware.cargosafe.platform.profiles.domain.model.commands;

/**
 * Create Profile Command
 */
public record CreateProfileCommand(String firstName,
                                   String lastName,
                                   String email,
                                   String street,
                                   String phone,
                                   String city,
                                   String country,
                                   String biography,
                                   String profileImageUrl) {
}
package com.dynoware.cargosafe.platform.profiles.interfaces.rest.resources;

public record UpdateProfileResource(
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
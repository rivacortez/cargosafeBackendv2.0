package com.dynoware.cargosafe.platform.profiles.interfaces.rest.resources;

/**
 * Resource for a profile.
 */
public record ProfileResource(
        Long id,
        String fullName,
        String email,
        String street,
        String phone,
        String city,
        String country,
        String biography,
        String profileImageUrl) {
}
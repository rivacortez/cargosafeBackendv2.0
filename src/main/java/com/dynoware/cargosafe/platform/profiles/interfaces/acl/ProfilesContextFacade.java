package com.dynoware.cargosafe.platform.profiles.interfaces.acl;

/**
 * ProfilesContextFacade
 */
public interface ProfilesContextFacade {
    /**
     * Create a new profile
     * @param firstName The first name
     * @param lastName The last name
     * @param email The email address
     * @param street The street address
     * @param phone The phone number
     * @param city The city
     * @param country The country
     * @param biography The biography
     * @param profileImageUrl The profile image URL
     * @return The profile ID
     */
    Long createProfile(String firstName, String lastName, String email, String street, String phone, String city, String country, String biography, String profileImageUrl);

    /**
     * Fetch a profile ID by email
     * @param email The email address
     * @return The profile ID
     */
    Long fetchProfileIdByEmail(String email);
}
package org.dci.walletapp;

import java.util.Objects;

public class Profile {
    private final String name;
    private final String email;

    private String profileImage;

    public Profile(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }


    public String getProfileImage() {
        return profileImage;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", profileImage='" + profileImage + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return Objects.equals(name, profile.name) && Objects.equals(email, profile.email) && Objects.equals(profileImage, profile.profileImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, profileImage);
    }


}

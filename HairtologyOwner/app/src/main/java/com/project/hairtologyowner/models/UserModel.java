package com.project.hairtologyowner.models;

import java.util.List;

public class UserModel {

    private String uuid;
    private String firstName;
    private String lastName;
    private String email;
    private String country;
    private Boolean isAccountDisabled;
    private List<String> favoriteShopList;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Boolean getAccountDisabled() {
        return isAccountDisabled;
    }

    public void setAccountDisabled(Boolean accountDisabled) {
        isAccountDisabled = accountDisabled;
    }

    public List<String> getFavoriteShopId() {
        return favoriteShopList;
    }

    public void setFavoriteShopId(List<String> favoriteShopList) {
        this.favoriteShopList = favoriteShopList;
    }

}

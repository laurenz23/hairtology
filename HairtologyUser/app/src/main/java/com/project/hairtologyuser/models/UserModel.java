package com.project.hairtologyuser.models;

import java.util.List;

public class UserModel {

    private String uuid;

    private String firstName;

    private String lastName;

    private String email;
    private List<Integer> favoriteShopList;

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

    public List<Integer> getFavoriteShopId() {
        return favoriteShopList;
    }

    public void setFavoriteShopId(List<Integer> favoriteShopList) {
        this.favoriteShopList = favoriteShopList;
    }

}

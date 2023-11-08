package com.project.hairtologyuser.models;

import java.util.ArrayList;

public class UserReservationModel {

    private String userUuid;
    private String userFirstName;
    private String userLastName;
    private ArrayList<ReservationModel> reservationList;

    public UserReservationModel() {}

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public ArrayList<ReservationModel> getReservationList() {
        return reservationList;
    }

    public void setReservationList(ArrayList<ReservationModel> reservationList) {
        this.reservationList = reservationList;
    }

}

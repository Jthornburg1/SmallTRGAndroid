package com.example.jon_thornburg.smalltrgandroid.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by jon_thornburg on 1/11/17.
 */

public class Employee implements Serializable {
    @SerializedName("emp_full_name")
    private String fullName;
    @SerializedName("employee_id")
    private int employee_id;
    @SerializedName("employee_number")
    private String employee_number;
    @SerializedName("emp_office_email_address")
    private String email;
    @SerializedName("emp_office_phone")
    private String phone;
    @SerializedName("emp_office_phone_extension")
    private String phone_ext;
    @SerializedName("seat_id")
    private String seat_id;
    @SerializedName("addName")
    private String adName;
    @SerializedName("emp_discipline_function")
    private String discipline;

    public Employee(String fullName, int employee_id, String employee_number, String email, String phone,
                    String phone_ext, String seat_id, String adName, String discipline) {
        this.fullName = fullName;
        this.employee_id = employee_id;
        this.employee_number = employee_number;
        this.email = email;
        this.phone = phone;
        this.phone_ext = phone_ext;
        this.seat_id = seat_id;
        this.adName = adName;
        this.discipline = discipline;
    }

    //Getters
    public String getFullName() { return fullName; }
    public int getEmployee_id() { return employee_id; }
    public String getEmployee_number() { return employee_number; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getPhone_ext() { return phone_ext; }
    public String getSeat_id() { return seat_id; }
    public String getAdName() { return adName; }
    public String getDiscipline() { return discipline; }

    //Setters
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmployee_id(int employee_id) { this.employee_id = employee_id; }
    public void setEmployee_number(String employee_number) { this.employee_number = employee_number; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setPhone_ext(String phone_ext) { this.phone_ext = phone_ext; }
    public void setSeat_id(String seat_id) { this.seat_id = seat_id; }
    public void setAdName(String adName) { this.adName = adName; }
    public void setDiscipline(String discipline) { this.discipline = discipline; }

    //Get small and large image urlStrings
    public String getSmallImageUrlString() {
        String url = "http://devapps.ad.richards.com/SharedAssets/Employee_Images/Color/Small/" + employee_number + ".jpg";
        return url;
    }

    public String getLargeImageUrlString() {
        String url = "http://devapps.ad.richards.com/SharedAssets/Employee_Images/Color/Large/" + employee_number + ".jpg";
        return url;
    }

    public static String getSmallImageUrlString(String emp_num) {
        String url = "http://devapps.ad.richards.com/SharedAssets/Employee_Images/Color/Small/" + emp_num + ".jpg";
        return url;
    }
}

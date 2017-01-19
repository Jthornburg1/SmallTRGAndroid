package com.example.jon_thornburg.smalltrgandroid.Utils;

import com.example.jon_thornburg.smalltrgandroid.Models.Employee;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by jon_thornburg on 1/11/17.
 */

public interface NetworkInterface {
    @GET("employee")
    @Headers("Content-type: application/json")
    Call<List<Employee>> getEmployees();
}

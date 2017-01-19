package com.example.jon_thornburg.smalltrgandroid.Utils;

import com.example.jon_thornburg.smalltrgandroid.Models.RobinObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by jon_thornburg on 1/13/17.
 */

public interface RoomsInterface {
    @GET("spaces")
    @Headers("Authorization: Access-Token lXJBvOFkSx8CzfvBYSh4xdlLmrcG6dPnoX43wuzqQ9DdWxtScjnBVDkrOSjsr3s4ZfSS6NAJ7Z2hYMRuW0dIfqCyifKN3CxpeQEs7BrjOxXTt59OzMwJMiH2lkKJRcxi")
    Call<RobinObject> getRooms(@Query("location_ids") int ids , @Query("page") int pages, @Query("per_page") int roomsPerPage, @Query("before") String before,
                               @Query("after") String after);
}

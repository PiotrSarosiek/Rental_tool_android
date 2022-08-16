package com.rental_tool.service;

import com.rental_tool.dto.apartment.ApartmentRequestBody;
import com.rental_tool.dto.apartment.ApartmentResponse;
import com.rental_tool.dto.login.LoginResponse;
import com.rental_tool.dto.tenant.TenantResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> loginUser(@Field("username") String username, @Field("password") String password);

    @POST("logout")
    Call<Void> logoutUser();

    @GET("apartment")
    Call<List<ApartmentResponse>> getApartments();

    @POST("apartment")
    Call<ApartmentResponse> createApartment(@Body ApartmentRequestBody apartmentRequestBody);

    @GET("apartment/{id}/tenant")
    Call<List<TenantResponse>> getTenants(@Path("id") int apartmentId);

}

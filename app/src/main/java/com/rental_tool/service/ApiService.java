package com.rental_tool.service;

import com.rental_tool.dto.apartment.ApartmentRequest;
import com.rental_tool.dto.apartment.ApartmentResponse;
import com.rental_tool.dto.login.LoginResponse;
import com.rental_tool.dto.stableBill.StableBillRequest;
import com.rental_tool.dto.stableBill.StableBillResponse;
import com.rental_tool.dto.tenant.TenantRequest;
import com.rental_tool.dto.tenant.TenantResponse;
import com.rental_tool.dto.tenantInvitation.TenantInvitationRequest;
import com.rental_tool.dto.tenantInvitation.TenantInvitationResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
    Call<ApartmentResponse> createApartment(@Body ApartmentRequest apartmentRequestBody);

    @GET("apartment/{id}/tenant")
    Call<List<TenantResponse>> getTenants(@Path("id") long apartmentId);

    @POST("apartment/{id}/tenant")
    Call<TenantResponse> createTenant(@Path("id") long apartmentId, @Body TenantRequest tenantRequest);

    @POST("tenant_invitation")
    Call<TenantInvitationResponse> createTenantInvitation(@Body TenantInvitationRequest tenantInvitationRequest);

    @GET("tenant_invitation/user")
    Call<List<TenantInvitationResponse>> getUserInvitations();

    @FormUrlEncoded
    @PUT("tenant_invitation/{id}")
    Call<TenantInvitationResponse> updateInvitationStatus(@Path("id") long tenantInvitationId, @Field("accepted") Boolean accepted);

    @GET("apartment/assigned_to_tenant")
    Call<ApartmentResponse> getApartmentByTenantAssignedToUser();

    @PUT("apartment/{id}/stable_bill")
    Call<StableBillResponse> updateStableBill(@Path("id") long apartmentId, @Body StableBillRequest stableBillRequest);

}

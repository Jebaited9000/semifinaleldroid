package com.example.semifinal.network

import com.example.semifinal.models.Post
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("/tweet/tesoro")
    fun getPostList(): Call<List<Post>>

    @GET("/tweet/tesoro/{id}")
    fun getPostById(@Path("id") id: String): Call<Post>

    @POST("/tweet/tesoro")
    fun createPost(@Body post: Post): Call<Post>

    @POST("/tweet/tesoro/{id}")
    fun updatePost(@Path("id") id: String): Call<Post>

    @DELETE("/tweet/tesoro/{id}")
    fun deletePost(@Path("id") id: String): Call<Post>

}
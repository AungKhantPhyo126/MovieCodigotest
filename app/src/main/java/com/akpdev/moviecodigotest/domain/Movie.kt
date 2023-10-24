package com.akpdev.moviecodigotest.domain

data class Movie(
    val id:String,
    val movieId:String,
    val title:String,
    val posterPath:String,
    val type:String,
    val overview:String,
    val isFav:Boolean
)

data class LoginApiResponse(
    val response:ResponseDto,
    val data:LoginDto
)

data class ResponseDto(
    val status: String,
    val message: String
)

data class LoginDto(
    val token:String
)


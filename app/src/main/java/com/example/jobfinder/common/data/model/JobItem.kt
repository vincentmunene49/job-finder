package com.example.jobfinder.common.data.model

data class JobItem(
    val jobTitle: String,
    val date: String,
    val status:Boolean,
    val country:String,
    val town:String,
    val image:String,
    val companyName:String,
    val salary:String,
    val frequency:Frequency
)


enum class Frequency{
    MO,
    YR
}
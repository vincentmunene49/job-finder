package com.example.jobfinder.common.util.downloader

interface Downloader {
    fun downloadFile(url: String,name:String):Long
}
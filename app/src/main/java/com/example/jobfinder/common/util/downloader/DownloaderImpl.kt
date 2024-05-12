package com.example.jobfinder.common.util.downloader

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import javax.inject.Inject

class DownloaderImpl @Inject constructor(
    private val context: Context
) : Downloader {

    private val downloadManager = context.getSystemService(DownloadManager::class.java)
    override fun downloadFile(url: String, name: String): Long {
        val regex = "%2F(.*?)\\?".toRegex()
        val matchResult = regex.find(url)
        val fileNameAndExtension = matchResult?.groups?.get(1)?.value ?: ""

        val mimeType = when (fileNameAndExtension.substringAfterLast(".")) {
            "jpg", "jpeg" -> "image/jpeg"
            "png" -> "image/png"
            "gif" -> "image/gif"
            "pdf" -> "application/pdf"
            "doc", "docx" -> "application/msword"
            "xls", "xlsx" -> "application/vnd.ms-excel"
            "ppt", "pptx" -> "application/vnd.ms-powerpoint"
            else -> "*/*" // Fallback MIME type for unknown file types
        }

        val request = DownloadManager
            .Request(url.toUri())
            .setMimeType(mimeType)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle("${name}.pdf")
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "${name}.pdf")
        return downloadManager.enqueue(request)

    }
}
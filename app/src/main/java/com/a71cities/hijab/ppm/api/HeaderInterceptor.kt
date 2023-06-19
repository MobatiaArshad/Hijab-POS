package com.a71cities.hijab.ppm.api

import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.internal.http2.ConnectionShutdownException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class HeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        try {
            return chain.proceed(
                chain.request()
                    .newBuilder()
                    .build()
            )
        } catch (e: IOException) {
            e.printStackTrace()
            var msg = ""
            val statusCode = 401
            msg = when (e) {
                is SocketTimeoutException -> {
                    "Timeout - Please check your internet connection"
                }
                is UnknownHostException -> {
                    "Unable to make a connection. Please check your internet"
                }
                is ConnectionShutdownException -> {
                    "Connection shutdown. Please check your internet"
                }
                else -> {
                    "Server is unreachable, please try again later."
                }
            }

            val message = "{ \"statusCode\": $statusCode,\"message\": \"$msg\" }"

            return Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(statusCode)
                .message(message)
                .body(message.toResponseBody(null)).build()
        }
    }
}
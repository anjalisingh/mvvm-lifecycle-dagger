package com.solutions.mvvmlifeycledagger.network.repository

import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException
import java.lang.Exception

/**
 * Created by anjalisingh on 14,January,2019
 */

class Resource<T> (val status: Resource.Status, val data: T?, val exception: RetrofitException?
                   , val error : AppException?) {
    enum class Status {
        SUCCESS, RETROFIT_ERROR, ERROR
    }

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null, null)
        }

        fun <T> exception(exception: RetrofitException?): Resource<T> {
            return Resource(Status.RETROFIT_ERROR, null, exception, null)
        }

        fun <T> error(exception: AppException?): Resource<T> {
            return Resource(Status.ERROR, null, null, exception)
        }
    }

    class AppException(val exception: Throwable?) : Exception()
}

 class RetrofitException(message: String?, url: String?, val response: Response<*>?, val kind: Kind?,
                         exception: Throwable?, val retrofit: Retrofit?) : Exception(message, exception) {


     companion object {
         fun httpError(url: String, response: Response<*>, retrofit: Retrofit): RetrofitException {
             val message = response.code().toString() + " " + response.message()
             return RetrofitException(message, url, response, Kind.HTTP, null, retrofit)
         }

         fun networkError(exception: IOException): RetrofitException {
             return RetrofitException(exception.message, null, null, Kind.NETWORK, exception, null)
         }

         fun unexpectedError(exception: Throwable): RetrofitException {
             return RetrofitException(
                 exception.message,
                 null, null, Kind.UNEXPECTED, exception, null
             )
         }

     }

    enum class Kind {
        /** An [IOException] occurred while communicating to the server.  */
        NETWORK,

        /** A non-200 HTTP status code was received from the server.  */
        HTTP,

        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED
    }

    @Throws(IOException::class)
    fun <T> getErrorBodyAs(type: Class<T>): T? {
        if (response?.errorBody() == null) {
            return null
        }
        val converter = retrofit?.responseBodyConverter<T>(type, arrayOfNulls(0))
        return converter?.convert(response.errorBody())
    }
}

package com.example.myntra.comman

import androidx.core.app.NotificationCompat.MessagingStyle.Message

sealed class ResultState<out T>{
data class Succes<T>(val data:T):ResultState<T>()
data class error<T>(val message:String):ResultState<T>()
    data object Loading:ResultState<Nothing>()

}
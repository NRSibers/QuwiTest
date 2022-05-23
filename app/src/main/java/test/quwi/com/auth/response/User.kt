package test.quwi.com.auth.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class User (
    val id: Long,
    val name: String,
    val nick: String?,
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    @SerializedName("dta_create")
    val dtaCreate: String?,
    @SerializedName("timezone_offset")
    val timezoneOffset: Long,
    @SerializedName("is_online")
    val isOnline: Int,
    @SerializedName("is_chat_email_notification")
    val isChatEmailNotification: Boolean,
    @SerializedName("dta_activity")
    val dtaActivity: String,
    @SerializedName("is_active")
    val isActive: Boolean,
    @SerializedName("id_company")
    val idCompany: String,
    val role: String,
    @SerializedName("due_penalties")
    val duePenalties : Long,
    @SerializedName("is_timer_online")
    val isTimerOnline: Int,
    @SerializedName("dta_timer_activity")
    val dtaTimerActivity: String?,
    @SerializedName("timer_last")
    val timerLast: String?,
    @SerializedName("is_shared_from_me")
    val isSharedFromMe: Boolean,
    val projects: List<Project>,
    val email: String,
    @SerializedName("is_telegram_connected")
    val isTelegramConnected: Boolean
)
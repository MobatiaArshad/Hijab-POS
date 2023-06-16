package com.a71cities.hijab.ppm.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson

object Pref {
//    private const val NAME = BuildConfig.APPLICATION_ID + ".1"
    private const val NAME = "com.a71cities.hijab.ppm"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    private const val TOKEN = "TOKEN"
    private const val USER_ID = "USER_ID"
    private const val EMAIL = "EMAIL"
    private const val COUNTRY_CODE = "COUNTRY_CODE"
    private const val COUNTRY_NAME = "COUNTRY_NAME"
    private const val CURRENCY_CODE = "CURRENCY_CODE"
    private const val LOGGED = "ALREADY"
    private const val PROFILE = "PROFILE"
    private const val USER_NAME = "USER_NAME"
    private const val NAME_ = "NAME__"
    private const val FIREBASE_TOKEN = "FIREBASE_TOKEN"
    private const val LAT = "LAT"
    private const val LNG = "LNG"
    private const val PROFILE_IMAGE = "PROFILE_IMAGE"
    private const val TUTORIAL = "TUTORIAL"
    private const val ON_BOARD_CMPLTD = "ON_BOARD_CMPLTD"
    private const val STEP_API = "STEP_API"
    private const val HOME_TOUR = "HOME_TOUR"
    private const val CONTENT_MANAGER_TUTORIAL = "contentManagerTutorial"
    private const val SWIPE_TUTORIAL_SHOWN = "swipeTutorialShown"
    private const val CONTENT_MANAGER_STEP_ONE_NAV = "IS_FROM_CAMPAIGN"
    private const val CAMPAIGN_ID_CONTENT_MANAGER = "CAMPAIGN_ID_CONTENT_MANAGER"
    private const val CAMPAIGN_BACK_STACK = "CAMPAIGN_BACK_STACK"
    private const val OVER_VIEW_BACK_STACK = "OVER_VIEW_BACK_STACK"
    private const val IS_FROM_CAMPAIGN_DETAIL = "IS_FROM_CAMPAIGN_DETAIL"
    private const val IS_DEVICE_TOKEN_UPDATED = "IS_DEVICE_TOKEN_UPDATED"
    private const val FROM_NEXT_PAGE = "FROM_NEXT_PAGE"
    private const val NOTIFY_AVAILABLE = "NOTIFY_AVAILABLE"
    private const val MSG_AVAILABLE = "MSG_AVAILABLE"

    const val USER_ALLOW_NOTIFICATION_PERMISSION = "USER_ALLOW_NOTIFICATION_PERMISSION"
    const val USER_PUSH_NOTIFICATION_PERMISSION = "USER_PUSH_NOTIFICATION_PERMISSION"
    const val USER_LOCATION_PERMISSION = "USER_LOCATION_PERMISSION"
    const val USER_ALLOW_NEW_CAMPAINS = "USER_ALLOW_NEW_CAMPAINS"
    const val USER_ALLOW_NEW_EVENTS = "USER_ALLOW_NEW_EVENTS"
    const val USER_ALLOW_NEW_MESSAGES = "USER_ALLOW_NEW_MESSAGES"
    const val USER_RECOMMENDATIONS_TIPS = "USER_RECOMMENDATIONS_TIPS"
    const val USER_CONTENT_FEEDBACK = "USER_CONTENT_FEEDBACK"
    const val USER_BIDS_COUNTEROFFER = "USER_BIDS_COUNTEROFFER"
    const val CAMPAIGN_TUTORIAL_SHOWN = "campaignTutorialShown"
    const val CONTACT_RECIPIENTS = "contactRecipients"
    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    fun clear() {
        preferences.edit {
            it.clear()
            it.apply()
        }
    }

    fun setBooleanDataWithDynamicKey(key:String,value: Boolean)
    {
        preferences.edit {
            it.putBoolean(key, value)
        }
    }
    fun getBooleanDataWithDynamicKey(key:String):Boolean
    {
      return  preferences.getBoolean(key, false)
    }

    var hasUserAllowNotification: Boolean
        get() = preferences.getBoolean(USER_ALLOW_NOTIFICATION_PERMISSION, false)
        set(value) = preferences.edit {
            it.putBoolean(USER_ALLOW_NOTIFICATION_PERMISSION, value)
        }
    var isSwipeTutorialShown: Boolean
        get() = preferences.getBoolean(SWIPE_TUTORIAL_SHOWN, false)
        set(value) = preferences.edit {
            it.putBoolean(SWIPE_TUTORIAL_SHOWN, value)
        }

    var hasUserAllowContentFeedBack: Boolean
        get() = preferences.getBoolean(USER_CONTENT_FEEDBACK, false)
        set(value) = preferences.edit {
            it.putBoolean(USER_CONTENT_FEEDBACK, value)
        }

    var hasUserAllowedPushNotification: Boolean
        get() = preferences.getBoolean(USER_PUSH_NOTIFICATION_PERMISSION, false)
        set(value) = preferences.edit {
            it.putBoolean(USER_PUSH_NOTIFICATION_PERMISSION, value)
        }
    var fromCreateContentTo: Int
        get() = preferences.getInt(CONTENT_MANAGER_STEP_ONE_NAV, 1)
        set(value) = preferences.edit {
            it.putInt(CONTENT_MANAGER_STEP_ONE_NAV, value)
        }

    var isFromCampaign: Boolean
        get() = preferences.getBoolean(IS_FROM_CAMPAIGN_DETAIL, false)
        set(value) = preferences.edit {
            it.putBoolean(IS_FROM_CAMPAIGN_DETAIL, value)
        }
    var isFromNextPage: Boolean
        get() = preferences.getBoolean(FROM_NEXT_PAGE, false)
        set(value) = preferences.edit {
            it.putBoolean(FROM_NEXT_PAGE, value)
        }

    var hasUserAllowedLocation: Boolean
        get() = preferences.getBoolean(USER_LOCATION_PERMISSION, false)
        set(value) = preferences.edit {
            it.putBoolean(USER_LOCATION_PERMISSION, value)
        }

    var hasUserAllowedNewCampains: Boolean
        get() = preferences.getBoolean(USER_ALLOW_NEW_CAMPAINS, false)
        set(value) = preferences.edit {
            it.putBoolean(USER_ALLOW_NEW_CAMPAINS, value)
        }

    var hasUserAllowedNewEvents: Boolean
        get() = preferences.getBoolean(USER_ALLOW_NEW_EVENTS, false)
        set(value) = preferences.edit {
            it.putBoolean(USER_ALLOW_NEW_EVENTS, value)
        }

    var isContactRecipientsShown: Boolean
        get() = preferences.getBoolean(CONTACT_RECIPIENTS, false)
        set(value) = preferences.edit {
            it.putBoolean(CONTACT_RECIPIENTS, value)
        }
    var hasUserAllowedNewMessage: Boolean
        get() = preferences.getBoolean(USER_ALLOW_NEW_MESSAGES, false)
        set(value) = preferences.edit {
            it.putBoolean(USER_ALLOW_NEW_MESSAGES, value)
        }
    var hasUserAllowedRecommentedTips: Boolean
        get() = preferences.getBoolean(USER_RECOMMENDATIONS_TIPS, false)
        set(value) = preferences.edit {
            it.putBoolean(USER_RECOMMENDATIONS_TIPS, value)
        }

    var hasUserAllowedBidsAndCounterOffers: Boolean
        get() = preferences.getBoolean(USER_BIDS_COUNTEROFFER, false)
        set(value) = preferences.edit {
            it.putBoolean(USER_BIDS_COUNTEROFFER, value)
        }


    var accessToken: String
        get() = preferences.getString(TOKEN, "0")!!
        set(value) = preferences.edit {
            it.putString(TOKEN, value)
        }

    var isLogged: Boolean
        get() = preferences.getBoolean(LOGGED, false)
        set(value) = preferences.edit {
            it.putBoolean(LOGGED, value)
        }

    var isOnboardCompleted: Boolean
        get() = preferences.getBoolean(ON_BOARD_CMPLTD, false)
        set(value) = preferences.edit {
            it.putBoolean(ON_BOARD_CMPLTD, value)
        }

    var showHomeTour: Boolean
        get() = preferences.getBoolean(HOME_TOUR, false)
        set(value) = preferences.edit {
            it.putBoolean(HOME_TOUR, value)
        }

    var isDeviceTokenUpdated: Boolean
        get() = preferences.getBoolean(IS_DEVICE_TOKEN_UPDATED, false)
        set(value) = preferences.edit {
            it.putBoolean(IS_DEVICE_TOKEN_UPDATED, value)
        }

//    var userId: Int
//        get() = preferences.getInt(USER_ID, -1)!!
//        set(value) = preferences.edit {
//            it.putInt(USER_ID, value)
//        }

    var countryId: String
        get() = preferences.getString(COUNTRY_CODE, null)!!
        set(value) = preferences.edit {
            it.putString(COUNTRY_CODE, value)
        }

    var countryName: String
        get() = preferences.getString(COUNTRY_NAME, "")!!
        set(value) = preferences.edit {
            it.putString(COUNTRY_NAME, value)
        }

    var currencyCode: String
        get() = preferences.getString(CURRENCY_CODE, "")!!
        set(value) = preferences.edit {
            it.putString(CURRENCY_CODE, value)
        }

    var name: String
        get() = preferences.getString(NAME_, null)!!
        set(value) = preferences.edit {
            it.putString(NAME_, value)
        }

    var userName: String
        get() = preferences.getString(USER_NAME, null)!!
        set(value) = preferences.edit {
            it.putString(USER_NAME, value)
        }

    var firebaseToken: String
        get() = preferences.getString(FIREBASE_TOKEN, "0")!!
        set(value) = preferences.edit {
            it.putString(FIREBASE_TOKEN, value)
        }

    var latitude: String
        get() = preferences.getString(LAT, "")!!
        set(value) = preferences.edit {
            it.putString(LAT, value)
        }

    var longitude: String
        get() = preferences.getString(LNG, "")!!
        set(value) = preferences.edit {
            it.putString(LNG, value)
        }

    var isTutorialCompleted: Boolean
        get() = preferences.getBoolean(TUTORIAL, false)
        set(value) = preferences.edit {
            it.putBoolean(TUTORIAL, value)
        }

//    var profile: Profile?
//        get() {
//            val s = preferences.getString(PROFILE, null) ?: return null
//            return Gson().fromJson(s, Profile::class.java)
//        }
//        set(value) = preferences.edit {
//            if (value == null)
//                it.putString(PROFILE, null)
//            else
//                it.putString(PROFILE, Gson().toJson(value))
//        }


    var proPic: String
        get() = preferences.getString(PROFILE_IMAGE, "")!!
        set(value) = preferences.edit {
            it.putString(PROFILE_IMAGE, value)
        }


    var needApi: Boolean
        get() = preferences.getBoolean(STEP_API, false)
        set(value) = preferences.edit {
            it.putBoolean(STEP_API, value)
        }

    var showContentManagerTutorial: Boolean
        get() = preferences.getBoolean(CONTENT_MANAGER_TUTORIAL, false)
        set(value) = preferences.edit {
            it.putBoolean(CONTENT_MANAGER_TUTORIAL, value)
        }

    var isCampaignTutorialShown: Boolean
        get() = preferences.getBoolean(CAMPAIGN_TUTORIAL_SHOWN, false)
        set(value) = preferences.edit {
            it.putBoolean(CAMPAIGN_TUTORIAL_SHOWN, value)
        }

    var msgInvisible: Boolean
        get() = preferences.getBoolean(MSG_AVAILABLE, false)
        set(value) = preferences.edit {
            it.putBoolean(MSG_AVAILABLE, value)
        }

    var notifyInvisible: Boolean
        get() = preferences.getBoolean(NOTIFY_AVAILABLE, false)
        set(value) = preferences.edit {
            it.putBoolean(NOTIFY_AVAILABLE, value)
        }
}
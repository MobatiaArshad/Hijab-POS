package com.a71cities.hijab.ppm.extras

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.graphics.pdf.PdfRenderer
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.*
import android.os.Build.VERSION.SDK_INT
import android.os.ext.SdkExtensions.getExtensionVersion
import android.provider.MediaStore
import android.provider.Settings
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.util.Base64
import android.util.Log
import android.util.Patterns
import android.util.TypedValue
import android.view.*
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.text.toSpannable
import androidx.core.view.*
import androidx.core.widget.NestedScrollView
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.a71cities.hijab.ppm.MainActivity
import com.a71cities.hijab.ppm.R
import com.a71cities.hijab.ppm.databinding.ImageChooserAlertLytBinding
import com.a71cities.hijab.ppm.databinding.SingleBtnAlertBinding
import com.a71cities.hijab.ppm.extras.Constants.LOG
import com.bumptech.glide.Glide
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.a71cities.hijab.ppm.utils.Pref
import com.fondesa.kpermissions.PermissionStatus
import com.fondesa.kpermissions.request.PermissionRequest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.net.URLConnection
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.reflect.KFunction0


const val GALLERY_IMAGE_REQ_CODE = 102
const val CAMERA_IMAGE_REQ_CODE = 103

fun TabLayout.onTabSelect(function: (selectedTabPosition: Int) -> Unit) {
    addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            function?.invoke(selectedTabPosition)
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {

        }

        override fun onTabReselected(tab: TabLayout.Tab?) {

        }

    })

}


fun Array<out View>.onclickSingleSelection(
    defaultSelected: View? = null,
    invokeCallbackOnDefaultSelection: Boolean = true,
    callBack: ((selecte_view: View) -> Unit)? = null,

    ) {

    fun select(v: View, invokeCallback: Boolean = true) {
        forEach {
            it.isSelected = it == v
            if (it.isSelected && invokeCallback) {
                callBack?.invoke(v)
            }
            println("invokeCallback $invokeCallback $v")
        }
    }
    forEach {
        it.setOnClickListener { select(it, true) }
    }
    if (defaultSelected != null)
        select(defaultSelected, invokeCallbackOnDefaultSelection)


}


fun Array<out CompoundButton>.onSingleCheck(
    defaultSelected: CompoundButton? = null,
    invokeCallbackOnDefaultSelection: Boolean = true,
    callBack: ((selecte_view: CompoundButton) -> Unit)? = null,

    ) {

    fun select(v: CompoundButton, invokeCallback: Boolean = true) {
        forEach {
            it.isChecked = it == v
            if (invokeCallback) {
                callBack?.invoke(v)
            }
            println("invokeCallback $invokeCallback $v")
        }
    }
    forEach {
        it.setOnClickListener { _ -> select(it, true) }
    }
    if (defaultSelected != null)
        select(defaultSelected, invokeCallbackOnDefaultSelection)


}


fun Array<out View>.select1(
    defaultSelected: View
) {
    forEach {
        it.isSelected = it == defaultSelected

    }
}


/*fun ImageView?.fullScreen(list: Collection<String?>, position: Int = 0) {
    if (this != null) {
        setOnClickListener {
            StfalconImageViewer.Builder<String>(context, list.toList()) { view, image ->
                view.load(image)
            }
                .withStartPosition(position)
                .show()
        }

    }

}*/


fun convertDate(s: String?, fIn: String, fOut: String): String {
    return try {
        SimpleDateFormat(fOut, Locale.ENGLISH).format(
            SimpleDateFormat(fIn, Locale.ENGLISH).parse(s)
        )
    } catch (e: Exception) {
        s ?: ""
    }
}


fun TextView?.isEllipsized(): Boolean {


    Log.d("isEllipsizedd", "${this?.lineCount}")
    this?.let { textview1 ->
        val layout = textview1.getLayout();
        if (layout != null) {
            val lines = layout.getLineCount();
            if (lines > 0) {
                val ellipsisCount = layout.getEllipsisCount(lines - 1);
                if (ellipsisCount > 0) {
                    Log.d("TAG", "Text is ellipsized");
                    return true
                }
            }
        }
    }
    return false
}


internal fun EditText.setEditable(isEditable: Boolean) {
    isEnabled = isEditable
    isClickable = isEditable
    isFocusableInTouchMode = isEditable
    isFocusable = isEditable
}

internal fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

internal fun ViewGroup.addView(@LayoutRes layoutRes: Int): View {
    val v = LayoutInflater.from(context).inflate(layoutRes, this, false)
    addView(v)
    return v
}


val TextView?.isTextEmpty: Boolean
    get() {
        return this?.text?.toString()?.trim()?.isEmpty() ?: true
    }


val TextView?.isValidEmail: Boolean
    get() {
        return Patterns.EMAIL_ADDRESS.matcher(this?.text?.toString()).matches()
    }
//
//val CharSequence?.isValidEmail: Boolean
//    get() {
//        return Patterns.EMAIL_ADDRESS.matcher(this!!).matches()
//    }

fun isValidEmail(input: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(input).matches()


val TextView?.isValidPhone: Boolean
    get() {
        return Patterns.PHONE.matcher(this?.text?.toString()).matches()
    }


val TextView?.hasText: Boolean
    get() {
        return this?.text?.toString()?.isNotBlank() ?: false
    }


fun View?.fixHeight() {
    this?.post {
        this?.layoutParams?.height = this.height
    }
}


/*fun ImageView?.loadUrl(url: String?, callback: Callback? = null, errorHolder: Int? = null) {
    if (this == null) return
    if ((url.isNullOrBlank() && errorHolder != null) || (getTag(R.string.image_error) == true && errorHolder != null)) {
        setImageResource(errorHolder)
    } else {
        val picas = Picasso.get().load(BASE_URL_IMAGE + url).noFade()

        picas.into(this, object : Callback {
            override fun onError(e: java.lang.Exception?) {
                setTag(R.string.isDissolvEffectDone, true)
                callback?.onError(e)
                setTag(R.string.image_error, true)
                if (errorHolder != null) setImageResource(errorHolder)
            }

            override fun onSuccess() {
                if (getTag(R.string.isDissolvEffectDone) != true) {
                    val fadeOut = AlphaAnimation(0f, 1f)
                    fadeOut.interpolator = AccelerateInterpolator()
                    fadeOut.duration = 750
                    startAnimation(fadeOut)
                }

                setTag(R.string.isDissolvEffectDone, true)
                callback?.onSuccess()
            }

        })
    }


}*/


/*
fun ImageView?.loadProfileImage(url: String?, callback: Callback? = null) {
    if (this == null) return
*/
/*    if (url == null || url.trim().isBlank() || this.getTag(R.string.image_error) == true) {
        *//*
*/
/* IMAGE Error*//*
*/
/*
        this.setTag(R.string.image_error, true)
        Picasso.get().load(BASE_URL_IMAGE + url).*//*
*/
/*placeholder(R.drawable.avatar).error_red(R.drawable.avatar).*//*
*/
/*into(this, object : Callback {
            override fun onSuccess() {
                if (getTag(R.string.imageLoaded) != true) {
                    setAlpha(0f)
                    animate().setDuration(250).alpha(1f).start();
                }

                setTag(R.string.imageLoaded, true)
                callback?.onSuccess()
            }

            override fun onError(e: java.lang.Exception?) {
                setTag(R.string.imageLoaded, true)
                callback?.onError(e)
                this@loadProfileImage.setTag(R.string.image_error, true)
            }

        })

    } else {
        Picasso.get().load(BASE_URL_IMAGE + url)*//*
*/
/*.error_red(R.drawable.avatar)*//*
*/
/*.into(this, object : Callback {
            override fun onSuccess() {
                if (getTag(R.string.imageLoaded) != true) {
                    setAlpha(0f)
                    animate().setDuration(250).alpha(1f).start();
                }

                setTag(R.string.imageLoaded, true)
                callback?.onSuccess()
            }

            override fun onError(e: java.lang.Exception?) {
                setTag(R.string.imageLoaded, true)
                callback?.onError(e)
                this@loadProfileImage.setTag(R.string.image_error, true)
            }

        })
    }*//*




    Picasso.get().load(BASE_URL_IMAGE + url).noFade()*/
/*.error_red(R.drawable.avatar)*//*
.into(this, object : Callback {
        override fun onSuccess() {
            if (getTag(R.string.isDissolvEffectDone) != true) {
                val fadeOut = AlphaAnimation(0f, 1f)
                fadeOut.interpolator = AccelerateInterpolator()
                fadeOut.duration = 750
                startAnimation(fadeOut)
            }

            setTag(R.string.isDissolvEffectDone, true)
            callback?.onSuccess()
        }

        override fun onError(e: java.lang.Exception?) {
            setTag(R.string.isDissolvEffectDone, true)
            callback?.onError(e)
            setTag(R.string.image_error, true)
        }

    })
}
*/


fun JsonObject.addProperty(s: String, editText: TextView?) {
    addProperty(s, editText?.text?.toString()?.trim())

}


fun isSameDay(cal1: Calendar?, cal2: Calendar?): Boolean {
    return cal1 != null &&
            cal2 != null &&
            cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
            cal1.get(Calendar.DATE) == cal2.get(Calendar.DATE)
}


fun Calendar.isTodayOrTomeorrow(): Boolean {
    return this != null && (isSameDay(this, Calendar.getInstance()) ||
            isSameDay(this, Calendar.getInstance().apply { add(Calendar.DATE, 1) }))
}


fun Calendar.isTodayOrTomeorrowOrDayAfterTomorrow(): Boolean {
    return this != null && (
            isSameDay(this, Calendar.getInstance())
                    ||
                    isSameDay(this, Calendar.getInstance().apply { add(Calendar.DATE, 1) })
                    ||
                    isSameDay(this, Calendar.getInstance().apply { add(Calendar.DATE, 2) })
            )
}


fun String?.convertApiDateToDisplayDate(): String {
    return if (this == null || this.isBlank() || this == "0000-00-00") {
        ""

    } else {
        try {

            SimpleDateFormat("d-MM-yyyy", Locale.ENGLISH).format(
                SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(
                    this
                )
            )
        } catch (e: Exception) {
            ""
        }
    }
}

fun String?.convertMessageDateToDisplayDate(): String {
    return if (this == null || this.isBlank() || this == "0000-00-00") {
        ""
    } else {
        try {
            val originalDate = Instant.parse(this)
            val dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' h:mm a")

            val localDateTime = originalDate.atZone(ZoneId.systemDefault()).toLocalDateTime()
            dateFormatter.format(localDateTime)
        } catch (e: Exception) {
            ""
        }
    }
}

fun String?.convertMessageDateToDisplayDate(format: String): String {
    return if (this == null || this.isBlank() || this == "0000-00-00") {
        ""
    } else {
        try {
            val originalDate = Instant.parse(this)
            val dateFormatter = DateTimeFormatter.ofPattern(format)

            val localDateTime = originalDate.atZone(ZoneId.systemDefault()).toLocalDateTime()
            dateFormatter.format(localDateTime)
        } catch (e: Exception) {
            ""
        }
    }
}

fun String?.convertApiDateToCalendar(): Calendar? {
    return if (this == null || this.isBlank() || this == "0000-00-00") {
        null

    } else {
        try {

            Calendar.getInstance().apply {
                timeInMillis = SimpleDateFormat(
                    "yyyy-MM-dd",
                    Locale.ENGLISH
                ).parse(this@convertApiDateToCalendar).time
            }
        } catch (e: Exception) {
            null
        }
    }
}


fun String?.convertDisplayDateToApiDate(): String {
    return if (this == null || this.isBlank() || this == "0000-00-00") {
        ""

    } else {
        try {

            SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(
                SimpleDateFormat("d-MM-yyyy", Locale.ENGLISH).parse(
                    this
                )
            )
        } catch (e: Exception) {
            ""
        }
    }
}


fun Calendar?.convertCaledarToApiDate(): String {
    return if (this == null || this.timeInMillis < 1000) {
        ""

    } else {
        try {
            SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(this.time)
        } catch (e: Exception) {
            ""
        }
    }
}

fun TextInputLayout.markRequired() {
    hint = "$hint *"
}


fun EditText?.onDone(
    kFunctionOnSearch: KFunction0<Unit>,
    reset: View? = null,
    kFunctionReset: KFunction0<Unit>? = null
) {
    this?.setOnFocusChangeListener { v, hasFocus ->
        if (hasFocus)
            reset?.visibility = View.VISIBLE
        else
            reset?.visibility = View.GONE

    }

    this?.setOnEditorActionListener { v, actionId, event ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH
            || actionId == EditorInfo.IME_ACTION_DONE ||
            actionId == EditorInfo.IME_ACTION_NEXT
            || event != null && event.getAction() == KeyEvent.ACTION_DOWN
            && event.getKeyCode() == KeyEvent.KEYCODE_ENTER
        ) {
            /*   this.hideKeyboardView()*/
            kFunctionOnSearch()
            //  this@onDone.clearFocus()

            return@setOnEditorActionListener true
        }
        return@setOnEditorActionListener false
    }

    reset?.setOnClickListener {
        if (kFunctionReset != null) {
            kFunctionReset()
        }
    }
}


public interface OnDone {
    public fun onDone()
}


fun EditText?.onDone(l: (View) -> Unit) {
    this?.setOnEditorActionListener { v, actionId, event ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH
            || actionId == EditorInfo.IME_ACTION_DONE ||
            actionId == EditorInfo.IME_ACTION_NEXT
            || event != null && event.getAction() == KeyEvent.ACTION_DOWN
            && event.getKeyCode() == KeyEvent.KEYCODE_ENTER
        ) {
            /*   this.hideKeyboardView()*/
            l.invoke(this)
            //  this@onDone.clearFocus()

            return@setOnEditorActionListener true
        }
        return@setOnEditorActionListener false
    }

}

fun AutoCompleteTextView?.hideErrorOnSelection() {
    this?.addTextChangedListener {
        if (text.isNotBlank())
            if (error != null)
                setError(null)
    }

}

fun String?.floatStringIn2DecimalIfAny(): String {
    if (this?.isNullOrBlank() == true) return ""
    else if (!contains(".")) return this else if (toFloatOrNull() != null) return String.format(
        Locale.ENGLISH,
        "%.2f",
        toFloat()
    ) else return this
}


fun View?.hideKeyboardView() {
    if (this == null || this.context == null) return
    val inputMethodManager =
        this.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

    if (inputMethodManager != null && inputMethodManager.isActive) {
        //inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        //InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
    }

}

fun Activity?.hideKeyboardView() {
    if (this != null && this.window != null && this.window.decorView != null) {
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.window.decorView.windowToken, 0)
    }
}


fun Fragment?.hideKeyboardView() {
    this?.activity?.window?.decorView?.let {
        val imm =
            this.requireActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.requireActivity().window.decorView.windowToken, 0)
    }


}


fun Fragment?.showKeyboard(editText: EditText) {
    this?.activity?.window?.decorView?.let {
        val imm =
            this.requireActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, 0)
    }


}


fun EditText.bold() {

    this.setText(SpannableString(this.text.toString()).apply {
        setSpan(StyleSpan(Typeface.BOLD), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    })


}


fun Context?.getDeviceID(): String? {
    return Settings.Secure.getString(this?.contentResolver, Settings.Secure.ANDROID_ID)
}


fun Fragment?.getDeviceID(): String? {
    return Settings.Secure.getString(this?.activity?.contentResolver, Settings.Secure.ANDROID_ID)
}

val Calendar.isLessThanOrEqual48HoursFromNow: Boolean
    get() {
        return timeInMillis - Calendar.getInstance().timeInMillis <= TimeUnit.HOURS.toMillis(48)
    }


val Calendar.isLessThanOrEqual24HoursFromNow: Boolean
    get() {
        return timeInMillis - Calendar.getInstance().timeInMillis <= TimeUnit.HOURS.toMillis(24)
    }


/*val JsonObject.toMultiPart: HashMap<String, RequestBody>
    get() {
        val partRequest = HashMap<String, RequestBody>()
        keySet().forEach {
            partRequest[it] = Utils.toRequestBody(this.get(it).asString)
        }
        return partRequest
    }*/


public fun Intent.putExtra(s: String, textView: TextView) {
    putExtra(s, textView.text.toString())
}


public fun Bundle.putString(s: String, textView: TextView) {
    putString(s, textView.text.toString())
}


@Throws(Exception::class)
fun JsonObject?.isStatusSuccess(): Boolean {
    return this?.get("status")?.asString == "200"
}


val RTL_EMBED = "\u202B"
val LTR_EMBED = "\u202A"

public fun String.ltrEmbed(): String {
    return LTR_EMBED + this + LTR_EMBED
}

/*
public fun String?.withCurrency(
        context: Context? = AppApplication.instance,
        symbol_left: String? = null,
        symbol_right: String? = null,
       *//* scale: Int? = null,*//*


        ): String {
    try {
        if (this.isNullOrBlank()) return ""
        Log.d("embedPrice", "embedPrice: $this")
        val country = SharedPreferanceUtils.getCountry(context)
        val bd = this?.toBigDecimal()
                ?.setScale(
                      *//*  if (scale != null) scale else*//*
                            if (country?.iso_code?.toLowerCase() == "kw") 3
                            else 2, BigDecimal.ROUND_HALF_UP)


        val symbolRight = symbol_right ?: country?.symbol_right ?: "".trim()
        val symbolLeft = symbol_left ?: country?.symbol_left ?: "".trim()
        Log.d("withCurrency", "withCurrency: $bd")
        val s = LTR_EMBED + (symbolLeft + " " + bd.toString().replaceAR() + " " + symbolRight).trim() + LTR_EMBED
        Log.d("withCurrency", "withCurrency: $bd $s")
        return s
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return ""
}*/

val ars = arrayOf(
    "١",
    "٢",
    "٣",
    "٤",
    "٥",
    "٦",
    "7",
    "٨",
    "٩",
    "٠",
    "۱",
    "۲",
    "۳",
    "۴",
    "۵",
    "۶",
    "۷",
    "۸",
    "۹",
    "۰"
)


fun String?.replaceAR(): String {
    return this ?: ""
        .replace("١", "1")
        .replace("٢", "2")
        .replace("٣", "3")
        .replace("٤", "4")
        .replace("٥", "5")
        .replace("٦", "6")
        .replace("٧", "7")
        .replace("٨", "8")
        .replace("٩", "9")
        .replace("٠", "0")
        .replace("۱", "1")
        .replace("۲", "2")
        .replace("۳", "3")
        .replace("۴", "4")
        .replace("۵", "5")
        .replace("۶", "6")
        .replace("۷", "7")
        .replace("۸", "8")
        .replace("۹", "9")
        .replace("۰", "0")
}


public fun Activity?.isTrue(string: String): Boolean {
    return this?.intent?.getBooleanExtra(string, false) == true
}


public fun Intent?.isTrue(string: String): Boolean {
    return this?.getBooleanExtra(string, false) == true
}


public fun Bundle?.isTrue(string: String): Boolean {
    return this?.getBoolean(string, false) == true
}


public fun Fragment?.isTrue(string: String): Boolean {
    return this?.arguments?.getBoolean(string, false) == true
}


fun Context.getResearchStorageDir(): File {
    return File(filesDir, "docs").apply {
        if (!exists())
            mkdir()
        else if (!isDirectory) {
            delete()
            mkdir()
        }


    }


}


fun String?.toDisplayDate(): String {
    if (this.isNullOrBlank()) return ""
    return try {
        val parsed = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(this)
        SimpleDateFormat("d-M-yyyy", Locale.ENGLISH).format(parsed.time)
    } catch (e: Exception) {
        this
    }
}

fun String?.toDDMMYYYY(): String {
    if (this.isNullOrBlank()) return ""
    return try {
        val parsed = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(this)
        SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(parsed.time)
    } catch (e: Exception) {
        this
    }
}


//fun JsonObject?.getAPIResponseMessage(): String {
//    var message: String? = null
//    if (this != null) {
//        try {
//            if (Pref.isArabic) {
//                if (this.has("message_arabic"))
//                    message = this.get("message_arabic").asString
//                else if (this.has("message_ar"))
//                    message = this.get("message_ar").asString
//                else if (this.has("message"))
//                    message = this.get("message").asString
//
//            } else {
//                if (this.has("message"))
//                    message = this.get("message").asString
//                else if (this.has("message_ar"))
//                    message = this.get("message_ar").asString
//                else if (this.has("message_arabic"))
//                    message = this.get("message_arabic").asString
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//    }
//    return if (message == null) "" else message
//}


fun View.setOnClickListenerDummy(listener: (View) -> Unit) {
    listener(this)
}


fun TextView.bold() {
    this.text = this.text.toString().bold()
}

/*
fun TextView.calligraphyFont(@StringRes strFontId: Int) {
    setText(SpannableString(text.toString()).apply {
        setSpan(CalligraphyTypefaceSpan(TypefaceUtils.load(context?.assets, context.getString(strFontId))), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    })
}
*/


fun TextView.boldClear() {
    this.text = this.text.toString().boldClear()
}

fun String?.bold(): SpannableString {
    return SpannableString(this).apply {
        setSpan(StyleSpan(Typeface.BOLD), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}

fun String?.boldClear(): SpannableString {
    return SpannableString(this).apply {
        setSpan(StyleSpan(Typeface.NORMAL), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}


/*fun logEventsToFireBase(key: String, vale: String) {
    Log.d(key, vale)
    try {
        FirebaseAnalytics.getInstance(AppApplication.instance).logEvent("LOGS", Bundle().apply {
            putString(key, vale)

        })
    } catch (e: Exception) {
    }
}*/

/*fun Throwable?.printAndReportException() {
    if (this != null) {
        printStackTrace()
        FirebaseCrashlytics.getInstance().recordException(this)
    } else {
        Log.d("logExceptionAndReport", "Exception null")
    }
}*/


fun Activity?.getColorFromResource(@ColorRes colorResource: Int): Int {
    if (this != null) {
        return ContextCompat.getColor(this, colorResource)
    } else {
        return Color.TRANSPARENT
    }
}


fun Fragment?.getColorFromResource(@ColorRes colorResource: Int): Int {
    return if (this?.activity != null) {
        ContextCompat.getColor(this.requireActivity(), colorResource)
    } else {
        Color.TRANSPARENT
    }
}

fun View?.getColorFromResource(@ColorRes colorResource: Int): Int {
    if (this?.context != null) {
        return ContextCompat.getColor(this?.context!!, colorResource)
    } else {
        return Color.TRANSPARENT
    }
}


fun pxFromDp(context: Context, dp: Float): Float {
    return dp * context.resources.displayMetrics.density
}


fun dpFromPx(context: Context, px: Float): Float {
    return px / context.resources.displayMetrics.density
}


fun CharSequence?.trimTrailingWhitespace(): CharSequence? {
    if (this == null) return ""
    var i = this.length

    // loop back to the first non-whitespace character
    while (--i >= 0 && Character.isWhitespace(this[i])) {
    }
    return this.subSequence(0, i + 1)
}


/*fun getShortDayNames(int: Int): String {
    if (AppApplication.isArabic) {
        return dayNamesAr[calDays.indexOf(int)]
    } else {
        return dayNamesEnShort[calDays.indexOf(int)]
    }
}*/

/*fun getFullDayNames(int: Int): String {
    if (AppApplication.isArabic) {
        return dayNamesAr[calDays.indexOf(int)]
    } else {
        return dayNamesEnFull[calDays.indexOf(int)]
    }
}*/


val calDays = arrayOf(
    Calendar.SUNDAY,
    Calendar.MONDAY,
    Calendar.TUESDAY,
    Calendar.WEDNESDAY,
    Calendar.THURSDAY,
    Calendar.FRIDAY,
    Calendar.SATURDAY
)
val dayNamesEnFull = arrayOf(
    "Sunday",
    "Monday",
    "Tuesday",
    "Wednesday",
    "Thursday",
    "Friday",
    "Saturday"
)


val dayNamesEnShort = arrayOf(
    "Sun",
    "Mon",
    "Tue",
    "Wed",
    "Thu",
    "Fri",
    "Sat"
)


val dayNamesAr = arrayOf(
    "الأحد",
    "الإثنين",
    "الثلاثاء",
    "الأربعاء",
    "الخميس",
    "الجمعة",
    "السبت"
)


fun ImageView.fetch(
    url: String?,
    @DrawableRes error: Int = R.drawable.hijab_ico,
    @DrawableRes placeholder: Int = R.drawable.hijab_ico
) {
    Glide
        .with(context)
        .load(url)
        .centerCrop()
        .placeholder(placeholder)
        .error(error) // todo remove
        .into(this)
}

fun NestedScrollView.reachedBottom(l: (View) -> Unit) {
    this.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
        if (scrollY == v?.getChildAt(0)!!.measuredHeight - v.measuredHeight) {
            l.invoke(this@reachedBottom)
        }
    })
}

fun NestedScrollView.paginate(l: (View) -> Unit) {
    this.viewTreeObserver.addOnScrollChangedListener {
        val view: View = this@paginate.getChildAt(this@paginate.childCount - 1)
        val diff = (view.bottom - (this@paginate.height + this@paginate.scrollY))

        if (diff == 0) {
            l.invoke(this)
        }

    }
}

fun RecyclerView.runWhenReady(action: () -> Unit) {
    val globalLayoutListener = object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            action()
            viewTreeObserver.removeOnGlobalLayoutListener(this)
        }
    }
    viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)
}

fun TextView.leftDrawable(@DrawableRes id: Int = 0) {
    this.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
}

/*fun ImageView?.picasoLoadImageSp1(image: String?, placeHolder: String = AppConfig.place_holder_no_image) {
    Log.d("PICASO", "picasoLoadImageSp1: " + AppConfig.BASE_URL_IMAGE + image)
    this?.let { imageView ->
        Utils.log("LLLJJJJ")
        val tag = System.currentTimeMillis()
        imageView.setTag(tag)
        try {
            Utils.log("LLLJJJJ")
            if (image?.trim().isNullOrBlank() || placeHolder == AppConfig.BASE_URL_IMAGE + image) {
                imageView?.picasoLoadImageSp1LoadPlaceHolder(image, placeHolder, tag)
                Utils.log("LLLJJJJ")
            } else {
                Utils.log("LLLJJJJ")
                Picasso.get().load(AppConfig.BASE_URL_IMAGE + image).into(imageView, object : Callback {
                    override fun onSuccess() {
                        Utils.log("LLLJJJJ")
                    }

                    override fun onError(e: java.lang.Exception?) {
                        Utils.log("LLLJJJJ")
                        if (tag == imageView.tag) {
                            Utils.log("LLLJJJJ")
                            imageView?.picasoLoadImageSp1LoadPlaceHolder(image, placeHolder, tag)
                        }
                    }

                })
            }
        } catch (e: Exception) {
            Utils.log("LLLJJJJ")
            e.printStackTrace()
        }
    }

}*/


/*private fun ImageView?.picasoLoadImageSp1LoadPlaceHolder(image: String?, placeHolder: String, tag: Long) {
    this?.let { imageView ->
        try {
            Utils.log("LLLJJJJ")
            if (tag == imageView.tag) {
                Utils.log("LLLJJJJ")
                Picasso.get().load(placeHolder).into(imageView, object : Callback {
                    override fun onSuccess() {
                        Utils.log("LLLJJJJ")
                        GlobalScope.launch {
                            Utils.log("LLLJJJJ")
                            val clp = pref.getLong("cl_$placeHolder", 0L)
                            var cl = AppApplication.rxRestService.loadUrlSuspended(placeHolder).contentLength()
                            Log.d("hghghghggh", "$tag $clp $cl")
                            if (cl > 0 && clp != cl) {
                                Utils.log("LLLJJJJ")
                                editPref.putLong("cl_$placeHolder", cl).commit()
                                Picasso.get().invalidate(placeHolder)
                                picasoLoadImageSp1LoadPlaceHolder(image, placeHolder, tag)
                            }

                        }

                    }

                    override fun onError(e: java.lang.Exception?) {
                        Utils.log("LLLJJJJ")
                    }

                })
            }
        } catch (e: Exception) {
            Utils.log("LLLJJJJ")
            e.printStackTrace()
        }

    }

}*/


/*fun ImageView?.picasoRetail(image: String?, placeHolder: Int) {
    if (image.isNullOrBlank()){
       this?. setImageResource(placeHolder)
    }else
    Picasso.get().load(AppConfig.BASE_URL_IMAGE + image)
            .error(placeHolder)
            .into(this, object : Callback {
        override fun onSuccess() {

        }

        override fun onError(e: java.lang.Exception?) {


        }

    })
}*/







/**
 * Used to scroll to the given view.
 *
 * @param scrollViewParent Parent ScrollView
 * @param view View to which we need to scroll.
 */
fun scrollToView(scrollViewParent: ScrollView, view: View) {
    // Get deepChild Offset
    val childOffset = Point()
    getDeepChildOffset(scrollViewParent, view.parent, view, childOffset)
    // Scroll to child.
    scrollViewParent.smoothScrollTo(0, childOffset.y)
}


fun scrollToViewH(scrollViewParent: HorizontalScrollView, view: View) {
    // Get deepChild Offset
    val childOffset = Point()
    getDeepChildOffset(scrollViewParent, view.parent, view, childOffset)
    // Scroll to child.
    scrollViewParent.smoothScrollTo(0, childOffset.y)
}

fun NestedScrollView.scrollToBottom() {
    this.post {
        fullScroll(ScrollView.FOCUS_DOWN)
    }
}


/**
 * Used to get deep child offset.
 *
 *
 * 1. We need to scroll to child in scrollview, but the child may not the direct child to scrollview.
 * 2. So to get correct child position to scroll, we need to iterate through all of its parent views till the main parent.
 *
 * @param mainParent        Main Top parent.
 * @param parent            Parent.
 * @param child             Child.
 * @param accumulatedOffset Accumulated Offset.
 */
private fun getDeepChildOffset(
    mainParent: ViewGroup,
    parent: ViewParent,
    child: View,
    accumulatedOffset: Point
) {
    val parentGroup = parent as ViewGroup
    accumulatedOffset.x += child.left
    accumulatedOffset.y += child.top
    if (parentGroup == mainParent) {
        return
    }
    getDeepChildOffset(mainParent, parentGroup.parent, parentGroup, accumulatedOffset)
}


fun RecyclerView?.addItemDecoration(
    @DrawableRes drawableRes: Int,
    orientation: Int = RecyclerView.VERTICAL
) {
    this?.addItemDecoration(DividerItemDecoration(context, orientation).apply {
        ContextCompat.getDrawable(context, drawableRes)?.let { setDrawable(it) }
    })
}

fun TextView.showStrikeThrough(show: Boolean) {
    paintFlags =
        if (show) paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        else paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
}

fun TextView?.wordCapitalize() {
    this?.isAllCaps = false
    this?.setText(text.toString().wordCapitalize())
}

fun String?.wordCapitalize(): String {
    if (this.isNullOrBlank()) {
        return ""
    }
    var s = ""
    toLowerCase().split(" ").forEach {
        if (it.isNotBlank()) {
            s = s + " " + it.replaceFirst(it[0], it[0].toUpperCase())
        }

    }
    return s
}


fun EditText.addSuffix(suffix: String) {
    val editText = this
    val formattedSuffix = " $suffix"
    var text = ""
    var isSuffixModified = false

    val setCursorPosition: () -> Unit =
        { Selection.setSelection(editableText, editableText.length - formattedSuffix.length) }

    val setEditText: () -> Unit = {
        editText.setText(text)
        setCursorPosition()
    }

    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            val newText = editable.toString()

            if (isSuffixModified) {
                // user tried to modify suffix
                isSuffixModified = false
                setEditText()
            } else if (text.isNotEmpty() && newText.length < text.length && !newText.contains(formattedSuffix)) {
                // user tried to delete suffix
                setEditText()
            } else if (!newText.contains(formattedSuffix)) {
                // new input, add suffix
                text = "$newText$formattedSuffix"
                setEditText()
            } else {
                text = newText
            }
        }

        override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
            charSequence?.let {
                val textLengthWithoutSuffix = it.length - formattedSuffix.length
                if (it.isNotEmpty() && start > textLengthWithoutSuffix) {
                    isSuffixModified = true
                }
            }
        }

        override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {

        }
    })
}

fun toRequestBody(value: String?): RequestBody {
    return RequestBody.create("text/plain".toMediaTypeOrNull(), value!!)
}

fun toRequestBody(value: List<String>?): RequestBody {
    return RequestBody.create("text/plain".toMediaTypeOrNull(), value.toString())
}


fun HashMap<String, RequestBody>.addImage(
    file: File?,
    key: String = "profile_image",
    mime: String = "image/jpeg"
): HashMap<String, RequestBody> {
    if (file != null) {
        val requestFile = RequestBody.create(mime.toMediaTypeOrNull(), file)
        this["$key\"; filename=\"" + file.name + "\""] = requestFile
    }
    return this
}


//fun Context.openImageFullScreen(imgUrl: String?){
//    val dialog = Dialog(this)
//    if (dialog.isShowing) dialog.dismiss()
//    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//    dialog.setContentView(R.layout.dialog_image_full_screen_layout)
//    dialog.setCanceledOnTouchOutside(true)
//    dialog.setCancelable(true)
//    dialog.show()
//    dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
//
//    val image = dialog.findViewById<ZoomImageview>(R.id.fullViewImage)
//    val close = dialog.findViewById<ImageView>(R.id.fullViewImageClose)
//
//    Glide.with(this)
//        .asBitmap()
//        .placeholder(R.drawable.group_117)
//        .load(BuildConfig.BASE_IMG_URL+imgUrl)
//        .into(image)
//
//    close.setOnClickListener { dialog.dismiss() }
//}

fun ScrollView.moveToTop() {
    this.isFocusableInTouchMode = true
    this.fullScroll(View.FOCUS_UP)
    this.smoothScrollTo(0, 0)
}


//fun Fragment.getDateFromDatePicker(showFuture: Boolean = false,date: (String,String) -> Unit) {
//    var finalDate = ""
//    var toApiDate = ""
//    val dobCal = Calendar.getInstance()
//    val datePicker = DatePickerDialog(
//        requireContext(), R.style.my_dialog_theme,
//        { view, year, month, dayOfMonth ->
//            dobCal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
//            dobCal.set(Calendar.MONTH, month)
//            dobCal.set(Calendar.YEAR, year)
//            finalDate = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(dobCal.time)
//            toApiDate = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(dobCal.time)
//            print("DATA: $finalDate $toApiDate")
//            date.invoke(finalDate,toApiDate)
//        }, dobCal.get(Calendar.YEAR), dobCal.get(Calendar.MONTH), dobCal.get(Calendar.DATE)
//    )
//    if (!showFuture)datePicker.datePicker.maxDate = System.currentTimeMillis()
//    datePicker.create()
//    if (!datePicker.isShowing)datePicker.show()
//}
//
//fun Fragment.getTimeFromTimePicker(sate: Int,click:(String) -> Unit){
//    val mcurrentTime = Calendar.getInstance()
//    val hour = mcurrentTime[Calendar.HOUR_OF_DAY]
//    val minute = mcurrentTime[Calendar.MINUTE]
//    val mTimePicker = TimePickerDialog(
//        requireContext(), { timePicker, selectedHour, selectedMinute ->
//
////            val time = Time(hour, minute, 0)
////            val simpleDateFormat = SimpleDateFormat("h:mma")
////            val s = simpleDateFormat.format(time)
//
//            val AM_PM: String = if (selectedHour in 0..11) {
//                "AM"
//            } else {
//                "PM"
//            }
//
//            click.invoke("$selectedHour:$selectedMinute $AM_PM")
////            click.invoke("$selectedHour:$selectedMinute $AM_PM")
////            eReminderTime.setText("$selectedHour:$selectedMinute")
//        },
//        hour,
//        minute,
//        true
//    ) //Yes 24 hour time
//
//    mTimePicker.setTitle(when(sate){
//        1 -> "Time From"
//        else -> "Time To"
//    })
//    mTimePicker.show()
//}

//fun Fragment.havePermission(): Boolean {
//    var permisssn = false
//
//    val permissions = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION)
//    val rationale = "Please provide location permission so that you can ..."
//    val options = Permissions.Options()
//        .setRationaleDialogTitle("Info")
//        .setSettingsDialogTitle("Warning")
//
//    Permissions.check(
//        requireContext() /*context*/,
//        permissions,
//        rationale,
//        options,
//        object : PermissionHandler() {
//            override fun onGranted() {
//                permisssn = true
//            }
//
//            override fun onDenied(context: Context?, deniedPermissions: ArrayList<String?>?) {
//                permisssn = false
//            }
//        })
//    return permisssn
//}

//fun AppCompatActivity.havePermission(): Boolean {
//    var permisssn = false
//
//    val permissions = arrayOf(
//        Manifest.permission.ACCESS_COARSE_LOCATION,
//        Manifest.permission.ACCESS_FINE_LOCATION,
//        Manifest.permission.READ_EXTERNAL_STORAGE,
//        Manifest.permission.CAMERA
//    )
//    val rationale = "Please provide location permission so that you can ..."
//    val options = Permissions.Options()
//        .setRationaleDialogTitle("Info")
//        .setSettingsDialogTitle("Warning")
//
//    Permissions.check(
//        this /*context*/,
//        permissions,
//        rationale,
//        options,
//        object : PermissionHandler() {
//            override fun onGranted() {
//                permisssn = true
//            }
//
//            override fun onDenied(context: Context?, deniedPermissions: ArrayList<String?>?) {
//                permisssn = false
//            }
//        })
//
//    return permisssn
//}


fun InputStream.toFile(path: String) {
    File(path).outputStream().use { this.copyTo(it) }
}

fun setLongTxtData(string: String): String {
    var findString = ""
    findString = if (string.length > 150) {
        Html.fromHtml(string.substring(0, 150) + "..." + "<font color='#CDC4EB'> <u>Continue reading</u></font>").toString()
    } else {
        string
    }

    return findString
}

fun Uri.getFileExtension(context: Context): String? {
    return MimeTypeMap.getSingleton()
        .getExtensionFromMimeType(context.contentResolver.getType(this))
}

@SuppressLint("MissingPermission")
fun Fragment.getLastKnownLocation() {
    val locationManager: LocationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val providers: List<String> = locationManager.getProviders(true)
    var location: Location? = null
    for (i in providers.size - 1 downTo 0) {
        location = locationManager.getLastKnownLocation(providers[i])
        if (location != null)
            break
    }
    val gps = DoubleArray(2)
    if (location != null) {
        gps[0] = location.latitude
        gps[1] = location.longitude
        Pref.latitude = gps[0].toString()
        Pref.longitude = gps[1].toString()
        Log.e("gpsLat", gps[0].toString())
        Log.e("gpsLong", gps[1].toString())

    }

}

fun View?.showSnack(msg: String) {
    Snackbar.make(this!!, msg, Snackbar.LENGTH_LONG).apply {
        gravityTop()
        view.background = ContextCompat.getDrawable(context, R.drawable.snack_container)
        setTextColor(ContextCompat.getColor(context, R.color.white))
    }.show()
}

fun View?.showBlackSnack(msg: String) {
    Snackbar.make(this!!, msg, Snackbar.LENGTH_LONG).apply {
        gravityTop()
        view.background = ContextCompat.getDrawable(context, R.drawable.snack_black_container)
        setTextColor(ContextCompat.getColor(context, R.color.white))
    }.show()
}

fun AutoCompleteTextView.searchQueryTyped(report: (Boolean, String) -> Unit) {

    var runnable: Runnable? = null
    var handler: Handler? = null
    val timeToWait = 700L //change this one for delay (time in milli)

    handler = Handler(Looper.myLooper()!!)

    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (this@searchQueryTyped.text.toString() != "") {
                runnable = Runnable {
                    report.invoke(false, this@searchQueryTyped.text.toString())
                }
                handler.postDelayed(runnable!!, timeToWait)
            } else {
                report.invoke(true, "")
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (runnable != null) handler.removeCallbacks(runnable!!)
        }
    })
}

fun EditText.searchQueryTyped(report: (Boolean, String) -> Unit) {

    var runnable: Runnable? = null
    var handler: Handler? = null
    val timeToWait = 700L //change this one for delay (time in milli)

    handler = Handler(Looper.myLooper()!!)

    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (this@searchQueryTyped.text.toString() != "") {
                runnable = Runnable {
                    report.invoke(false, this@searchQueryTyped.text.toString())
                }
                handler.postDelayed(runnable!!, timeToWait)
            } else {
                report.invoke(true, "")
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (runnable != null) handler.removeCallbacks(runnable!!)
        }
    })
}

fun EditText.hashTagEditText(report: (Boolean, String) -> Unit) {

    val mSpannable = text
    var setTextLastIndex = 0

    text.trim().split(Regex("\\s+")).forEach {
        if (it.isNotEmpty() && it[0] == '#') {
            val boldSpan = StyleSpan(Typeface.BOLD)
            val foregroundSpan = ForegroundColorSpan(ContextCompat.getColor(context, R.color.hashTagColor))
            mSpannable.apply {
                setSpan(
                    foregroundSpan,
                    text.indexOf(it, startIndex = setTextLastIndex),
                    text.indexOf(it, startIndex = setTextLastIndex) + it.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                setSpan(
                    boldSpan,
                    text.indexOf(it, startIndex = setTextLastIndex),
                    text.indexOf(it, startIndex = setTextLastIndex) + it.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

        }
        setTextLastIndex += it.length - 1
    }
    report.invoke(text!!.trim().length >= 10, text.toString())


    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            var lastIndex = 0

            text.trim().split(Regex("\\s+")).forEach {
                if (it.isNotEmpty() && it[0] == '#') {
                    val boldSpan = StyleSpan(Typeface.BOLD)
                    val foregroundSpan = ForegroundColorSpan(ContextCompat.getColor(context, R.color.hashTagColor))
                    mSpannable.apply {
                        setSpan(
                            foregroundSpan,
                            text.indexOf(it, startIndex = lastIndex),
                            text.indexOf(it, startIndex = lastIndex) + it.length,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        setSpan(
                            boldSpan,
                            text.indexOf(it, startIndex = lastIndex),
                            text.indexOf(it, startIndex = lastIndex) + it.length,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }

                }
                lastIndex += it.length - 1
            }

            report.invoke(s!!.trim().length >= 10, s.toString())
        }
    })
}

fun TextView.textToHashtag() {
    val mSpannable = this.text.toSpannable()
    var lastIndex = 0

    text.trim().split(Regex("\\s+")).forEach {
        if (it.isNotEmpty() && it[0] == '#') {
            val boldSpan = StyleSpan(Typeface.BOLD)
            val foregroundSpan = ForegroundColorSpan(ContextCompat.getColor(context, R.color.hashTagColor))
            text = mSpannable.apply {
                setSpan(
                    foregroundSpan,
                    text.indexOf(it, startIndex = lastIndex),
                    text.indexOf(it, startIndex = lastIndex) + it.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                setSpan(
                    boldSpan,
                    text.indexOf(it, startIndex = lastIndex),
                    text.indexOf(it, startIndex = lastIndex) + it.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
        lastIndex += it.length - 1
    }

}

@BindingAdapter("paymentType")
fun ImageView.paymentType(input: Int) {
    when(input) {
        3 -> setImageResource(R.drawable.cashpay_ico)
        1 -> setImageResource(R.drawable.gpay_ico)
        2 -> setImageResource(R.drawable.phonepe_ico)
    }
}

@BindingAdapter("setPrice")
fun TextView.setPrice(input: String) {
    text = "₹$input"
}

@BindingAdapter("toHashTags")
fun TextView.toHashtag(input: String) {
    val mSpannable = this.text.toSpannable()
    var lastIndex = 0

    input.trim().split(Regex("\\s+")).forEach {
        if (it.isNotEmpty() && it[0] == '#') {
            val boldSpan = StyleSpan(Typeface.BOLD)
            val foregroundSpan = ForegroundColorSpan(ContextCompat.getColor(context, R.color.hashTagColor))
            this.text = mSpannable.apply {
                setSpan(
                    foregroundSpan,
                    input.indexOf(it, startIndex = lastIndex),
                    input.indexOf(it, startIndex = lastIndex) + it.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                setSpan(
                    boldSpan,
                    input.indexOf(it, startIndex = lastIndex),
                    input.indexOf(it, startIndex = lastIndex) + it.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
        lastIndex += it.length - 1
    }

}

fun ImageView.breathAnim() {
    startAnimation(AnimationUtils.loadAnimation(context, R.anim.pulse))
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun Fragment.showNotificationPermission(granted: () -> Unit) {

    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission is granted. Continue the action or workflow in your
            // app.
            granted.invoke()
            println("PERMISSION GRANTED")
        } else {
            // Explain to the user that the feature is unavailable because the
            // features requires a permission that the user has denied. At the
            // same time, respect the user's decision. Don't link to system
            // settings in an effort to convince the user to change their
            // decision.
        }
    }

    when {
        ContextCompat.checkSelfPermission(
            requireActivity(), Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED -> {
            // You can use the API that requires the permission.
            Log.e("PERMISSION", "onCreate: PERMISSION GRANTED")
        }
        shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {

        }
        else -> {
            // The registered ActivityResultCallback gets the result of this request
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }


}

fun CharSequence.makeUnderlineClickable(listener: (index: Int) -> Unit): SpannableString {
    val spannedString = SpannableString(this)
    spannedString.getSpans(0, length, UnderlineSpan::class.java)?.forEachIndexed { index, underlineSpan ->
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                listener.invoke(index)
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = true
            }
        }
        spannedString.setSpan(
            clickableSpan,
            spannedString.getSpanStart(underlineSpan),
            spannedString.getSpanEnd(underlineSpan),
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    return spannedString
}

fun TextView.makeLinks(vararg links: Pair<String, View.OnClickListener>) {
    val spannableString = SpannableString(this.text)
    var startIndexOfLink = -1
    for (link in links) {
        val clickableSpan = object : ClickableSpan() {
            override fun updateDrawState(textPaint: TextPaint) {
                textPaint.color = ContextCompat.getColor(context, R.color.dot_selected)
            }

            override fun onClick(view: View) {
                Selection.setSelection((view as TextView).text as Spannable, 0)
                view.invalidate()
                link.second.onClick(view)
            }
        }
        startIndexOfLink = this.text.toString().indexOf(link.first, startIndexOfLink + 1)
        spannableString.setSpan(
            clickableSpan, startIndexOfLink, startIndexOfLink + link.first.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    this.movementMethod = LinkMovementMethod.getInstance()
    this.highlightColor = Color.TRANSPARENT
    this.setText(spannableString, TextView.BufferType.SPANNABLE)
}

fun TextView.makeBlueLinks(vararg links: Pair<String, View.OnClickListener>) {
    val spannableString = SpannableString(this.text)
    var startIndexOfLink = -1
    for (link in links) {
        val clickableSpan = object : ClickableSpan() {
            override fun updateDrawState(textPaint: TextPaint) {
                textPaint.color = ContextCompat.getColor(context, R.color.upload_media_color)
            }

            override fun onClick(view: View) {
                Selection.setSelection((view as TextView).text as Spannable, 0)
                view.invalidate()
                link.second.onClick(view)
            }
        }
        startIndexOfLink = this.text.toString().indexOf(link.first, startIndexOfLink + 1)
        spannableString.setSpan(
            clickableSpan, startIndexOfLink, startIndexOfLink + link.first.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    this.movementMethod = LinkMovementMethod.getInstance()
    this.highlightColor = Color.TRANSPARENT
    this.setText(spannableString, TextView.BufferType.SPANNABLE)
}

fun printHashKey(pContext: Context) {
    try {
        val info: PackageInfo = pContext.packageManager
            .getPackageInfo(pContext.packageName, PackageManager.GET_SIGNATURES)
        for (signature in info.signatures) {
            val md: MessageDigest = MessageDigest.getInstance("SHA")
            md.update(signature.toByteArray())
            val hashKey: String = String(Base64.encode(md.digest(), 0))
            Log.i("ARS -> ", "printHashKey() Hash Key: $hashKey")
        }
    } catch (e: NoSuchAlgorithmException) {
        Log.e("ARS -> ", "printHashKey()", e)
    } catch (e: Exception) {
        Log.e("ARS -> ", "printHashKey()", e)
    }
}

fun Snackbar.gravityTop() {
    this.view.layoutParams = (this.view.layoutParams as FrameLayout.LayoutParams).apply {
        gravity = Gravity.TOP
    }
}

fun Button.formVerified(enabled: Boolean) {
    isEnabled = enabled
    animate().alpha(if (enabled) 1f else 0.5f).duration = 100
}

fun ImageView.chatVerified(enabled: Boolean) {
    isEnabled = enabled
    animate().alpha(if (enabled) 1f else 0.5f)
}

fun EditText.validateQueryTyped(type: Int, report: (Boolean) -> Unit) {

    /**
     * @param type
     *  1 -> name
     *  2 -> email
     *  3 -> password
     *  4 -> empty check
     */

    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

            when (type) {
                1 -> if (s?.length!! >= 3) report.invoke(true) else report.invoke(false)
                2 -> if (this@validateQueryTyped.text.isValidEmail()) report.invoke(true) else report.invoke(false)
                3 -> if (s?.length!! >= 6) report.invoke(true) else report.invoke(false)
                4 -> if (this@validateQueryTyped.text.toString().isEmpty().not()) report.invoke(true) else report.invoke(false)
            }

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
    })
}

fun ArrayList<Pair<TextInputEditText, Int>>.validateQueryTyped(report: (ArrayList<Int>) -> Unit) {

    /**
     * @param type
     *  1 -> name
     *  2 -> email
     *  3 -> password
     *  4 -> empty check
     *  5 -> lastName
     */

    val returnArray = arrayListOf<Int>()

    this.forEach {
        it.first.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                when (it.second) {
                    1 -> if (s?.length!! >= 3) returnArray.add(1) else returnArray.removeIf { r -> r == 1 }
                    2 -> if (it.first.text.isValidEmail()) returnArray.add(2) else returnArray.removeIf { r -> r == 2 }
                    3 -> if (s?.length!! >= 6) returnArray.add(3) else returnArray.removeIf { r -> r == 3 }
                    4 -> if (it.first.text.toString().isEmpty().not()) returnArray.add(4) else returnArray.removeIf { r -> r == 4 }
                    5 -> if (s?.length!! >= 1) returnArray.add(5) else returnArray.removeIf { r -> r == 5 }
                }

                report.invoke(returnArray)

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

    }

}

fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun Fragment.openDatePicker(title: String = "Select Date", date: (String, String) -> Unit) {

    val picker = MaterialDatePicker.Builder.datePicker()
        .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
        .setTitleText(title)
        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
        .setCalendarConstraints(CalendarConstraints.Builder().setValidator(DateValidatorPointForward.now()).build())
        .build()


    picker.addOnPositiveButtonClickListener {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.timeInMillis = it
        val format = SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)
        val backend = SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH)
        date.invoke(format.format(calendar.time), backend.format(calendar.time))
    }

    picker.show(this.childFragmentManager, picker.toString())
}

fun Fragment.openScheduleDatePicker(title: String = "Select Date", date: (String, String) -> Unit) {

    val today = MaterialDatePicker.todayInUtcMilliseconds()
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

    calendar.timeInMillis = today
    calendar[Calendar.MONTH] = +2
    val febThisYear = calendar.timeInMillis

    val consBuilder = CalendarConstraints.Builder()
        .setStart(MaterialDatePicker.todayInUtcMilliseconds())

        .setValidator(DateValidatorPointForward.now())

    val picker = MaterialDatePicker.Builder.datePicker()
        .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
        .setTitleText(title)
        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
        .setCalendarConstraints(consBuilder.build())
        .build()


    picker.addOnPositiveButtonClickListener {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.timeInMillis = it
        val format = SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)
        val backend = SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH)
        date.invoke(format.format(calendar.time), backend.format(calendar.time))
    }

    picker.show(this.childFragmentManager, picker.toString())
}

fun Fragment.openTimePicker(title: String = "Select Time", time: (String, String) -> Unit) {

    val now = Calendar.getInstance()

    val picker = MaterialTimePicker.Builder()
        .setTitleText(title)
        .setHour(now.get(Calendar.HOUR_OF_DAY))
        .setMinute(now.get(Calendar.MINUTE))
        .setTimeFormat(TimeFormat.CLOCK_12H)
        .build()

    picker.addOnPositiveButtonClickListener {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, picker.hour)
        calendar.set(Calendar.MINUTE, picker.minute)

        val displayFormat = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(calendar.time)

        val timeFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        timeFormat.timeZone = TimeZone.getTimeZone("UTC")

        val backendTime = timeFormat.format(calendar.time)

        time.invoke(displayFormat, backendTime)
    }

    picker.show(this.childFragmentManager, "RSKLSS_TIME")

}

fun Fragment.openDobDatePicker(title: String = "Select Date", date: (String, String) -> Unit) {

    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    calendar.add(Calendar.YEAR, -16)
    val endDate = calendar.timeInMillis
    val consBuilder = CalendarConstraints.Builder()
        .setEnd(endDate)
        .setValidator(DateValidatorPointBackward.before(endDate))

    val picker = MaterialDatePicker.Builder.datePicker()
        .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
        .setTitleText(title)
        .setSelection(endDate)
        .setCalendarConstraints(consBuilder.build())
        .build()

    picker.addOnPositiveButtonClickListener {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.timeInMillis = it
        val format = SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)
        val backend = SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH)
        date.invoke(format.format(calendar.time), backend.format(calendar.time))
    }

    picker.show(this.childFragmentManager, picker.toString())
}

fun Fragment.openDobDatePicker(title: String = "Select Date", isEndDateNeeded: Boolean = true, dateFormat: String, date: (String) -> Unit) {

    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    val endDate = calendar.timeInMillis
    val consBuilder = CalendarConstraints.Builder()
    if (isEndDateNeeded) {
        consBuilder.setEnd(endDate)
            .setValidator(DateValidatorPointBackward.before(endDate))
    }
    val picker1 = MaterialDatePicker.Builder.datePicker()
        .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
        .setTitleText(title)
        .setCalendarConstraints(consBuilder.build())

    if (isEndDateNeeded)
        picker1.setSelection(endDate)

    val picker = picker1.build()
    picker.addOnPositiveButtonClickListener {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.timeInMillis = it

        val backend = SimpleDateFormat(dateFormat, Locale.ENGLISH)
        date.invoke(backend.format(calendar.time))
    }

    picker.show(this.childFragmentManager, picker.toString())
}

fun EditText.googleQueryTyped(report: (String) -> Unit) {

    var runnable: Runnable? = null
    var handler: Handler? = null
    val timeToWait = 700L //change this one for delay (time in milli)

    handler = Handler()

    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (s?.length!! >= 3) {
                runnable = Runnable {
                    if (this@googleQueryTyped.text.toString().isEmpty().not()) {
                        report.invoke(this@googleQueryTyped.text.toString())
                    }
                }
                handler.postDelayed(runnable!!, timeToWait)

            } else {
                if (this@googleQueryTyped.text.toString() != "") {
                    runnable = Runnable {
                        val snack =
                            Snackbar.make(this@googleQueryTyped, context.getString(R.string.enter_atleast_three_character), 2000)
                        snack.gravityTop()
                        snack.view.background = ContextCompat.getDrawable(context, R.drawable.snack_container)
                        snack.setTextColor(ContextCompat.getColor(context, R.color.white))
                        snack.show()
                    }
                    handler.postDelayed(runnable!!, 1000)
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (runnable != null) handler.removeCallbacks(runnable!!)
        }
    })
}

fun AutoCompleteTextView.googleQueryTyped(report: (Boolean, String) -> Unit) {

    var runnable: Runnable? = null
    var handler: Handler? = null
    val timeToWait = 700L //change this one for delay (time in milli)

    handler = Handler()

    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (s?.length!! >= 3) {
                runnable = Runnable {
                    if (this@googleQueryTyped.text.toString().isEmpty().not()) {
                        report.invoke(false, this@googleQueryTyped.text.toString())
                    }
                }
                handler.postDelayed(runnable!!, timeToWait)

            } else {

                report.invoke(true, "")

                if (this@googleQueryTyped.text.toString() != "") {
                    runnable = Runnable {
                        val snack =
                            Snackbar.make(this@googleQueryTyped.rootView, context.getString(R.string.enter_atleast_three_character), 2000)
                        snack.gravityTop()
                        snack.view.background = ContextCompat.getDrawable(context, R.drawable.snack_container)
                        snack.setTextColor(ContextCompat.getColor(context, R.color.white))
                        snack.show()
                    }
                    handler.postDelayed(runnable!!, 1000)
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (runnable != null) handler.removeCallbacks(runnable!!)
        }
    })
}

//fun NavController.finishFrag(id: Int) {
//    this.popBackStack(id, true)
//    this.navigate(id)
//}

fun NavController.finishFrag(current: Int, to: Int) {
    this.popBackStack(current, true)
    this.navigate(to)
}

fun Fragment.onBackPressed(action: () -> Unit) {
    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
        override
        fun handleOnBackPressed() {
            action()
        }
    })
}

fun Fragment.isFragmentInBackStack(destinationId: Int) =
    try {
        findNavController().getBackStackEntry(destinationId)
        true
    } catch (e: Exception) {
        false
    }

fun Fragment.invokeNavigationUp(bundle: Bundle? = null) {
    Pref.needApi = true
//    (activity as MainActivity).setBundle(bundle)
    (activity as MainActivity).onSupportNavigateUp()
}

fun Fragment.goBack() {
    findNavController().navigateUp()
}

fun Context.openSingleButtonAlert(title: String?,msg: String?){
    val dialog = Dialog(this)
    if (dialog.isShowing) dialog.dismiss()
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.window!!.setLayout(
        ActionBar.LayoutParams.MATCH_PARENT,
        ActionBar.LayoutParams.MATCH_PARENT
    )
    val binding: SingleBtnAlertBinding =
        DataBindingUtil.inflate(dialog.layoutInflater, R.layout.single_btn_alert, null, false)
    dialog.setContentView(binding.root)
    dialog.setCancelable(true)
    dialog.show()

//    binding.alertMsg.text = msg
//    binding.textView10.text = title
//    binding.alertOk.setOnClickListener { _ ->
//        dialog.dismiss()
//    }
}

fun Fragment.findNavControllerSafely(id: Int): NavController? {
    return if (findNavController().currentDestination?.id == id) {
        findNavController()
    } else {
        null
    }
}

fun Fragment.openSingleButtonAlert(title: String?, msg: String?, buttonText: String = "") {
    val dialog = Dialog(requireContext())
    if (dialog.isShowing) dialog.dismiss()
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.window!!.setLayout(
        ActionBar.LayoutParams.MATCH_PARENT,
        ActionBar.LayoutParams.MATCH_PARENT
    )
    val binding: SingleBtnAlertBinding = DataBindingUtil.inflate(dialog.layoutInflater, R.layout.single_btn_alert, null, false)
    dialog.setContentView(binding.root)
    dialog.setCancelable(true)
    dialog.show()

    binding.msgTxt.text = msg
    binding.titleTxt.text = title

    if (buttonText.isNotEmpty()) binding.submitBtn.text = buttonText

    binding.submitBtn.setOnClickListener { _ ->
        dialog.dismiss()
    }
}

fun Fragment.openSingleButtonAlert(title: String?, msg: String?, click: () -> Unit) {
    val dialog = Dialog(requireContext())
    if (dialog.isShowing) dialog.dismiss()
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.window!!.setLayout(
        ActionBar.LayoutParams.MATCH_PARENT,
        ActionBar.LayoutParams.MATCH_PARENT
    )
    val binding: SingleBtnAlertBinding = DataBindingUtil.inflate(dialog.layoutInflater, R.layout.single_btn_alert, null, false)
    dialog.setContentView(binding.root)
    dialog.setCancelable(true)
    dialog.show()

    binding.msgTxt.text = msg
    binding.titleTxt.text = title
    binding.submitBtn.setOnClickListener { _ ->
        dialog.dismiss()
        click.invoke()
    }
}

fun Fragment.imageChooserDialog(showPdf: Boolean = false, click: (String) -> Unit) {
    val dialog = Dialog(requireContext())
    if (dialog.isShowing) dialog.dismiss()
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.window!!.setLayout(
        ActionBar.LayoutParams.MATCH_PARENT,
        ActionBar.LayoutParams.MATCH_PARENT
    )
    dialog.window!!.attributes.gravity = Gravity.BOTTOM
    dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation

    val binding: ImageChooserAlertLytBinding = DataBindingUtil.inflate(dialog.layoutInflater, R.layout.image_chooser_alert_lyt, null, false)
    dialog.setContentView(binding.root)
    dialog.setCancelable(true)
    dialog.show()

    binding.pdfTxt.isVisible = showPdf

    binding.galleryTxt.setOnClickListener { _ ->
        click.invoke("Gallery")
        dialog.dismiss()
    }

    binding.cameraTxt.setOnClickListener {
        click.invoke("Camera")
        dialog.dismiss()
    }

    binding.pdfTxt.setOnClickListener {
        click.invoke("Pdf")
        dialog.dismiss()
    }

    binding.cancelBtn.setOnClickListener {
        dialog.dismiss()
    }
}


//@BindingAdapter("loadGlide")
//fun ImageView.loadGlide(url: String?) {
//    Glide.with(this.context)
//        .load(IMAGE_BASE_URL + url)
//        .error(R.drawable.placeholder_ico)
//        .placeholder(R.drawable.placeholder_ico)
//        .into(this)
//}

//@BindingAdapter("loadGlideCircle")
//fun ImageView.loadGlideCircle(url: String?) {
//    Glide.with(this.context)
//        .load(IMAGE_BASE_URL + url)
//        .apply(RequestOptions.circleCropTransform())
//        .error(R.drawable.circle_app_ico)
//        .placeholder(R.drawable.circle_app_ico)
//        .into(this)
//}
//@BindingAdapter("loadGlideCircleOrAppIcon")
//fun ImageView.loadGlideCircleOrAppIcon(url: String?) {
//    Glide.with(this.context)
//        .load(IMAGE_BASE_URL + url)
//        .apply(RequestOptions.circleCropTransform())
//        .error(R.mipmap.ic_launcher_round)
//        .placeholder(R.mipmap.ic_launcher_round)
//        .into(this)
//}

@BindingAdapter("loadGlideWithoutBaseUrl")
fun ImageView.loadGlideWithoutBaseUrl(url: Any?) {
    Glide.with(this.context)
        .load(url)
        .placeholder(R.drawable.app_icon_foreground)
        .error(R.drawable.app_icon_foreground)
        .into(this)
}


//@BindingAdapter("loadDrawable")
//fun ImageView.loadDrawable(drawable: Drawable) {
//    Glide.with(this.context)
//        .load(drawable)
//        .error(R.drawable.black_app_ico)
//        .into(this)
//}

@BindingAdapter("convertPrgDate")
fun TextView.convertPrgDate(date: String) {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
    val outputFormat = SimpleDateFormat("E, MMM dd • h:mm a", Locale.ENGLISH)

//    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
//    calendar.timeInMillis = date
    text = outputFormat.format(inputFormat.parse(date)!!)
}

fun String.convertContentDate(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
    val outputFormat = SimpleDateFormat("E, MMM dd", Locale.ENGLISH)

//    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
//    calendar.timeInMillis = date
    return outputFormat.format(inputFormat.parse(this)!!)
}

@BindingAdapter("txtColor")
fun TextView.txtColor(color: Int) {
    setTextColor(color)
}

//fun Activity.attachFullScreen() {
//    window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
//}
@BindingAdapter("appendDate", "appendTime", requireAll = true)
fun TextView.appendDateTime(date: String, time: String) {

    var timeOut = ""
    var dateOut = ""

    return try {
        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
        val outputDateFormat = SimpleDateFormat("E, MMM dd", Locale.ENGLISH)
        dateOut = outputDateFormat.format(inputDateFormat.parse(date)!!)

        val inputTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val timeFormat = SimpleDateFormat(" • h:mm a", Locale.ENGLISH)
        timeOut = timeFormat.format(inputTime.parse(time)!!)

        text = dateOut + timeOut
    } catch (e: ParseException) {
        e.message
        text = dateOut + timeOut
    }

}


@BindingAdapter("feedbackDate")
fun TextView.feedbackDate(date: String) {
    // UTC time to LocaleTime

    var timeOut = ""
    var dateOut = ""

    return try {
        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("MMM dd, ", Locale.ENGLISH)
        dateOut = outputDateFormat.format(inputDateFormat.parse(date)!!)

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")
        val utcDate = inputFormat.parse(date)

        val outputFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
        outputFormat.timeZone = TimeZone.getDefault()
        timeOut = outputFormat.format(utcDate)

        text = "Sent " + dateOut + "at " + timeOut
    } catch (e: ParseException) {
        e.message
        text = "Sent " + dateOut + "at " + timeOut
    }
}


@BindingAdapter("publishedDate")
fun TextView.publishedDate(date: String) {

    var dateOut = ""

    return try {
        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH)
        val outputDateFormat = SimpleDateFormat("EEE, MMM dd", Locale.ENGLISH)
        dateOut = outputDateFormat.format(inputDateFormat.parse(date)!!)

        text = "Published " + dateOut
    } catch (e: ParseException) {
        e.message
        text = "Sent " + dateOut
    }
}

@BindingAdapter("isHot")
fun ImageView.isHot(tickets: Int) {
    isInvisible = tickets !in 1..5
}

fun convertPrgDate(date: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
    val outputFormat = SimpleDateFormat("E, MMM dd • h:mm a", Locale.ENGLISH)

//    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
//    calendar.timeInMillis = date

    return outputFormat.format(inputFormat.parse(date)!!)
}

fun convertPrgDateWithoutTime(date: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
    val outputFormat = SimpleDateFormat("E, MMM dd ", Locale.ENGLISH)

//    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
//    calendar.timeInMillis = date

    return outputFormat.format(inputFormat.parse(date)!!)
}

fun Activity.attachFullScreen(view: View) {
    this.apply {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.transparent)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        ViewCompat.setOnApplyWindowInsetsListener(view) { root, windowInset ->
            val inset = windowInset.getInsets(WindowInsetsCompat.Type.systemBars())
            val inset1 = windowInset.getInsets(WindowInsetsCompat.Type.statusBars())
            root.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                leftMargin = inset.left
                bottomMargin = inset.bottom
                topMargin = inset1.top
                rightMargin = inset.right
            }
            WindowInsetsCompat.CONSUMED
        }
    }
}

fun Activity.detachFullScreen() {
    window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
}

fun Window.setLightStatusBars(b: Boolean) {
    WindowCompat.getInsetsController(this, decorView).isAppearanceLightStatusBars = b
}

//fun ConstraintLayout.isClicked(b: Boolean) {
//    background = AppCompatResources.getDrawable(context, if (b) R.drawable.search_chip_lyt_dark else R.drawable.search_chip_lyt_light)
//}

//fun TextView.isClicked(click: () -> Unit) {
//    var b = false
//    setOnClickListener {
//        b = !b
//
//        setTextColor(ContextCompat.getColor(context, if (b) R.color.white else R.color.black))
//        background = AppCompatResources.getDrawable(context, if (b) R.drawable.search_chip_lyt_dark else R.drawable.search_chip_lyt_light)
//
//        click.invoke()
//    }
//
//}

inline fun <reified T : Parcelable> Intent.passedParcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}

inline fun <reified T : Parcelable> Bundle.passedParcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}

inline fun <reified T : Parcelable> Bundle.passedParcelableArrayList(key: String): ArrayList<T>? = when {
    SDK_INT >= 33 -> getParcelableArrayList(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableArrayList(key)
}

inline fun <reified T : Parcelable> Intent.passedParcelableArrayList(key: String): ArrayList<T>? = when {
    SDK_INT >= 33 -> getParcelableArrayListExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableArrayListExtra(key)
}

fun Any.toJson(): String = Gson().toJson(this)

inline fun <reified T : Any> String.fromJson(): T = Gson().fromJson(this, T::class.java)

fun getErrorResponse(errorString: JSONObject): String {
    return errorString.optString("message")
}

fun HttpException.getErrorResponse(): String {
    return try {
        JSONObject((this as? HttpException)?.response()?.errorBody()?.string()!!).optString("message")
    } catch (e: java.lang.Exception) {
        "Server exception"
    }
}

fun String.convertMatchDate(): String {
// yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
// 2022-08-20T10:57
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH)
    val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

    return outputFormat.format(inputFormat.parse(this)!!)

}

fun String.convertDate(dateFormat: String): String {
// yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
// 2022-08-20T10:57
    return if (this.isNotEmpty()) {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat(dateFormat, Locale.ENGLISH)

        outputFormat.format(inputFormat.parse(this)!!)
    } else ""
}

fun String.convertToServerDateFormat(dateFormat: String): String {
// yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
// 2022-08-20T10:57
    return if (this.isNotEmpty()) {
        val inputFormat = SimpleDateFormat(dateFormat, Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)


        outputFormat.format(inputFormat.parse(this)!!)
    } else ""
}

fun Activity.changeStatusBarColor(color: Int = ContextCompat.getColor(this, R.color.white), isLight: Boolean) {
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = color

    WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = isLight
}

//fun ImageView.bookmark(bookmarked: Boolean) {
//    if (bookmarked) {
//        this.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.bookmarked_ico))
//    } else {
//        this.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.bookmark_ico))
//    }
//}

fun getStartEndWeek(date: (String, String) -> Unit) {
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    calendar.time = Date()
    calendar.firstDayOfWeek = Calendar.SUNDAY

    calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
    val startDate = calendar.time

    calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
    val endDate = calendar.time

    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

    date.invoke(sdf.format(startDate), sdf.format(endDate))

}

fun Any.log(input: Any) {
    println(LOG + input)
}


@SuppressLint("ClickableViewAccessibility")
fun EditText.setupClearButtonWithAction(unit: () -> Unit) {

    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            val clearIcon = if (editable?.isNotEmpty() == true) R.drawable.close_ico else 0
            setCompoundDrawablesWithIntrinsicBounds(0, 0, clearIcon, 0)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
    })

    setOnTouchListener(View.OnTouchListener { _, event ->
        if (event.action == MotionEvent.ACTION_UP) {
            if (event.rawX >= (this.right - this.compoundPaddingRight)) {
                this.text.clear()
                this.clearFocus()
                unit.invoke()
                return@OnTouchListener true
            }
        }
        return@OnTouchListener false
    })
}

@BindingAdapter("isClosing")
fun ImageView.isClosing(dateString: String?) {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
    val givenDate = dateFormat.parse(dateString)
    val now = Calendar.getInstance().time

    val diff = givenDate!!.time - now.time
    if (givenDate.before(now)) {
        this.visibility = isOnTheList(false)
        return
    }
    this.visibility = isOnTheList(diff <= 48 * 60 * 60 * 1000)
}


fun String.alphabetsOnly(): String {

    return this.filter { !it.isDigit() }
}

fun yearDifference(dateString: String?): Int {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
    val givenDate = dateFormat.parse(dateString)


    val c = Calendar.getInstance()
    if (givenDate != null) {
        c.time = givenDate
    }
    return Period.between(
        LocalDate.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH)),
        LocalDate.now()
    ).years


}

fun isOnTheList(b: Boolean): Int {
    return if (b) View.VISIBLE else View.INVISIBLE
}


fun View.setTopMargin() {
    val params = layoutParams as ViewGroup.MarginLayoutParams
    params.setMargins(0, 50.toDp(context).toInt(), 0, 0)
    layoutParams = params
}

fun Int.toDp(context: Context): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        toFloat(),
        context.resources.displayMetrics
    )
}

fun RecyclerView.clippingRec(size: Int) {
    if (size == 1) {
        setPadding(25.toDp(context).toInt(), 0, 0, 0)
    } else {
        setPadding(25.toDp(context).toInt(), 0, 10.toDp(context).toInt(), 0)
    }
    clipToPadding = false
}

fun RecyclerView.clippingBottomRec(input: Int) {
    setPadding(0, 0, 0, input.toDp(context).toInt())
    clipToPadding = false
}

fun Fragment.isAllPermissionsGranted(permissions: Array<String>) = permissions.all {
    ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
}

fun Fragment.openAppSettings() {
    startActivity(
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", requireActivity().packageName, null)
        )
    )
}

internal fun Fragment.showRationaleDialog(permissions: List<PermissionStatus>, request: PermissionRequest) {
    val msg = getString(R.string.rationale_permissions, permissions.toMessage<PermissionStatus.Denied.ShouldShowRationale>())

    AlertDialog.Builder(requireContext())
        .setTitle(R.string.permissions_required)
        .setMessage(msg)
        .setPositiveButton(R.string.request_again) { _, _ ->
            // Send the request again.
            request.send()
        }
        .setNegativeButton(android.R.string.cancel, null)
        .show()
}

internal fun Fragment.showPermanentlyDeniedDialog(permissions: List<PermissionStatus>) {
    val msg = getString(R.string.permanently_denied_permissions, permissions.toMessage<PermissionStatus.Denied.Permanently>())

    openSingleButtonAlert("Warning", msg) {
        openAppSettings()
    }
}
//
private inline fun <reified T : PermissionStatus> List<PermissionStatus>.toMessage(): String = filterIsInstance<T>()
    .joinToString { it.permission }

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun isPhotoPickerAvailable(): Boolean {
    return if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        true
    } else if (SDK_INT >= Build.VERSION_CODES.R) {
        getExtensionVersion(Build.VERSION_CODES.R) >= 2
    } else {
        false
    }
}

@Throws(IOException::class)
fun Fragment.createImageFile(): File {
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val image = File.createTempFile(
        imageFileName, /* prefix */
        ".jpg", /* suffix */
        storageDir      /* directory */
    )

    // Save a file: path for use with ACTION_VIEW intents
    //mCurrentPhotoPath = image.absolutePath
    return image
}

fun rotateBitmap(source: Bitmap, angle: Float): Bitmap? {
    val matrix = Matrix()
//    matrix.postRotate(angle)
    if (source.width >= source.height) {
        matrix.setRotate(90F);
    } else {
        matrix.setRotate(0F);
    }

    return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
}

fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
    val bytes = ByteArrayOutputStream()
    inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path: String =
        MediaStore.Images.Media.insertImage(
            inContext.contentResolver,
            inImage,
            "IMG_" + Calendar.getInstance().time,
            null
        )
    return Uri.parse(path)
}

fun isVideo(path: String): Boolean {
    val mimeType: String = URLConnection.guessContentTypeFromName(path)
    return mimeType.startsWith("video")
}

val File.size get() = if (!exists()) 0.0 else length().toDouble()
val File.sizeInKb get() = size / 1024
val File.sizeInMb get() = sizeInKb / 1024
val File.sizeInGb get() = sizeInMb / 1024
val File.sizeInTb get() = sizeInGb / 1024

fun String.extractYtId(): String {
    var videoId = ""
    val regex =
        "http(?:s)?:\\/\\/(?:m.)?(?:www\\.)?youtu(?:\\.be\\/|(?:be-nocookie|be)\\.com\\/(?:watch|[\\w]+\\?(?:feature=[\\w]+.[\\w]+\\&)?v=|v\\/|e\\/|embed\\/|user\\/(?:[\\w#]+\\/)+))([^&#?\\n]+)"
    val pattern: Pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE)
    val matcher: Matcher = pattern.matcher(this)
    if (matcher.find()) {
        videoId = matcher.group(1) as String
    }
    return videoId
}

fun String.isValidYtLink(): Boolean {
    return Regex("^((?:https?:)?\\/\\/)?((?:www|m)\\.)?((?:youtube\\.com|youtu.be))(\\/(?:[\\w\\-]+\\?v=|embed\\/|v\\/)?)([\\w\\-]+)(\\S+)?\$").matches(
        this
    )
}

fun String.isValidTwitterLink(): Boolean {
    return Regex("^https?://(?:www\\.)?twitter\\.com/(?:\\w+)/status/(\\d+)(?:\\?.*)?\$").matches(this)
}

fun String.isValidInstaLink(): Boolean {
    return Regex("^https?://(?:www\\.)?instagram\\.com/(?:p|reel|tv)/(?:[a-zA-Z0-9_\\-]+/)?(?:\\?[^\\s\"]*)?\$").matches(this)
}

fun String.isValidUrl(): Boolean {
    return Patterns.WEB_URL.matcher(this).matches()
}

fun PackageManager.getPackageInfoCompat(packageName: String, flags: Int = 0): PackageInfo =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(flags.toLong()))
    } else {
        @Suppress("DEPRECATION") getPackageInfo(packageName, flags)
    }

fun Fragment.openPlatform(platform: Int, url: String) {

    val platformPackage = when (platform) {
        0 -> "com.twitter.android"
        1 -> "com.instagram.android"
        2 -> "com.google.android.youtube"
        else -> ""
    }

    var intent: Intent? = null
    try {
        requireContext().packageManager.getPackageInfoCompat(platformPackage)
        intent = Intent(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    } catch (e: Exception) {
        intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    }

    startActivity(intent!!)
}

fun toDuration(input: String): String {


    var convTime: String? = null
    val suffix = "ago"

    try {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX", Locale.getDefault())
        val pasTime = dateFormat.parse(input)
        val nowTime = Date()
        val dateDiff = nowTime.time - pasTime!!.time
        val second = TimeUnit.MILLISECONDS.toSeconds(dateDiff)
        val minute = TimeUnit.MILLISECONDS.toMinutes(dateDiff)
        val hour = TimeUnit.MILLISECONDS.toHours(dateDiff)
        val day = TimeUnit.MILLISECONDS.toDays(dateDiff)

        if (second < 60) {
            convTime = "$second seconds $suffix"
        } else if (minute < 60) {
            convTime = "$minute minutes $suffix"
        } else if (hour < 24) {
            convTime = "$hour hours $suffix"
        } else if (day >= 7) {
            convTime = if (day > 360) {
                (day / 360).toString() + " years " + suffix
            } else if (day > 30) {
                (day / 30).toString() + " months " + suffix
            } else {
                (day / 7).toString() + " week " + suffix
            }
        } else if (day < 7) {
            convTime = "$day Days $suffix"
        }

    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return convTime!!

}

fun toChatTime(input: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX", Locale.getDefault())
        val outputFormat = SimpleDateFormat("h:mm a", Locale.ENGLISH)
        outputFormat.format(inputFormat.parse(input)!!)

    } catch (e: ParseException) {
        e.printStackTrace()
        ""
    }
}

fun toChatDate(input: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX", Locale.getDefault())
        val outputFormat = SimpleDateFormat("EEEE, MMMM d yyyy", Locale.ENGLISH)
        outputFormat.format(inputFormat.parse(input)!!)

    } catch (e: ParseException) {
        e.printStackTrace()
        ""
    }
}

fun getRealPathFromURI(context: Context, uri: Uri): String? {
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    cursor?.moveToFirst()
    val documentId = cursor?.getString(0)

    return try {
        documentId?.let {
            if (it.contains(":")) {
                val split = it.split(":")
                val type = split[0]
                if ("primary".equals(type, ignoreCase = true)) {
                    return "${Environment.getExternalStorageDirectory()}/${split[1]}"
                } else ""
            } else {
                return it
            }
        }
    } catch (e: Exception) {
        e.printStackTrace().toString()
    }
}

fun Fragment.getPath(uri: Uri): String? {
    val projection = arrayOf(MediaStore.Images.Media.DATA)
    val cursor = requireActivity().contentResolver.query(uri, projection, null, null, null)
    val column_index = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
    cursor?.moveToFirst()
    return cursor?.getString(column_index!!)
}

fun ConstraintLayout.viewLayout(show: Boolean) {

    if (show) {
        animate()
            .alpha(1f)
            .translationYBy(1f)
            .setDuration(100)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    this@viewLayout.isVisible = show
                }
            })
    } else {
        animate()
            .alpha(0f)
            .translationYBy(0f)
            .setDuration(100)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    this@viewLayout.isVisible = show
                }
            })
    }
}

fun View.hideView(hide: Boolean) {
    if (hide) {
        animate()
            .alpha(0f)
            .setDuration(700)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    this@hideView.isVisible = hide
                }
            })
    } else {
        animate()
            .alpha(1f)
            .setDuration(700)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    this@hideView.isVisible = hide
                }
            })
    }
}

//fun Fragment.viewChatMedia(state: String, url: String) {
//    val dialog = Dialog(requireContext(), android.R.style.Theme_Translucent_NoTitleBar)
//    if (dialog.isShowing) dialog.dismiss()
//
//    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//    dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//    dialog.window!!.statusBarColor = resources.getColor(R.color.black)
//    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.BLACK))
//    dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
//    dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//
//    val binding: FragmentChatMediaBottomSheetBinding = DataBindingUtil.inflate(
//        dialog.layoutInflater,
//        R.layout.fragment_chat_media_bottom_sheet, null, false
//    )
//    dialog.setContentView(binding.root)
//    dialog.show()
//
//    val exoPlayer = ExoPlayer.Builder(requireContext()).build()
//    binding.videoPlayer.player = exoPlayer
//
//    when (state) {
//        "media_img" -> {
//            binding.zoomImg.isInvisible = false
//            binding.zoomImg.loadGlideWithoutBaseUrl(url)
//        }
//        "media_video" -> {
//            binding.videoPlayer.isInvisible = false
//
//            val mediaItem = MediaItem.fromUri(url)
//            exoPlayer.addMediaItem(mediaItem)
//            exoPlayer.prepare()
//            exoPlayer.play()
//        }
//        "media_pdf" -> {
//            binding.webView.isInvisible = false
//
//            binding.webView.settings.javaScriptEnabled = true
//            binding.webView.loadUrl("https://docs.google.com/gview?embedded=true&url=$url")
//        }
//
//
//    }
//
//
//    binding.closeBtn.setOnClickListener {
//        if (exoPlayer.isPlaying) exoPlayer.stop()
//        dialog.dismiss()
//    }
//}

fun ImageView.previewPdf(file: File) {
    val mFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
    val mPdfRenderer = PdfRenderer(mFileDescriptor)
    if (mPdfRenderer.pageCount <= 0) {
        Log.d("RSKLSS", "loadThumbnailFromPdfFile: No pages in pdf")
    } else {
        val mCurrentPage = mPdfRenderer.openPage(0)
        val bitmap = Bitmap.createBitmap(mCurrentPage.width, mCurrentPage.height, Bitmap.Config.ARGB_8888)
        mCurrentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        setImageBitmap(bitmap)
    }
}

fun View.hideShowItemView(toShow: Boolean) {
    isVisible = toShow
    layoutParams.height = if (toShow) ViewGroup.LayoutParams.WRAP_CONTENT else 0
}

fun View.hideItemViewHR(toShow: Boolean) {
    isVisible = toShow
    layoutParams.width = if (toShow) ViewGroup.LayoutParams.WRAP_CONTENT else 0
}

fun View.setMargin(left: Int? = null, top: Int? = null, right: Int? = null, bottom: Int? = null) {
    val params = (layoutParams as? ViewGroup.MarginLayoutParams)
    params?.setMargins(
        left ?: params.leftMargin,
        top ?: params.topMargin,
        right ?: params.rightMargin,
        bottom ?: params.bottomMargin)
    layoutParams = params
}

fun getCurrentDateTime(): String {
    return try {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy hh:mm a", Locale.getDefault())
        dateFormat.format(Date())
    } catch (e: Exception) {
        "Error generating date"
    }
}

fun getTodayDate(): String {
    return try {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        dateFormat.format(Date())
    } catch (e: Exception) {
        "Error generating date"
    }
}
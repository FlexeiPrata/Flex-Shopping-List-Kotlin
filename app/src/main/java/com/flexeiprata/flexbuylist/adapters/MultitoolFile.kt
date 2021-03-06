package com.flexeiprata.flexbuylist.adapters

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import android.view.inputmethod.InputMethodManager
import com.flexeiprata.flexbuylist.db.ContApplication
import kotlin.math.roundToInt

const val SHARED_PREF_TAG = "shared_pref_flex_buy_list"
const val FIRST_LAUNCH = "first_launch"
const val FIRST_LAUNCH_MAIN = "first_launch_main"
const val FIRST_LAUNCH_BOTTOM_ADDER = "first_launch_bottom_adder"
const val FIRST_LAUNCH_LIST = "first_launch_list"
const val FIRST_LAUNCH_ELEMENT = "first_launch_element"

const val DEBU = "DEBU"

const val LIST_PRIME = "Shopping #1"
const val LIST_PRIME_DESC = "Test me!"
const val ITEM_PRIME = "Snickers"

//анимация плавного сжатия View
fun collapse(view: View) : Animation{
        val actualHeight = view.measuredHeight
        val animation: Animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                if (interpolatedTime == 1f) view.visibility = View.GONE else {
                    view.layoutParams.height = actualHeight - (actualHeight * interpolatedTime).toInt()
                    view.requestLayout()
                }
            }
        }
        animation.duration = (actualHeight / view.context.resources.displayMetrics.density).toLong() * 2
        return animation
    }

//анимация плавного расширения View
fun expandAction(view: View) {
    view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    val actualHeight = view.measuredHeight
    view.layoutParams.height = 0
    view.visibility = View.VISIBLE
    val animation: Animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            view.layoutParams.height =
                if (interpolatedTime == 1f) ViewGroup.LayoutParams.WRAP_CONTENT else (actualHeight * interpolatedTime).toInt()
            view.requestLayout()
        }
    }
    animation.duration = (actualHeight / view.context.resources.displayMetrics.density).toLong() * 2
    view.startAnimation(animation)
}

//функция для того чтобы спрятать Soft Keyboard из не активити
fun hideKeyboardFrom(context: Context, view: View) {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

//конвертация Drawable в Bitmap, необходимая для рисования векторного элемента
fun drawableToBitmap(drawable: Drawable): Bitmap? {
    if (drawable is BitmapDrawable) {
        return drawable.bitmap
    }
    val bitmap =
        Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
    drawable.draw(canvas)
    return bitmap
}

//конвертация dp в пиксели
fun convertDpToPx(context: Context, dp: Int): Int {
    return (dp * (context.resources.displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
}

fun getStringWhenever(stringID: Int){
    val context = ContApplication() as Context
}
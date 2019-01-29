package com.wei.wanandroidkotlin.model

import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.util.Log

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * @author XiangWei
 * @since 2018/12/10
 */
object Test {

    fun matcherSearchTitle(color: Int, text: String?, keyword: String?): SpannableString {
        var text = text
        var keyword = keyword
        val s = SpannableString(text)
        keyword = escapeExprSpecialWord(keyword)
        text = escapeExprSpecialWord(text)
        if (text!!.contains(keyword!!) && !TextUtils.isEmpty(keyword)) {
            try {
                val p = Pattern.compile(keyword)
                val m = p.matcher(s)
                while (m.find()) {
                    val start = m.start()
                    val end = m.end()
                    s.setSpan(ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            } catch (e: Exception) {
                Log.e("exception: ", e.toString())
            }

        }
        return s
    }

    fun escapeExprSpecialWord(keyword: String?): String? {
        var keyword = keyword
        if (!TextUtils.isEmpty(keyword)) {
            val fbsArr = arrayOf("\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|")
            for (key in fbsArr) {
                if (keyword!!.contains(key)) {
                    keyword = keyword.replace(key, "\\" + key)
                }
            }
        }
        return keyword
    }

}

/** 生成的 Java 类名 */
@file:JvmName("Global")
package com.shijingfeng.skin_changer.global

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.shijingfeng.skin_changer.constant.SP_NAME

/**
 * Function: 全局数据
 * Date: 2020/7/20 14:59
 * Description:
 * @author ShiJingFeng
 */

/** Application Context */
internal lateinit var appContext: Context

/** 主线程 Handler */
private val mainHandler = Handler(Looper.getMainLooper())

/** 获取 主线程 Handler */
fun getMainHandler() = mainHandler

/**
 * 运行在主线程
 * @param action 要执行的回调
 */
fun runOnUiThread(
    delay: Long = 0L,
    action: () -> Unit
) = mainHandler.postDelayed(action, delay)
package com.shijingfeng.app

import java.io.File

/**
 * Function:
 * Date: 2020/7/23 17:05
 * Description:
 * @author ShiJingFeng
 */

/** 内部存储 -> 应用私有目录 -> file目录  /data/data/<application package>/files/ */
val PERSONAL_INTERNAL_FILE_DIR: String by lazy {
    application.filesDir.absolutePath + File.separator
}

/** 内部存储 -> 应用私有目录 -> skin目录  /data/data/<application package>/files/skin/ */
internal val PERSONAL_INTERNAL_WAN_ANDROID_SKIN_FILE_DIR: String by lazy {
    val skinPath = PERSONAL_INTERNAL_FILE_DIR + "skin"
    val skinFile = File(skinPath)

    if (!skinFile.exists()) {
        skinFile.mkdirs()
    }
    skinPath + File.separator
}

/** 玩Android 本地皮肤文件 */
internal val WAN_ANDROID_SKIN_FILE: String by lazy {
    PERSONAL_INTERNAL_WAN_ANDROID_SKIN_FILE_DIR + WAN_ANDROID_SKIN_ASSETS_FILE
}

/** assets目录内 玩Android皮肤文件 */
internal const val WAN_ANDROID_SKIN_ASSETS_FILE = "wan_android_skin.skin"
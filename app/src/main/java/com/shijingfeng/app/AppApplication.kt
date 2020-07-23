package com.shijingfeng.app

import android.app.Application
import android.view.View
import com.shijingfeng.skin_changer.entity.SkinAttribute
import com.shijingfeng.skin_changer.listener.ExecuteListener
import com.shijingfeng.skin_changer.listener.ParseListener
import com.shijingfeng.skin_changer.manager.SkinChangerManager
import java.io.File
import java.io.FileOutputStream

/** Application实例 */
lateinit var application: AppApplication
/** SkinChangerManager实例 */
lateinit var skinChangerManager: SkinChangerManager

/**
 * Function:
 * Date: 2020/7/23 17:01
 * Description:
 * @author ShiJingFeng
 */
class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        application = this
        skinChangerManager = SkinChangerManager.Builder(context = this, skinChannel = "test")
            .setDefaultSkinPluginPath(WAN_ANDROID_SKIN_FILE)
            .setDefaultSkinPluginPackageName("com.shijingfeng.wan_android_skin")
            .setParseListener(object : ParseListener {
                override fun onParse(view: View, name: String): Any? {
                    return null
                }
            })
            .setExecuteListener(object : ExecuteListener {
                override fun onExecute(view: View, skinAttribute: SkinAttribute): Boolean {
                    return false
                }
            })
            .build()

        copySkinFileToLocal()
    }

    /**
     * 复制 asset目录中 玩Android skin文件 到本地内部存储目录中
     */
    private fun copySkinFileToLocal() {
        val skinFile = File(WAN_ANDROID_SKIN_FILE)

        if (!skinFile.exists() && skinFile.createNewFile()) {
            application.assets.open(WAN_ANDROID_SKIN_ASSETS_FILE).use { inputStream ->
                FileOutputStream(skinFile).use { fileOutputStream ->
                    val byteArray = ByteArray(1024)
                    var length: Int

                    while (inputStream.read(byteArray).also { length = it } != -1) {
                        fileOutputStream.write(byteArray, 0, length)
                    }
                }
            }
        }
    }

}
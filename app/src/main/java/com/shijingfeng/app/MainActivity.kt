package com.shijingfeng.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.shijingfeng.skin_changer.listener.SkinChangingCallback
import com.shijingfeng.skin_changer.manager.SkinChangerManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        skinChangerManager.register(this)

        btn_plugin.setOnClickListener { v ->
            skinChangerManager.changeSkinByPlugin(
                skinSuffix = "black",
                skinPluginPath = WAN_ANDROID_SKIN_FILE,
                skinPluginPackageName = "com.shijingfeng.wan_android_skin",
                skinChangingCallback = object : SkinChangingCallback {
                    override fun onStart() {
                        super.onStart()
                        Log.e("测试", "插件换肤开始")
                    }

                    override fun onError(e: Throwable?) {
                        super.onError(e)
                        Log.e("测试", "插件换肤失败")
                    }

                    override fun onCompleted() {
                        super.onCompleted()
                        Log.e("测试", "插件换肤成功")
                    }
                }
            )
        }

        btn_app_internal.setOnClickListener { v ->
            skinChangerManager.changeSkinByAppInternal("")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        skinChangerManager.unregister(this)
    }
}
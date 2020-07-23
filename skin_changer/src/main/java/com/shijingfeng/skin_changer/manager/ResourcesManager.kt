package com.shijingfeng.skin_changer.manager

import android.content.res.ColorStateList
import android.content.res.Resources
import android.content.res.Resources.NotFoundException
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.shijingfeng.skin_changer.annotation.SkinType.APP_INTERNAL
import com.shijingfeng.skin_changer.annotation.SkinType.PLUGIN
import com.shijingfeng.skin_changer.constant.*
import com.shijingfeng.skin_changer.global.appContext
import com.shijingfeng.skin_changer.util.getSkinPluginPackageName
import com.shijingfeng.skin_changer.util.getSkinSuffix
import com.shijingfeng.skin_changer.util.getSkinType

/**
 * Function: 资源管理器
 * Date: 2020/7/20 13:34
 * Description:
 * @author ShiJingFeng
 */
class ResourcesManager(
    /** 主题渠道 */
    private var mSkinChannel: String,
    /** Resources 资源类 */
    private var mResources: Resources
) {
    /**
     * 通过默认颜色资源名称 获取 当前主题显示的Color
     *
     * @param defaultColorResName 默认Color资源名称
     * @return 当前主题显示的Color
     */
    @ColorInt
    fun getRealColor(defaultColorResName: String): Int {
        val realColorResName = getRealResName(defaultColorResName)
        @ColorRes val colorId = getColorIdByResName(realColorResName)

        if (colorId != RESOURCE_NONE) {
            return when(getSkinType(mSkinChannel)) {
                // 插件式换肤
                PLUGIN -> mResources.getColor(colorId)
                // 应用内换肤
                APP_INTERNAL -> appContext.resources.getColor(colorId)
                else -> RESOURCE_NONE
            }
        }
        return RESOURCE_NONE
    }

    /**
     * 通过默认颜色资源名称 获取 当前主题显示的ColorStateList
     *
     * @param defaultColorResName 默认Color资源名称
     * @return 当前主题显示的ColorStateList
     */
    fun getRealColorStateList(defaultColorResName: String): ColorStateList? {
        val realColorResName = getRealResName(defaultColorResName)
        @ColorRes val colorId = getColorIdByResName(realColorResName)

        if (colorId != RESOURCE_NONE) {
            return when(getSkinType(mSkinChannel)) {
                // 插件式换肤
                PLUGIN -> mResources.getColorStateList(colorId)
                // 应用内换肤
                APP_INTERNAL -> appContext.resources.getColorStateList(colorId)
                else -> null
            }
        }
        return null
    }

    /**
     * 通过 Color资源名称 获取 Color ID
     *
     * @param colorResName Color资源名称
     * @return Color ID
     */
    @ColorRes
    private fun getColorIdByResName(
        colorResName: String
    ): Int {
        val skinType = getSkinType(mSkinChannel)
        val skinPackageName = getSkinPluginPackageName(mSkinChannel)

        return try {
            when (skinType) {
                // 插件式换肤
                PLUGIN -> mResources.getIdentifier(colorResName, RESOURCE_TYPE_COLOR, skinPackageName)
                // 应用内换肤
                APP_INTERNAL -> appContext.resources.getIdentifier(colorResName, RESOURCE_TYPE_COLOR, appContext.packageName)
                else -> RESOURCE_NONE
            }
        } catch (e: NotFoundException) {
            e.printStackTrace()
            RESOURCE_NONE
        }
    }

    /**
     * 通过默认Drawable资源名称 获取 当前主题显示的Drawable
     *
     * @param defaultDrawableResName 默认Drawable资源名称
     * @return 当前主题显示的Drawable
     */
    fun getRealDrawableByResName(
        defaultDrawableResName: String
    ): Drawable? {
        val realDrawableResName = getRealResName(defaultDrawableResName)
        @DrawableRes var drawableId = RESOURCE_NONE

        for (i in 0..1) {
            when (i) {
                0 -> drawableId = getDrawableIdByResName(realDrawableResName, RESOURCE_TYPE_DRAWABLE)
                1 -> drawableId = getDrawableIdByResName(realDrawableResName, RESOURCE_TYPE_MIPMAP)
            }
            if (drawableId != RESOURCE_NONE) {
                return when(getSkinType(mSkinChannel)) {
                    // 插件式换肤
                    PLUGIN -> mResources.getDrawable(drawableId)
                    // 应用内换肤
                    APP_INTERNAL -> appContext.resources.getDrawable(drawableId)
                    else -> null
                }
            }
        }
        return null
    }

    /**
     * 通过 Drawable资源名称 获取 Drawable ID
     *
     * @param drawableResName Drawable资源名称
     * @param drawableType Drawable类型 (drawable文件内Drawable, mipmap文件夹内Drawable)
     * @return Drawable ID
     */
    @DrawableRes
    private fun getDrawableIdByResName(
        drawableResName: String,
        drawableType: String = RESOURCE_TYPE_DRAWABLE
    ): Int {
        val skinType = getSkinType(mSkinChannel)
        val skinPackageName = getSkinPluginPackageName(mSkinChannel)

        return try {
            when (skinType) {
                // 插件式换肤
                PLUGIN -> mResources.getIdentifier(drawableResName, drawableType, skinPackageName)
                // 应用内换肤
                APP_INTERNAL -> appContext.resources.getIdentifier(drawableResName, drawableType, appContext.packageName)
                else -> RESOURCE_NONE
            }
        } catch (e: NotFoundException) {
            e.printStackTrace()
            RESOURCE_NONE
        }
    }

    /**
     * 根据后缀 获取要切换到的皮肤真实资源名称
     *
     * @param defaultResName 默认资源名称
     * @return 真实资源名称
     */
    private fun getRealResName(defaultResName: String): String {
        val skinSuffix = getSkinSuffix(mSkinChannel)

        if (skinSuffix.isEmpty() || defaultResName.isEmpty()) return defaultResName

        return "${defaultResName}_${skinSuffix}"
    }

    /**
     * 设置 Resources
     *
     * @param resources Resources
     */
    fun setResources(resources: Resources) {
        this.mResources = resources
    }

    /**
     * 获取 Resources
     */
    fun getResources() = mResources

}
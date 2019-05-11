package com.allen.common.utils

import android.content.Context
import android.content.SharedPreferences
import com.allen.common.base.BaseApplication
import java.io.*
import kotlin.reflect.KProperty


/**
 * Created by Allen on 2019/4/18
 *
 * kotlin委托属性+ SharedPreferences实例
 */
class Preference<T>(val key: String, private val default: T) {
    companion object {
        private val fileName = "kotlin_action_file"
    }

    private val prefs: SharedPreferences by lazy {
        BaseApplication.mApplication.getSharedPreferences(fileName, Context.MODE_PRIVATE)
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return getSharedPreferences(key, default)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putSharedPreferences(key, value)
    }

    private fun putSharedPreferences(key: String, value: T) = with(prefs.edit()) {
        when (value) {
            is Boolean -> putBoolean(key, value)
            is Int -> putInt(key, value)
            is Float -> putFloat(key, value)
            is Long -> putLong(key, value)
            is String -> putString(key, value)
            else -> putString(key, serialize(value))
        }.apply()

    }

    @Suppress("UNCHECKED_CAST")
    private fun getSharedPreferences(key: String, default: T): T = with(prefs) {
        val res: Any = when (default) {
            is Boolean -> getBoolean(key, default)
            is Long -> getLong(key, default)
            is Int -> getInt(key, default)
            is Float -> getFloat(key, default)
            is String -> getString(key, default)
            else -> deSerialization(getString(key, serialize(default)))
        }
        return res as T
    }

    /**
     * 序列化对象
     */
    @Throws(IOException::class)
    private fun <A> serialize(obj: A): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val objectOutputStream = ObjectOutputStream(
                byteArrayOutputStream)
        objectOutputStream.writeObject(obj)
        var serStr = byteArrayOutputStream.toString("ISO-8859-1")
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8")
        objectOutputStream.close()
        byteArrayOutputStream.close()
        return serStr
    }

    /**
     * 反序列化对象

     * @param str
     * *
     * @return
     * *
     * @throws IOException
     * *
     * @throws ClassNotFoundException
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IOException::class, ClassNotFoundException::class)
    private fun <A> deSerialization(str: String): A {
        val redStr = java.net.URLDecoder.decode(str, "UTF-8")
        val byteArrayInputStream = ByteArrayInputStream(
                redStr.toByteArray(charset("ISO-8859-1")))
        val objectInputStream = ObjectInputStream(
                byteArrayInputStream)
        val obj = objectInputStream.readObject() as A
        objectInputStream.close()
        byteArrayInputStream.close()
        return obj
    }

}
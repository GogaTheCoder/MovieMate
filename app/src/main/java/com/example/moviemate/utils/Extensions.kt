package com.example.moviemate.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

inline fun <reified T : ViewBinding> ViewGroup.viewBinding(
    inflate: (LayoutInflater, ViewGroup, Boolean) -> T
) = inflate(LayoutInflater.from(context), this, false)

inline fun <reified T : ViewBinding> ViewGroup.viewBinding() = viewBinding<T> { inflater, container, attach ->
    val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
    val method = type.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
    method.invoke(null, inflater, container, attach) as T
}
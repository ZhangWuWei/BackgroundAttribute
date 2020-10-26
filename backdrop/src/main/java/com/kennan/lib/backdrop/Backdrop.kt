package com.kennan.lib.backdrop

import android.content.Context
import android.view.LayoutInflater

object Backdrop {
    fun initBackDrop(context: Context){
        val inflater = LayoutInflater.from(context)
        val factory = BackdropFactory(inflater, inflater.factory2,
            inflater.factory, getField(LayoutInflater::class.java, "mPrivateFactory", inflater), context)
        setField(LayoutInflater::class.java, "mFactory2", inflater, factory)
    }

    private fun setField(cla:Class<*>, name:String, ob:Any, value:Any):Boolean{
        try {
            var field = cla.getDeclaredField(name)
            field.isAccessible = true
            field.set(ob, value)
            return true
        }catch (e : Exception){
            e.printStackTrace()
        }
        return false
    }

    private fun getField(cla:Class<*>, name:String, ob:Any): LayoutInflater.Factory2?{
        try {
            var field = cla.getDeclaredField(name)
            field.isAccessible = true
            val result = field.get(ob)
            if(result is LayoutInflater.Factory2){
                return result
            }
            return null
        }catch (e : Exception){
            e.printStackTrace()
        }
        return null
    }
}
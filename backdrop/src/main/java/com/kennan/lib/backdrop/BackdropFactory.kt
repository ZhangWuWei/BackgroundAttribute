package com.kennan.lib.backdrop

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View

class BackdropFactory : LayoutInflater.Factory2 {

    companion object{
        private val sClassPrefixList = arrayOf(
            "android.widget.",
            "android.view.",
            "android.webkit.",
            "android.app."
        )
    }

    private var mInflater : LayoutInflater
    private var factory2 : LayoutInflater.Factory2?=null
    private var factory : LayoutInflater.Factory?=null
    private var privateFactory : LayoutInflater.Factory2?=null
    private val context : Context

    constructor(
        mInflater: LayoutInflater,
        factory2: LayoutInflater.Factory2?,
        factory: LayoutInflater.Factory?,
        privateFactory: LayoutInflater.Factory2?,
        context: Context
    ) {
        this.mInflater = mInflater
        this.factory2 = factory2
        this.factory = factory
        this.privateFactory = privateFactory
        this.context = context
    }


    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        try {
            var view : View?
            if(factory2 != null){
                view = factory2!!.onCreateView(parent, name, context, attrs)
            }else if(factory != null){
                view = factory!!.onCreateView(name, context, attrs)
            }else{
                view = null
            }

            if (view == null && privateFactory != null) {
                view = privateFactory!!.onCreateView(parent, name, context, attrs)
            }

            if (view == null) {

                    if (-1 == name.indexOf('.')) {
                        view = onCreateView(name, attrs)
                    } else {
                        view = mInflater?.createView(name, null, attrs)
                    }
            }
            parserBackdrop(view, attrs, context)
            return view
        }catch (e: Exception){

        }
        return null
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return onCreateView(null, name, context, attrs)
    }


    private fun onCreateView(name: String?, attrs: AttributeSet?): View? {
        for (prefix in sClassPrefixList) {
            try {
                val view = mInflater?.createView(name, prefix, attrs)
                if (view != null) {
                    return view
                }
            } catch (e: Exception) {
                // In this case we want to let the base class take a crack
                // at it.
            }
        }
        return null
    }

    private fun parserBackdrop(view: View?, attrs: AttributeSet, context: Context){
        if(view == null)return
        val a = context.obtainStyledAttributes(attrs, R.styleable.backdrop)
        if(a.indexCount == 0)return
        val n = a.indexCount
        val shapeDrawable = GradientDrawable()

        var borderWidth = 0
        var borderColor = Color.TRANSPARENT
        for (i in 0 until n) {
            val attr = a.getIndex(i)
            when(attr) {
                R.styleable.backdrop_backgroundColor ->
                    shapeDrawable.setColor(a.getColor(attr, Color.TRANSPARENT))
                R.styleable.backdrop_radius ->
                    shapeDrawable.cornerRadius = a.getDimension(attr, 0f)
                R.styleable.backdrop_borderWidth ->
                    borderWidth  = a.getDimensionPixelSize(attr, 0)
                R.styleable.backdrop_borderColor ->
                    borderColor = a.getColor(attr, Color.TRANSPARENT)
            }
        }
        shapeDrawable.setStroke(borderWidth, borderColor)
        view.background = shapeDrawable

    }
}
package com.example.littlepainter.bottomView

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.BounceInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.DrawableCompat
import com.example.littlepainter.R
import com.google.android.material.internal.ViewUtils.dpToPx

class TabItem(context: Context, attrs: AttributeSet?) : ViewGroup(context, attrs) {

    private lateinit var mTitle: String
    private lateinit var mNormalIcon: Drawable
    private lateinit var mSelectedIcon: Drawable
    private var mNormalColor: Int = 0
    private var mSelectedColor: Int = 0

    private lateinit var mTabIcon: ImageView
    private lateinit var mTabTitle: TextView

    @SuppressLint("RestrictedApi")
    private var mIconSize = dpToPx(context, 24).toInt()//图标大小
    private var mTextSize = 14f

    @SuppressLint("RestrictedApi")
    private var mSpace = dpToPx(context, 2).toInt()

    var index = 0//当前item的编号
    var addListener: ((TabItem, Int) -> Unit)? = null


    init {
        parseAttrs(attrs)
        addChild()
        setOnClickListener {
            touchEvent()
        }
    }



    private fun touchEvent(){
        val scaleAnim = AnimationUtils.loadAnimation(context,R.anim.scale).apply {
            interpolator = BounceInterpolator()
        }
        mTabIcon.startAnimation(scaleAnim)
        mTabTitle.startAnimation(scaleAnim)

        addListener?.let { it(this,index) }

    }

    //是否被选中
    fun isSelected(isSelected:Boolean){
        if (isSelected){
            changeState(SelectState.Selected)
        }else{
            changeState(SelectState.Normal)
        }
    }

    //更改选中状态
    private fun changeState(state:SelectState ){
        val color = if(state == SelectState.Selected) mSelectedColor else mNormalColor
            mTabIcon.setImageDrawable(tintDrawable(mNormalIcon,color))
            mTabTitle.setTextColor(color)
    }

    //渲染颜色
    private fun tintDrawable(drawable: Drawable,color:Int):Drawable{
        val tintDrawable = DrawableCompat.wrap(drawable)
        tintDrawable.setTint(color)
        return tintDrawable
    }

    @SuppressLint("CustomViewStyleable", "UseCompatLoadingForDrawables")
    private fun parseAttrs(attrs: AttributeSet?) {
        context.obtainStyledAttributes(attrs, R.styleable.tabItem).apply {
            mTitle = getString(R.styleable.tabItem_title) ?: "tab"
            mNormalIcon = getDrawable(R.styleable.tabItem_icon)
                ?: resources.getDrawable(R.drawable.ic_launcher_foreground)
            mSelectedIcon = getDrawable(R.styleable.tabItem_selected_icon) ?: resources.getDrawable(
                R.drawable.ic_launcher_foreground
            )
            mNormalColor = getColor(R.styleable.tabItem_normal_color, resources.getColor(R.color.black))
            mSelectedColor = getColor(R.styleable.tabItem_selected_color, mNormalColor)

            recycle()
        }
    }

    private fun addChild() {
        //图标
        mTabIcon = ImageView(context).apply {
            setImageDrawable(mNormalIcon)
            scaleType = ImageView.ScaleType.FIT_CENTER
        }
        //标题
        mTabTitle = TextView(context).apply {
            setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize)
            setTextColor(mNormalColor)
            text = mTitle
        }
        addView(mTabIcon, mIconSize, mIconSize)
        addView(mTabTitle, LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT))//内容决定

        measureChild(mTabTitle, MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        val iconLeft = (width - mIconSize) / 2
        val iconTop = (height - mIconSize - mTabTitle.measuredHeight - mSpace) / 2
        mTabIcon.layout(iconLeft, iconTop, iconLeft + mIconSize, iconTop + mIconSize)

        val titleLeft = iconLeft + mIconSize / 2 - mTabTitle.measuredWidth / 2
        val titleTop = iconTop + mIconSize + mSpace
        Log.v("csh", "left:$titleLeft+top:$titleTop" + "meW:${mTabTitle.measuredHeight}")
        mTabTitle.layout(
            titleLeft,
            titleTop,
            titleLeft + mTabTitle.measuredWidth,
            titleTop + mTabTitle.measuredHeight
        )
    }

}

enum class SelectState{
    Normal,Selected
}
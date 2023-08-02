package com.example.littlepainter.bottomView

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children
import androidx.navigation.NavController
import com.example.littlepainter.R
import com.google.android.material.internal.ViewUtils.dpToPx
import java.lang.ref.WeakReference

/**
 * View:
 *  onMeasure -> 测量自己的宽高（有父容器或者自己决定）
 *  onDraw
 *
 * ViewGroup
 *  onMeasure -> 有父容器 自己 子控件决定
 *  onLayout -> 布局每一个子控件
 */

class BottomView(context: Context, attrs: AttributeSet) : ViewGroup(context, attrs) {

    private var mSize = 0

    private var childList: List<TabItem> = children.toList() as List<TabItem>
    private var lastSelectedIndex = 0//上一个（肯定有一个被选中，不可能为空）
    private var navController:WeakReference<NavController>? = null

    private fun selectTabItem(index: Int) {
        if (index == lastSelectedIndex) return
        childList[lastSelectedIndex].isSelected(false)
        childList[index].isSelected(true)
        lastSelectedIndex = index
    }

    @SuppressLint("RestrictedApi")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val mWidth = MeasureSpec.getSize(widthMeasureSpec)
        val mHeight = dpToPx(context, 64)
        setMeasuredDimension(mWidth, mHeight.toInt())
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mSize = w / 4

        childList = children.toList() as List<TabItem>
        //默认选中主页
        childList[lastSelectedIndex].isSelected(true)
        //给每个tabItem编号
        childList.forEachIndexed { index, tabItem ->
            tabItem.index = index
            tabItem.addListener = { tabItem, i ->
                selectTabItem(i)
                navController?.get()?.popBackStack()
                navController?.get()?.navigate(tabItem.id)
            }
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i in 0 until childCount) {
            //获取对应的子控件
            val mChild = getChildAt(i)
            //布局子空间
            val left = i * mSize
            mChild.layout(left, 0, left + mSize, height)//这里的参数是相对于底部的View的
        }
    }

    //配置NavController
    fun setNavController(navController: NavController){
        this.navController = WeakReference(navController)
    }
}
package com.shakespace.advanceandroid.chapter03view

import android.animation.*
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shakespace.advanceandroid.R
import com.shakespace.advanceandroid.global.TAG
import kotlinx.android.synthetic.main.fragment_animator.*


class AnimatorFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_animator, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        /**
         * 使用方式：
         *  1. ObjectAnimator.ofFloat、ofInt、ofArgb、ofPropertyValuesHolder.... 创建动画
         *  2. 属性值必须要有set、get方法
         *  3. 属性值类型要和of方法对应
         *  4. 通过.start()开始动画
         *  5. 一个属性如果没有get、set方法， 可以通过包装类来实现
         *
         * 通过AnimatorInflater.inflate 来加载xml动画
         * 通过animator.setTarget(iv_anim) 执行执行的view
         *
         * 补间动画是通过 AnimationUtils.loadAnimation() 来加载xml
         */


        val alphaAnimator = ObjectAnimator.ofFloat(iv_anim, "alpha", 0.5f)
        val rotationAnimator = ObjectAnimator.ofFloat(iv_anim, "rotation", 0f, 180f, 270f, 90f)
        val transAnimator = ObjectAnimator.ofFloat(iv_anim, "translationX", 500f, -200f, 100f, 0f)
        val scaleAnimator = ObjectAnimator.ofFloat(iv_anim, "scaleY", 0.2f, 2f)

        // 一个或多个， 多个只能一起执行
        val propertyValuesHolderAnimator = ObjectAnimator.ofPropertyValuesHolder(
            iv_anim,
            PropertyValuesHolder.ofFloat("translationX", 0f, 200f),
            PropertyValuesHolder.ofFloat("rotation", 0f, 270f)
        ).setDuration(1000)


        rotationAnimator.setEvaluator(MyFloatEvaluator())
//        rotationAnimator.interpolator = MyLinearInterpolator()
        rotationAnimator.interpolator = MyDecelerateInterpolator()

        tv_object_alpha.setOnClickListener {
            alphaAnimator.duration = 2000
            alphaAnimator.start()
        }
        tv_object_rotate.setOnClickListener {
            rotationAnimator.duration = 1600
            rotationAnimator.start()
        }

        tv_object_scale.setOnClickListener {
            scaleAnimator.duration = 2000
            scaleAnimator.start()
        }

        tv_object_trans.setOnClickListener {
            transAnimator.duration = 2000
            transAnimator.start()
        }

        tv_object_set.setOnClickListener {
            val animSet = AnimatorSet()
            animSet.play(alphaAnimator).with(transAnimator).with(scaleAnimator)
                .after(rotationAnimator)
            animSet.duration = 2000
            animSet.start()
        }

        tv_property.setOnClickListener {
            propertyValuesHolderAnimator.start()
        }

        // 监听
        alphaAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
                Log.e(this.TAG, "onAnimationRepeat:  ")
            }

            override fun onAnimationEnd(animation: Animator?) {
                Log.e(this.TAG, "onAnimationEnd:  ")
            }

            override fun onAnimationCancel(animation: Animator?) {
                Log.e(this.TAG, "onAnimationCancel:  ")
            }

            override fun onAnimationStart(animation: Animator?) {
                Log.e(this.TAG, "onAnimationStart:  ")
            }
        })

        // AnimatorListenerAdapter 可以单独重写某一个方法
        transAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                Log.e(this.TAG, "onAnimationEnd:  ")
            }
        })

        rotationAnimator.addUpdateListener {
            // 屏幕每刷新一次，调用一次，通常是16ms 此时可以获得当前的属性值
            Log.e(this.TAG, "onUpdate: ${it.animatedValue} ")
        }

        tv_object_xml.setOnClickListener {
            val animator = AnimatorInflater.loadAnimator(requireContext(), R.animator.demo)
            animator.setTarget(iv_anim)
            animator.start()
        }

        iv_anim.setOnClickListener {
            Log.e(this.TAG, "onClick:  ")
        }
    }

}
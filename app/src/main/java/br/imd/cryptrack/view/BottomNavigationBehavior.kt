package br.imd.cryptrack.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * https://www.androidhive.info/2017/12/android-working-with-bottom-navigation/
 */

class BottomNavigationBehavior: CoordinatorLayout.Behavior<BottomNavigationView> {

    constructor(): super()

    constructor(context: Context, attributes: AttributeSet): super(context, attributes)

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: BottomNavigationView,
        dependency: View
    ): Boolean {
        return dependency is FrameLayout
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: BottomNavigationView,
        directTargetChild: View,
        target: View,
        axes: Int
    ): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: BottomNavigationView,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray
    ) {
        if (dy < 0) {
            showBottomNavigation(child)
        } else if (dy > 0) {
            hideBottomNavigation(child)
        }
    }

    fun showBottomNavigation(view: BottomNavigationView) {
        view.animate().translationY(0.toFloat())
    }

    fun hideBottomNavigation(view: BottomNavigationView) {
        view.animate().translationY(view.height.toFloat())
    }
}
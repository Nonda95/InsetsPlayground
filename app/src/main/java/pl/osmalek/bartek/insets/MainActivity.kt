package pl.osmalek.bartek.insets

import android.os.Bundle
import android.view.View.*
import android.view.WindowInsets
import android.view.WindowInsetsController.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import pl.osmalek.bartek.insets.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        } else {
            window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            ViewCompat.setOnApplyWindowInsetsListener(stablePadding) { view, insets ->
                view.updatePadding(
                    top = insets.stableInsetTop,
                    left = insets.stableInsetLeft,
                    right = insets.stableInsetRight,
                    bottom = insets.stableInsetBottom
                )
                insets
            }
            ViewCompat.setOnApplyWindowInsetsListener(gesturePadding) { view, insets ->
                view.updatePadding(
                    top = insets.systemGestureInsets.top,
                    left = insets.systemGestureInsets.left,
                    right = insets.systemGestureInsets.right,
                    bottom = insets.systemGestureInsets.bottom
                )
                insets
            }
            ViewCompat.setOnApplyWindowInsetsListener(tappablePadding) { view, insets ->
                view.updatePadding(
                    top = insets.tappableElementInsets.top,
                    left = insets.tappableElementInsets.left,
                    right = insets.tappableElementInsets.right,
                    bottom = insets.tappableElementInsets.bottom
                )
                insets
            }
            ViewCompat.setOnApplyWindowInsetsListener(systemPadding) { view, insets ->
                view.updatePadding(
                    top = insets.systemWindowInsetTop,
                    left = insets.systemWindowInsetLeft,
                    right = insets.systemWindowInsetRight,
                    bottom = insets.systemWindowInsetBottom
                )
                insets
            }
            toggleSystemUi.setOnClickListener {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                    window.insetsController?.run {
                        when (systemBarsBehavior) {
                            BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE -> {
                                systemBarsBehavior = BEHAVIOR_SHOW_BARS_BY_TOUCH
                                show(WindowInsets.Type.systemBars())
                            }
                            BEHAVIOR_SHOW_BARS_BY_SWIPE -> {
                                systemBarsBehavior = BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                                hide(WindowInsets.Type.systemBars())
                            }
                            BEHAVIOR_SHOW_BARS_BY_TOUCH -> {
                                systemBarsBehavior = BEHAVIOR_SHOW_BARS_BY_SWIPE
                                hide(WindowInsets.Type.systemBars())
                            }
                        }
                    }
                } else with(window.decorView) {
                    systemUiVisibility = when {
                        systemUiVisibility and SYSTEM_UI_FLAG_IMMERSIVE_STICKY == SYSTEM_UI_FLAG_IMMERSIVE_STICKY -> systemUiVisibility xor SYSTEM_UI_FLAG_IMMERSIVE_STICKY xor SYSTEM_UI_FLAG_FULLSCREEN xor SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        systemUiVisibility and SYSTEM_UI_FLAG_IMMERSIVE == SYSTEM_UI_FLAG_IMMERSIVE -> (systemUiVisibility xor SYSTEM_UI_FLAG_IMMERSIVE) or SYSTEM_UI_FLAG_IMMERSIVE_STICKY or SYSTEM_UI_FLAG_FULLSCREEN or SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        else -> systemUiVisibility or SYSTEM_UI_FLAG_IMMERSIVE or SYSTEM_UI_FLAG_FULLSCREEN or SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    }
                }
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { view, insets ->
            ViewCompat.onApplyWindowInsets(view, insets)
            insets
        }
    }
}

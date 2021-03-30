package pl.osmalek.bartek.insets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import androidx.core.view.updatePaddingRelative
import pl.osmalek.bartek.insets.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
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
        }
    }
}

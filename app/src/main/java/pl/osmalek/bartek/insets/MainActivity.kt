package pl.osmalek.bartek.insets

import android.os.Bundle
import android.view.View.*
import android.view.WindowInsets
import android.view.WindowInsetsController.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import pl.osmalek.bartek.insets.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            ViewCompat.setOnApplyWindowInsetsListener(stablePadding) { view, insets ->
                val stableInsets = insets.getInsetsIgnoringVisibility(WindowInsetsCompat.Type.systemBars())
                view.updatePadding(
                    top = stableInsets.top,
                    left = stableInsets.left,
                    right = stableInsets.right,
                    bottom = stableInsets.bottom
                )
                insets
            }
            ViewCompat.setOnApplyWindowInsetsListener(gesturePadding) { view, insets ->
                val gesturesInsets = insets.getInsets(WindowInsetsCompat.Type.systemGestures())
                view.updatePadding(
                    top = gesturesInsets.top,
                    left = gesturesInsets.left,
                    right = gesturesInsets.right,
                    bottom = gesturesInsets.bottom
                )
                insets
            }
            ViewCompat.setOnApplyWindowInsetsListener(tappablePadding) { view, insets ->
                val tappableInsets = insets.getInsets(WindowInsetsCompat.Type.tappableElement())
                view.updatePadding(
                    top = tappableInsets.top,
                    left = tappableInsets.left,
                    right = tappableInsets.right,
                    bottom = tappableInsets.bottom
                )
                insets
            }
            ViewCompat.setOnApplyWindowInsetsListener(systemPadding) { view, insets ->
                val imeInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars() + WindowInsetsCompat.Type.ime())
                view.updatePadding(
                    top = imeInsets.top,
                    left = imeInsets.left,
                    right = imeInsets.right,
                    bottom = imeInsets.bottom
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
    }
}

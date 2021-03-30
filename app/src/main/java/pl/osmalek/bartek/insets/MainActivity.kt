package pl.osmalek.bartek.insets

import android.os.Bundle
import android.view.View.*
import android.view.WindowInsets
import android.view.WindowInsetsController.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import dev.chrisbanes.insetter.applyInsetter
import pl.osmalek.bartek.insets.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            stablePadding.applyInsetter {
                // no ignore visibility available
                type(navigationBars = true) {
                    padding()
                }
                type(statusBars = true) {
                    margin()
                }
            }
            gesturePadding.applyInsetter {
                type(WindowInsetsCompat.Type.systemGestures()) {
                    padding()
                }
            }
            tappablePadding.applyInsetter {
                type(tappableElement = true) {
                    padding()
                }
            }
            systemPadding.applyInsetter {
                type(navigationBars = true, statusBars = true, ime = true) {
                    padding()
                }
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

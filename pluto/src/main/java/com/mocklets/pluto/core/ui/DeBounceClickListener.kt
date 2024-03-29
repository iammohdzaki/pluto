package com.mocklets.pluto.core.ui

import android.view.HapticFeedbackConstants
import android.view.SoundEffectConstants
import android.view.View
import com.mocklets.pluto.core.ui.ClickUtils.Companion.DEBOUNCE_DELAY
import com.mocklets.pluto.core.ui.ClickUtils.Companion.enabled

internal fun View.setDebounceClickListener(
    delay: Long = DEBOUNCE_DELAY,
    haptic: Boolean = false,
    action: ((View) -> Unit)?
) {
    action?.let { action ->
        setOnClickListener { view ->
            view.onDebounce(delay) {
                view.hapticFeedback(haptic)
                view.soundFeedback()
                action.invoke(view)
            }
        }
        return
    }
    setOnClickListener(null)
}

private inline fun View.onDebounce(delay: Long, next: () -> Unit?) {
    if (enabled) {
        enabled = false
        postDelayed({ enabled = true }, delay)
        next()
    }
}

internal fun View.hapticFeedback(isGlobal: Boolean) {
    isHapticFeedbackEnabled = true
    performHapticFeedback(
        HapticFeedbackConstants.VIRTUAL_KEY,
        if (isGlobal) HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING else HapticFeedbackConstants.FLAG_IGNORE_VIEW_SETTING
    )
}

internal fun View.soundFeedback() {
    isSoundEffectsEnabled = true
    playSoundEffect(SoundEffectConstants.CLICK)
}

internal class ClickUtils private constructor() {
    companion object {
        var enabled: Boolean = true
        const val DEBOUNCE_DELAY = 250L
    }
}

package com.exteragram.messenger.utils.system;

import android.os.VibrationEffect;
import android.view.View;

public abstract class VibratorUtils {

    public static void disableHapticFeedback(View view) {
        app.nebulagram.messenger.utils.system.VibratorUtils.disableHapticFeedback(view);
    }

    public static int getType(int type) {
        return app.nebulagram.messenger.utils.system.VibratorUtils.getType(type);
    }

    public static void vibrate(long duration) {
        app.nebulagram.messenger.utils.system.VibratorUtils.vibrate(duration);
    }

    public static void vibrateEffect(VibrationEffect vibrationEffect) {
        app.nebulagram.messenger.utils.system.VibratorUtils.vibrateEffect(vibrationEffect);
    }

    public static void vibrate() {
        app.nebulagram.messenger.utils.system.VibratorUtils.vibrate();
    }
}

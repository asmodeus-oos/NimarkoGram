package com.exteragram.messenger.utils.system;

import android.app.Activity;

public abstract class SystemUtils {

    public static boolean isAppInstalled(String packageName) {
        return app.nebulagram.messenger.utils.system.SystemUtils.isAppInstalled(packageName);
    }

    public static boolean isPermissionGranted(String permission) {
        return app.nebulagram.messenger.utils.system.SystemUtils.isPermissionGranted(permission);
    }

    public static boolean isStoragePermissionGranted() {
        return app.nebulagram.messenger.utils.system.SystemUtils.isStoragePermissionGranted();
    }

    public static boolean isImagesPermissionGranted() {
        return app.nebulagram.messenger.utils.system.SystemUtils.isImagesPermissionGranted();
    }

    public static boolean isVideoPermissionGranted() {
        return app.nebulagram.messenger.utils.system.SystemUtils.isVideoPermissionGranted();
    }

    public static boolean isAudioPermissionGranted() {
        return app.nebulagram.messenger.utils.system.SystemUtils.isAudioPermissionGranted();
    }

    public static boolean isImagesAndVideoPermissionGranted() {
        return app.nebulagram.messenger.utils.system.SystemUtils.isImagesAndVideoPermissionGranted();
    }

    public static boolean hasBiometrics() {
        return app.nebulagram.messenger.utils.system.SystemUtils.hasBiometrics();
    }

    public static void requestPermissions(Activity activity, int requestCode, String... permissions) {
        app.nebulagram.messenger.utils.system.SystemUtils.requestPermissions(activity, requestCode, permissions);
    }

    public static void requestStoragePermission(Activity activity) {
        app.nebulagram.messenger.utils.system.SystemUtils.requestStoragePermission(activity);
    }

    public static int getRoundAudioBitrate() {
        return app.nebulagram.messenger.utils.system.SystemUtils.getRoundAudioBitrate();
    }

    public static int getRoundVideoBitrate() {
        return app.nebulagram.messenger.utils.system.SystemUtils.getRoundVideoBitrate();
    }

    public static int getRoundVideoResolution() {
        return app.nebulagram.messenger.utils.system.SystemUtils.getRoundVideoResolution();
    }
}

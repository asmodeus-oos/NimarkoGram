package com.exteragram.messenger;

public final class ExteraConfig {

    private ExteraConfig() {}

    public static final android.content.SharedPreferences.Editor editor =
            app.nebulagram.messenger.NebulaConfig.getEditor();

    public static android.content.SharedPreferences.Editor getEditor() {
        try {
            return app.nebulagram.messenger.NebulaConfig.getEditor();
        } catch (Throwable t) {
            return editor;
        }
    }

    public static android.content.SharedPreferences getPreferences() {
        try {
            return app.nebulagram.messenger.NebulaConfig.getPreferences();
        } catch (Throwable t) {
            return null;
        }
    }

    public static volatile boolean pluginsSafeMode = app.nebulagram.messenger.NebulaConfig.pluginsSafeMode;

    public static boolean getPluginsSafeMode() {
        try { return app.nebulagram.messenger.NebulaConfig.pluginsSafeMode; } catch (Throwable t) { return false; }
    }

    public static void setPluginsSafeMode(boolean enabled) {
        pluginsSafeMode = enabled;
        try { app.nebulagram.messenger.NebulaConfig.setPluginsSafeMode(enabled); } catch (Throwable t) {   }
    }

    public static boolean getPluginsDevMode() {
        try { return app.nebulagram.messenger.NebulaConfig.pluginsDevMode; } catch (Throwable t) { return false; }
    }

    public static boolean getPluginsCompactView() {
        try { return app.nebulagram.messenger.NebulaConfig.pluginsCompactView; } catch (Throwable t) { return false; }
    }

    public static boolean getPluginsDisableArtOpts() {
        try { return app.nebulagram.messenger.NebulaConfig.pluginsDisableArtOpts; } catch (Throwable t) { return false; }
    }

    public static boolean getPluginsPySdkAutoUpdate() {
        try { return app.nebulagram.messenger.NebulaConfig.pluginsPySdkAutoUpdate; } catch (Throwable t) { return false; }
    }

    public static boolean getPluginsPySdkBetaVersions() {
        try { return app.nebulagram.messenger.NebulaConfig.pluginsPySdkBetaVersions; } catch (Throwable t) { return false; }
    }

    public static boolean getPluginsEngine() {
        try { return app.nebulagram.messenger.NebulaConfig.pluginsEngine; } catch (Throwable t) { return true; }
    }

    public static float getAvatarCorners() {
        try { return app.nebulagram.messenger.NebulaConfig.avatarCorners; } catch (Throwable t) { return 0f; }
    }

    public static int getAvatarCorners(float size) {
        try { return app.nebulagram.messenger.NebulaConfig.getAvatarCorners(size); } catch (Throwable t) { return 0; }
    }

    public static int getAvatarCorners(float size, boolean toPx) {
        try { return app.nebulagram.messenger.NebulaConfig.getAvatarCorners(size, toPx); } catch (Throwable t) { return 0; }
    }

    public static int getAvatarCorners(float size, boolean toPx, boolean forum) {
        return getAvatarCorners(size, toPx, forum, false);
    }

    public static int getAvatarCorners(float size, boolean toPx, boolean forum, boolean withStory) {
        try {
            float corners = app.nebulagram.messenger.NebulaConfig.avatarCorners;
            if (corners == 0f) {
                return 0;
            }
            float value = (corners * size) / 56.0f;
            if (withStory) {
                value -= 2.5f;
            }
            if (!toPx) {
                value = org.telegram.messenger.AndroidUtilities.dp(value);
            }
            if (forum) {
                value = (((int) value) * 42) >> 6;
            }
            return (int) java.lang.Math.ceil(value);
        } catch (Throwable t) {
            return 0;
        }
    }

    public static float getAvatarSquareness() {
        try {
            float v = 1.0f - (app.nebulagram.messenger.NebulaConfig.avatarCorners / 28.0f);
            return v < 0f ? 0f : (v > 1f ? 1f : v);
        } catch (Throwable t) {
            return 0f;
        }
    }

    public static void setAvatarCorners(float v) {
        try { app.nebulagram.messenger.NebulaConfig.setAvatarCorners(v); } catch (Throwable t) {   }
    }

    public static final com.google.gson.Gson GSON = new com.google.gson.Gson();

    public static com.google.gson.Gson getGSON() {
        return GSON;
    }

    public static boolean getDisableNumberRounding() {
        try { return app.nebulagram.messenger.NebulaConfig.noRounding; } catch (Throwable t) { return false; }
    }

    public static boolean getFormatTimeWithSeconds() {
        try { return app.nebulagram.messenger.NebulaConfig.showSeconds; } catch (Throwable t) { return false; }
    }

    public static boolean getInAppVibration() {
        try { return !app.nebulagram.messenger.NebulaConfig.disableVibration; } catch (Throwable t) { return false; }
    }

    public static int getDoubleTapSeekDuration() {
        try { return app.nebulagram.messenger.NebulaConfig.videoSeekDuration; } catch (Throwable t) { return 0; }
    }

    public static boolean getUseSystemFonts() {
        try { return app.nebulagram.messenger.NebulaConfig.systemFonts; } catch (Throwable t) { return false; }
    }

    public static boolean getHideStories() {
        try { return app.nebulagram.messenger.NebulaConfig.hideStories; } catch (Throwable t) { return false; }
    }

    public static String getTargetLang() {
        try { return app.nebulagram.messenger.NebulaConfig.translationTarget; } catch (Throwable t) { return "app"; }
    }

    public static void setTargetLang(String v) {
        try { app.nebulagram.messenger.NebulaConfig.setTranslationTarget(v); } catch (Throwable t) {   }
    }

    public static int getTranslationProvider() {
        try { return 0; } catch (Throwable t) { return 0; }
    }

    public static int getTitleText() {
        return 0;
    }

    public static boolean getRelativeLastSeen() {
        try { return false; } catch (Throwable t) { return false; }
    }

    public static boolean getFilterZalgo() {
        try { return false; } catch (Throwable t) { return false; }
    }
}

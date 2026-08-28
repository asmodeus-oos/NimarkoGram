/*
 * This file is part of NebulaGram for Android.
 * Licensed under GNU GPL v2 or later. See LICENSE.
 * Copyright Ettacent, 2026.
 */

package app.nebulagram.messenger.utils;

import android.app.Activity;

import app.nebulagram.messenger.NebulaConfig;
import app.nebulagram.messenger.security.NebulaBiometricPrompt;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.UserConfig;

public final class CGCompat {

    private CGCompat() {}

    public static void runOrAskBiometricsBeforeDelete(Activity activity, Runnable action) {
        runOrAsk(activity, UserConfig.selectedAccount, NebulaConfig.askPasscodeBeforeDelete, action);
    }

    public static void runOrAskBiometricsBeforeDelete(Activity activity, int account, Runnable action) {
        runOrAsk(activity, account, NebulaConfig.askPasscodeBeforeDelete, action);
    }

    public static void runOrAskBeforeDestructive(Activity activity, Runnable action) {
        runOrAsk(activity, UserConfig.selectedAccount, NebulaConfig.askPasscodeBeforeDelete, action);
    }

    public static void runOrAskBeforeDestructive(Activity activity, int account, Runnable action) {
        runOrAsk(activity, account, NebulaConfig.askPasscodeBeforeDelete, action);
    }

    private static void runOrAsk(Activity activity, int account, boolean wantPrompt, Runnable action) {
        if (action == null) return;
        if (!wantPrompt) {
            action.run();
            return;
        }
        if (activity == null) return;
        if (!NebulaBiometricPrompt.canAuthenticateConfigured()) return;
        try {
            NebulaBiometricPrompt.prompt(activity, account, action, null);
        } catch (Throwable t) {
            FileLog.e("Nebula destructive authentication prompt failed closed", t);
        }
    }

    public static boolean isChatLocked(long dialogId) {
        return isChatLocked(UserConfig.selectedAccount, dialogId);
    }

    public static boolean isChatLocked(int account, long dialogId) {
        if (!NebulaConfig.askBiometricsToOpenChat) return false;
        if (dialogId == 0L) return false;
        return LockedChats.isLocked(account, dialogId);
    }

    public static boolean shouldRequireBiometricsToOpenChats() {
        return NebulaConfig.askBiometricsToOpenChat;
    }

    public static boolean shouldRequireBiometricsToOpenEncryptedChats() {
        return NebulaConfig.askBiometricsToOpenEncrypted;
    }

    public static boolean shouldRequireBiometricsToOpenArchive() {
        return NebulaConfig.askBiometricsToOpenArchive;
    }

    public static void unlockChatThen(Activity activity, long dialogId, Runnable onUnlocked) {
        unlockChatThen(activity, UserConfig.selectedAccount, dialogId, onUnlocked);
    }

    public static void unlockChatThen(Activity activity, int account, long dialogId, Runnable onUnlocked) {
        if (onUnlocked == null) return;
        if (!isChatLocked(account, dialogId)
                || NebulaBiometricPrompt.isRecentlyVerified(account, 0L, dialogId, 0)) {
            onUnlocked.run();
            return;
        }
        if (activity == null) return;
        if (!NebulaBiometricPrompt.canAuthenticateConfigured()) return;
        try {
            NebulaBiometricPrompt.prompt(activity, account, () -> {
                NebulaBiometricPrompt.markVerified(account, 0L, dialogId, 0);
                onUnlocked.run();
            }, null);
        } catch (Throwable t) {
            FileLog.e("Nebula chat authentication prompt failed closed", t);
        }
    }
}

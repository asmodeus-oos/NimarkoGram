package app.nebulagram.messenger.preferences;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.view.View;

import androidx.biometric.BiometricPrompt;

import java.util.ArrayList;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.R;
import org.telegram.messenger.UserConfig;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.Components.BulletinFactory;
import org.telegram.ui.Components.IconBackgroundColors;
import org.telegram.ui.Components.UItem;
import org.telegram.ui.Components.UniversalAdapter;

import app.nebulagram.messenger.NebulaConfig;
import app.nebulagram.messenger.preferences.helpers.PopupHelper;
import app.nebulagram.messenger.preferences.helpers.SettingsHelper;
import app.nebulagram.messenger.security.NebulaBiometricPrompt;
import app.nebulagram.messenger.utils.chats.NebulaChatMenuInjector;

public class PrivacyPreferencesActivity extends BasePreferencesActivity {
    private static final int ID_HIDE_PROXY = 1;
    private static final int ID_DELETE_ACCOUNT = 2;

    private static final int ID_HIDE_ARCHIVED_STORIES = 3;
    private static final int ID_HIDE_ARCHIVE_LIST = 4;
    private static final int ID_PROTECT_SELECTED_CHATS = 5;
    private static final int ID_LOCKED_CHATS = 6;
    private static final int ID_REQUIRE_BIO_DELETE = 7;
    private static final int ID_ALLOW_SYSTEM_PASSCODE = 8;
    private static final int ID_TEST_FINGERPRINT = 9;
    private static final int ID_LOCKED_CHATS_TTL = 11;
    private static final int ID_PROTECT_SECRET_CHATS = 12;
    private static final int ID_PROTECT_ARCHIVE = 13;
    private static final int ID_OPEN_ARCHIVE = 14;
    private static final int ID_PROTECT_SAVED_MESSAGES = 15;

    @Override
    public String getTitle() {
        return LocaleController.getString(R.string.NM_Cat_Privacy);
    }

    @Override
    public void fillItems(ArrayList<UItem> items, UniversalAdapter adapter) {
        items.add(UItem.asHeader(LocaleController.getString(R.string.NM_SettingsSectionAuthentication)));
        items.add(SettingsHelper.asSwitchCG(ID_ALLOW_SYSTEM_PASSCODE,
                        LocaleController.getString(R.string.NM_PR_AllowSystemPasscode),
                        LocaleController.getString(R.string.NM_PR_AllowSystemPasscode_Desc))
                .setChecked(NebulaConfig.allowSystemPasscode));
        items.add(asSettingsLink(ID_TEST_FINGERPRINT, IconBackgroundColors.BLUE,
                R.drawable.msg_pin_code,
                LocaleController.getString(R.string.NM_PR_TestFingerprint),
                LocaleController.getString(R.string.NM_PR_TestFingerprint_Desc)));
        items.add(UItem.asShadow(null));

        items.add(UItem.asHeader(LocaleController.getString(R.string.NM_PR_Header_ChatProtection)));
        items.add(SettingsHelper.asSwitchCG(ID_PROTECT_SELECTED_CHATS,
                        LocaleController.getString(R.string.NM_PR_AskBioOpenChats),
                        LocaleController.getString(R.string.NM_PR_AskBioOpenChats_Desc))
                .setChecked(NebulaConfig.askBiometricsToOpenChat));
        items.add(SettingsHelper.asSwitchCG(ID_PROTECT_SAVED_MESSAGES,
                        LocaleController.getString(R.string.NM_PR_AskBioOpenSavedMessages),
                        LocaleController.getString(R.string.NM_PR_AskBioOpenSavedMessages_Desc))
                .setChecked(NebulaConfig.askBiometricsToOpenSavedMessages));
        if (NebulaConfig.askBiometricsToOpenChat) {
            int count = app.nebulagram.messenger.utils.LockedChats.count(currentAccount);
            items.add(asSettingsValue(ID_LOCKED_CHATS, IconBackgroundColors.GREEN,
                    R.drawable.msg_saved,
                    LocaleController.getString(R.string.NM_PR_LockedChats), String.valueOf(count)));
            items.add(asSettingsValue(ID_LOCKED_CHATS_TTL, IconBackgroundColors.ORANGE,
                    R.drawable.msg_recent,
                    LocaleController.getString(R.string.NM_PR_LockedChatsTtl), getLockedChatsTtlValueText()));
        }
        items.add(SettingsHelper.asSwitchCG(ID_PROTECT_SECRET_CHATS,
                        LocaleController.getString(R.string.NM_PR_AskBioOpenEncrypted),
                        LocaleController.getString(R.string.NM_PR_AskBioOpenEncrypted_Desc))
                .setChecked(NebulaConfig.askBiometricsToOpenEncrypted));
        items.add(SettingsHelper.asSwitchCG(ID_PROTECT_ARCHIVE,
                        LocaleController.getString(R.string.NM_PR_AskBioOpenArchive),
                        LocaleController.getString(R.string.NM_PR_AskBioOpenArchive_Desc))
                .setChecked(NebulaConfig.askBiometricsToOpenArchive));
        items.add(SettingsHelper.asSwitchCG(ID_REQUIRE_BIO_DELETE,
                        LocaleController.getString(R.string.NM_PR_RequireBiometricsToDelete),
                        LocaleController.getString(R.string.NM_PR_RequireBiometricsToDelete_Desc))
                .setChecked(NebulaConfig.askPasscodeBeforeDelete));
        items.add(UItem.asShadow(null));

        items.add(UItem.asHeader(LocaleController.getString(R.string.NM_SettingsSectionHiddenItems)));
        items.add(UItem.asCheck(ID_HIDE_PROXY, LocaleController.getString(R.string.NM_PR_HideProxy))
                .setChecked(NebulaConfig.hideProxySponsor));
        items.add(SettingsHelper.asSwitchCG(ID_HIDE_ARCHIVED_STORIES,
                        LocaleController.getString(R.string.NM_PR_HideArchivedStories),
                        LocaleController.getString(R.string.NM_PR_HideArchivedStories_Desc))
                .setChecked(NebulaConfig.hideArchivedStories));
        items.add(SettingsHelper.asSwitchCG(ID_HIDE_ARCHIVE_LIST,
                        LocaleController.getString(R.string.NM_PR_HideArchiveList),
                        LocaleController.getString(R.string.NM_PR_HideArchiveList_Desc))
                .setChecked(NebulaConfig.hideArchiveFromChatsList));
        if (NebulaConfig.hideArchiveFromChatsList) {
            items.add(asSettingsLink(ID_OPEN_ARCHIVE, IconBackgroundColors.BLUE_DEEP,
                    R.drawable.msg_archive,
                    LocaleController.getString(R.string.NM_PR_OpenArchive)));
        }
        items.add(UItem.asShadow(null));

        items.add(UItem.asHeader(LocaleController.getString(R.string.NM_SettingsSectionAccount)));
        items.add(asSettingsLink(ID_DELETE_ACCOUNT, IconBackgroundColors.RED,
                R.drawable.msg_user_remove,
                LocaleController.getString(R.string.NM_PR_DeleteAccount)).red());
        items.add(UItem.asShadow(null));
    }

    @Override
    public void onClick(UItem item, View view, int position, float x, float y) {
        int id = item.id;
        if (id == ID_HIDE_PROXY) {
            NebulaConfig.toggleHideProxySponsor();
            applyCheck(item, view, NebulaConfig.hideProxySponsor);
            for (int account = 0; account < UserConfig.MAX_ACCOUNT_COUNT; account++) {
                if (UserConfig.getInstance(account).isClientActivated()) {
                    org.telegram.messenger.MessagesController.getInstance(account).checkPromoInfo(true);
                }
            }
        } else if (id == ID_DELETE_ACCOUNT) {
            if (NebulaConfig.askPasscodeBeforeDelete) {
                runAfterAuthentication(() -> DeleteAccountDialog.showDeleteAccountDialog(this));
            } else {
                DeleteAccountDialog.showDeleteAccountDialog(this);
            }
        } else if (id == ID_HIDE_ARCHIVED_STORIES) {
            NebulaConfig.toggleHideArchivedStories();
            applyCheck(item, view, NebulaConfig.hideArchivedStories);
            showRestartBulletin();
        } else if (id == ID_HIDE_ARCHIVE_LIST) {
            NebulaConfig.toggleHideArchiveFromChatsList();
            applyCheck(item, view, NebulaConfig.hideArchiveFromChatsList);
            for (int account = 0; account < UserConfig.MAX_ACCOUNT_COUNT; account++) {
                if (UserConfig.getInstance(account).isClientActivated()) {
                    NotificationCenter.getInstance(account).postNotificationName(NotificationCenter.dialogsNeedReload);
                }
            }
            refreshItems();
        } else if (id == ID_OPEN_ARCHIVE) {
            NebulaChatMenuInjector.openArchivedChats(this);
        } else if (id == ID_PROTECT_SELECTED_CHATS) {
            changeProtectedSetting(NebulaConfig.askBiometricsToOpenChat, () -> {
                NebulaConfig.toggleAskBiometricsToOpenChat();
                applyCheck(item, view, NebulaConfig.askBiometricsToOpenChat);
                refreshItems();
            });
        } else if (id == ID_PROTECT_SAVED_MESSAGES) {
            changeProtectedSetting(NebulaConfig.askBiometricsToOpenSavedMessages, () -> {
                NebulaConfig.toggleAskBiometricsToOpenSavedMessages();
                applyCheck(item, view, NebulaConfig.askBiometricsToOpenSavedMessages);
            });
        } else if (id == ID_PROTECT_SECRET_CHATS) {
            changeProtectedSetting(NebulaConfig.askBiometricsToOpenEncrypted, () -> {
                NebulaConfig.toggleAskBiometricsToOpenEncrypted();
                applyCheck(item, view, NebulaConfig.askBiometricsToOpenEncrypted);
            });
        } else if (id == ID_PROTECT_ARCHIVE) {
            changeProtectedSetting(NebulaConfig.askBiometricsToOpenArchive, () -> {
                NebulaConfig.toggleAskBiometricsToOpenArchive();
                applyCheck(item, view, NebulaConfig.askBiometricsToOpenArchive);
            });
        } else if (id == ID_LOCKED_CHATS) {
            runAfterAuthentication(() -> presentFragment(new LockedChatsPreferencesActivity()));
        } else if (id == ID_LOCKED_CHATS_TTL) {
            runAfterAuthentication(() -> showLockedChatsTtlPicker(view));
        } else if (id == ID_REQUIRE_BIO_DELETE) {
            changeProtectedSetting(NebulaConfig.askPasscodeBeforeDelete, () -> {
                NebulaConfig.toggleAskPasscodeBeforeDelete();
                applyCheck(item, view, NebulaConfig.askPasscodeBeforeDelete);
            });
        } else if (id == ID_ALLOW_SYSTEM_PASSCODE) {
            Runnable toggle = () -> {
                NebulaConfig.toggleAllowSystemPasscode();
                applyCheck(item, view, NebulaConfig.allowSystemPasscode);
            };
            if (NebulaConfig.allowSystemPasscode && !NebulaBiometricPrompt.canAuthenticate(true)) {
                confirmProtectionReset(toggle);
            } else {
                runAfterAuthentication(true, toggle);
            }
        } else if (id == ID_TEST_FINGERPRINT) {
            testFingerprint();
        }
    }

    private void runAfterAuthentication(Runnable action) {
        runAfterAuthentication(NebulaConfig.allowSystemPasscode, action);
    }

    private void runAfterAuthentication(boolean allowSystem, Runnable action) {
        if (getParentActivity() == null) {
            showAuthenticationRequired();
            return;
        }
        if (!NebulaBiometricPrompt.canAuthenticate(allowSystem)) {
            showAuthenticationRequired();
            return;
        }
        NebulaBiometricPrompt.prompt(getParentActivity(), currentAccount, allowSystem,
                action, this::showAuthenticationRequired);
    }

    private void changeProtectedSetting(boolean currentlyEnabled, Runnable action) {
        boolean allowSystem = NebulaConfig.allowSystemPasscode;
        if (currentlyEnabled && !NebulaBiometricPrompt.canAuthenticate(allowSystem)
                && NebulaBiometricPrompt.canAuthenticate(true)) {
            allowSystem = true;
        }
        if (currentlyEnabled && !NebulaBiometricPrompt.canAuthenticate(allowSystem)) {
            confirmProtectionReset(action);
            return;
        }
        runAfterAuthentication(allowSystem, action);
    }

    private void confirmProtectionReset(Runnable action) {
        if (getParentActivity() == null) {
            showAuthenticationRequired();
            return;
        }
        new AlertDialog.Builder(getParentActivity())
                .setTitle(LocaleController.getString(R.string.NM_PR_AuthenticationUnavailable))
                .setMessage(LocaleController.getString(R.string.NM_PR_AuthenticationUnavailable_Desc))
                .setPositiveButton(LocaleController.getString(R.string.Disable), (dialog, which) -> action.run())
                .setNegativeButton(LocaleController.getString(R.string.Cancel), null)
                .show();
    }

    private void refreshItems() {
        if (listView != null && listView.adapter != null) {
            listView.adapter.update(true);
        }
    }

    private void showAuthenticationRequired() {
        BulletinFactory.of(this).createErrorBulletin(
                LocaleController.getString(R.string.NM_PR_AuthenticationRequired)
        ).show();
    }

    private void testFingerprint() {
        if (getParentActivity() == null) return;
        NebulaBiometricPrompt.fixFingerprint(getParentActivity(), new NebulaBiometricPrompt.NebulaBiometricListener() {
            @Override
            public void onSuccess(BiometricPrompt.AuthenticationResult result) {
                NebulaBiometricPrompt.cancelPendingAuthentications();
                if (listView != null && listView.adapter != null) listView.adapter.update(true);
                AndroidUtilities.runOnUIThread(() ->
                        BulletinFactory.of(PrivacyPreferencesActivity.this).createSimpleBulletin(
                                R.raw.chats_infotip,
                                LocaleController.getString(R.string.NM_PR_TestFingerprint)
                        ).show(), 300);
            }

            @Override
            public void onFailed() {
            }

            @Override
            public void onError(int error, CharSequence msg) {
                showError(error);
            }

            private void showError(int error) {
                BulletinFactory.of(PrivacyPreferencesActivity.this).createSimpleBulletin(
                        R.raw.chats_infotip,
                        LocaleController.getString(R.string.NM_PR_TestFingerprint_Desc),
                        LocaleController.getString(R.string.Settings),
                        () -> openFingerprintSettings(getContext())
                ).show();
            }
        });
    }

    private static void openFingerprintSettings(Context context) {
        if (context == null) return;
        Intent fallbackIntent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                Intent fingerprintIntent = new Intent(Settings.ACTION_FINGERPRINT_ENROLL);
                fingerprintIntent.setPackage("com.android.settings");
                if (fingerprintIntent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(fingerprintIntent);
                    return;
                }
            }
            context.startActivity(fallbackIntent);
        } catch (SecurityException e) {
            FileLog.e(e);
            try { context.startActivity(fallbackIntent); } catch (Throwable ignored) {}
        } catch (Throwable e) {
            FileLog.e(e);
        }
    }

    private void applyCheck(UItem item, View view, boolean value) {
        item.checked = value;
        updateCheckState(view, value);
    }

    private String getLockedChatsTtlValueText() {
        int s = NebulaConfig.lockedChatsBiometricTtlSec;
        if (s == NebulaConfig.LOCKED_CHATS_TTL_ALWAYS) {
            return LocaleController.getString(R.string.NM_PR_LockedChatsTtl_Always);
        }
        if (s == NebulaConfig.LOCKED_CHATS_TTL_UNTIL_RESTART) {
            return LocaleController.getString(R.string.NM_PR_LockedChatsTtl_UntilRestart);
        }
        if (s == NebulaConfig.LOCKED_CHATS_TTL_1_MIN) {
            return LocaleController.getString(R.string.NM_PR_LockedChatsTtl_1Min);
        }
        if (s == NebulaConfig.LOCKED_CHATS_TTL_15_MIN) {
            return LocaleController.getString(R.string.NM_PR_LockedChatsTtl_15Min);
        }
        return LocaleController.getString(R.string.NM_PR_LockedChatsTtl_5Min);
    }

    private void showLockedChatsTtlPicker(View anchor) {
        ArrayList<CharSequence> labels = new ArrayList<>();
        ArrayList<Integer> values = new ArrayList<>();
        labels.add(LocaleController.getString(R.string.NM_PR_LockedChatsTtl_Always));
        values.add(NebulaConfig.LOCKED_CHATS_TTL_ALWAYS);
        labels.add(LocaleController.getString(R.string.NM_PR_LockedChatsTtl_1Min));
        values.add(NebulaConfig.LOCKED_CHATS_TTL_1_MIN);
        labels.add(LocaleController.getString(R.string.NM_PR_LockedChatsTtl_5Min));
        values.add(NebulaConfig.LOCKED_CHATS_TTL_5_MIN);
        labels.add(LocaleController.getString(R.string.NM_PR_LockedChatsTtl_15Min));
        values.add(NebulaConfig.LOCKED_CHATS_TTL_15_MIN);
        labels.add(LocaleController.getString(R.string.NM_PR_LockedChatsTtl_UntilRestart));
        values.add(NebulaConfig.LOCKED_CHATS_TTL_UNTIL_RESTART);
        int current = values.indexOf(NebulaConfig.lockedChatsBiometricTtlSec);
        if (current < 0) current = values.indexOf(NebulaConfig.LOCKED_CHATS_TTL_5_MIN);
        PopupHelper.show(labels,
                LocaleController.getString(R.string.NM_PR_LockedChatsTtl),
                current,
                getContext(),
                i -> {
                    NebulaConfig.setLockedChatsBiometricTtl(values.get(i));
                    if (listView != null && listView.adapter != null) {
                        listView.adapter.update(true);
                    }
                });
    }
}

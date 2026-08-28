/*
 * Copyright github.com/arsLan4k1390, 2022-2026.
 * Licensed under GNU GPL v2 or later. See LICENSE.
 */

package app.nebulagram.messenger.utils;

import android.net.Uri;

import org.telegram.messenger.browser.Browser;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.messenger.R;
import static org.telegram.messenger.LocaleController.getString;
import org.telegram.ui.Components.Premium.LimitReachedBottomSheet;
import org.telegram.ui.LaunchActivity;
import org.telegram.ui.Stars.StarsIntroActivity;

import java.util.Locale;

import app.nebulagram.messenger.preferences.AppearancePreferencesActivity;
import app.nebulagram.messenger.preferences.BottomTabsPreferencesActivity;
import app.nebulagram.messenger.preferences.CameraPreferencesActivity;
import app.nebulagram.messenger.preferences.ChatsPreferencesActivity;
import app.nebulagram.messenger.preferences.DebugPreferencesActivity;
import app.nebulagram.messenger.preferences.ExperimentalPreferencesActivity;
import app.nebulagram.messenger.preferences.FoldersPreferencesActivity;
import app.nebulagram.messenger.preferences.GeneralPreferencesActivity;
import app.nebulagram.messenger.preferences.MainPreferencesActivity;
import app.nebulagram.messenger.preferences.MessageFiltersPreferencesActivity;
import app.nebulagram.messenger.preferences.MessageMenuPreferencesActivity;
import app.nebulagram.messenger.preferences.MessagesAndProfilesPreferencesActivity;
import app.nebulagram.messenger.preferences.PrivacyPreferencesActivity;

public class NebulaDeeplinkHelper {

    public static void processDeepLink(Uri uri, BaseFragment fragment, Callback callback, Runnable unknown, Browser.Progress progress) {
        if (fragment == null) {
            fragment = LaunchActivity.getSafeLastFragment();
        }
        if (fragment == null) {
            return;
        }
        if (uri == null) {
            unknown.run();
            return;
        }
        var segments = uri.getPathSegments();
        if (segments.isEmpty() || segments.size() > 2) {
            unknown.run();
            return;
        }

        if (segments.size() == 1) {
            var segment = segments.get(0).toLowerCase(Locale.US);
            BaseFragment target = null;
            switch (segment) {
                case DeepLinksRepo.NG_Settings:
                case "nebula_main":
                    target = new MainPreferencesActivity();
                    break;
                case DeepLinksRepo.NG_General:
                    target = new GeneralPreferencesActivity();
                    break;
                case DeepLinksRepo.NG_Appearance:
                    target = new AppearancePreferencesActivity();
                    break;
                case DeepLinksRepo.NG_Tabs:
                    target = new BottomTabsPreferencesActivity();
                    break;
                case DeepLinksRepo.NG_Folders:
                    target = new FoldersPreferencesActivity();
                    break;
                case DeepLinksRepo.NG_Messages_And_Profiles:
                    target = new MessagesAndProfilesPreferencesActivity();
                    break;
                case DeepLinksRepo.NG_Chats:
                    target = new ChatsPreferencesActivity();
                    break;
                case DeepLinksRepo.NG_Messages:
                    target = new ChatsPreferencesActivity();
                    break;
                case DeepLinksRepo.NG_Message_Menu:
                case "nebula_messages_menu":
                    target = new MessageMenuPreferencesActivity();
                    break;
                case DeepLinksRepo.NG_Message_Filters:
                case "nebula_filter":
                    target = new MessageFiltersPreferencesActivity();
                    break;
                case DeepLinksRepo.NG_Camera:
                    target = new CameraPreferencesActivity();
                    break;
                case DeepLinksRepo.NG_Privacy:
                case "nebula_security":
                    target = new PrivacyPreferencesActivity();
                    break;
                case DeepLinksRepo.NG_Experimental:
                    target = new ExperimentalPreferencesActivity();
                    break;
                case DeepLinksRepo.NG_Debug:
                    target = new DebugPreferencesActivity();
                    break;
                case DeepLinksRepo.NG_Stars:
                    new StarsIntroActivity.StarsOptionsSheet(
                            fragment.getContext(),
                            fragment.getCurrentAccount(),
                            fragment.getResourceProvider()
                    ).show();
                    return;
                case DeepLinksRepo.NG_Username_Limits:
                    fragment.showDialog(new LimitReachedBottomSheet(
                            fragment,
                            fragment.getContext(),
                            LimitReachedBottomSheet.TYPE_PUBLIC_LINKS,
                            fragment.getCurrentAccount(),
                            fragment.getResourceProvider()
                    ));
                    return;
                case DeepLinksRepo.NG_Restart:
                case "nebula_reboot":
                case "restart":
                case "reboot":
                    if (fragment.getParentActivity() == null) return;
                    AlertDialog.Builder restart = new AlertDialog.Builder(fragment.getParentActivity());
                    restart.setTitle(getString(R.string.NM_HUB_Restart));
                    restart.setMessage(getString(R.string.NM_RestartRequired));
                    BaseFragment restartFragment = fragment;
                    restart.setPositiveButton(getString(R.string.NM_Restart), (d, w) ->
                            AppRestartHelper.triggerRebirth(restartFragment.getContext()));
                    restart.setNegativeButton(getString(R.string.Cancel), null);
                    fragment.showDialog(restart.create());
                    return;
                default:
                    unknown.run();
                    return;
            }
            if (target != null) {
                final BaseFragment finalTarget = target;
                callback.presentFragment(finalTarget);
                return;
            }
        }
        callback.presentFragment(fragment);
    }

    public interface Callback {
        void presentFragment(BaseFragment fragment);
    }

    public static class DeepLinksRepo {

        public static final String NG_Settings = "nebula_settings";

        public static final String NG_General = "nebula_general";

        public static final String NG_Appearance = "nebula_appearance";
        public static final String NG_Folders = "nebula_folders";
        public static final String NG_Luck = "nebula_luck";
        public static final String NG_Tabs = "nebula_tabs";
        public static final String NG_Messages_And_Profiles = "nebula_messages_profiles";

        public static final String NG_Chats = "nebula_chats";
        public static final String NG_Messages = "nebula_messages";
        public static final String NG_Message_Menu = "nebula_message_menu";
        public static final String NG_Message_Filters = "nebula_filters";

        public static final String NG_Camera = "nebula_camera";

        public static final String NG_Experimental = "nebula_experimental";

        public static final String NG_Privacy = "nebula_privacy";

        public static final String NG_Restart = "nebula_restart";

        public static final String NG_Debug = "nebula_debug";

        public static final String NG_Stars = "nebula_stars";
        public static final String NG_Username_Limits = "nebula_username_limits";

        private DeepLinksRepo() {}
    }

}

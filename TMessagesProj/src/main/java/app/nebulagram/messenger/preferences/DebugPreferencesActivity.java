/**
 * This is the source code of Cherrygram for Android.
 * It is licensed under GNU GPL v. 2 or later.
 * You should have received a copy of the license in this archive (see LICENSE).
 * Please, be respectful and credit the original author if you use this code.
 *
 * Copyright github.com/arsLan4k1390, 2022-2026.
 *
 * Ported to NebulaGram (DrKLO base) from CherrygramDebugPreferencesEntry.
 * Strip: Firebase analytics, CG-server, SettingsHelper.asSwitchCG; replace
 * with DrKLO UItem.asCheck + NebulaConfig flags.
 */
package app.nebulagram.messenger.preferences;

import android.os.Build;
import android.view.View;

import java.util.ArrayList;

import org.telegram.messenger.BuildVars;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;
import org.telegram.messenger.SharedConfig;
import org.telegram.ui.Components.IconBackgroundColors;
import org.telegram.ui.Components.UItem;
import org.telegram.ui.Components.UniversalAdapter;

import app.nebulagram.messenger.NebulaConfig;
import app.nebulagram.messenger.preferences.helpers.PopupHelper;
import app.nebulagram.messenger.preferences.helpers.SettingsHelper;

public class DebugPreferencesActivity extends BasePreferencesActivity {
    private static final int ID_SHOW_RPC_ERRORS    = 1;
    private static final int ID_AUDIO_SOURCE       = 2;
    private static final int ID_JACKSON_JSON       = 3;
    private static final int ID_HIDE_VIDEO_TS      = 5;
    private static final int ID_OLD_TIME_STYLE     = 6;
    private static final int ID_REPLACE_PUNCT      = 7;
    private static final int ID_EDIT_TEXT_FIX      = 8;
    private static final int ID_SHOW_ACCOUNTS      = 9;
    private static final int ID_SEND_MAX_QUALITY   = 11;
    private static final int ID_FORCE_CRASH        = 10;

    @Override
    public String getTitle() {
        return LocaleController.getString(R.string.NM_SettingsAdvanced);
    }

    @Override
    public void fillItems(ArrayList<UItem> items, UniversalAdapter adapter) {
        items.add(UItem.asHeader(LocaleController.getString(R.string.NM_SettingsSectionInterfaceInput)));
        items.add(UItem.asCheck(ID_SHOW_ACCOUNTS, LocaleController.getString(R.string.NM_DBG_ShowAccounts))
                .setChecked(NebulaConfig.showAccounts));
        items.add(UItem.asCheck(ID_OLD_TIME_STYLE, LocaleController.getString(R.string.NM_DBG_OldTimeStyle))
                .setChecked(NebulaConfig.oldTimeStyle));
        items.add(UItem.asCheck(ID_REPLACE_PUNCT, LocaleController.getString(R.string.NM_DBG_ReplacePunctuation))
                .setChecked(NebulaConfig.replacePunctuationMarks));
        items.add(UItem.asCheck(ID_EDIT_TEXT_FIX, LocaleController.getString(R.string.NM_DBG_EditTextFix))
                .setChecked(NebulaConfig.editTextSuggestionsFix));
        items.add(UItem.asShadow(null));

        items.add(UItem.asHeader(LocaleController.getString(R.string.NM_SettingsSectionMediaRecording)));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            items.add(asSettingsValue(ID_AUDIO_SOURCE, IconBackgroundColors.BLUE,
                    R.drawable.msg_voice_unmuted,
                    LocaleController.getString(R.string.NM_DBG_AudioSource),
                    audioSourceLabel(NebulaConfig.audioSource)));
        }
        items.add(UItem.asCheck(ID_HIDE_VIDEO_TS, LocaleController.getString(R.string.NM_DBG_HideVideoTimestamp))
                .setChecked(NebulaConfig.hideVideoTimestamp));
        items.add(UItem.asCheck(ID_SEND_MAX_QUALITY, LocaleController.getString(R.string.NM_DBG_SendMaxQuality))
                .setChecked(NebulaConfig.sendVideosAtMaxQuality));
        items.add(UItem.asShadow(null));

        items.add(UItem.asHeader(LocaleController.getString(R.string.NM_SettingsSectionDiagnosticsCompatibility)));
        items.add(UItem.asCheck(ID_SHOW_RPC_ERRORS, LocaleController.getString(R.string.NM_DBG_ShowRPCErrors))
                .setChecked(NebulaConfig.showRPCErrors));
        items.add(UItem.asCheck(ID_JACKSON_JSON, LocaleController.getString(R.string.NM_DBG_JacksonJSONProvider))
                .setChecked(NebulaConfig.jacksonJSON_Provider));
        items.add(UItem.asShadow(null));

        if (BuildVars.DEBUG_VERSION) {
            items.add(UItem.asHeader(LocaleController.getString(R.string.NM_DBG_Header_Danger)));
            items.add(asSettingsLink(ID_FORCE_CRASH, IconBackgroundColors.RED,
                    R.drawable.msg_warning, LocaleController.getString(R.string.NM_DBG_ForceCrash)).red());
            items.add(UItem.asShadow(LocaleController.getString(R.string.NM_DBG_ForceCrash_Desc)));
        }
    }

    @Override
    public void onClick(UItem item, View view, int position, float x, float y) {
        int id = item.id;
        if (id == ID_SHOW_RPC_ERRORS) {
            NebulaConfig.toggleShowRPCErrors();
            applyCheck(item, view, NebulaConfig.showRPCErrors);
            showRestartBulletin();
        } else if (id == ID_OLD_TIME_STYLE) {
            NebulaConfig.toggleOldTimeStyle();
            applyCheck(item, view, NebulaConfig.oldTimeStyle);
        } else if (id == ID_AUDIO_SOURCE) {
            PopupHelper.showSimpleAlert(this,
                    LocaleController.getString(R.string.NM_DBG_AudioSource),
                    audioSourceOptions(), audioSourceSelection(NebulaConfig.audioSource), sel -> {
                        int[] values = audioSourceValues();
                        int source = sel >= 0 && sel < values.length
                                ? values[sel] : NebulaConfig.AUDIO_SOURCE_DEFAULT;
                        NebulaConfig.setAudioSource(source);
                        SettingsHelper.updateButtonValue(view, audioSourceLabel(source));
                        if (listView != null && listView.adapter != null) listView.adapter.update(true);
                    });
        } else if (id == ID_JACKSON_JSON) {
            NebulaConfig.toggleJacksonJSON_Provider();
            applyCheck(item, view, NebulaConfig.jacksonJSON_Provider);
            showRestartBulletin();
        } else if (id == ID_HIDE_VIDEO_TS) {
            NebulaConfig.toggleHideVideoTimestamp();
            applyCheck(item, view, NebulaConfig.hideVideoTimestamp);
        } else if (id == ID_REPLACE_PUNCT) {
            NebulaConfig.toggleReplacePunctuationMarks();
            applyCheck(item, view, NebulaConfig.replacePunctuationMarks);
            showRestartBulletin();
        } else if (id == ID_EDIT_TEXT_FIX) {
            NebulaConfig.toggleEditTextSuggestionsFix();
            applyCheck(item, view, NebulaConfig.editTextSuggestionsFix);
            showRestartBulletin();
        } else if (id == ID_SHOW_ACCOUNTS) {
            NebulaConfig.toggleShowAccounts();
            applyCheck(item, view, NebulaConfig.showAccounts);
            showRestartBulletin();
        } else if (id == ID_SEND_MAX_QUALITY) {
            NebulaConfig.toggleSendVideosAtMaxQuality();
            applyCheck(item, view, NebulaConfig.sendVideosAtMaxQuality);
        } else if (id == ID_FORCE_CRASH && BuildVars.DEBUG_VERSION) {
            throw new RuntimeException("NebulaGram debug: force-crash button");
        }
    }

    private String[] audioSourceOptions() {
        int[] values = audioSourceValues();
        String[] labels = new String[values.length];
        for (int i = 0; i < values.length; i++) labels[i] = audioSourceLabel(values[i]);
        return labels;
    }

    private int[] audioSourceValues() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return new int[]{0, 1, 5, 6, 7, 9, 10};
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return new int[]{0, 1, 5, 6, 7, 9};
        }
        return new int[]{0, 1, 5, 6, 7};
    }

    private int audioSourceSelection(int value) {
        int sanitized = NebulaConfig.sanitizeAudioSource(value);
        int[] values = audioSourceValues();
        for (int i = 0; i < values.length; i++) {
            if (values[i] == sanitized) return i;
        }
        return 0;
    }

    private String audioSourceLabel(int v) {
        switch (NebulaConfig.sanitizeAudioSource(v)) {
            case NebulaConfig.AUDIO_SOURCE_MIC: return "MIC";
            case NebulaConfig.AUDIO_SOURCE_CAMCORDER: return "CAMCORDER";
            case NebulaConfig.AUDIO_SOURCE_VOICE_RECOGNITION: return "VOICE_RECOGNITION";
            case NebulaConfig.AUDIO_SOURCE_VOICE_COMMUNICATION: return "VOICE_COMMUNICATION";
            case NebulaConfig.AUDIO_SOURCE_UNPROCESSED: return "UNPROCESSED";
            case NebulaConfig.AUDIO_SOURCE_VOICE_PERFORMANCE: return "VOICE_PERFORMANCE";
            default: return "DEFAULT";
        }
    }

    private void applyCheck(UItem item, View view, boolean value) {
        item.checked = value;
        updateCheckState(view, value);
    }
}

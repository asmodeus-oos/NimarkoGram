/**
 * NebulaMedia native settings screen. Mirrors the Python plugin's
 * create_settings: auto-download switch, YouTube default-format selector, ask
 * YouTube format every time. Plus a test-download tile and the supported
 * platforms info card.
 *
 * Reached from MainPreferencesActivity → "Разное" / "Misc" → NebulaMedia.
 */
package app.nebulagram.messenger.preferences;

import android.view.View;

import java.util.ArrayList;

import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.Components.UItem;
import org.telegram.ui.Components.UniversalAdapter;

import app.nebulagram.messenger.NebulaConfig;

public class NebulaMediaPreferencesActivity extends BasePreferencesActivity {

    private static final int ID_AUTO_DOWNLOAD = 100;
    private static final int ID_YT_ASK        = 101;
    private static final int ID_YT_FMT_VIDEO  = 102;
    private static final int ID_YT_FMT_AUDIO  = 103;
    private final Runnable refreshRowsRunnable = () -> {
        if (listView != null && listView.adapter != null) {
            listView.adapter.update(true);
        }
    };

    @Override
    public String getTitle() {
        return LocaleController.getString(R.string.NM_DownloadMedia);
    }

    @Override
    public void fillItems(ArrayList<UItem> items, UniversalAdapter adapter) {
        String supportedPlatforms = LocaleController.getString(R.string.NM_NM_SupportedPlatforms)
                + "\n" + LocaleController.getString(R.string.NM_NM_PlatformsList);
        items.add(UItem.asHeader(LocaleController.getString(R.string.NM_SettingsSectionDownloads)));
        items.add(UItem.asCheck(ID_AUTO_DOWNLOAD,
                LocaleController.getString(R.string.NM_NM_AutoDownload))
                .setChecked(NebulaConfig.nebulaMediaAuto));
        String mainHint = LocaleController.getString(R.string.NM_NM_AutoDownload_Desc);
        if (!NebulaConfig.nebulaMediaAuto) {
            mainHint += "\n\n" + supportedPlatforms;
        }
        items.add(UItem.asShadow(mainHint));

        if (NebulaConfig.nebulaMediaAuto) {
            items.add(UItem.asHeader(LocaleController.getString(R.string.NM_NM_YtFormat)));
            items.add(UItem.asCheck(ID_YT_ASK,
                    LocaleController.getString(R.string.NM_NM_YtAsk))
                    .setChecked(NebulaConfig.nebulaMediaYtAsk));
            if (!NebulaConfig.nebulaMediaYtAsk) {
                items.add(UItem.asRadio(ID_YT_FMT_VIDEO,
                        LocaleController.getString(R.string.NM_NM_FormatVideo))
                        .setChecked(NebulaConfig.nebulaMediaYtFmt == 0));
                items.add(UItem.asRadio(ID_YT_FMT_AUDIO,
                        LocaleController.getString(R.string.NM_NM_FormatAudio))
                        .setChecked(NebulaConfig.nebulaMediaYtFmt == 1));
            }
            items.add(UItem.asShadow(supportedPlatforms));
        }
    }

    @Override
    public void onClick(UItem uItem, View view, int i, float f, float f2) {
        if (uItem == null) return;
        switch (uItem.id) {
            case ID_AUTO_DOWNLOAD:
                NebulaConfig.toggleNebulaMediaAuto();
                updateCheckState(view, NebulaConfig.nebulaMediaAuto);
                reloadMainInfo();
                break;
            case ID_YT_ASK:
                NebulaConfig.toggleNebulaMediaYtAsk();
                updateCheckState(view, NebulaConfig.nebulaMediaYtAsk);
                reloadMainInfo();
                break;
            case ID_YT_FMT_VIDEO:
                NebulaConfig.setNebulaMediaYtFmt(0);
                updateCheckState(view, true);
                reloadMainInfo();
                break;
            case ID_YT_FMT_AUDIO:
                NebulaConfig.setNebulaMediaYtFmt(1);
                updateCheckState(view, true);
                reloadMainInfo();
                break;
        }
    }

    /** Refreshes the recycler so switches/radios show the new state. */
    private void reloadMainInfo() {
        AndroidUtilities.cancelRunOnUIThread(refreshRowsRunnable);
        AndroidUtilities.runOnUIThread(refreshRowsRunnable, 180);
    }

    @Override
    public void onFragmentDestroy() {
        AndroidUtilities.cancelRunOnUIThread(refreshRowsRunnable);
        super.onFragmentDestroy();
    }
}

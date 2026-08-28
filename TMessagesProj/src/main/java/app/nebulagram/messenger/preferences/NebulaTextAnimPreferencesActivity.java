package app.nebulagram.messenger.preferences;

import android.view.View;

import java.util.ArrayList;

import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;
import org.telegram.ui.Components.UItem;
import org.telegram.ui.Components.UniversalAdapter;

import app.nebulagram.messenger.NebulaConfig;

public class NebulaTextAnimPreferencesActivity extends BasePreferencesActivity {

    private static final int ID_MASTER  = 200;
    private static final int ID_APPEAR  = 201;
    private static final int ID_CURSOR  = 202;
    private static final int ID_DELETE  = 203;
    private static final int ID_SPOILER = 204;

    @Override
    public String getTitle() {
        return LocaleController.getString(R.string.NM_TA_Title);
    }

    @Override
    public void fillItems(ArrayList<UItem> items, UniversalAdapter adapter) {
        items.add(UItem.asHeader(LocaleController.getString(R.string.NM_TA_HeaderMain)));
        items.add(UItem.asCheck(ID_MASTER,
                        LocaleController.getString(R.string.NM_TA_Master))
                .setChecked(NebulaConfig.nebulaTextAnim));

        if (NebulaConfig.nebulaTextAnim) {
            items.add(UItem.asShadow(LocaleController.getString(R.string.NM_TA_Master_Desc)));
            items.add(UItem.asHeader(LocaleController.getString(R.string.NM_TA_HeaderEffects)));
            items.add(UItem.asCheck(ID_APPEAR,
                            LocaleController.getString(R.string.NM_TA_Appear))
                    .setChecked(NebulaConfig.nebulaTextAnimAppear));
            items.add(UItem.asCheck(ID_CURSOR,
                            LocaleController.getString(R.string.NM_TA_Cursor))
                    .setChecked(NebulaConfig.nebulaTextAnimCursor));
            items.add(UItem.asCheck(ID_DELETE,
                            LocaleController.getString(R.string.NM_TA_Delete))
                    .setChecked(NebulaConfig.nebulaTextAnimDelete));
            items.add(UItem.asCheck(ID_SPOILER,
                            LocaleController.getString(R.string.NM_TA_Spoiler))
                    .setChecked(NebulaConfig.nebulaTextAnimSpoiler));
            items.add(UItem.asShadow(LocaleController.getString(R.string.NM_SettingsSummaryTextAnimation)));
        } else {
            items.add(UItem.asShadow(LocaleController.getString(R.string.NM_TA_Master_Hint)));
        }
    }

    @Override
    public void onClick(UItem uItem, View view, int i, float f, float f2) {
        if (uItem == null) return;
        switch (uItem.id) {
            case ID_MASTER:
                NebulaConfig.toggleNebulaTextAnim();
                applyCheck(uItem, view, NebulaConfig.nebulaTextAnim);
                reload();
                break;
            case ID_APPEAR:
                NebulaConfig.toggleNebulaTextAnimAppear();
                applyCheck(uItem, view, NebulaConfig.nebulaTextAnimAppear);
                break;
            case ID_CURSOR:
                NebulaConfig.toggleNebulaTextAnimCursor();
                applyCheck(uItem, view, NebulaConfig.nebulaTextAnimCursor);
                break;
            case ID_DELETE:
                NebulaConfig.toggleNebulaTextAnimDelete();
                applyCheck(uItem, view, NebulaConfig.nebulaTextAnimDelete);
                break;
            case ID_SPOILER:
                NebulaConfig.toggleNebulaTextAnimSpoiler();
                applyCheck(uItem, view, NebulaConfig.nebulaTextAnimSpoiler);
                break;
        }
    }

    private void applyCheck(UItem item, View view, boolean value) {
        item.checked = value;
        updateCheckState(view, value);
    }

    private void reload() {
        if (listView != null && listView.adapter != null) {
            listView.adapter.update(true);
        }
    }
}

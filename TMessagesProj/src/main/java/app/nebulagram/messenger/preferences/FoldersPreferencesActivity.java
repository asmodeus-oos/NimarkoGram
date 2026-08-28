package app.nebulagram.messenger.preferences;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;

import org.telegram.messenger.LocaleController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.R;
import org.telegram.ui.Cells.TextCell;
import org.telegram.ui.SettingsActivity;
import org.telegram.ui.Components.IconBackgroundColors;
import org.telegram.ui.Components.UItem;
import org.telegram.ui.Components.UniversalAdapter;

import app.nebulagram.messenger.NebulaConfig;
import app.nebulagram.messenger.preferences.folders.cells.FoldersPreviewCell;
import app.nebulagram.messenger.preferences.helpers.PopupHelper;
import app.nebulagram.messenger.preferences.helpers.SettingsHelper;

public class FoldersPreferencesActivity extends BasePreferencesActivity {
    private static final int ID_PREVIEW = 0;
    private static final int ID_HIDE_ALL_CHATS = 1;
    private static final int ID_HIDE_COUNTER = 2;
    private static final int ID_TAB_ICON_TYPE = 3;
    private static final int ID_ADD_STROKE = 4;

    private static final int ID_FOLDER_NAME_HEADER = 5;
    private static final int ID_FOLDERS_AT_BOTTOM = 6;

    private FoldersPreviewCell previewCell;

    @Override
    public String getTitle() {
        return LocaleController.getString(R.string.NM_AP_Folders);
    }

    private FoldersPreviewCell ensurePreviewCell(Context context) {
        if (previewCell == null) {
            previewCell = new FoldersPreviewCell(context);
            previewCell.setBackgroundColor(org.telegram.ui.ActionBar.Theme.getColor(
                    org.telegram.ui.ActionBar.Theme.key_windowBackgroundWhite));
        }
        return previewCell;
    }

    @Override
    public void fillItems(ArrayList<UItem> items, UniversalAdapter adapter) {
        Context ctx = getContext();
        if (ctx != null) {
            items.add(UItem.asCustom(ID_PREVIEW, ensurePreviewCell(ctx)));
            items.add(UItem.asShadow(null));
        }

        items.add(UItem.asHeader(LocaleController.getString(R.string.NM_SettingsSectionFolderTabs)));
        items.add(UItem.asCheck(ID_HIDE_ALL_CHATS, LocaleController.getString(R.string.NM_FO_TabsHideAllChats))
                .setChecked(NebulaConfig.tabsHideAllChats));
        items.add(UItem.asCheck(ID_HIDE_COUNTER, LocaleController.getString(R.string.NM_FO_TabsNoCounter))
                .setChecked(NebulaConfig.tabsNoUnread));
        items.add(asSettingsValue(ID_TAB_ICON_TYPE, IconBackgroundColors.BLUE,
                R.drawable.msg_customize,
                LocaleController.getString(R.string.NM_FO_TabStyle),
                safeAt(tabModeOptions(), NebulaConfig.tabMode)));
        items.add(SettingsHelper.asSwitchCG(ID_ADD_STROKE,
                        LocaleController.getString(R.string.NM_FO_TabStyleStroke),
                        LocaleController.getString(R.string.NM_FO_TabStyleStroke_Desc))
                .setChecked(NebulaConfig.tabStyleStroke));
        items.add(UItem.asShadow(null));

        items.add(UItem.asHeader(LocaleController.getString(R.string.NM_SettingsSectionFolderLayout)));
        items.add(SettingsHelper.asSwitchCG(ID_FOLDER_NAME_HEADER,
                LocaleController.getString(R.string.NM_FO_FolderNameInHeader))
                .setChecked(NebulaConfig.folderNameInHeader));
        items.add(SettingsHelper.asSwitchCG(ID_FOLDERS_AT_BOTTOM,
                        LocaleController.getString(R.string.NM_FO_FoldersAtBottom),
                        LocaleController.getString(R.string.NM_FO_FoldersAtBottom_Desc))
                .setChecked(NebulaConfig.foldersAtBottom));
        items.add(UItem.asShadow(null));
    }

    @Override
    public void onClick(UItem item, View view, int position, float x, float y) {
        int id = item.id;
        if (id == ID_HIDE_ALL_CHATS) {
            NebulaConfig.toggleTabsHideAllChats();
            applyCheck(item, view, NebulaConfig.tabsHideAllChats);
            if (previewCell != null) previewCell.updateAllChatsTabName(true);
            if (getParentLayout() != null) getParentLayout().rebuildAllFragmentViews(false, false);
            getNotificationCenter().postNotificationName(NotificationCenter.dialogFiltersUpdated);
            getNotificationCenter().postNotificationName(NotificationCenter.mainUserInfoChanged);
        } else if (id == ID_HIDE_COUNTER) {
            NebulaConfig.toggleTabsNoUnread();
            applyCheck(item, view, NebulaConfig.tabsNoUnread);
            if (previewCell != null) previewCell.updateTabCounter(true);
            if (getParentLayout() != null) getParentLayout().rebuildAllFragmentViews(false, false);
            getNotificationCenter().postNotificationName(NotificationCenter.dialogFiltersUpdated);
        } else if (id == ID_TAB_ICON_TYPE) {
            PopupHelper.showSimpleAlert(this,
                    LocaleController.getString(R.string.NM_FO_TabStyle),
                    tabModeOptions(),
                    NebulaConfig.tabMode,
                    sel -> {
                        NebulaConfig.setTabMode(sel);
                        refreshValueCell(view, safeAt(tabModeOptions(), sel));
                        if (previewCell != null) {
                            previewCell.updateTabIcons(true);
                            previewCell.updateTabTitle(true);
                        }
                        if (getParentLayout() != null) getParentLayout().rebuildAllFragmentViews(false, false);
                        getNotificationCenter().postNotificationName(NotificationCenter.dialogFiltersUpdated);
                    });
        } else if (id == ID_ADD_STROKE) {
            NebulaConfig.toggleTabStyleStroke();
            applyCheck(item, view, NebulaConfig.tabStyleStroke);
            if (previewCell != null) previewCell.invalidate();
            if (getParentLayout() != null) getParentLayout().rebuildAllFragmentViews(false, false);
        } else if (id == ID_FOLDER_NAME_HEADER) {
            NebulaConfig.toggleFolderNameInHeader();
            applyCheck(item, view, NebulaConfig.folderNameInHeader);
            if (getParentLayout() != null) getParentLayout().rebuildAllFragmentViews(false, false);
            getNotificationCenter().postNotificationName(NotificationCenter.dialogFiltersUpdated);
        } else if (id == ID_FOLDERS_AT_BOTTOM) {
            NebulaConfig.toggleFoldersAtBottom();
            applyCheck(item, view, NebulaConfig.foldersAtBottom);
            if (getParentLayout() != null) getParentLayout().rebuildAllFragmentViews(false, false);
            showRestartBulletin();
        }
    }

    private void applyCheck(UItem item, View view, boolean value) {
        item.checked = value;
        updateCheckState(view, value);
    }

    private void refreshValueCell(View view, String newValue) {
        if (view instanceof TextCell) {
            ((TextCell) view).setValue(newValue, true);
        } else if (view instanceof SettingsActivity.SettingCell) {
            ((SettingsActivity.SettingCell) view).setValue(newValue);
        }
        if (listView != null && listView.adapter != null) listView.adapter.update(true);
    }

    private String[] tabModeOptions() {
        return new String[]{
                LocaleController.getString(R.string.NM_FO_TabModeMix),
                LocaleController.getString(R.string.NM_FO_TabModeText),
                LocaleController.getString(R.string.NM_FO_TabModeIcon),
        };
    }

    private static String safeAt(String[] arr, int i) {
        if (arr == null || arr.length == 0) return "";
        if (i < 0 || i >= arr.length) return arr[0];
        return arr[i];
    }
}

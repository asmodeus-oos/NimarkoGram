package app.nebulagram.messenger.preferences;

import android.view.View;

import java.util.ArrayList;

import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;
import org.telegram.ui.Components.UItem;
import org.telegram.ui.Components.UniversalAdapter;

import app.nebulagram.messenger.NebulaConfig;

public class MessageMenuItemsPreferencesActivity extends BasePreferencesActivity {
    private static final int ID_SAVE_FOR_NOTIFICATIONS = 1;
    private static final int ID_REPLY = 2;
    private static final int ID_SAVE_TO_GALLERY = 3;
    private static final int ID_COPY_PHOTO = 4;
    private static final int ID_COPY_PHOTO_AS_STICKER = 5;
    private static final int ID_SAVE_TO_DOWNLOADS = 6;
    private static final int ID_SHARE = 7;
    private static final int ID_CLEAR_FROM_CACHE = 8;
    private static final int ID_FORWARD = 9;
    private static final int ID_FORWARD_WO_AUTHORSHIP = 10;
    private static final int ID_VIEW_HISTORY = 11;
    private static final int ID_SAVE_MESSAGE = 12;
    private static final int ID_REPORT = 13;
    private static final int ID_JSON = 14;
    private static final int ID_FORWARD_WO_CAPTION = 15;
    private static final int ID_DOWNLOAD_STICKER = 16;
    private static final int ID_GET_CUSTOM_REACTIONS = 17;
    private static final int ID_DETAILS = 18;

    @Override
    public String getTitle() {
        return LocaleController.getString(R.string.NM_MM_MessageMenuItems);
    }

    @Override
    public void fillItems(ArrayList<UItem> items, UniversalAdapter adapter) {
        items.add(UItem.asHeader(LocaleController.getString(R.string.NM_SettingsSectionPrimaryActions)));
        items.add(UItem.asCheck(ID_SAVE_FOR_NOTIFICATIONS, LocaleController.getString(R.string.SaveForNotifications))
                .setChecked(NebulaConfig.showSaveForNotifications));
        items.add(UItem.asCheck(ID_REPLY, LocaleController.getString(R.string.Reply))
                .setChecked(NebulaConfig.showReply));
        items.add(UItem.asCheck(ID_SAVE_TO_GALLERY, LocaleController.getString(R.string.SaveToGallery))
                .setChecked(NebulaConfig.showSaveToGallery));
        items.add(UItem.asCheck(ID_COPY_PHOTO, LocaleController.getString(R.string.NM_MI_CopyPhoto))
                .setChecked(NebulaConfig.showCopyPhoto));
        items.add(UItem.asCheck(ID_COPY_PHOTO_AS_STICKER, LocaleController.getString(R.string.NM_MI_CopyPhotoAsSticker))
                .setChecked(NebulaConfig.showCopyPhotoAsSticker));
        items.add(UItem.asCheck(ID_SAVE_TO_DOWNLOADS, LocaleController.getString(R.string.SaveToDownloads))
                .setChecked(NebulaConfig.showSaveToDownloads));
        items.add(UItem.asCheck(ID_SHARE, LocaleController.getString(R.string.ShareFile))
                .setChecked(NebulaConfig.showShare));
        items.add(UItem.asCheck(ID_CLEAR_FROM_CACHE, LocaleController.getString(R.string.NM_MI_ClearFromCache))
                .setChecked(NebulaConfig.showClearFromCache));
        items.add(UItem.asCheck(ID_FORWARD, LocaleController.getString(R.string.Forward))
                .setChecked(NebulaConfig.showForward));
        items.add(UItem.asCheck(ID_FORWARD_WO_AUTHORSHIP, LocaleController.getString(R.string.NM_MI_ForwardWoAuthorship))
                .setChecked(NebulaConfig.showForwardWoAuthorship));
        items.add(UItem.asCheck(ID_VIEW_HISTORY, LocaleController.getString(R.string.AvatarPreviewSearchMessages))
                .setChecked(NebulaConfig.showViewHistory));
        items.add(UItem.asCheck(ID_SAVE_MESSAGE, LocaleController.getString(R.string.NM_MI_SaveToSaved))
                .setChecked(NebulaConfig.showSaveMessage));
        items.add(UItem.asCheck(ID_REPORT, LocaleController.getString(R.string.ReportChat))
                .setChecked(NebulaConfig.showReport));
        items.add(UItem.asCheck(ID_JSON, LocaleController.getString(R.string.NM_MI_JSON))
                .setChecked(NebulaConfig.showJSON));
        items.add(UItem.asShadow(null));
        items.add(UItem.asHeader(LocaleController.getString(R.string.NM_SettingsSectionExtraActions)));
        items.add(UItem.asCheck(ID_FORWARD_WO_CAPTION, LocaleController.getString(R.string.NM_MI_ForwardWoCaption))
                .setChecked(NebulaConfig.showForwardWoCaption));
        items.add(UItem.asCheck(ID_DOWNLOAD_STICKER, LocaleController.getString(R.string.NM_MI_DownloadSticker))
                .setChecked(NebulaConfig.showDownloadSticker));
        items.add(UItem.asCheck(ID_GET_CUSTOM_REACTIONS, LocaleController.getString(R.string.AccDescrCustomEmoji))
                .setChecked(NebulaConfig.showGetCustomReactions));
        items.add(UItem.asCheck(ID_DETAILS, LocaleController.getString(R.string.NM_MI_Details))
                .setChecked(NebulaConfig.showDetails));
        items.add(UItem.asShadow(null));
    }

    @Override
    public void onClick(UItem item, View view, int position, float x, float y) {
        int id = item.id;
        if (id == ID_SAVE_FOR_NOTIFICATIONS) {
            NebulaConfig.toggleShowSaveForNotifications();
            applyCheck(item, view, NebulaConfig.showSaveForNotifications);
        } else if (id == ID_REPLY) {
            NebulaConfig.toggleShowReply();
            applyCheck(item, view, NebulaConfig.showReply);
        } else if (id == ID_SAVE_TO_GALLERY) {
            NebulaConfig.toggleShowSaveToGallery();
            applyCheck(item, view, NebulaConfig.showSaveToGallery);
        } else if (id == ID_COPY_PHOTO) {
            NebulaConfig.toggleShowCopyPhoto();
            applyCheck(item, view, NebulaConfig.showCopyPhoto);
        } else if (id == ID_COPY_PHOTO_AS_STICKER) {
            NebulaConfig.toggleShowCopyPhotoAsSticker();
            applyCheck(item, view, NebulaConfig.showCopyPhotoAsSticker);
        } else if (id == ID_SAVE_TO_DOWNLOADS) {
            NebulaConfig.toggleShowSaveToDownloads();
            applyCheck(item, view, NebulaConfig.showSaveToDownloads);
        } else if (id == ID_SHARE) {
            NebulaConfig.toggleShowShare();
            applyCheck(item, view, NebulaConfig.showShare);
        } else if (id == ID_CLEAR_FROM_CACHE) {
            NebulaConfig.toggleShowClearFromCache();
            applyCheck(item, view, NebulaConfig.showClearFromCache);
        } else if (id == ID_FORWARD) {
            NebulaConfig.toggleShowForward();
            applyCheck(item, view, NebulaConfig.showForward);
        } else if (id == ID_FORWARD_WO_AUTHORSHIP) {
            NebulaConfig.toggleShowForwardWoAuthorship();
            applyCheck(item, view, NebulaConfig.showForwardWoAuthorship);
        } else if (id == ID_VIEW_HISTORY) {
            NebulaConfig.toggleShowViewHistory();
            applyCheck(item, view, NebulaConfig.showViewHistory);
        } else if (id == ID_SAVE_MESSAGE) {
            NebulaConfig.toggleShowSaveMessage();
            applyCheck(item, view, NebulaConfig.showSaveMessage);
        } else if (id == ID_REPORT) {
            NebulaConfig.toggleShowReport();
            applyCheck(item, view, NebulaConfig.showReport);
        } else if (id == ID_JSON) {
            NebulaConfig.toggleShowJSON();
            applyCheck(item, view, NebulaConfig.showJSON);
        } else if (id == ID_FORWARD_WO_CAPTION) {
            NebulaConfig.toggleShowForwardWoCaption();
            applyCheck(item, view, NebulaConfig.showForwardWoCaption);
        } else if (id == ID_DOWNLOAD_STICKER) {
            NebulaConfig.toggleShowDownloadSticker();
            applyCheck(item, view, NebulaConfig.showDownloadSticker);
        } else if (id == ID_GET_CUSTOM_REACTIONS) {
            NebulaConfig.toggleShowGetCustomReactions();
            applyCheck(item, view, NebulaConfig.showGetCustomReactions);
        } else if (id == ID_DETAILS) {
            NebulaConfig.toggleShowDetails();
            applyCheck(item, view, NebulaConfig.showDetails);
        }
    }

    private void applyCheck(UItem item, View view, boolean value) {
        item.checked = value;
        updateCheckState(view, value);
    }
}

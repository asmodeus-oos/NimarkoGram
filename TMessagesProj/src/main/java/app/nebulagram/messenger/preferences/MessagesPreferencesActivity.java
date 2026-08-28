/*
 * This file is part of NebulaGram for Android.
 * Licensed under GNU GPL v2 or later. See LICENSE.
 * Copyright Ettacent, 2026.
 */

package app.nebulagram.messenger.preferences;

import static org.telegram.messenger.LocaleController.getString;

import android.content.Context;
import android.view.View;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.R;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.Components.UItem;
import org.telegram.ui.Components.UniversalAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.nebulagram.messenger.NebulaConfig;
import app.nebulagram.messenger.preferences.helpers.NebulaAlertDialogSwitchers;
import app.nebulagram.messenger.preferences.helpers.PopupHelper;
import app.nebulagram.messenger.preferences.helpers.SettingsHelper;

public class MessagesPreferencesActivity extends NebulaUniversalPreferencesActivity {

    private final int messageMenuRow = 1, messageSizeRow = 2, directShareRow = 3,
            showForwardDateRow = 5, pencilIconForEditedRow = 6;

    private final int messageFilterRow = 9, leftBottomBtnRow = 10, doubleTapRow = 11, slideActionRow = 12, deleteForAllRow = 13;

    private final int reactionsOverlayRow = 14, reactionAnimationRow = 15, tapsOnPremiumStickersRow = 16, premiumStickersAutoplayRow = 17;

    private final int gifSpoilersRow = 18;

    @Override
    protected CharSequence getTitle() {
        return getString(R.string.MessagesSettings);
    }

    @Override
    public View createView(Context context) {
        setMD3(true);
        return super.createView(context);
    }

    @Override
    public void fillItems(ArrayList<UItem> items, UniversalAdapter adapter) {
        items.add(UItem.asHeader(getString(R.string.NM_CP_Header_ChatView)));
        items.add(asPlainSettingsRowWithSubtitle(messageMenuRow, getString(R.string.CP_MessageMenu),
                getString(R.string.NM_SettingsSummaryMessageMenu)));
        items.add(asPlainSettingsRow(messageSizeRow, getString(R.string.CP_Messages_Size)));
        items.add(asPlainSettingsRowWithSubtitle(directShareRow, getString(R.string.DirectShare),
                getString(R.string.DirectShareInfo)));
        items.add(SettingsHelper.asSwitchCG(showForwardDateRow, getString(R.string.CP_ForwardMsgDate))
                .setChecked(app.nebulagram.messenger.NebulaConfig.msgForwardDate)
        );
        items.add(SettingsHelper.asSwitchCG(pencilIconForEditedRow, getString(R.string.AP_ShowPencilIcon))
                .setChecked(app.nebulagram.messenger.NebulaConfig.showPencilIcon)
        );
        items.add(UItem.asShadow(null));

        items.add(UItem.asHeader(getString(R.string.ActionsChartTitle)));
        items.add(asPlainSettingsRowWithSubtitle(messageFilterRow, getString(R.string.CP_Message_Filtering),
                getString(R.string.NM_SettingsSummaryMessageFilters)));
        items.add(asPlainSettingsRow(leftBottomBtnRow,
                getString(R.string.CP_LeftBottomButtonAction), getLeftBottomButtonValue()));
        items.add(asPlainSettingsRow(doubleTapRow,
                getString(R.string.CP_DoubleTapAction), getDoubleTapActionValue()));
        items.add(asPlainSettingsRow(slideActionRow,
                getString(R.string.NM_MsgSlideAction), getSlideActionValue()));
        items.add(SettingsHelper.asSwitchCG(deleteForAllRow, getString(R.string.CP_DeleteForAll))
                .setChecked(app.nebulagram.messenger.NebulaConfig.deleteForAll)
        );
        items.add(UItem.asShadow(null));

        items.add(UItem.asHeader(getString(R.string.TelegramPremium)));
        items.add(SettingsHelper.asSwitchCG(reactionsOverlayRow, getString(R.string.CP_DisableReactionsOverlay))
                .setChecked(app.nebulagram.messenger.NebulaConfig.disableReactionsOverlay)
        );
        items.add(SettingsHelper.asSwitchCG(reactionAnimationRow, getString(R.string.CP_DisableReactionAnim))
                .setChecked(app.nebulagram.messenger.NebulaConfig.disableReactionAnim)
        );
        items.add(SettingsHelper.asSwitchCG(tapsOnPremiumStickersRow, getString(R.string.CP_DisablePremStickAnim))
                .setChecked(app.nebulagram.messenger.NebulaConfig.disablePremStickAnim)
        );
        items.add(SettingsHelper.asSwitchCG(premiumStickersAutoplayRow, getString(R.string.CP_DisablePremStickAutoPlay))
                .setChecked(app.nebulagram.messenger.NebulaConfig.disablePremStickAutoPlay)
        );
        items.add(UItem.asShadow(null));

        items.add(UItem.asHeader(getString(R.string.NM_MSG_Header_Sending)));
        items.add(SettingsHelper.asSwitchCG(gifSpoilersRow, getString(R.string.NM_MSG_GifSpoilers))
                .setChecked(app.nebulagram.messenger.NebulaConfig.gifSpoilers)
        );
        items.add(UItem.asShadow(null));
    }

    @Override
    public void onClick(UItem item, View view, int position, float x, float y) {
        if (item.id == messageMenuRow) {
            presentFragment(new MessageMenuPreferencesActivity());
        } else if (item.id == messageSizeRow) {
            NebulaAlertDialogSwitchers.showMessageSize(this);
        } else if (item.id == directShareRow) {
            showDirectShareConfigurator(this);
        } else if (item.id == showForwardDateRow) {
            NebulaConfig.toggleMsgForwardDate();
            updateCheckState(view, app.nebulagram.messenger.NebulaConfig.msgForwardDate);
        } else if (item.id == pencilIconForEditedRow) {
            NebulaConfig.toggleShowPencilIcon();
            updateCheckState(view, app.nebulagram.messenger.NebulaConfig.showPencilIcon);
        } else if (item.id == messageFilterRow) {
            presentFragment(new MessageFiltersPreferencesActivity());
        } else if (item.id == leftBottomBtnRow) {
            showLeftBottomButtonSelector(() -> SettingsHelper.updateButtonValue(view, getLeftBottomButtonValue()));
        } else if (item.id == doubleTapRow) {
            showDoubleTapSelector(() -> SettingsHelper.updateButtonValue(view, getDoubleTapActionValue()));
        } else if (item.id == slideActionRow) {
            showSlideActionSelector(() -> SettingsHelper.updateButtonValue(view, getSlideActionValue()));
        } else if (item.id == deleteForAllRow) {
            NebulaConfig.toggleDeleteForAll();
            updateCheckState(view, app.nebulagram.messenger.NebulaConfig.deleteForAll);
        } else if (item.id == reactionsOverlayRow) {
            NebulaConfig.toggleDisableReactionsOverlay();
            updateCheckState(view, app.nebulagram.messenger.NebulaConfig.disableReactionsOverlay);

            showRestartBulletin();
        } else if (item.id == reactionAnimationRow) {
            NebulaConfig.toggleDisableReactionAnim();
            updateCheckState(view, app.nebulagram.messenger.NebulaConfig.disableReactionAnim);

            showRestartBulletin();
        } else if (item.id == tapsOnPremiumStickersRow) {
            NebulaConfig.toggleDisablePremStickAnim();
            updateCheckState(view, app.nebulagram.messenger.NebulaConfig.disablePremStickAnim);

            showRestartBulletin();
        } else if (item.id == premiumStickersAutoplayRow) {
            NebulaConfig.toggleDisablePremStickAutoPlay();
            updateCheckState(view, app.nebulagram.messenger.NebulaConfig.disablePremStickAutoPlay);

            showRestartBulletin();
        } else if (item.id == gifSpoilersRow) {
            NebulaConfig.toggleGifSpoilers();
            updateCheckState(view, app.nebulagram.messenger.NebulaConfig.gifSpoilers);
        }
    }

    @Override
    public boolean onLongClick(UItem item, View view, int position, float x, float y) {
        return false;
    }

    private void showDirectShareConfigurator(BaseFragment fragment) {
        List<ChatsPreferencesActivity.MenuItemConfig> menuItems = Arrays.asList(
                new ChatsPreferencesActivity.MenuItemConfig(
                        getString(R.string.RepostToStory),
                        R.drawable.large_repost_story,
                        () -> NebulaConfig.shareDrawStoryButton,
                        () -> NebulaConfig.toggleShareDrawStoryButton(),
                        true,
                        false
                ),
                new ChatsPreferencesActivity.MenuItemConfig(
                        getString(R.string.FilterChats),
                        0,
                        () -> NebulaConfig.usersDrawShareButton,
                        () -> NebulaConfig.toggleUsersDrawShareButton(),
                        false,
                        false
                ),
                new ChatsPreferencesActivity.MenuItemConfig(
                        getString(R.string.FilterGroups),
                        0,
                        () -> NebulaConfig.supergroupsDrawShareButton,
                        () -> NebulaConfig.toggleSupergroupsDrawShareButton(),
                        false,
                        false
                ),
                new ChatsPreferencesActivity.MenuItemConfig(
                        getString(R.string.FilterChannels),
                        0,
                        () -> NebulaConfig.channelsDrawShareButton,
                        () -> NebulaConfig.toggleChannelsDrawShareButton(),
                        false,
                        false
                ),
                new ChatsPreferencesActivity.MenuItemConfig(
                        getString(R.string.FilterBots),
                        0,
                        () -> NebulaConfig.botsDrawShareButton,
                        () -> NebulaConfig.toggleBotsDrawShareButton(),
                        false,
                        false
                ),
                new ChatsPreferencesActivity.MenuItemConfig(
                        getString(R.string.StickersName),
                        0,
                        () -> NebulaConfig.stickersDrawShareButton,
                        () -> NebulaConfig.toggleStickersDrawShareButton(),
                        false,
                        false
                )
        );

        handleMenuAlert(getString(R.string.DirectShare), menuItems, fragment);
    }

    private String getLeftBottomButtonValue() {
        return switch (app.nebulagram.messenger.NebulaConfig.actionsBarLeftButton) {
            case NebulaConfig.ACTIONS_LEFT_SAVE_MESSAGE -> getString(R.string.NM_ToSaved);
            case NebulaConfig.ACTIONS_LEFT_DIRECT_SHARE -> getString(R.string.DirectShare);
            case NebulaConfig.ACTIONS_LEFT_FORWARD_WO_AUTHORSHIP -> getString(R.string.Forward) + " " + getString(R.string.NM_Without_Authorship);
            case NebulaConfig.ACTIONS_LEFT_REPLY -> getString(R.string.Reply);
            default -> getString(R.string.Reply);
        };
    }

    private void showLeftBottomButtonSelector(Runnable runnable) {
        ArrayList<String> configStringKeys = new ArrayList<>();
        ArrayList<Integer> configValues = new ArrayList<>();

        configStringKeys.add(getString(R.string.Forward) + " " + getString(R.string.NM_Without_Authorship));
        configValues.add(NebulaConfig.ACTIONS_LEFT_FORWARD_WO_AUTHORSHIP);

        configStringKeys.add(getString(R.string.Reply));
        configValues.add(NebulaConfig.ACTIONS_LEFT_REPLY);

        configStringKeys.add(getString(R.string.NM_ToSaved));
        configValues.add(NebulaConfig.ACTIONS_LEFT_SAVE_MESSAGE);

        configStringKeys.add(getString(R.string.DirectShare));
        configValues.add(NebulaConfig.ACTIONS_LEFT_DIRECT_SHARE);

        PopupHelper.show(configStringKeys, getString(R.string.CP_LeftBottomButtonAction), configValues.indexOf(app.nebulagram.messenger.NebulaConfig.actionsBarLeftButton), getContext(), i -> {
            NebulaConfig.setActionsBarLeftButton(configValues.get(i));
            if (runnable != null) runnable.run();
        });
    }

    private String getDoubleTapActionValue() {
        return switch (app.nebulagram.messenger.NebulaConfig.doubletapaction) {
            case NebulaConfig.DOUBLE_TAP_ACTION_REACTION -> getString(R.string.Reactions);
            case NebulaConfig.DOUBLE_TAP_ACTION_REPLY -> getString(R.string.Reply);
            case NebulaConfig.DOUBLE_TAP_ACTION_SAVE -> getString(R.string.NM_ToSaved);
            case NebulaConfig.DOUBLE_TAP_ACTION_EDIT -> getString(R.string.Edit);
            case NebulaConfig.DOUBLE_TAP_ACTION_EDIT_OR_REACTION -> getString(R.string.NM_DoubleTap_EditOrReact);
            case NebulaConfig.DOUBLE_TAP_ACTION_TRANSLATE -> getString(R.string.TranslateMessage);
            default -> getString(R.string.Disable);
        };
    }

    private void showDoubleTapSelector(Runnable runnable) {
        ArrayList<String> configStringKeys = new ArrayList<>();
        ArrayList<Integer> configValues = new ArrayList<>();

        configStringKeys.add(getString(R.string.Disable));
        configValues.add(NebulaConfig.DOUBLE_TAP_ACTION_NONE);

        configStringKeys.add(getString(R.string.Reactions));
        configValues.add(NebulaConfig.DOUBLE_TAP_ACTION_REACTION);

        configStringKeys.add(getString(R.string.Reply));
        configValues.add(NebulaConfig.DOUBLE_TAP_ACTION_REPLY);

        configStringKeys.add(getString(R.string.NM_ToSaved));
        configValues.add(NebulaConfig.DOUBLE_TAP_ACTION_SAVE);

        configStringKeys.add(getString(R.string.Edit));
        configValues.add(NebulaConfig.DOUBLE_TAP_ACTION_EDIT);

        configStringKeys.add(getString(R.string.TranslateMessage));
        configValues.add(NebulaConfig.DOUBLE_TAP_ACTION_TRANSLATE);

        configStringKeys.add(getString(R.string.NM_DoubleTap_EditOrReact));
        configValues.add(NebulaConfig.DOUBLE_TAP_ACTION_EDIT_OR_REACTION);

        PopupHelper.show(configStringKeys, getString(R.string.CP_DoubleTapAction), configValues.indexOf(app.nebulagram.messenger.NebulaConfig.doubletapaction), getContext(), i -> {
            NebulaConfig.setDoubleTapAction(configValues.get(i));
            if (runnable != null) runnable.run();
        });
    }

    private String getSlideActionValue() {
        return switch (app.nebulagram.messenger.NebulaConfig.messageslideaction) {
            case NebulaConfig.MESSAGE_SLIDE_ACTION_SAVE -> getString(R.string.NM_ToSaved);
            case NebulaConfig.MESSAGE_SLIDE_ACTION_TRANSLATE -> getString(R.string.TranslateMessage);
            case NebulaConfig.MESSAGE_SLIDE_ACTION_DIRECT_SHARE -> getString(R.string.DirectShare);
            default -> getString(R.string.Reply);
        };
    }

    private void showSlideActionSelector(Runnable runnable) {
        ArrayList<String> configStringKeys = new ArrayList<>();
        ArrayList<Integer> configValues = new ArrayList<>();

        configStringKeys.add(getString(R.string.Reply));
        configValues.add(NebulaConfig.MESSAGE_SLIDE_ACTION_REPLY);

        configStringKeys.add(getString(R.string.NM_ToSaved));
        configValues.add(NebulaConfig.MESSAGE_SLIDE_ACTION_SAVE);

        configStringKeys.add(getString(R.string.TranslateMessage));
        configValues.add(NebulaConfig.MESSAGE_SLIDE_ACTION_TRANSLATE);

        configStringKeys.add(getString(R.string.DirectShare));
        configValues.add(NebulaConfig.MESSAGE_SLIDE_ACTION_DIRECT_SHARE);

        PopupHelper.show(configStringKeys, getString(R.string.NM_MsgSlideAction), configValues.indexOf(app.nebulagram.messenger.NebulaConfig.messageslideaction), getContext(), i -> {
            NebulaConfig.setMessageSlideAction(configValues.get(i));
            if (runnable != null) runnable.run();
        });
    }

    private static void handleMenuAlert(String title, List<ChatsPreferencesActivity.MenuItemConfig> items, BaseFragment fragment) {
        ArrayList<String> prefTitle = new ArrayList<>();
        ArrayList<Integer> prefIcon = new ArrayList<>();
        ArrayList<Boolean> prefCheck = new ArrayList<>();
        ArrayList<Boolean> prefCheckInvisible = new ArrayList<>();
        ArrayList<Boolean> prefDivider = new ArrayList<>();
        ArrayList<Runnable> clickListener = new ArrayList<>();

        for (ChatsPreferencesActivity.MenuItemConfig item : items) {
            prefTitle.add(item.title);
            prefIcon.add(item.iconRes);
            prefCheck.add(item.isChecked.get());
            prefCheckInvisible.add(item.isCheckInvisible);
            prefDivider.add(item.divider);
            clickListener.add(item.toggle);
        }

        PopupHelper.showSwitchAlert(
                title,
                fragment,
                prefTitle,
                prefIcon,
                prefCheck,
                prefCheckInvisible,
                null,
                prefDivider,
                clickListener,
                null
        );
    }

}

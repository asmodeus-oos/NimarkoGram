package com.exteragram.messenger.utils;

import org.telegram.messenger.MessageObject;
import org.telegram.messenger.UserConfig;

public final class ChatUtils {

    private final int account;
    private final app.nebulagram.messenger.utils.chats.ChatUtils real;

    private ChatUtils(int account, app.nebulagram.messenger.utils.chats.ChatUtils real) {
        this.account = account;
        this.real = real;
    }

    public static ChatUtils getInstance() {
        return getInstance(UserConfig.selectedAccount);
    }

    public static ChatUtils getInstance(int account) {
        return new ChatUtils(account, app.nebulagram.messenger.utils.chats.ChatUtils.getInstance(account));
    }

    public String getPathToMessage(MessageObject messageObject) {
        return real.getPathToMessage(messageObject);
    }

    public CharSequence getMessageText(MessageObject selectedObject, MessageObject.GroupedMessages selectedObjectGroup) {
        return app.nebulagram.messenger.utils.chats.NebulaChatHelper.getInstance(account)
                .getMessageText(selectedObject, selectedObjectGroup);
    }
}

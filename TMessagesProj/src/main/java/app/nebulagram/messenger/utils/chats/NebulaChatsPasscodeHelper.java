/*
 * This file is part of NebulaGram for Android.
 * Licensed under GNU GPL v2 or later. See LICENSE.
 * Copyright Ettacent, 2026.
 */

package app.nebulagram.messenger.utils.chats;

import org.telegram.messenger.BaseController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.TLRPC;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicReferenceArray;

import app.nebulagram.messenger.NebulaConfig;
import app.nebulagram.messenger.utils.LockedChats;

public class NebulaChatsPasscodeHelper extends BaseController {

    private static final String LOCKED_CHATS_KEY = "locked_chats_list";
    private static final AtomicReferenceArray<NebulaChatsPasscodeHelper> instances =
            new AtomicReferenceArray<>(UserConfig.MAX_ACCOUNT_COUNT);

    public static NebulaChatsPasscodeHelper getInstance(int account) {
        NebulaChatsPasscodeHelper instance = instances.get(account);
        if (instance == null) {
            synchronized (NebulaChatsPasscodeHelper.class) {
                instance = instances.get(account);
                if (instance == null) {
                    instance = new NebulaChatsPasscodeHelper(account);
                    instances.set(account, instance);
                }
            }
        }
        return instance;
    }

    private NebulaChatsPasscodeHelper(int account) {
        super(account);
    }

    public String getPasscodeArray() {
        return LOCKED_CHATS_KEY;
    }

    public void saveArrayList(ArrayList<String> list, String key) {
        if (!LOCKED_CHATS_KEY.equals(key)) return;
        HashSet<Long> dialogIds = new HashSet<>();
        if (list != null) {
            for (String value : list) {
                try {
                    long dialogId = Long.parseLong(value);
                    if (dialogId != 0L) dialogIds.add(dialogId);
                } catch (Throwable ignored) {
                }
            }
        }
        LockedChats.replaceAll(currentAccount, getUserConfig().getClientUserId(), dialogIds);
    }

    public ArrayList<String> getArrayList(String key) {
        return LOCKED_CHATS_KEY.equals(key) ? LockedChats.getAll(currentAccount) : new ArrayList<>();
    }

    public boolean isChatLocked(long chatId) {
        return NebulaChatsPasswordHelper.isChatLocked(currentAccount, chatId);
    }

    public boolean isChatLocked(MessageObject messageObject) {
        return NebulaChatsPasswordHelper.isChatLocked(messageObject);
    }

    public boolean isEncryptedChat(long chatId) {
        return NebulaChatsPasswordHelper.isEncryptedChat(chatId, currentAccount);
    }

    public boolean isEncryptedChat(MessageObject messageObject) {
        return NebulaChatsPasswordHelper.isEncryptedChat(messageObject);
    }

    public ArrayList<TLRPC.MessageEntity> checkLockedChatsEntities(MessageObject messageObject) {
        return NebulaChatsPasswordHelper.checkLockedChatsEntities(messageObject);
    }

    public ArrayList<TLRPC.MessageEntity> checkLockedChatsEntities(
            MessageObject messageObject, ArrayList<TLRPC.MessageEntity> original) {
        return NebulaChatsPasswordHelper.checkLockedChatsEntities(messageObject, original);
    }

    public String replaceStringToSpoilers(String originalText, boolean force) {
        return NebulaChatsPasswordHelper.replaceStringToSpoilers(originalText, force);
    }

    public int getLockedChatsCount() {
        return LockedChats.count(currentAccount);
    }

    public boolean shouldRequireBiometrics(long userId, long chatId, long encId) {
        return NebulaChatsPasswordHelper.shouldRequireBiometrics(
                userId, chatId, encId, currentAccount);
    }

    public boolean shouldRequireBiometricsToOpenChats() {
        return NebulaConfig.askBiometricsToOpenChat;
    }

    public boolean shouldRequireBiometricsToOpenEncryptedChats() {
        return NebulaConfig.askBiometricsToOpenEncrypted;
    }

    public boolean askPasscodeBeforeDelete() {
        return NebulaConfig.askPasscodeBeforeDelete;
    }

    public boolean checkBiometricAvailable() {
        return NebulaChatsPasswordHelper.checkBiometricAvailable();
    }
}

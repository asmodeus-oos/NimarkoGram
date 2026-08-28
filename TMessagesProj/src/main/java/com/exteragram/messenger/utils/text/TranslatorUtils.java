package com.exteragram.messenger.utils.text;

import java.util.ArrayList;

import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

public abstract class TranslatorUtils {

    public interface TranslateCallback
            extends app.nebulagram.messenger.utils.text.TranslatorUtils.TranslateCallback {
    }

    public static boolean isTargetLanguageFollowApp() {
        return app.nebulagram.messenger.utils.text.TranslatorUtils.isTargetLanguageFollowApp();
    }

    public static void setTargetLanguage(String str) {
        app.nebulagram.messenger.utils.text.TranslatorUtils.setTargetLanguage(str);
    }

    public static String normalizeLanguageCode(String str) {
        return app.nebulagram.messenger.utils.text.TranslatorUtils.normalizeLanguageCode(str);
    }

    public static String primaryLanguageOf(String str) {
        return app.nebulagram.messenger.utils.text.TranslatorUtils.primaryLanguageOf(str);
    }

    public static String getResolvedTargetLanguageCode(String str) {
        return app.nebulagram.messenger.utils.text.TranslatorUtils.getResolvedTargetLanguageCode(str);
    }

    public static String getResolvedTargetLanguageCode() {
        return app.nebulagram.messenger.utils.text.TranslatorUtils.getResolvedTargetLanguageCode();
    }

    public static String getLanguageDisplayName(String str) {
        return app.nebulagram.messenger.utils.text.TranslatorUtils.getLanguageDisplayName(str);
    }

    public static String getLanguageTitleSystem(String str) {
        return app.nebulagram.messenger.utils.text.TranslatorUtils.getLanguageTitleSystem(str);
    }

    public static String getTargetLanguageTitle() {
        return app.nebulagram.messenger.utils.text.TranslatorUtils.getTargetLanguageTitle();
    }

    public static CharSequence[] getTargetLanguageTitles() {
        return app.nebulagram.messenger.utils.text.TranslatorUtils.getTargetLanguageTitles();
    }

    public static String getTargetLanguageCodeByIndex(int i) {
        return app.nebulagram.messenger.utils.text.TranslatorUtils.getTargetLanguageCodeByIndex(i);
    }

    public static int getTargetLanguageIndexByCode(String str) {
        return app.nebulagram.messenger.utils.text.TranslatorUtils.getTargetLanguageIndexByCode(str);
    }

    public static String getCurrentTranslatorName() {
        return app.nebulagram.messenger.utils.text.TranslatorUtils.getCurrentTranslatorName();
    }

    public static boolean isTargetLanguageSupportedForCurrentProvider(String str) {
        return app.nebulagram.messenger.utils.text.TranslatorUtils.isTargetLanguageSupportedForCurrentProvider(str);
    }

    public static boolean isRestrictedLanguage(String str) {
        return app.nebulagram.messenger.utils.text.TranslatorUtils.isRestrictedLanguage(str);
    }

    public static void ensureTargetLanguageCompatibleWithProvider() {
        app.nebulagram.messenger.utils.text.TranslatorUtils.ensureTargetLanguageCompatibleWithProvider();
    }

    public static void translate(CharSequence charSequence,
                                 String fromLang,
                                 String toLang,
                                 ArrayList<TLRPC.MessageEntity> entities,
                                 final TranslateCallback callback) {
        app.nebulagram.messenger.utils.text.TranslatorUtils.translate(
                charSequence, fromLang, toLang, entities, callback);
    }

    public static void translate(final CharSequence charSequence,
                                 String toLang,
                                 final ArrayList<TLRPC.MessageEntity> entities,
                                 final TranslateCallback callback) {
        app.nebulagram.messenger.utils.text.TranslatorUtils.translate(
                charSequence, toLang, entities, callback);
    }

    public static void translate(CharSequence charSequence, String toLang, TranslateCallback callback) {
        app.nebulagram.messenger.utils.text.TranslatorUtils.translate(charSequence, toLang, callback);
    }
}

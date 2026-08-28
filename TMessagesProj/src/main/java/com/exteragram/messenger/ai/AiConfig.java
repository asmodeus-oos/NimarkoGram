package com.exteragram.messenger.ai;

import com.exteragram.messenger.ai.data.Role;

import java.util.ArrayList;

public abstract class AiConfig {

    public static volatile boolean showResponseOnly = app.nebulagram.messenger.ai.AiConfig.showResponseOnly;
     
    public static volatile boolean insertAsQuote = app.nebulagram.messenger.ai.AiConfig.insertAsQuote;
     
    public static volatile boolean saveHistory = app.nebulagram.messenger.ai.AiConfig.saveHistory;
     
    public static volatile boolean responseStreaming = app.nebulagram.messenger.ai.AiConfig.responseStreaming;

    private AiConfig() {
    }

    public static boolean getShowResponseOnly() {
        return app.nebulagram.messenger.ai.AiConfig.getShowResponseOnly();
    }

    public static void setShowResponseOnly(boolean value) {
        showResponseOnly = value;
        app.nebulagram.messenger.ai.AiConfig.setShowResponseOnly(value);
    }

    public static boolean getInsertAsQuote() {
        return app.nebulagram.messenger.ai.AiConfig.getInsertAsQuote();
    }

    public static void setInsertAsQuote(boolean value) {
        insertAsQuote = value;
        app.nebulagram.messenger.ai.AiConfig.setInsertAsQuote(value);
    }

    public static boolean getSaveHistory() {
        return app.nebulagram.messenger.ai.AiConfig.getSaveHistory();
    }

    public static void setSaveHistory(boolean value) {
        saveHistory = value;
        app.nebulagram.messenger.ai.AiConfig.setSaveHistory(value);
    }

    public static boolean getResponseStreaming() {
        return app.nebulagram.messenger.ai.AiConfig.getResponseStreaming();
    }

    public static void setResponseStreaming(boolean value) {
        responseStreaming = value;
        app.nebulagram.messenger.ai.AiConfig.setResponseStreaming(value);
    }

    public static String getSelectedRole() {
        return app.nebulagram.messenger.ai.AiConfig.getSelectedRole();
    }

    public static void setSelectedRole(String name) {
        app.nebulagram.messenger.ai.AiConfig.setSelectedRole(name);
    }

    public static void setSelectedAiRole(Role role) {
        app.nebulagram.messenger.ai.AiConfig.setSelectedAiRole(role);
    }

    public static int getSelectedServiceHash() {
        return app.nebulagram.messenger.ai.AiConfig.getSelectedServiceHash();
    }

    public static void setSelectedServiceHash(int hash) {
        app.nebulagram.messenger.ai.AiConfig.setSelectedServiceHash(hash);
    }

    public static ArrayList<Role> getRoles() {
        
        return new ArrayList<>();
    }

    public static void saveRoles(ArrayList<Role> roles) {
        
        app.nebulagram.messenger.ai.AiConfig.saveRoles(new ArrayList<>());
    }

    public static void clearConversationHistory() {
        app.nebulagram.messenger.ai.AiConfig.clearConversationHistory();
    }

    public static void removeLastFromHistory() {
        app.nebulagram.messenger.ai.AiConfig.removeLastFromHistory();
    }
}

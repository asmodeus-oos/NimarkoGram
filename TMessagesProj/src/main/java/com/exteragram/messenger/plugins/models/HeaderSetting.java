package com.exteragram.messenger.plugins.models;

public class HeaderSetting extends app.nebulagram.messenger.plugins.models.HeaderSetting {
    public HeaderSetting(String text) {
        super(text);
    }

    public String getText() { return text; }
    public void setText(String v) { text = v; }
}

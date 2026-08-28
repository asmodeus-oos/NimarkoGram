package com.exteragram.messenger.plugins.ui;

import app.nebulagram.messenger.plugins.Plugin;

public class PluginSettingsActivity extends app.nebulagram.messenger.plugins.ui.PluginSettingsActivity {
    public PluginSettingsActivity(Plugin plugin) {
        super(plugin);
    }

    public PluginSettingsActivity(Plugin plugin, String name) {
        super(plugin, name);
    }

    public PluginSettingsActivity(com.exteragram.messenger.plugins.Plugin plugin) {
        super(plugin);
    }

    public PluginSettingsActivity(com.exteragram.messenger.plugins.Plugin plugin, String name) {
        super(plugin, name);
    }
}

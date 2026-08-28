package app.nebulagram.messenger.plugins.hooks;

import app.nebulagram.messenger.plugins.PluginsController;

public interface HookRecord {
    void cleanup();

    boolean matches(Object obj);

    default PluginsController.PluginRuntimeToken getRuntimeToken() {
        return null;
    }
}

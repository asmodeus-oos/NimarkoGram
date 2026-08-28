package com.exteragram.messenger.plugins.hooks;

public class XposedHookRecord extends app.nebulagram.messenger.plugins.hooks.XposedHookRecord {
    public XposedHookRecord(de.robv.android.xposed.XC_MethodHook.Unhook unhook) {
        super(unhook);
    }
}

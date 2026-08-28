package com.exteragram.messenger.components;

import android.view.View;

import org.telegram.ui.ActionBar.BaseFragment;

public abstract class IconSelectorAlert extends app.nebulagram.messenger.components.IconSelectorAlert {

    public static void show(BaseFragment fragment, View anchor, String selected,
                            OnIconSelectedListener listener) {
        app.nebulagram.messenger.components.IconSelectorAlert.show(fragment, anchor, selected, listener);
    }
}

package com.exteragram.messenger.components;

import android.content.Context;

import org.telegram.messenger.Utilities;
import org.telegram.ui.Components.PopupSwipeBackLayout;

public class SearchPhotoPopupWrapper extends app.nebulagram.messenger.components.SearchPhotoPopupWrapper {
    public SearchPhotoPopupWrapper(Context context,
                                   PopupSwipeBackLayout popupSwipeBackLayout,
                                   Utilities.Callback2<String, Boolean> callback) {
        super(context, popupSwipeBackLayout, callback);
    }
}

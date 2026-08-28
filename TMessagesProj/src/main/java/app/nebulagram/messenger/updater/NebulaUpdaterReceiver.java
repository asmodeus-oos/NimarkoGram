package app.nebulagram.messenger.updater;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NebulaUpdaterReceiver extends BroadcastReceiver {

    public static final String ACTION_CANCEL = "app.nebulagram.messenger.UPDATE_CANCEL";
    public static final String ACTION_PAUSE = "app.nebulagram.messenger.UPDATE_PAUSE";
    public static final String ACTION_RESUME = "app.nebulagram.messenger.UPDATE_RESUME";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) return;
        final String action = intent.getAction();
        try {
            if (ACTION_CANCEL.equals(action)) {
                NebulaUpdater.cancelDownload(context, 0);
            } else if (ACTION_PAUSE.equals(action)) {
                NebulaUpdater.pauseDownload();
            } else if (ACTION_RESUME.equals(action)) {
                NebulaUpdater.resumeDownload(context.getApplicationContext());
            }
        } catch (Throwable ignore) {}
    }
}

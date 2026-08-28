/*
 * Copyright github.com/arsLan4k1390, 2022-2026.
 * Licensed under GNU GPL v2 or later. See LICENSE.
 */

package app.nebulagram.messenger.utils;

import app.nebulagram.messenger.NebulaConfig;

public final class NebulaAudioEnhance {

    private NebulaAudioEnhance() {}

    public static int getAudioSource() {
        return NebulaConfig.getMediaRecorderAudioSource();
    }

}

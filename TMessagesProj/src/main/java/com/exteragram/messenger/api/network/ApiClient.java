package com.exteragram.messenger.api.network;

import com.exteragram.messenger.api.dto.BadgeDTO;
import com.exteragram.messenger.api.dto.ProfileDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ApiClient {

    private ApiClient() {}

    public static List<ProfileDTO> getAllProfiles() {
        List<app.nebulagram.messenger.api.dto.ProfileDTO> real =
                app.nebulagram.messenger.api.network.ApiClient.getAllProfiles();
        List<ProfileDTO> out = new ArrayList<>(real != null ? real.size() : 0);
        if (real != null) {
            for (app.nebulagram.messenger.api.dto.ProfileDTO p : real) {
                out.add(ProfileDTO.fromReal(p));
            }
        }
        return out;
    }

    public static Map<Long, BadgeDTO> getNebulaBadges() {
        Map<Long, app.nebulagram.messenger.api.dto.BadgeDTO> real =
                app.nebulagram.messenger.api.network.ApiClient.getNebulaBadges();
        Map<Long, BadgeDTO> out = new HashMap<>(real != null ? real.size() : 0);
        if (real != null) {
            for (Map.Entry<Long, app.nebulagram.messenger.api.dto.BadgeDTO> e : real.entrySet()) {
                out.put(e.getKey(), BadgeDTO.fromReal(e.getValue()));
            }
        }
        return out;
    }
}

package org.smartregister.chw.core.shadows;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.smartregister.chw.core.repository.CommunityResponderRepository;

@Implements(CommunityResponderRepository.class)
public class CommunityResponderRepositoryShadowHelper {
    private static long respondersCount;

    public static void setRespondersCount(long count) {
        respondersCount = count;
    }

    @Implementation
    public long getRespondersCount() {
        return respondersCount;
    }
}

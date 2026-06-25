package io.github.jason13official.hermetica.impl.common.magic.research;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.resources.ResourceLocation;

/// Player's can only have a single research session at a time :/
public class ResearchSessionManager {

  /// required research as a concept is implemented as named research mapped to
  /// the amount of points required to complete that research
  private static final Map<UUID, Map<ResourceLocation, Integer>> ACTIVE_SESSIONS = new ConcurrentHashMap<>();

  public static void clearAllSessions() {

    ACTIVE_SESSIONS.clear();
  }

  public static Map<UUID, Map<ResourceLocation, Integer>> getSessions() {

    return ACTIVE_SESSIONS;
  }
}

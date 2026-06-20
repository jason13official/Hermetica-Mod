package io.github.jason13official.hermetica.impl.common.world.level.magic.ambient;

import com.mojang.logging.LogUtils;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;

public class MagicLevelManager {

  private static final Logger LOG = LogUtils.getLogger();

  public static ConcurrentHashMap<ResourceKey<Level>, MagicLevel> MAGIC_LEVELS = new ConcurrentHashMap<>();
  public static ConcurrentHashMap<ResourceKey<Level>, MagicLevelThread> MAGIC_LEVEL_THREADS = new ConcurrentHashMap<>();
  public static ConcurrentHashMap<ResourceKey<Level>, CopyOnWriteArrayList<ChunkPos>> DIRTY_CHUNKS = new ConcurrentHashMap<>();

  public static void createThreadForDimension(ResourceKey<Level> dimension) {
    if (!MAGIC_LEVEL_THREADS.containsKey(dimension) && getMagicLevel(dimension) != null) {

      LOG.info("Spinning up new magic level thread for {}", dimension.location());

      MagicLevelThread magicLevelThread = new MagicLevelThread(dimension);
      Thread thread = new Thread(magicLevelThread);
      thread.setDaemon(true); // non-user; does not prevent shutdown
      thread.setName("hermetica-magic-" + dimension.location());
      thread.start();
      MAGIC_LEVEL_THREADS.put(dimension, magicLevelThread);
    }
    // TODO gate this logic
//    else {
//      LOG.info("Attempted to create another magic level thread for {} but it already exists.", dimension.location());
//    }
  }

  public static MagicLevel getMagicLevel(ResourceKey<Level> dimension) {
    return MAGIC_LEVELS.get(dimension);
  }
}

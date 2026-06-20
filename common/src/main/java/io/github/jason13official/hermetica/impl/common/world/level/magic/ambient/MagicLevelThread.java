package io.github.jason13official.hermetica.impl.common.world.level.magic.ambient;

import com.mojang.logging.LogUtils;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;

/// one thread is created for each dimension (i.e. overworld, the_nether, the_end)
public class MagicLevelThread implements Runnable {

  private static final Logger LOG = LogUtils.getLogger();

  private final ResourceKey<Level> dimension;

  private volatile boolean stop = false;

  public MagicLevelThread(ResourceKey<Level> dimension) {
    this.dimension = dimension;
  }

  public void stop() {
    this.stop = true;
  }

  public static void markDirty(MagicLevelThread magicLevelThread, MagicChunk chunk) {

    ChunkPos chunkPos = chunk.getChunkPos();
    CopyOnWriteArrayList<ChunkPos> dirtyChunks = MagicLevelManager.DIRTY_CHUNKS
        .computeIfAbsent(magicLevelThread.dimension, k -> new CopyOnWriteArrayList<>());

    if (!dirtyChunks.contains(chunkPos)) {
      dirtyChunks.add(chunkPos);
    }
  }

  @Override
  public void run() {

    while (!this.stop) {

      MagicLevel magicLevel = MagicLevelManager.getMagicLevel(this.dimension);
      if (magicLevel == null) {
        LOG.info("MagicLevel gone for dimension {}, stopping thread", this.dimension.location());
        this.stop();
        break;
      }

      long startTime = System.currentTimeMillis();

      for (MagicChunk magicChunk : magicLevel.getMagicChunks().values()) {
        MagicLevelAndChunkOperation.process(this, magicLevel, magicChunk);
      }

      long executionTime = System.currentTimeMillis() - startTime;

      try {
        if (executionTime > 1000L) {
          LOG.warn("MAGIC LEVEL TICKS TAKING {} ms LONGER THAN NORMAL IN DIMENSION {}", executionTime - 1000L, this.dimension.location());
        }
        Thread.sleep(Math.max(1L, 1000L - executionTime));
      } catch (InterruptedException ignored) {
      }
    }

    LOG.info("Stopping MagicLevel thread for dimension {}", this.dimension.location());
    MagicLevelManager.MAGIC_LEVEL_THREADS.remove(this.dimension);
  }
}

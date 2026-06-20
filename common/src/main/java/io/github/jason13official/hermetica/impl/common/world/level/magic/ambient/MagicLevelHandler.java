package io.github.jason13official.hermetica.impl.common.world.level.magic.ambient;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.slf4j.Logger;

public class MagicLevelHandler {
  
  private static final Logger LOG = LogUtils.getLogger();

  protected static short generateBase(RandomSource random) {
    float noise = (float) (1.0D + random.nextGaussian() * 0.1D);
    return (short) Math.clamp((int) (200 * noise), 0, 500);
  }

  protected static void addMagicLevel(ResourceKey<Level> dimension) {
    if (!MagicLevelManager.MAGIC_LEVELS.containsKey(dimension)) {
      MagicLevelManager.MAGIC_LEVELS.put(dimension, new MagicLevel(dimension));
      LOG.info("Creating MagicLevel for dimension {}", dimension.location());
    }
  }

  protected static void removeMagicLevel(ResourceKey<Level> dimension) {
    MagicLevelManager.MAGIC_LEVELS.remove(dimension);
    MagicLevelManager.DIRTY_CHUNKS.remove(dimension);
    MagicLevelThread magicLevelThread = MagicLevelManager.MAGIC_LEVEL_THREADS.remove(dimension);
    if (magicLevelThread != null) {
      magicLevelThread.stop();
    }
    LOG.info("Removed MagicLevel for dimension {}", dimension.location());
  }

  protected static void addMagicChunk(ResourceKey<Level> dimension, ChunkAccess chunk, short base, float vis, float flux) {
    MagicLevel magicLevel = MagicLevelManager.MAGIC_LEVELS.get(dimension);
    if (magicLevel == null) {
      addMagicLevel(dimension);
      magicLevel = MagicLevelManager.MAGIC_LEVELS.get(dimension);
    }
    magicLevel.getMagicChunks().put(chunk.getPos(), new MagicChunk(chunk, base, vis, flux));
  }

  protected static void removeMagicChunk(ResourceKey<Level> dimension, int x, int z) {
    MagicLevel magicLevel = MagicLevelManager.MAGIC_LEVELS.get(dimension);
    if (magicLevel != null) {
      magicLevel.getMagicChunks().remove(new ChunkPos(x, z));
    }
  }
}

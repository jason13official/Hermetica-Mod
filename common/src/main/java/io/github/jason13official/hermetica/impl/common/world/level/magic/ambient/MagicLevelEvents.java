package io.github.jason13official.hermetica.impl.common.world.level.magic.ambient;

import io.github.jason13official.hermetica.platform.Services;
import java.util.HashMap;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;

public class MagicLevelEvents {

  public static final HashMap<ResourceKey<Level>, Integer> TICKS_BY_DIMENSION = new HashMap<>();
  
  public static void onServerLevelLoad(ServerLevel serverLevel) {

    MagicLevelHandler.addMagicLevel(serverLevel.dimension());
  }

  public static void onServerLevelUnload(ServerLevel serverLevel) {

    MagicLevelEvents.TICKS_BY_DIMENSION.remove(serverLevel.dimension());
    MagicLevelHandler.removeMagicLevel(serverLevel.dimension());
  }
  
  public static void onServerLevelTickStart(ServerLevel serverLevel) {

    ResourceKey<Level> dimension = serverLevel.dimension();

    // Start magicLevelThread once vis serverLevel is populated.
    MagicLevelManager.createThreadForDimension(dimension);

    // Every 20 ticks: flush dirty chunks -> sync attachment + mark unsaved.
    int ticks = MagicLevelEvents.TICKS_BY_DIMENSION.getOrDefault(dimension, 0) + 1;
    MagicLevelEvents.TICKS_BY_DIMENSION.put(dimension, ticks);

    if (ticks % 20 == 0) {
      MagicLevelCleanup.flushDirtyChunks(dimension);
    }
  }

  public static void onServerChunkLoad(ServerLevel serverLevel, LevelChunk levelChunk) {

    ResourceKey<Level> dimension = serverLevel.dimension();
    MagicChunkData magicChunkData = Services.magicLevel().getMagicChunk(levelChunk);
    if (magicChunkData != null && magicChunkData.base() > 0) {

      // data was already attached to this chunk, and it's base is greater than 0
      // begin tracking via magic level manager

      MagicLevelHandler.addMagicChunk(dimension, levelChunk, magicChunkData.base(), magicChunkData.thaum(), magicChunkData.vorp());
    } else {

      // else no data attached to chunk or data is missing/corrupted data
      // generate deterministic magic from chunk position

      short base = MagicLevelHandler.generateBase(RandomSource.create(ChunkPos.asLong(levelChunk.getPos().x, levelChunk.getPos().z)));

      Services.magicLevel().setMagicChunk(levelChunk, new MagicChunkData(base, base, 0F));
      levelChunk.setUnsaved(true);

      MagicLevelHandler.addMagicChunk(dimension, levelChunk, base, base, 0F);
    }
  }

  public static void onServerChunkUnload(ServerLevel serverLevel, ChunkPos chunkPos) {

    MagicLevelHandler.removeMagicChunk(serverLevel.dimension(), chunkPos.x, chunkPos.z);
  }
}

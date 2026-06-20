package io.github.jason13official.hermetica.impl.common.world.level.magic.ambient;

import io.github.jason13official.hermetica.platform.Services;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;

public class MagicLevelCleanup {

  public static void flushDirtyChunks(ResourceKey<Level> dimension) {

    CopyOnWriteArrayList<ChunkPos> dirtyChunks = MagicLevelManager.DIRTY_CHUNKS.get(dimension);

    if (dirtyChunks == null || dirtyChunks.isEmpty()) {
      return;
    }

    MagicLevel magicLevel = MagicLevelManager.getMagicLevel(dimension);
    for (ChunkPos chunkPos : new ArrayList<>(dirtyChunks)) {
      MagicChunk magicChunk = magicLevel != null ? magicLevel.getMagicChunk(chunkPos) : null;
      if (magicChunk != null && magicChunk.getChunkReference().get() instanceof LevelChunk chunk) {
        Services.magicLevel().setMagicChunk(chunk, new MagicChunkData(magicChunk.getBase(), magicChunk.getThaum(), magicChunk.getVorp()));
        chunk.setUnsaved(true);
      }
    }

    dirtyChunks.clear();
  }
}

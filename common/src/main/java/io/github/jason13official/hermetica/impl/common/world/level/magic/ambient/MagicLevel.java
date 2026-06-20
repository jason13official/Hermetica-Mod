package io.github.jason13official.hermetica.impl.common.world.level.magic.ambient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

public class MagicLevel {

  private final ResourceKey<Level> dimension;

  private final Map<ChunkPos, MagicChunk> magicChunks = new ConcurrentHashMap<>();

  public MagicLevel(ResourceKey<Level> dimension) {
    this.dimension = dimension;
  }

  public ResourceKey<Level> getDimension() {
    return dimension;
  }

  public Map<ChunkPos, MagicChunk> getMagicChunks() {
    return magicChunks;
  }

  public MagicChunk getMagicChunk(int x, int z) {
    return this.getMagicChunk(new ChunkPos(x, z));
  }

  public MagicChunk getMagicChunk(ChunkPos chunkPos) {
    return this.magicChunks.get(chunkPos);
  }
}

package io.github.jason13official.hermetica.platform.services;

import io.github.jason13official.hermetica.impl.common.world.level.magic.ambient.MagicChunkData;
import net.minecraft.world.level.chunk.ChunkAccess;

public interface IMagicLevelHelper {

  MagicChunkData getMagicChunk(ChunkAccess chunkAccess);

  void setMagicChunk(ChunkAccess chunkAccess, MagicChunkData magicChunkData);

}

package io.github.jason13official.hermetica.platform;

import io.github.jason13official.hermetica.fabric.HermeticaDataAttachments;
import io.github.jason13official.hermetica.impl.common.world.level.magic.ambient.MagicChunkData;
import io.github.jason13official.hermetica.platform.services.IMagicLevelHelper;
import net.minecraft.world.level.chunk.ChunkAccess;

public class FabricMagicLevelHelper implements IMagicLevelHelper {

  @Override
  public MagicChunkData getMagicChunk(ChunkAccess chunkAccess) {

    return chunkAccess.getAttachedOrElse(HermeticaDataAttachments.MAGIC_CHUNK_DATA, null);
  }

  @Override
  public void setMagicChunk(ChunkAccess chunkAccess, MagicChunkData magicChunkData) {

    chunkAccess.setAttached(HermeticaDataAttachments.MAGIC_CHUNK_DATA, magicChunkData);
  }
}

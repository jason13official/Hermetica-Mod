package io.github.jason13official.hermetica.platform;

import io.github.jason13official.hermetica.impl.common.world.level.magic.ambient.MagicChunkData;
import io.github.jason13official.hermetica.neoforge.HermeticaDataAttachments;
import io.github.jason13official.hermetica.platform.services.IMagicLevelHelper;
import net.minecraft.world.level.chunk.ChunkAccess;

public class NeoForgeMagicLevelHelper implements IMagicLevelHelper {

  @Override
  public MagicChunkData getMagicChunk(ChunkAccess chunkAccess) {

    return chunkAccess.getData(HermeticaDataAttachments.MAGIC_CHUNK_DATA);
  }

  @Override
  public void setMagicChunk(ChunkAccess chunkAccess, MagicChunkData magicChunkData) {

    chunkAccess.setData(HermeticaDataAttachments.MAGIC_CHUNK_DATA, magicChunkData);
  }
}

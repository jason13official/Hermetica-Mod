package io.github.jason13official.hermetica.fabric;

import io.github.jason13official.hermetica.Hermetica;
import io.github.jason13official.hermetica.impl.common.world.level.magic.ambient.MagicChunkData;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;

public class HermeticaDataAttachments {

  public static AttachmentType<MagicChunkData> MAGIC_CHUNK_DATA;

  public static void register() {
    MAGIC_CHUNK_DATA = AttachmentRegistry.createPersistent(Hermetica.identifier("magic_chunk_data"), MagicChunkData.CODEC);
  }
}

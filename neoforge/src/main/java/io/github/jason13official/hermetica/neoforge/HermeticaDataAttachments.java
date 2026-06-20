package io.github.jason13official.hermetica.neoforge;

import io.github.jason13official.hermetica.Hermetica;
import io.github.jason13official.hermetica.impl.common.world.level.magic.ambient.MagicChunkData;
import java.util.function.BiConsumer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.attachment.AttachmentType;

public class HermeticaDataAttachments {

  public static AttachmentType<MagicChunkData> MAGIC_CHUNK_DATA;

  public static void register(BiConsumer<AttachmentType<?>, ResourceLocation> consumer) {

    MAGIC_CHUNK_DATA = AttachmentType.builder(() -> MagicChunkData.DEFAULT).serialize(MagicChunkData.CODEC).build();
    consumer.accept(MAGIC_CHUNK_DATA, Hermetica.identifier("magic_chunk_data"));
  }
}

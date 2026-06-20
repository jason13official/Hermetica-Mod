package io.github.jason13official.hermetica.impl.common.world.level.magic.ambient;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record MagicChunkData(short base, float thaum, float vorp) {

  public static final MagicChunkData DEFAULT = new MagicChunkData((short) 0, 0F, 0F);

  public static final MapCodec<MagicChunkData> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
      instance.group(
          Codec.SHORT.fieldOf("base").forGetter(MagicChunkData::base),
          Codec.FLOAT.fieldOf("thaum").forGetter(MagicChunkData::thaum),
          Codec.FLOAT.fieldOf("vorp").forGetter(MagicChunkData::vorp)
      ).apply(instance, MagicChunkData::new));

  public static final Codec<MagicChunkData> CODEC = MAP_CODEC.codec();
}

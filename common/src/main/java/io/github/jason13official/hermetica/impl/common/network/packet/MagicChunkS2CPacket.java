package io.github.jason13official.hermetica.impl.common.network.packet;

import io.github.jason13official.hermetica.Hermetica;
import io.github.jason13official.hermetica.impl.client.gui.HermeticaHUD;
import io.github.jason13official.hermetica.impl.common.world.level.magic.ambient.MagicChunk;
import io.github.jason13official.hermetica.impl.common.world.level.magic.ambient.MagicChunkData;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

/// Used to update [HermeticaHUD] with data from the server
public record MagicChunkS2CPacket(short base, float vis, float flux) implements CustomPacketPayload {

  public static final Type<MagicChunkS2CPacket> TYPE = new Type<>(Hermetica.identifier("magic_chunk_data"));

  public static final StreamCodec<ByteBuf, MagicChunkS2CPacket> STREAM_CODEC = StreamCodec.of(MagicChunkS2CPacket::write, MagicChunkS2CPacket::read);

  /// functionally the same as our standard setter/getter methods
  public static final StreamCodec<ByteBuf, MagicChunkS2CPacket> ALT_CODEC = StreamCodec.composite(
      ByteBufCodecs.SHORT, MagicChunkS2CPacket::base,
      ByteBufCodecs.FLOAT, MagicChunkS2CPacket::vis,
      ByteBufCodecs.FLOAT, MagicChunkS2CPacket::flux,
      MagicChunkS2CPacket::new);

  public MagicChunkS2CPacket(MagicChunkData magicChunkData) {
    this(magicChunkData.base(), magicChunkData.thaum(), magicChunkData.vorp());
  }

  private static MagicChunkS2CPacket read(ByteBuf byteBuf) {

    short base = byteBuf.readShort();
    float vis = byteBuf.readFloat();
    float flux = byteBuf.readFloat();

    return new MagicChunkS2CPacket(base, vis, flux);
  }

  private static void write(ByteBuf byteBuf, MagicChunkS2CPacket packet) {
    byteBuf.writeShort(packet.base());
    byteBuf.writeFloat(packet.vis());
    byteBuf.writeFloat(packet.flux());
  }

  public static void handle(MagicChunkS2CPacket packet) {
    HermeticaHUD.lastChunkData = new MagicChunkData(packet.base, packet.vis, packet.flux);
  }

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }
}

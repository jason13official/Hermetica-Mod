package io.github.jason13official.hermetica.impl.common.world.level.magic.ambient;

import java.lang.ref.WeakReference;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkAccess;

public class MagicChunk {

  private final WeakReference<ChunkAccess> chunk;

  private final ChunkPos chunkPos;

  /// the starting point for magic in a chunk, and the highest level it will naturally regenerate to
  private final short base;

  /// positive useful magic
  private float thaum;

  /// negative corrupting magic
  private float vorp;

  public MagicChunk(ChunkAccess chunk, short base, float thaum, float vorp) {
    this.chunk = new WeakReference<>(chunk);
    this.chunkPos = chunk.getPos();

    this.base = base;
    this.thaum = thaum;
    this.vorp = vorp;
  }

  public WeakReference<ChunkAccess> getChunkReference() {
    return chunk;
  }

  public ChunkPos getChunkPos() {
    return chunkPos;
  }

  public short getBase() {
    return base;
  }

  public float getThaum() {
    return thaum;
  }

  public float getVorp() {
    return vorp;
  }

  /// clamped between 0.0f and 32768.0f (inclusive)
  public void setThaum(float thaum) {
    this.thaum = Mth.clamp(thaum, 0.0f, (float) (Short.MAX_VALUE + 1));
  }

  /// clamped between 0.0f and 32768.0f (inclusive)
  public void setVorp(float vorp) {
    this.vorp = Mth.clamp(vorp, 0.0f, (float) (Short.MAX_VALUE + 1));
  }
}

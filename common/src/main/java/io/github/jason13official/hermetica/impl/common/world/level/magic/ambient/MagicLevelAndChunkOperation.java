package io.github.jason13official.hermetica.impl.common.world.level.magic.ambient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.core.Direction;

public class MagicLevelAndChunkOperation {

  /// inherently non-deterministic (maybe switch to using the level seed in the future),
  /// and based on the time this class itself is loaded.
  private static final Random RANDOM = new Random(System.currentTimeMillis());

  /// spread thaum/vorp or process regeneration/oversaturation
  public static void process(MagicLevelThread magicLevelThread, MagicLevel magicLevel, MagicChunk magicChunk) {

    // North, South, East, West (then randomize order)
    List<Integer> directions = Arrays.asList(0, 1, 2, 3);
    Collections.shuffle(directions, RANDOM);

    // whether the chunk should be marked as changed
    AtomicBoolean dirtyMarked = new AtomicBoolean(false);

    // the raw position of the chunk
    int x = magicChunk.getChunkPos().x;
    int z = magicChunk.getChunkPos().z;
    
    // the raw values of the chunk
    float currentBase = magicChunk.getBase();
    float currentThaum = magicChunk.getThaum();
    float currentVorp = magicChunk.getVorp();

    // placeholders for neighbor chunks with the lowest thaum and vorp amounts
    MagicChunk lowThaumChunk = null;
    MagicChunk lowVorpChunk = null;

    float lowestThaum = Float.MAX_VALUE;
    float lowestVorp = Float.MAX_VALUE;
    
    for (Integer a : directions) {
      
      Direction dir = Direction.from2DDataValue(a);
      MagicChunk neighbor = magicLevel.getMagicChunk(x + dir.getStepX(), z + dir.getStepZ());
      
      if (neighbor == null) continue;
      
      boolean isLowestThaum = (lowThaumChunk == null || lowestThaum > neighbor.getThaum());
      boolean neighborTotalUnderBase = neighbor.getThaum() + neighbor.getVorp() < neighbor.getBase();
      if (isLowestThaum && neighborTotalUnderBase) {
        lowThaumChunk = neighbor;
        lowestThaum = neighbor.getThaum();
      }
      if (lowVorpChunk == null || lowestVorp > neighbor.getVorp()) {
        lowVorpChunk = neighbor;
        lowestVorp = neighbor.getVorp();
      }
    }

    // spread thaum to neighboring low-thaum chunk
    if (lowThaumChunk != null && lowestThaum < currentThaum && lowestThaum / currentThaum < 0.75F) {
      float inc = Math.min(currentThaum - lowestThaum, 1.0F);
      currentThaum -= inc;
      lowThaumChunk.setThaum(lowestThaum + inc);
      dirtyMarked.set(true);
      MagicLevelThread.markDirty(magicLevelThread, lowThaumChunk);
    }

    // spread vorp to neighboring low-vorp chunk
    if (lowVorpChunk != null
        && currentVorp > Math.max(5.0F, magicChunk.getBase() / 10.0F)
        && lowestVorp < currentVorp / 1.75F) {
      float inc = Math.min(currentVorp - lowestVorp, 1.0F);
      currentVorp -= inc;
      lowVorpChunk.setVorp(lowestVorp + inc);
      dirtyMarked.set(true);
      MagicLevelThread.markDirty(magicLevelThread, lowVorpChunk);
    }

    regenerate(magicLevelThread, magicChunk, currentThaum, currentVorp, currentBase, dirtyMarked.get());
  }

  private static void regenerate(MagicLevelThread t, MagicChunk chunk, float thaum, float vorp, float base, boolean dirty) {
    if (thaum + vorp < base) {

      // our thaum and vorp are smaller than our initial base value
      // regenerate a small portion
      // (or if we would naturally regenerate a small amount, do that now)

      float increaseByAmount = Math.min(base - (thaum + vorp), 0.5f);
      thaum += increaseByAmount;
      dirty = true;
    } else if (thaum > base * 1.25F && RANDOM.nextFloat() < 0.1F) {

      // we are oversaturated with thaum,
      // convert some to vorp

      vorp += 0.5f;
      thaum -= 0.5f;
      dirty = true;
    } else if (thaum <= base * 0.1F && thaum >= vorp && RANDOM.nextFloat() < 0.1F) {

      // thaum under 10% of base AND thaum greater than current vorp amount
      // saturate vorp

      vorp += 0.5f;
      dirty = true;
    }

    if (dirty) {

      // chunk was pre-marked as dirty, update values and mark it fully for saving

      chunk.setThaum(thaum);
      chunk.setVorp(vorp);
      MagicLevelThread.markDirty(t, chunk);
    }
  }
}

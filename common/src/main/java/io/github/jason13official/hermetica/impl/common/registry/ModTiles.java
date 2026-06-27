package io.github.jason13official.hermetica.impl.common.registry;

import io.github.jason13official.hermetica.Hermetica;
import io.github.jason13official.hermetica.impl.common.block.tile.AcidicCauldronTile;
import java.util.function.BiConsumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModTiles {

  public static BlockEntityType<AcidicCauldronTile> ACIDIC_CAULDRON;

  public static void register(BiConsumer<BlockEntityType<?>, ResourceLocation> consumer) {

    ACIDIC_CAULDRON = BlockEntityType.Builder.of(AcidicCauldronTile::new, ModBlocks.ACIDIC_CAULDRON).build(null);
    consumer.accept(ACIDIC_CAULDRON, Hermetica.identifier("acidic_cauldron"));
  }
}

package io.github.jason13official.hermetica.impl.common.registry;

import io.github.jason13official.hermetica.Hermetica;
import io.github.jason13official.hermetica.impl.common.block.TowerHeartBlock;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class ModBlocks {

  public static Block TOWER_HEART;
  public static Block VESTIGIAL_TOWER_HEART;

  public static Block AURA_NODE;

  public static void register(BiConsumer<Block, ResourceLocation> consumer) {

    TOWER_HEART = construct("tower_heart", p -> new TowerHeartBlock(true, p), Properties.of(), consumer);
    VESTIGIAL_TOWER_HEART = construct("vestigial_tower_heart", p -> new TowerHeartBlock(false, p), Properties.of(), consumer);

    AURA_NODE = construct("aura_node", VanillaTransparentBlock::new, BlockBehaviour.Properties.of().noCollission().noOcclusion(), consumer);
  }

  private static Block construct(String name, Function<Properties, Block> constructor, Properties properties, BiConsumer<Block, ResourceLocation> consumer) {

    ResourceLocation id = Hermetica.identifier(name);
    Block block = constructor.apply(properties);
    consumer.accept(block, id);

    return block;
  }

  public static class VanillaTransparentBlock extends TransparentBlock {

    public VanillaTransparentBlock(Properties properties) {
      super(properties);
    }
  }
}

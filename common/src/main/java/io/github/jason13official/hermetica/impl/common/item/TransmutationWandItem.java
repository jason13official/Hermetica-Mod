package io.github.jason13official.hermetica.impl.common.item;

import io.github.jason13official.hermetica.impl.common.registry.ModBlockStateProperties;
import io.github.jason13official.hermetica.impl.common.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;

public class TransmutationWandItem extends Item {

  public TransmutationWandItem(Properties properties) {
    super(properties);
  }

  @Override
  public InteractionResult useOn(UseOnContext context) {

    Level level = context.getLevel();
    BlockPos blockPos = context.getClickedPos();
    BlockState blockState = level.getBlockState(blockPos);

    tryCauldronTransmutation(blockState, level, blockPos);

    return super.useOn(context);
  }

  private static void tryCauldronTransmutation(BlockState blockState, Level level, BlockPos blockPos) {
    BlockState acidicCauldron = ModBlocks.ACIDIC_CAULDRON.defaultBlockState();
    if (blockState.is(Blocks.CAULDRON)) {
      level.setBlockAndUpdate(blockPos, acidicCauldron);
    } else if (blockState.is(Blocks.WATER_CAULDRON) && blockState.hasProperty(LayeredCauldronBlock.LEVEL)) {
      int layeredCauldronContentLevel = blockState.getValue(LayeredCauldronBlock.LEVEL);
      acidicCauldron = acidicCauldron.setValue(ModBlockStateProperties.CAULDRON_ACID_LEVEL, layeredCauldronContentLevel);
      level.setBlockAndUpdate(blockPos, acidicCauldron);
    }
  }
}

package io.github.jason13official.hermetica.impl.common.block;

import com.mojang.serialization.MapCodec;
import io.github.jason13official.hermetica.impl.common.registry.ModBlockStateProperties;
import io.github.jason13official.hermetica.impl.common.world.level.magic.cauldron.AcidicCauldronInteraction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class AcidicCauldronBlock extends AbstractCauldronBlock {

  public static final MapCodec<AcidicCauldronBlock> CODEC = simpleCodec(AcidicCauldronBlock::new);

  public AcidicCauldronBlock(Properties properties) {
    super(properties, AcidicCauldronInteraction.ACID);

    this.registerDefaultState(this.stateDefinition.any().setValue(ModBlockStateProperties.CAULDRON_ACID_LEVEL, 0));
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {

    builder.add(ModBlockStateProperties.CAULDRON_ACID_LEVEL);
  }

  @Override
  protected MapCodec<? extends AbstractCauldronBlock> codec() {

    return CODEC;
  }

  @Override
  public boolean isFull(BlockState state) {

    return state.getValue(ModBlockStateProperties.CAULDRON_ACID_LEVEL) == 3;
  }

  @Override
  protected double getContentHeight(BlockState state) {

    int level = state.getValue(ModBlockStateProperties.CAULDRON_ACID_LEVEL);

    return level > 0 ? ((double) 6.0F + (double) level * (double) 3.0F) / (double) 16.0F : 0.0F;
  }

  @Override
  protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {

    process(level, pos, entity);
  }

  private static void process(Level level, BlockPos pos, Entity entity) {
    // server side and entity inside bounding box
    if (!level.isClientSide() && entity instanceof ItemEntity itemEntity) {
      itemEntity.discard();
    }
  }
}

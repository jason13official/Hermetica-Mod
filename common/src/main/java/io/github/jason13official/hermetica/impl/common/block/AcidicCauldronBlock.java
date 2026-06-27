package io.github.jason13official.hermetica.impl.common.block;

import com.mojang.serialization.MapCodec;
import io.github.jason13official.hermetica.impl.common.block.tile.AcidicCauldronTile;
import io.github.jason13official.hermetica.impl.common.registry.ModBlockStateProperties;
import io.github.jason13official.hermetica.impl.common.registry.ModTiles;
import io.github.jason13official.hermetica.impl.common.world.level.magic.cauldron.AcidicCauldronInteraction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.CommonColors;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class AcidicCauldronBlock extends AbstractCauldronBlock implements EntityBlock {

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
  protected boolean isRandomlyTicking(BlockState state) {
    return state.hasProperty(ModBlockStateProperties.CAULDRON_ACID_LEVEL)
        && state.getValue(ModBlockStateProperties.CAULDRON_ACID_LEVEL) == 0;
  }

  @Override
  protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
    if (state.hasProperty(ModBlockStateProperties.CAULDRON_ACID_LEVEL)
        && state.getValue(ModBlockStateProperties.CAULDRON_ACID_LEVEL) == 0) {
      if (level.getRandom().nextBoolean()) {
        level.setBlockAndUpdate(pos, state.setValue(ModBlockStateProperties.CAULDRON_ACID_LEVEL, 3));
      }
    }
  }

  @Override
  public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {

    if (level.getBlockState(pos).hasProperty(ModBlockStateProperties.CAULDRON_ACID_LEVEL)
        && level.getBlockState(pos).getValue(ModBlockStateProperties.CAULDRON_ACID_LEVEL) == 0) {
      return;
    }

    for (int i = 0; i < 4; i++) {
      double x = (double)pos.getX() + random.nextDouble();
      double y = (double)pos.getY() + random.nextDouble();
      double d = (double)pos.getZ() + random.nextDouble();
      double xd = ((double)random.nextFloat() - 0.5) * 0.5;
      double yd = ((double)random.nextFloat() - 0.5) * 0.5;
      double zd = ((double)random.nextFloat() - 0.5) * 0.5;
      int mod = random.nextInt(2) * 2 - 1;
      if (!level.getBlockState(pos.west()).is(this) && !level.getBlockState(pos.east()).is(this)) {
        x = (double)pos.getX() + 0.5 + 0.25 * (double)mod;
        xd = (double)(random.nextFloat() * 2.0F * (float)mod);
      } else {
        d = (double)pos.getZ() + 0.5 + 0.25 * (double)mod;
        zd = (double)(random.nextFloat() * 2.0F * (float)mod);
      }

      level.addParticle(new DustParticleOptions(DustParticleOptions.REDSTONE_PARTICLE_COLOR, 1.0f), x, y, d, xd, yd, zd);
    }
  }

  @Override
  protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {

    if (level.getBlockEntity(pos) instanceof AcidicCauldronTile cauldron) {
      cauldron.entityInside(level, pos, entity);
    }
  }
  
  // Tile stuff -----------------------------------------------------------------------------

  @Override
  public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
    return new AcidicCauldronTile(blockPos, blockState);
  }

  @Override
  public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
    return createCauldronTicker(level, blockEntityType, ModTiles.ACIDIC_CAULDRON);
  }

  protected static <T extends BlockEntity> BlockEntityTicker<T> createCauldronTicker(Level level, BlockEntityType<T> serverType, BlockEntityType<? extends AcidicCauldronTile> clientType) {
    return level.isClientSide ? null : createTickerHelper(serverType, clientType, AcidicCauldronTile::serverTick);
  }

  @SuppressWarnings("all")
  protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> serverType, BlockEntityType<E> clientType, BlockEntityTicker<? super E> ticker) {
    return clientType == serverType ? (BlockEntityTicker<A>) ticker : null;
  }
}

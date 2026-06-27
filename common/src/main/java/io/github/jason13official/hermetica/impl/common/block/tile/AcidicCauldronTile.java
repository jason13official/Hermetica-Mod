package io.github.jason13official.hermetica.impl.common.block.tile;

import io.github.jason13official.hermetica.Constants;
import io.github.jason13official.hermetica.impl.common.registry.ModTiles;
import io.github.jason13official.hermetica.impl.common.world.level.magic.aspect.Aspect;
import io.github.jason13official.hermetica.impl.common.world.level.magic.aspect.Aspects;
import io.github.jason13official.hermetica.impl.common.world.level.magic.cauldron.AcidicCauldronInteraction;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AcidicCauldronTile extends BlockEntity {

  private final Map<Aspect, Integer> countByAspect = new HashMap<>();

  public AcidicCauldronTile(BlockPos pos, BlockState blockState) {
    super(ModTiles.ACIDIC_CAULDRON, pos, blockState);
  }

  public static void serverTick(Level level, BlockPos pos, BlockState state, AcidicCauldronTile cauldron) {

  }

  public void entityInside(Level level, BlockPos pos, Entity entity) {

    if (!level.isClientSide() && entity instanceof ItemEntity itemEntity) {
      Item rawItem = itemEntity.getItem().getItem();
      if (AcidicCauldronInteraction.ASPECT_BY_ITEM.containsKey(rawItem)) {
        Aspect aspect = AcidicCauldronInteraction.ASPECT_BY_ITEM.get(rawItem);
        if (!this.countByAspect.containsKey(aspect)) {
          this.countByAspect.putIfAbsent(aspect, 1);
        } else {
          this.countByAspect.computeIfPresent(aspect, (aspect_, integer) -> ++integer);
        }
        level.blockEntityChanged(pos);
      }
      itemEntity.discard();
    }
  }

  @Override
  protected void saveAdditional(CompoundTag tag, Provider registries) {
    ListTag list = new ListTag();
    this.countByAspect.forEach((aspect, count) -> {
      CompoundTag entry = new CompoundTag();
      entry.putString("id", aspect.id().toString());
      entry.putInt("count", count);
      list.add(entry);
    });
    tag.put("count_by_aspect", list);
  }

  @Override
  protected void loadAdditional(CompoundTag tag, Provider registries) {
    this.countByAspect.clear();
    ListTag list = tag.getList("count_by_aspect", CompoundTag.TAG_COMPOUND);
    for (int i = 0; i < list.size(); i++) {
      CompoundTag entry = list.getCompound(i);
      ResourceLocation id = ResourceLocation.parse(entry.getString("id"));
      Aspect aspect = Aspects.get(id);
      if (aspect != null) {
        this.countByAspect.put(aspect, entry.getInt("count"));
      } else {
        Constants.LOG.warn("Skipping unknown aspect id in AcidicCauldronTile: {}", id);
      }
    }
  }
}

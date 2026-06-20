package io.github.jason13official.hermetica.impl.common.block;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

public class TowerHeartBlock extends Block {

  private final boolean isPrimary;

  public TowerHeartBlock(boolean isPrimary, Properties properties) {
    super(properties);

    this.isPrimary = isPrimary;
  }

  public boolean isPrimary() {
    return isPrimary;
  }

  @Override
  public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list,
      TooltipFlag tooltipFlag) {
    super.appendHoverText(stack, context, list, tooltipFlag);

    if (this.isPrimary()) {
      list.add(Component.literal("The Heart of your tower."));
    } else {
      list.add(Component.literal("Expand the Heart as you see fit."));
    }
  }
}

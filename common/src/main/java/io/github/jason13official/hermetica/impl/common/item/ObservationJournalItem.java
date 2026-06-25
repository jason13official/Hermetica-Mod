package io.github.jason13official.hermetica.impl.common.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ObservationJournalItem extends Item {

  public ObservationJournalItem(Properties properties) {
    super(properties);
  }

  @Override
  public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
    if (entity instanceof Player player) {
      if (!player.getTags().contains("HermeticaFirstJournal")) {
        player.addTag("HermeticaFirstJournal");
      }
    }
  }
}

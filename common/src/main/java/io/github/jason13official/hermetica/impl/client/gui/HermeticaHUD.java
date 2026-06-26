package io.github.jason13official.hermetica.impl.client.gui;

import io.github.jason13official.hermetica.impl.common.registry.ModBlocks;
import io.github.jason13official.hermetica.impl.common.registry.ModItems;
import io.github.jason13official.hermetica.impl.common.world.level.magic.ambient.MagicChunkData;
import io.github.jason13official.hermetica.platform.Services;
import java.util.function.Supplier;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.BlockHitResult;

public class HermeticaHUD {

  private static final Supplier<ItemStack> DISPLAY_WRITABLE_BOOK = () -> new ItemStack(Items.WRITABLE_BOOK);
  private static final Supplier<ItemStack> DISPLAY_OBSERVATION_JOURNAL = () -> new ItemStack(ModItems.OBSERVATION_JOURNAL);

  public static MagicChunkData lastChunkData = MagicChunkData.DEFAULT;

  public static void renderOverlay(GuiGraphics guiGraphics, DeltaTracker partialTick) {

    Minecraft mc = Minecraft.getInstance();

    if (mc.player == null || mc.level == null) {
      return;
    }

    if (mc.options.hideGui) {
      return;
    }

    if (Services.PLATFORM.isDevelopmentEnvironment()) {
      renderDevHudOverlay(guiGraphics, mc);
    }

    // ignore non block hit results
    if (mc.hitResult instanceof BlockHitResult bhr) {

      // are we looking at an aura node
      boolean playerLookingAtAuraNode = mc.level.getBlockState(bhr.getBlockPos()).is(ModBlocks.AURA_NODE);

      // end the if statement early, if we're not looking at an aura node,
      // otherwise check against player tag
      if (!playerLookingAtAuraNode) return;
      int centerX = (guiGraphics.guiWidth() - 16) / 2;
      int centerY = (guiGraphics.guiHeight() - 16) / 2;
      if (!mc.player.getTags().contains("HermeticaFirstJournal")) {
        // positive x to the right, positive y down
        guiGraphics.renderFakeItem(DISPLAY_WRITABLE_BOOK.get(), centerX - 16, centerY + 16); // down left from crosshair
        guiGraphics.renderFakeItem(DISPLAY_OBSERVATION_JOURNAL.get(), centerX + 16, centerY + 16); // down right from crosshair
      } else if (!mc.player.getTags().contains("ObservedAuraNode")) {
        guiGraphics.renderFakeItem(DISPLAY_OBSERVATION_JOURNAL.get(), centerX, centerY + 16); // down from crosshair
      }
    }
  }

  private static void renderDevHudOverlay(GuiGraphics guiGraphics, Minecraft mc) {
    guiGraphics.drawString(mc.font, "HUD OVERLAY FROM HERMETICA", 5, 5, 0xAAFFFFFF);
    if (lastChunkData != null) {
      guiGraphics.drawString(mc.font, "Chunk Magic Base: " + lastChunkData.base(), 5, 15, 0xAAFFFFFF);
      guiGraphics.drawString(mc.font, "Chunk Magic Thaum: " + lastChunkData.thaum(), 5, 25, 0xAAFFFFFF);
      guiGraphics.drawString(mc.font, "Chunk Magic Vorp: " + lastChunkData.vorp(), 5, 35, 0xAAFFFFFF);
    }
  }
}

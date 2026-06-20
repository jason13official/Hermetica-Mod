package io.github.jason13official.hermetica.impl.client.gui;

import io.github.jason13official.hermetica.impl.common.world.level.magic.ambient.MagicChunkData;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class HermeticaHUD {

  public static MagicChunkData lastChunkData = MagicChunkData.DEFAULT;

  public static void renderOverlay(GuiGraphics guiGraphics, DeltaTracker partialTick) {

    Minecraft mc = Minecraft.getInstance();

    if (mc.options.hideGui) {
      return;
    }

    guiGraphics.drawString(mc.font, "HUD OVERLAY FROM HERMETICA", 5, 5, 0xAAFFFFFF);
    if (lastChunkData != null) {
      guiGraphics.drawString(mc.font, "Chunk Magic Base: " + lastChunkData.base(), 5, 15, 0xAAFFFFFF);
      guiGraphics.drawString(mc.font, "Chunk Magic Thaum: " + lastChunkData.thaum(), 5, 25, 0xAAFFFFFF);
      guiGraphics.drawString(mc.font, "Chunk Magic Vorp: " + lastChunkData.vorp(), 5, 35, 0xAAFFFFFF);
    }
  }
}

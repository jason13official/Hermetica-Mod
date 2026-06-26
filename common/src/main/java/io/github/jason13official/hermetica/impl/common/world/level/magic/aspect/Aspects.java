package io.github.jason13official.hermetica.impl.common.world.level.magic.aspect;

import io.github.jason13official.hermetica.Hermetica;
import net.minecraft.resources.ResourceLocation;

// TODO our latin bases are terrible, might find an alternative
public class Aspects {

  // Primals
  public static final Aspect AIR = new Aspect(id("air"), "aspect.hermetica.air", "aspect.hermetica.aeris");
  public static final Aspect EARTH = new Aspect(id("earth"), "aspect.hermetica.earth", "aspect.hermetica.terrae");
  public static final Aspect WATER = new Aspect(id("water"), "aspect.hermetica.water", "aspect.hermetica.aquae");
  public static final Aspect FIRE = new Aspect(id("fire"), "aspect.hermetica.fire", "aspect.hermetica.ignis");
  public static final Aspect ORDER = new Aspect(id("order"), "aspect.hermetica.order", "aspect.hermetica.ordinis");
  public static final Aspect ENTROPY = new Aspect(id("entropy"), "aspect.hermetica.entropy", "aspect.hermetica.entropiae");

  // Tier 1
  public static final Aspect STORM = new Aspect(id("storm"), "aspect.hermetica.storm", "aspect.hermetica.tempestatis", AIR, WATER);
  public static final Aspect LAVA = new Aspect(id("lava"), "aspect.hermetica.lava", "aspect.hermetica.lavae", FIRE, EARTH);
  public static final Aspect STEAM = new Aspect(id("steam"), "aspect.hermetica.steam", "aspect.hermetica.vaporis", FIRE, WATER);
  public static final Aspect LIGHT = new Aspect(id("light"), "aspect.hermetica.light", "aspect.hermetica.lucis", FIRE, ORDER);
  public static final Aspect VOID = new Aspect(id("void"), "aspect.hermetica.void", "aspect.hermetica.vacui", ENTROPY, AIR);
  public static final Aspect DECAY = new Aspect(id("decay"), "aspect.hermetica.decay", "aspect.hermetica.putredinis", ENTROPY, EARTH);

  // Tier 2
  public static final Aspect LIFE = new Aspect(id("life"), "aspect.hermetica.life", "aspect.hermetica.vitae", WATER, ORDER);
  public static final Aspect MIND = new Aspect(id("mind"), "aspect.hermetica.mind", "aspect.hermetica.mentis", AIR, ORDER);
  public static final Aspect BLOOD = new Aspect(id("blood"), "aspect.hermetica.blood", "aspect.hermetica.sanguinis", FIRE, WATER, ENTROPY);
  public static final Aspect NIGHT = new Aspect(id("night"), "aspect.hermetica.night", "aspect.hermetica.noctis", ENTROPY, VOID);
  public static final Aspect STONE = new Aspect(id("stone"), "aspect.hermetica.stone", "aspect.hermetica.saxi", EARTH, ORDER);
  public static final Aspect BEAST = new Aspect(id("beast"), "aspect.hermetica.beast", "aspect.hermetica.bestiae", EARTH, ENTROPY);

  // Tier 3
  public static final Aspect SOUL = new Aspect(id("soul"), "aspect.hermetica.soul", "aspect.hermetica.animae", LIFE, ENTROPY);
  public static final Aspect DEATH = new Aspect(id("death"), "aspect.hermetica.death", "aspect.hermetica.mortis", LIFE, NIGHT);
  public static final Aspect SHADOW = new Aspect(id("shadow"), "aspect.hermetica.shadow", "aspect.hermetica.umbrae", NIGHT, MIND);
  public static final Aspect IRON = new Aspect(id("iron"), "aspect.hermetica.iron", "aspect.hermetica.ferri", STONE, FIRE);
  public static final Aspect CHANGE = new Aspect(id("change"), "aspect.hermetica.change", "aspect.hermetica.mutationis", ENTROPY, ORDER);
  public static final Aspect SEA = new Aspect(id("sea"), "aspect.hermetica.sea", "aspect.hermetica.maris", WATER, STORM);

  // Tier 4
  public static final Aspect GOLD = new Aspect(id("gold"), "aspect.hermetica.gold", "aspect.hermetica.auri", IRON, ORDER);
  public static final Aspect SPIRIT = new Aspect(id("spirit"), "aspect.hermetica.spirit", "aspect.hermetica.spiritus", SOUL, LIGHT);
  public static final Aspect UNDEATH = new Aspect(id("undeath"), "aspect.hermetica.undeath", "aspect.hermetica.mortis_novae", DEATH, CHANGE);
  public static final Aspect STAR = new Aspect(id("star"), "aspect.hermetica.star", "aspect.hermetica.stellae", LIGHT, VOID);
  public static final Aspect TIME = new Aspect(id("time"), "aspect.hermetica.time", "aspect.hermetica.temporis", CHANGE, ORDER);

  private static ResourceLocation id(String path) {
    return Hermetica.identifier(path);
  }
}

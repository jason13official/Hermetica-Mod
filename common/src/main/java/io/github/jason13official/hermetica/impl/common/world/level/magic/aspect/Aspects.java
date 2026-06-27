package io.github.jason13official.hermetica.impl.common.world.level.magic.aspect;

import io.github.jason13official.hermetica.Hermetica;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;

public class Aspects {

  private static final Map<ResourceLocation, Aspect> REGISTRY = new HashMap<>();

  public static final Aspect AIR = createAndRegister("air", "aspect.hermetica.air");
  public static final Aspect EARTH = createAndRegister("earth", "aspect.hermetica.earth");
  public static final Aspect WATER = createAndRegister("water", "aspect.hermetica.water");
  public static final Aspect FIRE = createAndRegister("fire", "aspect.hermetica.fire");

  public static final Aspect ORDER = createAndRegister("order", "aspect.hermetica.order");
  public static final Aspect ENTROPY = createAndRegister("entropy", "aspect.hermetica.entropy");
  public static final Aspect VOID = createAndRegister("void", "aspect.hermetica.void");
  public static final Aspect LIGHT = createAndRegister("light", "aspect.hermetica.light");
  public static final Aspect MOTION = createAndRegister("motion", "aspect.hermetica.motion");
  public static final Aspect COLD = createAndRegister("cold", "aspect.hermetica.cold");
  public static final Aspect CRYSTAL = createAndRegister("crystal", "aspect.hermetica.crystal");
  public static final Aspect METAL = createAndRegister("metal", "aspect.hermetica.metal");
  public static final Aspect LIFE = createAndRegister("life", "aspect.hermetica.life");
  public static final Aspect DEATH = createAndRegister("death", "aspect.hermetica.death");
  public static final Aspect ENERGY = createAndRegister("energy", "aspect.hermetica.energy");
  public static final Aspect EXCHANGE = createAndRegister("exchange", "aspect.hermetica.exchange");
  public static final Aspect MAGIC = createAndRegister("magic", "aspect.hermetica.magic");
  public static final Aspect AURA = createAndRegister("aura", "aspect.hermetica.aura");
  public static final Aspect ALCHEMY = createAndRegister("alchemy", "aspect.hermetica.alchemy");
  public static final Aspect FLUX = createAndRegister("flux", "aspect.hermetica.flux");
  public static final Aspect DARKNESS = createAndRegister("darkness", "aspect.hermetica.darkness");
  public static final Aspect ELDRITCH = createAndRegister("eldritch", "aspect.hermetica.eldritch");
  public static final Aspect FLIGHT = createAndRegister("flight", "aspect.hermetica.flight");
  public static final Aspect PLANT = createAndRegister("plant", "aspect.hermetica.plant");
  public static final Aspect TOOL = createAndRegister("tool", "aspect.hermetica.tool");
  public static final Aspect CRAFT = createAndRegister("craft", "aspect.hermetica.craft");
  public static final Aspect MECHANISM = createAndRegister("mechanism", "aspect.hermetica.mechanism");
  public static final Aspect TRAP = createAndRegister("trap", "aspect.hermetica.trap");
  public static final Aspect SOUL = createAndRegister("soul", "aspect.hermetica.soul");
  public static final Aspect MIND = createAndRegister("mind", "aspect.hermetica.mind");
  public static final Aspect SENSES = createAndRegister("senses", "aspect.hermetica.senses");
  public static final Aspect AVERSION = createAndRegister("aversion", "aspect.hermetica.aversion");
  public static final Aspect PROTECT = createAndRegister("protect", "aspect.hermetica.protect");
  public static final Aspect DESIRE = createAndRegister("desire", "aspect.hermetica.desire");
  public static final Aspect UNDEAD = createAndRegister("undead", "aspect.hermetica.undead");
  public static final Aspect BEAST = createAndRegister("beast", "aspect.hermetica.beast");
  public static final Aspect MAN = createAndRegister("man", "aspect.hermetica.man");

  public static Aspect get(ResourceLocation id) {
    return REGISTRY.get(id);
  }

  private static Aspect createAndRegister(String id, String translationKey, Aspect... components) {
    return createAndRegister(Hermetica.identifier(id), translationKey, components);
  }

  public static Aspect createAndRegister(ResourceLocation id, String translationKey, Aspect... components) {

    if (REGISTRY.containsKey(id)) {
      throw new IllegalStateException("Attempted to register duplicate or override Aspect with id " + id.toString());
    }

    Aspect aspect = new Aspect(id, translationKey, components);
    REGISTRY.put(id, aspect);
    return aspect;
  }
}

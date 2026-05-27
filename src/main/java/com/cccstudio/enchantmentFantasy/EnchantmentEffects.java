package com.cccstudio.enchantmentFantasy;

import com.cccstudio.enchantmentFantasy.enchantment.weapon.*;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class EnchantmentEffects {

    public static final DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>> REGISTER =
            DeferredRegister.create(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, EnchantmentFantasy.MODID);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> POISON =
            REGISTER.register("poison", () -> PoisonEnchantment.CODEC);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> INVISIBILITY =
            REGISTER.register("invisibility", () -> InvisibilityEnchantment.CODEC);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> WEALTH =
            REGISTER.register("wealth", () -> WealthEnchantment.CODEC);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> STIFLING =
            REGISTER.register("stifling", () -> StiflingEnchantment.CODEC);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> STUNNING_HIT =
            REGISTER.register("stunning_hit", () -> StunningHitEnchantment.CODEC);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> DISTURBANCE =
            REGISTER.register("disturbance", () -> DisturbanceEnchantment.CODEC);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> BREAKING_STROKE =
            REGISTER.register("breaking_stroke", () -> BreakingStrokeEnchantment.CODEC);

}
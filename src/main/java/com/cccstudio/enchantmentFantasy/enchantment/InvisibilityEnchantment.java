package com.cccstudio.enchantmentFantasy.enchantment;

import com.mojang.serialization.MapCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record InvisibilityEnchantment() implements EnchantmentEntityEffect {

    public static final MapCodec<InvisibilityEnchantment> CODEC = MapCodec.unit(InvisibilityEnchantment::new);

    @Override
    public void apply(ServerLevel serverLevel, int i, EnchantedItemInUse enchantedItemInUse, Entity entity, Vec3 vec3) {
        if(entity instanceof LivingEntity living) {
            living.addEffect(new MobEffectInstance(
                    MobEffects.INVISIBILITY,
                    i * 60,
                    1
            ));
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }

}

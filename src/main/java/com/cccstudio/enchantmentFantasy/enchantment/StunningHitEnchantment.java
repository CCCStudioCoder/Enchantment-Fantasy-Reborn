package com.cccstudio.enchantmentFantasy.enchantment;

import com.cccstudio.enchantmentFantasy.EnchantmentFantasy;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.function.Supplier;

public record StunningHitEnchantment() implements EnchantmentEntityEffect {

    public static final MapCodec<StunningHitEnchantment> CODEC = MapCodec.unit(StunningHitEnchantment::new);
    public static final Supplier<AttachmentType<Integer>> UNABLE_ATTACK = EnchantmentFantasy.ATTACHMENTS.register(
            "unable_attack", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build()
    );

    @Override
    public void apply(ServerLevel serverLevel, int i, EnchantedItemInUse enchantedItemInUse, Entity entity, Vec3 vec3) {
        if(entity instanceof LivingEntity living) {
            living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, i, 255, false,  false, false));
            living.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, i, 255, false,  false, false));
            living.setData(UNABLE_ATTACK, ((int) serverLevel.getGameTime()));
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }

}

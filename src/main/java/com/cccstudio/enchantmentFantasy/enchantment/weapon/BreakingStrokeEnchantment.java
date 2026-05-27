package com.cccstudio.enchantmentFantasy.enchantment.weapon;

import com.mojang.serialization.MapCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record BreakingStrokeEnchantment() implements EnchantmentEntityEffect {

    public static final MapCodec<BreakingStrokeEnchantment> CODEC = MapCodec.unit(BreakingStrokeEnchantment::new);

    @Override
    public void apply(ServerLevel serverLevel, int i, EnchantedItemInUse enchantedItemInUse, Entity entity, Vec3 vec3) {
        if(entity instanceof LivingEntity living) {
            for(ItemStack armorPiece : living.getArmorSlots()) {
                if(!armorPiece.isEmpty()) {
                    armorPiece.setDamageValue(armorPiece.getDamageValue() + i);
                }
            }
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }

}

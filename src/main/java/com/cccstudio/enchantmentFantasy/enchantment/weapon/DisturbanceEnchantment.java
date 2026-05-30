package com.cccstudio.enchantmentFantasy.enchantment.weapon;

import com.mojang.serialization.MapCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record DisturbanceEnchantment() implements EnchantmentEntityEffect {

    public static final MapCodec<DisturbanceEnchantment> CODEC = MapCodec.unit(DisturbanceEnchantment::new);

    @Override
    public void apply(ServerLevel serverLevel, int i, EnchantedItemInUse enchantedItemInUse, Entity entity, Vec3 vec3) {
        RandomSource source = entity.getRandom();
        if(entity instanceof Player player && source.nextInt(100) < 30 * i) {
            ItemStack mainHandItem = player.getMainHandItem();
            int slot = source.nextInt(40);
            ItemStack randomSlotItem = player.getInventory().getItem(slot);

            player.getInventory().setItem(slot, mainHandItem);
            player.setItemInHand(player.getUsedItemHand(), randomSlotItem);
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }

}

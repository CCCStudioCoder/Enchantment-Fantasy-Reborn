package com.cccstudio.enchantmentFantasy;

import com.cccstudio.enchantmentFantasy.enchantment.PoisonEnchantment;
import com.cccstudio.enchantmentFantasy.enchantment.StunningHitEnchantment;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.Objects;

@EventBusSubscriber
public class Events {

    @SubscribeEvent
    public static void onEntityHurt(LivingDamageEvent.Post event) {
        // Super Poison effect
        LivingEntity target = event.getEntity();
        if(target.hasEffect(MobEffects.POISON) && target.getHealth() < 1
                && (target.level().getGameTime() - target.getData(PoisonEnchantment.SUPER_POISON) < 1000) ) {
            target.setData(PoisonEnchantment.SUPER_POISON, 0);
            target.hurt(new DamageSources(target.registryAccess()).magic(), 2);
        }

        // Life Thief effect
        Holder<Enchantment> lifeThief = event.getEntity().registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolder(ModEnchantments.LIFE_THIEF).orElseThrow();
        int enchantLevel = 0;
        try {
            enchantLevel = Objects.requireNonNull(Objects.requireNonNull(event.getSource().getWeaponItem())
                            .getComponents()
                            .get(DataComponents.ENCHANTMENTS))
                    .getLevel(lifeThief);
        } catch (NullPointerException ignored) {}
        if(enchantLevel > 0 && event.getSource().getEntity() instanceof LivingEntity attacker) {
            attacker.heal(event.getNewDamage() * 0.5f * enchantLevel);
        }
    }

    @SubscribeEvent
    public static void onEntityAttack(LivingIncomingDamageEvent event) {
        if(event.getSource().getEntity() instanceof LivingEntity attacker) {
            if(attacker.level().getGameTime() - attacker.getData(StunningHitEnchantment.UNABLE_ATTACK) < 20) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void livingTick(EntityTickEvent event) {

    }

}

package com.cccstudio.enchantmentFantasy;

import com.cccstudio.enchantmentFantasy.enchantment.weapon.PoisonEnchantment;
import com.cccstudio.enchantmentFantasy.enchantment.weapon.StunningHitEnchantment;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.Objects;
import java.util.Optional;

@EventBusSubscriber
public class Events {

    public static void sendDebugMessage(Level level, String message){
        level.players().forEach(player -> player.sendSystemMessage(Component.literal(message)));
    }

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
        Optional<Holder.Reference<Enchantment>> lifeThiefOptional = event.getEntity().registryAccess().registryOrThrow(Registries.ENCHANTMENT)
                .getHolder(ModEnchantments.LIFE_THIEF);
        if(lifeThiefOptional.isEmpty()) {
            return;
        }
        Holder.Reference<Enchantment> lifeThief = lifeThiefOptional.get();

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
        if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            // Stunning Hit effect
            if (attacker.level().getGameTime() - attacker.getData(StunningHitEnchantment.UNABLE_ATTACK) < 30) {
                sendDebugMessage(attacker.level(), "Attack canceled due to Stunning Hit cooldown");
                event.setCanceled(true);
            }

            // Invisibility effect
            Optional<Holder.Reference<Enchantment>> invisibilityOptional =
                    attacker.registryAccess()
                            .registryOrThrow(Registries.ENCHANTMENT)
                            .getHolder(ModEnchantments.INVISIBILITY);

            if (invisibilityOptional.isEmpty()) return;
            Holder.Reference<Enchantment> invisibility = invisibilityOptional.get();

            ItemStack weapon = event.getSource().getWeaponItem();
            if (weapon == null || weapon.isEmpty()) return;

            ItemEnchantments enchants = weapon.getComponents().get(DataComponents.ENCHANTMENTS);
            if (enchants == null) return;

            int level = enchants.getLevel(invisibility);
            if (level > 0) {
                attacker.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 30 * level, Math.max(0, level - 1)));
            }
        }
    }


    @SubscribeEvent
    public static void livingTick(EntityTickEvent.Post event) {
        if(!(event.getEntity() instanceof LivingEntity living)) {
            return;
        }

        ItemStack stack = living.getMainHandItem();

        if(stack.isEmpty()) {
            return;
        }

        ItemEnchantments enchants =
                stack.get(DataComponents.ENCHANTMENTS);

        if(enchants == null) {
            return;
        }

        Holder<Enchantment> galvanizing =
                event.getEntity()
                        .registryAccess()
                        .registryOrThrow(Registries.ENCHANTMENT)
                        .getHolder(ModEnchantments.GALVANIZING)
                        .orElse(null);

        if(galvanizing == null) {
            return;
        }

        int level = enchants.getLevel(galvanizing);

        if(level <= 0) {
            return;
        }

        living.addEffect(new MobEffectInstance(
                MobEffects.MOVEMENT_SPEED,
                40,
                level - 1
        ));
    }

}

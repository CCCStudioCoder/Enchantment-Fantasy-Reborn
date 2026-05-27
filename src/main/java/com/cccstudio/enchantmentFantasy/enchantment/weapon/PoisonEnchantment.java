package com.cccstudio.enchantmentFantasy.enchantment.weapon;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.attachment.AttachmentType;
import java.util.function.Supplier;
import static com.cccstudio.enchantmentFantasy.EnchantmentFantasy.ATTACHMENTS;

public record PoisonEnchantment() implements EnchantmentEntityEffect {

    public static final MapCodec<PoisonEnchantment> CODEC = MapCodec.unit(PoisonEnchantment::new);
    public static final Supplier<AttachmentType<Integer>> SUPER_POISON = ATTACHMENTS.register(
            "super_poison", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build());

    @Override
    public void apply(ServerLevel serverLevel, int level, EnchantedItemInUse itemInUse, Entity entity, Vec3 vec3) {
        if(entity instanceof LivingEntity living) {
            living.addEffect(
                    new MobEffectInstance(MobEffects.POISON,
                    (level - 1) * 3 + 2,
                    (int) ((level - 1) * 0.5f)));
            if(level == 4 && itemInUse.owner() instanceof ServerPlayer) {
                living.setData(SUPER_POISON, (int)(serverLevel.getGameTime()));
            }
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}

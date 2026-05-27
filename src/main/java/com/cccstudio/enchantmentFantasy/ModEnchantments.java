package com.cccstudio.enchantmentFantasy;

import com.cccstudio.enchantmentFantasy.enchantment.weapon.*;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentTarget;

public class ModEnchantments {

    public static final ResourceKey<Enchantment> POISON = key("poison");
    public static final ResourceKey<Enchantment> LIFE_THIEF = key("life_thief");
    public static final ResourceKey<Enchantment> INVISIBILITY = key("invisibility");
    public static final ResourceKey<Enchantment> WEALTH = key("wealth");
    public static final ResourceKey<Enchantment> WEALTH_OF_NETHER = key("wealth_of_nether");
    public static final ResourceKey<Enchantment> STIFLING = key("stifling");
    public static final ResourceKey<Enchantment> STUNNING_HIT = key("stunning_hit");
    public static final ResourceKey<Enchantment> GALVANIZING = key("shield_bash");
    public static final ResourceKey<Enchantment> DISTURBANCE = key("disturbance");
    public static final ResourceKey<Enchantment> BREAKING_STROKE = key("breaking_stroke");

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        //var enchantments = context.lookup(Registries.ENCHANTMENT);
        var items = context.lookup(Registries.ITEM);
        HolderSet.Named<Item> weapons = items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE);

        register(context, POISON, Enchantment.enchantment(Enchantment.definition(
                weapons,
                10,
                4,
                Enchantment.dynamicCost(1, 5),
                Enchantment.dynamicCost(10, 5),
                2,
                EquipmentSlotGroup.MAINHAND
        )).withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER, EnchantmentTarget.VICTIM,
            new PoisonEnchantment()));

        register(context, LIFE_THIEF, Enchantment.enchantment(Enchantment.definition(
                weapons,
                10,
                3,
                Enchantment.dynamicCost(1, 5),
                Enchantment.dynamicCost(10, 5),
                2,
                EquipmentSlotGroup.MAINHAND
        )));

        register(context, INVISIBILITY, Enchantment.enchantment(Enchantment.definition(
                weapons,
                10,
                2,
                Enchantment.dynamicCost(10, 5),
                Enchantment.dynamicCost(25, 5),
                3,
                EquipmentSlotGroup.MAINHAND
        )).withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.VICTIM, EnchantmentTarget.ATTACKER, new InvisibilityEnchantment()));

        register(context, WEALTH, Enchantment.enchantment(Enchantment.definition(
                weapons,
                10,
                1,
                Enchantment.dynamicCost(15, 0),
                Enchantment.dynamicCost(25, 0),
                3,
                EquipmentSlotGroup.MAINHAND
        )).withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER, EnchantmentTarget.VICTIM, new WealthEnchantment(false)));

        register(context, WEALTH_OF_NETHER, Enchantment.enchantment(Enchantment.definition(
                weapons,
                10,
                1,
                Enchantment.dynamicCost(20, 0),
                Enchantment.dynamicCost(30, 0),
                3,
                EquipmentSlotGroup.MAINHAND
        )).withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER, EnchantmentTarget.VICTIM, new WealthEnchantment(true)));

        register(context, STIFLING, Enchantment.enchantment(Enchantment.definition(
                weapons,
                10,
                1,
                Enchantment.dynamicCost(20, 0),
                Enchantment.dynamicCost(30, 0),
                3,
                EquipmentSlotGroup.MAINHAND
        )).withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER, EnchantmentTarget.VICTIM, new StiflingEnchantment()));

        register(context, STUNNING_HIT, Enchantment.enchantment(Enchantment.definition(
                weapons,
                10,
                1,
                Enchantment.dynamicCost(20, 0),
                Enchantment.dynamicCost(30, 0),
                3,
                EquipmentSlotGroup.MAINHAND
        )).withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER, EnchantmentTarget.VICTIM, new StiflingEnchantment()));

        register(context, GALVANIZING, Enchantment.enchantment(Enchantment.definition(
                weapons,
                10,
                1,
                Enchantment.dynamicCost(20, 0),
                Enchantment.dynamicCost(30, 0),
                3,
                EquipmentSlotGroup.MAINHAND
        )));

        register(context, DISTURBANCE, Enchantment.enchantment(Enchantment.definition(
                weapons,
                10,
                2,
                Enchantment.dynamicCost(15, 8),
                Enchantment.dynamicCost(20, 10),
                3,
                EquipmentSlotGroup.MAINHAND
        )).withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER, EnchantmentTarget.VICTIM, new DisturbanceEnchantment()));

        register(context, BREAKING_STROKE, Enchantment.enchantment(Enchantment.definition(
                weapons,
                10,
                1,
                Enchantment.dynamicCost(20, 0),
                Enchantment.dynamicCost(30, 0),
                3,
                EquipmentSlotGroup.MAINHAND
        )).withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER, EnchantmentTarget.VICTIM, new BreakingStrokeEnchantment()));
    }

    private static ResourceKey<Enchantment> key(String name) {
        return ResourceKey.create(Registries.ENCHANTMENT,
                ResourceLocation.fromNamespaceAndPath(EnchantmentFantasy.MODID, name));
    }

    private static void register(BootstrapContext<Enchantment> registry, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        registry.register(key, builder.build(key.location()));
    }

}

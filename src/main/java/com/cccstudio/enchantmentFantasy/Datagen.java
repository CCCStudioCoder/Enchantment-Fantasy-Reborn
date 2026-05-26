package com.cccstudio.enchantmentFantasy;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class Datagen extends DatapackBuiltinEntriesProvider {

    public Datagen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, new RegistrySetBuilder().add(Registries.ENCHANTMENT, ModEnchantments::bootstrap), Set.of(EnchantmentFantasy.MODID));
    }

}

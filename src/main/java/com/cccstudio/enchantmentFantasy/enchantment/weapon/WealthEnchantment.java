package com.cccstudio.enchantmentFantasy.enchantment.weapon;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public record WealthEnchantment(boolean forNether) implements EnchantmentEntityEffect {

    public static final MapCodec<WealthEnchantment> CODEC = RecordCodecBuilder.mapCodec(enchant -> enchant.group(
            Codec.BOOL.fieldOf("for_nether").forGetter(WealthEnchantment::forNether)
    ).apply(enchant, WealthEnchantment::new));

    private static final HashMap<Block, Integer> OVERWORLD_ORES = new HashMap<>() {{
        put(Blocks.COAL_ORE, 20);
        put(Blocks.IRON_ORE, 8);
        put(Blocks.COPPER_ORE, 15);
        put(Blocks.GOLD_ORE, 5);
        put(Blocks.DIAMOND_ORE, 1);
        put(Blocks.EMERALD_ORE, 1);
    }};

    private static final HashMap<Block, Integer> NETHER_ORES = new HashMap<>() {{
        put(Blocks.NETHER_QUARTZ_ORE, 30);
        put(Blocks.NETHER_GOLD_ORE, 19);
        put(Blocks.ANCIENT_DEBRIS, 1);
    }};

    @Override
    public void apply(ServerLevel serverLevel, int i, EnchantedItemInUse enchantedItemInUse, Entity entity, Vec3 vec3) {
        RandomSource random = entity.getRandom();
        int randomInt = random.nextInt(200);
        if(randomInt < 50) {
            Block block = null;
            HashMap<Block, Integer> options = forNether ? NETHER_ORES : OVERWORLD_ORES;
            int cumulative = 0;
            for (Map.Entry<Block, Integer> entry : options.entrySet()) {
                cumulative += entry.getValue();
                if (randomInt < cumulative) {
                    block = entry.getKey();
                    break;
                }
            }
            ArrayList<BlockPos> availablePositions = new ArrayList<>();
            for(int x = 0; x < 20; x++) {
                for(int y = 0; y < 20; y++) {
                    for(int z = 0; z < 20; z++) {
                        BlockPos pos = new BlockPos(entity.blockPosition().offset(x - 10, y - 10, z - 10));
                        if(!serverLevel.isEmptyBlock(pos)) {
                            availablePositions.add(pos);
                        }
                    }
                }
            }
            assert block != null;
            BlockPos pos = availablePositions.get(random.nextInt(availablePositions.size()));
            serverLevel.setBlockAndUpdate(pos, block.defaultBlockState());
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }

}

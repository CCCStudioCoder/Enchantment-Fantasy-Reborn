package com.cccstudio.enchantmentFantasy;

import net.minecraft.data.DataGenerator;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(EnchantmentFantasy.MODID)
public class EnchantmentFantasy {

    public static final String MODID = "enchantmentfantasy";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MODID);

    public EnchantmentFantasy(IEventBus modEventBus, ModContainer modContainer) {
        EnchantmentEffects.REGISTER.register(modEventBus);
        ATTACHMENTS.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        NeoForge.EVENT_BUS.register(this);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }

    @EventBusSubscriber(modid = MODID)
    public static class Events {
        @SubscribeEvent
        public static void gatherData(GatherDataEvent event) {
            DataGenerator gen = event.getGenerator();
            gen.addProvider(
                    event.includeServer(),
                    new Datagen(gen.getPackOutput(), event.getLookupProvider())
            );
        }
    }

}

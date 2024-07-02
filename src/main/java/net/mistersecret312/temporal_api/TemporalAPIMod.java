package net.mistersecret312.temporal_api;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.mistersecret312.temporal_api.events.ControlEvent;
import net.mistersecret312.temporal_api.events.FlightEventEvent;
import net.mistersecret312.temporal_api.events.MinigameStartEvent;
import net.mistersecret312.temporal_api.events.TardisEvent;
import net.tardis.mod.items.SonicItem;
import net.tardis.mod.items.TItems;
import net.tardis.mod.registries.FlightEventRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TemporalAPIMod.MOD_ID)
public class TemporalAPIMod
{
    public static final String MOD_ID = "temporal_api";

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public TemporalAPIMod() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {

    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
    }

    private void processIMC(final InterModProcessEvent event)
    {

    }

    @Mod.EventBusSubscriber(modid = TemporalAPIMod.MOD_ID)
    public static class TestEvents
    {
        @SubscribeEvent
        public static void onSonicEnter(ControlEvent.SonicPutEvent event)
        {
            System.out.println("Successful Sonic Port! " + event.getItemStack().getItem());
        }

        @SubscribeEvent
        public static void onMinigame(MinigameStartEvent event)
        {
            if(event.getStack().getItem().equals(TItems.ARTRON_CAPACITOR_HIGH.get()))
                event.setCanceled(true);
        }

        @SubscribeEvent
        public static void onNewTARDIS(TardisEvent.TardisCreatedEvent event)
        {
            SonicItem item = new SonicItem();
            ItemStack sonic = new ItemStack(item);
            item.setTardis(sonic, event.getConsole().getWorld().getDimensionKey().getLocation());
            item.charge(sonic, 500);
            event.getConsole().setSonicItem(sonic);
        }

        @SubscribeEvent
        public static void onNewEvent(FlightEventEvent.StartFlightEvent event)
        {
            if(event.getEvent().getEntry().equals(FlightEventRegistry.TIMEWIND.get()))
            {
                System.out.println(event.getEvent().getEntry().toString());
            }
        }
    }
}

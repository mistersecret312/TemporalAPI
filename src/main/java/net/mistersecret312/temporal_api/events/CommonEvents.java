package net.mistersecret312.temporal_api.events;

import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.mistersecret312.temporal_api.TemporalAPIMod;
import net.tardis.mod.helper.TardisHelper;
import net.tardis.mod.helper.WorldHelper;
import net.tardis.mod.tileentities.ConsoleTile;
import net.tardis.mod.world.dimensions.TDimensions;

import java.util.*;

@Mod.EventBusSubscriber(modid = TemporalAPIMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonEvents {

    @SubscribeEvent
    public static void onServerStarting(FMLServerStartingEvent event){

        event.getServer().getWorlds();
        List<RegistryKey<World>> worlds = new ArrayList<>(Collections.emptyList());
        for(ServerWorld world : event.getServer().getWorlds()){
            if(WorldHelper.areDimensionTypesSame(world, TDimensions.DimensionTypes.TARDIS_TYPE)) {
                worlds.add(world.getDimensionKey());
            }
        }

        if(!worlds.isEmpty()) {
            int size = worlds.size();
            for(int i = 0; i < size; i++) {
                if (TardisHelper.getConsoleInWorld(event.getServer().getWorld(worlds.get(i))).isPresent()) {
                    Optional<ConsoleTile> console = TardisHelper.getConsoleInWorld(event.getServer().getWorld(worlds.get(i)));
                    console.ifPresent((tile -> {
                        tile.removeControls();
                        tile.getOrCreateControls();
                    }));
                }
            }
        }
    }
}

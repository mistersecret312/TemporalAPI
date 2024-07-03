package net.mistersecret312.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.mistersecret312.temporal_api.events.TardisEvent;
import net.tardis.mod.ars.ConsoleRoom;
import net.tardis.mod.cap.Capabilities;
import net.tardis.mod.helper.TardisHelper;
import net.tardis.mod.items.TItems;
import net.tardis.mod.tileentities.ConsoleTile;
import net.tardis.mod.tileentities.inventory.PanelInventory;
import net.tardis.mod.world.dimensions.TDimensions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.UUID;

@Mixin(TardisHelper.class)
public class TardisHelperMixin {

    @Shadow(remap = false) @Final public static BlockPos TARDIS_POS;

    /**
     * @author mistersecret312
     * @reason TardisCreatedEvent
     */
    @Overwrite(remap = false)
    public static ServerWorld setupTardisDim(MinecraftServer server, BlockState consoleBlock, ConsoleRoom room) {
        ServerWorld tardisWorld = TDimensions.registerOrGetTardisDim(UUID.randomUUID().toString(), server);
        if(tardisWorld != null && !(tardisWorld.getTileEntity(TARDIS_POS) instanceof ConsoleTile)) {
            tardisWorld.setBlockState(TARDIS_POS, consoleBlock, 3);
            ConsoleTile console = ((ConsoleTile)tardisWorld.getTileEntity(TARDIS_POS));
            console.onInitialSpawn();
        }
        room.spawnConsoleRoom(tardisWorld, true);

        //Randomly contain artron caps
        tardisWorld.getCapability(Capabilities.TARDIS_DATA).ifPresent(cap -> {
            if(!MinecraftForge.EVENT_BUS.post(new TardisEvent.EngineFillEvent((ConsoleTile) tardisWorld.getTileEntity(TARDIS_POS), cap))) {
                PanelInventory inv = cap.getEngineInventoryForSide(Direction.WEST);
                for (int i = 0; i < inv.getSlots(); ++i) {
                    inv.setStackInSlot(i, tardisWorld.rand.nextDouble() < 0.1 ? new ItemStack(TItems.LEAKY_ARTRON_CAPACITOR.get()) : ItemStack.EMPTY);
                }
            }
        });

        return tardisWorld;
    }

}

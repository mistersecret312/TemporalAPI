package net.mistersecret312.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.common.MinecraftForge;
import net.mistersecret312.temporal_api.ItemTagsInit;
import net.mistersecret312.temporal_api.events.ControlEvent;
import net.tardis.mod.cap.Capabilities;
import net.tardis.mod.controls.SonicPortControl;
import net.tardis.mod.items.TItems;
import net.tardis.mod.schematics.Schematic;
import net.tardis.mod.tileentities.ConsoleTile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.HashSet;
import java.util.Set;

@Mixin(SonicPortControl.class)
public class SonicPortControlMixin {

    /**
     * @author mistersecret312
     * @reason Placing items into the Sonic Port
     */
    @Overwrite(remap = false)
    public boolean onRightClicked(ConsoleTile console, PlayerEntity player) {
        if(!console.getWorld().isRemote()) {
            if(ItemTagsInit.SONIC.contains(player.getHeldItemMainhand().getItem()) && console.getSonicItem().isEmpty() && !MinecraftForge.EVENT_BUS.post(new ControlEvent.SonicPutEvent(((SonicPortControl) (Object) this), player.getHeldItemMainhand()))) {
                console.setSonicItem(player.getHeldItemMainhand());
                player.setHeldItem(Hand.MAIN_HAND, ItemStack.EMPTY);
                console.getSonicItem().getCapability(Capabilities.SONIC_CAPABILITY).ifPresent(cap -> {
                    Set<Schematic> uniqueSchematics = new HashSet<>();
                    uniqueSchematics.addAll(cap.getSchematics());
                    for(Schematic s : uniqueSchematics) {
                        if (s.onConsumedByTARDIS(console, player))
                            cap.getSchematics().remove(s);
                    }
                });
            }
            else if(player.getHeldItemMainhand().isEmpty() && !console.getSonicItem().isEmpty() && !MinecraftForge.EVENT_BUS.post(new ControlEvent.SonicTakeEvent(((SonicPortControl) (Object) this), console.getSonicItem()))) {
                InventoryHelper.spawnItemStack(console.getWorld(), player.getPosX(), player.getPosY(), player.getPosZ(), console.getSonicItem());
                console.setSonicItem(ItemStack.EMPTY);
            }
        }
        return true;
    }

}

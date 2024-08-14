package net.mistersecret312.mixin;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.mistersecret312.temporal_api.events.ComponentEvent;
import net.tardis.mod.containers.slot.EngineSlot;
import org.spongepowered.asm.mixin.Mixin;

import javax.annotation.Nonnull;

@Mixin(EngineSlot.class)
public class EngineSlotMixin {

    public void onSlotChange(@Nonnull ItemStack oldStackIn, @Nonnull ItemStack newStackIn)
    {
        MinecraftForge.EVENT_BUS.post(new ComponentEvent.ComponentChanged(oldStackIn, newStackIn));
    }
}

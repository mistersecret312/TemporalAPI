package net.mistersecret312.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.mistersecret312.temporal_api.events.MinigameStartEvent;
import net.tardis.mod.client.guis.containers.EngineContainerScreen;
import net.tardis.mod.client.guis.minigame.WireGameScreen;
import net.tardis.mod.containers.EngineContainer;
import net.tardis.mod.containers.slot.EngineSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EngineContainerScreen.class)
public abstract class EngineScreenMixin extends ContainerScreen<EngineContainer> {

    public EngineScreenMixin(EngineContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    /**
     * @author mistersecret312
     * @reason MinigameStartEvent
     */
    @Overwrite
    protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type) {
        ItemStack held = this.playerInventory.getItemStack();
        if(!held.isEmpty() && slotIn instanceof EngineSlot) {
            if(slotIn.isItemValid(held))
                if(!MinecraftForge.EVENT_BUS.post(new MinigameStartEvent(held,this.container, this.playerInventory.player)))
                    Minecraft.getInstance().enqueue(() -> Minecraft.getInstance().displayGuiScreen(new WireGameScreen(slotId, this.container.getPanelDirection())));
        }
        super.handleMouseClick(slotIn, slotId, mouseButton, type);
    }

}

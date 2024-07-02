package net.mistersecret312.temporal_api.events;

import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;
import net.tardis.mod.containers.EngineContainer;

/**
    Canceling this event will stop the normal NTM minigame from playing.

 */
public class MinigameStartEvent extends Event {

    public final ItemStack stack;
    public final EngineContainer container;
    public MinigameStartEvent(ItemStack stack, EngineContainer container)
    {
        this.stack = stack;
        this.container = container;
    }

    public EngineContainer getContainer() {
        return container;
    }

    public ItemStack getStack() {
        return stack;
    }

    @Override
    public boolean isCancelable() {
        return true;
    }
}

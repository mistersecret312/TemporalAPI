package net.mistersecret312.temporal_api.events;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.Event;
import net.tardis.mod.containers.EngineContainer;

/**
    Canceling this event will stop the normal NTM minigame from playing.

 */
public class MinigameStartEvent extends Event {

    public final ItemStack stack;
    public final EngineContainer container;
    public final PlayerEntity player;
    public MinigameStartEvent(ItemStack stack, EngineContainer container, PlayerEntity player)
    {
        this.stack = stack;
        this.container = container;
        this.player = player;
    }

    public PlayerEntity getPlayer() {
        return player;
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

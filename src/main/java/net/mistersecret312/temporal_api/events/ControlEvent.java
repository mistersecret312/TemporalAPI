package net.mistersecret312.temporal_api.events;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;
import net.tardis.mod.controls.AbstractControl;

public class ControlEvent extends Event {

    private final AbstractControl control;
    private final PlayerEntity player;
    public ControlEvent(AbstractControl control, PlayerEntity player){
        this.control = control;
        this.player = player;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public AbstractControl getControl() {
        return control;
    }

    /**
     * If canceled, the control will not do its action.
     * Handles LMB interaction, for RMB interaction, use {@link net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific}.
     */
    public static class ControlHitEvent extends ControlEvent{
        public ControlHitEvent(AbstractControl control, PlayerEntity player){
            super(control, player);
        }

        @Override
        public boolean isCancelable() {
            return true;
        }
    }

    /**
     * Fired when an item is put into the Sonic Port
     * Is still fired even when the item isn't acceptable due to not being in the 'sonic' Item Tag
     * If canceled, Item will not be placed into the Sonic Port
     */
    public static class SonicPutEvent extends ControlEvent
    {
        private final ItemStack stack;
        public SonicPutEvent(AbstractControl control, PlayerEntity player, ItemStack stack) {
            super(control, player);
            this.stack = stack;
        }

        public ItemStack getItemStack() {
            return stack;
        }

        @Override
        public boolean isCancelable() {
            return true;
        }
    }

    /**
     * Fired when the item is about to be taken from the Sonic Port
     * If canceled, the item will not be taken from the Sonic Port
     */
    public static class SonicTakeEvent extends ControlEvent
    {
        private final ItemStack stack;
        public SonicTakeEvent(AbstractControl control, PlayerEntity player, ItemStack stack) {
            super(control, player);
            this.stack = stack;
        }

        public ItemStack getItemStack() {
            return stack;
        }

        @Override
        public boolean isCancelable() {
            return true;
        }
    }
}

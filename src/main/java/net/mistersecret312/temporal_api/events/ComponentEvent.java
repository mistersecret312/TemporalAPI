package net.mistersecret312.temporal_api.events;

import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;
import net.tardis.mod.controls.AbstractControl;
import net.tardis.mod.subsystem.Subsystem;

public class ComponentEvent extends Event {

    private final ItemStack component;
    public ComponentEvent(ItemStack component){
        this.component = component;
    }

    public ItemStack getComponent() {
        return component;
    }

    /**
     * getComponent() will give the old stack, getNew() gives the old one
     */
    public static class ComponentChanged extends ComponentEvent {
        private final ItemStack stack1;
        public ComponentChanged(ItemStack stack, ItemStack stack1){
            super(stack);
            this.stack1 = stack1;
        }

        public ItemStack getNew() {
            return stack1;
        }
    }
}

package net.mistersecret312.temporal_api.events;

import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;
import net.tardis.mod.controls.AbstractControl;
import net.tardis.mod.controls.SonicPortControl;
import net.tardis.mod.registries.ControlRegistry;

public class ControlEvent extends Event {

    private final AbstractControl control;
    public ControlEvent(AbstractControl control){
        this.control = control;
    }

    public AbstractControl getControl() {
        return control;
    }

    public static class ControlHitEvent extends ControlEvent{
        public ControlHitEvent(AbstractControl control){
            super(control);
        }
    }

    public static class SonicPutEvent extends ControlEvent
    {
        private final ItemStack stack;
        public SonicPutEvent(AbstractControl control, ItemStack stack) {
            super(control);
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

    public static class SonicTakeEvent extends ControlEvent
    {
        private final ItemStack stack;
        public SonicTakeEvent(AbstractControl control, ItemStack stack) {
            super(control);
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

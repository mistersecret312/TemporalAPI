package net.mistersecret312.mixin;

import jdk.internal.org.objectweb.asm.Opcodes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.mistersecret312.temporal_api.events.ControlEvent;
import net.mistersecret312.temporal_api.events.FlightEventEvent;
import net.mistersecret312.temporal_api.events.TardisEvent;
import net.tardis.mod.controls.AbstractControl;
import net.tardis.mod.flight.FlightEvent;
import net.tardis.mod.tileentities.ConsoleTile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ConsoleTile.class)
public abstract class ConsoleMixin {

    @Shadow
    private FlightEvent currentEvent;

    @Shadow
    private boolean isBeingTowed;

    @Redirect(method = "setFlightEvent(Lnet/tardis/mod/flight/FlightEvent;)V", at = @At(value = "FIELD", target = "Lnet/tardis/mod/tileentities/ConsoleTile;currentEvent:Lnet/tardis/mod/flight/FlightEvent;", opcode = Opcodes.PUTFIELD))
    public void onSetEvent(ConsoleTile tile, FlightEvent event){
        if(!MinecraftForge.EVENT_BUS.post(new FlightEventEvent.FlightEventStartEvent(event, tile)))
        {
            this.currentEvent = this.isBeingTowed ? null : event;
        }
    }

    @Inject(method = "onInitialSpawn()V", at = @At("TAIL"))
    public void onCreated(CallbackInfo ci){
        MinecraftForge.EVENT_BUS.post(new TardisEvent.TardisCreatedEvent(((ConsoleTile) (Object) this)));
    }

}

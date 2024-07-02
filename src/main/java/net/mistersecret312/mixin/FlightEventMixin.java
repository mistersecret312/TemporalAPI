package net.mistersecret312.mixin;

import net.minecraftforge.common.MinecraftForge;
import net.mistersecret312.temporal_api.events.FlightEventEvent;
import net.tardis.mod.controls.AbstractControl;
import net.tardis.mod.flight.FlightEvent;
import net.tardis.mod.tileentities.ConsoleTile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FlightEvent.class)
public abstract class FlightEventMixin {

    @Inject(method = "onComplete(Lnet/tardis/mod/tileentities/ConsoleTile;)Z", at = @At("TAIL"))
    public void onSuccessEvent(ConsoleTile tile, CallbackInfoReturnable<Boolean> cir){
        MinecraftForge.EVENT_BUS.post(new FlightEventEvent.FlightEventSuccessEvent(((FlightEvent) (Object) this), tile));
    }

    @Inject(method = "onMiss(Lnet/tardis/mod/tileentities/ConsoleTile;)V", at = @At("TAIL"))
    public void onFailEvent(ConsoleTile tile, CallbackInfo ci){
        MinecraftForge.EVENT_BUS.post(new FlightEventEvent.FlightEventFailEvent(((FlightEvent) (Object) this), tile));
    }
}

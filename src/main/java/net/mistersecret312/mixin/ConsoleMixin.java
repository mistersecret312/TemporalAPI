package net.mistersecret312.mixin;

import net.minecraftforge.common.MinecraftForge;
import net.mistersecret312.temporal_api.events.FlightEventEvent;
import net.mistersecret312.temporal_api.events.TardisEvent;
import net.tardis.mod.flight.FlightEvent;
import net.tardis.mod.tileentities.ConsoleTile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ConsoleTile.class)
public abstract class ConsoleMixin {

    @Redirect(method = "fly()V", at = @At(value = "INVOKE", target = "Lnet/tardis/mod/tileentities/ConsoleTile;setFlightEvent(Lnet/tardis/mod/flight/FlightEvent;)V"), remap = false)
    public void redirectRandomSetEvent(ConsoleTile tile, FlightEvent event)
    {
        if(!MinecraftForge.EVENT_BUS.post(new FlightEventEvent.StartFlightEvent(event, tile)))
            tile.setFlightEvent(event);
    }

    @Inject(method = "takeoff()Z", at = @At("RETURN"), cancellable = true, remap = false)
    public void onTakeOff(CallbackInfoReturnable<Boolean> cir)
    {
        if(!MinecraftForge.EVENT_BUS.post(new TardisEvent.TakeoffEvent(((ConsoleTile) (Object) this))))
            cir.setReturnValue(cir.getReturnValue());
    }

    @Inject(method = "calcSpeed()F", at = @At("RETURN"), cancellable = true, remap = false)
    public void calcSpeed(CallbackInfoReturnable<Float> cir)
    {
        TardisEvent.SpeedCalculationEvent event = new TardisEvent.SpeedCalculationEvent(((ConsoleTile) (Object) this), cir.getReturnValue());
        MinecraftForge.EVENT_BUS.post(event);
        cir.setReturnValue(event.getSpeed());
    }

    @Inject(method = "calcFuelUse()F", at = @At("RETURN"), cancellable = true, remap = false)
    public void calcFuelUse(CallbackInfoReturnable<Float> cir)
    {
        TardisEvent.FuelUseCalculationEvent event = new TardisEvent.FuelUseCalculationEvent(((ConsoleTile) (Object) this), cir.getReturnValue());
        MinecraftForge.EVENT_BUS.post(event);
        cir.setReturnValue(event.getFuelUse());
    }

    /**
     * @author mistersecret312
     * @reason TardisEvents.LandEvent
     */
    @Overwrite(remap = false)
    public void initLand() {
        ConsoleTile console = ((ConsoleTile) (Object) this);
        if(!MinecraftForge.EVENT_BUS.post(new TardisEvent.LandEvent(console))) {
            console.scaleDestination();
            console.land();
            console.updateClient();
        }
    }

}

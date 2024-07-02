package net.mistersecret312.mixin;

import net.minecraftforge.common.MinecraftForge;
import net.mistersecret312.temporal_api.events.FlightEventEvent;
import net.mistersecret312.temporal_api.events.TardisEvent;
import net.tardis.mod.flight.FlightEvent;
import net.tardis.mod.tileentities.ConsoleTile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ConsoleTile.class)
public abstract class ConsoleMixin {

    @Redirect(method = "fly()V", at = @At(value = "INVOKE", target = "Lnet/tardis/mod/tileentities/ConsoleTile;setFlightEvent(Lnet/tardis/mod/flight/FlightEvent;)V"), remap = false)
    public void redirectRandomSetEvent(ConsoleTile tile, FlightEvent event)
    {
        if(!MinecraftForge.EVENT_BUS.post(new FlightEventEvent.StartFlightEvent(event, tile)))
            tile.setFlightEvent(event);
    }

    @Inject(method = "onInitialSpawn()V", at = @At("TAIL"), remap = false)
    public void onCreated(CallbackInfo ci){
        MinecraftForge.EVENT_BUS.post(new TardisEvent.TardisCreatedEvent(((ConsoleTile) (Object) this)));
    }

}

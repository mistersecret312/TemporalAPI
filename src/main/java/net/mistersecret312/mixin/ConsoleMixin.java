package net.mistersecret312.mixin;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.mistersecret312.temporal_api.AdvancementTriggerInit;
import net.mistersecret312.temporal_api.TemporalAPIConfig;
import net.mistersecret312.temporal_api.events.FlightEventEvent;
import net.mistersecret312.temporal_api.events.TardisEvent;
import net.tardis.mod.entity.ControlEntity;
import net.tardis.mod.flight.FlightEvent;
import net.tardis.mod.registries.ControlRegistry;
import net.tardis.mod.tileentities.ConsoleTile;
import net.tardis.mod.tileentities.console.misc.ControlOverride;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.HashMap;

@Mixin(ConsoleTile.class)
public abstract class ConsoleMixin
{
    @Shadow(remap = false)
    private ArrayList<ControlRegistry.ControlEntry> controlEntries;
    @Shadow(remap = false)
    protected HashMap<Class<?>, ControlOverride> controlOverrides;
    @Shadow(remap = false)
    private ArrayList<ControlEntity> controls;

    @Redirect(method = "fly()V", at = @At(value = "INVOKE", target = "Lnet/tardis/mod/tileentities/ConsoleTile;setFlightEvent(Lnet/tardis/mod/flight/FlightEvent;)V"), remap = false)
    public void redirectRandomSetEvent(ConsoleTile tile, FlightEvent event)
    {
        if(!MinecraftForge.EVENT_BUS.post(new FlightEventEvent.StartFlightEvent(event, tile)))
            tile.setFlightEvent(event);

    }

    @Inject(method = "takeoff(Z)Z", at = @At("HEAD"), cancellable = true, remap = false)
    public void takeoff(boolean towed, CallbackInfoReturnable<Boolean> cir)
    {
        TardisEvent.TakeoffEvent event = new TardisEvent.TakeoffEvent(((ConsoleTile) (Object) this));
        MinecraftForge.EVENT_BUS.post(event);
        if(event.isCanceled())
            cir.setReturnValue(false);
        else if(!((ConsoleTile) (Object) this).getWorld().isRemote()) AdvancementTriggerInit.TAKEOFF.testForAll((ServerPlayerEntity) ((ConsoleTile) (Object) this).getPilot(), ((ConsoleTile) (Object) this).getCurrentDimension());
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
    public void initLand()
    {
        ConsoleTile console = ((ConsoleTile) (Object) this);
        if (!MinecraftForge.EVENT_BUS.post(new TardisEvent.LandEvent(console)))
        {
            console.scaleDestination();
            console.land();
            console.updateClient();
        }
    }

    @Inject(method = "land()V", at = @At("HEAD"), remap = false)
    public void land(CallbackInfo ci)
    {
        ConsoleTile console = ((ConsoleTile) (Object) this);
        if (!console.getWorld().isRemote) {
            if (console.getPilot() != null) {
                AdvancementTriggerInit.LAND.testForAll((ServerPlayerEntity) console.getPilot(), console.getPercentageJourney() > 0.5D ? console.getDestinationDimension() : console.getCurrentDimension(), console.isCrashing());
            }
        }
    }

    @ModifyConstant(method = "calcSpeed()F", constant = @Constant(ordinal = 2), remap = false)
    private int modifySpeed(int constant)
    {
        return TemporalAPIConfig.SERVER.baseSpeed.get();
    }

}

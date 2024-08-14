package net.mistersecret312.mixin;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.mistersecret312.temporal_api.AdvancementTriggerInit;
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

    @Inject(method = "onComplete(Lnet/tardis/mod/tileentities/ConsoleTile;)Z", at = @At("RETURN"), remap = false)
    public void onSuccessEvent(ConsoleTile tile, CallbackInfoReturnable<Boolean> cir){
        AdvancementTriggerInit.FLIGHT_EVENT.testForAll((ServerPlayerEntity) tile.getPilot(), ((FlightEvent) (Object) this).getEntry(), cir.getReturnValueZ());
        MinecraftForge.EVENT_BUS.post(new FlightEventEvent.SuccessFlightEvent(((FlightEvent) (Object) this), tile));
    }

    @Inject(method = "onMiss(Lnet/tardis/mod/tileentities/ConsoleTile;)V", at = @At("TAIL"), remap = false)
    public void onFailEvent(ConsoleTile tile, CallbackInfo ci){
        MinecraftForge.EVENT_BUS.post(new FlightEventEvent.FailFlightEvent(((FlightEvent) (Object) this), tile));
    }
}

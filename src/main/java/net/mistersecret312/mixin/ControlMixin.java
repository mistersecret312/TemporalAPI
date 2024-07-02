package net.mistersecret312.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.mistersecret312.temporal_api.events.ControlEvent;
import net.tardis.mod.controls.AbstractControl;
import net.tardis.mod.tileentities.ConsoleTile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractControl.class)
public abstract class ControlMixin {

    @Inject(method = "onRightClicked(Lnet/tardis/mod/tileentities/ConsoleTile;Lnet/minecraft/entity/player/PlayerEntity;)Z", at = @At("TAIL"))
    public void onUsed(ConsoleTile console, PlayerEntity player, CallbackInfoReturnable<Boolean> cir){
        MinecraftForge.EVENT_BUS.post(new ControlEvent.ControlHitEvent(((AbstractControl) (Object) this)));
    }

}

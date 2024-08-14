package net.mistersecret312.mixin;

import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.mistersecret312.temporal_api.ItemTagsInit;
import net.mistersecret312.temporal_api.TemporalAPIConfig;
import net.tardis.mod.client.renderers.InvisEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.List;

@Mixin(InvisEntityRenderer.class)
public class InvisibleRendererMixin
{

    @Redirect(method = "renderName(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/text/ITextComponent;Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;I)V",
    at = @At(value = "INVOKE", target = "Lnet/tardis/mod/helper/PlayerHelper;isInEitherHand(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/Item;)Z"),
    remap = false)
    public boolean renderName(LivingEntity holder, Item item)
    {
        if(TemporalAPIConfig.CLIENT.useHotbarForManual.get() && holder instanceof ClientPlayerEntity)
        {
            ClientPlayerEntity player = (ClientPlayerEntity) holder;
            List<ItemStack> stacks = new ArrayList<>();
            for(int i = 0; i < 9; i++)
                stacks.add(player.inventory.mainInventory.get(i));
            return stacks.stream().anyMatch(stack -> stack.getItem().isIn(ItemTagsInit.MANUAL));
        }
        else return holder.getHeldItem(Hand.MAIN_HAND).getItem().isIn(ItemTagsInit.MANUAL) || holder.getHeldItem(Hand.OFF_HAND).getItem().isIn(ItemTagsInit.MANUAL);
    }

}

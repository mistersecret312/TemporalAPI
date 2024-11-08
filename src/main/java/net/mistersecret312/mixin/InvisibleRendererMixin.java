package net.mistersecret312.mixin;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.mistersecret312.temporal_api.ItemTagsInit;
import net.mistersecret312.temporal_api.TemporalAPIConfig;
import net.tardis.mod.client.renderers.InvisEntityRenderer;
import net.tardis.mod.entity.ControlEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.ArrayList;
import java.util.List;

@Mixin(InvisEntityRenderer.class)

public class InvisibleRendererMixin extends EntityRenderer<Entity>
{

    protected InvisibleRendererMixin(EntityRendererManager renderManager)
    {
        super(renderManager);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity)
    {
        return null;
    }

    /**
     * @author mistersecret312
     * @reason Manual work
     */
    @Overwrite(remap = false)
    public void renderName(Entity entity, ITextComponent displayNameIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn)
    {
        matrixStackIn.push();
        if (entity instanceof ControlEntity && Minecraft.getInstance().objectMouseOver instanceof EntityRayTraceResult && canRender(Minecraft.getInstance().player) && ((EntityRayTraceResult)Minecraft.getInstance().objectMouseOver).getEntity() == entity) {
            matrixStackIn.translate(0.0, -0.20000000298023224, 0.0);
            super.renderName(entity, entity.getDisplayName(), matrixStackIn, bufferIn, packedLightIn);
        }

        matrixStackIn.pop();
    }

    public boolean canRender(LivingEntity entity)
    {
        if(TemporalAPIConfig.CLIENT.useHotbarForManual.get() && entity instanceof ClientPlayerEntity)
        {
            ClientPlayerEntity player = (ClientPlayerEntity) entity;
            List<ItemStack> stacks = new ArrayList<>();
            for(int i = 0; i < 9; i++)
                stacks.add(player.inventory.mainInventory.get(i));
            stacks.add(player.getHeldItem(Hand.OFF_HAND));
            return stacks.stream().anyMatch(stack -> stack.getItem().isIn(ItemTagsInit.MANUAL));
        }
        else return entity.getHeldItem(Hand.MAIN_HAND).getItem().isIn(ItemTagsInit.MANUAL) || entity.getHeldItem(Hand.OFF_HAND).getItem().isIn(ItemTagsInit.MANUAL);

    }

}

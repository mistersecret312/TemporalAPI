package net.mistersecret312.temporal_api.triggers;

import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class LandTardisTrigger extends AbstractCriterionTrigger<LandTardisTrigger.Instance>
{
    private static final ResourceLocation ID = new ResourceLocation("tardis","tardis_landed");

    public ResourceLocation getId() {
        return ID;
    }

    public LandTardisTrigger.Instance deserializeTrigger(JsonObject json, EntityPredicate.AndPredicate entityPredicate, ConditionArrayParser conditionsParser) {
        RegistryKey<World> registrykey = json.has("dimension") ? RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(JSONUtils.getString(json, "dimension"))) : null;
        Boolean crashed = json.has("crashed") ? JSONUtils.getBoolean(json, "crashed") : null;
        return new LandTardisTrigger.Instance(entityPredicate, registrykey, crashed);
    }

    public void testForAll(ServerPlayerEntity player, RegistryKey<World> dimension, Boolean crashed) {
        this.triggerListeners(player, (instance) -> instance.test(player, dimension, crashed));
    }

    public static class Instance extends CriterionInstance {
        @Nullable
        private final RegistryKey<World> dimension;
        @Nullable
        private final Boolean crashed;

        public Instance(EntityPredicate.AndPredicate entityPredicate, @Nullable RegistryKey<World> dimension, @Nullable Boolean crashed) {
            super(LandTardisTrigger.ID, entityPredicate);
            this.dimension = dimension;
            this.crashed = crashed;
        }

        public boolean test(ServerPlayerEntity player, RegistryKey<World> dimension, boolean crashed) {
            return (this.dimension == null || this.dimension == dimension) && (this.crashed == null || this.crashed == crashed);
        }

        public JsonObject serialize(ConditionArraySerializer conditions) {
            JsonObject jsonobject = super.serialize(conditions);
            if (this.dimension != null)
                jsonobject.addProperty("dimension", this.dimension.getLocation().toString());


            if(this.crashed != null)
                jsonobject.addProperty("crashed", this.crashed);

            return jsonobject;
        }
    }
}

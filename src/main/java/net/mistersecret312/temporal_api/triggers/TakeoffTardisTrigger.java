package net.mistersecret312.temporal_api.triggers;

import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.*;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class TakeoffTardisTrigger extends AbstractCriterionTrigger<TakeoffTardisTrigger.Instance>
{
    private static final ResourceLocation ID = new ResourceLocation("tardis","tardis_takeoff");

    public ResourceLocation getId() {
        return ID;
    }

    public TakeoffTardisTrigger.Instance deserializeTrigger(JsonObject json, EntityPredicate.AndPredicate entityPredicate, ConditionArrayParser conditionsParser) {
        RegistryKey<World> currentLocation = json.has("dimension") ? RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(JSONUtils.getString(json, "dimension"))) : null;
        return new TakeoffTardisTrigger.Instance(entityPredicate, currentLocation);
    }

    public void testForAll(ServerPlayerEntity player, RegistryKey<World> currentLocation) {
        this.triggerListeners(player, (instance) -> instance.test(player, currentLocation));
    }

    public static class Instance extends CriterionInstance {
        @Nullable
        private final RegistryKey<World> currentLocation;

        public Instance(EntityPredicate.AndPredicate entityPredicate, RegistryKey<World> currentLocation) {
            super(TakeoffTardisTrigger.ID, entityPredicate);
            this.currentLocation = currentLocation;
        }

        public boolean test(ServerPlayerEntity player, RegistryKey<World> currentLocation) {
            return this.currentLocation == null || this.currentLocation == currentLocation;
        }

        @Override
        public JsonObject serialize(ConditionArraySerializer conditions)
        {
            JsonObject object = super.serialize(conditions);

            if(this.currentLocation != null)
                object.addProperty("dimension", this.currentLocation.getLocation().toString());

            return object;
        }
    }
}

package net.mistersecret312.temporal_api.triggers;

import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.tardis.mod.flight.FlightEventFactory;
import net.tardis.mod.registries.FlightEventRegistry;

import javax.annotation.Nullable;

public class SucceedFlightEventTrigger extends AbstractCriterionTrigger<SucceedFlightEventTrigger.Instance>
{
    private static final ResourceLocation ID = new ResourceLocation("tardis","succeed_flight_event");

    public ResourceLocation getId() {
        return ID;
    }

    public SucceedFlightEventTrigger.Instance deserializeTrigger(JsonObject json, EntityPredicate.AndPredicate entityPredicate, ConditionArrayParser conditionsParser) {
        FlightEventFactory registrykey = json.has("flight_event") ? FlightEventRegistry.FLIGHT_EVENT_REGISTRY.get().getValue(new ResourceLocation(JSONUtils.getString(json, "flight_event"))) : null;
        Boolean success = json.has("success") ? JSONUtils.getBoolean(json, "success") : null;
        return new SucceedFlightEventTrigger.Instance(entityPredicate, registrykey, success);
    }

    public void testForAll(ServerPlayerEntity player, FlightEventFactory flightEvent, Boolean success) {
        this.triggerListeners(player, (instance) -> instance.test(flightEvent, success));
    }

    public static class Instance extends CriterionInstance {
        @Nullable
        private final FlightEventFactory flightEvent;
        @Nullable
        private final Boolean success;

        public Instance(EntityPredicate.AndPredicate entityPredicate, @Nullable FlightEventFactory flightEvent, @Nullable Boolean success) {
            super(SucceedFlightEventTrigger.ID, entityPredicate);
            this.flightEvent = flightEvent;
            this.success = success;
        }

        public boolean test(FlightEventFactory flightEvent, Boolean success) {
            return (this.flightEvent == null || this.flightEvent == flightEvent) && (this.success == null || this.success == success);
        }

        public JsonObject serialize(ConditionArraySerializer conditions) {
            JsonObject jsonobject = super.serialize(conditions);
            if(this.flightEvent != null)
                jsonobject.addProperty("flight_event", String.valueOf(this.flightEvent.getRegistryName()));

            if(this.success != null)
                jsonobject.addProperty("success", this.success);

            return jsonobject;
        }
    }
}

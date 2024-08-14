package net.mistersecret312.temporal_api;

import net.minecraft.advancements.CriteriaTriggers;
import net.mistersecret312.temporal_api.triggers.*;

public class AdvancementTriggerInit
{
    public static final LandTardisTrigger LAND = new LandTardisTrigger();
    public static final TakeoffTardisTrigger TAKEOFF = new TakeoffTardisTrigger();
    public static final SucceedFlightEventTrigger FLIGHT_EVENT = new SucceedFlightEventTrigger();

    public static void init()
    {
        CriteriaTriggers.register(LAND);
        CriteriaTriggers.register(TAKEOFF);
        CriteriaTriggers.register(FLIGHT_EVENT);
    }
}

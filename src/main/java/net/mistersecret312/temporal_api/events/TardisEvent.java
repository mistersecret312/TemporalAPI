package net.mistersecret312.temporal_api.events;

import net.minecraftforge.eventbus.api.Event;
import net.tardis.mod.cap.ITardisWorldData;
import net.tardis.mod.tileentities.ConsoleTile;

public class TardisEvent extends Event {

    private final ConsoleTile console;
    public TardisEvent(ConsoleTile tile){
        this.console = tile;
    }

    public ConsoleTile getConsole() {
        return console;
    }

    /**
     *If canceled, TARDIS will attempt to take off on the next tick, firing this event once more, until it isn't canceled
     */
    public static class TakeoffEvent extends TardisEvent
    {
        public boolean action;
        public TakeoffEvent(ConsoleTile tile) {
            super(tile);
        }

        @Override
        public boolean isCancelable() {
            return true;
        }
    }

    /**
     * If canceled, TARDIS will attempt to land on the next tick, firing this event once more, until it isn't canceled.
     */
    public static class LandEvent extends TardisEvent
    {
        public LandEvent(ConsoleTile tile) {
            super(tile);
        }

        @Override
        public boolean isCancelable() {
            return true;
        }
    }


    /**
     * Fired after NTM's calculation for TARDIS Speed, measured in Blocks per Tick and calculated each tick.
     * Default is about 200 blocks per second(10 per tick) at full throttle.
     */
    public static class SpeedCalculationEvent extends TardisEvent
    {
        public float speed;
        public SpeedCalculationEvent(ConsoleTile tile, float speed) {
            super(tile);
            this.speed = speed;
        }

        public float getSpeed() {
            return speed;
        }
    }

    /**
     * Fired after NTM's calculation for In-Flight fuel usage, calculated each tick.
     * Theoretical maximum of 20 AU per second(1 per tick)
     * Practical maximum of 0.5 AU per second(0.025 per tick) with full throttle and stabilizers.
     * Practical minimum(while still flying) of 0.025 AU per second(0.00125 AU per tick) with minimal throttle and no stabilizers.
     */
    public static class FuelUseCalculationEvent extends TardisEvent
    {
        public float fuelUse;
        public FuelUseCalculationEvent(ConsoleTile tile, float fuelUse) {
            super(tile);
            this.fuelUse = fuelUse;
        }

        public float getFuelUse() {
            return fuelUse;
        }
    }
    /**
     * This is fired before NTM fills the Engine with Leaky Capacitors.
     * If canceled, won't let NTM fill the engine with Leaky Capacitors.
     * This is equivalent to an event that is run when TARDIS gets created because it also provides the Console.
     */
    public static class EngineFillEvent extends TardisEvent
    {
        private final ITardisWorldData data;
        public EngineFillEvent(ConsoleTile tile, ITardisWorldData data) {
            super(tile);
            this.data = data;
        }

        public ITardisWorldData getData() {
            return data;
        }

        @Override
        public boolean isCancelable() {
            return true;
        }
    }

}

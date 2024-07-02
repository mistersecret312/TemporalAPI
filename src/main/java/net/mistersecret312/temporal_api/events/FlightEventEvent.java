package net.mistersecret312.temporal_api.events;

import net.minecraftforge.eventbus.api.Event;
import net.tardis.mod.controls.AbstractControl;
import net.tardis.mod.flight.FlightEvent;
import net.tardis.mod.tileentities.ConsoleTile;

public class FlightEventEvent extends Event {

    private final FlightEvent event;
    private final ConsoleTile console;

    public FlightEventEvent(FlightEvent event, ConsoleTile console){
        this.event = event;
        this.console = console;
    }

    public ConsoleTile getConsole() {
        return console;
    }

    public FlightEvent getEvent() {
        return event;
    }


    /**
     * If canceled, NTM will not set the event.
     * Only applies to random events during flight.
     */
    public static class StartFlightEvent extends FlightEventEvent {
        public StartFlightEvent(FlightEvent event, ConsoleTile console){
            super(event, console);
        }

        @Override
        public boolean isCancelable() {
            return true;
        }
    }

    public static class SuccessFlightEvent extends FlightEventEvent {
        public SuccessFlightEvent(FlightEvent event, ConsoleTile console){
            super(event, console);
        }
    }

    public static class FailFlightEvent extends FlightEventEvent {
        public FailFlightEvent(FlightEvent event, ConsoleTile console){
            super(event, console);
        }
    }
}

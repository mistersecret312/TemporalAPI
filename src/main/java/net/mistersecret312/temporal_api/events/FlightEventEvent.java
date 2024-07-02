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
     * If not canceled, NTM will set the event as per usual.
     */
    public static class FlightEventStartEvent extends FlightEventEvent {
        public FlightEventStartEvent(FlightEvent event, ConsoleTile console){
            super(event, console);
        }

        @Override
        public boolean isCancelable() {
            return true;
        }
    }

    public static class FlightEventSuccessEvent extends FlightEventEvent {
        public FlightEventSuccessEvent(FlightEvent event, ConsoleTile console){
            super(event, console);
        }
    }

    public static class FlightEventFailEvent extends FlightEventEvent {
        public FlightEventFailEvent(FlightEvent event, ConsoleTile console){
            super(event, console);
        }
    }
}

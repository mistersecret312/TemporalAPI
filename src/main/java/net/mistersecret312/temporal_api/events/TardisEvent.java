package net.mistersecret312.temporal_api.events;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.Event;
import net.tardis.mod.misc.SpaceTimeCoord;
import net.tardis.mod.tileentities.ConsoleTile;

public class TardisEvent extends Event {

    private final ConsoleTile console;
    public TardisEvent(ConsoleTile tile){
        this.console = tile;
    }

    public ConsoleTile getConsole() {
        return console;
    }

    public static class TardisCreatedEvent extends TardisEvent{

        public TardisCreatedEvent(ConsoleTile tile) {
            super(tile);
        }
    }

}

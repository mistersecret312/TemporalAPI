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

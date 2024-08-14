package net.mistersecret312.temporal_api;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class TemporalAPIConfig
{
    public static final TemporalAPIConfig.Common COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;
    public static TemporalAPIConfig.Client CLIENT;
    public static ForgeConfigSpec CLIENT_SPEC;

    public static final TemporalAPIConfig.Server SERVER;
    public static final ForgeConfigSpec SERVER_SPEC;

    static 
    {
        final Pair<TemporalAPIConfig.Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(TemporalAPIConfig.Common::new);
        COMMON = specPair.getLeft();
        COMMON_SPEC = specPair.getRight();

        Pair<TemporalAPIConfig.Client, ForgeConfigSpec> specClientPair = new ForgeConfigSpec.Builder().configure(Client::new);
        CLIENT_SPEC = specClientPair.getRight();
        CLIENT = specClientPair.getLeft();

        final Pair<TemporalAPIConfig.Server, ForgeConfigSpec> specServerPair = new ForgeConfigSpec.Builder().configure(TemporalAPIConfig.Server::new);
        SERVER = specServerPair.getLeft();
        SERVER_SPEC = specServerPair.getRight();
    }

    public static class Common
    {
        public Common(ForgeConfigSpec.Builder builder)
        {
            builder.push("Common Settings");
        }
    }

    public static class Client
    {
        public ForgeConfigSpec.ConfigValue<Boolean> useHotbarForManual;

        public Client(ForgeConfigSpec.Builder builder)
        {
            builder.push("Client Settings");

            useHotbarForManual = builder.translation("config.tardis.useHotbarForManual")
                    .comment("A toggle to use the entire hotbar for rendering the control names instead of just the hand for a manual")
                    .define("useHotbarForManual", false, Boolean.class::isInstance);
        }
    }

    public static class Server
    {
        public ForgeConfigSpec.ConfigValue<Integer> baseSpeed;

        public Server(ForgeConfigSpec.Builder builder)
        {
            builder.push("Server Settings");

            baseSpeed = builder.translation("config.tardis.baseSpeed")
                    .comment("The base flight speed of a TARDIS, in blocks per tick")
                    .define("baseSpeed", 10, Integer.class::isInstance);
        }
    }
}

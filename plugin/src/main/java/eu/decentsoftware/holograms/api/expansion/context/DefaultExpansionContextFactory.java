package eu.decentsoftware.holograms.api.expansion.context;

import eu.decentsoftware.holograms.api.commands.CommandManager;
import eu.decentsoftware.holograms.api.expansion.Expansion;
import eu.decentsoftware.holograms.nms.NmsPacketListenerService;

import java.util.logging.Logger;

public class DefaultExpansionContextFactory implements ExpansionContextFactory {
    private final CommandManager commandManager;
    private final NmsPacketListenerService packetListenerService;
    private final Logger logger;

    public DefaultExpansionContextFactory(
            CommandManager commandManager, NmsPacketListenerService packetListenerService, Logger logger) {
        this.commandManager = commandManager;
        this.packetListenerService = packetListenerService;
        this.logger = logger;
    }

    @Override
    public ExpansionContext createExpansionContext(Expansion expansion) {
        return new DefaultExpansionContext(commandManager, packetListenerService, logger);
    }
}

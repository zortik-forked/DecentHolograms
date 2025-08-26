package eu.decentsoftware.holograms.api.expansion.context;

import eu.decentsoftware.holograms.api.commands.CommandManager;
import eu.decentsoftware.holograms.api.expansion.Expansion;

public class DefaultExpansionContextFactory implements ExpansionContextFactory {
    private final CommandManager commandManager;

    public DefaultExpansionContextFactory(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public ExpansionContext createExpansionContext(Expansion expansion) {
        return new DefaultExpansionContext(commandManager);
    }
}

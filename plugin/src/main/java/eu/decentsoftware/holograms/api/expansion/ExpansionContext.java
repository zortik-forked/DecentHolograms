package eu.decentsoftware.holograms.api.expansion;

import eu.decentsoftware.holograms.api.commands.DecentCommand;

/**
 * Per-expansion context to hold resources lifecycle, tied to the expansion.
 *
 * @author ZorTik
 */
public interface ExpansionContext {

    /**
     * Registers a command.
     *
     * @param command the command to register
     */
    void registerCommand(DecentCommand command);

    /**
     * Unregisters a command.
     *
     * @param command the command to unregister
     */
    boolean unregisterCommand(DecentCommand command);

    /**
     * Cleans up all associated resources with the holding expansion.
     */
    void clear();
}

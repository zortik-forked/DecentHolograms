package eu.decentsoftware.holograms.api.expansion.context;

import eu.decentsoftware.holograms.api.commands.CommandManager;
import eu.decentsoftware.holograms.api.commands.DecentCommand;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * Default implementation of the {@link ExpansionContext} interface.
 *
 * @author ZorTik
 */
public class DefaultExpansionContext implements ExpansionContext {
    private final CommandManager commandManager;
    private final Map<UUID, Runnable> commandUnregisterCallbacks;
    private final List<ExpansionContextEventHandler> eventHandlers;

    private boolean closed;

    public DefaultExpansionContext(CommandManager commandManager) {
        this.commandManager = commandManager;
        this.commandUnregisterCallbacks = new ConcurrentHashMap<>();
        this.eventHandlers = new CopyOnWriteArrayList<>();
        this.closed = false;
    }

    @Override
    public UUID registerCommand(RegisterCommandCall registerCommandCall) {
        Runnable unregisterCallback = registerCommandReturnCallback(registerCommandCall);

        UUID registrationId = UUID.randomUUID();
        commandUnregisterCallbacks.put(registrationId, unregisterCallback);

        return registrationId;
    }

    /**
     * Registers the command specified in the given {@link RegisterCommandCall} and returns a callback
     * to unregister it.
     *
     * @param registerCommandCall the command registration details
     * @return a {@link Runnable} that, when run, will unregister the command
     */
    private @NotNull Runnable registerCommandReturnCallback(RegisterCommandCall registerCommandCall) {
        DecentCommand command = registerCommandCall.getCommand();
        DecentCommand parent = registerCommandCall.getParent();

        Runnable unregisterCallback;
        if (parent == null) {
            commandManager.registerCommand(command);

            unregisterCallback = () -> commandManager.unregisterCommand(command.getName());
        } else {
            parent.addSubCommand(command);

            unregisterCallback = () -> parent.removeSubCommand(command);
        }

        return unregisterCallback;
    }

    @Override
    public void unregisterCommand(UUID registrationId) {
        Runnable unregisterCallback = commandUnregisterCallbacks.remove(registrationId);

        if (unregisterCallback != null) {
            unregisterCallback.run();
        }
    }

    @Override
    public void addContextEventHandler(ExpansionContextEventHandler handler) {
        eventHandlers.add(handler);
    }

    private void callEventHandlers(Consumer<ExpansionContextEventHandler> call) {
        for (ExpansionContextEventHandler handler : eventHandlers) {
            try {
                call.accept(handler);
            } catch (Exception e) {
                // TODO: Log
            }
        }
    }

    @Override
    public void close() {
        if (isClosed()) {
            throw new IllegalStateException("ExpansionContext is already closed.");
        }

        for (Runnable unregisterCallback : commandUnregisterCallbacks.values()) {
            unregisterCallback.run();
        }
        commandUnregisterCallbacks.clear();

        closed = true;

        callEventHandlers(handler -> handler.onContextClosed(this));
    }

    @Override
    public boolean isClosed() {
        return closed;
    }
}

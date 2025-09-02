package eu.decentsoftware.holograms.api.expansion.registry;

import eu.decentsoftware.holograms.api.expansion.Expansion;
import eu.decentsoftware.holograms.api.expansion.ExpansionActivator;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class DefaultExpansionRegistry implements ExpansionRegistry {
    private final ExpansionActivator activator;
    private final Plugin plugin;
    private final Map<String, Expansion> expansions;
    private final Map<Plugin, List<Expansion>> expansionsForPlugin;

    public DefaultExpansionRegistry(ExpansionActivator activator, Plugin plugin) {
        this.activator = activator;
        this.plugin = plugin;
        this.expansions = new ConcurrentHashMap<>();
        this.expansionsForPlugin = new ConcurrentHashMap<>();
    }

    @Override
    public void registerExpansion(Expansion expansion) {
        if (expansions.containsKey(expansion.getId())) {
            throw new IllegalArgumentException("An expansion with the id " + expansion.getId() + " is already registered.");
        }

        Plugin plugin = Optional.ofNullable(expansion.getPlugin()).orElse(this.plugin);
        if (plugin == null) {
            throw new IllegalStateException("Could not resolve plugin for expansion " + expansion.getName() + ". " +
                    "Please set the plugin in the expansion or add a suitable plugin resolver.");
        }

        activator.activateExpansion(expansion);

        expansions.put(expansion.getId(), expansion);
        expansionsForPlugin.computeIfAbsent(plugin, k -> new CopyOnWriteArrayList<>()).add(expansion);
    }

    @Override
    public @Nullable Expansion unregisterExpansion(String id) {
        Expansion expansion = expansions.remove(id);
        if (expansion != null) {
            activator.deactivateExpansion(expansion);
        }

        return expansion;
    }

    @Override
    public void unregisterExpansionsForPlugin(Plugin plugin) {
        List<Expansion> linkedExpansions = expansionsForPlugin.get(plugin);
        if (linkedExpansions == null) {
            return;
        }

        new ArrayList<>(linkedExpansions).forEach(expansion -> unregisterExpansion(expansion.getId()));
    }

    @Override
    public @Nullable Expansion getExpansion(String id) {
        return expansions.get(id);
    }

    @Override
    public @Unmodifiable List<Expansion> getAllExpansions() {
        List<Expansion> list = new ArrayList<>(expansions.values());

        return Collections.unmodifiableList(list);
    }
}

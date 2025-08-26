package eu.decentsoftware.holograms.api.expansion;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultExpansionRegistry implements ExpansionRegistry {
    private final Map<String, Expansion> expansions;

    public DefaultExpansionRegistry() {
        this.expansions = new ConcurrentHashMap<>();
    }

    @Override
    public void registerExpansion(Expansion expansion) {
        if (expansions.containsKey(expansion.getId())) {
            throw new IllegalArgumentException("An expansion with the id " + expansion.getId() + " is already registered.");
        }

        expansions.put(expansion.getId(), expansion);
    }

    @Override
    public @Nullable Expansion unregisterExpansion(String id) {
        return expansions.remove(id);
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

package eu.decentsoftware.holograms.api.expansion;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

public interface ExpansionRegistry {

    /**
     * Registers a new expansion.
     *
     * @param expansion the expansion to register
     * @throws IllegalArgumentException if an expansion with the same ID is already registered
     */
    void registerExpansion(Expansion expansion);

    /**
     * Unregisters an expansion by its ID.
     *
     * @param id the ID of the expansion to unregister
     * @return the unregistered expansion, or null if no expansion with the given ID was found
     */
    @Nullable
    Expansion unregisterExpansion(String id);

    /**
     * Gets an expansion by its ID.
     *
     * @param id the ID of the expansion to get
     * @return the expansion, or null if no expansion with the given ID was found
     */
    @Nullable
    Expansion getExpansion(String id);

    /**
     * Gets a list of all registered expansions.
     *
     * @return a list of all registered expansions
     */
    @Unmodifiable
    List<Expansion> getAllExpansions();
}

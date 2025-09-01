package eu.decentsoftware.holograms.api.expansion.external;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ExternalExpansionService {

    /**
     * Loads an expansion package by its name.
     *
     * @param name The name of the expansion package to load.
     * @return The loaded expansion package, or null if not found.
     */
    @Nullable
    ExternalExpansionPackage loadExpansionPackage(String name);

    /**
     * Unloads an expansion package by its name.
     *
     * @param name The name of the expansion package to unload.
     */
    void unloadExpansionPackage(String name);

    /**
     * Activates an expansion package by its name.
     *
     * @param name The name of the expansion package to activate.
     * @return true if the expansion package was successfully activated, false otherwise.
     */
    boolean activateExpansionPackage(String name);

    /**
     * Deactivates an expansion package by its name.
     *
     * @param name The name of the expansion package to deactivate.
     * @return true if the expansion package was successfully deactivated, false otherwise.
     */
    boolean deactivateExpansionPackage(String name);

    /**
     * Checks if an expansion package is activated.
     *
     * @param name The name of the expansion package.
     * @return true if the expansion package is activated, false otherwise.
     */
    boolean isExpansionPackageActivated(String name);

    /**
     * Returns an expansion package if it is loaded.
     *
     * @param name The name of the expansion package.
     * @return The loaded expansion package, or null if not loaded.
     */
    @Nullable
    ExternalExpansionPackage getLoadedExpansionPackage(String name);

    /**
     * Gets a list of all available expansion packages.
     *
     * @return A list of all available expansion packages.
     */
    List<String> getAvailableExpansionPackages();
}

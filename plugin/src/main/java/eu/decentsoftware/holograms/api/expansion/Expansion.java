package eu.decentsoftware.holograms.api.expansion;

import eu.decentsoftware.holograms.api.context.AppContext;
import eu.decentsoftware.holograms.api.expansion.requirement.ExpansionRequirement;

import java.util.Collection;
import java.util.Collections;

public interface Expansion {

    /**
     * Called when the expansion is being enabled.
     *
     * @param context the expansion context
     * @param appContext the application context
     */
    default void onEnable(ExpansionContext context, AppContext appContext) {
        // Bootup and registration logic
    }

    /**
     * Called when the expansion is being disabled.
     *
     * @param context the expansion context
     * @param appContext the application context
     */
    default void onDisable(ExpansionContext context, AppContext appContext) {
        // Auto-cleanup by default
        context.clear();
    }

    /**
     * Returns the name of this expansion.
     *
     * @return the name of the expansion
     */
    String getName();

    /**
     * Returns a brief description of this expansion.
     *
     * @return the description of the expansion
     */
    String getDescription();

    /**
     * Returns the author of this expansion.
     *
     * @return the author of the expansion
     */
    String getAuthor();

    /**
     * Returns the version of this expansion.
     *
     * @return the version of the expansion
     */
    String getVersion();

    /**
     * Returns a collection of requirements that must be met for this expansion to be able to be enabled.
     *
     * @return a collection of expansion requirements
     */
    default Collection<? extends ExpansionRequirement> getRequirements() {
        return Collections.emptyList();
    }
}

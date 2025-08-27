package eu.decentsoftware.holograms.api.expansion.external;

import eu.decentsoftware.holograms.api.expansion.Expansion;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

public interface ExternalExpansionPackage {

    /**
     * Gets the name of this expansion package.
     *
     * @return Name of the expansion package.
     */
    String getName();

    /**
     * Gets a list of all expansions provided by this package.
     *
     * @return List of expansions.
     */
    @Unmodifiable
    List<? extends Expansion> getExpansions();
}

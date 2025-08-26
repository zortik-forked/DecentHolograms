package eu.decentsoftware.holograms.api.expansion.source;

import eu.decentsoftware.holograms.api.expansion.Expansion;

import java.util.Collection;

public interface ExpansionSource {

    /**
     * Gets a collection of all expansions available from this source.
     *
     * @return a collection of all expansions
     */
    Collection<? extends Expansion> getExpansions();
}

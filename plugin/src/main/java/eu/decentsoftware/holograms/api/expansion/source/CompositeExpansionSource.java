package eu.decentsoftware.holograms.api.expansion.source;

import eu.decentsoftware.holograms.api.expansion.Expansion;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * An expansion source that aggregates multiple expansion sources into one.
 *
 * @author ZorTik
 */
public class CompositeExpansionSource implements ExpansionSource {
    private final Collection<ExpansionSource> sources;

    public CompositeExpansionSource(Collection<ExpansionSource> sources) {
        this.sources = sources;
    }

    @Override
    public Collection<? extends Expansion> getExpansions() {
        return sources
                .stream()
                .flatMap(source -> source.getExpansions().stream())
                .collect(Collectors.toList());
    }
}

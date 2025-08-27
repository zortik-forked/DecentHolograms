package eu.decentsoftware.holograms.api.expansion.external;

import eu.decentsoftware.holograms.api.expansion.Expansion;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ExternalExpansionPackageImpl implements ExternalExpansionPackage {
    private final String name;
    private final List<? extends Expansion> expansions;

    public ExternalExpansionPackageImpl(String name, Iterable<? extends Expansion> expansions) {
        this.name = name;
        this.expansions = StreamSupport.stream(expansions.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public String getName() {
        return name;
    }

    @Unmodifiable
    @Override
    public List<? extends Expansion> getExpansions() {
        return Collections.unmodifiableList(expansions);
    }
}

package eu.decentsoftware.holograms.api.expansion.context;

import eu.decentsoftware.holograms.api.expansion.Expansion;

public interface ExpansionContextFactory {

    ExpansionContext createExpansionContext(Expansion expansion);
}

package eu.decentsoftware.holograms.api.expansion.external;

import eu.decentsoftware.holograms.api.expansion.Expansion;
import eu.decentsoftware.holograms.api.expansion.ExpansionActivator;
import eu.decentsoftware.holograms.api.expansion.ExpansionLoader;
import eu.decentsoftware.holograms.api.expansion.ExpansionRegistry;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class DefaultExternalExpansionService implements ExternalExpansionService {
    private final File expansionDirectory;
    private final ExpansionLoader expansionLoader;
    private final ExpansionRegistry expansionRegistry;
    private final ExpansionActivator expansionActivator;

    private final Map<String, ExternalExpansionPackage> loadedExpansionPackages;

    /**
     * Creates a new DefaultExternalExpansionService with the specified expansion directory.
     *
     * @param expansionDirectory the directory where expansion packages (jars) are located
     * @param expansionLoader the loader to use for loading expansions from jar files
     * @param expansionRegistry the registry to register loaded expansions
     */
    public DefaultExternalExpansionService(
            File expansionDirectory,
            ExpansionLoader expansionLoader,
            ExpansionRegistry expansionRegistry, ExpansionActivator expansionActivator) {
        this.expansionDirectory = expansionDirectory;
        this.expansionLoader = expansionLoader;
        this.expansionRegistry = expansionRegistry;
        this.expansionActivator = expansionActivator;
        this.loadedExpansionPackages = new ConcurrentHashMap<>();
    }

    @Nullable
    @Override
    public ExternalExpansionPackage loadExpansionPackage(String name) {
        if (loadedExpansionPackages.containsKey(name)) {
            return null;
        }

        File file = new File(expansionDirectory, name + ".jar");
        if (!file.exists() || !file.isFile()) {
            return null;
        }

        Iterable<? extends Expansion> expansions = expansionLoader.loadExpansions(file);

        ExternalExpansionPackage expansionPackage = new ExternalExpansionPackageImpl(name, expansions);
        loadedExpansionPackages.put(name, expansionPackage);

        expansions.forEach(expansionRegistry::registerExpansion);

        return expansionPackage;
    }

    @Override
    public void unloadExpansionPackage(String name) {
        ExternalExpansionPackage expansionPackage = loadedExpansionPackages.remove(name);

        if (expansionPackage != null) {
            expansionPackage.getExpansions().forEach(expansion ->
                    expansionRegistry.unregisterExpansion(expansion.getId()));
        }
    }

    @Override
    public boolean activateExpansionPackage(String name) {
        if (isExpansionPackageActivated(name)) {
            return false;
        }

        ExternalExpansionPackage expansionPackage = getLoadedExpansionPackage(name);
        if (expansionPackage == null) {
            return false;
        }

        expansionPackage.getExpansions().forEach(expansionActivator::activateExpansion);
        return true;
    }

    @Override
    public boolean deactivateExpansionPackage(String name) {
        if (!isExpansionPackageActivated(name)) {
            return false;
        }

        ExternalExpansionPackage expansionPackage = getLoadedExpansionPackage(name);
        if (expansionPackage == null) {
            return false;
        }

        expansionPackage.getExpansions().forEach(expansionActivator::deactivateExpansion);
        return true;
    }

    @Override
    public boolean isExpansionPackageActivated(String name) {
        ExternalExpansionPackage expansionPackage = getLoadedExpansionPackage(name);
        if (expansionPackage == null) {
            return false;
        }

        return expansionPackage.getExpansions()
                .stream()
                .anyMatch(expansionActivator::isExpansionActivated);
    }

    @Override
    public @Nullable ExternalExpansionPackage getLoadedExpansionPackage(String name) {
        return loadedExpansionPackages.get(name);
    }

    @Override
    public List<String> getAvailableExpansionPackages() {
        String[] names = expansionDirectory.list();
        if (names == null) {
            return Collections.emptyList();
        }

        return Arrays.stream(names)
                .filter(name -> name.endsWith(".jar"))
                .map(name -> name.substring(0, name.length() - 4))
                .collect(Collectors.toList());
    }
}

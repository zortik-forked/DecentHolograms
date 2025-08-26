package eu.decentsoftware.holograms.api.expansion;

import eu.decentsoftware.holograms.api.context.AppContext;
import eu.decentsoftware.holograms.api.context.AppContextFactory;
import eu.decentsoftware.holograms.api.expansion.context.ExpansionContext;
import eu.decentsoftware.holograms.api.expansion.context.ExpansionContextFactory;
import eu.decentsoftware.holograms.api.expansion.requirement.CheckResult;
import eu.decentsoftware.holograms.api.expansion.requirement.ExpansionRequirement;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Default implementation of the ExpansionActivator.
 *
 * @author ZorTik
 */
public class DefaultExpansionActivator implements ExpansionActivator {
    private final AppContextFactory appContextFactory;
    private final ExpansionContextFactory expansionContextFactory;

    private final Map<String, ExpansionContext> contexts;

    public DefaultExpansionActivator(
            AppContextFactory appContextFactory, ExpansionContextFactory expansionContextFactory) {
        this.appContextFactory = appContextFactory;
        this.expansionContextFactory = expansionContextFactory;
        this.contexts = new ConcurrentHashMap<>();
    }

    @Override
    public boolean activate(Expansion expansion) {
        if (isActivated(expansion)) {
            return false;
        }

        AppContext appContext = appContextFactory.createAppContext();
        if (!passesRequirements(expansion, appContext)) {
            return false;
        }

        return doActivate(expansion, appContext);
    }

    /**
     * Check if all requirements are met.
     *
     * @param expansion the expansion to check
     * @param appContext the application context
     * @return true if all requirements are met, false otherwise
     */
    private static boolean passesRequirements(Expansion expansion, AppContext appContext) {
        for (ExpansionRequirement requirement : expansion.getRequirements()) {
            CheckResult checkResult = requirement.canEnable(expansion, appContext);
            if (checkResult.isSuccess()) {
                continue;
            }

            // TODO: Log the reason
            return false;
        }

        return true;
    }

    /**
     * Actually activate the expansion.
     *
     * @param expansion the expansion to activate
     * @param appContext the application context
     * @return true if activation was successful, false otherwise
     */
    private boolean doActivate(Expansion expansion, AppContext appContext) {
        ExpansionContext context = expansionContextFactory.createExpansionContext(expansion);
        try {
            expansion.onEnable(context, appContext);
        } catch (Exception e) {
            context.close();
            // TODO: Log

            return false;
        }

        // TODO: Log successful activation

        contexts.put(expansion.getId(), context);
        return true;
    }

    @Override
    public boolean deactivate(Expansion expansion) {
        ExpansionContext context = contexts.remove(expansion.getId());
        if (context == null) {
            return false;
        }

        AppContext appContext = appContextFactory.createAppContext();

        expansion.onDisable(context, appContext);
        if (!context.isClosed()) {
            context.close();

            // TODO: Log that he forgot to close the context
        }

        return true;
    }

    @Override
    public boolean isActivated(Expansion expansion) {
        return contexts.containsKey(expansion.getId());
    }
}

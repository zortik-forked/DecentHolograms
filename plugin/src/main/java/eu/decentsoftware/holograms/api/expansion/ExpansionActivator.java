package eu.decentsoftware.holograms.api.expansion;

/**
 * Interface for managing the activation and deactivation of expansions.
 *
 * @author ZorTik
 */
public interface ExpansionActivator {

    /**
     * Activates the given expansion.
     *
     * @param expansion the expansion to activate
     * @return true if the expansion was successfully activated, false otherwise
     */
    boolean activate(Expansion expansion);

    /**
     * Deactivates the given expansion.
     *
     * @param expansion the expansion to deactivate
     * @return true if the expansion was successfully deactivated, false otherwise
     */
    boolean deactivate(Expansion expansion);

    /**
     * Checks if the given expansion is activated.
     *
     * @param expansion the expansion to check
     * @return true if the expansion is activated, false otherwise
     */
    boolean isActivated(Expansion expansion);
}

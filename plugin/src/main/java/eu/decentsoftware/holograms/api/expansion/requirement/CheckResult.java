package eu.decentsoftware.holograms.api.expansion.requirement;

import org.jetbrains.annotations.Nullable;

/**
 * The result of a requirement check.
 */
public interface CheckResult {

    /**
     * Returns an error message if the requirement is not met, or null if it is met.
     *
     * @return the error message, or null
     */
    @Nullable
    String getErrorMessage();

    /**
     * Success state.
     *
     * @return true if the requirement is met, false otherwise
     */
    boolean isSuccess();

    /**
     * Creates a successful CheckResult.
     *
     * @return a successful CheckResult
     */
    static CheckResult success() {
        return new CheckResult() {
            @Override
            public @Nullable String getErrorMessage() {
                return null;
            }

            @Override
            public boolean isSuccess() {
                return true;
            }
        };
    }
}

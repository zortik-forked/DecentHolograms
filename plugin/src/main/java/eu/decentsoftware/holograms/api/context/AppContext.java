package eu.decentsoftware.holograms.api.context;

import eu.decentsoftware.holograms.api.utils.reflect.Version;
import org.bukkit.plugin.java.JavaPlugin;

public interface AppContext {

    Version getServerVersion();

    JavaPlugin getPlugin();
}

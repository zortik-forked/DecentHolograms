package eu.decentsoftware.holograms.test;

import eu.decentsoftware.holograms.api.context.AppContext;
import eu.decentsoftware.holograms.api.expansion.Expansion;
import eu.decentsoftware.holograms.api.expansion.context.ExpansionContext;
import eu.decentsoftware.holograms.api.utils.Log;
import org.bukkit.configuration.ConfigurationSection;

public class TestExpansion implements Expansion {

    @Override
    public void onEnable(ExpansionContext context, AppContext appContext) {
        Log.info("TestExpansion.onEnable");

        int x = context.getExpansionConfig().getSettings().getInt("x");
        Log.info("Config value x: " + x);
        int y = context.getExpansionConfig().getSettings().getInt("y");
        Log.info("Config value y: " + y);
    }

    @Override
    public void onDisable(ExpansionContext context, AppContext appContext) {
        Log.info("TestExpansion.onDisable");
    }

    @Override
    public void applyConfigurationDefaults(ConfigurationSection settings) {
        settings.set("x", 42);
        settings.set("y", 1);
    }

    @Override
    public boolean isEnabledByDefault() {
        return false;
    }

    @Override
    public String getId() {
        return "test";
    }

    @Override
    public String getName() {
        return "Test";
    }

    @Override
    public String getDescription() {
        return "Test expansion";
    }

    @Override
    public String getAuthor() {
        return "ZorTik";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }
}

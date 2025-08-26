package eu.decentsoftware.holograms.nms;

import eu.decentsoftware.holograms.nms.api.NmsAdapter;
import eu.decentsoftware.holograms.nms.api.NmsPacketListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This service is responsible for managing packet listener for online players.
 *
 * @author d0by
 * @since 2.9.0
 */
public class NmsPacketListenerService {

    private final NmsAdapter nmsAdapter;
    private final NmsPacketListener packetListener;
    private final NmsPlayerListener playerListener;

    private final Map<String, NmsPacketListenerDelegate> delegates;

    public NmsPacketListenerService(JavaPlugin plugin, NmsAdapter nmsAdapter, NmsPacketListener packetListener) {
        this.nmsAdapter = nmsAdapter;
        this.packetListener = packetListener;
        this.playerListener = new NmsPlayerListener(this);
        this.delegates = new ConcurrentHashMap<>();

        Bukkit.getPluginManager().registerEvents(playerListener, plugin);
        registerListenerForAllPlayers();
    }

    /**
     * Shutdown this service.
     * This method should be called when the plugin is being disabled.
     */
    public void shutdown() {
        HandlerList.unregisterAll(playerListener);
        unregisterListenerForAllPlayers();
    }

    /**
     * Register a packet listener for the given player.
     *
     * @param player The player.
     */
    void registerListener(Player player) {
        delegates.put(player.getName(), new NmsPacketListenerDelegate(Collections.singletonList(packetListener)));

        nmsAdapter.registerPacketListener(player, packetListener);
    }

    /**
     * Unregister a packet listener for the given player.
     *
     * @param player The player.
     */
    void unregisterListener(Player player) {
        delegates.remove(player.getName());

        nmsAdapter.unregisterPacketListener(player);
    }

    /**
     * Add a packet listener for the given player.
     *
     * @param player The player.
     * @param listener The listener to add.
     */
    public void addPacketListener(Player player, NmsPacketListener listener) {
        NmsPacketListenerDelegate delegate = delegates.get(player.getName());

        if (delegate != null) {
            delegate.addListener(listener);
        }
    }

    /**
     * Remove a packet listener for the given player.
     *
     * @param player The player.
     * @param listener The listener to remove.
     */
    public void removePacketListener(Player player, NmsPacketListener listener) {
        NmsPacketListenerDelegate delegate = delegates.get(player.getName());

        if (delegate != null) {
            delegate.removeListener(listener);
        }
    }

    private void registerListenerForAllPlayers() {
        Bukkit.getOnlinePlayers().forEach(this::registerListener);
    }

    private void unregisterListenerForAllPlayers() {
        Bukkit.getOnlinePlayers().forEach(this::unregisterListener);
    }

}

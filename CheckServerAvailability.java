package de.drache.limboPaper.Listener;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.drache.limboPaper.main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class CheckServerAvailability extends BukkitRunnable {

    private final String targetServer;
    // Constructor to pass in the player and server to check
    public CheckServerAvailability(String targetServer) {
        this.targetServer = targetServer;
    }

    /**
     * Sends a request to the Velocity proxy to check if a specific server is available.
     */
    public void checkServer() {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        /*out.writeUTF("ServerAvailable"); // Custom message to check server status
        out.writeUTF(targetServer);*/      // Name of the server to check
        out.writeUTF("Connect");
        out.writeUTF(targetServer);
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("limbo.admin")){
                return;
            }
            p.sendPluginMessage(main.plugin, "BungeeCord", out.toByteArray());
            p.sendMessage("§cChecking if main server is available");
        }
    }

    @Override
    public void run() {
        // Call the method to check the server availability
        checkServer();
    }

    /**
     * Start the task to check server availability every 2 seconds (40 ticks).
     */
    public void startTask(JavaPlugin plugin) {
        this.runTaskTimer(plugin, 0L, 40L); // 0L initial delay, 40L repeat every 2 seconds (40 ticks)
    }
}

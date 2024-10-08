
package de.drache.limboPaper;

import de.drache.limboPaper.LimboAdminListener.kickAll;
import de.drache.limboPaper.Listener.CancelEverything;
import de.drache.limboPaper.Listener.CheckServerAvailability;
import de.drache.limboPaper.Listener.PluginMessageListener;
import de.drache.limboPaper.Listener.PostWorldGen;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class main extends JavaPlugin {
    public static main plugin;

    @Override
    public void onEnable() {
        plugin = this;
        Objects.requireNonNull(this.getCommand("kickall")).setExecutor(new kickAll());
        getServer().getPluginManager().registerEvents(new CancelEverything(), this);
        WorldCreator creator = new WorldCreator("void_world");
        creator.generator(new PostWorldGen.VoidWorldGenerator());
        getServer().createWorld(creator);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        getServer().getMessenger().registerOutgoingPluginChannel(this, "velocity:main");

        getServer().getMessenger().registerIncomingPluginChannel(this, "velocity:main", new PluginMessageListener());
        CheckServerAvailability check = new CheckServerAvailability("lobby");
        check.startTask(main.plugin);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

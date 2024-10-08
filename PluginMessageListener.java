package de.drache.limboPaper.Listener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PluginMessageListener implements org.bukkit.plugin.messaging.PluginMessageListener {
    @Override
    public void onPluginMessageReceived(@NotNull String s, @NotNull Player player, @NotNull byte[] bytes) {
        if (!s.equals("velocity:main")) return;

        ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
        String command = in.readUTF();
        if (command.equals("sendToServer")) {
            Location loocation = player.getLocation();
            loocation.getWorld().spawnEntity(loocation, EntityType.CREEPER);
        }
    }
}

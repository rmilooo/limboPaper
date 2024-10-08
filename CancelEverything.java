package de.drache.limboPaper.Listener;

import de.drache.limboPaper.main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;

import java.util.HashMap;

public class CancelEverything implements Listener {

    // HashMap to track the last title send time for each player
    private final HashMap<Player, Long> titleCooldown = new HashMap<>();
    private static final long TITLE_COOLDOWN_TIME = 2000; // Cooldown time in milliseconds

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (hasPermission(event.getPlayer()) && event.hasChangedPosition()) {
            event.setCancelled(true);
            sendTitle(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Allow flight and set flying state for all players
        player.setAllowFlight(true);
        player.setFlying(true);

        // Set game mode if the player has permission
        if (hasPermission(player)) {
            player.setGameMode(GameMode.CREATIVE);
        }

        // Hide all other players from this player if they don't have permission

            for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
                if (otherPlayer != player) {  // Avoid hiding the player from themselves
                    if (!hasPermission(player)) {
                        player.hidePlayer(main.plugin, otherPlayer);
                    }// Hide other players from this player
                    otherPlayer.hidePlayer(main.plugin, player);  // Optionally, hide this player from others
                }
            }
        // Clear join message and send custom messages to the player
        event.setJoinMessage(null);
        player.sendMessage("§cYou joined the Limbo Server");
        sendTitle(player);

        // Teleport the player to the specified location
        player.teleport(player.getLocation().set(0, -200, 0));
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (hasPermission(event.getPlayer())) {
            event.setCancelled(true);  // Prevent block breaking
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            event.setCancelled(true);  // Prevent any damage to players
        }
    }
    @EventHandler
    public void onInventoryOpen(PlayerDeathEvent event) {
        event.setCancelled(true);
        event.setDeathMessage(null);  // Prevent death messages
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        event.getWorld().setClearWeatherDuration(2020000000);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (hasPermission(event.getPlayer())) {
            event.setCancelled(true);  // Prevent block placing
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (hasPermission(event.getPlayer())) {
            event.setCancelled(true);
            sendTitle(event.getPlayer());  // Prevent any interaction with blocks or items
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (hasPermission(event.getPlayer())) {
            event.setCancelled(true);  // Prevent dropping items
        }
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        if (hasPermission(event.getPlayer())) {
            event.setCancelled(true);  // Cancel all commands
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (hasPermission(event.getPlayer())) {
            event.setCancelled(true);  // Prevent chatting
            if (event.getMessage().toLowerCase().contains("help")) {
                sendTitle(event.getPlayer());
            } else {
                event.getPlayer().sendMessage("§cYou are not allowed to chat in Limbo.");
            }
        }
    }

    @EventHandler
    public void onPlayerOpenInventory(InventoryOpenEvent event) {
        if (hasPermission((Player) event.getPlayer())) {
            event.setCancelled(true);  // Prevent opening any inventories
        }
    }

    // Check if the player has the limbo.admin permission
    public static boolean hasPermission(Player player) {
        return !player.hasPermission("limbo.admin");

    }

    private void sendTitle(Player player) {
        long currentTime = System.currentTimeMillis();

        // Check if the player is on cooldown for sending titles
        if (!titleCooldown.containsKey(player) || (currentTime - titleCooldown.get(player) >= TITLE_COOLDOWN_TIME)) {
            player.sendTitle("§6§lYou are in Limbo", "§7It's a waiting server because the main server is restarting.", 10, 70, 20);
            // Update the last title send time for the player
            titleCooldown.put(player, currentTime);
        }
    }
}

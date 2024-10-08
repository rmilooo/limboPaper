package de.drache.limboPaper.LimboAdminListener;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class kickAll implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (s.equalsIgnoreCase("kickall")) {
            if (commandSender instanceof Player && commandSender.hasPermission("limbo.admin")) {
                for (org.bukkit.entity.Player player : commandSender.getServer().getOnlinePlayers()) {
                    if (player == commandSender) {
                        return false;
                    }
                    player.kickPlayer("§cLimboAdmin has kicked you!");
                }
            }
                return true;
            }
            return false;
        }
    }
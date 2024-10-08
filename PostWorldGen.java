package de.drache.limboPaper.Listener;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;

import java.util.Random;

public class PostWorldGen implements Listener {
    // Event to clear chunk generation to void
    @EventHandler
    public void onChunkPopulate(ChunkPopulateEvent event) {
        World world = event.getWorld();

        // Make sure we're in the "void_world" to clear chunks
        if (world.getName().equals("void_world")) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    for (int y = 0; y < world.getMaxHeight(); y++) {
                        Block block = event.getChunk().getBlock(x, y, z);
                        block.setType(Material.AIR);
                    }
                }
            }
        }
    }

    // Void world generator
    public static class VoidWorldGenerator extends ChunkGenerator {
        @Override
        public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {
            ChunkData chunkData = createChunkData(world);

            // Optionally, place a bedrock layer at Y=0
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    chunkData.setBlock(x, 0, z, Material.BEDROCK); // Bedrock at the bottom
                }
            }
            return chunkData;
        }

        @Override
        public boolean canSpawn(World world, int x, int z) {
            return true; // Allows spawning anywhere
        }

        @Override
        public Location getFixedSpawnLocation(World world, Random random) {
            return new Location(world, 0, 1, 0); // Spawn at (0, 1, 0)
        }
    }
}

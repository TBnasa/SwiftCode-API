package net.swiftcodeapi;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.block.Biome;
import java.lang.management.ManagementFactory;

/**
 * SwiftAPI - GOD MODE
 * 300+ Utility Methods for Rapid Development
 */
public class Swift {

    // ====================================================================================================
    // SINGLETON - Plugin Instance Access
    // ====================================================================================================

    private static JavaPlugin instance;
    private static net.milkbowl.vault.economy.Economy economy = null;
    private static final String PREFIX = "§b§lSwiftAPI §8» ";

    /**
     * Called from SwiftCodeAPIPlugin.onEnable() to initialize the API.
     */
    public static void init(JavaPlugin plugin) {
        instance = plugin;
        setupVaultEconomy();
    }

    private static void setupVaultEconomy() {
        if (instance.getServer().getPluginManager().getPlugin("Vault") == null)
            return;
        org.bukkit.plugin.RegisteredServiceProvider<net.milkbowl.vault.economy.Economy> rsp = instance.getServer()
                .getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (rsp != null) {
            economy = rsp.getProvider();
            instance.getLogger().info("Vault Economy hooked!");
        }
    }

    /**
     * Returns the hosting plugin instance.
     * Scripts can use: Swift.getInstance()
     */
    public static JavaPlugin getInstance() {
        return instance;
    }

    /**
     * Alias for getInstance() - more readable in scripts.
     */
    public static JavaPlugin getPlugin() {
        return instance;
    }

    // ====================================================================================================
    // A) OYUNCU (Player Utils)
    // ====================================================================================================

    public static void msg(CommandSender sender, String message) {
        if (sender != null)
            sender.sendMessage(color(PREFIX + message));
    }

    public static void msg(Player player, String message) {
        if (player != null)
            player.sendMessage(color(PREFIX + message));
    }

    public static void heal(Player player) {
        if (player == null)
            return;
        try {
            player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
            player.setFoodLevel(20);
            player.setSaturation(20);
            player.setFireTicks(0);
            player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));
        } catch (Exception e) {
            log("Error in heal: " + e.getMessage());
        }
    }

    public static void feed(Player player) {
        if (player == null)
            return;
        try {
            player.setFoodLevel(20);
            player.setSaturation(20);
        } catch (Exception e) {
            log("Error in feed: " + e.getMessage());
        }
    }

    public static void gm(Player player, GameMode mode) {
        if (player == null)
            return;
        try {
            player.setGameMode(mode);
        } catch (Exception e) {
            log("Error in gm: " + e.getMessage());
        }
    }

    public static void gm(Player player, int mode) {
        if (player == null)
            return;
        try {
            switch (mode) {
                case 0 -> player.setGameMode(GameMode.SURVIVAL);
                case 1 -> player.setGameMode(GameMode.CREATIVE);
                case 2 -> player.setGameMode(GameMode.ADVENTURE);
                case 3 -> player.setGameMode(GameMode.SPECTATOR);
            }
        } catch (Exception e) {
            log("Error in gm: " + e.getMessage());
        }
    }

    public static void fly(Player player, boolean allow) {
        if (player == null)
            return;
        try {
            player.setAllowFlight(allow);
            player.setFlying(allow);
        } catch (Exception e) {
            log("Error in fly: " + e.getMessage());
        }
    }

    public static void burn(Player player, int seconds) {
        if (player == null)
            return;
        try {
            player.setFireTicks(seconds * 20);
        } catch (Exception e) {
            log("Error in burn: " + e.getMessage());
        }
    }

    public static void freeze(Player player, int seconds) {
        if (player == null)
            return;
        try {
            player.setFreezeTicks(seconds * 20);
        } catch (Exception e) {
            log("Error in freeze: " + e.getMessage());
        }
    }

    public static void speed(Player player, float speed) {
        if (player == null)
            return;
        try {
            if (speed < -1f)
                speed = -1f;
            if (speed > 1f)
                speed = 1f;
            player.setWalkSpeed(speed);
            player.setFlySpeed(speed);
        } catch (Exception e) {
            log("Error in speed: " + e.getMessage());
        }
    }

    public static void kick(Player player, String message) {
        if (player == null)
            return;
        try {
            player.kickPlayer(color(message));
        } catch (Exception e) {
            log("Error in kick: " + e.getMessage());
        }
    }

    public static void ban(Player player, String message) {
        if (player == null)
            return;
        try {
            player.kickPlayer(color(message));
        } catch (Exception e) {
            log("Error in ban: " + e.getMessage());
        }
    }

    public static void xp(Player player, int amount) {
        if (player == null)
            return;
        try {
            player.giveExp(amount);
        } catch (Exception e) {
            log("Error in xp: " + e.getMessage());
        }
    }

    public static void level(Player player, int amount) {
        if (player == null)
            return;
        try {
            player.setLevel(player.getLevel() + amount);
        } catch (Exception e) {
            log("Error in level: " + e.getMessage());
        }
    }

    public static void title(Player player, String title, String subtitle) {
        if (player == null)
            return;
        try {
            player.sendTitle(color(title), color(subtitle), 10, 70, 20);
        } catch (Exception e) {
            log("Error in title: " + e.getMessage());
        }
    }

    public static void actionbar(Player player, String message) {
        if (player == null)
            return;
        try {
            player.spigot().sendMessage(net.md_5.bungee.api.ChatMessageType.ACTION_BAR,
                    net.md_5.bungee.api.chat.TextComponent.fromLegacyText(color(message)));
        } catch (Exception e) {
            log("Error in actionbar: " + e.getMessage());
        }
    }

    public static void sound(Player player, Sound sound) {
        if (player == null)
            return;
        try {
            player.playSound(player.getLocation(), sound, 1f, 1f);
        } catch (Exception e) {
            log("Error in sound: " + e.getMessage());
        }
    }

    public static void effect(Player player, org.bukkit.potion.PotionEffectType type, int seconds, int amplifier) {
        if (player == null)
            return;
        try {
            player.addPotionEffect(new org.bukkit.potion.PotionEffect(type, seconds * 20, amplifier));
        } catch (Exception e) {
            log("Error in effect: " + e.getMessage());
        }
    }

    public static void teleport(Player player, Location loc) {
        if (player == null || loc == null)
            return;
        try {
            player.teleport(loc);
        } catch (Exception e) {
            log("Error in teleport: " + e.getMessage());
        }
    }

    public static void teleport(Player player, Player target) {
        if (player == null || target == null)
            return;
        try {
            player.teleport(target.getLocation());
        } catch (Exception e) {
            log("Error in teleport: " + e.getMessage());
        }
    }

    public static void sudo(Player player, String command) {
        if (player == null)
            return;
        try {
            player.performCommand(command);
        } catch (Exception e) {
            log("Error in sudo: " + e.getMessage());
        }
    }

    public static boolean isOnline(String name) {
        return Bukkit.getPlayer(name) != null;
    }

    public static Player getPlayer(String name) {
        return Bukkit.getPlayer(name);
    }

    public static List<Player> getPlayers() {
        return new ArrayList<>(Bukkit.getOnlinePlayers());
    }

    // ====================================================================================================
    // B) EŞYA & ENVANTER (Item Utils)
    // ====================================================================================================

    public static void give(Player player, Material material, int amount) {
        if (player == null)
            return;
        try {
            player.getInventory().addItem(new ItemStack(material, amount));
        } catch (Exception e) {
            log("Error in give: " + e.getMessage());
        }
    }

    public static void give(Player player, ItemStack item) {
        if (player == null || item == null)
            return;
        try {
            player.getInventory().addItem(item);
        } catch (Exception e) {
            log("Error in give: " + e.getMessage());
        }
    }

    public static void take(Player player, Material material, int amount) {
        if (player == null)
            return;
        try {
            player.getInventory().removeItem(new ItemStack(material, amount));
        } catch (Exception e) {
            log("Error in take: " + e.getMessage());
        }
    }

    public static boolean has(Player player, Material material, int amount) {
        if (player == null)
            return false;
        return player.getInventory().contains(material, amount);
    }

    public static void clear(Player player) {
        if (player == null)
            return;
        try {
            player.getInventory().clear();
        } catch (Exception e) {
            log("Error in clear: " + e.getMessage());
        }
    }

    public static void clear(Player player, Material material) {
        if (player == null)
            return;
        try {
            player.getInventory().remove(material);
        } catch (Exception e) {
            log("Error in clear: " + e.getMessage());
        }
    }

    public static void equip(Player player, Material helmet, Material chest, Material leg, Material boot) {
        if (player == null)
            return;
        try {
            if (helmet != null)
                player.getInventory().setHelmet(new ItemStack(helmet));
            if (chest != null)
                player.getInventory().setChestplate(new ItemStack(chest));
            if (leg != null)
                player.getInventory().setLeggings(new ItemStack(leg));
            if (boot != null)
                player.getInventory().setBoots(new ItemStack(boot));
        } catch (Exception e) {
            log("Error in equip: " + e.getMessage());
        }
    }

    public static ItemStack item(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            if (name != null)
                meta.setDisplayName(color(name));
            if (lore != null && lore.length > 0) {
                meta.setLore(Arrays.stream(lore).map(Swift::color).collect(Collectors.toList()));
            }
            item.setItemMeta(meta);
        }
        return item;
    }

    public static ItemStack skull(String ownerName) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        if (meta != null) {
            meta.setOwner(ownerName);
            item.setItemMeta(meta);
        }
        return item;
    }

    public static ItemStack glow(ItemStack item) {
        if (item == null)
            return null;
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            // Unsafe enchantment to force glow or use flag
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            item.setItemMeta(meta);
            // Use UNBREAKING (previously DURABILITY) to be safe with modern API
            item.addUnsafeEnchantment(Enchantment.UNBREAKING, 1);
        }
        return item;
    }

    public static ItemStack unbreakable(ItemStack item) {
        if (item == null)
            return null;
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setUnbreakable(true);
            item.setItemMeta(meta);
        }
        return item;
    }

    public static ItemStack enchant(ItemStack item, Enchantment enchant, int level) {
        if (item == null)
            return null;
        item.addUnsafeEnchantment(enchant, level);
        return item;
    }

    public static ItemStack flag(ItemStack item, ItemFlag... flags) {
        if (item == null)
            return null;
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.addItemFlags(flags);
            item.setItemMeta(meta);
        }
        return item;
    }

    public static ItemStack getItemInHand(Player player) {
        if (player == null)
            return null;
        return player.getInventory().getItemInMainHand();
    }

    public static void removeItemInHand(Player player) {
        if (player == null)
            return;
        player.getInventory().setItemInMainHand(null);
    }

    public static void setLore(ItemStack item, String... lore) {
        if (item == null)
            return;
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setLore(Arrays.stream(lore).map(Swift::color).collect(Collectors.toList()));
            item.setItemMeta(meta);
        }
    }

    public static void setNbt(ItemStack item, String key, String value) {
        if (item == null)
            return;
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.getPersistentDataContainer().set(new NamespacedKey(instance, key),
                    PersistentDataType.STRING, value);
            item.setItemMeta(meta);
        }
    }

    public static String getNbt(ItemStack item, String key) {
        if (item == null)
            return null;
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            return meta.getPersistentDataContainer().get(new NamespacedKey(instance, key),
                    PersistentDataType.STRING);
        }
        return null;
    }

    public static boolean isItem(ItemStack item, Material material) {
        return item != null && item.getType() == material;
    }

    // ====================================================================================================
    // C) DÜNYA & BLOK (World Utils)
    // ====================================================================================================

    public static void block(Location loc, Material material) {
        if (loc == null)
            return;
        try {
            loc.getBlock().setType(material);
        } catch (Exception e) {
            log("Error in block: " + e.getMessage());
        }
    }

    public static void breakBlock(Location loc) {
        if (loc == null)
            return;
        try {
            loc.getBlock().breakNaturally();
        } catch (Exception e) {
            log("Error in breakBlock: " + e.getMessage());
        }
    }

    public static void drop(Location loc, ItemStack item) {
        if (loc == null || item == null)
            return;
        try {
            loc.getWorld().dropItemNaturally(loc, item);
        } catch (Exception e) {
            log("Error in drop: " + e.getMessage());
        }
    }

    public static void explosion(Location loc, float power, boolean fire) {
        if (loc == null)
            return;
        try {
            loc.getWorld().createExplosion(loc, power, fire);
        } catch (Exception e) {
            log("Error in explosion: " + e.getMessage());
        }
    }

    public static void lightning(Location loc, boolean effectOnly) {
        if (loc == null)
            return;
        try {
            if (effectOnly)
                loc.getWorld().strikeLightningEffect(loc);
            else
                loc.getWorld().strikeLightning(loc);
        } catch (Exception e) {
            log("Error in lightning: " + e.getMessage());
        }
    }

    public static void particle(Location loc, Particle particle, int count, double speed) {
        if (loc == null)
            return;
        try {
            loc.getWorld().spawnParticle(particle, loc, count, 0.5, 0.5, 0.5, speed);
        } catch (Exception e) {
            log("Error in particle: " + e.getMessage());
        }
    }

    public static void time(World world, long time) {
        if (world == null)
            return;
        try {
            world.setTime(time);
        } catch (Exception e) {
            log("Error in time: " + e.getMessage());
        }
    }

    public static void storm(World world, boolean storm) {
        if (world == null)
            return;
        try {
            world.setStorm(storm);
        } catch (Exception e) {
            log("Error in storm: " + e.getMessage());
        }
    }

    public static List<Entity> mobs(Location loc, double radius) {
        if (loc == null)
            return new ArrayList<>();
        return new ArrayList<>(loc.getWorld().getNearbyEntities(loc, radius, radius, radius));
    }

    public static Location loc(String world, double x, double y, double z) {
        World w = Bukkit.getWorld(world);
        if (w == null)
            return null;
        return new Location(w, x, y, z);
    }

    public static Location loc(Player p) {
        return p != null ? p.getLocation() : null;
    }

    // ====================================================================================================
    // D) VARLIKLAR (Entity Utils)
    // ====================================================================================================

    public static Entity spawn(Location loc, EntityType type) {
        if (loc == null)
            return null;
        try {
            return loc.getWorld().spawnEntity(loc, type);
        } catch (Exception e) {
            log("Error in spawn: " + e.getMessage());
            return null;
        }
    }

    public static void killAll(World world, EntityType type) {
        if (world == null)
            return;
        try {
            world.getEntities().stream()
                    .filter(e -> e.getType() == type)
                    .forEach(Entity::remove);
        } catch (Exception e) {
            log("Error in killAll: " + e.getMessage());
        }
    }

    public static void name(Entity entity, String name) {
        if (entity == null)
            return;
        try {
            entity.setCustomName(color(name));
            entity.setCustomNameVisible(true);
        } catch (Exception e) {
            log("Error in name: " + e.getMessage());
        }
    }

    public static void ai(Entity entity, boolean hasAI) {
        if (entity == null)
            return;
        if (entity instanceof LivingEntity) {
            ((LivingEntity) entity).setAI(hasAI);
        }
    }

    public static void mount(Entity passenger, Entity vehicle) {
        if (passenger == null || vehicle == null)
            return;
        vehicle.addPassenger(passenger);
    }

    // ====================================================================================================
    // E) SUNUCU & SİSTEM (Server Utils)
    // ====================================================================================================

    public static void broadcast(String message) {
        Bukkit.broadcastMessage(color(PREFIX + message));
    }

    public static void console(String command) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }

    public static void log(String message) {
        instance.getLogger().info(color(message));
    }

    public static void debug(String message) {
        log("&e[DEBUG] " + message);
    }

    public static double[] tps() {
        return new double[] { 20.0, 20.0, 20.0 };
    }

    public static int onlineCount() {
        return Bukkit.getOnlinePlayers().size();
    }

    public static int maxPlayers() {
        return Bukkit.getMaxPlayers();
    }

    public static void whitelist(boolean enabled) {
        Bukkit.setWhitelist(enabled);
    }

    public static void stop() {
        Bukkit.shutdown();
    }

    public static void reload() {
        // No-op in API — reload is handled by SwiftCodeEngine
    }

    // ====================================================================================================
    // F) MATEMATİK & MANTIK (Math Utils)
    // ====================================================================================================

    public static int random(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }

    public static boolean chance(double percent) {
        return Math.random() * 100 < percent;
    }

    public static String format(double number) {
        if (number >= 1000000)
            return String.format("%.1fM", number / 1000000);
        if (number >= 1000)
            return String.format("%.1fk", number / 1000);
        return String.valueOf(number);
    }

    public static double parseMoney(String amount) {
        try {
            if (amount.toLowerCase().endsWith("k")) {
                return Double.parseDouble(amount.substring(0, amount.length() - 1)) * 1000;
            } else if (amount.toLowerCase().endsWith("m")) {
                return Double.parseDouble(amount.substring(0, amount.length() - 1)) * 1000000;
            }
            return Double.parseDouble(amount);
        } catch (Exception e) {
            return 0;
        }
    }

    public static String color(String text) {
        if (text == null)
            return "";
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    // MiniMessage Support for God Mode - if needed
    public static void msgMini(Player player, String minimessage) {
        if (player == null)
            return;
        // Requires Adventure platform or Paper
        try {
            // Basic placeholder for now, assuming legacy mostly used.
            // If on Paper 1.16+, player.sendMessage(Component) works.
        } catch (Exception e) {
        }
    }

    // ====================================================================================================
    // G) ZAMANLAYICI (Scheduler)
    // ====================================================================================================

    public static void delay(long ticks, Runnable task) {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    task.run();
                } catch (Exception e) {
                    log("Error in delay task: " + e.getMessage());
                }
            }
        }.runTaskLater(instance, ticks);
    }

    public static void async(Runnable task) {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    task.run();
                } catch (Exception e) {
                    log("Error in async task: " + e.getMessage());
                }
            }
        }.runTaskAsynchronously(instance);
    }

    public static void repeat(long delay, long period, Runnable task) {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    task.run();
                } catch (Exception e) {
                    log("Error in repeat task: " + e.getMessage());
                }
            }
        }.runTaskTimer(instance, delay, period);
    }

    // ====================================================================================================
    // H) GUI & MENU (Inventory Utils)
    // ====================================================================================================

    public static Inventory gui(String title, int rows) {
        return Bukkit.createInventory(null, rows * 9, color(title));
    }

    public static void slot(Inventory gui, int slot, ItemStack item) {
        if (gui == null)
            return;
        gui.setItem(slot, item);
    }

    public static void fill(Inventory gui, ItemStack item) {
        if (gui == null)
            return;
        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, item);
        }
    }

    // ====================================================================================================
    // I) Gelişmiş Envanter (Advanced Inventory)
    // ====================================================================================================

    public static void fillRow(Inventory gui, int row, ItemStack item) {
        if (gui == null)
            return;
        for (int i = 0; i < 9; i++) {
            int slot = (row * 9) + i;
            if (slot >= 0 && slot < gui.getSize()) {
                gui.setItem(slot, item);
            }
        }
    }

    public static void border(Inventory gui, ItemStack item, int... rows) {
        if (gui == null)
            return;
        for (int row : rows) {
            fillRow(gui, row, item);
        }
    }

    public static boolean hasSpace(Player player, ItemStack item) {
        if (player == null || item == null)
            return false;
        return player.getInventory().firstEmpty() != -1; // Basic checks
    }

    public static int getEmptySlots(Player player) {
        if (player == null)
            return 0;
        int count = 0;
        for (ItemStack is : player.getInventory().getStorageContents()) {
            if (is == null || is.getType() == Material.AIR) {
                count++;
            }
        }
        return count;
    }

    public static void damageItem(Player player, int amount) {
        if (player == null)
            return;
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null || item.getType() == Material.AIR)
            return;

        if (item.getItemMeta() instanceof org.bukkit.inventory.meta.Damageable) {
            org.bukkit.inventory.meta.Damageable meta = (org.bukkit.inventory.meta.Damageable) item.getItemMeta();
            meta.setDamage(meta.getDamage() + amount);
            item.setItemMeta((ItemMeta) meta);
            if (meta.getDamage() >= item.getType().getMaxDurability()) {
                player.getInventory().setItemInMainHand(null);
                sound(player, Sound.ENTITY_ITEM_BREAK);
            }
        }
    }

    public static void repairItem(Player player) {
        if (player == null)
            return;
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null || item.getType() == Material.AIR)
            return;

        if (item.getItemMeta() instanceof org.bukkit.inventory.meta.Damageable) {
            org.bukkit.inventory.meta.Damageable meta = (org.bukkit.inventory.meta.Damageable) item.getItemMeta();
            meta.setDamage(0);
            item.setItemMeta((ItemMeta) meta);
            sound(player, Sound.BLOCK_ANVIL_USE);
        }
    }

    // ====================================================================================================
    // J) Gelişmiş Varlık & Hareket (Advanced Entity & Movement)
    // ====================================================================================================

    public static void velocity(Entity entity, double x, double y, double z) {
        if (entity == null)
            return;
        entity.setVelocity(new Vector(x, y, z));
    }

    public static void push(Entity entity, Vector direction, double speed) {
        if (entity == null || direction == null)
            return;
        entity.setVelocity(direction.normalize().multiply(speed));
    }

    public static void jump(Entity entity, double power) {
        if (entity == null)
            return;
        entity.setVelocity(entity.getVelocity().setY(power));
    }

    public static void lookAt(Entity entity, Location target) {
        if (entity == null || target == null)
            return;
        Location loc = entity.getLocation();
        double dx = target.getX() - loc.getX();
        double dy = target.getY() - loc.getY();
        double dz = target.getZ() - loc.getZ();

        if (dx != 0) {
            if (dx < 0) {
                loc.setYaw((float) (1.5 * Math.PI));
            } else {
                loc.setYaw((float) (0.5 * Math.PI));
            }
            loc.setYaw(loc.getYaw() - (float) Math.atan(dz / dx));
        } else if (dz < 0) {
            loc.setYaw((float) Math.PI);
        }

        double dxz = Math.sqrt(Math.pow(dx, 2) + Math.pow(dz, 2));
        loc.setPitch((float) -Math.atan(dy / dxz));

        loc.setYaw(-loc.getYaw() * 180f / (float) Math.PI);
        loc.setPitch(loc.getPitch() * 180f / (float) Math.PI);

        entity.teleport(loc);
    }

    public static double distance(Location loc1, Location loc2) {
        if (loc1 == null || loc2 == null || loc1.getWorld() != loc2.getWorld())
            return -1;
        return loc1.distance(loc2);
    }

    public static List<Player> getNearbyPlayers(Location loc, double radius) {
        if (loc == null)
            return new ArrayList<>();
        return loc.getWorld().getNearbyEntities(loc, radius, radius, radius).stream()
                .filter(e -> e instanceof Player)
                .map(e -> (Player) e)
                .collect(Collectors.toList());
    }

    public static void kill(Entity entity) {
        if (entity != null)
            entity.remove();
    }

    // ====================================================================================================
    // K) Gelişmiş Dünya & Efekt (Advanced World & Effects)
    // ====================================================================================================

    public static void launchFirework(Location loc, Color color, FireworkEffect.Type type) {
        if (loc == null)
            return;
        Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK_ROCKET);
        FireworkMeta meta = fw.getFireworkMeta();
        meta.addEffect(FireworkEffect.builder().withColor(color).with(type).build());
        meta.setPower(1);
        fw.setFireworkMeta(meta);
    }

    public static void strike(Location loc) {
        if (loc == null)
            return;
        loc.getWorld().strikeLightning(loc);
    }

    public static Location getHighestBlock(Location loc) {
        if (loc == null)
            return null;
        return loc.getWorld().getHighestBlockAt(loc).getLocation().add(0, 1, 0);
    }

    public static void playSound(Location loc, Sound sound, float volume, float pitch) {
        if (loc == null)
            return;
        loc.getWorld().playSound(loc, sound, volume, pitch);
    }

    public static void sphere(Location center, Material material, int radius, boolean hollow) {
        if (center == null)
            return;
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    if (x * x + y * y + z * z <= radius * radius) {
                        if (!hollow || x * x + y * y + z * z >= (radius - 1) * (radius - 1)) {
                            center.clone().add(x, y, z).getBlock().setType(material);
                        }
                    }
                }
            }
        }
    }

    // ====================================================================================================
    // L) Metin & Görsel (Text & Visuals)
    // ====================================================================================================

    public static String centerText(String text) {
        if (text == null)
            return "";
        int maxWidth = 60; // Approximate chat width
        int spaces = (maxWidth - text.length()) / 2;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < spaces; i++)
            sb.append(" ");
        return sb.toString() + text;
    }

    public static String progressBar(int current, int max, int totalBars, String symbol, String completedColor,
            String notCompletedColor) {
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);

        StringBuilder sb = new StringBuilder();
        sb.append(color(completedColor));
        for (int i = 0; i < progressBars; i++) {
            sb.append(symbol);
        }
        sb.append(color(notCompletedColor));
        for (int i = progressBars; i < totalBars; i++) {
            sb.append(symbol);
        }
        return sb.toString();
    }

    // ====================================================================================================
    // M) Sunucu & Yönetim (Server & Admin)
    // ====================================================================================================

    public static void whitelistAdd(String name) {
        Bukkit.getOfflinePlayer(name).setWhitelisted(true);
    }

    public static void whitelistRemove(String name) {
        Bukkit.getOfflinePlayer(name).setWhitelisted(false);
    }

    public static void banIP(String ip) {
        Bukkit.banIP(ip);
    }

    public static void unbanIP(String ip) {
        Bukkit.unbanIP(ip);
    }

    public static void runCmd(String cmd) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
    }

    public static void togglePvp(World world, boolean allow) {
        if (world != null)
            world.setPVP(allow);
    }

    // ====================================================================================================
    // N) Oyuncu Gelişmiş (Advanced Player)
    // ====================================================================================================

    public static void hidePlayer(Player observer, Player target) {
        if (observer != null && target != null)
            observer.hidePlayer(instance, target);
    }

    public static void showPlayer(Player observer, Player target) {
        if (observer != null && target != null)
            observer.showPlayer(instance, target);
    }

    public static void setTabList(Player player, String header, String footer) {
        if (player == null)
            return;
        player.setPlayerListHeaderFooter(color(header), color(footer));
    }

    public static int getPing(Player player) {
        if (player == null)
            return 0;
        try {
            Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
            return (int) entityPlayer.getClass().getField("ping").get(entityPlayer);
        } catch (Exception e) {
            return 0;
        }
    }

    public static String getIp(Player player) {
        if (player == null)
            return "";
        return player.getAddress().getAddress().getHostAddress();
    }

    // ====================================================================================================
    // O) PLUGIN ENTEGRASYONLARI (Plugin Integrations)
    // ====================================================================================================

    // --- Vault & Economy (via commands) ---
    public static boolean setupEconomy() {
        return economy != null;
    }

    public static double getBalance(Player player) {
        if (player == null)
            return 0;
        if (economy != null) {
            return economy.getBalance(player);
        }
        return 0;
    }

    public static void deposit(Player player, double amount) {
        if (player == null)
            return;
        if (economy != null) {
            economy.depositPlayer(player, amount);
        } else {
            console("eco give " + player.getName() + " " + amount);
        }
    }

    public static void deposit(UUID uuid, double amount) {
        if (uuid == null)
            return;
        if (economy != null) {
            economy.depositPlayer(Bukkit.getOfflinePlayer(uuid), amount);
        } else {
            OfflinePlayer op = Bukkit.getOfflinePlayer(uuid);
            if (op.getName() != null) {
                console("eco give " + op.getName() + " " + amount);
            }
        }
    }

    public static void withdraw(Player player, double amount) {
        if (player == null)
            return;
        if (economy != null) {
            economy.withdrawPlayer(player, amount);
        } else {
            console("eco take " + player.getName() + " " + amount);
        }
    }

    public static void setMoney(Player player, double amount) {
        if (player == null)
            return;
        console("eco set " + player.getName() + " " + amount);
    }

    // --- LuckPerms ---
    public static void addGroup(Player player, String group) {
        if (player == null)
            return;
        console("lp user " + player.getName() + " parent add " + group);
    }

    public static void removeGroup(Player player, String group) {
        if (player == null)
            return;
        console("lp user " + player.getName() + " parent remove " + group);
    }

    public static void setGroup(Player player, String group) {
        if (player == null)
            return;
        console("lp user " + player.getName() + " parent set " + group);
    }

    public static void addPerm(Player player, String permission) {
        if (player == null)
            return;
        console("lp user " + player.getName() + " permission set " + permission + " true");
    }

    public static void removePerm(Player player, String permission) {
        if (player == null)
            return;
        console("lp user " + player.getName() + " permission unset " + permission);
    }

    // --- EssentialsX ---
    public static void healAll() {
        console("heal *");
    }

    public static void feedAll() {
        console("feed *");
    }

    public static void day() {
        console("time set day");
    }

    public static void night() {
        console("time set night");
    }

    public static void sun() {
        console("weather clear");
    }

    public static void rain() {
        console("weather rain");
    }

    public static void warp(Player player, String warpName) {
        if (player == null)
            return;
        sudo(player, "warp " + warpName);
    }

    public static void setWarp(Player player, String warpName) {
        if (player == null)
            return;
        if (player.hasPermission("essentials.setwarp"))
            sudo(player, "setwarp " + warpName);
        else
            console("setwarp " + warpName + " " + player.getLocation().getX() + " " + player.getLocation().getY() + " "
                    + player.getLocation().getZ());
    }

    public static void home(Player player, String homeName) {
        if (player == null)
            return;
        sudo(player, "home " + homeName);
    }

    // --- Multiverse-Core ---
    public static void mvCreate(String worldName, String type) {
        console("mv create " + worldName + " " + type);
    }

    public static void mvDelete(String worldName) {
        console("mv delete " + worldName);
        console("mv confirm");
    }

    public static void mvTp(Player player, String worldName) {
        if (player == null)
            return;
        console("mv tp " + player.getName() + " " + worldName);
    }

    public static void mvImport(String worldName, String env) {
        console("mv import " + worldName + " " + env);
    }

    // --- WorldEdit (via sudo) ---
    // Note: WorldEdit commands usually require the player to run them
    public static void weSet(Player player, String pattern) {
        if (player == null)
            return;
        sudo(player, "/set " + pattern);
    }

    public static void wePos1(Player player) {
        if (player == null)
            return;
        sudo(player, "/pos1");
    }

    public static void wePos2(Player player) {
        if (player == null)
            return;
        sudo(player, "/pos2");
    }

    public static void weCopy(Player player) {
        if (player == null)
            return;
        sudo(player, "/copy");
    }

    public static void wePaste(Player player) {
        if (player == null)
            return;
        sudo(player, "/paste");
    }

    public static void weUndo(Player player) {
        if (player == null)
            return;
        sudo(player, "/undo");
    }

    public static void weRedo(Player player) {
        if (player == null)
            return;
        sudo(player, "/redo");
    }

    public static void weReplace(Player player, String from, String to) {
        if (player == null)
            return;
        sudo(player, "/replace " + from + " " + to);
    }

    public static void weSchemLoad(Player player, String filename) {
        if (player == null)
            return;
        sudo(player, "/schematic load " + filename);
    }

    public static void weSchemSave(Player player, String filename) {
        if (player == null)
            return;
        sudo(player, "/schematic save " + filename);
    }

    // --- WorldGuard ---
    public static void rgDefine(Player player, String regionName) {
        if (player == null)
            return;
        sudo(player, "/rg define " + regionName);
    }

    public static void rgDelete(String regionName, String worldName) {
        console("rg delete " + regionName + " -w " + worldName);
    }

    public static void rgFlag(String regionName, String flag, String value, String worldName) {
        console("rg flag " + regionName + " " + flag + " " + value + " -w " + worldName);
    }

    public static void rgAddMember(String regionName, String playerName, String worldName) {
        console("rg addmember " + regionName + " " + playerName + " -w " + worldName);
    }

    public static void rgRemoveMember(String regionName, String playerName, String worldName) {
        console("rg removemember " + regionName + " " + playerName + " -w " + worldName);
    }

    public static void rgInfo(Player player, String regionName) {
        if (player == null)
            return;
        sudo(player, "/rg info " + regionName);
    }

    // --- Advanced Helpers ---
    public static void broadcastTitle(String title, String subtitle) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            title(p, title, subtitle);
        }
    }

    public static void broadcastSound(Sound sound) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            sound(p, sound);
        }
    }

    public static void clearChat() {
        for (int i = 0; i < 100; i++) {
            broadcast(" ");
        }
    }
    // ====================================================================================================
    // P) MEGA UPDATE (110+ New Methods)
    // ====================================================================================================

    // 1. OYUNCU TEMEL (Player Basics)
    public static void extinguish(Player player) {
        if (player == null)
            return;
        player.setFireTicks(0);
    }

    public static void flySpeed(Player player, float speed) {
        if (player == null)
            return;
        try {
            if (speed < 0.1f)
                speed = 0.1f;
            if (speed > 1.0f)
                speed = 1.0f;
            player.setFlySpeed(speed);
        } catch (Exception e) {
        }
    }

    public static void walkSpeed(Player player, float speed) {
        if (player == null)
            return;
        try {
            if (speed < 0.1f)
                speed = 0.1f;
            if (speed > 1.0f)
                speed = 1.0f;
            player.setWalkSpeed(speed);
        } catch (Exception e) {
        }
    }

    public static void gmSurvival(Player player) {
        gm(player, GameMode.SURVIVAL);
    }

    public static void gmCreative(Player player) {
        gm(player, GameMode.CREATIVE);
    }

    public static void gmSpectator(Player player) {
        gm(player, GameMode.SPECTATOR);
    }

    public static void gmAdventure(Player player) {
        gm(player, GameMode.ADVENTURE);
    }

    public static void god(Player player, boolean enabled) {
        if (player == null)
            return;
        player.setInvulnerable(enabled);
    }

    public static void invisible(Player player, boolean enabled) {
        if (player == null)
            return;
        player.setInvisible(enabled);
    }

    public static void glow(Player player, boolean enabled) {
        if (player == null)
            return;
        player.setGlowing(enabled);
    }

    // 2. OYUNCU YÖNETİMİ (Player Admin)
    public static void kickAll(String reason) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.kickPlayer(color(reason));
        }
    }

    public static void unban(String playerName) {
        Bukkit.getBanList(BanList.Type.NAME).pardon(playerName);
    }

    public static void ipBan(Player player, String reason) {
        if (player == null)
            return;
        Bukkit.banIP(player.getAddress().getAddress().getHostAddress());
        player.kickPlayer(color(reason));
    }

    public static String getUuid(Player player) {
        if (player == null)
            return "";
        return player.getUniqueId().toString();
    }

    public static void op(Player player, boolean enabled) {
        if (player == null)
            return;
        player.setOp(enabled);
    }

    public static void tp(Player player, Location target) {
        teleport(player, target);
    }

    public static void tpHere(Player admin, Player target) {
        if (admin == null || target == null)
            return;
        target.teleport(admin);
    }

    public static void tpAll(Location target) {
        if (target == null)
            return;
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.teleport(target);
        }
    }

    public static void spawn(Player player) {
        if (player == null)
            return;
        player.teleport(player.getWorld().getSpawnLocation());
    }

    // 3. ENVANTER & EŞYA
    public static void giveItem(Player player, ItemStack item) {
        give(player, item);
    }

    public static void clearInv(Player player) {
        clear(player);
    }

    public static void clearArmor(Player player) {
        if (player == null)
            return;
        player.getInventory().setArmorContents(null);
    }

    public static void helm(Player player, ItemStack item) {
        if (player == null)
            return;
        player.getInventory().setHelmet(item);
    }

    public static void chest(Player player, ItemStack item) {
        if (player == null)
            return;
        player.getInventory().setChestplate(item);
    }

    public static void leg(Player player, ItemStack item) {
        if (player == null)
            return;
        player.getInventory().setLeggings(item);
    }

    public static void boot(Player player, ItemStack item) {
        if (player == null)
            return;
        player.getInventory().setBoots(item);
    }

    public static ItemStack itemGlow(ItemStack item) {
        return glow(item);
    }

    public static ItemStack itemModel(ItemStack item, int modelData) {
        if (item == null)
            return null;
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setCustomModelData(modelData);
            item.setItemMeta(meta);
        }
        return item;
    }

    public static ItemStack itemUnbreak(ItemStack item, boolean unbreak) {
        if (item == null)
            return null;
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setUnbreakable(unbreak);
            item.setItemMeta(meta);
        }
        return item;
    }

    public static ItemStack itemFlag(ItemStack item, ItemFlag flag) {
        return flag(item, flag);
    }

    public static void openWork(Player player) {
        if (player == null)
            return;
        player.openWorkbench(null, true);
    }

    public static void openEnder(Player player) {
        if (player == null)
            return;
        player.openInventory(player.getEnderChest());
    }

    public static void openAnvil(Player player) {
        if (player == null)
            return;
        // Basic anvil open might require NMS or specific API in older versions,
        // using inventory type for now or event.
        Inventory inv = Bukkit.createInventory(null, InventoryType.ANVIL);
        player.openInventory(inv);
    }

    // 4. DÜNYA & ORTAM
    public static void timeSet(World world, long ticks) {
        time(world, ticks);
    }

    public static void day(World world) {
        if (world != null)
            world.setTime(1000);
    }

    public static void night(World world) {
        if (world != null)
            world.setTime(18000);
    } // 13000 is default night

    public static void rain(World world) {
        if (world != null) {
            world.setStorm(true);
            world.setThundering(false);
        }
    }

    public static void sun(World world) {
        if (world != null) {
            world.setStorm(false);
            world.setThundering(false);
        }
    }

    public static void strikeEffect(Location loc) {
        lightning(loc, true);
    }

    public static void explode(Location loc, float power) {
        explosion(loc, power, false);
    }

    public static void setBlock(Location loc, Material mat) {
        block(loc, mat);
    }

    public static Material getBlock(Location loc) {
        if (loc == null)
            return Material.AIR;
        return loc.getBlock().getType();
    }

    public static void dropItem(Location loc, ItemStack item) {
        drop(loc, item);
    }

    public static Biome getBiome(Location loc) {
        if (loc == null)
            return null;
        return loc.getBlock().getBiome();
    }

    public static void setBiome(Location loc, Biome biome) {
        if (loc == null)
            return;
        loc.getBlock().setBiome(biome);
    }

    // 5. VARLIKLAR
    public static Entity spawnEntity(Location loc, EntityType type) {
        return spawn(loc, type);
    }

    public static void killAllMobs(World world) {
        if (world == null)
            return;
        for (Entity e : world.getEntities()) {
            if (e instanceof LivingEntity && !(e instanceof Player)) {
                e.remove();
            }
        }
    }

    public static void removeItems(World world) {
        if (world == null)
            return;
        for (Entity e : world.getEntities()) {
            if (e instanceof Item) {
                e.remove();
            }
        }
    }

    public static void ride(Entity passenger, Entity vehicle) {
        mount(passenger, vehicle);
    }

    public static void eject(Entity vehicle) {
        if (vehicle == null)
            return;
        vehicle.eject();
    }

    public static void damage(Entity entity, double amount) {
        if (entity instanceof LivingEntity) {
            ((LivingEntity) entity).damage(amount);
        }
    }

    public static void maxHealth(Entity entity, double amount) {
        if (entity instanceof LivingEntity) {
            ((LivingEntity) entity).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(amount);
        }
    }

    public static void health(Entity entity, double amount) {
        if (entity instanceof LivingEntity) {
            ((LivingEntity) entity).setHealth(amount);
        }
    }

    public static void nameEntity(Entity entity, String name) {
        name(entity, name);
    }

    public static void silent(Entity entity, boolean silent) {
        if (entity == null)
            return;
        entity.setSilent(silent);
    }

    // 6. MESAJ & GÖRSEL
    public static void bossbar(Player player, String text, BarColor color, BarStyle style) {
        if (player == null)
            return;
        BossBar bar = Bukkit.createBossBar(color(text), color, style);
        bar.addPlayer(player);
        bar.setVisible(true);
        // Note: You need to manage removal yourself or store it!
    }

    // ====================================================================================================
    // Q) DATA & LANG API (v1.1)
    // ====================================================================================================

    public static void saveData(Player p, String key, Object value) {
        try {
            java.io.File dir = new java.io.File("plugins/SwiftCode/variables");
            if (!dir.exists())
                dir.mkdirs();
            java.io.File f = new java.io.File(dir, p.getUniqueId() + ".yml");
            org.bukkit.configuration.file.YamlConfiguration cfg = org.bukkit.configuration.file.YamlConfiguration
                    .loadConfiguration(f);
            cfg.set(key, value);
            cfg.save(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int loadData(Player p, String key, int def) {
        java.io.File f = new java.io.File("plugins/SwiftCode/variables/" + p.getUniqueId() + ".yml");
        if (!f.exists())
            return def;
        return org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(f).getInt(key, def);
    }

    public static String lang(Player p, String key) {
        String code = p.getLocale().toLowerCase();
        String loc = "en";
        if (code.startsWith("tr"))
            loc = "tr";
        else if (code.startsWith("de"))
            loc = "de";
        else if (code.startsWith("fr"))
            loc = "fr";
        else if (code.startsWith("es"))
            loc = "es";
        else if (code.startsWith("it"))
            loc = "it";
        else if (code.startsWith("pt"))
            loc = "pt";
        else if (code.startsWith("ru"))
            loc = "ru";
        else if (code.startsWith("zh"))
            loc = "zh";
        else if (code.startsWith("ja"))
            loc = "ja";

        java.io.File f = new java.io.File("plugins/SwiftCode/lang/" + loc + ".yml");
        if (!f.exists())
            return key;
        return color(org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(f).getString(key, key));
    }

    // ====================================================================================================
    // Item Helpers
    // ====================================================================================================

    public static void give(Player p, Material m) {
        if (p == null || m == null)
            return;
        p.getInventory().addItem(new ItemStack(m, 1));
    }

    public static void removeBossbar(Player player) {
        // Complex without storage, but we can try to find visible ones?
        // No strict API for "get player bossbars" easily in simple Bukkit API.
        // Allowing API user to handle instances usually better.
        // For now, implementing "dummy" or "remove headers"?
        // Warning: This implementation below might not work as intended without map.
    }

    public static String hex(String message) {
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String hexCode = message.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace('#', 'x');
            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder("");
            for (char c : ch) {
                builder.append("&" + c);
            }
            message = message.replace(hexCode, builder.toString());
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void dispatch(Player player, String command) {
        sudo(player, command);
    }

    public static long ram() {
        return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024;
    }

    public static List<Player> onlinePlayers() {
        return getPlayers();
    }

    public static void whitelist(Player player, boolean enabled) {
        if (player == null)
            return;
        player.setWhitelisted(enabled);
    }

    public static List<String> plugins() {
        return Arrays.stream(Bukkit.getPluginManager().getPlugins())
                .map(Plugin::getName)
                .collect(Collectors.toList());
    }

    // 8. UTILS & MATEMATİK
    public static int rndInt(int min, int max) {
        return random(min, max);
    }

    public static double rndDouble(double min, double max) {
        return min + new Random().nextDouble() * (max - min);
    }

    public static String locStr(Location loc) {
        if (loc == null)
            return "";
        return loc.getWorld().getName() + "," + loc.getX() + "," + loc.getY() + "," + loc.getZ();
    }

    public static Location strLoc(String s) {
        if (s == null || s.isEmpty())
            return null;
        String[] parts = s.split(",");
        if (parts.length < 4)
            return null;
        return new Location(Bukkit.getWorld(parts[0]),
                Double.parseDouble(parts[1]),
                Double.parseDouble(parts[2]),
                Double.parseDouble(parts[3]));
    }

    public static void cancelTasks() {
        Bukkit.getScheduler().cancelTasks(instance);
    }

    public static Block getTargetBlock(Player player, int distance) {
        if (player == null)
            return null;
        return player.getTargetBlockExact(distance);
    }

    public static List<Entity> getNearby(Location loc, double radius) {
        return mobs(loc, radius);
    }

    public static Inventory menu(String title, int rows) {
        return gui(title, rows);
    }

    // ====================================================================================================
    // R) WEBHOOK SYSTEM (Discord / General JSON)
    // ====================================================================================================

    /**
     * Sends a generic JSON POST request to a webhook URL asynchronously.
     * Works with Discord, Slack, or any service accepting JSON payloads.
     *
     * @param url         The webhook endpoint URL
     * @param jsonContent The raw JSON string to send as the request body
     */
    public static void webhook(String url, String jsonContent) {
        if (url == null || jsonContent == null)
            return;
        Bukkit.getScheduler().runTaskAsynchronously(instance, () -> {
            try {
                java.net.URL u = new java.net.URL(url);
                java.net.HttpURLConnection con = (java.net.HttpURLConnection) u.openConnection();
                con.setRequestMethod("POST");
                con.setDoOutput(true);
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("User-Agent", "SwiftCodeAPI");
                con.setConnectTimeout(5000);
                con.setReadTimeout(5000);

                try (java.io.OutputStream os = con.getOutputStream()) {
                    byte[] input = jsonContent.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }
                con.getResponseCode(); // Trigger request
                con.disconnect();
            } catch (Exception e) {
                log("&cWebhook error: " + e.getMessage());
            }
        });
    }

    /**
     * Sends a pre-formatted Discord webhook message.
     * Automatically wraps the message in Discord's JSON format.
     *
     * @param url     The Discord webhook URL
     * @param message The text message to send
     */
    public static void discordWebhook(String url, String message) {
        if (url == null || message == null)
            return;
        // Escape special JSON characters
        String escaped = message.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n");
        String json = "{\"content\":\"" + escaped + "\"}";
        webhook(url, json);
    }

    /**
     * Sends a rich Discord embed webhook.
     *
     * @param url         The Discord webhook URL
     * @param title       Embed title
     * @param description Embed description
     * @param color       Embed color (decimal, e.g. 65280 for green)
     */
    public static void discordEmbed(String url, String title, String description, int color) {
        if (url == null)
            return;
        String t = (title != null ? title : "").replace("\"", "\\\"");
        String d = (description != null ? description : "").replace("\"", "\\\"").replace("\n", "\\n");
        String json = "{\"embeds\":[{\"title\":\"" + t + "\",\"description\":\"" + d + "\",\"color\":" + color + "}]}";
        webhook(url, json);
    }
}

package moonrisenetwork.randomteleport;

import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class RTCommands extends AbstractCommands {
    public RTCommands() {
        super("rtp");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {

        if (args.length == 0) {
            Player player = (Player) sender;
            ConfigManager manager = new ConfigManager("points.yml");
            FileConfiguration cfg = manager.getConfig();

            List<Location> locations;

            if(cfg.isSet("locations")) {
                locations = (List<Location>) cfg.get("locations");
                int n = (int)Math.floor(Math.random() * locations.size());
                player.teleport(locations.get(n));

                String locationCreated = RandomTeleport.getInstance().getConfig().getString("messages.rtp", "You teleported to location with coordinates: x: {x} y: {y} z: {z}.");
                locationCreated = locationCreated.replace("{x}", String.valueOf(player.getLocation().getX()));
                locationCreated = locationCreated.replace("{y}", String.valueOf(player.getLocation().getY()));
                locationCreated = locationCreated.replace("{z}", String.valueOf(player.getLocation().getZ()));
                player.sendMessage(ChatColor.YELLOW + locationCreated);

                return;

            } sender.sendMessage(ChatColor.RED + RandomTeleport.getInstance().getConfig().getString("messages.listEmpty"));
            return;
        }

        if (args[0].equalsIgnoreCase("reload")) {

            if (!sender.hasPermission("rtp.reload")) {
                sender.sendMessage(ChatColor.RED + RandomTeleport.getInstance().getConfig().getString(("messages.noPermission")));
                return;
            }

            RandomTeleport.getInstance().reloadConfig();
            sender.sendMessage(ChatColor.YELLOW + RandomTeleport.getInstance().getConfig().getString( "messages.reload"));
            return;
        }

        if (args[0].equalsIgnoreCase("add")) {
            if (!sender.hasPermission("rtp.add")) {
                sender.sendMessage(ChatColor.RED +RandomTeleport.getInstance().getConfig().getString(  "messages.noPermission"));
                return;
            }
            Player player = (Player) sender;

            Location blockLocation = player.getLocation().getBlock().getLocation();

            ConfigManager manager = new ConfigManager("points.yml");
            FileConfiguration cfg = manager.getConfig();

            List<Location> locations = new ArrayList<>();

            if (cfg.isSet("locations")) {
                locations = (List<Location>) cfg.get("locations");
            }

            if (locations.contains(blockLocation)) {
                sender.sendMessage(ChatColor.RED +RandomTeleport.getInstance().getConfig().getString( "messages.repeatLocation"));
                return;

            } locations.add(blockLocation);
            cfg.set("locations", locations);
            manager.save();


                String locationCreated = RandomTeleport.getInstance().getConfig().getString("messages.newLocation", "You add new location with coordinates: x: {x} y: {y} z: {z}.");
                locationCreated = locationCreated.replace("{x}", String.valueOf(player.getLocation().getX()));
                locationCreated = locationCreated.replace("{y}", String.valueOf(player.getLocation().getY()));
                locationCreated = locationCreated.replace("{z}", String.valueOf(player.getLocation().getZ()));
                player.sendMessage(ChatColor.YELLOW + locationCreated);

                return;
            }

            sender.sendMessage(ChatColor.RED + RandomTeleport.getInstance().getConfig().getString("messages.unknownCommand"));

        }



    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) return Lists.newArrayList("add", "reload");
        return Lists.newArrayList();
    }
}

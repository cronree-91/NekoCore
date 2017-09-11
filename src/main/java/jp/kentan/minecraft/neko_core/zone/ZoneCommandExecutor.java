package jp.kentan.minecraft.neko_core.zone;

import jp.kentan.minecraft.neko_core.utils.NekoUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

class ZoneCommandExecutor implements CommandExecutor {

    private ZoneManager mManager;

    ZoneCommandExecutor(ZoneManager manager){
        mManager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        final int params = args.length;

        if(params < 1 || !NekoUtils.isPlayer(sender)){
            printHelp(sender);
            return true;
        }

        final Player player = NekoUtils.toPlayer(sender);

        if(sender.hasPermission("neko.zone.admin")){
            switch (args[0]){
                case "world":
                    if(params < 4) {
                        return true;
                    }

                    try {
                        mManager.setWorldConfig(player, Double.parseDouble(args[1]), Double.parseDouble(args[2]), Integer.parseInt(args[3]));
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                case "register":
                case "rg":
                    if(params < 4) {
                        return true;
                    }

                    try {
                        mManager.register(player, args[1], args[2], Integer.parseInt(args[3]));
                    } catch (Exception e){
                        e.printStackTrace();
                    }

                    break;
            }
        }

        switch (args[0]){
            case "info":
                if(params < 2){
                    return true;
                }

                mManager.info(player, args[1]);
                break;
            case "buy":
                if(params < 2){
                    return true;
                }

                mManager.preBuy(player, args[1]);
                break;
            case "confirm":
                mManager.confirm(player);
                break;
            default:
                break;
        }
        return true;
    }

    private void printHelp(CommandSender sender){
        sender.sendMessage("help");
    }
}

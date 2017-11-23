package jp.kentan.minecraft.neko_core.tutorial;

import jp.kentan.minecraft.neko_core.util.NekoUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class TutorialCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        final int params = args.length;

        if(!NekoUtil.isPlayer(sender)){
            return true;
        }

        Player player = (Player) sender;

        if (TutorialManager.isGuest(player)) {

            if (params < 1) {
                sendHelp(player);
                return true;
            }

            TutorialManager.agree(player, args[0]);
        }

        return true;
    }

    private void sendHelp(Player player){
        player.sendMessage(ChatColor.GOLD + "****************************************************");
        Bukkit.getServer().dispatchCommand(
                Bukkit.getConsoleSender(),
                "tellraw " + player.getName() +
                        " [\"\",{\"text\":\"サーバールール\",\"bold\":true,\"color\":\"aqua\"},{\"text\":\"[\",\"color\":\"gray\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://minecraft.kentan.jp/rule/\"}},{\"text\":\"ｸﾘｯｸ!\",\"color\":\"aqua\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://minecraft.kentan.jp/rule/\"}},{\"text\":\"]\",\"color\":\"gray\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://minecraft.kentan.jp/rule/\"}},{\"text\":\"を\",\"color\":\"gold\"},{\"text\":\"確認\",\"color\":\"red\"},{\"text\":\"して,キーワードを入力してください.\",\"color\":\"gold\"}]");
        player.sendMessage(ChatColor.GOLD + "例 キーワードが cat の場合は");
        player.sendMessage(ChatColor.GOLD + "/tutorial cat");
        player.sendMessage(ChatColor.GOLD + "とチャットに入力してください.");
        player.sendMessage(ChatColor.GOLD + "****************************************************");
    }
}

package jp.kentan.minecraft.neko_core.bridge;


import jp.kentan.minecraft.neko_core.util.Log;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultProvider {

    private static Economy sEconomy;
    private static Chat sChat;

    public static void setup(){
        detectVault();
    }

    private static void detectVault() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            Log.error("failed to detect Vault.");
            return;
        }

        RegisteredServiceProvider<Economy> serviceProvider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        RegisteredServiceProvider<Chat> chatProvider = Bukkit.getServer().getServicesManager().getRegistration(Chat.class);

        if (serviceProvider == null) {
            Log.error("failed to get Economy service.");
            return;
        }

        if (chatProvider == null) {
            Log.error("failed to get Chat service.");
            return;
        }

        sEconomy = serviceProvider.getProvider();
        sChat = chatProvider.getProvider();
    }

    public static boolean deposit(Player player, double amount){
        if(player == null || !sEconomy.hasAccount(player) || amount < 0){
            return false;
        }

        EconomyResponse response = sEconomy.depositPlayer(player, amount);

        return response.transactionSuccess();
    }

    public static boolean withdraw(Player player, double amount){
        if(player == null || !sEconomy.hasAccount(player) || amount < 0){
            return false;
        }

        EconomyResponse response = sEconomy.withdrawPlayer(player, amount);

        return response.transactionSuccess();
    }

    public static double getBalance(Player player){
        if(player == null || !sEconomy.hasAccount(player)){
            return 0;
        }

        return sEconomy.getBalance(player);
    }

    public static String getPlayerPrefix(Player player){
        return sChat.getPlayerPrefix(player);
    }
}
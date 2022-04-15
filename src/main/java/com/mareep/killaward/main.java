package com.mareep.killaward;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.craftbukkit.v1_7_R4.scoreboard.CraftScoreboard;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;


public class main extends JavaPlugin {
    public static Economy econ = null;

    @Override
    public void onEnable() {
        getLogger().info("击杀奖励插件已加载! 作者 : Mareep");
        getServer().getPluginManager().registerEvents(new killaward(),this);
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            getLogger().info("没有发现Vault，插件无法继续使用！");
            //禁用插件
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupEconomy();
    }

    @Override
    public void onDisable() {
        getLogger().info("击杀奖励插件已卸载!作者 : Mareep");
        HandlerList.unregisterAll();
    }
    private void setupEconomy() {
        RegisteredServiceProvider < Economy > rsp = getServer().getServicesManager().getRegistration(Economy.class);
        econ = rsp.getProvider();
    }
    public class killaward implements Listener {
        Plugin Plu = com.mareep.killaward.main.getProvidingPlugin(com.mareep.killaward.main.class);
        @EventHandler
        public void ka(PlayerDeathEvent evt){
            Player killed = evt.getEntity().getPlayer();
            Entity killer = evt.getEntity().getKiller();
            CraftScoreboard scoreboard = (CraftScoreboard) Plu.getServer().getScoreboardManager().getMainScoreboard();
            if (killer != null || killer instanceof Player);{
                Player p = (Player) killer;
                String team1 = scoreboard.getPlayerTeam(killed).getName();
                String team2 = scoreboard.getPlayerTeam(p).getName();
                if (team1.equals(team2)){
                    p.sendMessage("你击杀了友军"+killed.getName()+"被扣除5游戏币");
                    econ.withdrawPlayer(p,5.00);
                }
                else {
                    p.sendMessage("你击杀了敌军"+killed.getName()+"获得5游戏币");
                    econ.depositPlayer(p,5.00);
                }
            }



        }
    }
}


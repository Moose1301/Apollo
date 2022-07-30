package com.moonsworth.apollo.impl.bukkit;

import com.moonsworth.apollo.api.Apollo;
import com.moonsworth.apollo.api.ApolloPlatform;
import com.moonsworth.apollo.api.bridge.ApolloPlayer;
import com.moonsworth.apollo.api.module.ApolloModule;
import com.moonsworth.apollo.api.module.impl.LegacyCombatModule;
import com.moonsworth.apollo.impl.bukkit.command.KnockbackCommand;
import com.moonsworth.apollo.impl.bukkit.listener.*;
import com.moonsworth.apollo.impl.bukkit.wrapper.BukkitPlayer;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;
import org.yaml.snakeyaml.Yaml;

import javax.annotation.Nullable;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Implementation of ApolloPlatform for Bukkit-based servers.
 */
public class ApolloBukkitPlatform extends JavaPlugin implements ApolloPlatform, Listener {

    @Getter
    private static ApolloBukkitPlatform instance;

    @Override
    public Kind getKind() {
        return Kind.SERVER;
    }

    @Override
    public void onEnable() {
        instance = this;
        Apollo.setPlatform(this);
        handleConfiguration();

        registerPluginChannel();
        getServer().getPluginManager().registerEvents(this, this);
        Apollo.getApolloModuleManager().registerModuleListener(LegacyCombatModule.class, combatModule -> {
            getCommand("setkb").setExecutor(new KnockbackCommand());
            getServer().getPluginManager().registerEvents(new DisableProjectileRandomnessListener(this, combatModule), this);
            getServer().getPluginManager().registerEvents(new AttackSpeedListener(combatModule), this);
            getServer().getPluginManager().registerEvents(new KnockbackListener(combatModule), this);
            getServer().getPluginManager().registerEvents(new ArmorDurabilityListener(this, combatModule), this);
            getServer().getPluginManager().registerEvents(new RegenListener(this, combatModule), this);
            getServer().getPluginManager().registerEvents(new AttackFrequencyListener(combatModule), this);
        });
    }

    private void handleConfiguration() {
        saveDefaultConfig();
        ConfigurationSection modules = getConfig().getConfigurationSection("modules");
        if (modules == null) {
            return;
        }
        Map<String, Object> values = modules.getValues(true);
        Apollo.getApolloModuleManager().loadConfiguration(values);
    }

    private void registerPluginChannel() {
        Messenger messenger = getServer().getMessenger();
        messenger.registerOutgoingPluginChannel(this, Apollo.PLUGIN_MESSAGE_CHANNEL);
        messenger.registerIncomingPluginChannel(this, Apollo.PLUGIN_MESSAGE_CHANNEL, (channel, player, bytes) -> {
            Apollo.getApolloPlayerManager().registerPlayer(player);
        });
    }

    @Override
    public @Nullable ApolloPlayer tryWrapPlayer(Object o) {
        if (o instanceof Player player) {
            if (Apollo.getApolloPlayerManager().getApolloPlayer(player.getUniqueId()).isEmpty()) {
                return new BukkitPlayer(player);
            }
        }
        return null;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Apollo.getApolloPlayerManager().unRegisterPlayer(event.getPlayer().getUniqueId());
    }

}

package com.lunarclient.apollo.module.tntcountdown;

import com.lunarclient.apollo.Apollo;
import com.lunarclient.apollo.ApolloBukkitPlatform;
import com.lunarclient.apollo.network.NetworkTypes;
import com.lunarclient.apollo.player.AbstractApolloPlayer;
import com.lunarclient.apollo.player.ApolloPlayer;
import com.lunarclient.apollo.tntcountdown.v1.SetTntCountdownMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.UUID;

/**
 * Provides the tnt countdown module.
 *
 * @since 1.0.0
 */
public final class TntCountdownModuleImpl extends TntCountdownModule implements Listener {

    @Override
    protected void onEnable() {
       Bukkit.getPluginManager().registerEvents(this, ApolloBukkitPlatform.getInstance());
    }

    @Override
    public void setTntCountdown(UUID tntUuid, int ticks) {
        Entity entity = Bukkit.getEntity(tntUuid);
        if(!(entity instanceof TNTPrimed)) return;

        ((TNTPrimed) entity).setFuseTicks(ticks);

        SetTntCountdownMessage message = SetTntCountdownMessage.newBuilder()
            .setEntityUuid(NetworkTypes.toProtobuf(tntUuid))
            .setDurationTicks(ticks)
            .build();

        for (ApolloPlayer viewer : Apollo.getPlayerManager().getPlayers()) {
            ((AbstractApolloPlayer) viewer).sendPacket(message);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onTntSpawn(EntitySpawnEvent event) {
        // We only care about TNT
        if(event.getEntityType() != EntityType.PRIMED_TNT) return;

        TNTPrimed primed = (TNTPrimed) event.getEntity();
        int customFuse = this.getOptions().get(TntCountdownModule.TNT_TICKS);

        // We only care about TNT with a non-standard fuse as well.
        if(primed.getFuseTicks() == customFuse) return;
        primed.setFuseTicks(customFuse);
    }

}
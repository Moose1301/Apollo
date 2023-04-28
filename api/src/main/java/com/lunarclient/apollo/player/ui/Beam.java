package com.lunarclient.apollo.player.ui;

import com.lunarclient.apollo.world.ApolloBlockLocation;
import java.awt.Color;
import lombok.Builder;
import lombok.Getter;

/**
 * Represents a beacon beam which can be shown on the client.
 *
 * @since 1.0.0
 */
@Getter
@Builder(setterPrefix = "with")
public class Beam {

    /**
     * Returns the beam {@link String} id.
     *
     * @return the beam id
     * @since 1.0.0
     */
    String id;

    /**
     * Returns the beam {@link Color}.
     *
     * @return the beam color
     * @since 1.0.0
     */
    Color color;

    /**
     * Returns the beam {@link ApolloBlockLocation}.
     *
     * @return the beam block location
     * @since 1.0.0
     */
    ApolloBlockLocation location;
}

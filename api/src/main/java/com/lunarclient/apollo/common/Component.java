/*
 * This file is part of Apollo, licensed under the MIT License.
 *
 * Copyright (c) 2023 Moonsworth
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.lunarclient.apollo.common;

import java.awt.Color;
import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

/**
 * Represents a component which can be shown on the client.
 *
 * @since 1.0.0
 */
@Getter
@Builder
public final class Component {

    /**
     * Returns the component {@link String} content.
     *
     * @return the component content
     * @since 1.0.0
     */
    String content;

    /**
     * Returns the component {@link Color}.
     *
     * @return the component color
     * @since 1.0.0
     */
    @Builder.Default
    Color color = Color.WHITE;

    /**
     * Returns a {@link List} of {@link TextDecorators}.
     *
     * @return the component decorators
     * @since 1.0.0
     */
    @Builder.Default
    List<TextDecorators> decorators = Collections.emptyList();

    /**
     * Returns a {@link List} of {@link Component}.
     *
     * @return the component children
     * @since 1.0.0
     */
    @Builder.Default
    List<Component> children = Collections.emptyList();

    /**
     * Represents the text decorator.
     *
     * @since 1.0.0
     */
    public enum TextDecorators {
        OBFUSCATED,
        BOLD,
        STRIKETHROUGH,
        UNDERLINED,
        ITALIC
    }

}

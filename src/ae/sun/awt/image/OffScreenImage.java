/*
 * Copyright 1996-2007 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */

package ae.sun.awt.image;

import ae.java.awt.Component;
import ae.java.awt.Color;
import ae.java.awt.SystemColor;
import ae.java.awt.Font;
import ae.java.awt.Graphics;
import ae.java.awt.Graphics2D;
import ae.java.awt.GraphicsEnvironment;
import ae.java.awt.image.BufferedImage;
import ae.java.awt.image.ImageProducer;
import ae.java.awt.image.ColorModel;
import ae.java.awt.image.WritableRaster;
import ae.sun.java2d.SunGraphics2D;
import ae.sun.java2d.SurfaceData;

/**
 * This is a special variant of BufferedImage that keeps a reference to
 * a Component.  The Component's foreground and background colors and
 * default font are used as the defaults for this image.
 */
public class OffScreenImage extends BufferedImage {

    protected Component c;
    private OffScreenImageSource osis;
    private Font defaultFont;

    /**
     * Constructs an OffScreenImage given a color model and tile,
     * for offscreen rendering to be used with a given component.
     * The component is used to obtain the foreground color, background
     * color and font.
     */
    public OffScreenImage(Component c, ColorModel cm, WritableRaster raster,
                          boolean isRasterPremultiplied)
    {
        super(cm, raster, isRasterPremultiplied, null);
        this.c = c;
        initSurface(raster.getWidth(), raster.getHeight());
    }

    public Graphics getGraphics() {
        return createGraphics();
    }

    public Graphics2D createGraphics() {
        if (c == null) {
            GraphicsEnvironment env =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
            return env.createGraphics(this);
        }

        Color bg = c.getBackground();
        if (bg == null) {
            bg = SystemColor.window;
        }

        Color fg = c.getForeground();
        if (fg == null) {
            fg = SystemColor.windowText;
        }

        Font font = c.getFont();
        if (font == null) {
            if (defaultFont == null) {
                defaultFont = new Font("Dialog", Font.PLAIN, 12);
            }
            font = defaultFont;
        }

        return new SunGraphics2D(SurfaceData.getPrimarySurfaceData(this),
                                 fg, bg, font);
    }

    private void initSurface(int width, int height) {
        Graphics2D g2 = createGraphics();
        try {
            g2.clearRect(0, 0, width, height);
        } finally {
            g2.dispose();
        }
    }

    public ImageProducer getSource() {
        if (osis == null) {
            osis = new OffScreenImageSource(this);
        }
        return osis;
    }
}
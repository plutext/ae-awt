/*
 * Copyright 1997-2003 Sun Microsystems, Inc.  All Rights Reserved.
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

package ae.sun.java2d.pipe;

import ae.java.awt.Color;
import ae.java.awt.Image;
import ae.java.awt.Shape;
import ae.java.awt.geom.AffineTransform;
import ae.java.awt.image.BufferedImage;
import ae.java.awt.image.BufferedImageOp;
import ae.java.awt.image.ImageObserver;
import ae.java.awt.font.GlyphVector;
import ae.sun.java2d.SunGraphics2D;

/**
 * This is a class that implements all of the basic pixel rendering
 * methods as NOPs.
 * This class is useful for installing as the pipeline when the
 * clip is determined to be empty or when the composite operation is
 * determined to have no effect (i.e. rule == SRC_OVER, extraAlpha == 0.0).
 */
public class NullPipe
    implements PixelDrawPipe, PixelFillPipe, ShapeDrawPipe, TextPipe,
    DrawImagePipe
{
    public void drawLine(SunGraphics2D sg,
                         int x1, int y1, int x2, int y2) {
    }

    public void drawRect(SunGraphics2D sg,
                         int x, int y, int width, int height) {
    }

    public void fillRect(SunGraphics2D sg,
                         int x, int y, int width, int height) {
    }

    public void drawRoundRect(SunGraphics2D sg,
                              int x, int y, int width, int height,
                              int arcWidth, int arcHeight) {
    }

    public void fillRoundRect(SunGraphics2D sg,
                              int x, int y, int width, int height,
                              int arcWidth, int arcHeight) {
    }

    public void drawOval(SunGraphics2D sg,
                         int x, int y, int width, int height) {
    }

    public void fillOval(SunGraphics2D sg,
                         int x, int y, int width, int height) {
    }

    public void drawArc(SunGraphics2D sg,
                        int x, int y, int width, int height,
                        int startAngle, int arcAngle) {
    }

    public void fillArc(SunGraphics2D sg,
                        int x, int y, int width, int height,
                        int startAngle, int arcAngle) {
    }

    public void drawPolyline(SunGraphics2D sg,
                             int xPoints[], int yPoints[],
                             int nPoints) {
    }

    public void drawPolygon(SunGraphics2D sg,
                            int xPoints[], int yPoints[],
                            int nPoints) {
    }

    public void fillPolygon(SunGraphics2D sg,
                            int xPoints[], int yPoints[],
                            int nPoints) {
    }

    public void draw(SunGraphics2D sg, Shape s) {
    }

    public void fill(SunGraphics2D sg, Shape s) {
    }

    public void drawString(SunGraphics2D sg, String s, double x, double y) {
    }

    public void drawGlyphVector(SunGraphics2D sg, GlyphVector g,
                                float x, float y) {
    }

    public void drawChars(SunGraphics2D sg,
                                char data[], int offset, int length,
                                int x, int y) {
    }

    public boolean copyImage(SunGraphics2D sg, Image img,
                             int x, int y,
                             Color bgColor,
                             ImageObserver observer) {
        return false;
    }
    public boolean copyImage(SunGraphics2D sg, Image img,
                             int dx, int dy, int sx, int sy, int w, int h,
                             Color bgColor,
                             ImageObserver observer) {
        return false;
    }
    public boolean scaleImage(SunGraphics2D sg, Image img, int x, int y,
                              int w, int h,
                              Color bgColor,
                              ImageObserver observer) {
        return false;
    }
    public boolean scaleImage(SunGraphics2D sg, Image img,
                              int dx1, int dy1, int dx2, int dy2,
                              int sx1, int sy1, int sx2, int sy2,
                              Color bgColor,
                              ImageObserver observer) {
        return false;
    }
    public boolean transformImage(SunGraphics2D sg, Image img,
                                  AffineTransform atfm,
                                  ImageObserver observer) {
        return false;
    }
    public void transformImage(SunGraphics2D sg, BufferedImage img,
                               BufferedImageOp op, int x, int y) {
    }
}

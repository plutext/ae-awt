/*
 * Copyright 1997-2002 Sun Microsystems, Inc.  All Rights Reserved.
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

import ae.java.awt.AlphaComposite;
import ae.java.awt.CompositeContext;
import ae.java.awt.PaintContext;
import ae.java.awt.Rectangle;
import ae.java.awt.Shape;
import ae.java.awt.RenderingHints;
import ae.java.awt.image.ColorModel;
import ae.java.awt.image.BufferedImage;
import ae.java.awt.image.Raster;
import ae.java.awt.image.WritableRaster;
import ae.sun.awt.image.BufImgSurfaceData;
import ae.sun.java2d.SunGraphics2D;
import ae.sun.java2d.SurfaceData;
import ae.sun.java2d.loops.Blit;
import ae.sun.java2d.loops.CompositeType;
import ae.sun.java2d.loops.MaskBlit;

public class GeneralCompositePipe implements CompositePipe {
    class TileContext {
        SunGraphics2D sunG2D;
        PaintContext paintCtxt;
        CompositeContext compCtxt;
        ColorModel compModel;
        Object pipeState;

        public TileContext(SunGraphics2D sg, PaintContext pCtx,
                           CompositeContext cCtx, ColorModel cModel) {
            sunG2D = sg;
            paintCtxt = pCtx;
            compCtxt = cCtx;
            compModel = cModel;
        }
    }

    public Object startSequence(SunGraphics2D sg, Shape s, Rectangle devR,
                                int[] abox) {
        RenderingHints hints = sg.getRenderingHints();
        ColorModel model = sg.getDeviceColorModel();
        PaintContext paintContext =
            sg.paint.createContext(model, devR, s.getBounds2D(),
                                   sg.cloneTransform(),
                                   hints);
        CompositeContext compositeContext =
            sg.composite.createContext(paintContext.getColorModel(), model,
                                       hints);
        return new TileContext(sg, paintContext, compositeContext, model);
    }

    public boolean needTile(Object ctx, int x, int y, int w, int h) {
        return true;
    }

    /**
    * GeneralCompositePipe.renderPathTile works with custom composite operator
    * provided by an application
    */
    public void renderPathTile(Object ctx,
                               byte[] atile, int offset, int tilesize,
                               int x, int y, int w, int h) {
        TileContext context = (TileContext) ctx;
        PaintContext paintCtxt = context.paintCtxt;
        CompositeContext compCtxt = context.compCtxt;
        SunGraphics2D sg = context.sunG2D;

        Raster srcRaster = paintCtxt.getRaster(x, y, w, h);
        ColorModel paintModel = paintCtxt.getColorModel();

        Raster dstRaster;
        Raster dstIn;
        WritableRaster dstOut;

        SurfaceData sd = sg.getSurfaceData();
        dstRaster = sd.getRaster(x, y, w, h);
        if (dstRaster instanceof WritableRaster && atile == null) {
            dstOut = (WritableRaster) dstRaster;
            dstOut = dstOut.createWritableChild(x, y, w, h, 0, 0, null);
            dstIn = dstOut;
        } else {
            dstIn = dstRaster.createChild(x, y, w, h, 0, 0, null);
            dstOut = dstIn.createCompatibleWritableRaster();
        }

        compCtxt.compose(srcRaster, dstIn, dstOut);

        if (dstRaster != dstOut && dstOut.getParent() != dstRaster) {
            if (dstRaster instanceof WritableRaster && atile == null) {
                ((WritableRaster) dstRaster).setDataElements(x, y, dstOut);
            } else {
                ColorModel cm = sg.getDeviceColorModel();
                BufferedImage resImg =
                    new BufferedImage(cm, dstOut,
                                      cm.isAlphaPremultiplied(),
                                      null);
                SurfaceData resData = BufImgSurfaceData.createData(resImg);
                if (atile == null) {
                    Blit blit = Blit.getFromCache(resData.getSurfaceType(),
                                                  CompositeType.SrcNoEa,
                                                  sd.getSurfaceType());
                    blit.Blit(resData, sd, AlphaComposite.Src, null,
                              0, 0, x, y, w, h);
                } else {
                    MaskBlit blit = MaskBlit.getFromCache(resData.getSurfaceType(),
                                                          CompositeType.SrcNoEa,
                                                          sd.getSurfaceType());
                    blit.MaskBlit(resData, sd, AlphaComposite.Src, null,
                                  0, 0, x, y, w, h,
                                  atile, offset, tilesize);
                }
            }
        }
    }

    public void skipTile(Object ctx, int x, int y) {
        return;
    }

    public void endSequence(Object ctx) {
        TileContext context = (TileContext) ctx;
        if (context.paintCtxt != null) {
            context.paintCtxt.dispose();
        }
        if (context.compCtxt != null) {
            context.compCtxt.dispose();
        }
    }

}
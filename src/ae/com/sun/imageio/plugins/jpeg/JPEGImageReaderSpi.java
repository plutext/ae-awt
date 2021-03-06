/*
 * Copyright 2000-2004 Sun Microsystems, Inc.  All Rights Reserved.
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

package ae.com.sun.imageio.plugins.jpeg;

import java.util.Locale;
import ae.javax.imageio.spi.ImageReaderSpi;
import ae.javax.imageio.stream.ImageInputStream;
import ae.javax.imageio.spi.IIORegistry;
import ae.javax.imageio.spi.ServiceRegistry;
import java.io.IOException;
import ae.javax.imageio.ImageReader;
import ae.javax.imageio.IIOException;

public class JPEGImageReaderSpi extends ImageReaderSpi {

    private static String [] writerSpiNames =
        {"com.sun.imageio.plugins.jpeg.JPEGImageWriterSpi"};

    private boolean registered = false;

    public JPEGImageReaderSpi() {
        super(JPEG.vendor,
              JPEG.version,
              JPEG.names,
              JPEG.suffixes,
              JPEG.MIMETypes,
              "com.sun.imageio.plugins.jpeg.JPEGImageReader",
              STANDARD_INPUT_TYPE,
              writerSpiNames,
              true,
              JPEG.nativeStreamMetadataFormatName,
              JPEG.nativeStreamMetadataFormatClassName,
              null, null,
              true,
              JPEG.nativeImageMetadataFormatName,
              JPEG.nativeImageMetadataFormatClassName,
              null, null
              );
    }

    public void onRegistration(ServiceRegistry registry,
                               Class<?> category) {
        if (registered) {
            return;
        }
        try {
            java.security.AccessController.doPrivileged(
                new sun.security.action.LoadLibraryAction("jpeg"));
            // Stuff it all into one lib for first pass
            //java.security.AccessController.doPrivileged(
            //new sun.security.action.LoadLibraryAction("imageioIJG"));
        } catch (Throwable e) { // Fail on any Throwable
            // if it can't be loaded, deregister and return
            registry.deregisterServiceProvider(this);
            return;
        }

        registered = true;
    }

    public String getDescription(Locale locale) {
        return "Standard JPEG Image Reader";
    }

    public boolean canDecodeInput(Object source) throws IOException {
        if (!(source instanceof ImageInputStream)) {
            return false;
        }
        ImageInputStream iis = (ImageInputStream) source;
        iis.mark();
        // If the first two bytes are a JPEG SOI marker, it's probably
        // a JPEG file.  If they aren't, it definitely isn't a JPEG file.
        int byte1 = iis.read();
        int byte2 = iis.read();
        iis.reset();
        if ((byte1 == 0xFF) && (byte2 == JPEG.SOI)) {
            return true;
        }
        return false;
    }

    public ImageReader createReaderInstance(Object extension)
        throws IIOException {
        return new JPEGImageReader(this);
    }

}

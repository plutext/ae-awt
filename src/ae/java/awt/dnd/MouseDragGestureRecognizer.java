/*
 * Copyright 1998-2003 Sun Microsystems, Inc.  All Rights Reserved.
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

package ae.java.awt.dnd;


import ae.java.awt.Component;
import ae.java.awt.event.MouseEvent;
import ae.java.awt.event.MouseListener;
import ae.java.awt.event.MouseMotionListener;

/**
 * This abstract subclass of <code>DragGestureRecognizer</code>
 * defines a <code>DragGestureRecognizer</code>
 * for mouse-based gestures.
 *
 * Each platform implements its own concrete subclass of this class,
 * available via the Toolkit.createDragGestureRecognizer() method,
 * to encapsulate
 * the recognition of the platform dependent mouse gesture(s) that initiate
 * a Drag and Drop operation.
 * <p>
 * Mouse drag gesture recognizers should honor the
 * drag gesture motion threshold, available through
 * {@link DragSource#getDragThreshold}.
 * A drag gesture should be recognized only when the distance
 * in either the horizontal or vertical direction between
 * the location of the latest mouse dragged event and the
 * location of the corresponding mouse button pressed event
 * is greater than the drag gesture motion threshold.
 * <p>
 * Drag gesture recognizers created with
 * {@link DragSource#createDefaultDragGestureRecognizer}
 * follow this convention.
 *
 * @author Laurence P. G. Cable
 *
 * @see ae.java.awt.dnd.DragGestureListener
 * @see ae.java.awt.dnd.DragGestureEvent
 * @see ae.java.awt.dnd.DragSource
 */

public abstract class MouseDragGestureRecognizer extends DragGestureRecognizer implements MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 6220099344182281120L;

    /**
     * Construct a new <code>MouseDragGestureRecognizer</code>
     * given the <code>DragSource</code> for the
     * <code>Component</code> c, the <code>Component</code>
     * to observe, the action(s)
     * permitted for this drag operation, and
     * the <code>DragGestureListener</code> to
     * notify when a drag gesture is detected.
     * <P>
     * @param ds  The DragSource for the Component c
     * @param c   The Component to observe
     * @param act The actions permitted for this Drag
     * @param dgl The DragGestureListener to notify when a gesture is detected
     *
     */

    protected MouseDragGestureRecognizer(DragSource ds, Component c, int act, DragGestureListener dgl) {
        super(ds, c, act, dgl);
    }

    /**
     * Construct a new <code>MouseDragGestureRecognizer</code>
     * given the <code>DragSource</code> for
     * the <code>Component</code> c,
     * the <code>Component</code> to observe, and the action(s)
     * permitted for this drag operation.
     * <P>
     * @param ds  The DragSource for the Component c
     * @param c   The Component to observe
     * @param act The actions permitted for this drag
     */

    protected MouseDragGestureRecognizer(DragSource ds, Component c, int act) {
        this(ds, c, act, null);
    }

    /**
     * Construct a new <code>MouseDragGestureRecognizer</code>
     * given the <code>DragSource</code> for the
     * <code>Component</code> c, and the
     * <code>Component</code> to observe.
     * <P>
     * @param ds  The DragSource for the Component c
     * @param c   The Component to observe
     */

    protected MouseDragGestureRecognizer(DragSource ds, Component c) {
        this(ds, c, DnDConstants.ACTION_NONE);
    }

    /**
     * Construct a new <code>MouseDragGestureRecognizer</code>
     * given the <code>DragSource</code> for the <code>Component</code>.
     * <P>
     * @param ds  The DragSource for the Component
     */

    protected MouseDragGestureRecognizer(DragSource ds) {
        this(ds, null);
    }

    /**
     * register this DragGestureRecognizer's Listeners with the Component
     */

    protected void registerListeners() {
        component.addMouseListener(this);
        component.addMouseMotionListener(this);
    }

    /**
     * unregister this DragGestureRecognizer's Listeners with the Component
     *
     * subclasses must override this method
     */


    protected void unregisterListeners() {
        component.removeMouseListener(this);
        component.removeMouseMotionListener(this);
    }

    /**
     * Invoked when the mouse has been clicked on a component.
     * <P>
     * @param e the <code>MouseEvent</code>
     */

    public void mouseClicked(MouseEvent e) { }

    /**
     * Invoked when a mouse button has been
     * pressed on a <code>Component</code>.
     * <P>
     * @param e the <code>MouseEvent</code>
     */

    public void mousePressed(MouseEvent e) { }

    /**
     * Invoked when a mouse button has been released on a component.
     * <P>
     * @param e the <code>MouseEvent</code>
     */

    public void mouseReleased(MouseEvent e) { }

    /**
     * Invoked when the mouse enters a component.
     * <P>
     * @param e the <code>MouseEvent</code>
     */

    public void mouseEntered(MouseEvent e) { }

    /**
     * Invoked when the mouse exits a component.
     * <P>
     * @param e the <code>MouseEvent</code>
     */

    public void mouseExited(MouseEvent e) { }

    /**
     * Invoked when a mouse button is pressed on a component.
     * <P>
     * @param e the <code>MouseEvent</code>
     */

    public void mouseDragged(MouseEvent e) { }

    /**
     * Invoked when the mouse button has been moved on a component
     * (with no buttons no down).
     * <P>
     * @param e the <code>MouseEvent</code>
     */

    public void mouseMoved(MouseEvent e) { }
}

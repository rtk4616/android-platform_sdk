/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Eclipse Public License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.eclipse.org/org/documents/epl-v10.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.adt.gscripts;

import com.android.ide.eclipse.adt.gscripts.IViewRule;
import com.android.ide.eclipse.adt.gscripts.INodeProxy;
import com.android.ide.eclipse.adt.gscripts.DropZone;
import com.android.ide.eclipse.adt.gscripts.Rect;
import com.android.ide.eclipse.adt.gscripts.Point;

import java.util.Map;
import java.util.ArrayList;


/**
 * An {@link IViewRule} for android.view.View and all its derived classes.
 * This is the "root" rule, that is used whenever there is not more specific rule to apply.
 */
public class AndroidViewViewRule implements IViewRule {

    private String mFqcn;

    /**
     * This method is called by the rule engine when the script is first loaded.
     * It gives the rule a chance to initialize itself.
     *
     * @param fqcn The fully qualified class name of the Layout or View that will be managed by
     *   this rule. This can be cached as it will never change for the lifetime of this rule
     *   instance. This may or may not match the script's filename as it may be the fqcn of a
     *   class derived from the one this rule can handle.
     * @return True if this rule can handle the given FQCN. False if the rule can't handle the
     *   given FQCN, in which case the rule engine will find another rule matching a parent clas.
     */
    public boolean onInitialize(String fqcn) {
        // This rule can handle anything.
        mFqcn = fqcn
        return true;
    }

    /**
     * This method is called by the rules engine just before the script is unloaded.
     */
    public void onDispose() {
    }

    /**
     * Returns the class name to display when an element is selected in the GLE.
     * <p/>
     * If null is returned, the GLE will automatically shorten the class name using its
     * own heuristic, which is to keep the first 2 package components and the class name.
     * The class name is the <code>fqcn</code> argument that was given
     * to {@link #onInitialize(String)}.
     *
     * @return Null for the default behavior or a shortened string.
     */
    public String getDisplayName() {
        // Use the default behavior.
        //return null;

        // For testing, we currently use this. TODO revert and just return null.
        def f = mFqcn.split("\\.");
        return "View:" + f[f.length-1];
    }


    // ==== XML Creation ====


    /**
     * Returns the default attributes that a new XML element of this type should have
     * when added to an XML layout file. Note that these defaults can be overridden by the
     * specific code performing the insertion.
     *
     * @return A map of attribute:values for a new element of this type. Can be null or empty.
     */
    public Map<?, ?> getDefaultAttributes() {
        return null;
    }



    // ==== Drag'n'drop support ====

    /**
     * Called when a drop operation starts, whilst the d'n'd is dragging the cursor over the
     * views. The purpose of the drop operation will be to create a new element.
     * <p/>
     * Drop targets that can't accept child views should always return null.
     * <p/>
     * Drop targets that can accept child views must return a non-empty list of drop zones,
     * customized to the actual bounds of the target.
     * The drop zones will be visually shown to the user. Once the user releases the mouse
     * in one of the drop zone, the dropAccept/dropFinish methods will be called.
     * <p/>
     * Note that at this stage, the drop operation does not offer a way to know what is going
     * to be dropped. We just know it's a view descriptor, typically from the layout palette,
     * but we don't know which view class yet.
     *
     * @param targetNode The XML view that is currently the target of the drop.
     * @return Null or an empty list if the rule rejects the drop, or a list of usable drop zones.
     */
    public ArrayList<DropZone> dropStart(INodeProxy targetNode) {
        // By default views do not accept child views.
        return null;
    }

    /**
     * Called after the user selects to drop the given source into one of the drop zones.
     */
    public void dropFinish(
            String sourceFqcn,
            INodeProxy targetNode,
            DropZone selectedZone,
            Point where) {
        // skip, we're not doing drag'n'drop here
    }


    public ArrayList<DropZone> moveStart(INodeProxy sourceNode, INodeProxy targetNode, boolean copy) {
        // later
    }

    public void moveFinish(INodeProxy sourceNode, INodeProxy targetNode, boolean copy, DropZone selectedZone) {
        // later
    }
}

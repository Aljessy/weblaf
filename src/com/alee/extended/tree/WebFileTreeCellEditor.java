/*
* This file is part of WebLookAndFeel library.
*
* WebLookAndFeel library is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* WebLookAndFeel library is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with WebLookAndFeel library.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.alee.extended.tree;

import com.alee.extended.image.WebImage;
import com.alee.laf.text.WebTextField;
import com.alee.laf.tree.WebTreeCellEditor;
import com.alee.utils.FileUtils;

import java.io.File;

/**
 * @author Mikle Garin
 */

public class WebFileTreeCellEditor extends WebTreeCellEditor<WebTextField>
{
    public WebFileTreeCellEditor ()
    {
        super ();
        clickCountToStart = 3;
        autoUpdateLeadingIcon = false;
        delegate = new FileNameEditorDelegate ();
    }

    /**
     * Custom editor delegate for WebFileTableCellEditor.
     */
    protected class FileNameEditorDelegate extends EditorDelegate<FileTreeNode>
    {
        /**
         * {@inheritDoc}
         */
        @Override
        public void setValue ( FileTreeNode value )
        {
            final File file = value.getFile ();
            editorComponent.setLeadingComponent ( new WebImage ( FileUtils.getFileIcon ( file ) ) );
            FileUtils.displayFileName ( editorComponent, file );
            super.setValue ( value );
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean stopCellEditing ()
        {
            final File file = value.getFile ();
            final String newName = editorComponent.getText ();
            if ( !newName.equals ( file.getName () ) )
            {
                final File renamed = new File ( file.getParent (), newName );
                if ( file.renameTo ( renamed ) )
                {
                    value.setFile ( renamed );
                }
                return super.stopCellEditing ();
            }
            else
            {
                super.cancelCellEditing ();
                return true;
            }
        }
    }
}
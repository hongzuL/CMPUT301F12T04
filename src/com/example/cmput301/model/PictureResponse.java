/*******************************************************************************
 * Copyright (c) 2012 Jason Reddekopp, Andrew McCann, Daniel Sopel, David Yu.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Jason Reddekopp, Andrew McCann, Daniel Sopel, David Yu - initial API and                              
 *     implementation
 ******************************************************************************/
package com.example.cmput301.model;

import java.util.Date;

import android.graphics.Picture;

public class PictureResponse extends Response {
	private static final long serialVersionUID = 1L;
	private Picture picture;

    public PictureResponse(Picture picture, String annotation, Date timestamp) {
        super(annotation, timestamp);
        this.picture = picture;
    }

    public Picture getPicture() {
        return this.picture;
    }

    public Response clone() {
        return null;

    }
}
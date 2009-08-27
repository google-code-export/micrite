/* ===========================================================
 * $Id$
 * This file is part of Micrite
 * ===========================================================
 *
 * (C) Copyright 2009, by Gaixie.org and Contributors.
 * 
 * Project Info:  http://micrite.gaixie.org/
 *
 * Micrite is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Micrite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Micrite.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.gaixie.micrite.security;

import org.gaixie.micrite.MicriteException;


/**
 * A base exception class for Micrite Security.
 */
public class SecurityException extends MicriteException {
    
    
    private static final long serialVersionUID = -4146997497348618309L;


    public SecurityException() {
        super();
    }
    
    
    /**
     * Construct SecurityException with message string.
     *
     * @param s Error message string.
     */
    public SecurityException(String s) {
        super(s);
    }
    
    
    /**
     * Construct SecurityException, wrapping existing throwable.
     *
     * @param s Error message
     * @param t Existing connection to wrap.
     */
    public SecurityException(String s, Throwable t) {
        super(s, t);
    }
    
    
    /**
     * Construct SecurityException, wrapping existing throwable.
     *
     * @param t Existing exception to be wrapped.
     */
    public SecurityException(Throwable t) {
        super(t);
    }
    
}

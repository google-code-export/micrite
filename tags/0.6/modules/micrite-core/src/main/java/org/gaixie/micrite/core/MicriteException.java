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

package org.gaixie.micrite.core;

import java.io.PrintStream;
import java.io.PrintWriter;


/**
 * MicriteException 类是Micrite中所有自定义异常的超类。
 * <p>
 * 
 */
public abstract class MicriteException extends Exception {
    
    private final Throwable cause;
    
    
    /**
     * Construct emtpy exception object.
     */
    public MicriteException() {
        super();
        cause = null;
    }
    
    
    /**
     * Construct MicriteException with message string.
     * @param s Error message string.
     */
    public MicriteException(String s) {
        super(s);
        cause = null;
    }
    
    
    /**
     * Construct MicriteException, wrapping existing throwable.
     * @param s Error message
     * @param t Existing connection to wrap.
     */
    public MicriteException(String s, Throwable t) {
        super(s);
        cause = t;
    }
    
    
    /**
     * Construct MicriteException, wrapping existing throwable.
     * @param t Existing exception to be wrapped.
     */
    public MicriteException(Throwable t) {
        cause = t;
    }
    
    
    /**
     * Get root cause object, or null if none.
     * @return Root cause or null if none.
     */
    public Throwable getRootCause() {
        return cause;
    }
    
    
    /**
     * Get root cause message.
     * @return Root cause message.
     */
    public String getRootCauseMessage() {
        String message = null;
        if (getRootCause()!=null) {
            if (getRootCause().getCause()!=null) {
                message = getRootCause().getCause().getMessage();
            }
            message = (message == null) ? getRootCause().getMessage() : message;
            message = (message == null) ? super.getMessage() : message;
            message = (message == null) ? "NONE" : message;
        }
        return message;
    }
    
    
    /**
     * Print stack trace for exception and for root cause exception if htere is one.
     * @see java.lang.Throwable#printStackTrace()
     */
    public void printStackTrace() {
        super.printStackTrace();
        if (cause != null) {
            System.out.println("--- ROOT CAUSE ---");
            cause.printStackTrace();
        }
    }
    
    
    /**
     * Print stack trace for exception and for root cause exception if htere is one.
     * @param s Stream to print to.
     */
    public void printStackTrace(PrintStream s) {
        super.printStackTrace(s);
        if (cause != null) {
            s.println("--- ROOT CAUSE ---");
            cause.printStackTrace(s);
        }
    }
    
    
    /**
     * Print stack trace for exception and for root cause exception if htere is one.
     * @param s Writer to write to.
     */
    public void printStackTrace(PrintWriter s) {
        super.printStackTrace(s);
        if (null != cause) {
            s.println("--- ROOT CAUSE ---");
            cause.printStackTrace(s);
        }
    }
    
}

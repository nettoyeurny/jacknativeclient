/*
 * Licensed under the GNU Lesser General Public License (LGPL).
 * This comes with NO WARRANTY. See file LICENSE for details.
 */

package com.noisepages.nettoyeur.jack;

/**
 * Checked exception for Jack.
 * 
 * @author Jens Gulden
 * @author Peter Brinkmann (peter.brinkmann@gmail.com
 */
public class JackException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public JackException() {
		super();
	}
	
	public JackException(String msg) {
		super(msg);
	}
}

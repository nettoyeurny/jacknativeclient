/*
 * Licensed under the GNU Lesser General Public License (LGPL).
 * This comes with NO WARRANTY. See file LICENSE for details.
 */

package com.noisepages.nettoyeur.jack;

/**
 * Interface for handling zombification events.
 * 
 * @author Peter Brinkmann (peter.brinkmann@gmail.com)
 */
public interface JackNativeClientListener {
	
	/**
	 * Deal with a zombified jack client.
	 */
	void handleShutdown(JackNativeClient client);
}

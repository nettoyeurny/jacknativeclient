/*
 * Licensed under the GNU Lesser General Public License (LGPL).
 * This comes with NO WARRANTY. See file LICENSE for details.
 */

package com.noisepages.nettoyeur.jack;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.HashSet;
import java.util.Set;


/**
 * Bare bones wrapper for the basic functionality of jack clients.
 * 
 * @author Jens Gulden
 * @author Peter Brinkmann (peter.brinkmann@gmail.com)
 */
public abstract class JackNativeClient {

	private long infPointer = 0;
	private final Set<JackNativeClientListener> listeners = new HashSet<JackNativeClientListener>();
	private final FloatBuffer[] outBuffers;
	private final FloatBuffer[] inBuffers;
	private final int portsIn;
	private final int portsOut;


	/**
	 * Constructor; opens native client
	 * 
	 * @param name: name of native client
	 * @param portsIn: number of input ports
	 * @param portsOut: number of output ports
	 * @param proc: processor for jack callback
	 * @throws JackException
	 */
	public JackNativeClient(String name, int portsIn, int portsOut) throws JackException {
		this(name, portsIn, portsOut, true);
	}

	/**
	 * Opens and starts a native JACK client
	 * 
	 * @param name
	 * @param portsIn
	 * @param portsOut
	 * @param proc
	 * @param isDaemon: flag indicating whether to attach JACK thread as daemon
	 * @throws JackException
	 */
	public JackNativeClient(String name, int portsIn, int portsOut, boolean isDaemon) throws JackException {
		if (name==null || name.equals("")) throw new IllegalArgumentException("name cannot be null or empty");
		if (portsIn<0 || portsIn>getMaxPorts()) throw new IllegalArgumentException("input ports out of range");
		if (portsOut<0 || portsOut>getMaxPorts()) throw new IllegalArgumentException("output ports out of range");

		this.portsIn = portsIn;
		this.portsOut = portsOut;
		inBuffers = new FloatBuffer[portsIn];
		outBuffers = new FloatBuffer[portsOut];
		infPointer = openClient(name, portsIn, portsOut, isDaemon);
	}
	
	/**
	 * Audio processing callback for Jack.
	 */
	abstract protected void process(FloatBuffer[] inBuffers, FloatBuffer[] outBuffers);

	/**
	 * connects all input ports to ports specified by target
	 * 
	 * @param target regular expression specifying target ports; "" means physical ports, and null means no connection
	 * @return number of connections made
	 */
	public int connectInputPorts(String target) {
		return connectInputPorts(0, portsIn, target);
	}

	/**
	 * connects all output ports to ports specified by target
	 * 
	 * @param target regular expression specifying target ports; "" means physical ports, and null means no connection
	 * @return number of connections made
	 */
	public int connectOutputPorts(String target) {
		return connectOutputPorts(0, portsOut, target);
	}

	/**
	 * connects a given range of input ports to ports specified by target
	 * 
	 * @param port first port to be connected
	 * @param range number of ports to be connected
	 * @param target regular expression specifying target ports; "" means physical ports, and null means no connection
	 * @return number of connections made
	 */
	public int connectInputPorts(int port, int range, String target) {
		checkRange(port, range, portsIn);
		return connectInputPorts(infPointer, port, range, target);
	}

	/**
	 * connects a given range of output ports to ports specified by target
	 * 
	 * @param port first port to be connected
	 * @param range number of ports to be connected
	 * @param target regular expression specifying target ports; "" means physical ports, and null means no connection
	 * @return number of connections made
	 */
	public int connectOutputPorts(int port, int range, String target) {
		checkRange(port, range, portsOut);
		return connectOutputPorts(infPointer, port, range, target);
	}

	/**
	 * disconnects all input ports
	 * 
	 * @return number of ports disconnected
	 */
	public int disconnectInputPorts() {
		return disconnectInputPorts(0, portsIn);
	}

	/**
	 * disconnects all output ports
	 * 
	 * @return number of ports disconnected
	 */
	public int disconnectOutputPorts() {
		return disconnectOutputPorts(0, portsOut);
	}

	/**
	 * disconnects a range of input ports
	 * 
	 * @param port first port to be disconnected
	 * @param range number of ports to be disconnected
	 * @return number of ports disconnected
	 */
	public int disconnectInputPorts(int port, int range) {
		checkRange(port, range, portsIn);
		return disconnectInputPorts(infPointer, port, range);
	}

	/**
	 * disconnects a range of output ports
	 * 
	 * @param port first port to be disconnected
	 * @param range number of ports to be disconnected
	 * @return number of ports disconnected
	 */
	public int disconnectOutputPorts(int port, int range) {
		checkRange(port, range, portsOut);
		return disconnectOutputPorts(infPointer, port, range);
	}

	private void checkRange(int port, int range, int nPorts) {
		if (infPointer==0) throw new IllegalStateException("native client invalid");
		if (port<0 || port+range>nPorts) throw new IllegalArgumentException("ports out of range");
	}

	/**
	 * closes and deallocates the native client
	 */
	public void close() {
		if (infPointer!=0) {
			closeClient(infPointer);
			infPointer = 0;
		}
	}

	/**
	 * @return the current jack sample rate
	 * @throws JackException if jack is unavailable
	 */
	public static native int getSampleRate() throws JackException;

	/**
	 * @return the current jack buffer size
	 * @throws JackException if jack is unavailable
	 */
	public static native int getBufferSize() throws JackException;

	/**
	 * @return the largest admissible number of input/output ports per client
	 */
	public static native int getMaxPorts();

	protected void finalize() throws Throwable {
		try {
			close();
		} finally {
			super.finalize();
		}
	}

	/**
	 * Add a listener to the list of objects to be notified in the event of zombification.
	 * @param listener
	 */
	public synchronized void addListener(JackNativeClientListener listener) {
		listeners.add(listener);
	}

	/**
	 * Remove a listener.
	 * @param listener
	 */
	public synchronized void removeListener(JackNativeClientListener listener) {
		listeners.remove(listener);
	}

	// only private stuff after this point

	private native long openClient(String clientName, int portsIn, int portsOut, boolean isDaemon) throws JackException;
	private native int connectInputPorts(long infPtr, int port, int range, String target);
	private native int connectOutputPorts(long infPtr, int port, int range, String target);
	private native int disconnectInputPorts(long infPtr, int port, int range);
	private native int disconnectOutputPorts(long infPtr, int port, int range);
	private native void closeClient(long infPtr);

	/**
	 * JACK processing callback; called directly from native code, and only from there.
	 *
	 * @param in: the direct memory access input buffer
	 * @param out: the direct memory access output buffer
	 */
	@SuppressWarnings("unused")
	private void processBytes(ByteBuffer[] in, ByteBuffer[] out, boolean realloc) {
		if (realloc) {
			for (int i=0; i<portsOut; i++) {
				outBuffers[i] = out[i].order(ByteOrder.LITTLE_ENDIAN).asFloatBuffer();
			}
			for (int i=0; i<portsIn; i++) {
				inBuffers[i] = in[i].order(ByteOrder.LITTLE_ENDIAN).asFloatBuffer();
			}
		} else {
			for(int i=0; i<portsOut; i++) {
				outBuffers[i].rewind();
			}
			for(int i=0; i<portsIn; i++) {
				inBuffers[i].rewind();
			}
		}
		process(inBuffers, outBuffers);
	}

	/**
	 * This method is called directly from native code in the event of zombification.
	 */
	@SuppressWarnings("unused")
	private synchronized void handleShutdown() {
		System.err.println("native jack client "+this+" has been zombified!");
		for(JackNativeClientListener listener: listeners) {
			listener.handleShutdown(this);
		}
	}

	static {
		System.loadLibrary("jacknative");
	}
	
	// Simple main routine, just to check whether we can start Jack clients.
	public static void main(String[] args) throws JackException, InterruptedException {
		JackNativeClient client = new JackNativeClient("test_client", 1, 2) {
			@Override
			protected void process(FloatBuffer[] inBuffers, FloatBuffer[] outBuffers) {
				// Do nothing...
			}
		};
		client.connectInputPorts("system");
		client.connectOutputPorts("system");
		while (true) {
			Thread.sleep(1000);
		}
	}
}

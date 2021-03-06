/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_noisepages_nettoyeur_jack_JackNativeClient */

#ifndef _Included_com_noisepages_nettoyeur_jack_JackNativeClient
#define _Included_com_noisepages_nettoyeur_jack_JackNativeClient
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_noisepages_nettoyeur_jack_JackNativeClient
 * Method:    getSampleRate
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_noisepages_nettoyeur_jack_JackNativeClient_getSampleRate
  (JNIEnv *, jclass);

/*
 * Class:     com_noisepages_nettoyeur_jack_JackNativeClient
 * Method:    getBufferSize
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_noisepages_nettoyeur_jack_JackNativeClient_getBufferSize
  (JNIEnv *, jclass);

/*
 * Class:     com_noisepages_nettoyeur_jack_JackNativeClient
 * Method:    getMaxPorts
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_noisepages_nettoyeur_jack_JackNativeClient_getMaxPorts
  (JNIEnv *, jclass);

/*
 * Class:     com_noisepages_nettoyeur_jack_JackNativeClient
 * Method:    openClient
 * Signature: (Ljava/lang/String;IIZ)J
 */
JNIEXPORT jlong JNICALL Java_com_noisepages_nettoyeur_jack_JackNativeClient_openClient
  (JNIEnv *, jobject, jstring, jint, jint, jboolean);

/*
 * Class:     com_noisepages_nettoyeur_jack_JackNativeClient
 * Method:    connectInputPorts
 * Signature: (JIILjava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_noisepages_nettoyeur_jack_JackNativeClient_connectInputPorts
  (JNIEnv *, jobject, jlong, jint, jint, jstring);

/*
 * Class:     com_noisepages_nettoyeur_jack_JackNativeClient
 * Method:    connectOutputPorts
 * Signature: (JIILjava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_noisepages_nettoyeur_jack_JackNativeClient_connectOutputPorts
  (JNIEnv *, jobject, jlong, jint, jint, jstring);

/*
 * Class:     com_noisepages_nettoyeur_jack_JackNativeClient
 * Method:    disconnectInputPorts
 * Signature: (JII)I
 */
JNIEXPORT jint JNICALL Java_com_noisepages_nettoyeur_jack_JackNativeClient_disconnectInputPorts
  (JNIEnv *, jobject, jlong, jint, jint);

/*
 * Class:     com_noisepages_nettoyeur_jack_JackNativeClient
 * Method:    disconnectOutputPorts
 * Signature: (JII)I
 */
JNIEXPORT jint JNICALL Java_com_noisepages_nettoyeur_jack_JackNativeClient_disconnectOutputPorts
  (JNIEnv *, jobject, jlong, jint, jint);

/*
 * Class:     com_noisepages_nettoyeur_jack_JackNativeClient
 * Method:    closeClient
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_noisepages_nettoyeur_jack_JackNativeClient_closeClient
  (JNIEnv *, jobject, jlong);

#ifdef __cplusplus
}
#endif
#endif

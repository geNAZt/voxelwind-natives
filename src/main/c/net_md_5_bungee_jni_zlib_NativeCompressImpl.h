/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class net_md_5_bungee_jni_zlib_NativeCompressImpl */

#ifndef _Included_net_md_5_bungee_jni_zlib_NativeCompressImpl
#define _Included_net_md_5_bungee_jni_zlib_NativeCompressImpl
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     net_md_5_bungee_jni_zlib_NativeCompressImpl
 * Method:    initFields
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_net_md_15_bungee_jni_zlib_NativeCompressImpl_initFields
  (JNIEnv *, jclass);

/*
 * Class:     net_md_5_bungee_jni_zlib_NativeCompressImpl
 * Method:    end
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_net_md_15_bungee_jni_zlib_NativeCompressImpl_end
  (JNIEnv *, jobject, jlong, jboolean);

/*
 * Class:     net_md_5_bungee_jni_zlib_NativeCompressImpl
 * Method:    reset
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_net_md_15_bungee_jni_zlib_NativeCompressImpl_reset
  (JNIEnv *, jobject, jlong, jboolean);

/*
 * Class:     net_md_5_bungee_jni_zlib_NativeCompressImpl
 * Method:    init
 * Signature: (ZI)J
 */
JNIEXPORT jlong JNICALL Java_net_md_15_bungee_jni_zlib_NativeCompressImpl_init
  (JNIEnv *, jobject, jboolean, jint);

/*
 * Class:     net_md_5_bungee_jni_zlib_NativeCompressImpl
 * Method:    process
 * Signature: (JJIJIZ)I
 */
JNIEXPORT jint JNICALL Java_net_md_15_bungee_jni_zlib_NativeCompressImpl_process
  (JNIEnv *, jobject, jlong, jlong, jint, jlong, jint, jboolean);

/*
 * Class:     net_md_5_bungee_jni_zlib_NativeCompressImpl
 * Method:    checksum
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_net_md_15_bungee_jni_zlib_NativeCompressImpl_checksum
  (JNIEnv *, jobject, jlong);

#ifdef __cplusplus
}
#endif
#endif

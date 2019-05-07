//
// Created by yang qing on 2019/4/26.
//
#include <jni.h>
//#include "com_example_learncomponent_NDKTools"
JNIEXPORT jstring JNICALL Java_com_example_learncomponent_NDKTools_getStringFromNDK
  (JNIEnv *env,jobject jclass){
    return (*env)->NewStringUTF(env,"Hellow World，这是NDK的第一行代码");
  }
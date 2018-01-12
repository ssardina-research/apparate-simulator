#include "jni.h"
#include <stdio.h>
#include "pplanning_utils_HRTimer.h"
#include <linux/time.h>


JNIEXPORT void JNICALL Java_pplanning_utils_HRTimer_print
  (JNIEnv* env, jobject obj)
{
	printf(" C says hello - printing") ;
}

JNIEXPORT jlong JNICALL Java_pplannig_utils_HRTimer_getCurrentNanotime
  (JNIEnv* env, jobject obj)
{
	struct timespec time;
	clock_gettime(CLOCK_THREAD_CPUTIME_ID, &time);
	jlong currTime = time.tv_sec * 1000000000 + time.tv_nsec;
	return currTime;
}


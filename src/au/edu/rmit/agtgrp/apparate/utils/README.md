# High Resolution Timer via JNI  (`HRTimer` class)

High resolution timer utilising C code via the Java Native Interface. This allows some algorithms (like DAS)  to measure its own performance with much higher precision than allowed by Java library functions. 

It includes two parts:

1. `HRTimer` Java class interface: this is the interface to Java for programs to use. Programs can use this class to access the high resolution time clock.  This class file itself is the one calling the C code. 
    * This library provides public method `public native long getCurrentNanotime();` to be used by Java applications to get the time.
    * It is provided in package `au.edu.rmit.agtgrp.apparate.utils`
2. `libHRTimer.c` native library code: this is the C code providing the implementation of `getCurrentNanotime()` in C.

Again, Java aplications do not make use of the native library directly, but only via `HRTimer` Java class. 


**Note:** this utilizes Linux specific C code, and therefore sacrifices Java’s natural portability.  
 * A Windows version of this timer would be a trivial addition, and conditionally built into a `.dll` (dynamic link library) instead of a so (shared object)


## Compile the library

Good instructions on Java Native Interface can be found at in this [Java Tutorial](https://www.ntu.edu.sg/home/ehchua/programming/java/JavaNativeInterface.html):

1. Prepare `HRTimer.java` (in package `X`) and compile it using  `javac`.
2. Produce header file using `javah`. This will produce a file `X_HRTimer.h` header file.
    * This header file already contains the right names with the package `X` included.
3. Prepare and compile corresponding C code into a `libHRTimer.c` file.
    * Make sure JNIEXPORT methods provided are of the form `X_HRTimer_<method name>`
4. Compile C code into an `.so` library:
    * Note that `-lrt` needs to be at the end!

        ```
        export  JAVA_HOME=$(readlink -f /usr/bin/javac | sed "s:bin/javac::")
        
        # if you get: /usr/lib/jvm/java-8-oracle/include/jni.h:45:20: fatal error: jni_md.h: No such file or directory
        export C_INCLUDE_PATH=/usr/lib/jvm/java-8-oracle/include/linux:$C_INCLUDE_PATH
        
        # now compile
        gcc -I${JAVA_HOME}/include  -shared libHRTimer.c -o libHRTimer.so -fPIC -lrt
        ```

5. Use native methods: 
    * In the Java code, import `X.HRTimer` and use `<method name>` in your Java files.
    * Make sure your `LD_LIBRARY_PATH` includes the path where `libHRTimer.so` is placed (e.g., `lib/`):

        ```    
        # make the library created available for dynamic use at runtime
        export  LD_LIBRARY_PATH=$LD_LIBRARY_PATH:<dir where libHRTimer.so will be placed>
        ```    



## Use the library

First, library `libHRTimer.so` needs to be in your `LD_LIBRARY_PATH` or Or run java with `-Djava.library.path=lib/` to tell Java where native libraries used are. See [this post](https://examples.javacodegeeks.com/java-basics/java-library-path-what-is-it-and-how-to-use/)


Finally, Java can import class `HRTimer` and measure time as follows:

```
import au.edu.rmit.agtgrp.apparate.utils.HRTimer;

...
    

    // C Timer called via JNI Interface.
    HRTimer timer = new HRTimer();
    
    ...
    
    // Time snapshot at the last expansion performed. Used to perform timing estimates.
    long timeAtLastExpansion;

    
    ...

    
    // Use of native library    
    timeAtLastExpansion = timer.getCurrentNanotime();
```


## Speed comparison

Average difference between two calls of timing function of 1000 runs:

* C: 173 (via calls to `getCurrentNanotime()`)
* Java ThreadMX: 761 (via calls to `getCurrentThreadCpuTime()`)

This is a comparison of the average difference in system time retrieved via subsequent calls to the respective timing functions. 

We tried to reverse the order of the timing calls to check if cache-hits were responsible, but it made no observable difference. 

What these numbers do not show is the variance, which from experimentation, is minimal for the C
timer, and relatively high for the ThreadMX implementation.

For these reasons, the C Timer seems to be always better than the ThreadMX implementation.


## Credit

This library and analysis was provided by Geoff Sutcliffe and Rhys van der Waerden as part of their DAS implementation, and factored out to make it available for any other agent as a utility.


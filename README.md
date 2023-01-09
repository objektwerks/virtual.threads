Virtual Threads
---------------
>JDK 19 virtual threads and structured concurrency feature tests.

JDK
---
>To enable preview features and load modules see .jvmopts, configured
>as: ```"--enable-preview --add-modules jdk.incubator.concurrent"```

Test
----
1. sbt clean test

Resources
---------
1. [Virtual Threads](openjdk.org/jeps/425)
2. [Structured Concurrency](openjdk.org/jeps/428)
3. [Loom](www.marcobehler.com/guides/java-project-loom)
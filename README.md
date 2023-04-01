Virtual Threads
---------------
>JDK 20 virtual threads, scoped values and structured concurrency feature tests using Scala 3.

JDK 20
------
>To enable preview features and load modules see .jvmopts, configured
>as: ```"--enable-preview --add-modules jdk.incubator.concurrent"```

Test
----
1. sbt clean test

Resources
---------
1. [Virtual Threads: JEPS 425](openjdk.org/jeps/425)
2. [Scoped Values: JEPS 429](https://openjdk.org/jeps/429)
3. [Structured Concurrency: JEPS 428](openjdk.org/jeps/428)
4. [Loom](www.marcobehler.com/guides/java-project-loom)
5. [Http Server](https://github.com/objektwerks/http.server)
6. [Ultimate Guide to Virtual Threads](https://blog.rockthejvm.com/ultimate-guide-to-java-virtual-threads/)
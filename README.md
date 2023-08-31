Virtual Threads
---------------
>JDK 20 virtual threads, scoped values and structured concurrency feature tests using Scala 3.

>Software Mill Ox library feature tests are included as well.

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
7. [Software Mill Ox](https://softwaremill.com/prototype-loom-based-concurrency-api-for-scala/)
8. [Software Mill Go Channels in Scala](https://softwaremill.com/go-like-channels-using-project-loom-and-scala/)
9. [Software Mill More On Go Channels in Scala](https://softwaremill.com/go-like-channels-in-scala-receive-send-and-default-clauses/)
10. [What is blocking in Loom?](https://softwaremill.com/what-is-blocking-in-loom/)
11. [Odersky: Async](https://github.com/lampepfl/async/)
12. [Virtual Threads Design](https://blogs.oracle.com/javamagazine/post/java-virtual-threads)
13. [Software Mill Ox: Supervision, Kafka and Java 21](https://softwaremill.com/supervision-kafka-and-java-21-whats-new-in-ox/)
14. [Java 21 Virtual Threads](https://www.youtube.com/watch?v=5E0LU85EnTI)
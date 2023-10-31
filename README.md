# New Valve

A library that adds Downloader implementation with OkHttp to [NewPipeExtractor](https://github.com/TeamNewPipe/NewPipeExtractor).

# Usage

Run `initNewPipe()` once to initialize NewPipeExtractor. Pick a service and extractor you want. Full
list of services can be found in `ServiceList` class. Full list of extractors can be found in `StreamingService` class.

# Example

```kotlin
fun getVideoInfo() {
    initNewPipe()
    val service = ServiceList.YouTube

    val extractor = service.getStreamExtractor("https://www.youtube.com/watch?v=3L6RDYFXURA")
    extractor.fetchPage()

    println("Video name: " + extractor.name)
    println("Uploader: " + extractor.uploaderName)
    println("Category: " + extractor.category)
    println("Likes: " + extractor.likeCount)
    println("Views: " + extractor.viewCount)
}
```

More examples are in `src/test/kotlin/Examples.kt`. Note for Android: Add internet permission and run `extractor.fetchPage()` on IO thread.

# Installation

Set JVM toolchain to at least 11:

```kotlin
kotlin {
    jvmToolchain(11)
}
```

Add Jitpack:
```kotlin
repositories {
    maven { setUrl("https://jitpack.io") }
}
```

Add dependency:

```kotlin
implementation("com.github.shalva97:NewValve:1.0")
```

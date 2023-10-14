# New Valve

A library that adds Downloader implementation with OkHttp library.

# Usage

Run `initNewPipe()` once to initialize NewPipeExtractor. Pick a service and extractor you want. Full
list of services can be found in `ServiceList` class. 

# Example

```kotlin
fun getVideoInfo() {
    initNewPipe()
    val service = ServiceList.YouTube

    val extractor = service.getStreamExtractor("https://www.youtube.com/watch?v=3L6RDYFXURA")
    extractor.fetchPage()

    println("Video name: " + extractor.name)
    println("Uploader: " + extractor.uploaderName)
    extractor.audioStreams.forEach {
        println("Audio: ${it.averageBitrate} - ${it.codec} - ${it.content}")
    }
    extractor.videoStreams.forEach {
        println("Video: ${it.bitrate} - ${it.content}")
    }
}
```

You can find more examples in `src/test/kotlin/Examples.kt`.

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
    println("Category: " + extractor.category)
    println("Likes: " + extractor.likeCount)
    println("Views: " + extractor.viewCount)
}
```

You can find more examples in `src/test/kotlin/Examples.kt`.

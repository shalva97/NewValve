# New Valve

Welcome to New Valve! This library enhances the capabilities of the popular [NewPipeExtractor](https://github.com/TeamNewPipe/NewPipeExtractor) by providing a robust Downloader implementation based on OkHttp.

## What is New Valve?

New Valve is essentially a bridge that allows you to seamlessly integrate OkHttp's efficient and flexible networking capabilities with NewPipeExtractor. NewPipeExtractor is a powerful tool for extracting data from various streaming services, but it requires a Downloader implementation to fetch web pages and other network resources. New Valve provides this missing piece, making it easier to use NewPipeExtractor in your projects.

## Purpose and Problems Solved

The primary purpose of New Valve is to simplify the process of using NewPipeExtractor. By offering a pre-built, reliable Downloader, it solves the following problems:

*   **Reduces Boilerplate:** You no longer need to write your own Downloader implementation from scratch, saving you time and effort.
*   **Leverages OkHttp:** It utilizes OkHttp, a modern and widely-used HTTP client for Android and Java, known for its performance, reliability, and features like connection pooling and response caching.
*   **Simplifies Dependency Management:** New Valve bundles the necessary OkHttp dependency, making it easier to manage your project's dependencies.

## Benefits of Using New Valve

*   **Ease of Use:** Get started quickly with NewPipeExtractor without worrying about the complexities of network requests.
*   **Reliability:** Benefit from OkHttp's battle-tested networking stack.
*   **Performance:** Leverage OkHttp's optimizations for faster and more efficient data extraction.
*   **Focus on Core Logic:** Concentrate on how you want to use the extracted data, rather than on the intricacies of fetching it.

# Usage

Getting started with New Valve involves a few key steps. This section will guide you through the process, explaining the core components and how they work together.

## 1. Initialization

The first and most crucial step is to initialize NewPipeExtractor. This is done by calling the `initNewPipe()` function. This function, provided by New Valve, sets up the necessary Downloader (using OkHttp) and other configurations required for NewPipeExtractor to function correctly.

**Important:** You should call `initNewPipe()` **only once** during your application's lifecycle, typically at startup (e.g., in your `Application` class on Android, or the `main` function in a server-side application).

```kotlin
import com.github.shalva97.initNewPipe // Make sure to import the function

// Example: In your application's initialization code
fun initializeApp() {
    initNewPipe()
    // Other setup code for your application
}
```

## 2. Selecting a Service (`ServiceList`)

Once initialized, you need to choose the streaming service you want to interact with. NewPipeExtractor supports a variety of services (like YouTube, SoundCloud, PeerTube, etc.). The `ServiceList` class acts as a registry for all available services.

```kotlin
import org.schabi.newpipe.extractor.ServiceList // Import ServiceList

// Example: Accessing the YouTube service
val youtubeService = ServiceList.YouTube

// Example: Accessing the SoundCloud service
val soundcloudService = ServiceList.SoundCloud
```
Each property in `ServiceList` (e.g., `ServiceList.YouTube`) returns an instance of a class that implements the `StreamingService` interface. This object represents the chosen service and is your entry point for fetching data from it.

## 3. Understanding `StreamingService`

The `StreamingService` interface is a cornerstone of NewPipeExtractor. It defines a contract for how to interact with a specific streaming platform. Key responsibilities of a `StreamingService` implementation include:

*   **Providing Extractors:** It has methods to get different types of "extractors" (e.g., for videos, channels, playlists, search results).
*   **Service Information:** It holds metadata about the service, like its name and base URL.
*   **Capability Discovery:** It can indicate what features are supported for that service (e.g., liking, commenting).

When you get a service from `ServiceList` (like `ServiceList.YouTube`), you are working with an object that fulfills this `StreamingService` contract for YouTube.

## 4. Using Extractors

Extractors are specialized objects that know how to fetch and parse data for specific types of content (a video, a playlist, search results, etc.) from the service you've selected. You obtain an extractor instance from your `StreamingService` object.

The most common scenario is extracting information about a video stream. For this, you'd use the `getStreamExtractor()` method, passing the URL of the video:

```kotlin
// Assuming 'youtubeService' is your StreamingService instance for YouTube
val videoUrl = "https://www.youtube.com/watch?v=dQw4w9WgXcQ" // An example video URL
val streamExtractor = youtubeService.getStreamExtractor(videoUrl)
// streamExtractor is now an instance of StreamExtractor, ready to fetch data for this video.
```

NewPipeExtractor provides various types of extractors for different needs:

*   `getStreamExtractor(url)`: For individual video/audio streams.
*   `getChannelExtractor(url)`: For channel pages.
*   `getPlaylistExtractor(url)`: For playlists.
*   `getSearchExtractor(query, contentFilter, sortFilter)`: For performing searches.
*   `getCommentsExtractor(url)`: For fetching comments.
*   `getSuggestionExtractor(query)`: For getting search suggestions.

The exact methods available can be found by inspecting the `StreamingService` interface and its implementations. The choice of extractor depends on the URL or query you have and the type of information you want to retrieve.

## 5. Fetching Data (`fetchPage()`)

After obtaining an extractor (e.g., `streamExtractor`), it initially doesn't contain any data. You need to explicitly tell it to fetch the information from the network. This is done by calling the `fetchPage()` method on the extractor instance.

```kotlin
// Assuming 'streamExtractor' is an instance of a StreamExtractor
// This call will make a network request to the video page and parse its content.
streamExtractor.fetchPage()
```

**Important for Android Developers:** Network operations like `fetchPage()` are blocking and can take time. They **must** be performed on a background thread (e.g., using Kotlin Coroutines with `Dispatchers.IO`, RxJava Schedulers, or other threading mechanisms) to avoid blocking the main UI thread. Failure to do so will likely result in Application Not Responding (ANR) errors.

```kotlin
// Example of fetching on a background thread using Kotlin Coroutines (Android):
// viewModelScope.launch(Dispatchers.IO) {
//     try {
//         streamExtractor.fetchPage()
//         // Now process the data on the main thread if updating UI
//         withContext(Dispatchers.Main) {
//             // Update UI with extractor.getName(), etc.
//         }
//     } catch (e: Exception) {
//         // Handle network errors or extraction errors
//     }
// }
```

## 6. Accessing the Extracted Data

Once `fetchPage()` completes successfully (without throwing an exception), the extractor object is populated with the data retrieved from the URL. You can then access this data through its various properties and methods.

The specific properties available will depend on the type of extractor you used. For a `StreamExtractor`, you can typically get:

*   Video title (`extractor.name`)
*   Uploader information (`extractor.uploaderName`, `extractor.uploaderUrl`, `extractor.uploaderAvatarUrl`)
*   Description (`extractor.description`)
*   View count, like count, dislike count (`extractor.viewCount`, `extractor.likeCount`, `extractor.dislikeCount`)
*   Category, duration, upload date (`extractor.category`, `extractor.length`, `extractor.uploadDate`)
*   Available audio and video streams (`extractor.audioStreams`, `extractor.videoStreams`)
*   Subtitles (`extractor.subtitles`)
*   And much more!

Always refer to the documentation or the source code of the specific extractor class to see the full list of available data.

# Example

```kotlin
fun getVideoInfo() {
fun getVideoInfo() {
    // Step 1: Initialize NewPipe (ideally done once at application startup)
    // For this example, we call it directly. In a real app, this would be in your Application class or main()
    com.github.shalva97.initNewPipe() // Using fully qualified name for clarity here

    // Step 2: Select the desired service from ServiceList
    val service = org.schabi.newpipe.extractor.ServiceList.YouTube // Using YouTube as an example

    // Step 3 & 4: Get a StreamExtractor for a specific video URL
    // Replace with the URL of the video you want to fetch info for.
    val videoUrl = "https://www.youtube.com/watch?v=3L6RDYFXURA" // Example video
    val extractor: org.schabi.newpipe.extractor.stream.StreamExtractor = service.getStreamExtractor(videoUrl)

    // Step 5: Fetch the page data
    // IMPORTANT: In a real Android application, this MUST be done on a background thread.
    // e.g., using kotlinx.coroutines: GlobalScope.launch(Dispatchers.IO) { extractor.fetchPage() ... }
    // For this standalone K JVM example, direct call is fine.
    try {
        extractor.fetchPage()
    } catch (e: Exception) {
        println("An error occurred while fetching the page: ${e.message}")
        e.printStackTrace()
        return
    }

    // Step 6: Access the extracted information
    // Basic Information
    println("Video Title: ${extractor.name}")
    println("Uploader: ${extractor.uploaderName}")
    println("Uploader URL: ${extractor.uploaderUrl}")
    println("Upload Date: ${extractor.uploadDate?.textual}") // UploadDate is a UploaderDate object
    println("Duration (seconds): ${extractor.length}")
    println("Views: ${extractor.viewCount}")
    println("Likes: ${extractor.likeCount}")
    println("Dislikes: ${extractor.dislikeCount}") // May not always be available
    println("Category: ${extractor.category}")
    println("Description: ${extractor.description?.content}") // Description is a Html descrizione object

    // Audio Streams
    println("\nAvailable Audio Streams:")
    extractor.audioStreams.forEachIndexed { index, stream ->
        println("  Audio Stream ${index + 1}:")
        println("    Format: ${stream.format}")
        println("    Codec: ${stream.codec}")
        println("    Average Bitrate (kbps): ${stream.averageBitrate}")
        println("    Sampling Rate (Hz): ${stream.samplingRate}")
        println("    Content URL: ${stream.content}") // URL to download/stream
        println("    Quality: ${stream.quality}")
    }

    // Video Streams
    println("\nAvailable Video Streams:")
    extractor.videoStreams.forEachIndexed { index, stream ->
        println("  Video Stream ${index + 1}:")
        println("    Resolution: ${stream.resolution}")
        println("    Format: ${stream.format}")
        println("    Codec: ${stream.codec}")
        println("    Bitrate (kbps): ${stream.bitrate}") // Note: this is just 'bitrate', not 'averageBitrate' like in AudioStream
        println("    FPS: ${stream.fps}")
        println("    Content URL: ${stream.content}") // URL to download/stream
        println("    Quality: ${stream.quality}")
        if (stream.isVideoOnly) {
            println("    Video-Only: Yes")
        } else {
            println("    Video-Only: No (contains audio)")
        }
    }

    // Subtitles/Closed Captions
    println("\nAvailable Subtitles:")
    extractor.subtitles.forEachIndexed { index, subtitle ->
        println("  Subtitle ${index + 1}:")
        println("    Language Code: ${subtitle.locale.language}")
        println("    Format: ${subtitle.format}")
        println("    Is Auto-generated: ${subtitle.isAutoGenerated}")
        println("    Content URL: ${subtitle.content}")
    }
}
```

The `Examples.kt` file in `src/test/kotlin/` contains more runnable examples, including how to fetch channel information, playlist details, and search results. It's highly recommended to explore these examples to get a practical understanding of using different extractors.

# Installation

This section guides you through adding New Valve to your project.

## Prerequisites

*   **JVM Version:** New Valve requires your project to be configured to use JVM version 11 or higher.
    For Kotlin projects (including Android), you can set this in your `build.gradle` or `build.gradle.kts` file:
    ```kotlin
    // build.gradle.kts (Kotlin DSL)
    kotlin {
        jvmToolchain(11)
    }

    // build.gradle (Groovy DSL)
    // kotlin {
    //     jvmToolchain(11)
    // }
    // For Java-only projects, ensure your JDK is version 11+ and your
    // project's source/target compatibility is set accordingly.
    // android {
    //     compileOptions {
    //         sourceCompatibility = JavaVersion.VERSION_11
    //         targetCompatibility = JavaVersion.VERSION_11
    //     }
    //     kotlinOptions { // If you also use Kotlin in the Android project
    //         jvmTarget = "11"
    //     }
    // }
    ```

## Adding the Dependency (Gradle)

New Valve is hosted on JitPack. To add it to your project, follow these steps:

1.  **Add JitPack Repository:**
    If you haven't already, add JitPack to your list of repositories in your project's root `build.gradle` or `build.gradle.kts` file:

    ```kotlin
    // build.gradle.kts (Kotlin DSL)
    // In your settings.gradle.kts or root build.gradle.kts (depending on Gradle version)
    // dependencyResolutionManagement {
    //     repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS) // Optional but recommended
    //     repositories {
    //         google()
    //         mavenCentral()
    //         maven { setUrl("https://jitpack.io") } // Add JitPack
    //     }
    // }

    // build.gradle (Groovy DSL)
    // allprojects { // Or in settings.gradle for newer Gradle versions
    //     repositories {
    //         google()
    //         mavenCentral()
    //         maven { url 'https://jitpack.io' } // Add JitPack
    //     }
    // }
    ```
    *Note: For newer Gradle versions, repository declarations are often centralized in `settings.gradle` or `settings.gradle.kts`.*

2.  **Add New Valve Dependency:**
    Add the New Valve library as a dependency in your module-level `build.gradle` or `build.gradle.kts` file (usually `app/build.gradle` or `app/build.gradle.kts` for Android projects). Replace `1.5` with the latest version of New Valve you wish to use. You can find the latest version on the [JitPack page for New Valve](https://jitpack.io/#shalva97/NewValve).

    ```kotlin
    // build.gradle.kts (Kotlin DSL)
    dependencies {
        implementation("com.github.shalva97:NewValve:1.5")
        // ... other dependencies
    }

    // build.gradle (Groovy DSL)
    // dependencies {
    //     implementation 'com.github.shalva97:NewValve:1.5'
    //     // ... other dependencies
    // }
    ```

3.  **Sync Project:**
    Sync your project with Gradle files.

## Android Specific Considerations

If you are using New Valve in an Android project, please take note of the following:

1.  **Internet Permission:**
    Your application must request the `android.permission.INTERNET` permission to allow NewPipeExtractor (and OkHttp via New Valve) to make network requests. Add this to your `AndroidManifest.xml`:
    ```xml
    <uses-permission android:name="android.permission.INTERNET" />
    ```

2.  **Core Library Desugaring (for minSdk < 33 when using `java.nio`):**
    NewPipeExtractor and its dependencies might use Java APIs that are not available on older Android versions. Specifically, `java.nio` (New Input/Output) classes are fully available only from Android API level 33 (Android 13). If your `minSdk` is below 33, you **must** enable core library desugaring and include the `desugar_jdk_libs_nio` artifact.
    *   Enable desugaring in your module-level `build.gradle` or `build.gradle.kts`:
        ```kotlin
        // build.gradle.kts (Kotlin DSL)
        // android {
        //     compileOptions {
        //         isCoreLibraryDesugaringEnabled = true
        //     }
        // }

        // build.gradle (Groovy DSL)
        android {
            compileOptions {
                coreLibraryDesugaringEnabled true
            }
        }
        ```
    *   Add the desugaring dependency:
        ```kotlin
        // build.gradle.kts (Kotlin DSL)
        // dependencies {
        //     coreLibraryDesugaring("com.android.tools:desugar_jdk_libs_nio:2.0.4") // Check for the latest version
        // }

        // build.gradle (Groovy DSL)
        dependencies {
            coreLibraryDesugaring 'com.android.tools:desugar_jdk_libs_nio:2.0.4' // Check for the latest version
        }
        ```
    *   Refer to the official [Android documentation on core library desugaring](https://developer.android.com/studio/write/java8-support#library-desugaring) for the most up-to-date information and latest versions of the desugaring library.

3.  **Network Operations on IO Thread:**
    As mentioned in the "Usage" section, all network operations performed by the extractor (i.e., calls to `extractor.fetchPage()`) **must** be executed on a background (IO) thread. This is crucial to prevent ANR (Application Not Responding) errors. Use Kotlin Coroutines, RxJava, or other asynchronous mechanisms for this.
    ```kotlin
    // Example with Kotlin Coroutines
    // import kotlinx.coroutines.Dispatchers
    // import kotlinx.coroutines.GlobalScope
    // import kotlinx.coroutines.launch
    // import kotlinx.coroutines.withContext

    // GlobalScope.launch(Dispatchers.IO) {
    //     try {
    //         extractor.fetchPage()
    //         // Process data, possibly switching to Main dispatcher for UI updates
    //         withContext(Dispatchers.Main) {
    //             // Update your UI here
    //         }
    //     } catch (e: Exception) {
    //         // Handle exceptions
    //     }
    // }
    ```
    Always ensure proper error handling for network requests.

# Contributing

We welcome contributions to New Valve! Whether you're fixing a bug, improving documentation, or proposing a new feature, your help is appreciated. Please take a moment to review these guidelines to make the contribution process smooth for everyone.

## Reporting Bugs and Suggesting Features

*   **Found a bug?** If you encounter a bug, please open an issue on the [GitHub Issues page](https://github.com/shalva97/NewValve/issues). Try to include as much information as possible:
    *   The version of New Valve you are using.
    *   The version of NewPipeExtractor (if relevant).
    *   Steps to reproduce the bug.
    *   Expected behavior and actual behavior.
    *   Any relevant logs or stack traces.
*   **Have a feature idea?** We'd love to hear it! Please open an issue on the [GitHub Issues page](https://github.com/shalva97/NewValve/issues). Describe the feature, why it would be beneficial, and any potential implementation ideas you might have.

## Submitting Pull Requests

If you'd like to contribute code, please follow these steps:

1.  **Fork the Repository:** Create your own fork of the [New Valve repository](https://github.com/shalva97/NewValve) on GitHub.
2.  **Create a Branch:** Create a new branch in your forked repository for your changes. Choose a descriptive branch name (e.g., `fix/downloader-bug`, `feature/retry-mechanism`).
    ```bash
    git checkout -b your-branch-name
    ```
3.  **Make Your Changes:** Implement your fix or feature.
    *   **Coding Standards:** Please try to follow the existing coding style in the project (Kotlin conventions). Keep your code clean, readable, and well-commented where necessary.
    *   **Tests:** New Valve currently has basic examples in `src/test/kotlin/Examples.kt`. If you are adding a new feature or fixing a critical bug, please consider adding or updating an example to demonstrate its functionality. For more complex changes, unit tests are highly encouraged.
4.  **Commit Your Changes:** Make clear and concise commit messages.
    ```bash
    git commit -m "Fix: Describe the fix" -m "Detailed explanation of the fix if needed."
    # or
    git commit -m "Feat: Describe the new feature"
    ```
5.  **Push to Your Fork:** Push your changes to your forked repository.
    ```bash
    git push origin your-branch-name
    ```
6.  **Open a Pull Request:** Go to the original [New Valve repository](https://github.com/shalva97/NewValve) and open a pull request from your forked branch to the `master` (or `main`) branch of the New Valve repository.
    *   Provide a clear title and description for your pull request, explaining the changes you've made and why.
    *   Reference any related issues (e.g., "Closes #123").

## Code of Conduct

While New Valve does not have a formal Code of Conduct document, we expect all contributors to interact respectfully and constructively. Please be kind and considerate when discussing issues and code.

Thank you for considering contributing to New Valve!

# License

New Valve is licensed under the **GNU General Public License v3.0**.

The full text of the license can be found in the [LICENSE](LICENSE) file in this repository.

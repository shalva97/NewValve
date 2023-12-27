import io.github.shalva97.initNewPipe
import org.schabi.newpipe.extractor.ServiceList
import kotlin.test.Test

class Examples {
    @Test
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
        extractor.audioStreams.forEach {
            println("Audio: ${it.averageBitrate} - ${it.codec} - ${it.content}")
        }
        extractor.videoStreams.forEach {
            println("Video: ${it.bitrate} - ${it.content}")
        }
    }

    @Test
    fun `Get channel info and videos`() {
        initNewPipe()
        val service = ServiceList.YouTube

        val channelExtractor =
            service.getChannelExtractor("https://www.youtube.com/channel/UC2H-gXCiXWfLdf9Be8pQ6Vg")
        channelExtractor.fetchPage()

        println("Channel name: " + channelExtractor.name)
        println("Subscribers: " + channelExtractor.subscriberCount)
        println("-------------------------")

        val tabExtractor =
            service.getChannelTabExtractorFromId("UC2H-gXCiXWfLdf9Be8pQ6Vg", "videos")
        tabExtractor.fetchPage()

        var currentPage = tabExtractor.initialPage
        var counter = 0

        currentPage.items.forEach {
            counter++
            println("${it.url} - $counter - ${it.name}")
        }

//        while (currentPage.nextPage != null) {
//            currentPage = extractor.getPage(currentPage.nextPage)
//            currentPage.items.forEach { it: StreamInfoItem ->
//                counter++
//                println("${it.url} - $counter - ${it.name}")
//            }
//        }
    }
}

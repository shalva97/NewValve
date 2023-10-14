import io.github.shalva97.initNewPipe
import org.schabi.newpipe.extractor.ServiceList
import org.schabi.newpipe.extractor.stream.StreamInfoItem
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

        val extractor =
            service.getChannelExtractor("https://www.youtube.com/channel/UC2H-gXCiXWfLdf9Be8pQ6Vg")
        extractor.fetchPage()

        println("Channel name: " + extractor.name)
        println("Channel name: " + extractor.subscriberCount)

        var currentPage = extractor.initialPage
        var counter = 0

        currentPage.items.forEach { it: StreamInfoItem ->
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

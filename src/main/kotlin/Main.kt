import okhttp3.OkHttpClient
import org.schabi.newpipe.extractor.NewPipe
import org.schabi.newpipe.extractor.ServiceList
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeCommentsExtractor

fun main(args: Array<String>) {
    NewPipe.init(DownloaderImpl(OkHttpClient()))
    val service = ServiceList.YouTube
//    val video = service.getStreamExtractor("https://www.youtube.com/watch?v=da-Vy31tops")
    val comments = service.getChannelExtractor("https://www.youtube.com/channel/UC2gDt99Fit70JLsrardC2gQ")
//    video.fetchPage()
    comments.fetchPage()

    val ytExtractor = comments
    ytExtractor

    println("u")
}
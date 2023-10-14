import okhttp3.OkHttpClient
import org.schabi.newpipe.extractor.NewPipe
import org.schabi.newpipe.extractor.StreamingService

fun initNewPipe() {
    NewPipe.init(DownloaderImpl(OkHttpClient()))
}

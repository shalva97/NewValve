package io.github.shalva97

import okhttp3.OkHttpClient
import org.schabi.newpipe.extractor.NewPipe

fun initNewPipe() {
    NewPipe.init(DownloaderImpl(OkHttpClient()))
}

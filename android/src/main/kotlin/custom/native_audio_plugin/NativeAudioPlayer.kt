package custom.native_audio_plugin

import android.content.Context
import android.media.MediaPlayer
import java.io.IOException

class NativeAudioPlayer private constructor(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null
    private var isLooping = false
    private var soundAssetName: String? = null

    companion object {
        @Volatile
        private var INSTANCE: NativeAudioPlayer? = null

        fun getInstance(context: Context): NativeAudioPlayer {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: NativeAudioPlayer(context.applicationContext).also { INSTANCE = it }
            }
        }
    }

    fun play(assetName: String, loop: Boolean) {
        // If already playing the same sound, just update looping if needed
        if (mediaPlayer?.isPlaying == true && soundAssetName == assetName) {
            if (isLooping != loop) {
                mediaPlayer?.isLooping = loop
                isLooping = loop
            }
            return
        }

        stop() // Stop previous playback
        soundAssetName = assetName
        isLooping = loop

        try {
            val afd = context.assets.openFd("flutter_assets/assets/sound/$assetName")
            mediaPlayer = MediaPlayer().apply {
                setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                isLooping = loop
                setOnPreparedListener { start() }
                setOnErrorListener { _, _, _ -> true } // swallow errors
                prepareAsync()
            }
            afd.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun stop() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.reset()
            it.release()
        }
        mediaPlayer = null
        soundAssetName = null
    }

    fun isPlaying(): Boolean {
        return mediaPlayer?.isPlaying ?: false
    }

    fun dispose() {
        stop()
    }
}

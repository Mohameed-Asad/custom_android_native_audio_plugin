package custom.native_audio_plugin

import android.content.Context
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** NativeAudioPlugin */
class NativeAudioPlugin: FlutterPlugin, MethodCallHandler {
  private lateinit var channel: MethodChannel
  private var nativeAudioPlayer: NativeAudioPlayer? = null
  private lateinit var context: Context

  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    context = flutterPluginBinding.applicationContext
    // Use the singleton instead of creating a new instance
    nativeAudioPlayer = NativeAudioPlayer.getInstance(context)

    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "com.tawaf.bravo/audio")
    channel.setMethodCallHandler(this)
  }

  override fun onMethodCall(call: MethodCall, result: Result) {
    when (call.method) {
      "play" -> {
        val url = call.argument<String>("url")
        val loop = call.argument<Boolean>("loop") ?: false
        if (url != null) {
          nativeAudioPlayer?.play(url, loop)
          result.success(null)
        } else {
          result.error("INVALID_ARGUMENT", "URL cannot be null", null)
        }
      }
      "stop" -> {
        nativeAudioPlayer?.stop()
        result.success(null)
      }
      "isPlaying" -> {
        val isPlaying = nativeAudioPlayer?.isPlaying() ?: false
        result.success(isPlaying)
      }
      else -> result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    nativeAudioPlayer?.dispose()
    channel.setMethodCallHandler(null)
  }
}

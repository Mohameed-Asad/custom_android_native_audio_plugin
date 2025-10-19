
import 'native_audio_plugin_platform_interface.dart';

class NativeAudioPlugin {
  Future<String?> getPlatformVersion() {
    return NativeAudioPluginPlatform.instance.getPlatformVersion();
  }
}

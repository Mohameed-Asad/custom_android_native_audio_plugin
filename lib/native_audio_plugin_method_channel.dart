import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'native_audio_plugin_platform_interface.dart';

/// An implementation of [NativeAudioPluginPlatform] that uses method channels.
class MethodChannelNativeAudioPlugin extends NativeAudioPluginPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('native_audio_plugin');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}

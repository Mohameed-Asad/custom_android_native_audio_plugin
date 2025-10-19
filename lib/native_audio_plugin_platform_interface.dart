import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'native_audio_plugin_method_channel.dart';

abstract class NativeAudioPluginPlatform extends PlatformInterface {
  /// Constructs a NativeAudioPluginPlatform.
  NativeAudioPluginPlatform() : super(token: _token);

  static final Object _token = Object();

  static NativeAudioPluginPlatform _instance = MethodChannelNativeAudioPlugin();

  /// The default instance of [NativeAudioPluginPlatform] to use.
  ///
  /// Defaults to [MethodChannelNativeAudioPlugin].
  static NativeAudioPluginPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [NativeAudioPluginPlatform] when
  /// they register themselves.
  static set instance(NativeAudioPluginPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}

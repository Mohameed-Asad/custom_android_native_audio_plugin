import 'package:flutter_test/flutter_test.dart';
import 'package:native_audio_plugin/native_audio_plugin.dart';
import 'package:native_audio_plugin/native_audio_plugin_platform_interface.dart';
import 'package:native_audio_plugin/native_audio_plugin_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockNativeAudioPluginPlatform
    with MockPlatformInterfaceMixin
    implements NativeAudioPluginPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final NativeAudioPluginPlatform initialPlatform = NativeAudioPluginPlatform.instance;

  test('$MethodChannelNativeAudioPlugin is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelNativeAudioPlugin>());
  });

  test('getPlatformVersion', () async {
    NativeAudioPlugin nativeAudioPlugin = NativeAudioPlugin();
    MockNativeAudioPluginPlatform fakePlatform = MockNativeAudioPluginPlatform();
    NativeAudioPluginPlatform.instance = fakePlatform;

    expect(await nativeAudioPlugin.getPlatformVersion(), '42');
  });
}

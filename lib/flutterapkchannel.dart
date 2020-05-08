import 'dart:async';
import 'dart:io';
import 'package:flutter/services.dart';

class Flutterapkchannel {
  static const MethodChannel _channel =
      const MethodChannel('flutterapkchannel');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}

//获取渠道信息
class AppChannel {
  static Future<String> getChannel() async {
    if (Platform.isIOS) {
      return Future.value('AppStore');
    } else if (Platform.isAndroid) {
      final String channel =
          await Flutterapkchannel._channel.invokeMethod<String>('getChannel');
      return channel;
    }
    return Future.value('unknow');
  }
}

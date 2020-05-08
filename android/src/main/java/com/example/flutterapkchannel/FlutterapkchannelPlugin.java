package com.example.flutterapkchannel;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * FlutterapkchannelPlugin
 */
public class FlutterapkchannelPlugin implements FlutterPlugin, MethodCallHandler {
    private Context applicationContext;
    private MethodChannel methodChannel;

    public static void registerWith(Registrar registrar) {
        final FlutterapkchannelPlugin instance = new FlutterapkchannelPlugin();
        instance.onAttachedToEngine(registrar.context(), registrar.messenger());
    }

    @Override
    public void onAttachedToEngine(FlutterPluginBinding flutterPluginBinding) {
        onAttachedToEngine(
                flutterPluginBinding.getApplicationContext(), flutterPluginBinding.getFlutterEngine().getDartExecutor());
    }

    private void onAttachedToEngine(Context applicationContext, BinaryMessenger messenger) {
        this.applicationContext = applicationContext;
        methodChannel = new MethodChannel(messenger, "flutterapkchannel");
        methodChannel.setMethodCallHandler(this);
    }

    @Override
    public void onDetachedFromEngine(FlutterPluginBinding binding) {
        applicationContext = null;
        methodChannel.setMethodCallHandler(null);
        methodChannel = null;
    }

    @Override
    public void onMethodCall(MethodCall call, Result result) {
        if (call.method.equals("getPlatformVersion")) {
            result.success("Android " + android.os.Build.VERSION.RELEASE);
        } else if (call.method.equals("getChannel")) {
            try {
                Context context = applicationContext;
                String channel = "Android";
                PackageManager pm = context.getPackageManager();
                //获取渠道名使用
                ApplicationInfo appInfo = pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                channel = String.valueOf(appInfo.metaData.getString("UMENG_CHANNEL"));
                result.success(channel);
            } catch (PackageManager.NameNotFoundException ex) {
                result.error("Name not found", ex.getMessage(), null);
            }
        } else {
            result.notImplemented();
        }
    }
}

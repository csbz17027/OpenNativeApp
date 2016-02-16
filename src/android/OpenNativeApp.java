package lance.cordova.plugins;


import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by lance on 16/1/27.
 */
public class OpenNativeApp extends CordovaPlugin {
    private static final String QUERY_MAP_APP = "queryMapApp";
    private static final String OPEN_MAP_APP = "openMapApp";
    private CallbackContext callbackContext;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        this.callbackContext = callbackContext;
        if (action.equals(QUERY_MAP_APP)) {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("appNames", queryMapApp());
            callbackContext.success(jsonObj);
            return true;
        } else if (action.equals(OPEN_MAP_APP)) {
            if (null == args || args.length() == 0) {
                this.callbackContext.error("没有传递参数");
            }
            return openMapApp(args.getString(0), args.getString(1), args.getString(2), args.getString(3), args.getString(4), args.getString(5));
        }

        return super.execute(action, args, callbackContext);
    }

    private JSONArray queryMapApp() {
        final JSONArray mapAppNames = new JSONArray();
        final PackageManager packageManager = cordova.getActivity().getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (null != pinfo) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.baidu.BaiduMap")) {
                    mapAppNames.put("baidu");
                } else if (pn.equals("com.autonavi.minimap")) {
                    mapAppNames.put("amap");
                }
            }
        }
        return mapAppNames;
    }

    private boolean openMapApp(final String nowLat, final String nowLng, final String sourceLat, final String sourceLng, final String type, final String choice) {
        System.out.println("plugin native");
        cordova.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                if (choice.equals("baidu")) {
                    String action = "intent://map/direction?" +
                            "origin=latlng:" + nowLat + "," + nowLng +
                            "|name:起点&destination=latlng:" + sourceLat + "," + sourceLng +
                            "|name:终点&mode=" + type +
                            "&src=顺手赚钱#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end";
                    System.out.println(action);
                    try {
                        Intent intent = Intent.getIntent(action);
                        cordova.getActivity().startActivity(intent);
                        callbackContext.success();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                        callbackContext.error(e.toString());
                    }
                } else if (choice.equals("amap")) {
                    String data = "androidamap://navi?sourceApplication=顺手赚钱&" +
                            "lat=" + sourceLat + "&lon=" + sourceLng +
                            "&dev=0&style=0";
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(data));
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setPackage("com.autonavi.minimap");
                    cordova.getActivity().startActivity(intent);
                    callbackContext.success();
                }
            }
        });
        return true;
    }
}

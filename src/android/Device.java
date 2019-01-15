/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
*/
package org.apache.cordova.device;

import java.util.TimeZone;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.hardware.SensorManager;
import android.provider.Settings;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.squareup.seismic.ShakeDetector;

// this class will be available in Javascript to check if the device is HD od SD
class HDCheck {
    private Context activityContext;

    HDCheck(Context activityContext) {
        this.activityContext = activityContext;
    }

    @JavascriptInterface
    public boolean isHDApp() {
        return ((this.activityContext.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) >=
                Configuration.SCREENLAYOUT_SIZE_LARGE);
    }
}

public class Device extends CordovaPlugin implements ShakeDetector.Listener {
    public static final String TAG = "Device";

    public static String platform;                            // Device OS
    public static String uuid;                                // Device UUID

    private static final String ANDROID_PLATFORM = "Android";
    private static final String AMAZON_PLATFORM = "amazon-fireos";
    private static final String AMAZON_DEVICE = "Amazon";

    private static boolean isTablet = false;

    private long lastShakeTimestamp = 0;

    /**
     * Constructor.
     */
    public Device() {
    }

    /**
     * Sets the context of the Command. This can then be used to do things like
     * get file paths associated with the Activity.
     *
     * @param cordova The context of the main Activity.
     * @param webView The CordovaWebView Cordova is running in.
     */
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        Device.uuid = getUuid();

        WebView systemWebView = (WebView) webView.getView();

        // clearing the webView cache.. (we had a view problems with cached, invalid local responses)
        systemWebView.clearCache(true);
        systemWebView.getSettings().setAppCacheEnabled(false);
        systemWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        systemWebView.getSettings().setAppCacheMaxSize(0);
        systemWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        HDCheck hdCheck = new HDCheck(this.cordova.getActivity().getApplicationContext());

        isTablet = hdCheck.isHDApp();
        if (!isTablet) {
            this.cordova.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        // "Injects" the NativeHDCheck to Javascript and allows it to natively check if the device is HD or SD via its methode ".isHDApp()"
        systemWebView.addJavascriptInterface(hdCheck, "NativeHDCheck");

        // shake recognition
        SensorManager sensorManager = (SensorManager) cordova.getActivity().getSystemService(Context.SENSOR_SERVICE);
        ShakeDetector sd = new ShakeDetector(this);
        sd.setSensitivity(ShakeDetector.SENSITIVITY_HARD);
        sd.start(sensorManager);
    }

    /**
     * Executes the request and returns PluginResult.
     *
     * @param action            The action to execute.
     * @param args              JSONArry of arguments for the plugin.
     * @param callbackContext   The callback id used when calling back into JavaScript.
     * @return                  True if the action was valid, false if not.
     */
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("getDeviceInfo".equals(action)) {
            JSONObject r = new JSONObject();
            r.put("uuid", Device.uuid);
            r.put("version", this.getOSVersion());
            r.put("platform", ANDROID_PLATFORM);
            r.put("model", this.getModel());
            r.put("manufacturer", this.getManufacturer());
            r.put("isVirtual", this.isVirtual());
            r.put("serial", this.getSerialNumber());

            r.put("appname", getAppName());
            r.put("appversion", getAppVersion());

            r.put("isTablet", isTablet);
            r.put("has3DTouch", false);

            JSONObject accessibility = new JSONObject();
            accessibility.put("textSizeAdjustment", 200);
            r.put("accessibility", accessibility);

            callbackContext.success(r);
        }
        else {
            return false;
        }
        return true;
    }

    //--------------------------------------------------------------------------
    // LOCAL METHODS
    //--------------------------------------------------------------------------

    /**
     * Get the OS name.
     *
     * @return
     */
    public String getPlatform() {
        String platform;
        if (isAmazonDevice()) {
            platform = AMAZON_PLATFORM;
        } else {
            platform = ANDROID_PLATFORM;
        }
        return platform;
    }

    /**
     * Get the device's Universally Unique Identifier (UUID).
     *
     * @return
     */
    public String getUuid() {
        String uuid = Settings.Secure.getString(this.cordova.getActivity().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        return uuid;
    }

    public String getModel() {
        String model = android.os.Build.MODEL;
        return model;
    }

    public String getProductName() {
        String productname = android.os.Build.PRODUCT;
        return productname;
    }

    public String getManufacturer() {
        String manufacturer = android.os.Build.MANUFACTURER;
        return manufacturer;
    }

    public String getSerialNumber() {
        String serial = android.os.Build.SERIAL;
        return serial;
    }

    /**
     * Get the OS version.
     *
     * @return
     */
    public String getOSVersion() {
        String osversion = android.os.Build.VERSION.RELEASE;
        return osversion;
    }

    public String getSDKVersion() {
        @SuppressWarnings("deprecation")
        String sdkversion = android.os.Build.VERSION.SDK;
        return sdkversion;
    }

    public String getTimeZoneID() {
        TimeZone tz = TimeZone.getDefault();
        return (tz.getID());
    }

    /**
     * Function to check if the device is manufactured by Amazon
     *
     * @return
     */
    public boolean isAmazonDevice() {
        if (android.os.Build.MANUFACTURER.equals(AMAZON_DEVICE)) {
            return true;
        }
        return false;
    }

    public boolean isVirtual() {
        return android.os.Build.FINGERPRINT.contains("generic") ||
                android.os.Build.PRODUCT.contains("sdk");
    }

    @Override
    public void hearShake() {
        long currentTimestamp = System.currentTimeMillis();
        if (currentTimestamp - lastShakeTimestamp > 1000) {
            WebView systemWebView = (WebView) webView.getView();
            String javaScript = "(typeof window.onShakeEvent === \"function\") && window.onShakeEvent();";
            javaScript = "javascript:" + javaScript;
            Log.i(TAG, "using loadUrl:" + javaScript);
            systemWebView.loadUrl(javaScript); // ATTENTION: using 'evaluateJavascript' hides the Cursor/Caret in input fields! ID: 67913872

            lastShakeTimestamp = currentTimestamp;
        }
    }



    private String getAppVersion() {
        String packageName = getAppName();
        String versionName = null;
        try {
            PackageManager packageManager = cordova.getActivity().getPackageManager();
            if (packageManager != null) {
                versionName = packageManager.getPackageInfo(packageName, 0).versionName;
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(this.toString(), e.getMessage());
            versionName = "0";
        }
        return versionName;
    }

    private String getAppName() {
        return cordova.getActivity().getPackageName();
    }
}

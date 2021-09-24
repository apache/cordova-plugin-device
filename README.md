---
title: Device
description: Get device information.
---
<!--
# license: Licensed to the Apache Software Foundation (ASF) under one
#         or more contributor license agreements.  See the NOTICE file
#         distributed with this work for additional information
#         regarding copyright ownership.  The ASF licenses this file
#         to you under the Apache License, Version 2.0 (the
#         "License"); you may not use this file except in compliance
#         with the License.  You may obtain a copy of the License at
#
#           http://www.apache.org/licenses/LICENSE-2.0
#
#         Unless required by applicable law or agreed to in writing,
#         software distributed under the License is distributed on an
#         "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
#         KIND, either express or implied.  See the License for the
#         specific language governing permissions and limitations
#         under the License.
-->

# cordova-plugin-device

[![Android Testsuite](https://github.com/apache/cordova-plugin-device/actions/workflows/android.yml/badge.svg)](https://github.com/apache/cordova-plugin-device/actions/workflows/android.yml) [![Chrome Testsuite](https://github.com/apache/cordova-plugin-device/actions/workflows/chrome.yml/badge.svg)](https://github.com/apache/cordova-plugin-device/actions/workflows/chrome.yml) [![iOS Testsuite](https://github.com/apache/cordova-plugin-device/actions/workflows/ios.yml/badge.svg)](https://github.com/apache/cordova-plugin-device/actions/workflows/ios.yml) [![Lint Test](https://github.com/apache/cordova-plugin-device/actions/workflows/lint.yml/badge.svg)](https://github.com/apache/cordova-plugin-device/actions/workflows/lint.yml)

This plugin defines a global `device` object, which describes the device's hardware and software.
Although the object is in the global scope, it is not available until after the `deviceready` event.

```js
document.addEventListener("deviceready", onDeviceReady, false);
function onDeviceReady() {
    console.log(device.cordova);
}
```

## Installation

    cordova plugin add cordova-plugin-device

## Properties

- device.cordova
- device.model
- device.platform
- device.uuid
- device.version
- device.manufacturer
- device.isVirtual
- device.serial

## device.cordova

Get the version of Cordova running on the device.

### Supported Platforms

- Android
- Browser
- iOS
- Windows
- OSX

## device.model

The `device.model` returns the name of the device's model or
product. The value is set by the device manufacturer and may be
different across versions of the same product.

### Supported Platforms

- Android
- Browser
- iOS
- Windows
- OSX

### Quick Example

```js
// Android:    Nexus One       returns "Passion" (Nexus One code name)
//             Motorola Droid  returns "voles"
// BlackBerry: Torch 9800      returns "9800"
// Browser:    Google Chrome   returns "Chrome"
//             Safari          returns "Safari"
// iOS:     for the iPad Mini, returns iPad2,5; iPhone 5 is iPhone 5,1. See http://theiphonewiki.com/wiki/index.php?title=Models
// OSX:                        returns "x86_64"
//
var model = device.model;
```

### Android Quirks

- Gets the [product name](http://developer.android.com/reference/android/os/Build.html#PRODUCT) instead of the [model name](http://developer.android.com/reference/android/os/Build.html#MODEL), which is often the production code name. For example, the Nexus One returns `Passion`, and Motorola Droid returns `voles`.

## device.platform

Get the device's operating system name.

```js
var string = device.platform;
```
### Supported Platforms

- Android
- Browser
- iOS
- Windows
- OSX

### Quick Example

```js
// Depending on the device, a few examples are:
//   - "Android"
//   - "BlackBerry 10"
//   - "browser"
//   - "iOS"
//   - "WinCE"
//   - "Tizen"
//   - "Mac OS X"
var devicePlatform = device.platform;
```

## device.uuid

Get the device's Universally Unique Identifier ([UUID](http://en.wikipedia.org/wiki/Universally_Unique_Identifier)).

```js
var string = device.uuid;
```

### Description

The details of how a UUID is generated are determined by the device manufacturer and are specific to the device's platform or model.

### Supported Platforms

- Android
- iOS
- Windows
- OSX

### Quick Example

```js
// Android: Returns a random 64-bit integer (as a string, again!)
//
// BlackBerry: Returns the PIN number of the device
//             This is a nine-digit unique integer (as a string, though!)
//
// iPhone: (Paraphrased from the UIDevice Class documentation)
//         Returns the [UIDevice identifierForVendor] UUID which is unique and the same for all apps installed by the same vendor. However the UUID can be different if the user deletes all apps from the vendor and then reinstalls it.
// Windows Phone 7 : Returns a hash of device+current user,
// if the user is not defined, a guid is generated and will persist until the app is uninstalled
// Tizen: returns the device IMEI (International Mobile Equipment Identity or IMEI is a number
// unique to every GSM and UMTS mobile phone.
var deviceID = device.uuid;
```

### Android Quirk

The `uuid` on Android is a 64-bit integer (expressed as a hexadecimal string). The behaviour of this `uuid` is different on two different OS versions-

**For < Android 8.0 (API level 26)**

In versions of the platform lower than Android 8.0, the `uuid` is randomly generated when the user first sets up the device and should remain constant for the lifetime of the user's device.

**For Android 8.0 or higher**

The above behaviour was changed in Android 8.0. Read it in detail [here](https://developer.android.com/about/versions/oreo/android-8.0-changes#privacy-all).

On Android 8.0 and higher versions, the `uuid` will be unique to each combination of app-signing key, user, and device. The value is scoped by signing key and user. The value may change if a factory reset is performed on the device or if an APK signing key changes.

Read more here https://developer.android.com/reference/android/provider/Settings.Secure#ANDROID_ID.

### iOS Quirk

The `uuid` on iOS uses the identifierForVendor property. It is unique to the device across the same vendor, but will be different for different vendors and will change if all apps from the vendor are deleted and then reinstalled.
Refer [here](https://developer.apple.com/library/ios/documentation/UIKit/Reference/UIDevice_Class/#//apple_ref/occ/instp/UIDevice/identifierForVendor) for details.
The UUID will be the same if app is restored from a backup or iCloud as it is saved in preferences. Users using older versions of this plugin will still receive the same previous UUID generated by another means as it will be retrieved from preferences.

### OSX Quirk

The `uuid` on OSX is generated automatically if it does not exist yet and is stored in the `standardUserDefaults` in the `CDVUUID` property.

## device.version

Get the operating system version.

    var string = device.version;

### Supported Platforms

- Android 2.1+
- Browser
- iOS
- Windows
- OSX

### Quick Example

```js
// Android:    Froyo OS would return "2.2"
//             Eclair OS would return "2.1", "2.0.1", or "2.0"
//             Version can also return update level "2.1-update1"
//
// BlackBerry: Torch 9800 using OS 6.0 would return "6.0.0.600"
//
// Browser:    Returns version number for the browser
//
// iPhone:     iOS 3.2 returns "3.2"
//
// Windows Phone 7: returns current OS version number, ex. on Mango returns 7.10.7720
// Windows 8: return the current OS version, ex on Windows 8.1 returns 6.3.9600.16384
// Tizen: returns "TIZEN_20120425_2"
// OSX:        El Capitan would return "10.11.2"
//
var deviceVersion = device.version;
```

## device.manufacturer

Get the device's manufacturer.

    var string = device.manufacturer;

### Supported Platforms

- Android
- iOS
- Windows

### Quick Example

```js
// Android:    Motorola XT1032 would return "motorola"
// BlackBerry: returns "BlackBerry"
// iPhone:     returns "Apple"
//
var deviceManufacturer = device.manufacturer;
```

## device.isVirtual

whether the device is running on a simulator.

```js
var isSim = device.isVirtual;
```

### Supported Platforms

- Android 2.1+
- Browser
- iOS
- Windows
- OSX

### OSX and Browser Quirk

The `isVirtual` property on OS X and Browser always returns false.

## device.serial

Get the device hardware serial number ([SERIAL](http://developer.android.com/reference/android/os/Build.html#SERIAL)).

```js
var string = device.serial;
```

### Supported Platforms

- Android
- OSX

### Android Quirk

As of Android 9, the underlying native API that powered the `uuid` property is deprecated and will always return `UNKNOWN` without proper permissions. Cordova have never implemented handling the required permissions. As of Android 10, **all** non-resettable device identifiers are no longer readable by normal applications and will always return `UNKNOWN`. More information can be [read here](https://developer.android.com/about/versions/10/privacy/changes#non-resettable-device-ids).

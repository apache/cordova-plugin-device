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

|AppVeyor|Travis CI|
|:-:|:-:|
|[![Build status](https://ci.appveyor.com/api/projects/status/github/apache/cordova-plugin-device?branch=master)](https://ci.appveyor.com/project/ApacheSoftwareFoundation/cordova-plugin-device)|[![Build Status](https://travis-ci.org/apache/cordova-plugin-device.svg?branch=master)](https://travis-ci.org/apache/cordova-plugin-device)|

# cordova-plugin-device

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
- OS X

## device.model

The `device.model` returns the name of the device's model or
product. The value is set by the device manufacturer and may be
different across versions of the same product.

### Supported Platforms

- Android
- Browser
- iOS
- Windows
- OS X

### Quick Example

```js
// Android:    Nexus One       returns "Passion" (Nexus One code name)
//             Motorola Droid  returns "voles"
// Browser:    Google Chrome   returns "Chrome"
//             Safari          returns "Safari"
// iOS:     for the iPad Mini, returns iPad2,5; iPhone 5 is iPhone 5,1. See https://www.theiphonewiki.com/wiki/Models
// OS X:                        returns "x86_64"
//
var model = device.model;
```

### Android Quirks

- Gets the [product name](https://developer.android.com/reference/android/os/Build.html#PRODUCT) instead of the [model name](https://developer.android.com/reference/android/os/Build.html#MODEL), which is often the production code name. For example, the Nexus One returns `Passion`, and Motorola Droid returns `voles`.

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
- OS X

### Quick Example

```js
// Depending on the device, a few examples are:
//   - "Android"
//   - "browser"
//   - "iOS"
//   - "WinCE"
//   - "Mac OS X"
//
var devicePlatform = device.platform;
```

## device.uuid

Get the device's Universally Unique Identifier ([UUID](https://en.wikipedia.org/wiki/Universally_unique_identifier)).

```js
var string = device.uuid;
```

### Description

The details of how a UUID is generated are determined by the device manufacturer and are specific to the device's platform or model.

### Supported Platforms

- Android
- iOS
- Windows
- OS X

### Quick Example

```js
// Android: Returns a random 64-bit integer (as a string, again!)
//          The integer is generated on the device's first boot
//
// iOS: (Paraphrased from the UIDevice Class documentation)
//         Returns the [UIDevice identifierForVendor] UUID which is unique and the same for all apps installed by the same vendor. However the UUID can be different if the user deletes all apps from the vendor and then reinstalls it.
//
// Windows Phone 7 : Returns a hash of device+current user,
// if the user is not defined, a guid is generated and will persist until the app is uninstalled
//
var deviceID = device.uuid;
```

### iOS Quirk

The `uuid` on iOS uses the identifierForVendor property. It is unique to the device across the same vendor, but will be different for different vendors and will change if all apps from the vendor are deleted and then reinstalled.
Refer [here](https://developer.apple.com/documentation/uikit/uidevice/1620059-identifierforvendor) for details.
The UUID will be the same if app is restored from a backup or iCloud as it is saved in preferences. Users using older versions of this plugin will still receive the same previous UUID generated by another means as it will be retrieved from preferences.

### OS X Quirk

The `uuid` on OS X is generated automatically if it does not exist yet and is stored in the `standardUserDefaults` in the `CDVUUID` property.

## device.version

Get the operating system version.

    var string = device.version;

### Supported Platforms

- Android
- Browser
- iOS
- Windows
- OS X

### Quick Example

```js
// Android:    Froyo OS would return "2.2"
//             Eclair OS would return "2.1", "2.0.1", or "2.0"
//             Version can also return update level "2.1-update1"
//
// Browser:    Returns version number for the browser
//
// iOS:     iOS 3.2 returns "3.2"
//
// Windows Phone 7: returns current OS version number, ex. on Mango returns 7.10.7720
// Windows 8: return the current OS version, ex on Windows 8.1 returns 6.3.9600.16384
//
// OS X:        El Capitan would return "10.11.2"
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
// iOS:     returns "Apple"
//
var deviceManufacturer = device.manufacturer;
```

## device.isVirtual

whether the device is running on a simulator.

```js
var isSim = device.isVirtual;
```

### Supported Platforms

- Android
- Browser
- iOS
- Windows
- OS X

### OS X and Browser Quirk

The `isVirtual` property on OS X and Browser always returns false.

## device.serial

Get the device hardware serial number ([SERIAL](https://developer.android.com/reference/android/os/Build.html#SERIAL)).

```js
var string = device.serial;
```

### Supported Platforms

- Android
- OS X

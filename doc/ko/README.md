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

|Android|iOS| Windows 8.1 Store | Windows 8.1 Phone | Windows 10 Store | Travis CI |
|:-:|:-:|:-:|:-:|:-:|:-:|
|[![Build Status](http://cordova-ci.cloudapp.net:8080/buildStatus/icon?job=cordova-periodic-build/PLATFORM=android,PLUGIN=cordova-plugin-device)](http://cordova-ci.cloudapp.net:8080/job/cordova-periodic-build/PLATFORM=android,PLUGIN=cordova-plugin-device/)|[![Build Status](http://cordova-ci.cloudapp.net:8080/buildStatus/icon?job=cordova-periodic-build/PLATFORM=ios,PLUGIN=cordova-plugin-device)](http://cordova-ci.cloudapp.net:8080/job/cordova-periodic-build/PLATFORM=ios,PLUGIN=cordova-plugin-device/)|[![Build Status](http://cordova-ci.cloudapp.net:8080/buildStatus/icon?job=cordova-periodic-build/PLATFORM=windows-8.1-store,PLUGIN=cordova-plugin-device)](http://cordova-ci.cloudapp.net:8080/job/cordova-periodic-build/PLATFORM=windows-8.1-store,PLUGIN=cordova-plugin-device/)|[![Build Status](http://cordova-ci.cloudapp.net:8080/buildStatus/icon?job=cordova-periodic-build/PLATFORM=windows-8.1-phone,PLUGIN=cordova-plugin-device)](http://cordova-ci.cloudapp.net:8080/job/cordova-periodic-build/PLATFORM=windows-8.1-phone,PLUGIN=cordova-plugin-device/)|[![Build Status](http://cordova-ci.cloudapp.net:8080/buildStatus/icon?job=cordova-periodic-build/PLATFORM=windows-10-store,PLUGIN=cordova-plugin-device)](http://cordova-ci.cloudapp.net:8080/job/cordova-periodic-build/PLATFORM=windows-10-store,PLUGIN=cordova-plugin-device/)|[![Build Status](https://travis-ci.org/apache/cordova-plugin-device.svg?branch=master)](https://travis-ci.org/apache/cordova-plugin-device)|

# cordova-plugin-device


이 플러그인은 전역 `device` 개체를 정의합니다, 이 개체는 디바이스의 하드웨어 및 소프트웨어에 설명 합니다.
개체는 전역 범위에서 사용할 수 있지만, `deviceready` 이벤트 후에 사용할 수 있습니다.

```js
document.addEventListener("deviceready", onDeviceReady, false);
function onDeviceReady() {
    console.log(device.cordova);
}
```

이 플러그인에 대한 이슈들은 여기에 보고해주세요. [Apache Cordova issue tracker](https://issues.apache.org/jira/issues/?jql=project%20%3D%20CB%20AND%20status%20in%20%28Open%2C%20%22In%20Progress%22%2C%20Reopened%29%20AND%20resolution%20%3D%20Unresolved%20AND%20component%20%3D%20%22Plugin%20Device%22%20ORDER%20BY%20priority%20DESC%2C%20summary%20ASC%2C%20updatedDate%20DESC)


## 설치

    cordova plugin add cordova-plugin-device
    

## 속성

- device.cordova
- device.model
- device.platform
- device.uuid
- device.version
- device.manufacturer
- device.isVirtual
- device.serial

## device.cordova

기기에서 실행되고 있는 코르도바의 버전을 얻을 수 있습니다.

### 지원 되는 플랫폼

- 아마존 Fire 운영 체제
- 안드로이드
- 블랙베리 10
- 브라우저
- Firefox 운영 체제
- iOS
- Tizen
- Windows Phone 7과 8
- 윈도우 8

## device.model

`device.model` 의 모델 또는 제품의 이름을 반환 합니다.
값은 장치 제조업체에서 설정 되고 동일 제품이라도 버전은 다를 수 있습니다.

### 지원 되는 플랫폼

- 안드로이드
- 블랙베리 10
- 브라우저
- iOS
- Tizen
- Windows Phone 7과 8
- 윈도우 8

### 빠른 예제

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

### 안드로이드 특징

- 어떤 것은 종종 프로덕션 코드 이름 대신 [제품 모델 이름][1], [제품 이름][2] 을 가져옵니다.
예를 들어 Nexus One은 `Passion`을 반환하고, 모토로라 Droid는 `voles` 를 반환 합니다.

 [1]: http://developer.android.com/reference/android/os/Build.html#MODEL
 [2]: http://developer.android.com/reference/android/os/Build.html#PRODUCT

### Tizen 특징

- 공급 업체에 의해 할당된 디바이스 모델을 반환 합니다. 예를 들면, `TIZEN`

### Windows Phone 7, 8 특징

- 제조업체에서 지정하는 장치 모델을 반환 합니다. 예를 들면, 삼성 포커스는 `SGH-i917`를 반환 합니다.

## device.platform

기기의 운영체제 이름을 얻어옵니다.
```js
var string = device.platform;
```  

### 지원 되는 플랫폼

- 안드로이드
- 블랙베리 10
- Browser
- Firefox 운영 체제
- iOS
- Tizen
- Windows Phone 7과 8
- 윈도우 8

### 빠른 예제
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

### Windows Phone 7 특징

Windows Phone 7 장치를 `WinCE` 플랫폼으로 알려줍니다.

### Windows Phone 8 특징

Windows Phone 8 장치 `Win32NT` 플랫폼으로 알려줍니다.

## device.uuid

기기의 고유 식별자를 얻습니다. ([UUID][3]).

 [3]: http://en.wikipedia.org/wiki/Universally_Unique_Identifier

```js
var string = device.uuid;
```

### 설명

UUID 생성 방법의 자세한 내용은 장치 제조업체에 의해 결정 됩니다.
기기의 플랫폼이나 모델에 따라 특정됩니다.

### 지원 되는 플랫폼

- 안드로이드
- 블랙베리 10
- iOS
- Tizen
- Windows Phone 7과 8
- 윈도우 8

### 빠른 예제

```js
// Android: Returns a random 64-bit integer (as a string, again!)
//          The integer is generated on the device's first boot
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
    

### iOS 특징

iOS의`uuid`는 identifierForVendor 속성을 사용합니다. 
이 같은 공급 업체에서 장치에는 고유하지만 서로 다른 벤더에서는 다를 수 있습니다.
공급 업체의 모든 앱을 삭제하고 다시 설치하는 경우 변경됩니다.
자세한 내용은 [여기](https://developer.apple.com/library/ios/documentation/UIKit/Reference/UIDevice_Class/#//apple_ref/occ/instp/UIDevice/identifierForVendor)를 참조하십시오.

이 환경 설정에 저장 될 때 응용 프로그램이 백업 또는 아이 클라우드에서 복원되는 경우 UUID는 동일합니다.
이 환경 설정에서 검색되는 바와 같이,
이 플러그인의 이전 버전을 사용하는 사용자는 여전히 다른 수단에 의해 생성된 같은 이전 UUID를 받게 됩니다.

### OSX 특징

OSX에`uuid`는 아직 존재하지 않는 경우 자동으로 생성되고 
`CDVUUID` 속성에 `standardUserDefaults`에 저장된다.

### Windows Phone 7, 8 특징

윈도우 폰 7에 대한`uuid`는 권한이 필요합니다
`ID_CAP_IDENTITY_DEVICE`. Microsoft는 이 속성을 곧 deprecate 할 것입니다.
이 기능을 사용할 수 없는 경우, 애플리케이션은 기기에 설치시 일정 기간 동안 지속되는 GUID를 생성합니다. 

## device.version

기기의 운영체제 버전을 얻습니다.

```js
var string = device.version;
```

### 지원 되는 플랫폼

- 안드로이드 2.1 +
- 블랙베리 10
- 브라우저
- iOS
- Tizen
- Windows Phone 7과 8
- 윈도우즈 8
- OSX

### 빠른 예제

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
// Tizen: returns "TIZEN_20120425_2"
// OSX:        El Capitan would return "10.11.2"
//
var deviceVersion = device.version;
```

## device.manufacturer

기기의 제조업체 정보를 얻습니다.

```js
var string = device.manufacturer;
```

### 지원되는 플랫폼

- 안드로이드
- BlackBerry 10
- iOS
- Windows Phone 7 and 8
- Windows

### 빠른 예제

```js
// Android:    Motorola XT1032 would return "motorola"
// BlackBerry: returns "BlackBerry"
// iPhone:     returns "Apple"
//
var deviceManufacturer = device.manufacturer;
```

## device.isVirtual

기기의 시뮬레이터 여부를 얻습니다.

```js
var isSim = device.isVirtual;
```

### 지원되는 플랫폼

- 안드로이드 2.1+
- iOS
- Windows Phone 8
- Windows
- OSX

### OSX 특징

 `isVirtual` 은 OSX에서 항상 false를 반환합니다.

## device.serial

하드웨어의 시리얼 값을 얻습니다 ([SERIAL](http://developer.android.com/reference/android/os/Build.html#SERIAL)).

```js
var string = device.serial;
```

### Supported Platforms

- 안드로이드
- OSX


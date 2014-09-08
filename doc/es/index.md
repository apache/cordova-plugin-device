<!---
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
-->

# org.apache.cordova.device

Este plugin define un objeto global `device` que describe el hardware y software del dispositivo. Aunque el objeto es global, no está disponible hasta después del evento `deviceready`.

    document.addEventListener("deviceready", onDeviceReady, false);
    function onDeviceReady() {
        console.log(device.cordova);
    }
    

## Instalación

    cordova plugin add org.apache.cordova.device
    

## Propiedades

*   device.cordova
*   device.model
*   device.name
*   device.platform
*   device.uuid
*   device.version

## device.cordova

Obtiener la versión de Córdoba en el dispositivo.

### Plataformas soportadas

*   Amazon fuego OS
*   Android
*   BlackBerry 10
*   Firefox OS
*   iOS
*   Tizen
*   Windows Phone 7 y 8
*   Windows 8

## device.model

`device.model` devuelve el nombre del producto o modelo del dispositivo. El valor es fijado por el fabricante del dispositivo y puede variar entre versiones del mismo producto.

### Plataformas soportadas

*   Android
*   BlackBerry 10
*   iOS
*   Tizen
*   Windows Phone 7 y 8
*   Windows 8

### Ejemplo rápido

    // Android:    Nexus One       returns "Passion" (Nexus One code name)
    //             Motorola Droid  returns "voles"
    // BlackBerry: Torch 9800      returns "9800"
    // iOS:     for the iPad Mini, returns iPad2,5; iPhone 5 is iPhone 5,1. See http://theiphonewiki.com/wiki/index.php?title=Models
    //
    var model = device.model;
    

### Rarezas Android

*   Obtiene el [nombre del producto][1] en lugar del [nombre del modelo][2], que es a menudo el nombre de código de producción. Por ejemplo, el Nexus One devuelve `Passion` , y Motorola Droid devuelve`voles`.

 [1]: http://developer.android.com/reference/android/os/Build.html#PRODUCT
 [2]: http://developer.android.com/reference/android/os/Build.html#MODEL

### Rarezas Tizen

*   Devuelve el modelo de dispositivo asignado por el proveedor, por ejemplo,`TIZEN`

### Windows Phone 7 y 8 rarezas

*   Devuelve el modelo de dispositivo especificado por el fabricante. Por ejemplo, devuelve el Samsung Focus`SGH-i917`.

## device.name

**ADVERTENCIA**: `device.name` está obsoleto desde la versión 2.3.0. Usar `device.model` en su lugar.

## device.platform

Obtener el nombre del sistema operativo del dispositivo.

    var string = device.platform;
    

### Plataformas soportadas

*   Android
*   BlackBerry 10
*   Firefox OS
*   iOS
*   Tizen
*   Windows Phone 7 y 8
*   Windows 8

### Ejemplo rápido

    // Depending on the device, a few examples are:
    //   - "Android"
    //   - "BlackBerry 10"
    //   - "iOS"
    //   - "WinCE"
    //   - "Tizen"
    var devicePlatform = device.platform;
    

### Windows Phone 7 rarezas

Dispositivos Windows Phone 7 informe de la plataforma`WinCE`.

### Windows Phone 8 rarezas

Dispositivos Windows Phone 8 Informe la plataforma como`Win32NT`.

## device.uuid

Obtener identificador único universal del dispositivo ([UUID][3]).

 [3]: http://en.wikipedia.org/wiki/Universally_Unique_Identifier

    var string = device.uuid;
    

### Descripción

Los detalles de cómo se genera un UUID son determinados por el fabricante del dispositivo y son específicos de la plataforma del dispositivo o modelo.

### Plataformas soportadas

*   Android
*   BlackBerry 10
*   iOS
*   Tizen
*   Windows Phone 7 y 8
*   Windows 8

### Ejemplo rápido

    // Android: Returns a random 64-bit integer (as a string, again!)
    //          The integer is generated on the device's first boot
    //
    // BlackBerry: Returns the PIN number of the device
    //             This is a nine-digit unique integer (as a string, though!)
    //
    // iPhone: (Paraphrased from the UIDevice Class documentation)
    //         Returns a string of hash values created from multiple hardware identifies.
    / / Está garantizado para ser único para cada dispositivo y no puede ser atado / / a la cuenta de usuario.
    / / Windows Phone 7: devuelve un hash de dispositivo + usuario actual, / / si el usuario no está definido, un guid se genera y persistirá hasta que se desinstala la aplicación / / Tizen: devuelve el dispositivo IMEI (identidad de equipo móvil internacional o IMEI es un número / / único para cada teléfono móvil GSM y UMTS.
    var deviceID = device.uuid;
    

### iOS chanfle

El `uuid` en iOS no es exclusivo de un dispositivo, varía para cada aplicación y para cada instalación de la aplicación. Cambia si se borra y se vuelve a instalar la aplicación, y posiblemente también cuándo se actualiza iOS, o incluso cuando se mejorara la versión de la aplicación (evidente en iOS 5.1). El `uuid` no es un valor confiable.

### Windows Phone 7 y 8 rarezas

El `uuid` para Windows Phone 7 requiere el permiso `ID_CAP_IDENTITY_DEVICE`. Microsoft probablemente pronto descartará esta propiedad. Si la capacidad no está disponible, la aplicación genera un guid persistente que se mantiene durante la duración de la instalación de la aplicación en el dispositivo.

## device.version

Obtiene la versión del sistema operativo.

    var string = device.version;
    

### Plataformas soportadas

*   Android 2.1 +
*   BlackBerry 10
*   iOS
*   Tizen
*   Windows Phone 7 y 8
*   Windows 8

### Ejemplo rápido

    / / Android: Froyo OS volvería "2.2" / / Eclair OS volvería "2.1", "2.0.1" o "2.0" / / versión puede también devolver actualizar nivel "2.1-update1" / / / / BlackBerry: Torch 9800 OS 6.0 usando volvería "6.0.0.600" / / / / iPhone: iOS 3.2 devuelve "3.2" / / / / Windows Phone 7: devuelve el número de versión de sistema operativo actual, ex. on Mango returns 7.10.7720
    // Tizen: returns "TIZEN_20120425_2"
    var deviceVersion = device.version;

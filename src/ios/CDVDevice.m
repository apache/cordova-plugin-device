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

#include <sys/types.h>
#include <sys/sysctl.h>
#include "TargetConditionals.h"

#import <Cordova/CDV.h>
#import "CDVDevice.h"

#import "Macros.h"
#import <WebKit/WebKit.h>

@implementation UIDevice (ModelVersion)

- (NSString*)modelVersion
{
    size_t size;

    sysctlbyname("hw.machine", NULL, &size, NULL, 0);
    char* machine = malloc(size);
    sysctlbyname("hw.machine", machine, &size, NULL, 0);
    NSString* platform = [NSString stringWithUTF8String:machine];
    free(machine);

    return platform;
}

@end

@interface CDVDevice () {}
@end

@implementation CDVDevice

- (NSString*)uniqueAppInstanceIdentifier:(UIDevice*)device
{
    NSUserDefaults* userDefaults = [NSUserDefaults standardUserDefaults];
    static NSString* UUID_KEY = @"CDVUUID";
    
    // Check user defaults first to maintain backwards compaitibility with previous versions
    // which didn't user identifierForVendor
    NSString* app_uuid = [userDefaults stringForKey:UUID_KEY];
    if (app_uuid == nil) {
        /*if ([device respondsToSelector:@selector(identifierForVendor)]) {
            app_uuid = [[device identifierForVendor] UUIDString];
        } else {*/
            CFUUIDRef uuid = CFUUIDCreate(NULL);
            app_uuid = (__bridge_transfer NSString *)CFUUIDCreateString(NULL, uuid);
            CFRelease(uuid);
        //}

        [userDefaults setObject:app_uuid forKey:UUID_KEY];
        [userDefaults synchronize];
    }
    
    return app_uuid;
}

- (void)getDeviceInfo:(CDVInvokedUrlCommand*)command
{
    NSDictionary* deviceProperties = [self deviceProperties];
    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:deviceProperties];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (NSDictionary*)deviceProperties
{
    UIDevice* device = [UIDevice currentDevice];
    
    NSString *versionString = [NSString stringWithFormat:@"%@ (%@)", [[NSBundle mainBundle] objectForInfoDictionaryKey:@"CFBundleShortVersionString"], [[NSBundle mainBundle] objectForInfoDictionaryKey:@"CFBundleVersion"]];
    NSString *nameString = [[NSBundle mainBundle] objectForInfoDictionaryKey:@"CFBundleName"];
    
    NSNumber *adjustment = [NSNumber numberWithInt:[self calculateFontSizeAdjustment]];
    NSDictionary *accessability = [NSDictionary dictionaryWithObject:adjustment forKey:@"textSizeAdjustment"];

    return @{
             @"manufacturer": @"Apple",
             @"model": [device modelVersion],
             @"platform": @"iOS",
             @"version": [device systemVersion],
             @"uuid": [self uniqueAppInstanceIdentifier:device],
             @"cordova": [[self class] cordovaVersion],
             @"isVirtual": @([self isVirtual]),
             
             @"appversion": versionString,
             @"appname": nameString,
             
             @"isTablet": @([self isTablet]),
             @"has3DTouch": @([self has3DTouch]),
             
             @"accessbilitity": accessability
             };
}

+ (NSString*)cordovaVersion
{
    return CDV_VERSION;
}

- (BOOL)isVirtual
{
    #if TARGET_OS_SIMULATOR
        return true;
    #elif TARGET_IPHONE_SIMULATOR
        return true;
    #else
        return false;
    #endif
}



# pragma mark - Custom

-(void)pluginInitialize {
    [super pluginInitialize];
    
    // clearing the webView cache.. (we had a view problems with cached, invalid local responses)
    [[NSURLCache sharedURLCache] removeAllCachedResponses];
    
    WKWebView *webview = (WKWebView *)[self webView];
    [webview evaluateJavaScript:[NSString stringWithFormat:@"window.HD_APP = %@;", [self isTablet] ? @"true" : @"false"] completionHandler:nil];
    
    UIScreenEdgePanGestureRecognizer *screenEdgeRecognizer = [[UIScreenEdgePanGestureRecognizer alloc] initWithTarget:self action:@selector(navigateBack:)];
    screenEdgeRecognizer.edges = UIRectEdgeLeft;
    [webview addGestureRecognizer:screenEdgeRecognizer];
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(fontSizeDidChange) name:UIContentSizeCategoryDidChangeNotification object:nil];
}

-(BOOL)isTablet {
    return UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad;
}

-(BOOL)has3DTouch {
    if (SYSTEM_VERSION_GREATER_THAN_OR_EQUAL_TO(@"9.0")) {
        UIWindow *mainWindow = [[UIApplication sharedApplication] delegate].window;
        return [mainWindow traitCollection].forceTouchCapability == UIForceTouchCapabilityAvailable;
    } else {
        return false;
    }
}


-(void)navigateBack:(UIScreenEdgePanGestureRecognizer *)screenEdgeRecognizer {
    UIGestureRecognizerState state = screenEdgeRecognizer.state;
    if (state == UIGestureRecognizerStateRecognized) {
        WKWebView *webview = (WKWebView *)[self webView];
        [webview evaluateJavaScript:@"window.onNativeNavigateBack()" completionHandler:nil];
    }
}

-(void)fontSizeDidChange {
    int adjustment = [self calculateFontSizeAdjustment];
    WKWebView *webview = (WKWebView *)[self webView];
    [webview evaluateJavaScript:[NSString stringWithFormat:@"window.onFontSizeAdjustmentDidChange('%i')", adjustment] completionHandler:nil];
}

-(int)calculateFontSizeAdjustment {
    CGFloat defaultSize = 17;
    CGFloat currSize = [[UIFont preferredFontForTextStyle:UIFontTextStyleBody] pointSize];
    return currSize / defaultSize * 100;
}

@end

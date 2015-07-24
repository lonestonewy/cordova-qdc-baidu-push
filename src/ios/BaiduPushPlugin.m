//
//  BaiduPushPlugin.m
//  HaobanPlugin
//
//  Created by 马杰磊 on 15/6/17.
//  Copyright (c) 2015年 NTTData. All rights reserved.
//

#import "BaiduPushPlugin.h"
#import "BPush.h"

@implementation BaiduPushPlugin{
    NSNotificationCenter *_observer;
}

/*!
 @method
 @abstract 绑定
 */
- (void)startWork:(CDVInvokedUrlCommand*)command{
    NSLog(@"绑定");
    
    
    _observer = [[NSNotificationCenter defaultCenter] addObserverForName:CDVRemoteNotification
                  object:nil
                  queue:[NSOperationQueue mainQueue]
                  usingBlock:^(NSNotification *note) {
                      NSData *deviceToken = [note object];
                      [BPush registerDeviceToken:deviceToken];

                      
                      [BPush bindChannelWithCompleteHandler:^(id result, NSError *error) {
                          // 绑定返回值
                          if ([self returnBaiduResult:result])
                          {
                              self.result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:result];
                          }
                          else{
                              self.result = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:result];
                          }
                          [[NSNotificationCenter defaultCenter] removeObserver:_observer];
                          [self.commandDelegate sendPluginResult:self.result callbackId:command.callbackId];
                      }];
                  }];
    
    if ([[[UIDevice currentDevice] systemVersion] floatValue] >= 8.0) {
        UIUserNotificationType myTypes = UIUserNotificationTypeBadge | UIUserNotificationTypeSound | UIUserNotificationTypeAlert;
        
        UIUserNotificationSettings *settings = [UIUserNotificationSettings settingsForTypes:myTypes categories:nil];
        [[UIApplication sharedApplication] registerUserNotificationSettings:settings];
        [[UIApplication sharedApplication] registerForRemoteNotifications];
    }else {
        UIRemoteNotificationType myTypes = UIRemoteNotificationTypeBadge|UIRemoteNotificationTypeAlert|UIRemoteNotificationTypeSound;
        [[UIApplication sharedApplication] registerForRemoteNotificationTypes:myTypes];
    }
    
}

/*!
 @method
 @abstract 解除绑定
 */
- (void)stopWork:(CDVInvokedUrlCommand*)command{
    NSLog(@"解除绑定");
    [BPush unbindChannelWithCompleteHandler:^(id result, NSError *error) {
        // 解绑返回值
        if ([self returnBaiduResult:result])
        {
            self.result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
        }
        else{
            self.result = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
        }
        [self.commandDelegate sendPluginResult:self.result callbackId:command.callbackId];
    }];
}

/*!
 @method
 @abstract 回复绑定
 */
- (void)resumeWork:(CDVInvokedUrlCommand*)command{
    NSLog(@"回复绑定");
}

/*!
@method
@abstract 设置Tag
*/
- (void)setTags:(CDVInvokedUrlCommand*)command{
    NSLog(@"设置Tag");
    NSString *tagsString = command.arguments[0];
    if (![self checkTagString:tagsString]) {
        self.result = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
        [self.commandDelegate sendPluginResult:self.result callbackId:command.callbackId];
        return;
    }
    
    NSArray *tags = [tagsString componentsSeparatedByString:@","];
    if (tags) {
        [BPush setTags:tags withCompleteHandler:^(id result, NSError *error) {
            // 设置多个标签组的返回值
            if ([self returnBaiduResult:result])
            {
                self.result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
            }
            else{
                self.result = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
            }
            [self.commandDelegate sendPluginResult:self.result callbackId:command.callbackId];
        }];
    }
}

/*!
 @method
 @abstract 删除Tag
 */
- (void)delTags:(CDVInvokedUrlCommand*)command{
    NSLog(@"删除Tag");
    NSString *tagsString = command.arguments[0];
    if (![self checkTagString:tagsString]) {
        self.result = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
        [self.commandDelegate sendPluginResult:self.result callbackId:command.callbackId];
        return;
    }
    
    NSArray *tags = [tagsString componentsSeparatedByString:@","];
    if (tags) {
        [BPush delTags:tags withCompleteHandler:^(id result, NSError *error) {
            // 删除标签的返回值
            if ([self returnBaiduResult:result])
            {
                self.result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
            }
            else{
                self.result = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
            }
            [self.commandDelegate sendPluginResult:self.result callbackId:command.callbackId];
        }];
    }
}

- (BOOL)checkTagString:(NSString *)tagStr {
    NSString *str = [tagStr stringByReplacingOccurrencesOfString:@"," withString:@""];
    if ([str isEqualToString:@""]) {
        return NO;
    }
    return YES;
}

- (BOOL)returnBaiduResult:(id)result{
    NSString *resultStr = result[@"error_code"];
    if (!resultStr || [[resultStr description] isEqualToString:@"0"]){
        return YES;
    }
    return NO;
}

@end

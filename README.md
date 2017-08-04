# 注意事项

#### 关于签名加密
1. 在Module xccore 中执行，具体详见代码，由于对包名进行检查，修改包名后，XcCore.cpp也需要更改，替换com.loveorange.bank即可
2. 同时也对应用程序签名进行检查，该签名需要自行获取，在XcCore.cpp的方法checkSigned()进行更改

#### 其他第三方，需要在Gradle中配置
1. 推送使用激光推送，需要更换JPUSH_APPKEY
2. 统计使用友盟统计，需要更换UMENG_KEY_VALUE
3. 同盾合作方标识PARTNER_CODE
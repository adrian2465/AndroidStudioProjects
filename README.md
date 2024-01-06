# Steps to get project onto phone

## Initial setup
 - Installed `Android` and `Android Design Tools`
 - Created simulator for Samsung Galaxy S23+

## To setup project in ADS
 - Cloned AndroidStudioProjects from Github - [https://github.com/adrian2465/AndroidStudioProjects](https://github.com/adrian2465/AndroidStudioProjects)
 - Copied Google Drive to project directory. E.g. `cp -R ~/Google\ Drive/My\ Drive/Skunkworks/AndroidStudioProjects/GPSLocation/ GPSLocation/`
 - IS THIS NECESSARY?: Remove from project `.idea,` `.gradle,` `app/build,` `*.iml`
 - Open project in Intellij and let it do its thing.
 - (Initial only?) Updated Android Gradle Project (AGP) per recommendations to 4.2.2 (and then again to 7.4.2) (and then again to 8.4.1)

## For Each App:
 - Downloaded icons from [https://icon.kitchen](https://icon.kitchen).  NOTE: Did not download round ones.
 - Placed icons onto mipmap in Resource Manager. Overwrite all.
   - In zip archive, drill down to `Location -> Android -> res`
   - Copy `res`
   - Paste into `$project -> src -> main -> res` and replace all.

## Installing the App on the phone

### One time setup on Android - Development
 - https://developer.android.com/studio/debug/dev-options#enable 
   - Enable Developer Options by tapping Build Number 7 times
   - Under Developer Options, turn on "Stay Awake While Charging"
   - Under Developer Options, enable USB Debugging
 - On Android Studio, under Run Configuration, I checked "Always install for all users" (Not sure I need this)
 - Plug Android into USB port. Click "Allow USB Debugging" 
 - On Android Studio, get QR code provided (under Run -> Select Device -> Pair using WIFI)
 - Not sure I need this: On Samsung, using camera or in WIFI Settings, pair using QR code. Samsung S23+ should appear in Studio as a _physical_ device on which to run.
 - Run it. App is now installed.
 - Probably can disable USB Debugging again afterwards.


# 2024-01-06 @ 12:18:46 : Color Scheme (Teal)
```
<color name="colorPrimary">#00BCD4</color>
<color name="colorPrimaryDark">#3700B3</color>
<color name="colorAccent">#009688</color>
```

# 2024-01-06 @ 09:13:11 : Links
* For The Sailings: https://icon.kitchen/i/H4sIAAAAAAAAA1VOzQ6CMAx%2Bl3rlAEQNcuXgC3AzxnSs1IWxkQ00hvDudhoPXtr0%2B%2Fr9rPBAu1CEegWNYWjvNBLUPdpIGfTcWDNhmBMdSRZo6nGxM2RgOu8EYHIUcDaOb7MfyEXYMlDceOuD0Ls8V53ey7%2Fi9jWJN3BAbcglD8Xn3yEJXdIUf6IPVH6h07GqBELHVmyK8rBJ0uj1YlP%2FixA6eKNTNR9lPknBdXsDp54jLeIAAAA%3D
* For Position
* When to start engine: 


# store.*

> --------------------- ------------------------------------------------------------------------------------------
> __Type__              [Library][api.type.Library]
> __Revision__          [REVISION_LABEL](REVISION_URL)
> __Keywords__          store, IAP, Google IAP v3
> __Availability__      Basic, Pro, Enterprise
> --------------------- ------------------------------------------------------------------------------------------

## Overview

This plugin allows you to support in-app purchases using Google's in-app billing version 3.

For more information see the [In-App Purchases (IAP)][guide.monetization.IAP] guide.


## Functions

#### [store.canLoadProducts][plugin.google-iap-v3.canLoadProducts]

#### [store.canMakePurchases][plugin.google-iap-v3.canMakePurchases]

#### [store.consumePurchase()][plugin.google-iap-v3.consumePurchase]

#### [store.finishTransaction()][plugin.google-iap-v3.finishTransaction]

#### [store.init()][plugin.google-iap-v3.init]

#### [store.isActive][plugin.google-iap-v3.isActive]

#### [store.loadProducts()][plugin.google-iap-v3.loadProducts]

#### [store.purchase()][plugin.google-iap-v3.purchase]

#### [store.restore()][plugin.google-iap-v3.restore]


## Migration

If you are migrating from Google IAP v2, please see the [Migration&nbsp;Guide][plugin.google-iap-v3.migration] for an overview of key differences between each version.


## Project Settings

To use this plugin, add an entry into the `plugins` table of `build.settings`. When added, the build server will integrate the plugin during the build phase.

``````lua
settings =
{
	plugins =
	{
		["plugin.google.iap.v3"] =
		{
			publisherId = "com.coronalabs",
			supportedPlatforms = { android=true }
		},
	},
}
``````

In addition, you must enable the `BILLING` permission in `build.settings`:

``````lua
settings =
{
	android =
	{
		usesPermissions =
		{
			"com.android.vending.BILLING",
		},
	},
}
``````

## Project Configuration

To use Google IAP v3, the `license` table must be added to the project `config.lua` file. Inside this table, the `key` value should be set to the corresponding key obtained from the [Google&nbsp;Play&nbsp;Developer&nbsp;Console](https://play.google.com/apps/publish).

``````lua
application = 
{
	license =
	{
		google =
		{
			key = "Your key",
		},
	},
}
``````

## Implementation

For details on how to implement and use Google IAP v3, please see the [In-App Purchases (IAP)][guide.monetization.IAP] guide.
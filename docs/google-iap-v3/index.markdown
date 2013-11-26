
# store.*

> --------------------- ------------------------------------------------------------------------------------------
> __Type__              [Library][api.type.Library]
> __Revision__          [REVISION_LABEL](REVISION_URL)
> __Keywords__          store
> __Sample code__       */CoronaSDK/SampleCode/Store/InAppPurchase*
> __See also__          
> __Availability__      Basic, Pro, Enterprise
> --------------------- ------------------------------------------------------------------------------------------

## Overview

This feature allows you to support In-App Purchases using Google's in app billing version 3.

For more information see the [In-App Purchases (IAP)][guide.monetization.IAP] guide.


## Functions

#### [store.canLoadProducts][plugin.google-iap-v3.canLoadProducts]

#### [store.canMakePurchases][plugin.google-iap-v3.canMakePurchases]

#### [store.canMakePurchases][plugin.google-iap-v3.canMakePurchases]

#### [store.consumePurchase][plugin.google-iap-v3.consumePurchase]

#### [store.finishTransaction()][plugin.google-iap-v3.finishTransaction]

#### [store.init()][plugin.google-iap-v3.init]

#### [store.isActive][plugin.google-iap-v3.isActive]

#### [store.loadProducts()][plugin.google-iap-v3.loadProducts]

#### [store.purchase()][plugin.google-iap-v3.purchase]

#### [store.restore()][plugin.google-iap-v3.restore]

``````
settings =
{
	plugins =
	{
		-- key is the name passed to Lua's 'require()'
		["plugin.google.iap.v3"] =
		{
			-- required
			publisherId = "com.coronalabs",
		},
	},
}
``````
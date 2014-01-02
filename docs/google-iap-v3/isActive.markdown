
# store.isActive

> --------------------- ------------------------------------------------------------------------------------------
> __Type__              [Function][api.type.Function]
> __Library__           [store.*][api.library.store]
> __Return value__      [Boolean][api.type.Boolean]
> __Revision__          [REVISION_LABEL](REVISION_URL)
> __Keywords__          store, purchase, iap, in app purchase
> __Sample code__       */CoronaSDK/SampleCode/Store/InAppPurchase*
> __See also__          [store.init()][api.library.store.init]
> --------------------- ------------------------------------------------------------------------------------------


## Overview

Used to confirm that a store is properly initialized after calling [store.init()][api.library.store.init]. This returns `true` if the store has successfully initialized.

## Syntax

	store.isActive

## Example

``````lua
local store = require("plugin.google.iap.v3")

store.init( storeTransaction )

local arrayOfProductIdentifiers = 
{
	"com.coronalabs.NewExampleInAppPurchase.ConsumableTier1",
	"com.coronalabs.NewExampleInAppPurchase.NonConsumableTier1",
	"com.coronalabs.NewExampleInAppPurchase.SubscriptionTier1",
}

--confirm that store was initialized
if ( store.isActive ) then

	if ( store.canLoadProducts ) then
		store.loadProducts( arrayOfProductIdentifiers, loadProductsCallback )
	else
		--this store does not support an app fetching products
	end

end
``````
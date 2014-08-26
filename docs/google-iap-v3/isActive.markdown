
# store.isActive

> --------------------- ------------------------------------------------------------------------------------------
> __Type__              [Function][api.type.Function]
> __Library__           [store.*][plugin.google-iap-v3]
> __Return value__      [Boolean][api.type.Boolean]
> __Revision__          [REVISION_LABEL](REVISION_URL)
> __Keywords__          store, IAP, Google IAP v3, active
> __See also__          [store.init()][plugin.google-iap-v3.init]
> --------------------- ------------------------------------------------------------------------------------------


## Overview

Used to confirm that a store is properly initialized after calling [store.init()][api.library.store.init]. This returns `true` if the store was successfully initialized.

## Syntax

	store.isActive

## Example

``````lua
local store = require( "plugin.google.iap.v3" )

store.init( storeTransaction )

local arrayOfProductIdentifiers = 
{
	"com.coronalabs.NewExampleInAppPurchase.ConsumableTier1",
	"com.coronalabs.NewExampleInAppPurchase.NonConsumableTier1",
}

--confirm that the store was initialized
if ( store.isActive ) then

	if ( store.canLoadProducts ) then
		store.loadProducts( arrayOfProductIdentifiers, loadProductsCallback )
	else
		--this store does not support fetching of products
	end
end
``````
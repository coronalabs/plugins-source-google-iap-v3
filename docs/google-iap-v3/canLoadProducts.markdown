
# store.canLoadProducts

> --------------------- ------------------------------------------------------------------------------------------
> __Type__              [Function][api.type.Function]
> __Library__           [store.*][plugin.google-iap-v3]
> __Return value__      [Boolean][api.type.Boolean]
> __Revision__          [REVISION_LABEL](REVISION_URL)
> __Keywords__          store, IAP, Google IAP v3, load products
> __See also__          [store.loadProducts()][plugin.google-iap-v3.loadProducts]
> --------------------- ------------------------------------------------------------------------------------------


## Overview

This determines if the store can load products or not. It should be called before [store.loadProducts()][plugin.google-iap-v3.loadProducts].


## Syntax

	store.canLoadProducts


## Example

``````lua
local store = require( "plugin.google.iap.v3" )

local arrayOfProductIdentifiers =
{
	"com.coronalabs.NewExampleInAppPurchase.ConsumableTier1",
	"com.coronalabs.NewExampleInAppPurchase.NonConsumableTier1",
	--"bad.product.id",
}

local function productCallback( event )
	print( "showing valid products", #event.products )
	
	for i = 1,#event.products do
		print( event.products[i].title )              --string
		print( event.products[i].description )        --string
		print( event.products[i].price )              --number
		print( event.products[i].localizedPrice )     --string
		print( event.products[i].productIdentifier )  --string
	end

	print( "showing invalidProducts", #event.invalidProducts )
	for i = 1,#event.invalidProducts do
		print( event.invalidProducts[i] )
	end
end

if ( store.canLoadProducts ) then
	store.loadProducts( arrayOfProductIdentifiers, productCallback )
end
``````
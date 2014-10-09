
# store.loadProducts()

> --------------------- ------------------------------------------------------------------------------------------
> __Type__              [Function][api.type.Function]
> __Library__           [store.*][plugin.google-iap-v3]
> __Return value__      none
> __Revision__          [REVISION_LABEL](REVISION_URL)
> __Keywords__          store, IAP, Google IAP v3, load products
> __See also__          [store.canLoadProducts][plugin.google-iap-v3.canLoadProducts]
> --------------------- ------------------------------------------------------------------------------------------


## Overview

Google IAP v3 supports the loading of product information that you've entered into the developer's console. Corona provides the [store.canLoadProducts][plugin.google-iap-v3.canLoadProducts] function which returns `true` if the initialized store supports the loading of products. Following this check, the `store.loadProducts()` function will retrieve information about items available for sale. This includes the title, description, localized price, product identifier, and type.


## Syntax

	store.loadProducts( productList, listener )

##### productList ~^(required)^~
_[Table][api.type.Table]._ Lua array where each element represents a product identifier (string) of the product to load.

##### listener ~^(required)^~
_[Listener][api.type.Listener]._ Callback function which is invoked when the store finishes retrieving the product information.


## Example

`````lua
local store = require( "plugin.google.iap.v3" )

local listOfProducts = 
{
	"com.coronalabs.NewExampleInAppPurchase.ConsumableTier1",
	"com.coronalabs.NewExampleInAppPurchase.NonConsumableTier1",
	--"bad.product.id",
}

local function productCallback( event )

	if ( event.isError ) then
		print( event.errorType )
		print( event.errorString )
		return
	end
    
	print( "Showing valid products:", #event.products )
	for i = 1,#event.products do
		print( event.products[i].title )              --string
		print( event.products[i].description )        --string
		print( event.products[i].localizedPrice )     --string
		print( event.products[i].productIdentifier )  --string
		print( event.products[i].type )               --string
		print( event.products[i].priceAmountMicros )  --string
		print( event.products[i].priceCurrencyCode )  --string
		print( event.products[i].originalJson )       --string
	end

	print( "Showing invalid products:", #event.invalidProducts )
	for j = 1,#event.invalidProducts do
		print( event.invalidProducts[j] )
	end
end
 
store.loadProducts( listOfProducts, productCallback )
`````

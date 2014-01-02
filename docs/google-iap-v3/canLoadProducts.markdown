
# store.canLoadProducts

> --------------------- ------------------------------------------------------------------------------------------
> __Type__              [Function][api.type.Function]
> __Library__           [store.*][api.library.store]
> __Return value__      [Boolean][api.type.Boolean]
> __Revision__          [REVISION_LABEL](REVISION_URL)
> __Keywords__          store, load products, product listing, list products
> __Sample code__       */CoronaSDK/SampleCode/Store/InAppPurchase*
> __See also__          [event.invalidProducts][api.event.productList.invalidProducts]<br/>[event.products][api.event.productList.products]
> --------------------- ------------------------------------------------------------------------------------------


## Overview

This should be called before [store.loadProducts()][api.library.store.loadProducts].  This will return `true`.

## Syntax

	store.canLoadProducts

## Example

`````lua
local store = require("plugin.google.iap.v3")

local listOfProducts = 
{
    "com.coronalabs.NewExampleInAppPurchase.ConsumableTier1",
    "com.coronalabs.NewExampleInAppPurchase.NonConsumableTier1",
    "com.coronalabs.NewExampleInAppPurchase.SubscriptionTier1",
--  "bad.product.id",
}
 
local function productCallback( event )
    print("showing valid products", #event.products)
    for i=1, #event.products do
        print(event.products[i].title)    -- This is a string.
        print(event.products[i].description)    -- This is a string.
        print(event.products[i].price)    -- This is a number.
        print(event.products[i].localizedPrice)    -- This is a string.
        print(event.products[i].productIdentifier)    -- This is a string.
    end

    print("showing invalidProducts", #event.invalidProducts)
    for i=1, #event.invalidProducts do
		print(event.invalidProducts[i])
    end
end
if store.canLoadProducts then
    store.loadProducts( listOfProducts, productCallback )
end
`````

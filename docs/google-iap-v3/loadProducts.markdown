
# store.loadProducts()

> --------------------- ------------------------------------------------------------------------------------------
> __Type__              [Function][api.type.Function]
> __Library__           [store.*][api.library.store]
> __Return value__      none
> __Revision__          [REVISION_LABEL](REVISION_URL)
> __Keywords__          store, load products, product listing, list products
> __Sample code__       */CoronaSDK/SampleCode/Store/InAppPurchase*
> __See also__          [event.invalidProducts][api.event.productList.invalidProducts]<br/>[event.products][api.event.productList.products]
> --------------------- ------------------------------------------------------------------------------------------


## Overview

Google IAP v3 supports the loading of product information that you've entered into the developer's console. Corona provides the `store.canLoadProducts` function which returns `true` if the initialized store supports the loading of products. Following this check, the `store.loadProducts()` function will retrieve information about items available for sale. This includes the title, description, localizedPrice, productIdentifier, and type.

## Syntax

	store.loadProducts( productIdentifiers, listener )

##### productIdentifiers ~^(required)^~
_[Array][api.type.Array]._ A Lua array with each element containing a string which is the product identifier of the in-app item you want to know about.

##### listener ~^(required)^~
_[Listener][api.type.Listener]._ A callback function that is invoked when the store finishes retrieving the product information.

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
        print(event.products[i].localizedPrice)    -- This is a string.  ex. $1.23
        print(event.products[i].productIdentifier)    -- This is a string.
        print(event.products[i].type)    -- This is a string.  It can be "inapp" for an in-app product or "subs" for subscriptions
    end

    print("showing invalidProducts", #event.invalidProducts)
    for i=1, #event.invalidProducts do
		print(event.invalidProducts[i])
    end
end
 
store.loadProducts( listOfProducts, productCallback )
`````

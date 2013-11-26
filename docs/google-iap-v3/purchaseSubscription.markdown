
# store.purchaseSubscription()

> --------------------- ------------------------------------------------------------------------------------------
> __Type__              [Function][api.type.Function]
> __Library__           [store.*][api.library.store]
> __Return value__      none
> __Revision__          [REVISION_LABEL](REVISION_URL)
> __Keywords__          store, purchase, iap, in app purchase
> __Sample code__       */CoronaSDK/SampleCode/Store/InAppPurchase*
> __See also__          [event.products][api.event.productList.products]<br/>[store.canMakePurchases][api.library.store.canMakePurchases]<br/>[api.library.store.init()][api.library.store.init]
> --------------------- ------------------------------------------------------------------------------------------


## Overview

Subscribe to a subscription

## Syntax

	store.purchase( productSku )

##### productSku ~^(required)^~
_[String][api.type.String]._ A Lua array specifying the products you want to buy. Each element may contain a string which is the product identifier or a Lua table with the same fields as the product elements passed back to you from the [event.products][api.event.productList.products] array in the `loadProductsCallback` listener.

## Example

`````lua
local store = require("store")
 
function storeTransaction( event )
    local transaction = event.transaction
    if transaction.state == "purchased" then
        -- If store.purchase() was successful, you should end up in here for each product you buy.
        print("Transaction succuessful!")
        print("productIdentifier", transaction.productIdentifier)
        print("receipt", transaction.receipt)
        print("transactionIdentifier", transaction.identifier)
        print("date", transaction.date)

    elseif  transaction.state == "restored" then
        print("Transaction restored (from previous session)")

    elseif transaction.state == "cancelled" then
        print("User cancelled transaction")

    elseif transaction.state == "failed" then
        print("Transaction failed, type:", transaction.errorType, transaction.errorString)

    else
        print("unknown event")
    end

    -- Once we are done with a transaction, call this to tell the store
    -- we are done with the transaction.
    -- If you are providing downloadable content, wait to call this until
    -- after the download completes.
    store.finishTransaction( transaction )
end
 
store.init( storeTransaction )
 
store.purchase( "com.coronalabs.NewExampleInAppPurchase.NonConsumableTier1" )
`````

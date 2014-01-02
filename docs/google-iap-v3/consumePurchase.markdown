
# store.consumePurchase

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

This will consume the purchase and make the item available for purchase again.  Managed items that have been purchased but not consumed can not be purchased again.  The transaction event state will be `consumed`.  There will be no callbacks for invalid products.

## Syntax

	store.consumePurchase( productSku, listener )

##### productSku ~^(required)^~
_[Array][api.type.Array]._ A Lua array with each element containing a string which is the product identifier of the in-app item you want to consume

## Example

`````lua
local store = require("plugin.google.iap.v3")
 
function transactionCallback( event )
    local transaction = event.transaction
    if transaction.state == "purchased" then
        print("Transaction succuessful!")

    elseif transaction.state == "restored" then

       print("Transaction restored")

    elseif transaction.state == "consumed" then

        print("Transaction consumed")
        print("productIdentifier", transaction.productIdentifier)
        print("receipt", transaction.receipt)
        print("transactionIdentifier", transaction.identifier)
        print("date", transaction.date)
        print("originalReceipt", transaction.originalReceipt)
        print("originalTransactionIdentifier", transaction.originalIdentifier)
        print("originalDate", transaction.originalDate)

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
 
store.init( transactionCallback )
store.consumePurchase( {"manangedItem1", "managedItem2"} )
`````

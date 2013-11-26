
# store.restore()

> --------------------- ------------------------------------------------------------------------------------------
> __Type__              [Function][api.type.Function]
> __Library__           [store.*][api.library.store]
> __Return value__      none
> __Revision__          [REVISION_LABEL](REVISION_URL)
> __Keywords__          
> __Sample code__       */CoronaSDK/SampleCode/Store/InAppPurchase*
> __See also__          
> --------------------- ------------------------------------------------------------------------------------------


## Overview

Users who wipe the information on a device or buy a new device, may wish to restore previously purchased items without paying for them again. The `store.restore()` API initiates this process.

## Gotchas

In the Google Play Marketplace, there is no `"restored"` state for items. All purchased items will be grouped under the `"purchased"` state. When you do a restore, you should clear all purchases saved to file/database — except for consumable purchases — and treat the returned restored purchases as normal purchases.


## Syntax

	store.restore()


## Example

`````lua
local store = require "store"
 
function transactionCallback( event )
    local transaction = event.transaction
    if transaction.state == "purchased" then
        print("Transaction succuessful!")

    elseif transaction.state == "restored" then

        print("Transaction restored (from previous session)")
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
store.restore()
`````

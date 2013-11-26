
# store.init()

> --------------------- ------------------------------------------------------------------------------------------
> __Type__              [Function][api.type.Function]
> __Library__           [store.*][api.library.store]
> __Return value__      none
> __Revision__          [REVISION_LABEL](REVISION_URL)
> __Keywords__          store, init, purchase
> __Sample code__       */CoronaSDK/SampleCode/Store/InAppPurchase*
> __See also__          
> --------------------- ------------------------------------------------------------------------------------------


## Overview

Activates in-app purchases (or in-app billing as it is known on Android platforms).

Starts up the In-App Purchase engine and allows you to receive callbacks with the listener function you specify.

## Syntax

	store.init( [storeName,] listener )

##### storeName ~^(optional)^~
_[String][api.type.String]._ The unique name of the store to use for in-app purchases such as:

* `"google"`: Can only be used on an Android device that has the Google Play app installed.

##### listener ~^(required)^~
_[Listener][api.type.Listener]._ This is the listener that will handle transaction callback events.

## Gotchas

#### Google Play

You must add the following permission to the "build.settings" file in order to use Google In-App Billing.

``````lua
settings =
{
    android =
    {
        usesPermissions =
        {
            "com.android.vending.BILLING",
        },
    },
}
``````

## Example

`````lua
local store = require("store")
 
function transactionCallback( event )
    local transaction = event.transaction

    if transaction.state == "purchased" then
        print("Transaction succuessful!")
        print("productIdentifier", transaction.productIdentifier)
        print("receipt", transaction.receipt)
        print("transactionIdentifier", transaction.identifier)
        print("date", transaction.date)

    elseif  transaction.state == "restored" then
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
 
store.init( "google", transactionCallback )
`````


# store.purchase()

> --------------------- ------------------------------------------------------------------------------------------
> __Type__              [Function][api.type.Function]
> __Library__           [store.*][plugin.google-iap-v3]
> __Return value__      none
> __Revision__          [REVISION_LABEL](REVISION_URL)
> __Keywords__          store, IAP, Google IAP v3, purchase
> __See also__          [store.canLoadProducts][plugin.google-iap-v3.canLoadProducts]
>								[store.loadProducts][plugin.google-iap-v3.loadProducts]
>								[store.init()][plugin.google-iap-v3.init]
> --------------------- ------------------------------------------------------------------------------------------


## Overview

Initiates a purchase transaction on a provided product by sending out a purchase request to the store. This call does not work for subscription purchases.


## Syntax

	store.purchase( productID )

##### productID ~^(required)^~
_[String][api.type.String]._ String representing the product identifier of the item to purchase.


## Example

`````lua
local store = require( "plugin.google.iap.v3" )

function storeTransaction( event )

	local transaction = event.transaction

	if ( transaction.state == "purchased" ) then
		-- Information about a successful purchase
		print( "Transaction succuessful!" )
		print( "product identifier", transaction.productIdentifier )
		print( "receipt", transaction.receipt )
		print( "transaction identifier", transaction.identifier )
		print( "date", transaction.date )

	elseif ( transaction.state == "restored" ) then
		print( "Transaction restored from previous session." )

	elseif ( transaction.state == "cancelled" ) then
		print( "User cancelled transaction." )

	elseif transaction.state == "failed" or transaction.isError then
		print( "Transaction failed:", transaction.errorType, transaction.errorString )

	else
		print( "(unknown event)" )
	end

end
 
store.init( storeTransaction )
 
store.purchase( "com.coronalabs.NewExampleInAppPurchase.NonConsumableTier1" )
`````

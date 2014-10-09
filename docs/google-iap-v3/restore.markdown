
# store.restore()

> --------------------- ------------------------------------------------------------------------------------------
> __Type__              [Function][api.type.Function]
> __Library__           [store.*][plugin.google-iap-v3]
> __Return value__      none
> __Revision__          [REVISION_LABEL](REVISION_URL)
> __Keywords__          store, IAP, Google IAP v3, restore
> --------------------- ------------------------------------------------------------------------------------------


## Overview

Users who wipe the information on a device or buy a new device may wish to restore previously purchased items without paying for them again. The `store.restore()` API initiates this process. However, in the Google Play Marketplace, there is no `"restored"` state for items. All purchased items will be grouped under the `"purchased"` state. When you do a restore, you should clear all purchases saved to file/database — except for consumable purchases — and treat the returned restored purchases as normal purchases.


## Syntax

	store.restore()


## Example

`````lua
local store = require( "plugin.google.iap.v3" )
 
function transactionCallback( event )

	local transaction = event.transaction
	 
	if ( transaction.state == "purchased" ) then

		print( "product identifier", transaction.productIdentifier )
		print( "receipt", transaction.receipt )
		print( "transaction identifier", transaction.identifier )
		print( "date", transaction.date )
		print( "original receipt", transaction.originalReceipt )
		print( "original transaction identifier", transaction.originalIdentifier )
		print( "original date", transaction.originalDate )

	elseif ( transaction.state == "cancelled" ) then
		print( "User cancelled transaction" )

	elseif ( transaction.state == "failed" ) then
		print( "Transaction failed:", transaction.errorType, transaction.errorString )

	else
		print( "(unknown event)" )
	end

end
 
store.init( transactionCallback )

store.restore()
`````
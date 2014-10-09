
# store.init()

> --------------------- ------------------------------------------------------------------------------------------
> __Type__              [Function][api.type.Function]
> __Library__           [store.*][plugin.google-iap-v3]
> __Return value__      none
> __Revision__          [REVISION_LABEL](REVISION_URL)
> __Keywords__          store, IAP, Google IAP v3, initialize
> __See also__          [store.purchase()][plugin.google-iap-v3.purchase]
>								[store.restore()][plugin.google-iap-v3.restore]
>								[store.isActive][plugin.google-iap-v3.isActive]
> --------------------- ------------------------------------------------------------------------------------------


## Overview

Activates in-app purchases (in-app billing) on Android platforms by starting the <nobr>in-app</nobr> purchase engine and allowing you to receive callbacks with the specified listener function.

## Syntax

	store.init( [storeName,] listener )

##### storeName ~^(optional)^~
_[String][api.type.String]._ The unique name of the store to use for in-app purchases. For Google IAP v3, use `"google"`. This can only be used on an Android device which has the Google Play app installed.

##### listener ~^(required)^~
_[Listener][api.type.Listener]._ The listener that will handle transaction callback events.


## Gotchas

Please see the Google IAP v3 plugin [index][plugin.google-iap-v3] page for important setup and configuration details.


## Example

`````lua
local store = require( "plugin.google.iap.v3" )

function transactionCallback( event )

	local transaction = event.transaction
	
	if ( transaction.state == "purchased" ) then
		print( "Transaction succuessful!" )

	elseif ( transaction.state == "consumed" ) then

		print( "Transaction consumed:" )
		print( "product identifier", transaction.productIdentifier )
		print( "receipt", transaction.receipt )
		print( "transaction identifier", transaction.identifier )
		print( "date", transaction.date )
		print( "original receipt", transaction.originalReceipt )
		print( "original transaction identifier", transaction.originalIdentifier )
		print( "original date", transaction.originalDate )

	elseif ( transaction.state == "cancelled" ) then
		print( "User cancelled transaction." )

	elseif ( transaction.state == "failed" ) then
		print( "Transaction failed:", transaction.errorType, transaction.errorString )

	else
		print( "(unknown event)" )
	end

end
 
store.init( transactionCallback )
`````
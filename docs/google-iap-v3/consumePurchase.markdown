
# store.consumePurchase

> --------------------- ------------------------------------------------------------------------------------------
> __Type__              [Function][api.type.Function]
> __Library__           [store.*][plugin.google-iap-v3]
> __Return value__      [Boolean][api.type.Boolean]
> __Revision__          [REVISION_LABEL](REVISION_URL)
> __Keywords__          store, IAP, Google IAP v3, consume purchase
> __See also__          [store.init()][plugin.google-iap-v3.init]
> --------------------- ------------------------------------------------------------------------------------------


## Overview

This will consume purchases and make the items available for purchase again. In Google&nbsp;IAP&nbsp;v3, once a product is purchased, it is considered "owned" and it cannot be purchased again. Thus, you must send a consumption request to revert "owned" products to "unowned" products so they become available for purchase again. Consuming products also discards their previous purchase data.

Note that some items are designed to be purchased only once and you should __not__ consume them. For example, if a purchase unlocks a new world within a game, it should be ineligible for future consumption. In the Google IAP portal, these type of purchases are set up as __managed&nbsp;products__. Alternatively, some items can be purchased multiple times, for example energy packs and gems. These are set up as __unmanaged&nbsp;products__. However, because Google now considers these purchases as "owned" just like managed products, they must be consumed before they can be purchased again. For further information, please see Google's [documentation](http://developer.android.com/google/play/billing/api.html#consume).

When products are consumed, the listener function's `event.transaction.state` will be `consumed`. There are no callbacks for invalid products.


## Syntax

	store.consumePurchase( productList, listener )

##### productList ~^(required)^~
_[Table][api.type.Table]._ Lua array where each element represents a product identifier (string) of the item to consume.


## Example

`````lua
local store = require( "plugin.google.iap.v3" )

function transactionCallback( event )

	local transaction = event.transaction
	
	if ( transaction.state == "purchased" ) then
		print( "Transaction succuessful!" )

	elseif ( transaction.state == "restored" ) then
		print( "Transaction restored from previous session." )

	elseif ( transaction.state == "consumed" ) then

		print( "Transaction consumed!" )
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
store.consumePurchase( { "product1", "product2" }, transactionCallback )
`````
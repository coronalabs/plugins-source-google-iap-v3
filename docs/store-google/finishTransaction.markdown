
# store.finishTransaction()

> --------------------- ------------------------------------------------------------------------------------------
> __Type__              [Function][api.type.Function]
> __Library__           [store.*][api.library.store]
> __Return value__      none
> __Revision__          [REVISION_LABEL](REVISION_URL)
> __Keywords__          store, finish, transaction
> __Sample code__       */CoronaSDK/SampleCode/Store/InAppPurchase*
> __See also__          [store.purchase()][api.library.store.purchase]<br/>[store.restore()][api.library.store.restore]
> --------------------- ------------------------------------------------------------------------------------------


## Overview

Notifies the Apple iTunes Store that a transaction is complete.

After you finish handling a transaction, you must call `store.finishTransaction()` on the transaction object. If you don't do this, the Apple iTunes Store will think your transaction was interrupted and will attempt to resume it on the next application launch.


## Syntax

	store.finishTransaction( transaction )

##### transaction ~^(required)^~
The transaction object belonging to the transaction you want to mark as finished.


## Example

`````lua
store.finishTransaction( transaction )
`````
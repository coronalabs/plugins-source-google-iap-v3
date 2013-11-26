
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

No-op.  This call does nothing and is here to help with cross platform compatibility.


## Syntax

	store.finishTransaction( transaction )

##### transaction ~^(optional)^~
The transaction object belonging to the transaction you want to mark as finished.


## Example

`````lua
store.finishTransaction( transaction )
`````
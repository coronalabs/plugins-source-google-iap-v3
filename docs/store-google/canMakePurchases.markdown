# store.canMakePurchases

> --------------------- ------------------------------------------------------------------------------------------
> __Type__              [Function][api.type.Function]
> __Library__           [store.*][api.library.store]
> __Return value__      [Boolean][api.type.Boolean]
> __Revision__          [REVISION_LABEL](REVISION_URL)
> __Keywords__          iap, in-app purchase, in-app billing, purchases
> __Sample code__       */CoronaSDK/SampleCode/Store/InAppPurchase*
> __See also__          [store.purchase()][api.library.store.purchase]
> --------------------- ------------------------------------------------------------------------------------------


## Overview

This property is `true` if purchases are allowed, `false` otherwise.

## Syntax

	store.canMakePurchases
	
## Gotchas

iOS devices have a setting that disables purchasing. A common case for this is to prevent children from accidentally purchasing things without permission. Corona provides an API to check whether purchasing is possible. Use this preemptively to avoid having your users navigate through many purchase steps only to find out that purchasing is forbidden.

## Example

`````lua
if store.canMakePurchases then
    store.purchase( listOfProducts )
else
    print("Store purchases are not available")
end
`````

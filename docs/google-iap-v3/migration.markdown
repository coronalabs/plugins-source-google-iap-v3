# Google IAP v3 Migration Guide

The following is an overview of the differences between Google&nbsp;IAP&nbsp;v2, implemented via Corona's [store][api.library.store] library, and Google&nbsp;IAP&nbsp;v3, implemented via the v3 [plugin][plugin.google-iap-v3].

## Additions

* [store.consumePurchase()][plugin.google-iap-v3.consumePurchase] &mdash; This will consume purchases and make the items available for purchase again. In Google&nbsp;IAP&nbsp;v3, once a product is purchased, it is considered "owned" and it cannot be purchased again. Thus, you must send a consumption request to revert "owned" products to "unowned" products so they become available for purchase again. Consuming products also discards their previous purchase data.

## Differences

* Instead of `require( "store" )`, use `require( "plugin.google.iap.v3" )`.

* [store.loadProducts()][plugin.google-iap-v3.loadProducts] now returns a list of all products.

* [store.purchase()][plugin.google-iap-v3.purchase] takes a single product identifier instead of a product array.

* [store.finishTransaction()][plugin.google-iap-v3.finishTransaction] is not necessary or operational.

## Transaction Events

* [store.purchase()][plugin.google-iap-v3.purchase] &mdash; Callback function receives one transaction event as `event.transaction`.

* [store.consumePurchase()][plugin.google-iap-v3.consumePurchase] &mdash; One transaction event occurs for each item.

* [store.restore()][plugin.google-iap-v3.restore] &mdash; One transaction event occurs for each item.

* [store.loadProducts()][plugin.google-iap-v3.loadProducts] &mdash; Callback function receives an array of valid products (`event.products`) and an array of invalid products (`event.invalidProducts`).
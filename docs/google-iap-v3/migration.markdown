# Store 2.0 Migration Guide

## What's New

### Subscriptions
store.canPurchaseSubscriptions
store.purchaseSubscription() - Starts the purchase flow for a single subscription.

### Consumables
store.consumePurchase() - This will mark the item as consumed.  Consumed products can be purchased again.  An example of a item that should be consumable is a 5 minute power up.  An example of a item that shouldn't be consumed is to unlock a level.

## What's Different
store.loadProducts() now returns a list of all the products.  In Google IAP v2 this did not work.
store.purchase() takes a single product instead of an array of products.

## What's Removed
store.finishTransaction() - noop

## Events
store.purchase() - Transaction event
store.purchaseSubscription() - Transaction event
store.consumePurchase() - One transaction event for each item
store.restore() - One transactin event for each item
store.loadProducts() Product list event
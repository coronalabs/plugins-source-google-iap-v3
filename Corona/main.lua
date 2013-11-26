local store = require("plugin.google.iap.v3")

local products = {
	"managed.product.1",
	"managed.product.2",
	"m3",
	"bad managed data"
}

local subscriptionProducts = {
	"some.subscription.1",
	"com.subscription.2",
	"bad subdata"
}

local listenerTable = {}
function listenerTable:productList(event)
	print("validProduct")
	for i = 1, #event.products do
		print("--------------------------------------------------------------------")
        print("title: ", event.products[i].title)    -- This is a string.
        print("description: ", event.products[i].description)    -- This is a string.
        print("localizedPrice: ", event.products[i].localizedPrice)    -- This is a string.
        print("productIdentifier: ", event.products[i].productIdentifier)    -- This is a string.
        print("type: ", event.products[i].type)    -- This is a string.
    end

    print("--------------------------------------------------------------------")
    print("invalidProducts")
    for i = 1, #event.invalidProducts do 
    	print(event.invalidProducts[i])
    end
end

function listenerTable:storeTransaction(event)
	print(event.name)
	print("--------------------------------------------------------------------")
	print("type: ", event.transaction.type)
	print("identifier: ", event.transaction.identifier)
	print("packageName: ", event.transaction.packageName)
	print("productIdentifier: ", event.transaction.productIdentifier)
	print("date: ", event.transaction.date)
	print("state: ", event.transaction.state)
	print("token: ", event.transaction.token)
	print("originalJson: ", event.transaction.originalJson)
	print("signature: ", event.transaction.signature)
end

function listenerTable:init(event)
	print("isError: ", event.isError)
	store.purchase(products[1])
	-- store.consumePurchase(products)
	-- store.loadProducts( products, subscriptionProducts, listenerTable )
	-- store.purchaseSubscription(subscriptionProducts)
	-- store.restore()
end

store.init(listenerTable)
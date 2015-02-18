display.setStatusBar( display.DefaultStatusBar )

-- for i=3,26 do
-- 	print('"' .. i .. '",')
-- end

local store = require("plugin.google.iap.v3")

local products = {
	"p3",
"p4",
-- "p5",
-- "p6",
-- "p7",
-- "p8",
-- "p9",
-- "p10",
-- "p11",
-- "p12",
-- "p13",
-- "p14",
-- "p15",
-- "p16",
-- "p17",
-- "p18",
-- "p19",
-- "p20",
-- "p21",
-- "p22",
-- "p23",
-- "p24",
-- "p25",
-- "p26",
-- "asdfasdf",
-- "valid1"
}

local subscriptionProducts = {
	"some.subscription.1",
	"com.subscription.2",
	"bad subdata"
}

local listenerTable = {}
function listenerTable:productList(event)
	print("isActive3: ", store.isActive)
	if event.isError then
        print(event.errorType)
        print(event.errorString)
        return
    end


	print("validProduct")
	for i = 1, #event.products do
		print("--------------------------------------------------------------------")
        print("title: ", event.products[i].title)    -- This is a string.
        print("description: ", event.products[i].description)    -- This is a string.
        print("localizedPrice: ", event.products[i].localizedPrice)    -- This is a string.
        print("productIdentifier: ", event.products[i].productIdentifier)    -- This is a string.
        print("type: ", event.products[i].type)    -- This is a string.
        print("priceAmountMicros: ", event.products[i].priceAmountMicros)
        print("priceCurrencyCode: ", event.products[i].priceCurrencyCode)
        print("originalJson: ", event.products[i].originalJson)
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
	print("errorType: ", event.transaction.errorType)
	print("errorString: ", event.transaction.errorString)
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
print("isActive1: ", store.isActive)
store.init(listenerTable)
print("isActive2: ", store.isActive)
print("target: ", store.target)

-- print("pre-loadProducts")
-- store.loadProducts( products, listenerTable )
-- -- print("post-loadProducts")

-- store.purchase( products[1] )

-- store.consumePurchase(products)

local function listener(event)
	for i=1, #products do
		store.purchase(products[i])
	end
end

local function listener1(event)
	store.consumePurchase(products)
end

local function listener2(event)
	local function productCallback( event )

	    if ( event.isError ) then
	        print( event.errorType )
	        print( event.errorString )
	        return
	    end

	    print( "Showing valid products:", #event.products )
	    for i = 1,#event.products do
	        print( event.products[i].title )              --string
	        print( event.products[i].description )        --string
	        print( event.products[i].localizedPrice )     --string
	        print( event.products[i].productIdentifier )  --string
	        print( event.products[i].type )               --string
	        print( event.products[i].priceAmountMicros )  --string
	        print( event.products[i].priceCurrencyCode )  --string
	        print( event.products[i].originalJson )       --string
	    end

	    print( "Showing invalid products:", #event.invalidProducts )
	    for j = 1,#event.invalidProducts do
	        print( event.invalidProducts[j] )
	    end
	end

	store.loadProducts( products, productCallback )
end

local buy = display.newText( "buy", 100, 100, native.systemFont, 25 )
buy:addEventListener( "tap", listener )

local consume = display.newText( "consume", 100, 140, native.systemFont, 25 )
consume:addEventListener( "tap", listener1 )

local restore = display.newText( "loadProducts", 100, 180, native.systemFont, 25 )
restore:addEventListener( "tap", listener2 )
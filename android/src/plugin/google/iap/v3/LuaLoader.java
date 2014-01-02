// This corresponds to the name of the Lua library,
// e.g. [Lua] require "plugin.library"
package plugin.google.iap.v3;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import plugin.google.iap.v3.util.IabHelper;
import plugin.google.iap.v3.util.IabResult;
import plugin.google.iap.v3.util.Inventory;
import plugin.google.iap.v3.util.Purchase;
import plugin.google.iap.v3.util.SkuDetails;

import com.naef.jnlua.LuaState;
import com.naef.jnlua.LuaType;
import com.naef.jnlua.JavaFunction;
import com.naef.jnlua.NamedJavaFunction;

import com.ansca.corona.CoronaActivity;
import com.ansca.corona.CoronaEnvironment;
import com.ansca.corona.CoronaLua;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeListener;
import com.ansca.corona.CoronaRuntimeTaskDispatcher;
import com.ansca.corona.CoronaRuntimeTask;

public class LuaLoader implements JavaFunction {
	private int fLibRef;
	private int fListener;
	private IabHelper fHelper;
	private CoronaRuntimeTaskDispatcher fDispatcher;

	/**
	 * Creates a new object for displaying banner ads on the CoronaActivity
	 */
	public LuaLoader() {
		CoronaActivity activity = CoronaEnvironment.getCoronaActivity();
		// Validate.
		if (activity == null) {
			throw new IllegalArgumentException("Activity cannot be null.");
		}
	}

	/**
	 * Warning! This method is not called on the main UI thread.
	 */
	@Override
	public int invoke(LuaState L) {
		fDispatcher = new CoronaRuntimeTaskDispatcher( L );

		// Add functions to library
		NamedJavaFunction[] luaFunctions = new NamedJavaFunction[] {
			new InitWrapper(),
			new LoadProductsWrapper(),
			new PurchaseWrapper(),
			new ConsumePurchaseWrapper(),
			new PurchaseSubscriptionWrapper(),
			new FinishTransactionWrapper(),
			new RestoreWrapper()
		};

		String libName = L.toString( 1 );
		L.register(libName, luaFunctions);

		L.pushValue(-1);
		fLibRef = L.ref(LuaState.REGISTRYINDEX);

		L.pushBoolean(true);
		L.setField(-2, "canLoadProducts");

		L.pushBoolean(true);
		L.setField(-2, "canMakePurchases");

		L.pushBoolean(false);
		L.setField(-2, "isActive");

		L.pushBoolean(true);
		L.setField(-2, "canPurchaseSubscriptions");

		return 1;
	}

	private int init(LuaState L) {
		int listenerIndex = 1;
		String licenseKey = "";

		L.getGlobal("require");
		L.pushString("config");
		L.call(1, LuaState.MULTRET);

		//gets the application table
		L.getGlobal("application");
		if (L.type(-1) == LuaType.TABLE) {
			//push the license table to the top of the stack
			L.getField(-1, "license");
			if(L.type(-1) == LuaType.TABLE) {
				//push the google table to the top of the stack
				L.getField(-1, "google");
				if(L.type(-1) == LuaType.TABLE) {
					//gets the key field from the google table
					L.getField(-1, "key");
					if(L.type(-1) == LuaType.STRING) {
						licenseKey = L.toString(-1);
					}
					L.pop(1);
				}
				L.pop(1);
			}
			L.pop(1);
		}
		L.pop(1);
		
		if (L.type(listenerIndex) == LuaType.STRING) {
			listenerIndex++;
		}

		fListener = CoronaLua.REFNIL;
		if (CoronaLua.isListener(L, listenerIndex, "storeTransaction")) {
			fListener = CoronaLua.newRef(L, listenerIndex);
		}

		final AtomicBoolean syncObject = new AtomicBoolean(true);

		Context context = CoronaEnvironment.getApplicationContext();
		if (licenseKey.length() > 0 && context != null) {
			fHelper = new IabHelper(context, licenseKey);
			fHelper.enableDebugLogging(true);
			fHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
				public void onIabSetupFinished(IabResult result) {
					final IabResult finalResult = result;
					CoronaRuntimeTask runtimeTask = new CoronaRuntimeTask() {
						@Override
						public void executeUsing(CoronaRuntime runtime) {
							LuaState L = runtime.getLuaState();
							// Set the various fields of the store object which we didn't know that status of when it was created but not inited yet
							L.rawGet(LuaState.REGISTRYINDEX, fLibRef);

							L.pushBoolean(finalResult.isSuccess());
							L.setField(-2, "isActive");

							L.pushBoolean(fHelper.subscriptionsSupported());
							L.setField(-2, "canPurchaseSubscriptions");

							L.pop(1);						
						}
					};
					fDispatcher.send(runtimeTask);
					synchronized(syncObject) {
						syncObject.set(false);
						syncObject.notifyAll();
					}
				}
			});
		}
		synchronized(syncObject) {
			while(syncObject.get()) {
				try {
					syncObject.wait();
				} catch (java.lang.InterruptedException e) {}
			}
		}
		return 0;
	}

	private int loadProducts(LuaState L) {
		if (fHelper == null) {
			Log.w("Corona", "Please call init before trying to load products.");
			return 0;
		}

		int managedProductsTableIndex = 1;
		int listenerIndex = 2;

		int managedProductsLength = L.length(managedProductsTableIndex);
		final HashSet<String> managedProducts = new HashSet<String>(managedProductsLength);

		for (int i = 1; i <= managedProductsLength; i++) {
			L.rawGet(managedProductsTableIndex, i);
			if (L.type(-1) == LuaType.STRING) {
				managedProducts.add(L.toString(-1));
			} else {
				managedProducts.add("");
			}
			L.pop(1);
		}

		int subscriptionProductsLength = 0;
		HashSet<String> subscriptionProducts = null;
		if (!CoronaLua.isListener(L, listenerIndex, "productList")) {
			subscriptionProductsLength = L.length(listenerIndex);
			subscriptionProducts = new HashSet<String>(subscriptionProductsLength);
			for (int i = 1; i <= subscriptionProductsLength; i++) {
				L.rawGet(listenerIndex, i);
				if (L.type(-1) == LuaType.STRING) {
					subscriptionProducts.add(L.toString(-1));
				} else {
					subscriptionProducts.add("");
				}
				L.pop(1);
			}
			listenerIndex++;
		}

		final HashSet<String> finalSubscriptionProducts = subscriptionProducts;

		final int listener = CoronaLua.isListener(L, listenerIndex, "productList") ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

		fHelper.queryInventoryAsync(true, managedProducts, finalSubscriptionProducts, new IabHelper.QueryInventoryFinishedListener() {
			@Override
			public void onQueryInventoryFinished(IabResult result, Inventory inv) {
				ProductListRuntimeTask task = new ProductListRuntimeTask(inv, managedProducts, finalSubscriptionProducts, listener);
				fDispatcher.send(task);
			}
		});

		return 0;
	}


	private int restore(LuaState L) {
		fHelper.queryInventoryAsync(false, null, null, new IabHelper.QueryInventoryFinishedListener() {
			@Override
			public void onQueryInventoryFinished(IabResult result, Inventory inv) {
				List<Purchase> allPurchases = inv.getAllPurchases();
				Iterator<Purchase> iterator = allPurchases.iterator();
				while (iterator.hasNext()) {
					fDispatcher.send(new StoreTransactionRuntimeTask(iterator.next(), fListener));
				}
			}
		});
		return 0;
	}

	private int purchaseSubscription(LuaState L) {
		if (fHelper == null) {
			Log.w("Corona", "Please call init before trying to purchase products.");
			return 0;
		}

		CoronaActivity activity = CoronaEnvironment.getCoronaActivity();
		if (activity == null) {
			Log.w("Corona", "The Corona Activity was null.");
			return 0;
		}

		int requestCode = activity.registerActivityResultHandler(new CoronaActivity.OnActivityResultHandler() {
			@Override
			public void onHandleActivityResult(CoronaActivity activity, int requestCode, int resultCode, android.content.Intent data) {
				activity.unregisterActivityResultHandler(this);
				if (fHelper == null) {
					return;
				}
				fHelper.handleActivityResult(requestCode, resultCode, data);
			}
		});

		int skuIndex = 1;
		String sku = "";
		if (L.type(skuIndex) == LuaType.STRING) {
			sku = L.toString(skuIndex);
		}
		L.pop(1);

		fHelper.launchSubscriptionPurchaseFlow(activity, sku, requestCode, new PurchaseFinishedListener(fDispatcher, fListener));

		return 0;
	}

	private int purchase(LuaState L) {
		if (fHelper == null) {
			Log.w("Corona", "Please call init before trying to purchase products.");
			return 0;
		}

		CoronaActivity activity = CoronaEnvironment.getCoronaActivity();
		if (activity == null) {
			Log.w("Corona", "The Corona Activity was null.");
			return 0;
		}

		int requestCode = activity.registerActivityResultHandler(new CoronaActivity.OnActivityResultHandler() {
			@Override
			public void onHandleActivityResult(CoronaActivity activity, int requestCode, int resultCode, android.content.Intent data) {
				activity.unregisterActivityResultHandler(this);
				if (fHelper == null) {
					return;
				}
				fHelper.handleActivityResult(requestCode, resultCode, data);
			}
		});

		int skuIndex = 1;
		String sku = "";
		if (L.type(skuIndex) == LuaType.STRING) {
			sku = L.toString(skuIndex);
		}
		L.pop(1);

		fHelper.launchPurchaseFlow(activity, sku, requestCode, new PurchaseFinishedListener(fDispatcher, fListener));

		return 0;
	}

	private int consumePurchase(LuaState L) {
		if (fHelper == null) {
			Log.w("Corona", "Please call init before trying to load products.");
			return 0;
		}

		int skusIndex = 1;
		final HashSet<String> consumedProducts = new HashSet<String>();
		if (L.type(skusIndex) == LuaType.TABLE) {
			int consumedProductsLength = L.length(skusIndex);

			for (int i = 1; i <= consumedProductsLength; i++) {
				L.rawGet(skusIndex, i);
				if (L.type(-1) == LuaType.STRING) {
					consumedProducts.add(L.toString(-1));
				} else {
					consumedProducts.add("");
				}
				L.pop(1);
			}
		} else {
			String sku = "";
			if (L.type(skusIndex) == LuaType.STRING) {
				sku = L.toString(skusIndex);
			}
			consumedProducts.add(sku);
		}

		fHelper.queryInventoryAsync(true, null, null, new IabHelper.QueryInventoryFinishedListener() {
			@Override
			public void onQueryInventoryFinished(IabResult result, Inventory inv) {
				ArrayList<Purchase> purchasesToConsume = new ArrayList<Purchase>(consumedProducts.size());
				Iterator<String> skuIterator = consumedProducts.iterator();

				while (skuIterator.hasNext()) {
					String sku = skuIterator.next();
					if (inv.hasPurchase(sku)) {
						purchasesToConsume.add(inv.getPurchase(sku));
					}
				}

				fHelper.consumeAsync(purchasesToConsume, new IabHelper.OnConsumeMultiFinishedListener() {
					public void onConsumeMultiFinished(final List<Purchase> purchases, final List<IabResult> results) {
						Iterator<Purchase> iterator = purchases.iterator();
						StoreTransactionRuntimeTask task;

						while (iterator.hasNext()) {
							Purchase purchase = iterator.next();
							purchase.setPurchaseState(Purchase.State.Consumed);
							task = new StoreTransactionRuntimeTask(purchase, fListener);
							fDispatcher.send(task);
						}
					}
				});
			}
		});

		return 0;
	}

	private class InitWrapper implements NamedJavaFunction {
		@Override
		public String getName() {
			return "init";
		}
		
		/**
		 * Warning! This method is not called on the main UI thread.
		 */
		@Override
		public int invoke(LuaState L) {
			return init(L);
		}
	}

	private class LoadProductsWrapper implements NamedJavaFunction {
		@Override
		public String getName() {
			return "loadProducts";
		}

		@Override
		public int invoke(LuaState L) {
			return loadProducts(L);
		}
	}

	private class PurchaseWrapper implements NamedJavaFunction {
		@Override
		public String getName() {
			return "purchase";
		}

		@Override
		public int invoke(LuaState L) {
			return purchase(L);
		}
	}

	private class PurchaseSubscriptionWrapper implements NamedJavaFunction {
		@Override
		public String getName() {
			return "purchaseSubscription";
		}

		@Override
		public int invoke(LuaState L) {
			return purchaseSubscription(L);
		}
	}

	private class ConsumePurchaseWrapper implements NamedJavaFunction {
		@Override
		public String getName() {
			return "consumePurchase";
		}

		@Override
		public int invoke(LuaState L) {
			return consumePurchase(L);
		}
	}

	private class FinishTransactionWrapper implements NamedJavaFunction {
		@Override
		public String getName() {
			return "finishTransaction";
		}

		@Override
		public int invoke(LuaState L) {
			return 0; 
		}
	}

	private class RestoreWrapper implements NamedJavaFunction {
		@Override
		public String getName() {
			return "restore";
		}

		@Override
		public int invoke(LuaState L) {
			return restore(L);
		}
	}
}
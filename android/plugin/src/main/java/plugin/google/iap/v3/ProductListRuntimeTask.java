// This corresponds to the name of the Lua library,
// e.g. [Lua] require "plugin.library"
package plugin.google.iap.v3;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

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

public class ProductListRuntimeTask implements CoronaRuntimeTask {

	private Inventory fInventory;
	private int fListener;
	private IabResult fResult;
	private HashSet<String> fManagedProducts;
	private HashSet<String> fSubscriptionProducts;

	public ProductListRuntimeTask(Inventory inventory, HashSet<String> managedProducts, HashSet<String> subscriptionProducts, IabResult result, int listener) {
		fInventory = inventory;
		fListener = listener;
		fResult = result;
		fManagedProducts = managedProducts;
		fSubscriptionProducts = subscriptionProducts;
	}

	@Override
	public void executeUsing(com.ansca.corona.CoronaRuntime runtime) {
		if (fListener == CoronaLua.REFNIL) {
			return;
		}

		// *** We are now running on the Corona runtime thread. ***
		// Fetch the Corona runtime's Lua state.
		LuaState L = runtime.getLuaState();
		try {
			CoronaLua.newEvent( L, "productList");

			if (fResult.isFailure()) {
				L.pushBoolean(true);
				L.setField(-2, "isError");

				L.pushNumber(fResult.getResponse());
				L.setField(-2, "errorType");

				L.pushString(fResult.getMessage());
				L.setField(-2, "errorString");
			} else {
				L.newTable();

				Collection<SkuDetails> allSkuDetails = fInventory.getAllSkuDetails();
				Iterator<SkuDetails> skuDetailsIterator = allSkuDetails.iterator();

				int count = 1;
				while(skuDetailsIterator.hasNext()) {
					L.newTable();
					SkuDetails skuDetails = skuDetailsIterator.next();
					skuDetails.pushToLua(L, -2);
					L.rawSet(-2, count);
					count++;

					if (fManagedProducts != null) {
						fManagedProducts.remove(skuDetails.getSku());
					}
					if (fSubscriptionProducts != null) {
						fSubscriptionProducts.remove(skuDetails.getSku());
					}
				}
				L.setField(-2, "products");

				Iterator<String> iterator;
				if (fManagedProducts != null) {
					iterator = fManagedProducts.iterator();
					L.newTable();
					count = 1;
					while(iterator.hasNext()) {
						L.pushString(iterator.next());
						L.rawSet(-2, count);
						count++;
					}
				}

				
				if (fSubscriptionProducts != null) {
					iterator = fSubscriptionProducts.iterator();
					while(iterator.hasNext()) {
						L.pushString(iterator.next());
						L.rawSet(-2, count);
						count++;
					}
				}

				L.setField(-2, "invalidProducts");
			}


			// Dispatch event table at top of stack
			CoronaLua.dispatchEvent(L, fListener, 0);
		}
		catch (Exception ex) {
			Log.e("Corona", "ProductListRuntimeTask: dispatching Google IAP productList event", ex);
		}
		CoronaLua.deleteRef(L, fListener);
	}
}

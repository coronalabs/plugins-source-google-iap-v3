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
	private HashSet<String> fManagedProducts;
	private HashSet<String> fSubscriptionProducts;

	public ProductListRuntimeTask(Inventory inventory, HashSet<String> managedProducts, HashSet<String> subscriptionProducts, int listener) {
		fInventory = inventory;
		fListener = listener;

		fManagedProducts = managedProducts;
		fSubscriptionProducts = subscriptionProducts;
	}

	@Override
	public void executeUsing(com.ansca.corona.CoronaRuntime runtime) {
		if (fListener == CoronaLua.REFNIL) {
			return;
		}

		Collection<SkuDetails> allSkuDetails = fInventory.getAllSkuDetails();
		Iterator<SkuDetails> skuDetailsIterator = allSkuDetails.iterator();

		// *** We are now running on the Corona runtime thread. ***
		// Fetch the Corona runtime's Lua state.
		LuaState L = runtime.getLuaState();
		try {

			CoronaLua.newEvent( L, "productList");

			L.newTable();
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

			Iterator<String> iterator = fManagedProducts.iterator();
			L.newTable();
			count = 1;
			while(iterator.hasNext()) {
				L.pushString(iterator.next());
				L.rawSet(-2, count);
				count++;
			}
			
			iterator = fSubscriptionProducts.iterator();
			while(iterator.hasNext()) {
				L.pushString(iterator.next());
				L.rawSet(-2, count);
				count++;
			}
			L.setField(-2, "invalidProducts");


			// Dispatch event table at top of stack
			CoronaLua.dispatchEvent(L, fListener, 0);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		CoronaLua.deleteRef(L, fListener);
	}
}
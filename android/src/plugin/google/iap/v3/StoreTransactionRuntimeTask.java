// This corresponds to the name of the Lua library,
// e.g. [Lua] require "plugin.library"
package plugin.google.iap.v3;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import plugin.google.iap.v3.util.IabResult;
import plugin.google.iap.v3.util.Purchase;

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

public class StoreTransactionRuntimeTask implements CoronaRuntimeTask {

	private Purchase fPurchase;
	private int fListener;
	private IabResult fResult;

	public StoreTransactionRuntimeTask(Purchase purchase, IabResult result, int listener) {
		fPurchase = purchase;
		fListener = listener;
		fResult = result;
	}

	@Override
	public void executeUsing(com.ansca.corona.CoronaRuntime runtime) {
		if (fListener == CoronaLua.REFNIL) {
			return;
		}
		
		// *** We are now running on the Corona runtime thread. ***
		LuaState L = runtime.getLuaState();
		try {
			CoronaLua.newEvent( L, "storeTransaction");

			if (fResult.isFailure()) {
				L.pushBoolean(true);
				L.setField(-2, "isError");

				L.pushNumber(fResult.getResponse());
				L.setField(-2, "errorType");

				L.pushString(fResult.getMessage());
				L.setField(-2, "errorString");

			} else {
				L.newTable();

				L.pushString(fPurchase.getItemType());
				L.setField(-2, "type");
				
				L.pushString(fPurchase.getOrderId());
				L.setField(-2, "identifier");
				
				L.pushString(fPurchase.getPackageName());
				L.setField(-2, "packageName");
				
				L.pushString(fPurchase.getSku());
				L.setField(-2, "productIdentifier");
				
				L.pushNumber(fPurchase.getPurchaseTime());
				L.setField(-2, "date");

				String state = null;
				switch(fPurchase.getPurchaseState()) {
					case Purchased: 
						state = "purchased";
						break;
					case Cancelled: 
						state = "cancelled";
						break;
					case Refunded: 
						state = "refunded";
						break;
					case Consumed: 
						state = "consumed";
						break;
					default: 
						state = "unknown";
				}
				L.pushString(state);
				L.setField(-2, "state");

				L.pushString(fPurchase.getToken());
				L.setField(-2, "token");

				L.pushString(fPurchase.getOriginalJson());
				L.setField(-2, "originalJson");

				L.pushString(fPurchase.getSignature());
				L.setField(-2, "signature");

				L.setField(-2, "transaction");
			}

			// Dispatch event table at top of stack
			CoronaLua.dispatchEvent(L, fListener, 0);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
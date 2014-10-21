// This corresponds to the name of the Lua library,
// e.g. [Lua] require "plugin.library"
package plugin.google.iap.v3;

import android.content.Context;
import android.util.Log;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import plugin.google.iap.v3.util.IabHelper;
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

public class PurchaseFinishedListener implements IabHelper.OnIabPurchaseFinishedListener {
	// This listener is needed for the Adrally plugin so that we can register a successful in app purchase
	private static CopyOnWriteArrayList<IabHelper.OnIabPurchaseFinishedListener> sListeners;

	static {
		sListeners = new CopyOnWriteArrayList<IabHelper.OnIabPurchaseFinishedListener>();
	}

	public static void addListener(IabHelper.OnIabPurchaseFinishedListener listener) {
		sListeners.add(listener);
	}

	public static void removeListener(IabHelper.OnIabPurchaseFinishedListener listener) {
		sListeners.remove(listener);
	}

	private CoronaRuntimeTaskDispatcher fDispatcher;
	private int fListener;

	public PurchaseFinishedListener(CoronaRuntimeTaskDispatcher dispatcher, int listener) {
		fDispatcher = dispatcher;
		fListener = listener;
	}

	@Override
	public void onIabPurchaseFinished(IabResult result, Purchase info) {
		Iterator<IabHelper.OnIabPurchaseFinishedListener> i = sListeners.iterator();
		IabHelper.OnIabPurchaseFinishedListener listener;
		while(i.hasNext()) {
			listener = i.next();
			listener.onIabPurchaseFinished(result, info);
		}

		StoreTransactionRuntimeTask task = new StoreTransactionRuntimeTask(info, result, fListener);
		// Send the above task to the Corona runtime asynchronously.
		// The send() method will do nothing if the Corona runtime is no longer available, which can
		// happen if the runtime was disposed/destroyed after the user has exited the Corona activity.
		fDispatcher.send(task);
	}
}
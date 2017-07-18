// This corresponds to the name of the Lua library,
// e.g. [Lua] require "plugin.library"
package plugin.google.iap.v3;

import android.content.Context;
import android.util.Log;

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

public class InitRuntimeTask implements CoronaRuntimeTask {

	private int fListener;
	private int fLibRef;
	private IabResult fResult;
	private IabHelper fHelper;
	private boolean DEBUG = false;

	public InitRuntimeTask(IabHelper helper, IabResult result, int listener, int libRef) {
		fHelper = helper;
		fResult = result;
		fListener = listener;
		fLibRef = libRef;
	}

	@Override
	public void executeUsing(com.ansca.corona.CoronaRuntime runtime) {
		if (fListener == CoronaLua.REFNIL) {
			return;
		}

		if (DEBUG) {
			Thread t = Thread.currentThread();
			Log.w("Corona", "InitRuntimeTask: thread id: " + t.getId());
		}

		// *** We are now running on the Corona runtime thread. ***
		LuaState L = runtime.getLuaState();
		try {
			// Set the store attributes
			L.rawGet(LuaState.REGISTRYINDEX, fLibRef);

			L.pushBoolean(fResult.isSuccess());
			L.setField(-2, "isActive");

			L.pushBoolean(fHelper.subscriptionsSupported());
			L.setField(-2, "canPurchaseSubscriptions");

			L.pop(1);

			String state = "";
			CoronaLua.newEvent( L, "init");

			L.newTable();
			if (fResult.isFailure()) {
				L.pushBoolean(true);
				L.setField(-2, "isError");

				L.pushNumber(fResult.getResponse());
				L.setField(-2, "errorType");

				L.pushString(fResult.getMessage());
				L.setField(-2, "errorString");
			} else {
				L.pushBoolean(false);
				L.setField(-2, "isError");
			}

			L.pushString("initialized");
			L.setField(-2, "state");

			L.setField(-2, "transaction");

			// Dispatch event table at top of stack
			CoronaLua.dispatchEvent(L, fListener, 0);
		}
		catch (Exception ex) {
			Log.e("Corona", "InitRuntimeTask: dispatching Google IAP init event", ex);
		}
	}
}

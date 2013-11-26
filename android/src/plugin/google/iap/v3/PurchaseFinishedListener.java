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

public class PurchaseFinishedListener implements IabHelper.OnIabPurchaseFinishedListener {
	private CoronaRuntimeTaskDispatcher fDispatcher;
	private int fListener;

	public PurchaseFinishedListener(CoronaRuntimeTaskDispatcher dispatcher, int listener) {
		fDispatcher = dispatcher;
		fListener = listener;
	}

	@Override
	public void onIabPurchaseFinished(IabResult result, Purchase info) {
		StoreTransactionRuntimeTask task = new StoreTransactionRuntimeTask(info, fListener);
		// Send the above task to the Corona runtime asynchronously.
		// The send() method will do nothing if the Corona runtime is no longer available, which can
		// happen if the runtime was disposed/destroyed after the user has exited the Corona activity.
		fDispatcher.send(task);
	}
}
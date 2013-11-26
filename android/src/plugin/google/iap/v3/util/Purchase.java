/* Copyright (c) 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package plugin.google.iap.v3.util;

import org.json.JSONException;
import org.json.JSONObject;

import com.naef.jnlua.LuaState;
/**
 * Represents an in-app billing purchase.
 */
public class Purchase {
    public enum State {
        Purchased,
        Cancelled,
        Refunded,
        Consumed,
        Unknown
    }

    String mItemType;  // ITEM_TYPE_INAPP or ITEM_TYPE_SUBS
    String mOrderId;
    String mPackageName;
    String mSku;
    long mPurchaseTime;
    State mPurchaseState;
    String mDeveloperPayload;
    String mToken;
    String mOriginalJson;
    String mSignature;

    public Purchase(String itemType, String jsonPurchaseInfo, String signature) throws JSONException {
        mItemType = itemType;
        mOriginalJson = jsonPurchaseInfo;
        JSONObject o = new JSONObject(mOriginalJson);
        mOrderId = o.optString("orderId");
        mPackageName = o.optString("packageName");
        mSku = o.optString("productId");
        mPurchaseTime = o.optLong("purchaseTime");
        mDeveloperPayload = o.optString("developerPayload");
        mToken = o.optString("token", o.optString("purchaseToken"));
        mSignature = signature;

        int state = o.optInt("purchaseState");
            switch(state) {
                case 0: mPurchaseState = State.Purchased;
                break;
                case 1: mPurchaseState = State.Cancelled;
                break;
                case 2: mPurchaseState = State.Refunded;
                break;
                default: mPurchaseState = State.Unknown;
            }

    }

    public void setPurchaseState(State purchaseState) { mPurchaseState = purchaseState; }
    public String getItemType() { return mItemType; }
    public String getOrderId() { return mOrderId; }
    public String getPackageName() { return mPackageName; }
    public String getSku() { return mSku; }
    public long getPurchaseTime() { return mPurchaseTime; }
    public State getPurchaseState() { return mPurchaseState; }
    public String getDeveloperPayload() { return mDeveloperPayload; }
    public String getToken() { return mToken; }
    public String getOriginalJson() { return mOriginalJson; }
    public String getSignature() { return mSignature; }

    @Override
    public String toString() { return "PurchaseInfo(type:" + mItemType + "):" + mOriginalJson; }
}

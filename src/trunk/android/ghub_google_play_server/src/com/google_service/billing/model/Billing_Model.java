package com.google_service.billing.model;

import java.util.Dictionary;
import java.util.List;

import android.content.Intent;
import android.app.Activity;
import java.util.Hashtable;

import com.github.aadt.kernel.mvp.Model;
import com.github.aadt.kernel.util.Logger;
import com.google_service.billing.model.event.*;
import com.google_service.billing.util.IabHelper;
import com.google_service.billing.util.IabResult;
import com.google_service.billing.util.Inventory;
import com.google_service.billing.util.Purchase;

public class Billing_Model extends Model {
  // (arbitrary) request code for the purchase flow
  static final int RC_REQUEST = 10001;

  private Activity activity;
  private IabHelper mHelper;

  public Billing_Model(String path, Activity ctx) {
    super(path);
    this.activity = ctx;
  }

  public void start(String license_key) {
    Logger.debug("Creating IAB helper.");
    mHelper = new IabHelper(activity, license_key);
    mHelper.enableDebugLogging(true);
    Logger.debug("Starting setup.");
    add_event_listener(Billing_Model_Event.START_COMPLETE, new Billing_Start_Complete_Handler(activity, this));
    mHelper.startSetup(create_setup_finished_listener());
  }

  private IabHelper.OnIabSetupFinishedListener create_setup_finished_listener() {
    return new IabHelper.OnIabSetupFinishedListener() {
      @Override
      public void onIabSetupFinished(IabResult result) {
        Billing_Model_Event event = new Billing_Model_Event(Billing_Model_Event.START_COMPLETE);
        event.set_data(result);
        dispatch_event(event);
        remove_event_listener(Billing_Model_Event.START_COMPLETE);
      }
    };
  }

  public void purchase(String product_id) {
    Logger.debug("Buy gas button clicked.");
    Logger.debug("Launching purchase flow for gas.");
    String payload = "";
    add_event_listener(Billing_Model_Event.PURCHASE_COMPLETE, new Billing_Purchase_Complete_Handler(activity, this));
    mHelper.launchPurchaseFlow(activity, product_id, RC_REQUEST, mPurchaseFinishedListener, payload);
  }

  private IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
    @Override
    public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
      Billing_Model_Event event = new Billing_Model_Event(Billing_Model_Event.PURCHASE_COMPLETE);
      event.set_data(create_purchase_event_data(result, purchase));
      dispatch_event(event);
      remove_event_listener(Billing_Model_Event.PURCHASE_COMPLETE);
    }
  };

  private Dictionary<String, Object> create_purchase_event_data(IabResult result, Purchase purchase) {
    Dictionary<String, Object> data = new Hashtable<String, Object>();
    data.put("result", result);
    if (purchase != null)
      data.put("purchase", purchase);
    return data;
  }

  public void get_inventory_product() {
    mHelper.queryInventoryAsync(mGotInventoryListener);
  }

  // Listener that's called when we finish querying the items and subscriptions
  // we own
  private IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
    @Override
    public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
      Logger.debug("Query inventory finished.");
      if (result.isFailure()) {
        Logger.complain(activity, "Failed to query inventory: " + result);
        return;
      }

      Logger.debug("Query inventory was successful.");
      List<Purchase> purchases = inventory.getAllPurchases();
      Billing_Model_Event event = new Billing_Model_Event(Billing_Model_Event.GET_INVENTORY_COMPLETE);
      event.set_data(create_get_inventory_data(purchases));
      dispatch_event(event);
    }
  };
  
  private Dictionary<String, Object> create_get_inventory_data(List<Purchase> purchases) {
    Dictionary<String, Object> data = new Hashtable<String, Object>();
    data.put("purchases", purchases);
    return data;
  }

  public void consume(Purchase purchase) {
    add_event_listener(Billing_Model_Event.CONSUME_COMPLETE, new Billing_Consume_Complete_Handler(activity, this));
    mHelper.consumeAsync(purchase, mConsumeFinishedListener);
  }

  // Called when consumption is complete
  private IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
    @Override
    public void onConsumeFinished(Purchase purchase, IabResult result) {
      Billing_Model_Event event = new Billing_Model_Event(Billing_Model_Event.CONSUME_COMPLETE);
      event.set_data(create_consume_event_data(result, purchase));
      dispatch_event(event);
      remove_event_listener(Billing_Model_Event.CONSUME_COMPLETE);
    }
  };

  private Dictionary<String, Object> create_consume_event_data(IabResult result, Purchase purchase) {
    Dictionary<String, Object> data = new Hashtable<String, Object>();
    data.put("result", result);
    if (purchase != null)
      data.put("purchase", purchase);
    return data;
  }

  public void stop() {
    Logger.debug("Destroying helper.");
    if (mHelper != null) {
      mHelper.dispose();
      mHelper = null;
    }
  }

  public boolean activity_result(int requestCode, int resultCode, Intent data) {
    Logger.debug("onActivityResult(" + requestCode + "," + resultCode + "," + data);

    // Pass on the activity result to the helper for handling
    return mHelper.handleActivityResult(requestCode, resultCode, data);
  }
}

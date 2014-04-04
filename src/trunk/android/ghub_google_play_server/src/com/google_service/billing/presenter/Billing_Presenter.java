package com.google_service.billing.presenter;

import java.util.Dictionary;
import java.util.Hashtable;
import android.content.Intent;

import com.gdkompanie.gdos.mvp.Presenter;
import com.google_service.billing.model.Billing_Model;
import com.google_service.billing.model.event.Billing_Model_Event;
import com.google_service.billing.presenter.event.Billing_Activity_Result_Handler;
import com.google_service.billing.presenter.event.Billing_Consume_Complete_Handler;
import com.google_service.billing.presenter.event.Billing_Get_Inventory;
import com.google_service.billing.presenter.event.Billing_Presenter_Event;
import com.google_service.billing.presenter.event.Billing_Purchase_Handler;
import com.google_service.billing.presenter.event.Billing_Start_Handler;
import com.google_service.billing.presenter.event.Billing_Stop_Handler;
import com.google_service.billing.util.Purchase;
import com.google_service.billing.view.Billing_View;

public class Billing_Presenter extends Presenter {
  private Billing_Model model;
  private Billing_View view;

  public Billing_Presenter(Billing_View view, Billing_Model model) {
    this.view = view;
    this.model = model;
    register_events();
  }

  private void register_events() {
    add_event_listener(Billing_Presenter_Event.ACTIVITY_RESULT, new Billing_Activity_Result_Handler(view, model));
    add_event_listener(Billing_Presenter_Event.START, new Billing_Start_Handler(model, this));
    add_event_listener(Billing_Presenter_Event.STOP, new Billing_Stop_Handler(model));
    add_event_listener(Billing_Presenter_Event.PURCHASE, new Billing_Purchase_Handler(view, model, this));
    add_event_listener(Billing_Presenter_Event.GET_INVENTORY, new Billing_Get_Inventory(model, this));
  }

  public void start(String license_key) {
    Billing_Presenter_Event event = new Billing_Presenter_Event(Billing_Presenter_Event.START);
    event.set_data(create_start_event_data(license_key));
    dispatch_event(event);
  }

  private Dictionary<String, Object> create_start_event_data(String license_key) {
    Dictionary<String, Object> result = new Hashtable<String, Object>();
    result.put("license_key", license_key);
    return result;
  }

  public void stop() {
    Billing_Presenter_Event event = new Billing_Presenter_Event(Billing_Presenter_Event.STOP);
    dispatch_event(event);
  }

  public void purchase(String product_id) {
    Billing_Presenter_Event event = new Billing_Presenter_Event(Billing_Presenter_Event.PURCHASE);
    event.set_data(create_purchase_event_data(product_id));
    dispatch_event(event);
  }
  
  public void consume(Purchase purchase) {
    model.remove_event_listener(Billing_Model_Event.CONSUME_COMPLETE);
    model.add_event_listener(Billing_Model_Event.CONSUME_COMPLETE, new Billing_Consume_Complete_Handler(this));
    model.consume(purchase);
  }

  private Dictionary<String, Object> create_purchase_event_data(String product_id) {
    Dictionary<String, Object> data = new Hashtable<String, Object>();
    data.put("product_id", product_id);
    return data;
  }
  
  public void get_inventory_product() {
    Billing_Presenter_Event event = new Billing_Presenter_Event(Billing_Presenter_Event.GET_INVENTORY);
    dispatch_event(event);
  }

  public void activity_result(int request_code, int result_code, Intent data) {
    Billing_Presenter_Event event = new Billing_Presenter_Event(Billing_Presenter_Event.ACTIVITY_RESULT);
    event.set_data(create_activity_result_event_data(request_code, result_code, data));
    dispatch_event(event);
  }

  private Dictionary<String, Object> create_activity_result_event_data(int request_code,
      int result_code, Intent intent_data) {
    Dictionary<String, Object> data = new Hashtable<String, Object>();
    data.put("request_code", request_code);
    data.put("result_code", result_code);
    if (null != intent_data)
      data.put("intent_data", intent_data);
    return data;
  }
}

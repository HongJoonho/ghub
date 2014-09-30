package com.tstore_service.billing.presenter;

import com.github.aadt.kernel.mvp.Presenter;
import com.github.aadt.kernel.util.Logger;
import com.tstore_service.billing.model.Billing_Model;
import com.tstore_service.billing.model.dto.Consume_Complete_DTO;
import com.tstore_service.billing.model.dto.Purchase_Complete_DTO;
import com.tstore_service.billing.model.event.Billing_Model_Event;
import com.tstore_service.billing.presenter.event.Billing_Consume_Complete_Handler;
import com.tstore_service.billing.presenter.event.Billing_Purchase_Complete_Handler;
import com.tstore_service.billing.presenter.event.Billing_Get_Purchase_Products_Complete_Handler;
import com.tstore_service.billing.presenter.event.Billing_Start_Complete_Handler;
import com.tstore_service.billing.view.Billing_View;

public class Billing_Presenter extends Presenter {
  private Billing_View view;
  private Billing_Model model;
  
  public Billing_Presenter(String path, Billing_View view, Billing_Model model) {
    super(path);
    this.view = view;
    this.model = model;
  }
  
  public void start(String app_id) {
    model.remove_event_listener(Billing_Model_Event.START_COMPLETE);
    model.add_event_listener(Billing_Model_Event.START_COMPLETE, new Billing_Start_Complete_Handler(this));
    model.start(app_id);
  }
  
  public void stop() {
    model.stop();
  }
  
  public void purchase(String product_id) {
    model.remove_event_listener(Billing_Model_Event.PURCHASE_COMPLETE);
    model.add_event_listener(Billing_Model_Event.PURCHASE_COMPLETE, new Billing_Purchase_Complete_Handler(this, view));
    model.purchase(product_id);
  }
  
  public void consume(String product_id, Purchase_Complete_DTO purchase) {
    model.remove_event_listener(Billing_Model_Event.CONSUME_COMPLETE);
    model.add_event_listener(Billing_Model_Event.CONSUME_COMPLETE, new Billing_Consume_Complete_Handler(this, purchase));
    model.consume(product_id);
  }
  
  public void get_unused_products() {
    model.remove_event_listener(Billing_Model_Event.GET_PURCHASE_PRODUCTS_COMPLETE);
    model.add_event_listener(Billing_Model_Event.GET_PURCHASE_PRODUCTS_COMPLETE, new Billing_Get_Purchase_Products_Complete_Handler(this));
    model.get_purchase_products();
  }
}

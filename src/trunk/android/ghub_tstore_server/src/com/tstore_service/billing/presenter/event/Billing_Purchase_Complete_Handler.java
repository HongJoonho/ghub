package com.tstore_service.billing.presenter.event;

import java.util.Dictionary;
import java.util.Hashtable;

import android.content.res.Resources;

import com.tstore_service.R;
import com.github.aadt.kernel.event.Event;
import com.github.aadt.kernel.event.Event_Handler;
import com.skplanet.dev.guide.pdu.Response.Product;
import com.tstore_service.billing.model.Billing_Model;
import com.tstore_service.billing.model.dto.Purchase_Complete_DTO;
import com.tstore_service.billing.presenter.Billing_Presenter;
import com.tstore_service.billing.view.Billing_View;

/**
 * 모델객체는 purchase에 대한 성공/실패 이벤트만을 발생시키고 성공한 경우 Presenter에 의해 consume을 바로 요청
 * @author maayalee
 */
public class Billing_Purchase_Complete_Handler extends Event_Handler {
  private Billing_Presenter presenter;
  private Billing_View view;

  public Billing_Purchase_Complete_Handler(Billing_Presenter presenter, Billing_View view) {
    this.presenter = presenter;
    this.view = view;
  }
  
  @Override
  public void dispatch(Event event) {
    Hashtable<String, Object> data = (Hashtable<String, Object>) event.get_data();
    Boolean is_success = (Boolean)data.get("is_success");
    if (is_success) {
      String result_code = (String)data.get("result_code");
      Purchase_Complete_DTO result = (Purchase_Complete_DTO)data.get("result");
      consume_product(result);
    }
    else {
      String error_code = (String)data.get("error_code");
    }
    occur_purchase_complete_event(event.get_data());
  }
  
  private void occur_purchase_complete_event(Object data) {
    Billing_Presenter_Event complete_event = new Billing_Presenter_Event(Billing_Presenter_Event.PURCHASE_COMPLETE);
    complete_event.set_data(data);
    presenter.dispatch_event(complete_event);
  }
  
  private void consume_product(Purchase_Complete_DTO result) {
    try {
      Product product = result.get_first_product();
      presenter.consume(product.id, result);
    } catch (Exception e) {
      Resources resources = view.get_resources();
      String message = resources.getString(R.string.tstore_consume_product_failed);
      view.display_alert(message);
    }
  }
}

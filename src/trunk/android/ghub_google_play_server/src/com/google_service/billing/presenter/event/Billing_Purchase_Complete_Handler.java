package com.google_service.billing.presenter.event;

import java.util.Dictionary;
import java.util.Hashtable;

import android.content.res.Resources;

import com.gdkompanie.gdos.event.Event;
import com.gdkompanie.gdos.event.Event_Handler;
import com.gdkompanie.gdos.util.Logger;
import com.google_service.R;
import com.google_service.billing.presenter.Billing_Presenter;
import com.google_service.billing.util.IabHelper;
import com.google_service.billing.util.IabResult;
import com.google_service.billing.util.Purchase;
import com.google_service.billing.view.Billing_View;

/**
 * Billing_Model이 Purchase_Complete 시에 발생시키는 이벤트 핸들러. 구매에 성공하면 해당 아이템을 바로
 * 사용처리한다.
 * 
 * @author maayalee
 * 
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
    Dictionary<String, Object> data = (Dictionary<String, Object>) event.get_data();
    IabResult result = (IabResult) data.get("result");
    Purchase purchase = null;
    if (null != data.get("purchase"))
      purchase = (Purchase) data.get("purchase");

    Billing_Presenter_Event complete_event = new Billing_Presenter_Event(Billing_Presenter_Event.PURCHASE_COMPLETE);
    complete_event.set_data(create_complete_data(purchase, result));
    presenter.dispatch_event(complete_event);

    if (result.isSuccess()) {
      presenter.consume(purchase);
    }
  }

  private Dictionary<String, Object> create_complete_data(Purchase purchase, IabResult purchase_result) {
    Dictionary<String, Object> result = new Hashtable<String, Object>();
    if (null != purchase)
      result.put("purchase", purchase);
    result.put("result", purchase_result);
    return result;
  }
}

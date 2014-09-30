package com.tstore_service.billing.model;


import java.util.Hashtable;
import android.app.Activity;
import android.os.Bundle;

import com.github.aadt.kernel.mvp.Model;
import com.github.aadt.kernel.util.Logger;
import com.skplanet.dev.guide.helper.ConverterFactory;
import com.skplanet.dev.guide.pdu.Response;
import com.skplanet.dodo.IapPlugin;
import com.skplanet.dodo.IapResponse;
import com.skplanet.iap.unity.CommandRequestUtil;
import com.skplanet.iap.unity.PaymentRequestUtil;
import com.tstore_service.billing.model.dto.Consume_Complete_DTO;
import com.tstore_service.billing.model.dto.Purchase_Complete_DTO;
import com.tstore_service.billing.model.dto.Get_Purchase_Products_Complete_DTO;
import com.tstore_service.billing.model.event.Billing_Model_Event;

public class Billing_Model extends Model {
  public static String PLUGIN_MODE_DEVELOPEMNT = "development";
  public static String PLUGIN_MODE_RELEASE = "release";

  private String plugin_mode;
  private Activity activity;
  private String app_id;
  private int ret_code;
  private IapPlugin plugin;
  private CommandRequestUtil mCommandRequestUtil = new CommandRequestUtil();
  private PaymentRequestUtil mPaymentRequestUtil = new PaymentRequestUtil();

  public Billing_Model(Activity activity, String plugin_mode) {
    this.activity = activity;
    this.plugin_mode = plugin_mode;
    Logger.debug("Billing_Model::getPlugin");
    plugin = IapPlugin.getPlugin(activity, plugin_mode);
  }

  public void start(String app_id) {
    this.app_id = app_id;
    occur_state_complete_event();
  }

  private void occur_state_complete_event() {
    Billing_Model_Event event = new Billing_Model_Event(Billing_Model_Event.START_COMPLETE);
    dispatch_event(event);
  }

  public void stop() {
    plugin.exit();
  }

  public int purchase(String product_id) {
    ret_code = 0;
    String parameter = mPaymentRequestUtil.makePaymentRequest(app_id, product_id, "", "", "");
    Bundle req = plugin.sendPaymentRequest(parameter, create_request_callback());
    if (req == null) {
      // 구매 요청 실패 처리
      ret_code = -1;
      return ret_code;
    }
    String mRequestId = req.getString(IapPlugin.EXTRA_REQUEST_ID);
    if (mRequestId == null || mRequestId.length() == 0) {
      // 구매 요청 실패 처리
      ret_code = -1;
      return ret_code;
    }
    return ret_code;
  }

  private IapPlugin.RequestCallback create_request_callback() {
    return new IapPlugin.RequestCallback() {
      @Override
      public void onResponse(IapResponse data) {
        if (data == null || data.getContentLength() <= 0) {
          occur_purchase_fail_event("", "purchase onResponse() response data is null");
          return;
        }
        // 1. 응답값이 null이 아닌경우 JSON 데이터를 통해 객체를 변환
        Response response = ConverterFactory.getConverter().fromJson(data.getContentToString());
        if (response == null) {
          occur_purchase_fail_event("", "purchase onResponse() invalid response data");
          return;
        }

        // 응답 코드 검증
        if (!response.result.code.equals("0000")) {
          // 구매 실패. 취소버튼이나 뒤로가기 시도시에 여기에 진입된다.
          occur_purchase_fail_event(response.result.code, "purchase Failed to request to purchase a item");
          return;
        }

        // 구매 성공, 전자영수증 검증
        Logger.debug("purchase success");
        occur_purchase_success_event(create_purchase_dto(response.result));
      }

      @Override
      public void onError(String reqid, String errcode, String errmsg) {
        ret_code = -1;
        occur_purchase_fail_event(errcode, "purchase error");
      }
    };
  }

  private Purchase_Complete_DTO create_purchase_dto(Response.Result response_result) {
    Purchase_Complete_DTO result = new Purchase_Complete_DTO(app_id, response_result);
    return result;
  }

  private void occur_purchase_fail_event(String result_code, String result_message) {
    Logger.debug(result_message);
    Billing_Model_Event event = new Billing_Model_Event(Billing_Model_Event.PURCHASE_COMPLETE);
    event.set_data(create_purchase_fail_event_data(result_code, result_message));
    dispatch_event(event);
  }

  private Hashtable<String, Object> create_purchase_fail_event_data(String result_code, String result_message) {
    Hashtable<String, Object> result = new Hashtable<String, Object>();
    result.put("is_success", false);
    result.put("error_code", result_code);
    result.put("error_message", result_message);
    return result;
  }

  private void occur_purchase_success_event(Purchase_Complete_DTO dto) {
    Billing_Model_Event event = new Billing_Model_Event(Billing_Model_Event.PURCHASE_COMPLETE);
    event.set_data(create_purchase_success_event_data(dto));
    dispatch_event(event);
  }

  private Hashtable<String, Object> create_purchase_success_event_data(Purchase_Complete_DTO dto) {
    Hashtable<String, Object> result = new Hashtable<String, Object>();
    result.put("is_success", true);
    result.put("result", dto);
    return result;
  }

  public void consume(String product_id) {
    command(product_id, "change_product_properties", 1, create_consume_callback());
  }

  public void get_purchase_products() {
    command_purchase_history("request_purchase_history", create_unused_products_callback());
  }

  private IapPlugin.RequestCallback create_consume_callback() {
    return new IapPlugin.RequestCallback() {
      @Override
      public void onResponse(IapResponse data) {
        if (data == null || data.getContentLength() == 0) {
          // TODO Unusual error
          return;
        }
        // TODO convert json string to object
        Response response = ConverterFactory.getConverter().fromJson(data.getContentToString());
        StringBuffer sb = new StringBuffer("consume() \n");
        sb.append("From:" + data.getContentToString()).append("\n").append("To:" + response.toString());
        occur_consume_success_event(create_consume_complete_dto(response.result));
      }

      @Override
      public void onError(String req_id, String error_code, String error_message) {
        Logger.debug("consume command error:" + error_code);
        occur_consume_fail_event(error_code, error_message);
      }
    };
  }
  
  private void occur_consume_success_event(Consume_Complete_DTO dto) {
    Billing_Model_Event event = new Billing_Model_Event(Billing_Model_Event.CONSUME_COMPLETE);
    event.set_data(create_consume_success_event_data(dto));
    dispatch_event(event);
  }

  private Hashtable<String, Object> create_consume_success_event_data(Consume_Complete_DTO dto) {
    Hashtable<String, Object> result = new Hashtable<String, Object>();
    result.put("is_success", true);
    result.put("result", dto);
    return result;
  }
  
  private Consume_Complete_DTO create_consume_complete_dto(Response.Result response_result) {
    Consume_Complete_DTO result = new Consume_Complete_DTO(response_result);
    return result;
  }
  
  private void occur_consume_fail_event(String error_code, String error_message) {
    Billing_Model_Event event = new Billing_Model_Event(Billing_Model_Event.CONSUME_COMPLETE);
    event.set_data(create_consume_fail_event_data(error_code, error_message));
    dispatch_event(event);
  }

  private Hashtable<String, Object> create_consume_fail_event_data(String error_code, String error_message) {
    Hashtable<String, Object> result = new Hashtable<String, Object>();
    result.put("is_success", false);
    result.put("error_code", error_code);
    result.put("error_message", error_message);
    return result;
  }

  private IapPlugin.RequestCallback create_unused_products_callback() {
    return new IapPlugin.RequestCallback() {
      @Override
      public void onResponse(IapResponse data) {
        if (data == null || data.getContentLength() == 0) {
          // TODO Unusual error
          return;
        }
        // TODO convert json string to object
        Response response = ConverterFactory.getConverter().fromJson(data.getContentToString());
        StringBuffer sb = new StringBuffer("get_unused_products_callback() \n");
        sb.append("From:" + data.getContentToString()).append("\n").append("To:" + response.toString());
        occur_get_unused_product_success_event(create_get_unused_products_complete_dto(response.result));
      }

      @Override
      public void onError(String req_id, String error_code, String error_message) {
        Logger.debug("consume command error:" + error_code);
        occur_get_unused_products_fail_event(error_code, error_message);
      }
    };
  }
  
  private void occur_get_unused_product_success_event(Get_Purchase_Products_Complete_DTO dto) {
    Billing_Model_Event event = new Billing_Model_Event(Billing_Model_Event.GET_PURCHASE_PRODUCTS_COMPLETE);
    event.set_data(create_get_unused_product_success_event_data(dto));
    dispatch_event(event);
  }

  private Hashtable<String, Object> create_get_unused_product_success_event_data(Get_Purchase_Products_Complete_DTO dto) {
    Hashtable<String, Object> result = new Hashtable<String, Object>();
    result.put("is_success", true);
    result.put("result", dto);
    return result;
  }
  
  private Get_Purchase_Products_Complete_DTO create_get_unused_products_complete_dto(Response.Result response_result) {
    Get_Purchase_Products_Complete_DTO result = new Get_Purchase_Products_Complete_DTO(response_result);
    return result;
  }
  
  private void occur_get_unused_products_fail_event(String error_code, String error_message) {
    Billing_Model_Event event = new Billing_Model_Event(Billing_Model_Event.GET_PURCHASE_PRODUCTS_COMPLETE);
    event.set_data(create_get_unused_products_fail_event_data(error_code, error_message));
    dispatch_event(event);
  }

  private Hashtable<String, Object> create_get_unused_products_fail_event_data(String error_code, String error_message) {
    Hashtable<String, Object> result = new Hashtable<String, Object>();
    result.put("is_success", false);
    result.put("error_code", error_code);
    result.put("error_message", error_message);
    return result;
  }

  public int command(String productid, String method, int cancel_flag,
      IapPlugin.RequestCallback callback) {
    String parameter = mCommandRequestUtil.makeCommandRequest(app_id, productid, method,
        cancel_flag);
    Logger.debug("Billing_Model::command::parameter is " + parameter);
    Bundle req = plugin.sendCommandRequest(parameter, callback);
    if (req == null) {
      // Command 요청 실패 처리
      Logger.debug("Billing_Model::command::req is null");
      return -1;
    }

    String id = req.getString(IapPlugin.EXTRA_REQUEST_ID);
    if (id == null || id.length() == 0) {
      // Command 요청 실패 처리
      Logger.debug("Billing_Model::command::id  is null or length is 0");
      return -1;
    }
    return 0;
  }
  
  public int command_purchase_history(String method, IapPlugin.RequestCallback callback) {
    String parameter = mCommandRequestUtil.makePurchasHistoryRequest(app_id, method);
    Logger.debug("Billing_Model::command_purchase_history::parameter is " + parameter);
    Bundle req = plugin.sendCommandRequest(parameter, callback);
    if (req == null) {
      // Command 요청 실패 처리
      Logger.debug("Billing_Model::command_purchase_history::req is null");
      return -1;
    }

    String id = req.getString(IapPlugin.EXTRA_REQUEST_ID);
    if (id == null || id.length() == 0) {
      // Command 요청 실패 처리
      Logger.debug("Billing_Model::command_purchase_history::id  is null or length is 0");
      return -1;
    }
    return 0;
    
  }
}

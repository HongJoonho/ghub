package com.tstore_service.billing.model.dto;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.github.aadt.kernel.util.Logger;
import com.skplanet.dev.guide.pdu.Response;
import com.skplanet.dev.guide.pdu.Response.Product;
import com.unity3d.player.UnityPlayer;

/**
 * Purchase 제품 구매 성공시에 응답해주는 데이터
 * @author maayalee
 *
 */
public class Purchase_Complete_DTO {
  private String app_id;
  private Response.Result result;
  
  public Purchase_Complete_DTO(String app_id, Response.Result result) {
    this.app_id = app_id;
    this.result = result;
  }
  
  public String get_app_id() {
    return app_id;
  }

  public String get_tex_id() {
    return result.txid;
  }
  
  public List<Product> get_products() {
    return result.product;
  }
  
  public String get_sign_data() {
    return result.receipt;
  }
  
  public Product get_first_product() throws Exception{
    if (0 == result.product.size())
      throw new Exception("products size is 0");
    return result.product.get(0);
  }

  public String to_json_string() throws Exception {
    try {
      JSONObject json = new JSONObject();
      json.put("appid", app_id);
      json.put("txid", get_tex_id());
      json.put("signdata", get_sign_data());
      json.put("products", get_products());
      return json.toString();
    } catch (JSONException e) {
      // 전자영수증 데이터 준비 실패
      e.printStackTrace();
      throw new Exception("Purchase_Complete_DTO data is wrong");
    }
  }
}

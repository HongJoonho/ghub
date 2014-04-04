package com.tstore_service.billing.model.dto;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.gdkompanie.gdos.util.Logger;
import com.skplanet.dev.guide.pdu.Response;
import com.skplanet.dev.guide.pdu.Response.Product;
import com.unity3d.player.UnityPlayer;

/**
 * Purchase 사용하지 않는 제품 리스트 조회 성공시에 응답해주는 데이터
 * @author maayalee
 *
 */
public class Get_Purchase_Products_Complete_DTO {
  private Response.Result result;
  
  public Get_Purchase_Products_Complete_DTO(Response.Result result) {
    this.result = result;
  }
  
  public List<Product> get_products() {
    return result.product;
  }

  public int get_count() {
    return result.count;
  }
  
  public Product get_first_product() throws Exception{
    if (0 == result.product.size())
      throw new Exception("products size is 0");
    return result.product.get(0);
  }

  public String to_json_string() throws Exception {
    try {
      JSONObject json = new JSONObject();
      json.put("count", get_count());
      json.put("products", get_products());
      return json.toString();
    } catch (JSONException e) {
      e.printStackTrace();
      throw new Exception("Get_Unused_Products_Complete_DTO data is wrong");
    }
  }
}

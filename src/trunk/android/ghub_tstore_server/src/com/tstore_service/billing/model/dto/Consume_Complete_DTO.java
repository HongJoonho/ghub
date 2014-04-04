package com.tstore_service.billing.model.dto;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.gdkompanie.gdos.util.Logger;
import com.skplanet.dev.guide.pdu.Response;
import com.skplanet.dev.guide.pdu.Response.Product;
import com.unity3d.player.UnityPlayer;

/**
 * Purchase 제품 사용 성공시에 응답해주는 데이터
 * @author maayalee
 *
 */
public class Consume_Complete_DTO {
  private Response.Result result;
  
  public Consume_Complete_DTO(Response.Result result) {
    this.result = result;
  }
  
  /**
   * 이번에 사용한 제품의 갯수를 리턴
   * @return 제품 수
   */
  public int get_consume_count() {
    return result.count;
  }
  
  /**
   * 사용 가능한 제품의 갯수를 리턴
   * @return 제품 수
   */
  public int get_remain_count() {
    try {
      Product product = get_first_product();
      return product.validity; 
    } catch (Exception e) {
      Logger.debug("Consume_Complete_DTO::get_remain_count::exception::" + e.getMessage());
    }
    return 0;
  }
  
  public List<Product> get_products() {
    return result.product;
  }
  
  public Product get_first_product() throws Exception{
    if (0 == result.product.size())
      throw new Exception("products size is 0");
    return result.product.get(0);
  }

  public String to_json_string() throws Exception {
    try {
      JSONObject json = new JSONObject();
      json.put("consume_count", get_consume_count());
      json.put("remain_count", get_remain_count());
      json.put("products", get_products());
      return json.toString();
    } catch (JSONException e) {
      // 전자영수증 데이터 준비 실패
      e.printStackTrace();
      throw new Exception("Consume_Complete_DTO data is wrong");
    }
  }
}

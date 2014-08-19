package com.google_service.notify.model.dto;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.github.aadt.kernel.util.Logger;

/**
 * Purchase 구글서버에 기기 등록 성공했을 경우에 날라오는 데이터를 래핑해주는 클래스
 * @author maayalee
 *
 */
public class Register_Complete_DTO {
  private String register_id;
  
  public Register_Complete_DTO(String register_id) {
    this.register_id = register_id;
  }
  
  public String get_register_id() {
    return register_id;
  }
  
  public String to_json_string() throws Exception {
    try {
      JSONObject json = new JSONObject();
      json.put("register_id", register_id);
      return json.toString();
    } catch (JSONException e) {
      e.printStackTrace();
      throw new Exception("Register_Complete_DTO data is wrong");
    }
  }
}

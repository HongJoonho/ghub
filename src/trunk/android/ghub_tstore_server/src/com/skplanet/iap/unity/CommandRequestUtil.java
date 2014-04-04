package com.skplanet.iap.unity;

import android.os.Bundle;
import android.view.View.OnCreateContextMenuListener;

import com.skplanet.dev.guide.helper.CommandBuilder;
import com.skplanet.dev.guide.pdu.Command;

public class CommandRequestUtil {

	CommandBuilder mBuilder;
	
	public CommandRequestUtil() {
		mBuilder = new CommandBuilder();
	}
	
	  public String makePurchasHistoryRequest(String app_id, String method) {
	    if (!Command.request_purchase_history.method().equals(method))
	      return null;
	    return mBuilder.requestPurchaseHistory(app_id);
	  }
	
    public String makeCommandRequest(String appid, String productid, String method, int change_flag) {
        if (Command.request_product_info.method().equals(method)) {
            return mBuilder.requestProductInfo(appid);
        }
        if (Command.request_purchase_history.method().equals(method)) {
            return mBuilder.requestPurchaseHistory(appid, productid);
        }
        if (Command.change_product_properties.method().equals(method)) {
        	
        	if (change_flag == 0) {
        		return mBuilder.changeProductProperties(appid, "cancel_subscription", productid);
        	} else if (change_flag == 1) {
        		return mBuilder.changeProductProperties(appid, "subtract_points", productid);
        	} else {
        		return mBuilder.changeProductProperties(appid, "cance_subscription", productid);
        	}
        }
        if (Command.check_purchasability.method().equals(method)) {
            return mBuilder.checkPurchasability(appid, productid);
        }
        if (Command.auth_item.method().equals(method)) {
            return mBuilder.authItem(appid, productid);
        }
        if (Command.whole_auth_item.method().equals(method)) {
            return mBuilder.wholeAuthItem(appid);
        }
        if (Command.item_use.method().equals(method)) {
            return mBuilder.itemUse(appid, productid);
        }
        if (Command.monthly_withdraw.method().equals(method)) {
            return mBuilder.monthlyWithdraw(appid, productid);
        }
        return null;
    }	
	
}

package com.skplanet.iap.unity;

import android.text.TextUtils;

import com.skplanet.dev.guide.helper.CommandBuilder;
import com.skplanet.dev.guide.helper.ParamsBuilder;

public class PaymentRequestUtil {

	CommandBuilder mBuilder;
	
	public PaymentRequestUtil() {
		mBuilder = new CommandBuilder();
	}	
	
    public String makePaymentRequest(String appid, String pid, String pname, String tid, String bpinfo) {
        ParamsBuilder pb = new ParamsBuilder();

        pb.put(ParamsBuilder.KEY_APPID,
        		appid.trim().toUpperCase()).put(
                ParamsBuilder.KEY_PID, pid.trim());

        String tmp = pname;
        if (!TextUtils.isEmpty(tmp)) {
            pb.put(ParamsBuilder.KEY_PNAME, tmp);
        } else {
            pb.put(ParamsBuilder.KEY_PNAME, "");
        }

        tmp = tid;
        if (!TextUtils.isEmpty(tmp)) {
            pb.put(ParamsBuilder.KEY_TID, tmp);
        } else {
            pb.put(ParamsBuilder.KEY_TID, "");
        }

        tmp = bpinfo;
        if (!TextUtils.isEmpty(tmp)) {
            pb.put(ParamsBuilder.KEY_BPINFO, tmp);
        } else {
            pb.put(ParamsBuilder.KEY_BPINFO, tmp);
        }

        return pb.build();
    }	
	
}

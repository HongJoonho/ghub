package com.security_server.model;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ApplicationInfo;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import com.github.aadt.kernel.util.Logger;

public class Install_Application_Model {
  private Context context;
  
  public Install_Application_Model(Context context) {
    this.context = context;
  }
  
  public boolean has_cracking_app() {
    List<String> names = get_install_package_names();
    Iterator<String> iter = names.iterator();
    while (iter.hasNext()) {
      String name = (String)iter.next();
      if (has_cracking_package(name))
        return true;
    }
    return false;
  }
  
  private boolean has_cracking_package(String package_name) {
    List<String> cracking_names = get_cracking_package_names();
    Iterator<String> iter = cracking_names.iterator();
    while (iter.hasNext()) {
      String name = (String)iter.next();
      if (name.equals(package_name))
        return true;
    }
    return false;
  }
  
  private List<String> get_install_package_names() {
    List<String> result = new ArrayList<String>();
    PackageManager packageManager = context.getPackageManager();
    List<ApplicationInfo> applist = packageManager.getInstalledApplications(0);
    Iterator<ApplicationInfo> it=applist.iterator();
    while (it.hasNext()){
        ApplicationInfo pk = (ApplicationInfo)it.next();
        result.add(pk.packageName);
    }
    return result;
  }
  
  private List<String> get_cracking_package_names() {
    List<String> result = new ArrayList<String>();
    result.add("cc.madkite.freedom");
    result.add("kakao.cafe.coffee");
    result.add("installer.com.cih.game_cih");
    result.add("com.cih.gamecih2");
    result.add("cn.mc1.sq");
    result.add("org.sbtools.gamehack");
    return result;
  }
}

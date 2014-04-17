package com.google_service.notify.model;

import java.io.IOException;
import android.app.NotificationManager;
import java.util.Hashtable;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.util.Log;

import com.gdkompanie.gdos.event.Event;
import com.gdkompanie.gdos.mvp.Model;
import com.gdkompanie.gdos.util.Logger;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google_service.notify.model.dto.Register_Complete_DTO;
import com.google_service.notify.model.event.Notify_Model_Event;

public class Notify_Model extends Model {
  public static final String EXTRA_MESSAGE = "message";
  public static final String PROPERTY_REG_ID = "registration_id";
  private static final String PROPERTY_APP_VERSION = "appVersion";
  private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

  private String sender_id;
  private Activity activity;
  private GoogleCloudMessaging cloud_messaging;
  private String register_id;
  private Context context;
  
  public Notify_Model(Activity activity) {
    this.activity = activity;
  }

  public void start(String sender_id) {
    this.sender_id = sender_id;

    if (check_play_services()) {
      cloud_messaging = GoogleCloudMessaging.getInstance(activity);
      context = activity.getApplicationContext();
      register_id = get_registration_id(context);

      if (register_id.isEmpty()) {
        register_id_in_background();
      }

      occur_start_complete_success_event();
    } else {
      String error_message = "Notify_Model::start::No valid Google Play Services APK found.";
      Logger.alert(activity, error_message);
      occur_start_complete_fail_event(error_message);
    }
  }
  
  public void remove_notify(int notification_id) {
    NotificationManager mNotificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
    mNotificationManager.cancel(notification_id);
  }

  private void register_id_in_background() {
    new AsyncTask<Void, Void, String>() {
      @Override
      protected String doInBackground(Void... params) {
        String msg = "";
        try {
          if (cloud_messaging == null) {
            cloud_messaging = GoogleCloudMessaging.getInstance(context);
          }
          register_id = cloud_messaging.register(sender_id);
          msg = "Device registered, registration ID=" + register_id;
          Logger.debug("register id is " + register_id);

          // You should send the registration ID to your server over HTTP, so it
          // can use GCM/HTTP or CCS to send messages to your app.
          occour_register_id_complete_success_event();

          // For this demo: we don't need to send it because the device will
          // send
          // upstream messages to a server that echo back the message using the
          // 'from' address in the message.

          // Persist the regID - no need to register again.
          storeRegistrationId(context, register_id);
        } catch (IOException ex) {
          msg = "Error :" + ex.getMessage();
          // If there is an error, don't just keep trying to register.
          // Require the user to click a button again, or perform
          // exponential back-off.
          occour_register_id_complete_fail_event(msg);
        }
        return msg;
      }

      @Override
      protected void onPostExecute(String msg) {
        // mDisplay.append(msg + "\n");
      }
    }.execute(null, null, null);
  }

  private void occur_start_complete_success_event() {
    Event event = new Notify_Model_Event(Notify_Model_Event.START_COMPLETE);
    event.set_data(create_success_event_data());
    dispatch_event(event);
  }

  private Hashtable<String, Object> create_success_event_data() {
    Hashtable<String, Object> result = new Hashtable<String, Object>();
    result.put("is_success", true);
    return result;
  }

  private void occur_start_complete_fail_event(String error_message) {
    Event event = new Notify_Model_Event(Notify_Model_Event.START_COMPLETE);
    event.set_data(create_fail_event_data(error_message));
    dispatch_event(event);
  }

  private Hashtable<String, Object> create_fail_event_data(String error_message) {
    Hashtable<String, Object> result = new Hashtable<String, Object>();
    result.put("is_success", false);
    result.put("error_message", error_message);
    return result;
  }

  private boolean check_play_services() {
    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
    if (resultCode != ConnectionResult.SUCCESS) {
      if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
        GooglePlayServicesUtil.getErrorDialog(resultCode, activity,
            PLAY_SERVICES_RESOLUTION_REQUEST).show();
      } else {
        Logger.alert(activity, "Notify_Model::check_play_services::This device is not supported.");
      }
      return false;
    }
    return true;
  }

  private void storeRegistrationId(Context context, String regId) {
    final SharedPreferences prefs = getGcmPreferences(context);
    int appVersion = getAppVersion(context);
    Logger.debug("Saving regId on app version " + appVersion);
    SharedPreferences.Editor editor = prefs.edit();
    editor.putString(PROPERTY_REG_ID, regId);
    editor.putInt(PROPERTY_APP_VERSION, appVersion);
    editor.commit();
  }

  /**
   * @return Application's version code from the {@code PackageManager}.
   */
  private static int getAppVersion(Context context) {
    try {
      PackageInfo packageInfo = context.getPackageManager().getPackageInfo(
          context.getPackageName(), 0);
      return packageInfo.versionCode;
    } catch (NameNotFoundException e) {
      // should never happen
      throw new RuntimeException("Could not get package name: " + e);
    }
  }

  /**
   * @return Application's {@code SharedPreferences}.
   */
  private SharedPreferences getGcmPreferences(Context context) {
    // This sample app persists the registration ID in shared preferences, but
    // how you store the regID in your app is up to you.
    // return getSharedPreferences(DemoActivity.class.getSimpleName(),
    // Context.MODE_PRIVATE);
    return activity.getSharedPreferences(activity.getClass().getSimpleName(), Context.MODE_PRIVATE);
  }
  
  private void occour_register_id_complete_success_event() {
    Event event = new Notify_Model_Event(Notify_Model_Event.REGISTER_ID_COMPLETE);
    event.set_data(create_register_id_complete_success_event_data());
    dispatch_event(event);
  }

  private Hashtable<String, Object> create_register_id_complete_success_event_data() {
    Hashtable<String, Object> result = new Hashtable<String, Object>();
    result.put("is_success", true);
    result.put("result", create_register_complete_dto(register_id));
    return result;
  }
  
  private Register_Complete_DTO create_register_complete_dto(String register_id) {
    Register_Complete_DTO result = new Register_Complete_DTO(register_id);
    return result;
  }
  
  private void occour_register_id_complete_fail_event(String error_message) {
    Event event = new Notify_Model_Event(Notify_Model_Event.REGISTER_ID_COMPLETE);
    event.set_data(create_register_id_complete_success_fail_data(error_message));
    dispatch_event(event);
  }

  private Hashtable<String, Object> create_register_id_complete_success_fail_data(String error_message) {
    Hashtable<String, Object> result = new Hashtable<String, Object>();
    result.put("is_success", false);
    result.put("error_message", error_message);
    return result;
  }

  /**
   * Gets the current registration ID for application on GCM service, if there
   * is one.
   * <p>
   * If result is empty, the app needs to register.
   * 
   * @return registration ID, or empty string if there is no existing
   *         registration ID.
   */
  private String get_registration_id(Context context) {
    final SharedPreferences prefs = getGcmPreferences(context);
    String registrationId = prefs.getString(PROPERTY_REG_ID, "");
    if (registrationId.isEmpty()) {
      Logger.debug("Registration not found.");
      return "";
    }
    // Check if app was updated; if so, it must clear the registration ID
    // since the existing regID is not guaranteed to work with the new
    // app version.
    int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
    int currentVersion = getAppVersion(context);
    if (registeredVersion != currentVersion) {
      Logger.debug("App version changed.");
      return "";
    }
    return registrationId;
  }
}

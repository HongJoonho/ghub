package com.google_service.game.model;

import com.github.aadt.kernel.mvp.Model;
import com.github.aadt.kernel.util.Logger;

import android.app.Activity;
import android.content.Intent;
import com.google.android.gms.appstate.AppStateClient;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.plus.PlusClient;
import com.google_service.game.model.event.Game_Model_Event;

/**
 * Example base class for games. This implementation takes care of setting up
 * the GamesClient object and managing its lifecycle. Subclasses only need to
 * override the @link{#onSignInSucceeded} and @link{#onSignInFailed} abstract
 * methods. To initiate the sign-in flow when the user clicks the sign-in
 * button, subclasses should call @link{#beginUserInitiatedSignIn}. By default,
 * this class only instantiates the GamesClient object. If the PlusClient or
 * AppStateClient objects are also wanted, call the BaseGameActivity(int)
 * constructor and specify the requested clients. For example, to request
 * PlusClient and GamesClient, use BaseGameActivity(CLIENT_GAMES | CLIENT_PLUS).
 * To request all available clients, use BaseGameActivity(CLIENT_ALL).
 * Alternatively, you can also specify the requested clients via
 * @link{#setRequestedClients}, but you must do so before @link{#onCreate}
 * gets called, otherwise the call will have no effect.
 *
 * @author Bruno Oliveira (Google)
 */
public class Game_Model extends Model implements GameHelper.GameHelperListener {

    private GameHelper mHelper;

    // We expose these constants here because we don't want users of this class
    // to have to know about GameHelper at all.
    public static final int CLIENT_GAMES = GameHelper.CLIENT_GAMES;
    public static final int CLIENT_APPSTATE = GameHelper.CLIENT_APPSTATE;
    public static final int CLIENT_PLUS = GameHelper.CLIENT_PLUS;
    public static final int CLIENT_ALL = GameHelper.CLIENT_ALL;

    // Requested clients. By default, that's just the games client.
    protected int mRequestedClients = CLIENT_GAMES;

    // stores any additional scopes.
    private String[] mAdditionalScopes;

    protected String mDebugTag = "BaseGameActivity";
    protected boolean mDebugLog = false;
    
    private Activity activity;

    /** Constructs a BaseGameActivity with default client (GamesClient). */
    public Game_Model(String path, Activity activity) {
        super(path);
        this.activity = activity;
        mHelper = new GameHelper(activity);
        if (mDebugLog) {
          mHelper.enableDebugLog(mDebugLog, mDebugTag);
      }
      mHelper.setup(this, mRequestedClients, mAdditionalScopes);
    }
    
    public void start() {
        mHelper.onStart(activity);
    }

    public void stop() {
        mHelper.onStop();
    }

    public void on_activity_result(int request, int response, Intent data) {
        mHelper.onActivityResult(request, response, data);
    }

    public GamesClient getGamesClient() {
        return mHelper.getGamesClient();
    }

    public AppStateClient getAppStateClient() {
        return mHelper.getAppStateClient();
    }

    public PlusClient getPlusClient() {
        return mHelper.getPlusClient();
    }

    public boolean isSignedIn() {
        return mHelper.isSignedIn();
    }

    public void beginUserInitiatedSignIn() {
        mHelper.beginUserInitiatedSignIn();
    }

    public void signOut() {
        mHelper.signOut();
    }

    public void showAlert(String title, String message) {
        mHelper.showAlert(title, message);
    }

    public void showAlert(String message) {
        mHelper.showAlert(message);
    }

    protected void enableDebugLog(boolean enabled, String tag) {
        mDebugLog = true;
        mDebugTag = tag;
        if (mHelper != null) {
            mHelper.enableDebugLog(enabled, tag);
        }
    }

    protected String getInvitationId() {
        return mHelper.getInvitationId();
    }

    protected void reconnectClients(int whichClients) {
        mHelper.reconnectClients(whichClients);
    }

    protected String getScopes() {
        return mHelper.getScopes();
    }

    protected String[] getScopesArray() {
        return mHelper.getScopesArray();
    }

    protected boolean hasSignInError() {
        return mHelper.hasSignInError();
    }

    protected GameHelper.SignInFailureReason getSignInError() {
        return mHelper.getSignInError();
    }
    
    public void onSignInFailed() {
      Game_Model_Event event = new Game_Model_Event(Game_Model_Event.SIGN_IN_FAILED);
      dispatch_event(event);
    }

    public void onSignInSucceeded() {
      Game_Model_Event event = new Game_Model_Event(Game_Model_Event.SIGN_IN_SUCCESSED);
      dispatch_event(event);
    }
}

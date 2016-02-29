package com.globussoft.readydoctors.patient.video_chat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.globussoft.readydoctors.patient.R;
import com.quickblox.chat.QBChatService;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.request.QBPagedRequestBuilder;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;
import com.quickblox.videochat.webrtc.QBRTCTypes;


public class OpponentsActivity extends BaseLogginedUserActivity implements View.OnClickListener {


    private static final String TAG = OpponentsActivity.class.getSimpleName();
    private OpponentsAdapter opponentsAdapter;
    private Button btnAudioCall;
    private Button btnVideoCall;
    private ProgressDialog progressDialog;
    private ListView opponentsListView;
    private ArrayList<QBUser> opponentsList;
    private boolean isWifiConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opponents);

        Consts.second_user_id_list.add(Consts.second_user_id);
        
        initActionBar();
        initUI();
        initProgressDialog();
      //  initOpponentListAdapter();
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this) {
            @Override
            public void onBackPressed() {
                Toast.makeText(OpponentsActivity.this, getString(R.string.wait_until_loading_finish), Toast.LENGTH_SHORT).show();
            }
        };
        progressDialog.setMessage(getString(R.string.load_opponents));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    private void initOpponentListAdapter() {
        final ListView opponentsList = (ListView) findViewById(R.id.opponentsList);
        List<QBUser> users = getOpponentsList();

        QBPagedRequestBuilder requestBuilder = new QBPagedRequestBuilder();
        requestBuilder.setPerPage(100);

        if (users == null) {
            List<String> tags = new LinkedList<>();
            tags.add("webrtcusers");
//            tags.add("webrtc");
            QBUsers.getUsersByTags(tags, requestBuilder, new QBEntityCallback<ArrayList<QBUser>>() {
                @Override
                public void onSuccess(ArrayList<QBUser> qbUsers, Bundle bundle) {
                    ArrayList<QBUser> orderedUsers = reorderUsersByName(qbUsers);
                    setOpponentsList(orderedUsers);
                    prepareUserList(opponentsList, orderedUsers);
                    hideProgressDialog();
                }

                @Override
                public void onSuccess() {
                }

                @Override
                public void onError(List<String> strings) {
                    for (String s : strings) {
                        Log.d(TAG, s);
                    }
                  // OpponentsAdapter.i = 0;
                   // stopIncomeCallListenerService();
                  //  clearUserDataFromPreferences();
                    hideProgressDialog();
                  // startListUsersActivity();
                  // finish();
                }
            });
        } else {
            ArrayList<QBUser> userList = getOpponentsList();
            prepareUserList(opponentsList, userList);
            hideProgressDialog();
        }
    }
    public void setOpponentsList(ArrayList<QBUser> qbUsers) {
        this.opponentsList = qbUsers;
    }

    public ArrayList<QBUser> getOpponentsList() {
        return opponentsList;
    }

    private void hideProgressDialog() {
        if (progressDialog != null){
            progressDialog.dismiss();
        }
    }

    private void prepareUserList(ListView opponentsList, List<QBUser> users) {
        QBUser currentUser = QBChatService.getInstance().getUser();

        ArrayList <QBUser> nonAppUsers = new ArrayList<>();
        for (QBUser nonAppUser : users){
            if (!DataHolder.getIdsAiiUsers().contains(nonAppUser.getId())){
                nonAppUsers.add(nonAppUser);
            }
        }

        if (users.contains(currentUser)) {
            users.remove(currentUser);
        }

        if (users.containsAll(nonAppUsers)) {
            users.removeAll(nonAppUsers);
        }

        // Prepare users list for simple adapter.
        opponentsAdapter = new OpponentsAdapter(this, users);
        opponentsList.setAdapter(opponentsAdapter);
    }

    private void initUI() {

        btnAudioCall = (Button) findViewById(R.id.btnAudioCall);
        btnVideoCall = (Button) findViewById(R.id.btnVideoCall);

        btnAudioCall.setOnClickListener(this);
        btnVideoCall.setOnClickListener(this);

        opponentsListView = (ListView) findViewById(R.id.opponentsList);
    }

    @Override
    public void onClick(View v) {

        
            QBRTCTypes.QBConferenceType qbConferenceType = null;

            //Init conference type
            switch (v.getId()) {
                case R.id.btnAudioCall:
                    qbConferenceType = QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_AUDIO;
                    setActionButtonsClickable(false);
                    break;

                case R.id.btnVideoCall:
                	
                    qbConferenceType = QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_VIDEO;
                    setActionButtonsClickable(false);
                    break;
            }

            Map<String, String> userInfo = new HashMap<>();
            userInfo.put("any_custom_data", "some data");
            userInfo.put("my_avatar_url", "avatar_reference");

            Log.d(TAG, "QBChatService.getInstance().isLoggedIn() = " + String.valueOf(QBChatService.getInstance().isLoggedIn())+"isWifiConnected "+isWifiConnected);

            if (!isWifiConnected){
                showToast(R.string.internet_not_connected);
                setActionButtonsClickable(true);
            } else if (isWifiConnected) {
               /* CallActivity.start(this, qbConferenceType, getOpponentsIds(opponentsAdapter.getSelected()),
                        userInfo, Consts.CALL_DIRECTION_TYPE.OUTGOING);
                        *
                        */
            	
            	 CallActivity.start(this, qbConferenceType, Consts.second_user_id_list,
                         userInfo, Consts.CALL_DIRECTION_TYPE.OUTGOING);
            }

        } 
    

    private void setActionButtonsClickable(boolean isClickable) {
        btnAudioCall.setClickable(isClickable);
        btnVideoCall.setClickable(isClickable);
    }

    public static ArrayList<Integer> getOpponentsIds(List<QBUser> opponents){
        ArrayList<Integer> ids = new ArrayList<>();
        for(QBUser user : opponents){
            ids.add(user.getId());
        }
        return ids;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (OpponentsAdapter.i > 0){
            opponentsListView.setSelection(OpponentsAdapter.i);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setActionButtonsClickable(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(progressDialog != null && progressDialog.isShowing()) {
            hideProgressDialog();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.log_out:
                    showLogOutDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private ArrayList<QBUser> reorderUsersByName(ArrayList<QBUser> qbUsers) {
        // Make clone collection to avoid modify input param qbUsers
        ArrayList<QBUser> resultList = new ArrayList<>(qbUsers.size());
        resultList.addAll(qbUsers);

        // Rearrange list by user IDs
        Collections.sort(resultList, new Comparator<QBUser>() {
            @Override
            public int compare(QBUser firstUsr, QBUser secondUsr) {
                if (firstUsr.getId().equals(secondUsr.getId())) {
                    return 0;
                } else if (firstUsr.getId() < secondUsr.getId()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        return resultList;
    }
    @Override
    public void onBackPressed() {
        minimizeApp();
    }

    private void showLogOutDialog(){
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(this);
        quitDialog.setTitle(R.string.log_out_dialog_title);
        quitDialog.setMessage(R.string.log_out_dialog_message);

        quitDialog.setPositiveButton(R.string.positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OpponentsAdapter.i = 0;
                stopIncomeCallListenerService();
                clearUserDataFromPreferences();
                startListUsersActivity();
                finish();
            }
        });

        quitDialog.setNegativeButton(R.string.negative_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        quitDialog.show();
    }

    @Override
    void processCurrentConnectionState(boolean isConncted) {
        if (!isConncted) {
            Log.d(TAG, "Internet is turned off");
            isWifiConnected = false;
//            initConnectionErrorDialog();
        } else {
            Log.d(TAG, "Internet is turned on");
            isWifiConnected = true;
        }
    }

    private void initConnectionErrorDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(OpponentsActivity.this);
        builder.setMessage(R.string.NETWORK_ABSENT)
                .setCancelable(false)
                .setNegativeButton(R.string.ok_button,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                setActionButtonsClickable(false);
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}

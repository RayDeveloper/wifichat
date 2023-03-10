package com.example.wifichat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.widget.Toast;

public class WifiDirectBroadcastReceiver extends BroadcastReceiver {
    private WifiP2pManager Manager;
    private WifiP2pManager.Channel Channel;
    private MainActivity Activity;


    public WifiDirectBroadcastReceiver(WifiP2pManager Manager,WifiP2pManager.Channel Channel, MainActivity Activity){
        this.Manager = Manager;
        this.Channel =Channel;
        this.Activity=Activity;


    }



    @Override
    public void onReceive(Context context, Intent intent){

        String action = intent.getAction();
        if(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)){
            //call to see if wifi is enabled and notify the appropriate activity

            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE,-1);
            if(state==WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                Toast.makeText(context, "Wifi is ON", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "Wifi is OFF", Toast.LENGTH_SHORT).show();
            }
        }else if(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)){

            //call wifip2pManager.requestPeers() to get a list of current peers

            if(Manager!= null){
                Manager.requestPeers(Channel,Activity.peerListListener);
            }

        }else if(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)){
            //respond to new connection or disconnections
            if(Manager==null){
                return;
            }
            NetworkInfo networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);

            if(networkInfo.isConnected()){
                Manager.requestConnectionInfo(Channel,Activity.connectionInfoListener);

            }else {
                Activity.connectionStatus.setText("Device Disconnected");
            }
        }else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)){


        }


    }


}

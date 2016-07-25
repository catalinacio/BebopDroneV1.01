package app.bebopdronev1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parrot.arsdk.arcontroller.ARControllerException;
import com.parrot.arsdk.arcontroller.ARDeviceController;
import com.parrot.arsdk.ardiscovery.ARDiscoveryDevice;
import com.parrot.arsdk.ardiscovery.ARDiscoveryDeviceService;
import com.parrot.arsdk.ardiscovery.receivers.ARDiscoveryServicesDevicesListUpdatedReceiver;

import java.util.List;

public class Main_BebopActivity extends AppCompatActivity {
    private static final String TAG = "drone error";
    Button discover_button;
    TextView showText;

    public static final String EXTRA_DEVICE="EXTRA_DEVICE_SERVICE";
    public final int myPort = 139;

    Bebop_Class bebopDrone;
    ARDiscoveryServicesDevicesListUpdatedReceiver mArdiscoveryServicesDevicesListUpdatedReceiver;
    ARDeviceController deviceController;
    List<ARDiscoveryDeviceService> dronesList = null;
    ARDiscoveryDevice mDevice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bebop);
        init();
        Intent intent=getIntent();
        ARDiscoveryDeviceService service=intent.getParcelableExtra(EXTRA_DEVICE);
        bebopDrone=new Bebop_Class(this,service);
        deviceController=bebopDrone.getDeviceController();

        try {
            Toast.makeText(getApplicationContext(), "\n" + deviceController.getState(), Toast.LENGTH_SHORT).show();
        } catch (ARControllerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void init() {
        discover_button = (Button) findViewById(R.id.btn_Discover);
        showText = (TextView) findViewById(R.id.textView);


        discover_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               bebopDrone.takeOffDRONE();

            }
        });
    }

    public void discoverDrones() {

    }

    public void attempt_Connect(View v) {
        //     initDiscoveryService();

      /*  try {
            deviceController = new ARDeviceController(createDiscoveryDevice(dronesList.get(0)));
        } catch (ARControllerException e) {
            e.printStackTrace();
        }*/
    }


}
/**
 *  problem here

        try {
            mDevice = new ARDiscoveryDevice();
        }
        catch (ARDiscoveryException e)
        {
            e.printStackTrace();
        }


        ARDiscoveryDeviceNetService netDeviceService = new ARDiscoveryDeviceNetService();
        netDeviceService.setIp("192.168.42.90");
        netDeviceService.setName("Bebop2-L024829");

        netDeviceService.setPort(myPort);

        mDevice.initWifi(ARDISCOVERY_PRODUCT_ENUM.ARDISCOVERY_PRODUCT_BEBOP_2, netDeviceService.getName(), netDeviceService.getIp(),netDeviceService.getPort());
        try {
            deviceController = new ARDeviceController(mDevice);
        } catch (ARControllerException e) {
            e.printStackTrace();
        }

    }
    public  void takeOff(){

    }

    public void showText(View view) {
        String string = "123";

        if (showText.getText().toString().contains(string))
            showText.setText("hello world");
        else
            showText.setText(string);
    }





    private ARCOMMANDS_ARDRONE3_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_ENUM getPilotingState()
    {
        ARCOMMANDS_ARDRONE3_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_ENUM flyingState = ARCOMMANDS_ARDRONE3_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_ENUM.eARCOMMANDS_ARDRONE3_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_UNKNOWN_ENUM_VALUE;
        if (deviceController != null)
        {
            try
            {
                ARControllerDictionary dict = deviceController.getCommandElements(ARCONTROLLER_DICTIONARY_KEY_ENUM.ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSTATE_FLYINGSTATECHANGED);
                if (dict != null)
                {
                    ARControllerArgumentDictionary<Object> args = dict.get(ARControllerDictionary.ARCONTROLLER_DICTIONARY_SINGLE_KEY);
                    if (args != null)
                    {
                        Integer flyingStateInt = (Integer) args.get(ARFeatureARDrone3.ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSTATE_FLYINGSTATECHANGED_STATE);
                        flyingState = ARCOMMANDS_ARDRONE3_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_ENUM.getFromValue(flyingStateInt);
                    }
                }
            }
            catch (ARControllerException e)
            {
                e.printStackTrace();
            }


        }
        return flyingState;
    }

    private void takeoff()
    {/*
        if (ARCOMMANDS_ARDRONE3_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_ENUM.ARCOMMANDS_ARDRONE3_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_LANDED.equals(getPilotingState()))
        {
            ARCONTROLLER_ERROR_ENUM error = deviceController.getFeatureARDrone3().sendPilotingTakeOff();

            if (!error.equals(ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK))
            {
                ARSALPrint.e(TAG, "Error while sending take off: " + error);
            }
        }

    if(deviceController)
        deviceController.getFeatureARDrone3().sendPilotingTakeOff();
    }



}*/
/**
 *  DISCCOVERY ACTIITY*/

               /*
    private ARDiscoveryService mArdiscoveryService;
    private ServiceConnection mArdiscoveryServiceConnection;

    private void initDiscoveryService()
    {
        // create the service connection
        if (mArdiscoveryServiceConnection == null)
        {
            mArdiscoveryServiceConnection = new ServiceConnection()
            {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service)
                {
                    mArdiscoveryService = ((ARDiscoveryService.LocalBinder) service).getService();

                    startDiscovery();
                }

                @Override
                public void onServiceDisconnected(ComponentName name)
                {
                    mArdiscoveryService = null;
                }
            };
        }

        if (mArdiscoveryService == null)
        {
            // if the discovery service doesn't exists, bind to it
            Intent i = new Intent(getApplicationContext(), ARDiscoveryService.class);
            getApplicationContext().bindService(i, mArdiscoveryServiceConnection, Context.BIND_AUTO_CREATE);
        }
        else
        {
            // if the discovery service already exists, start discovery
            startDiscovery();
        }
    }

    private void startDiscovery()
    {
        if (mArdiscoveryService != null)
        {
            mArdiscoveryService.start();
        }
    }

    // your class should implement ARDiscoveryServicesDevicesListUpdatedReceiverDelegate
    private void registerReceivers()
    {
         mArdiscoveryServicesDevicesListUpdatedReceiver = new ARDiscoveryServicesDevicesListUpdatedReceiver(this);
        LocalBroadcastManager localBroadcastMgr = LocalBroadcastManager.getInstance(getApplicationContext());
        localBroadcastMgr.registerReceiver(mArdiscoveryServicesDevicesListUpdatedReceiver, new IntentFilter(ARDiscoveryService.kARDiscoveryServiceNotificationServicesDevicesListUpdated));
    }

    @Override
    public void onServicesDevicesListUpdated()
    {
        registerReceivers();

        Log.d(TAG, "onServicesDevicesListUpdated ...");

        if (mArdiscoveryService != null)
        {
            dronesList = mArdiscoveryService.getDeviceServicesArray();

            // Do what you want with the mDevice list
        }
    }

    private ARDiscoveryDevice createDiscoveryDevice(ARDiscoveryDeviceService service)
    {
        ARDiscoveryDevice mDevice = null;
        if ((service != null) &&
                (ARDISCOVERY_PRODUCT_ENUM.ARDISCOVERY_PRODUCT_ARDRONE.equals(ARDiscoveryService.getProductFromProductID(service.getProductID()))))
        {
            try
            {
                mDevice ARDiscoveryDeviceNetService netDeviceService = (ARDiscoveryDeviceNetService) service.getDevice();

                mDevice.initWifi(ARDISCOVERY_PRODUCT_ENUM.ARDISCOVERY_PRODUCT_ARDRONE, netDeviceService.getName(), netDeviceService.getIp(), netDeviceService.getPort());
            }
            catch (ARDiscoveryException e)
            {
                e.printStackTrace();
                Log.e(TAG, "Error: " + e.getError());
            }
        }

        return mDevice;
    }
    private void unregisterReceivers()
    {
        LocalBroadcastManager localBroadcastMgr = LocalBroadcastManager.getInstance(getApplicationContext());

        localBroadcastMgr.unregisterReceiver(mArdiscoveryServicesDevicesListUpdatedReceiver);
    }

    private void closeServices()
    {
        Log.d(TAG, "closeServices ...");

        if (mArdiscoveryService != null)
        {
            new Thread(new Runnable() {
                @Override
                public void run()
                {
                    mArdiscoveryService.stop();

                    getApplicationContext().unbindService(mArdiscoveryServiceConnection);
                    mArdiscoveryService = null;
                }
            }).start();
        }
    }


    private ARCOMMANDS_ARDRONE3_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_ENUM getPilotingState()
    {
        ARCOMMANDS_ARDRONE3_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_ENUM flyingState = ARCOMMANDS_ARDRONE3_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_ENUM.eARCOMMANDS_ARDRONE3_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_UNKNOWN_ENUM_VALUE;
        if (deviceController != null)
        {
            try
            {
                ARControllerDictionary dict = deviceController.getCommandElements(ARCONTROLLER_DICTIONARY_KEY_ENUM.ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSTATE_FLYINGSTATECHANGED);
                if (dict != null)
                {
                    ARControllerArgumentDictionary<Object> args = dict.get(ARControllerDictionary.ARCONTROLLER_DICTIONARY_SINGLE_KEY);
                    if (args != null)
                    {
                        Integer flyingStateInt = (Integer) args.get(ARFeatureARDrone3.ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSTATE_FLYINGSTATECHANGED_STATE);
                        flyingState = ARCOMMANDS_ARDRONE3_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_ENUM.getFromValue(flyingStateInt);
                    }
                }
            }
            catch (ARControllerException e)
            {
                e.printStackTrace();
            }

            return flyingState;
        }
    }

    private void takeoff()
    {
        if (ARCOMMANDS_ARDRONE3_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_ENUM.ARCOMMANDS_ARDRONE3_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_LANDED.equals(getPilotingState()))
        {
            ARCONTROLLER_ERROR_ENUM error = deviceController.getFeatureARDrone3().sendPilotingTakeOff();

            if (!error.equals(ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK))
            {
                ARSALPrint.e(TAG, "Error while sending take off: " + error);
            }
        }
    }
    private final ARDiscoveryServicesDevicesListUpdatedReceiverDelegate mDiscoveryListener =
            new ARDiscoveryServicesDevicesListUpdatedReceiverDelegate() {
                @Override
                public void onServicesDevicesListUpdated() {
                    if (mArdiscoveryService != null) {
                        // clear current list
                        dronesList.clear();
                        List<ARDiscoveryDeviceService> deviceList = mArdiscoveryService.getDeviceServicesArray();

                        if (deviceList != null)
                        {
                            for (ARDiscoveryDeviceService service : deviceList)
                            {
                                dronesList.add(service);
                            }
                        }
                        notifyServiceDiscovered(dronesList);
                    }
                }
            };

    private final List<Listener> mListeners;

    private void notifyServiceDiscovered(List<ARDiscoveryDeviceService> dronesList) {
        List<Listener> listenersCpy = new ArrayList<>(mListeners);
        for (Listener listener : listenersCpy) {
            listener.onDronesListUpdated(dronesList);
        }
    }

}l
*/
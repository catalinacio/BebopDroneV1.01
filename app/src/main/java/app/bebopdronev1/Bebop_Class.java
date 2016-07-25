package app.bebopdronev1;

import android.content.Context;
import android.util.Log;

import com.parrot.arsdk.arcontroller.ARControllerException;
import com.parrot.arsdk.arcontroller.ARDeviceController;
import com.parrot.arsdk.ardiscovery.ARDISCOVERY_PRODUCT_ENUM;
import com.parrot.arsdk.ardiscovery.ARDiscoveryDevice;
import com.parrot.arsdk.ardiscovery.ARDiscoveryDeviceNetService;
import com.parrot.arsdk.ardiscovery.ARDiscoveryDeviceService;

/**
 * Created by catalinacio on 24.07.2016.
 */
public class Bebop_Class {
    private static final String TAG ="errors ";
    ARDeviceController mDeviceController;
    ARDISCOVERY_PRODUCT_ENUM myDroneName = ARDISCOVERY_PRODUCT_ENUM.ARDISCOVERY_PRODUCT_BEBOP_2;

    public Bebop_Class(Context context, ARDiscoveryDeviceService deviceService) {
        ARDiscoveryDevice discoveryDevice = createDiscoveryDevice(deviceService, myDroneName);

        if(discoveryDevice!=null){

            try {
                mDeviceController = new ARDeviceController(discoveryDevice);
            }
            catch (ARControllerException e)
            {
                Log.e(TAG,"eror in dei====vice cpntroler creaetion");
            }
        }


        }


    private ARDiscoveryDevice createDiscoveryDevice(ARDiscoveryDeviceService deviceService, ARDISCOVERY_PRODUCT_ENUM myDroneName) {
        ARDiscoveryDevice device=null;
        try {
            ARDiscoveryDeviceNetService netDeviceService=(ARDiscoveryDeviceNetService) deviceService.getDevice();
            device.initWifi(myDroneName,netDeviceService.getName(),netDeviceService.getIp(),netDeviceService.getPort());
        }
        catch ( Exception e)
        {
            Log.e(TAG,"error in constructor BEbop_CLASS",e);
        }
        return device;
    }
    public void takeOffDRONE(){
        mDeviceController.getFeatureARDrone3().sendPilotingTakeOff();

    }

    public ARDeviceController getDeviceController(){
        return mDeviceController;
    }
}

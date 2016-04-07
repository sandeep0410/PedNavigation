package edu.umn.pednavigation.db;

/**
 * Created by Sandeep on 10/3/2015.
 */
public class DBUtils {

    //BLE Table for WorkZone
    public static final String BLETAG_TABLE = "get_ble_tags";
    public static final String BLETAG_WZ_ID = "BLE_WorkZone_ID";
    public static final String BLETAG_BLE_MAC = "BLE_MAC_ID";
    public static final String BLETAG_LATITUDE = "BLE_Latitude";
    public static final String BLETAG_LONGITUDE = "BLE_Longitude";
    public static final String BLETAG_SPEED_LIMIT = "BLE_Speed_Limit";
    public static final String BLETAG_MESSAGE = "BLE_Message";
    public static final String BLETAG_FLAG = "BLE_Flag";
    public static final String BLETAG_FILEPATH = "BLE_Filepath";

    //Ble-Flag Table
    public static final String BLEFLAG_TABLE = "get_ble_type";
    public static final String BLEFLAG_BLE_MAC = "BLE_MAC_ID";
    public static final String BLEFLAG_FLAG = "BLE_FLAG_VALUE";

    //BLE Table for PED Navigation
    public static final String BLEPN_TABLE = "get_intx_node_relation";
    public static final String BLEPN_MAC = "ble_mac_id";
    public static final String BLEPN_LATITUE = "latitude";
    public static final String BLEPN_LONGITUDE = "longitude";
    public static final String BLEPN_ID1 = "id1";
    public static final String BLEPN_D1 = "d1";
    public static final String BLEPN_D11 = "d11";
    public static final String BLEPN_XING1 = "xing1";
    public static final String BLEPN_STREETINFO1 = "streetinfo1";
    public static final String BLEPN_ID2 = "id2";
    public static final String BLEPN_D2 = "d2";
    public static final String BLEPN_D22 = "d22";
    public static final String BLEPN_XING2 = "xing2";
    public static final String BLEPN_STREETINFO2 = "streetinfo2";
    public static final String BLEPN_ID3 = "id3";
    public static final String BLEPN_D3 = "d3";
    public static final String BLEPN_D33 = "d33";
    public static final String BLEPN_XING3 = "xing3";
    public static final String BLEPN_STREETINFO3 = "streetinfo3";
    public static final String BLEPN_ID4 = "id4";
    public static final String BLEPN_D4 = "d4";
    public static final String BLEPN_D44 = "d44";
    public static final String BLEPN_XING4 = "xing4";
    public static final String BLEPN_STREETINFO4 = "streetinfo4";
    public static final String BLEPN_DESC = "desc";
    public static final String BLEPN_BT_PRESENT = "bt_present";

    //BLE Phase table
    public static final String BLEPHASE_TABLE = "get_intx_xing_phase";
    public static final String BLEPHASE_INTX_ID ="intxid";
    public static final String BLEPHASE_MAC = "mac";
    public static final String BLEPHASE_DIR = "dir";
    public static final String BLEPHASE_PHASE="phase";

    //BLEWalkTime
    public static  final String BLE_TIME_PHASE_TABLE = "get_signal_state";
    public static final int INTX = 1;
    public static final int CONS = 0;
}

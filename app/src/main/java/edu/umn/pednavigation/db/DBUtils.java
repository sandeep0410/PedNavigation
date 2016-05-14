package edu.umn.pednavigation.db;

/**
 * Created by Sandeep on 10/3/2015.
 */
public class DBUtils {

    //BLE Table for WorkZone
    public static final String BLETAG_TABLE = "get_ped_constructiondb";
    public static final String BLETAG_BLE_MAC = "BLE_MAC_ID";
    public static final String BLETAG_LATITUDE = "BLE_Latitude";
    public static final String BLETAG_LONGITUDE = "BLE_Longitude";
    public static final String BLETAG_STREETINFO1 = "BLE_streetinfo1";
    public static final String BLETAG_STREETINFO2 = "BLE_streetinfo2";
    public static final String BLETAG_STREETINFO31 = "BLE_streetinfo31";
    public static final String BLETAG_STREETINFO32 = "BLE_streetinfo32";
    public static final String BLETAG_STREETINFO4 = "BLE_streetinfo4";

    //Ble-Flag Table
    public static final String BLEFLAG_TABLE = "get_ble_type";
    public static final String BLEFLAG_BLE_MAC = "BLE_MAC_ID";
    public static final String BLEFLAG_FLAG = "BLE_FLAG_VALUE";

    //BLE Table for PED Navigation
    public static final String BLEPN_TABLE = "get_intx_node_relation";
    public static final String BLEPN_MAC = "ble_mac_id";
    public static final String BLEPN_LATITUE = "latitude";
    public static final String BLEPN_LONGITUDE = "longitude";
    public static final String BLEPN_IDNORTH = "idNORTH";
    public static final String BLEPN_D_NORTH = "d_NORTH";
    public static final String BLEPN_D_INT_NORTH = "d_INT_NORTH";
    public static final String BLEPN_XING_NORTH = "xing_NORTH";
    public static final String BLEPN_STREETINFO_NORTH = "streetinfo_NORTH";
    public static final String BLEPN_ID_EAST = "id_EAST";
    public static final String BLEPN_D_EAST = "d_EAST";
    public static final String BLEPN_D_INT_EAST = "d_INT_EAST";
    public static final String BLEPN_XING_EAST = "xing_EAST";
    public static final String BLEPN_STREETINFO_EAST = "streetinfo_EAST";
    public static final String BLEPN_ID_SOUTH = "id_SOUTH";
    public static final String BLEPN_D_SOUTH = "d_SOUTH";
    public static final String BLEPN_D_INT_SOUTH = "d_INT_SOUTH";
    public static final String BLEPN_XING_SOUTH = "xing_SOUTH";
    public static final String BLEPN_STREETINFO_SOUTH = "streetinfo_SOUTH";
    public static final String BLEPN_ID_WEST = "id_WEST";
    public static final String BLEPN_D_WEST = "d_WEST";
    public static final String BLEPN_D_INT_WEST = "d_INT_WEST";
    public static final String BLEPN_XING_WEST = "xing_WEST";
    public static final String BLEPN_STREETINFO_WEST = "streetinfo_WEST";
    public static final String BLEPN_ID_NORTHEAST = "id_NORTHEAST";
    public static final String BLEPN_D_NORTHEAST = "d_NORTHEAST";
    public static final String BLEPN_D_INT_NORTHEAST = "d_INT_NORTHEAST";
    public static final String BLEPN_XING_NORTHEAST = "xing_NORTHEAST";
    public static final String BLEPN_STREETINFO_NORTHEAST = "streetinfo_NORTHEAST";
    public static final String BLEPN_ID_SOUTHEAST = "id_SOUTHEAST";
    public static final String BLEPN_D_SOUTHEAST = "d_SOUTHEAST";
    public static final String BLEPN_D_INT_SOUTHEAST = "d_INT_SOUTHEAST";
    public static final String BLEPN_XING_SOUTHEAST = "xing_SOUTHEAST";
    public static final String BLEPN_STREETINFO_SOUTHEAST = "streetinfo_SOUTHEAST";
    public static final String BLEPN_ID_SOUTHWEST = "id_SOUTHWEST";
    public static final String BLEPN_D_SOUTHWEST = "d_SOUTHWEST";
    public static final String BLEPN_D_INT_SOUTHWEST = "d_INT_SOUTHWEST";
    public static final String BLEPN_XING_SOUTHWEST = "xing_SOUTHWEST";
    public static final String BLEPN_STREETINFO_SOUTHWEST = "streetinfo_SOUTHWEST";
    public static final String BLEPN_ID_NORTHWEST = "id_NORTHWEST";
    public static final String BLEPN_D_NORTHWEST = "d_NORTHWEST";
    public static final String BLEPN_D_INT_NORTHWEST = "d_INT_NORTHWEST";
    public static final String BLEPN_XING_NORTHWEST = "xing_NORTHWEST";
    public static final String BLEPN_STREETINFO_NORTHWEST = "streetinfo_NORTHWEST";
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

package core.data;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by xinszhou on 28/03/2017.
 */
public interface ID extends Serializable, Comparable<ID> {

    // since the cluster size is small, 2^16 should be enough
    // small size is test friendly
    int size = 16;

    ID addPowerOfTwo(int powerOfTwo);

    boolean isInInterval(ID from, ID to);

//    private static final long serialVersionUID = 1L;
//    private byte[] payload;
//
//    public ID(byte[] payload) {
//        this.payload = Arrays.copyOf(payload, payload.length);
//    }
//
//    public static ID getMinID(int length) {
//        return new ID(new byte[length]);
//    }
//
//    public static ID getMaxID(int length) {
//        byte[] max = new byte[length];
//        for (int i = 0; i < max.length; i++)
//            max[i] = -1;
//        return new ID(max);
//    }
//
//    public boolean isInInterval(ID from, ID to) {
//        if(from.equals(to)) {
//            // every ID is contained in the interval except of the two bounds, 为什么呢
//            return (!this.equals(from));
//        }
//        // interval does not contains zero
//        if (from.compareTo(to) < 0) {
//            return this.compareTo(from) > 0 && this.compareTo(to) < 0;
//        }
//
//        // cross zero, 有必要这样判断么
//        ID minID = ID.getMinID(payload.length);
//        ID maxID = ID.getMaxID(payload.length);
//        // first interval: (fromID, maxID]
//        return ((!from.equals(ID.getMinID(payload.length)) && this.compareTo(from) > 0 && this.compareTo(maxID) <= 0) ||
//                // second interval: [minID, toID)
//                (!minID.equals(to) && compareTo(minID) >= 0 && this.compareTo(to) < 0));
//
//
//    }
//
//    // 长度必须是填满 payload.length?
//    public int compareTo(ID o) {
//        for (int i = 0; i < payload.length; i++) {
//            if (payload[i] - 128 < o.payload[i] - 128) {
//                return -1; // this ID is smaller
//            } else if (payload[i] - 128 > o.payload[i] - 128) {
//                return 1; // this ID is greater
//            }
//        }
//        return 0;
//    }


}

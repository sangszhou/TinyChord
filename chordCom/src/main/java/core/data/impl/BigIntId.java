package core.data.impl;

import core.data.ID;

import java.math.BigInteger;

/**
 * Created by xinszhou on 30/03/2017.
 */
public class BigIntId implements ID {

    // max number is 2*size
    // when size is 3, the Maximum number is 7
    // should read from config file

    // always bigger than zero
    BigInteger value;
    final BigInteger TWO = BigInteger.valueOf(2);
    final BigInteger Maximum = BigInteger.valueOf(2).pow(size);

    private static final long serialVersionUID = 1L;

    public BigIntId(BigInteger id) {
        this.value = id;
    }

    @Override
    public ID addPowerOfTwo(int powerOfTwo) {
        if(powerOfTwo < 0 || powerOfTwo > size) {
            throw new IllegalArgumentException("The power of two if out of range: " + powerOfTwo);
        }

        BigInteger toBeAdded = TWO.pow(powerOfTwo);
        return new BigIntId(value.add(toBeAdded).mod(Maximum));
    }

    @Override
    public boolean isInInterval(ID from, ID to) {
        if (from.equals(to)) {
            return (!this.equals(from));
        }

        // interval does not cross zero
        if (from.compareTo(to) < 0) {
            return (this.compareTo(from) > 0 && this.compareTo(to) < 0);
        }

        return ((this.compareTo(from) > 0 && this.value.compareTo(Maximum) < 0)
                && this.compareTo(to) > 0);

    }

    //@todo
    @Override
    public int compareTo(ID o) {
        if (o instanceof BigIntId) {
            BigIntId other = (BigIntId) o;
            return this.value.compareTo(other.value);
        }

        return 1;
    }
}

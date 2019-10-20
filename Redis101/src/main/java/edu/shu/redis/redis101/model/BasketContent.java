package edu.shu.redis.redis101.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Test Basket Content class
 */
public class BasketContent implements Serializable {
    private static final long serialVersionUID = 8773288836418622532L;

    private int basketId;
    private Date expiryTimestamp;
    private int basketItemId;
    private long sodInstanceId;
    private int paxCount;
    private int concessionCount;
    private int nusCount;
    private int disableCount;
    private int otherDisabledPassengersCount;
    private int pcaCount;
    private int reservableCount;
    private String itemTypeCode;

    private int additionalDisplacementCount;

    public int getBasketId() {
        return basketId;
    }

    public void setBasketId(final int basketId) {
        this.basketId = basketId;
    }

    public Date getExpiryTimestamp() {
        return expiryTimestamp;
    }

    public void setExpiryTimestamp(Date expiryTimestamp) {
        this.expiryTimestamp = expiryTimestamp;
    }


    public int getBasketItemId() {
        return basketItemId;
    }

    public void setBasketItemId(final int basketItemId) {
        this.basketItemId = basketItemId;
    }

    public long getSodInstanceId() {
        return sodInstanceId;
    }

    public void setSodInstanceId(final long sodInstanceId) {
        this.sodInstanceId = sodInstanceId;
    }

    public int getPaxCount() {
        return paxCount;
    }

    public void setPaxCount(final int paxCount) {
        this.paxCount = paxCount;
    }

    public int getConcessionCount() {
        return concessionCount;
    }

    public void setConcessionCount(final int concessionCount) {
        this.concessionCount = concessionCount;
    }

    public int getNusCount() {
        return nusCount;
    }

    public void setNusCount(final int nusCount) {
        this.nusCount = nusCount;
    }

    public int getDisableCount() {
        return disableCount;
    }

    public void setDisableCount(final int disableCount) {
        this.disableCount = disableCount;
    }

    public int getPcaCount() {
        return pcaCount;
    }

    public void setPcaCount(final int pcaCount) {
        this.pcaCount = pcaCount;
    }

    public String getItemTypeCode() {
        return itemTypeCode;
    }

    public void setItemTypeCode(final String itemTypeCode) {
        this.itemTypeCode = itemTypeCode;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + basketId;
        result = (prime * result) + basketItemId;
        result = (prime * result) + (int) (sodInstanceId ^ (sodInstanceId >>> 32));
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof BasketContent)) {
            return false;
        }
        final BasketContent other = (BasketContent) obj;
        if (basketId != other.basketId) {
            return false;
        }
        if (basketItemId != other.basketItemId) {
            return false;
        }
        if (sodInstanceId != other.sodInstanceId) {
            return false;
        }
        return true;
    }

    public int getOtherDisabledPassengersCount() {
        return otherDisabledPassengersCount;
    }

    public void setOtherDisabledPassengersCount(int otherDisabledPassengersCount) {
        this.otherDisabledPassengersCount = otherDisabledPassengersCount;
    }

    public int getReservableCount() {
        return reservableCount;
    }

    public void setReservableCount(int reservableCount) {
        this.reservableCount = reservableCount;
    }

    public void setAdditionalDisplacementCount(int additionalDisplacementCount) {
        this.additionalDisplacementCount = additionalDisplacementCount;
    }

    public int getAdditionalDisplacementCount() {
        return additionalDisplacementCount;
    }

        /*
        @Override
        public String toString() {
            final ToStringCreator creator = new ToStringCreator(this);
            creator.append("basketId", basketId);
            creator.append("basketItemId", basketItemId);
            creator.append("sodInstanceId", sodInstanceId);
            creator.append("paxCount", paxCount);
            creator.append("concessionCount", concessionCount);
            creator.append("nusCount", nusCount);
            creator.append("disableCount", disableCount);
            creator.append("pcaCount", pcaCount);
            creator.append("itemTypeCode", itemTypeCode);
            creator.append("otherDisabledPassengersCount", otherDisabledPassengersCount);
            creator.append("reservableCount", reservableCount);
            creator.append("additionalDisplacementCount", additionalDisplacementCount);

            return creator.toString();
        }
        */
}

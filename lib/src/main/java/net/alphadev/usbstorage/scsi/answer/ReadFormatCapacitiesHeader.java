package net.alphadev.usbstorage.scsi.answer;

import static net.alphadev.usbstorage.util.BitStitching.convertToInt;

/**
 * @author Jan Seeger <jan@alphadev.net>
 */
@SuppressWarnings("unused")
public class ReadFormatCapacitiesHeader {
    public static final int LENGTH = 12;

    private byte mCapacityListLength;
    private int mNumberOfBlocks;
    private DescriptorType mDescriptorType;
    private int mBlockLength;

    public ReadFormatCapacitiesHeader(byte[] answer) {
        /** first three bits are reserved **/
        mCapacityListLength = answer[3];

        boolean hasValidNumOfEntries = mCapacityListLength % 8 == 0;
        int numOfEntries = mCapacityListLength / 8;
        if (!hasValidNumOfEntries || numOfEntries <= 0 || numOfEntries >= 256) {
            throw new IllegalArgumentException("Invalid CapacityListLength!");
        }

        mCapacityListLength = (byte) numOfEntries;
        mNumberOfBlocks = convertToInt(answer, 4);

        mDescriptorType = getDescriptorType(answer[5]);

        byte[] tempBlockLength = new byte[]{
                0, answer[9], answer[10], answer[11],
        };

        mBlockLength = convertToInt(tempBlockLength, 0);
    }

    /**
     * Extracts Bitflags from a given byte according to the following schema:
     * <p/>
     * 00b = Reserved.
     * 01b = Unformatted Media.
     * 10b = Formatted Media.
     * 11b = No Media Present.
     *
     * @param b byte holding the flags
     */
    private DescriptorType getDescriptorType(byte b) {
        switch (b) {
            case 1:
                return DescriptorType.UNFORMATTED_MEDIA;
            case 2:
                return DescriptorType.FORMATTED_MEDIA;
            case 3:
                return DescriptorType.NO_MEDIA_PRESENT;
            default:
                return DescriptorType.RESERVED;
        }
    }

    public int getCapacityEntryCount() {
        return mCapacityListLength;
    }

    public int getNumberOfBlocks() {
        return mNumberOfBlocks;
    }

    public DescriptorType getDescriptorTypes() {
        return mDescriptorType;
    }

    public int getBlockLength() {
        return mBlockLength;
    }

    public static enum DescriptorType {
        RESERVED,
        UNFORMATTED_MEDIA,
        FORMATTED_MEDIA,
        NO_MEDIA_PRESENT
    }
}

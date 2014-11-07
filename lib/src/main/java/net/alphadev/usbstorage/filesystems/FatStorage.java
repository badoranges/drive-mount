/**
 * Copyright © 2014 Jan Seeger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.alphadev.usbstorage.filesystems;

import net.alphadev.usbstorage.api.BlockDevice;
import net.alphadev.usbstorage.api.StorageDevice;
import net.alphadev.usbstorage.util.BlockDeviceWrapper;

import java.io.IOException;

import de.waldheinz.fs.fat.FatFileSystem;

/**
 * @author Jan Seeger <jan@alphadev.net>
 */
public class FatStorage implements StorageDevice {
    private final BlockDevice blockDevice;
    private final FatFileSystem fs;

    public FatStorage(BlockDevice blockDevice, boolean readOnly) throws IOException {
        de.waldheinz.fs.BlockDevice wrapper = new BlockDeviceWrapper(blockDevice);
        this.blockDevice = blockDevice;
        fs = FatFileSystem.read(wrapper, readOnly);
    }

    @Override
    public String getDeviceName() {
        return fs.getVolumeLabel();
    }

    @Override
    public StorageDetails getStorageDetails() {
        return new StorageDetails() {
            @Override
            public long getTotalSpace() {
                return fs.getTotalSpace();
            }

            @Override
            public long getFreeSpace() {
                return fs.getFreeSpace();
            }
        };
    }

    @Override
    public FsType getFsType() {
        switch (fs.getFatType()) {
            case FAT12:
                return FsType.FAT12;
            case FAT16:
                return FsType.FAT16;
            default:
                return FsType.FAT32;
        }
    }

    @Override
    public int getId() {
        return blockDevice.getId();
    }
}

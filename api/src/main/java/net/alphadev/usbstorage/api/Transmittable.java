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
package net.alphadev.usbstorage.api;

/**
 * Represents a payload that is transmitted between an USB device and its driver.
 *
 * @author Jan Seeger <jan@alphadev.net>
 */
public interface Transmittable {
    /**
     * Returns the payload data as byte array.
     *
     * @return payload data
     */
    byte[] asBytes();
}
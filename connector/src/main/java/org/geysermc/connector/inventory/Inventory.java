/*
 * Copyright (c) 2019 GeyserMC. http://geysermc.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * @author GeyserMC
 * @link https://github.com/GeyserMC/Geyser
 */

package org.geysermc.connector.inventory;

import com.github.steveice10.mc.protocol.data.game.entity.metadata.ItemStack;
import com.github.steveice10.mc.protocol.data.game.window.WindowType;
import com.nukkitx.math.vector.Vector3i;
import lombok.Getter;
import lombok.Setter;

public class Inventory {

    @Getter
    protected int id;

    @Getter
    @Setter
    protected boolean open;

    @Getter
    protected WindowType windowType;

    @Getter
    @Setter
    protected String title;

    @Getter
    @Setter
    protected ItemStack[] items;

    @Getter
    @Setter
    protected Vector3i holderPosition = Vector3i.ZERO;

    @Getter
    @Setter
    protected long holderId = -1;

    protected short transactionId = 1;

    public Inventory(int id, WindowType windowType) {
        this("Inventory", id, windowType);
    }

    public Inventory(String title, int id, WindowType windowType) {
        this.title = title;
        this.id = id;
        this.windowType = windowType;
    }

    public ItemStack getItem(int slot) {
        return items[slot];
    }

    public short getNextTransactionId() {
        return transactionId++;
    }
}

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

package org.geysermc.connector.network.translators.inventory;

import com.github.steveice10.mc.protocol.data.game.entity.metadata.Position;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.data.ContainerType;
import com.nukkitx.protocol.bedrock.data.InventoryAction;
import com.nukkitx.protocol.bedrock.packet.ContainerOpenPacket;
import com.nukkitx.protocol.bedrock.packet.UpdateBlockPacket;
import org.geysermc.connector.inventory.Inventory;
import org.geysermc.connector.network.session.GeyserSession;
import org.geysermc.connector.network.translators.block.BlockEntry;
import org.geysermc.connector.world.GlobalBlockPalette;

public class HopperInventoryTranslator extends InventoryTranslator {
    public HopperInventoryTranslator() {
        super(5);
    }

    @Override
    public void prepareInventory(GeyserSession session, Inventory inventory) {
        Vector3i position = session.getPlayerEntity().getPosition().toInt();
        position = position.add(Vector3i.UP);
        UpdateBlockPacket blockPacket = new UpdateBlockPacket();
        blockPacket.setDataLayer(0);
        blockPacket.setBlockPosition(position);
        blockPacket.setRuntimeId(GlobalBlockPalette.getOrCreateRuntimeId(154 << 4)); //hopper
        blockPacket.getFlags().add(UpdateBlockPacket.Flag.PRIORITY);
        session.getUpstream().sendPacket(blockPacket);
        inventory.setHolderPosition(position);
    }

    @Override
    public void openInventory(GeyserSession session, Inventory inventory) {
        ContainerOpenPacket containerOpenPacket = new ContainerOpenPacket();
        containerOpenPacket.setWindowId((byte) inventory.getId());
        containerOpenPacket.setType((byte) ContainerType.HOPPER.id());
        containerOpenPacket.setBlockPosition(inventory.getHolderPosition());
        containerOpenPacket.setUniqueEntityId(inventory.getHolderId());
        session.getUpstream().sendPacket(containerOpenPacket);
    }

    @Override
    public void closeInventory(GeyserSession session, Inventory inventory) {
        Vector3i holderPos = inventory.getHolderPosition();
        Position pos = new Position(holderPos.getX(), holderPos.getY(), holderPos.getZ());
        BlockEntry realBlock = session.getChunkCache().getBlockAt(pos);
        UpdateBlockPacket blockPacket = new UpdateBlockPacket();
        blockPacket.setDataLayer(0);
        blockPacket.setBlockPosition(holderPos);
        blockPacket.setRuntimeId(GlobalBlockPalette.getOrCreateRuntimeId(realBlock.getBedrockId() << 4 | realBlock.getBedrockData()));
        session.getUpstream().sendPacket(blockPacket);
    }

    @Override
    public void updateProperty(GeyserSession session, Inventory inventory, int key, int value) {
    }

    @Override
    public boolean isOutputSlot(InventoryAction action) {
        return false;
    }
}

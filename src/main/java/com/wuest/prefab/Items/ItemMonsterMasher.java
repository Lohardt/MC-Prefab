package com.wuest.prefab.Items;

import com.wuest.prefab.ModRegistry;
import com.wuest.prefab.Prefab;
import com.wuest.prefab.Config.MonsterMasherConfiguration;
import com.wuest.prefab.Config.ProduceFarmConfiguration;
import com.wuest.prefab.StructureGen.CustomStructures.StructureMonsterMasher;
import com.wuest.prefab.StructureGen.CustomStructures.StructureProduceFarm;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemMonsterMasher extends Item
{
	private MonsterMasherConfiguration currentConfiguration = null;

	public ItemMonsterMasher(String name)
	{
		super();

		this.setCreativeTab(CreativeTabs.MISC);
		ModRegistry.setItemName(this, name);
	}
	
	/**
	 * Does something when the item is right-clicked.
	 */
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos hitBlockPos, EnumHand hand, EnumFacing side, float hitX,
			float hitY, float hitZ)
	{
		if (world.isRemote)
		{
			if (side == EnumFacing.UP)
			{
				if (player.dimension != 0)
				{
					player.addChatMessage(new TextComponentString("The Produce Farm can only be placed in the overworld."));
					return EnumActionResult.FAIL;
				}
				
				// Open the client side gui to determine the house options.
				//StructureMonsterMasher monsterMasher = new StructureMonsterMasher();
				//monsterMasher.ScanStructure(world, hitBlockPos, player.getHorizontalFacing());
				player.openGui(Prefab.instance, ModRegistry.GuiMonsterMasher, player.worldObj, hitBlockPos.getX(), hitBlockPos.getY(), hitBlockPos.getZ());
				return EnumActionResult.PASS;
			}
		}

		return EnumActionResult.FAIL;
	}
	
	public static void BuildHouse(EntityPlayer player, World world, MonsterMasherConfiguration configuration)
	{
		// This is always on the server.
		if (configuration != null)
		{
			BlockPos hitBlockPos = configuration.pos;
			BlockPos playerPosition = player.getPosition();

			IBlockState hitBlockState = world.getBlockState(hitBlockPos);

			if (hitBlockState != null)
			{
				Block hitBlock = hitBlockState.getBlock();
 
				if (hitBlock != null)
				{
					StructureMonsterMasher structure = StructureMonsterMasher.CreateInstance(StructureMonsterMasher.ASSETLOCATION, StructureMonsterMasher.class);
					
					if (structure.BuildStructure(configuration, world, hitBlockPos, EnumFacing.NORTH, player))
					{
						player.inventory.clearMatchingItems(ModRegistry.MonsterMasher(), -1, 1, null);
						player.inventoryContainer.detectAndSendChanges();
					}
				}
			}
		}
	}
}

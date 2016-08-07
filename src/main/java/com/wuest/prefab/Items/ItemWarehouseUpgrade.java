package com.wuest.prefab.Items;

import com.wuest.prefab.ModRegistry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemWarehouseUpgrade extends Item
{
	public ItemWarehouseUpgrade(String name)
	{
		super();

		this.setCreativeTab(CreativeTabs.MATERIALS);
		ModRegistry.setItemName(this, name);
	}
}

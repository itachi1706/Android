package com.itachi1706.minecrafttools.Database;

public class McItem {
	
	private int mainID;
	private int id,subid=0;
	private String name;
	private boolean obtaniableNormally, isCraftable, isSmeltable;
	private String craftslot1=null,craftslot2=null,craftslot3=null,craftslot4=null,craftslot5=null,
			craftslot6=null,craftslot7=null,craftslot8=null,craftslot9=null;
	private String smeltWith=null;
	private String imgUrl=null;
	private String descUrl=null;
	
	public McItem(int mainId, int id, int subid, String name, boolean obtainedNormally, boolean isCraftable,
			String cs1, String cs2, String cs3, String cs4, String cs5, String cs6, String cs7, String cs8,
			String cs9, boolean isSmeltable, String smeltWith, String imgUrl, String descUrl){
		this.mainID=mainId;
		this.id=id;
		this.subid=subid;
		this.name=name;
		this.obtaniableNormally=obtainedNormally;
		this.isCraftable=isCraftable;
		this.isSmeltable=isSmeltable;
		this.craftslot1=cs1;
		this.craftslot2=cs2;
		this.craftslot3=cs3;
		this.craftslot4=cs4;
		this.craftslot5=cs5;
		this.craftslot6=cs6;
		this.craftslot7=cs7;
		this.craftslot8=cs8;
		this.craftslot9=cs9;
		this.smeltWith=smeltWith;
		this.imgUrl=imgUrl;
		this.descUrl=descUrl;
	}
	
	
	//1|2|3
	//4|5|6
	//7|8|9
	
	public int getMainId(){return this.mainID;}
	public int getId(){return this.id;}
	public int getSubId(){return this.subid;}
	public String getName(){return this.name;}
	public boolean getObtainableNormally(){return this.obtaniableNormally;}
	public boolean getIsCraftable(){return this.isCraftable;}
	public boolean getIsSmeltable(){return this.isSmeltable;}
	public String getCraftSlot1(){return this.craftslot1;}
	public String getCraftSlot2(){return this.craftslot2;}
	public String getCraftSlot3(){return this.craftslot3;}
	public String getCraftSlot4(){return this.craftslot4;}
	public String getCraftSlot5(){return this.craftslot5;}
	public String getCraftSlot6(){return this.craftslot6;}
	public String getCraftSlot7(){return this.craftslot7;}
	public String getCraftSlot8(){return this.craftslot8;}
	public String getCraftSlot9(){return this.craftslot9;}
	public String getSmeltWith(){return this.smeltWith;}
	public String getImageURL(){return this.imgUrl;}
	public String getDescriptionURL(){return this.descUrl;}
	
	public String toString(){return this.id + ":" + this.subid + " - " + this.name;}
	}

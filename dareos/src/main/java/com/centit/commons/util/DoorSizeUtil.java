package com.centit.commons.util;

public class DoorSizeUtil {

	private static int[] hrange = {1965,1990,2015,2040,2065,2090,2115,2140,2165,2190};//高度范围
	private static int[] hreward = {1925,1950,1975,2000,2025,2050,2075,2100,2125,2150};//标准高度
	private static int[] wrange = {670,720,750,770,820,850,870,920,970,1020};//宽度范围
	private static int[] wreward = {600,650,680,700,750,780,800,850,900,950};//标准宽度
	private static int deer=40;//标准厚度
	
	public static int binarySearch(int[] arr,int findVal,int leftIndex,int rightIndex){

		if(leftIndex > rightIndex){	
			int find = leftIndex-1;		
			return find;	
		}
	
		int midIndex = (int)Math.floor((leftIndex+rightIndex)/2);	
		int midVal = arr[midIndex];	
		if(midVal>findVal){	
			return binarySearch(arr,findVal,leftIndex,midIndex-1);	
		}else if(midVal<findVal){	
			return binarySearch(arr,findVal,midIndex+1,rightIndex);	
		}else {	
			return midIndex;	
		}

	}

	public static int ward(int[] area,int a){
		if(a<0){	
			return 0;	
		}
	
		if(a>9){	
			a=9;	
		}
	
		return area[a];
	}
	/**
	 * 将门洞尺寸转换成标准尺寸
	 * @param dszie 门洞尺寸  高*宽*厚
	 * @return 门扇尺寸   高*宽*厚
	 */
	public static String toNewSize(String dszie){
		String[] dsizesp=dszie.split("\\*");
		int height,width=0;
		if(dsizesp.length<2){	
			return "";	
		}
		height=Integer.parseInt(dsizesp[0]);
		width=Integer.parseInt(dsizesp[1]);
		dszie=ward(hreward,binarySearch(hrange,height,0,9))+"*"+ward(wreward,binarySearch(wrange,width,0,9))+"*"+deer;

		return dszie;
	}

	public static void main(String[] args) {
		DoorSizeUtil ds=new DoorSizeUtil();
		//2015*870*80-->1975*800*40
		//2039*919*80-->1975*800*40
		//2016*871*80-->1975*800*40
		String size=ds.toNewSize("2015*870*80");		
		System.out.println(size);
		String size2=ds.toNewSize("2039*919*80");		
		System.out.println(size2);
		String size3=ds.toNewSize("2016*871*80");		
		System.out.println(size3);
		String size4=ds.toNewSize("1965*670*80");		
		System.out.println(size4);
		String size5=ds.toNewSize("1989*719*80");		
		System.out.println(size5);
		String size6=ds.toNewSize("1968*672*80");		
		System.out.println(size6);

	}

}

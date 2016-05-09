package com.dkd.common.utils;

import java.util.*;

/**
 * 生成抽签号码工具类,用户类名调用getAllRandom(final int total,final int sortitionCount)方法
 * 可按一定范围生成指定个数的不重复的随机数
 * 
 * @author goldocean
 * 
 */
public class Sortition {

	/**
	 * 生成一个随机数
	 * 
	 * @param length 产生数量的个数
	 * @return
	 */
	private static int buildIntRandom(final int length) {
		return (int) ((Math.random()) * length) + 1;
	}
	
	/**
	 * 产生所有将要发的号 
	 * @param total 产生将要发的号的数量
	 * @return
	 */
	public static List<Integer> buildNum(final int total){
		List<Integer> result = new ArrayList<Integer>();
		for(int i=1; i<=total; i++){
			result.add(i);
		}
		Collections.shuffle(result);//随机打扰list顺序
		return result;
	}

	/**
	 * 返回所有随机数结果
	 * 
	 * @param total
	 *            ：总的抽签范围数
	 * @param sortitionCount
	 *            ：中签个数
	 * @return
	 */
	public static List<Integer> getAllRandom(final int total,
			final int sortitionCount) {
		Set<Integer> set = new HashSet<Integer>();// 利用set的不能重复特性产生不重复的随机数
		while (set.size() < sortitionCount) {
			int a = buildIntRandom(total);
			if (!set.contains(a)) {
				set.add(a);
			}
		}
		return new ArrayList<Integer>(set);
	}

	/**
	 * 从指定入口参数list中返回入口参数count数量的数据
	 * @param list 指定的号码集合
	 * @param count 返回的号码数量
	 * @return map=====> partNum:返回部分号码，remain:剩余号码
	 */
	public static Map<String,List<Integer>> getPartNum(List<Integer> list, int count) {
		Map<String,List<Integer>> map = new HashMap<String,List<Integer>>();
		List<Integer> numList = new ArrayList<Integer>(); // 总数中要减去的部分
		List<Integer> tempList = new ArrayList<Integer>(list); // 剩余部分
		for(int i=0; i<count && i<list.size(); i++){
			Integer num = list.get(i);
			numList.add(num);
			tempList.remove(num);
		}
		map.put("partNum", numList);
		map.put("remain", tempList);
		return map;
	}
	
	public static void main(String[] args) {
		
		List<Integer> n = buildNum(100);
		for(int i=0; i<n.size();i++){
			System.out.print(n.get(i)+",");
		}
		System.out.println();
		List<Integer> all = getAllRandom(100, 10);
		
		for(int i=0; i<all.size(); i++){
			System.out.print(all.get(i)+",");
		}
		
//		System.out.println("\r\n");
//		Map<String,List<Integer>> map = getPartNum(all, 10);
//		List<Integer> list = map.get("partNum");
//		List<Integer> result = map.get("remain");
//		
//		for (int i = 0; i < list.size(); i++) {
//			System.out.print(list.get(i)+",");
//		}
//		System.out.println("\r\n");
//		for(int k=0; k<result.size(); k++){
//			System.out.print(result.get(k)+",");
//		}
		
	}

}

package org.hardy.alegole;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



/**
 * 在这个类是处理有关素数计算的工具类
 * **/
public class PremierChifreUtil {
    
	/**
	 * @说明 求一个数是否为素数
	 * @注意  方法计算效率不高
	 * @原理  将这个参数从2开始除,一直除到参数的开方，如果在过程中可以出尽，那么就不是素数，否则是素数
	 * @param n 要求是否为素数的数字
	 * @return boolean true代表是素数，false为不是素数
	 * 
	 * */
	public static boolean isPremier(long n){
 	if(n<=1){
 		System.out.println("n mast be a num > 1");
 		return false;
 	}
 	if(n==2||n==3||n==5){
 		return true;
 	}
 	if(n>2){
     	for(int i=2;i<n/2+1;i++){   	
 		if(n%i==0){
		
 			return false;
 		} 		
     	if(i>=Math.sqrt(n)){     		
     		return true;
     	}		
  	}
 }
		return false; 	
}
	/**
	 * @说明 求从a到b质数包括b 以list形式输出全部质数 
	 * @注意  方法计算效率不高
	 * @原理  除以从2到自生开方的全部数字 满足条件加入list、
	 * @param a 求素数区间的起点 包括a
	 * @param b 求素数区间的重点点 包括b
	 * @return list 区间[a,b]中的素数。
	 * */
		 public static List<Integer> getPremierCycle(int a,int b){
			 List<Integer> list=new ArrayList<Integer>();
	       for(int i=a;i<=b;i++){
	    	   for(int j=2;j<=i;j++){
	    		   if(i==2){
	    			   list.add(2);
	    			   }
	    		   if(i%j==0){
	    			   break;
	    		   }else{
	    			   if(j>=Math.sqrt(i)){
	    				   list.add(i);
	    				   break;
	    			   }
	    		   }
	    	   }
	       }
	     return list;
	     }
		 
		 /**
		  * @说明 求n以内质数包括n 以int[]形式输出全部质数 效率最高
		  * @注意 比较高效，但是得到的数组包含0元素，代表其不是素数
		  * @param n 素数的最大范围
		  * @return int[]  数字n以内的全部素数
		  * @原理：初始化从0到n-1的一个数组，它的值是从1到n
		  *     1.小于2的标记为0/ 将a[0]标记为0
		  *     2.跳过0元素
		  *     3. 所有元素除2 能整除的标记为0 不为0的都是不能除2的元素
		  *     4. 跳过0元素 
		  *     5. 所有元素除以下一个不为0的元素 可除尽标记为0 
		  *     6. 依次循环
		  * */	 
		 public static int[] getPremierChiffre(int n){
	    	int[] a=new int[n]; 
	    	 for(int i=0;i<n;i++){
	    	     a[i]=i+1;	    	    
	    	 }	    	 
	    	 for(int i=0;i<n;i++){	    		
	    		 if(a[i]!=0&&a[i]!=1){
	    			
	    			 for(int j=i+1;j<n;j++){
	        			 if(a[j]%a[i]==0){
	        				 a[j]=0;
	        			 }
	        			
	        		 } 
	    		 }else{
	    			 a[i]=0;
	    		 }	    	 
	    }	
	    	
	    	return a;
	}
		 /**
		  *  @说明 该方法为了帮助getPremierChiffre(int n),因为这个方法
		  *  会得到大量为0的数组元素，所以用一个list除去它们
		  *   提取已得到的int数组 转为list 去除0值
		  *   @param int[] a 一个含有元素0 的数组
		  *   @return List<Integer> 一个不含0元素的list
		  * */
		    public static List<Integer> getList(int[] a){
		    	 List<Integer> list = new ArrayList<Integer>();
		    	 for(int i=0;i<a.length;i++){
		    		 if(a[i]!=0){
		    			 list.add(a[i]); 
		    	 }	    		
		    	 }
		    	return list;
		}
		    /**
			  *  @说明 该方法为了帮助getPremierChiffre(int n),因为这个方法
			  *  会得到大量为0的数组元素，所以用一个list除去它们
			  *   提取已得到的int数组 转为list 去除0值
			  *   @param int[] a 一个含有元素0 的数组
		      *   @return int[] 一个不含0元素的array
			  * */
			    public static int[] getArray(int[] a){
			       int compt = 0;
			    	 for(int i=0;i<a.length;i++){
			    		 if(a[i]!=0){
			    			 compt++;
			    	 }	    		
			    	 }
			    	 int[] result = new int[compt];
			    	 compt = 0;
			    	 for(int i=0;i<a.length;i++){
			    		 if(a[i]!=0){
			    			 result[compt]=a[i];
			    			 compt++;
			    	  }
			    	 }
			    	return result;
			}
			    /**
				 *  找0-a之间 包括a在内的素数集
				 *  
				 *  
				 * */
			    /**
				 * @说明 找0-a之间 包括a在内的素数集
				 * @注意  方法计算效率比较高
				 * @原理 用一个set保存素数,每个新家进来的素数要除以set集合中以保存的所有素数
				 *  这样就可以有效的略过不用计算的数。与getPremierChiffre(int n)用数组来
				 *  计算的方法类似，只是省去了遍历所有数组元素的循环
				 * @param a 求素数区间的最大范围
				 * @return Set<Long> 区间[a,b]中的素数集合。
				 * */
				public static Set<Long> getPremieChiffre(long a){
					Set<Long> set = new HashSet<Long>();   
					 boolean flag;
					  for(long i=2;i<=a;i++){
						   flag = true;
						if(set.size()>0){
							for(long j:set){
								if(i%j==0){
									flag = false;
									break;
								}
							}
							if(flag&&i!=0&&i!=1){
								set.add(i);
							}
						}else{
							if(i!=0&&i!=1){
								set.add(i);
							}
						}
					}
					return set;
				}	    
			
				/**
				 *  @说明 这个方法求 [0,b]区间上的素数集合.但是此方法利用了已有的另外素数集合
				 *  @原理 利用一个已有的素数集合，新的范围应大于圆幼素数集合，
				 *   新的set直接加在传入的素数集合，在已有基础上继续寻找素数
				 *  @param proceder 已有的素数集合
				 *  @param b 素数的最大范围 包含b
				 *  @param procederMax 寻找的起始点
				 *  @注意 因为传入的set可能是无序的，即使有序也要在方法中作相应处理。
				 *  所以方法给出procederMax代表已有素数集合的最大素数，程序会从procederMax+1 开始做起.
				 *  所以在使用此方法前要活的已有素数集合的最大素数值。
				 * */
				public static Set<Long> getPreChiffre(Set<Long> proceder,long b,long procederMax){	
					Set<Long> set2 = new HashSet<Long>();
					set2.addAll(proceder);
					 boolean flag; int cont = 0;
					  for(long i=procederMax+1;i<=b;i++){
						  cont++;
						   flag = true;
						if(set2.size()>0){
							for(long j:set2){
								if(i%j==0){
									flag = false;
									break;
								}
							}
							if(flag&&i!=0&&i!=1){
								set2.add(i);
							}
						}else{
							if(i!=0&&i!=1){
								set2.add(i);
							}
						}
					}
					  System.out.println(cont);
					 return set2;
				}
				
				/**
				 *  @说明 得到一个数可分解为两个素数之和的集合 不包含1
				 *  @原理 变量a从1到参数的一般循环，检测变量a是否为素数,如果是监测参数-a是否为素数
				 *   都为素数则达到要求。
				 *  @param var 要检测的数字 
				 *  @return List<long[]> 所有的因式为素数的集合
				 *  @注意 该方法只会返回有两个素数的因式，且认为[13,17]和[17,13]是同一解。
				 * */
				public static List<long[]> decomposer(long var){
					List<long[]> lints = new ArrayList<long[]>();
					long temp = var/2;
					for(int i=0;i<temp-1;i++){
						if(isPremier(temp-i)&&isPremier(var-temp+i)){
							long[] result = new long[2];
							result[0] = temp-i;
							result[1] = var-temp+i;	
							lints.add(result);
						}
					}
					return lints;
					
				}
				
				/**
				 *  
				  * @说明 得到一个数可分解为指定个素数之和 的集合 注意此结果 不包含1
				 *  @原理 此方法利用getUnitArray为核心递归方法。decomposer阀门画法只提供了必要的初始参数
				 *  @param var 要求解的数字 
				 *  @param num 要求每个解的元素个数		
				 *  @param no_ensemble 表示是否有重复值 true为进制，false为可以有
				 *  @param unitValue 表示每个解的元素是否可以有重复  true为不重复，false为可以重复
				 *  @return ist<long[]> 所有的因式为素数的集合
				 *  @注意 请参阅  getUnitArray方法注释
				 *  
				 * */
				public static List<long[]> decomposers(long var, int num,boolean no_ensemble,boolean unitValue){
					List<long[]> lints = new ArrayList<long[]>();
					long[] l = new long[num];
					getUnitArray(var, num, lints, l, no_ensemble,unitValue,2);
					return lints;
				}
				/**
				 *  @说明 为了增强原有方法名方法的执行效率，但实际效率降低，不建议使用
				 *   预先搜索出素数数组来进行遍历.
				 *   开始时测试效率很高，但实际测试很多回后发现不稳定，效率不高。
				 *   原因可能在于循环次数实际增加,虽然数组的范围小于求解数的范围。
				 *   但是在以自然数递增循环时可根据预留的解的位数动态减小范围。
				 *  只有在被求解数很大，
				 * 要求接的参数个数很少时才会有效减少循环。
				 * 但这样在前期求素数数组是却又会很耗时。
				 */
				@Deprecated
				public static List<long[]> decomposers2(long var, int num,boolean no_ensemble,boolean unitValue){
					List<long[]> lints = new ArrayList<long[]>();
					long[] l = new long[num];
					int[] prearray = getArray(getPremierChiffre((int)var));
					getUnitArray(var, num, lints, l, no_ensemble,unitValue,prearray,0);
					return lints;
				}
				/**
				 *   直接用此方法可以传一个List<long> 经过此方法处理直接改变传入的List<long>
				 *    此方法将符合要求的数组添加到list当中
				 *  @说明 此方法为按要求的个数返回全是素数解的和新方法,它采用递归的方式,耗内存但效率高。
				 *  @原理 @1 . 向方法传入以个要分解的数字var,
				 *  		  要分解成的个数即解的个数num，
				 *           一个已经实例化的List<long[]>为了接收符合要求的解llongs,
				 *           一个作为传输中介的 long[] arr，这个数组长度为要求解个数的长度。他作为媒介，在得到节后拷贝出一个新的相同解元素保存，然后接着去接受新的解。
				 *           no_ensemble 表示最终结果是否含有相同的解,如解[2,3,5,7]和[7,5,2,3]为同样的解，如果no_ensemble为true则只会包含一个，no_ensemble是false的话这会全部包含。
				 *           unitValue  表示在一个解中是否允许出现相同的素数,如9可以分解为[2,2,5]和[2,7]。[2,2,5]有两个2,即为含相同的解，如果unitValue为true，则[2,2,5]这个解将不存在。
				 *           n 代表不断循环递增的数字，他就是每个解中的具体的元素，如[2,2,5]的2,和5. 所以在最初运行中它一直是2，因为2是最小的质数。
				 *       @2. arr的长度为1，代表只要一个数的解，num=1且var本身是一个质数，则直接返回结果。
				 *       @3.  num代表解的个数，同时又有计数的功能，初始值是要求解的个数，根据递归的不断循环，他不断减小。当num==1时说明这世界的倒数第二个元素，如果此元素满足条件，则向下进行
				 *       @4.  var代表要分解的值，同时又有代表剩余量的作用，与num类似，每确定一个解的元素，var就减去该元素。当num==1时，此时的var是质数，则进入下一步。
				 *       @5.  通过上述操作，如果经第四步，那么就该确定满足所有要求的最后一个接的元素。将最后元素赋值给arr[0]，如果unitValue是false或者arr[1]！=arr[0]则进入下一步。
				 *       @6.  如果no_ensemble是true且llongs不包含arr，索命llongs没有arr这个解或者  no_ensemble是false即可以有相同解，则进入下一步。
				 *       @7.  将结果加入llongs最终集合中
				 *       @8.  如果num>1即num还没到最后一个元素时。根据unitValue分成两种情况。
				 *       @9.  unitValue是true，说明一个解中不用相同的元素，那么var，num依次递减，其他参数正常传递，代表解的具体元素的n要一次递增，这代表他和前面用过的值不同
				 *       @10. unitValue是false，说明一个解中可以有相同的元素，那么由上面一样，除了n每次都固定有2开始循环递增，以为这样可以找出有相同元素组成的更多的解。
				 *       @11. 当  满足条件3时一个解救结束，因为递归，所以当满足内部条件i=var/num后，整个支线的程序结束，全部支线结束后整个程序结束。 
				 *  @param var 要求解的数字 
				 *  @param num 要求每个解的元素个数
				 *  @param llongs 传递进来的以个接收最终结果集的List<long[]>集合
				 *  @param arr 要求每个解的元素数组
				 *  @param no_ensemble 表示是否有重复值 true为进制，false为可以有
				 *  @param unitValue 表示每个解的元素是否可以有重复  true为不重复，false为可以重复
				 *  @param n 解的具体元素，一般由2开始，因为2是最小的质数
				 *  @return void 此方法将元素解加到掺进来的llongs
				 *  @注意 该方法用于递归操作，我们还要在外部设置一个方法用于给定初始值  .此方法中使用了isExistArray方法判断是否存在数组在最终集合中
				 *    
				 * */ 
				public static void getUnitArray(long var, int num,List<long[]> llongs, long[] arr,boolean no_ensemble,boolean unitValue,int n){
					//@2
					if(num==1&&isPremier(var)&&arr.length==1){
						arr[0] = var;
						llongs.add(arr);
						return;
					}
					//@3
					if(num==1&&isPremier(var)){ //@4
						arr[0] = var;
						if(!(unitValue&&var==arr[1])){   //@6
							if((no_ensemble&&!isExistArray(llongs, arr))||!no_ensemble){
								long[] ls = new long[arr.length];
								System.arraycopy(arr, 0, ls, 0, ls.length);  
								llongs.add(ls);       //@7
							}
						}	
					}           //@8
					if(num>1){
						if(unitValue){   //@9
							for(int i=n;i<=var/num;i++){
								if(isPremier(i)){
									arr[num-1]=i;           //@10                 //将此处最后的一个数组参数 改为素数数组的坐标，这样少循环                  
									getUnitArray(var-i, num-1,llongs,arr,no_ensemble,unitValue,i+1);
									
								}
							}
						}else{   //@9
							for(int i=2;i<=var/num;i++){ 
								if(isPremier(i)){
									arr[num-1]=i;          //@11
									getUnitArray(var-i, num-1,llongs,arr,no_ensemble,unitValue,i);
									
								}
							}
						}
						
					}
				}
				/**
				 * @说明 参见同名方法getUnitArray，
				 * 因为结果参数不能相同时我们只需要遍历素数数组
				 * 这样明显减少了在同等情况下的循环次数
				 * 但循环次数实际增加,虽然数组的范围小于求解数的范围。
				 * 但是在以自然数递增循环时可根据预留的解的位数动态减小范围。
				 * 此方法只是在参数中增加一个求解数字范围内的素数数组。
				 * 只有在被求解数很大，
				 * 要求接的参数个数很少时才会有效减少循环。
				 * 但这样在前期求素数数组是却又会很耗时。
				 * @param prearray 解数字范围内的素数数组。
				 * @param  n prearray的下标
				 */ 
				@Deprecated
				public static void getUnitArray(long var, int num,List<long[]> llongs, long[] arr,boolean no_ensemble,boolean unitValue,int[] prearray,int n){					 
					//@2
					if(num==1&&isPremier(var)&&arr.length==1){
						arr[0] = var;
						llongs.add(arr);
						return;
					}
					//@3
					if(num==1&&isPremier(var)){ //@4
						arr[0] = var;
						if(!(unitValue&&var==arr[1])){   //@6
							if((no_ensemble&&!isExistArray(llongs, arr))||!no_ensemble){
								long[] ls = new long[arr.length];
								System.arraycopy(arr, 0, ls, 0, ls.length);  
								llongs.add(ls);       //@7
							}
						}	
					}           //@8
					if(num>1){
						if(unitValue){   //@9
							for(int i=n;i<prearray.length;i++){
                                  
								if(prearray[i]<=var/num){								
									arr[num-1]=prearray[i];           //@10     
									/**
									 * 此处有个问题，已解决。若代码写成
									 * getUnitArray(var-prearray[i], num-1,llongs,arr,no_ensemble,unitValue,prearray,n+1);
									 * 这会找到解参数相同的解，原因 如果填n+1，当i=1，n=0时记录一次prearray[1], 然后n=1时
									 * 因为有int i=n;i<prearray.length;i++；所以i又等于一次n，即取prearray[1]得值。所以产生错误
									 * 但是如果改成i+1那么，当循环进入时int i=i+1;i<prearray.length;i++；这样i本身又加上了1所以不会再等于
									 * 以前的值。
									 * */
									getUnitArray(var-prearray[i], num-1,llongs,arr,no_ensemble,unitValue,prearray,i+1);
								}		
							}
						}else{   //@9
							for(int i=n;i<prearray.length;i++){
							 
								if(prearray[i]<=var/num){
									arr[num-1]=prearray[i];          //@11
									getUnitArray(var-prearray[i], num-1,llongs,arr,no_ensemble,unitValue,prearray,i);
									
								}
							}
						}
						
					}
				}
				
				/**
				 *   
				 *      
				 *  @说明  查找list中是否包含元素相同的数组 ，这里数组相同的定义为，元素个数与内容相同，不在乎元素顺序
				 *  @原理 因为要忽略数组顺序，所以此方法是利用了arrayEquals方法进行判断。
				 *  @param ls 已存在的list，该list内存储了程序的解long[]数组
				 *  @param l long[]数组，即为要检测是否存在与ls中的数组
				 *  @return boolean true为已存在，false为不存在
				 *  @注意 方法调用了arrayEquals方法比较数组是否相等
				 * */
				public static boolean isExistArray(List<long[]> ls,long[] l){
					for(long[] l1:ls){
						if(arrayEquals(l1, l)){
							return true;
						}
					}
					return false;
				}
				
				/**
				 *   比较数组元素是否相同
				 *   注意：
				 *  @说明  比较两个long数组是否相等，这里的相等值元素个数和元素相同。
				 *  @原理 1- 如果数组长度不等，直接返回不相同
				 *       2- 长度相等的话，为不改变原数组的引用传递，使用System.arraycopy方法
				 *       拷贝两个一样的对比数组。
				 *       3- 使用嵌套循环对比两个数组中的元素,如果想等将两个元素全部标成0，
				 *       4-循环完毕后，从新循环看两个数组元素是否都为0,,如果全部为0,则返回相等，否则为不相等
				 *  @param l1  第一个long[]数组 
				 *  @param l2  第二个long[]数组 
				 *  @return boolean  true为相等，false为不等
				 *  @注意 使用Arrays.equals(new int[]{0,3}, new int[]{3,0});
				 *   方法比较时 因为数组是有顺序的，所以 即使元素都相同因顺序不同 数组也不是相等的
				 *    所以这里的方法是为了查看数组元素是否相等 与顺序无关。这里只是对long[]数组的操作
				 *    如果需要按需求对比别的数组，可按相同逻辑编程。
				 * */
				
				public static boolean arrayEquals(long[] l1, long[] l2){
					if(l1.length!=l2.length){
						return false;
					}
					long[] t1 = new long[l1.length] ;
					long[] t2 = new long[l2.length] ;
					System.arraycopy(l1, 0, t1, 0, l1.length);
					System.arraycopy(l2, 0, t2, 0, l2.length);
					
					for(int i=0;i<l1.length;i++){
						for(int j=0;j<l1.length;j++){
							if(t1[i]==t2[j]){
								t1[i]=0;
								t2[j]=0;
							}
						}
					}
					
					for(int i=0;i<l1.length;i++){
						if(t1[i]!=0||t2[i]!=0){
							return false;
						}
					}
					return true;
				}
				
				/**
				 * @说明 警示及测验，这种方法效率很低，虽然采用二分法查找，但因为初始值与
				 *  实际值的区间远远小于二分法循环次数，并且对于某些值这种方法准确率也有误差
				 *  比如100，在分解时decomposerTrop方法可以找到9个解的参数，但二分法找到的
				 *  是7，因为参数个数为8时没有解，所以二分法向下搜索到7，但实际9却可以。
				 * @param var 
				 * @return
				 */
				@Deprecated
				public static int decomposerTrop2(long var){	
					boolean flag = true ;
					int flag2 = (int) Math.round(Math.sqrt(var));
					int flag1 = flag2/4;
					int n = flag2/2;
					System.out.println("0 n="+n+" flag1="+flag1+" flag2="+flag2);
					if(var<2){
						return 0;
					}
					if(var==2||var==3){
						return 1;
					}	
					while(flag){
						if(isExisteUnitArray(var, n)){
							flag1 = n;	
							System.out.println("1 n="+n+" flag1="+flag1+" flag2="+flag2);
						}else{
							flag2 = n;		
							System.out.println("2 n="+n+" flag1="+flag1+" flag2="+flag2);
						}
						if(flag2<=flag1+1){
							flag = false;
							System.out.println("3 n="+n+" flag1="+flag1+" flag2="+flag2);
							return flag1;
						}
						n = (flag1+flag2)/2;		
					}	
					System.out.println("最后输出:n="+n+" flag1="+flag1+" flag2="+flag2);
					return flag1;
				}
				/**
				 *  @说明  得到一个数可分解为最多多少个素数之和  注意此结果 不包含1 ,目前效率最高
				 *  @原理  确定一个值即最终结果值n，是要搜索数字的开方，如果满足有解的要求，则向上探测n+1的值是否满足要求。
				 *       如果满足继续向上探测，如果不满足就结束。返回n。如果第一次就不满足则向下探测，如果不满足条件，则继续向下探测n-1。
				 *       如果满足则返回n。
				 *  @param var 要分解的数。
				 *  @return int 能够陪分解的最多个数
				 *  @注意  合理选择初始值n，很重要。此方法使用了 isExisteUnitArray用于探测是否存在一个解。
				 *  并且注意这个方法是探测解的元素不重复的最多解个数，因为如果可重复
				 *  则将大量存在2,2,2,2....这样的解们没有太大意义 
				 * */
				public static int decomposerTrop(long var){
					int initial = (int)Math.sqrt(var);
					return decomposerTrop(var, initial);
				}	
				public static int decomposerTrop(long var,int initial){	
					if(var<2){
						return 0;
					}
					boolean flag;
					int n = initial;
					if(n==0){
						n=1;
					}
					flag = isExisteUnitArray(var, n);
					if(flag){
						while(flag){
							n = n+1;
							flag = isExisteUnitArray(var, n);
						}
						return n-1;
					}else{
						while(!flag&&n>=0){
							n = n-1;
							flag = isExisteUnitArray(var, n);						
						}
						
						return n;
					}
				
				}
				
				/**
				 *  
				 *  @说明  判断对于给定的解的个数，要分解的数字是否满足。
				 *  @原理  这个方法主要是设定初始值和对结果进行判断。
				 *  @param var 要分解的数。
				 *  @param num 解的个数。
				 *  @return boolean  true代表可以被分解，false表示不能被分解
				 *  @注意  此方法被decomposerTrop调用，它主要是设定初始值并将值传给另一个isExisteUnitArray方法。
				 *  并且注意这个方法是探测解的元素不重复的最多解个数，因为如果可重复
				 *  则将大量存在2,2,2,2....这样的解们没有太大意义   
				 * */
				public static boolean isExisteUnitArray(long var, int num){
					if(num<=0){
						return false;
					}
					long[] l = new long[num];			
					isExisteUnitArray(var, num,l,2);		
					if(l.length>0&&l[0]!=0){
						return true;
					}else{
						return false;
					}
					
				}
				
				
				/**
				 *  
				 *  @说明   此方法用递归的原理探测给定解的个数
				 *  @原理  此方法类似于getUnitArray(long var, int num,List<long[]> llongs, long[] arr,boolean no_ensemble,boolean unitValue,int n)方法
				 *       只是如果有一个接被找到，那么立即返回，如果没找到则要循环运行试图找到一个解，如果所有可能都被探测过还没找到则同样返回解但是解的元素将不会满足调用判断解的方法的要求
				 *  @param var 要求解的数字 
				 *  @param num 要求每个解的元素个数
				 *  @param arr 要求每个解的元素数组
				 *  @param n 解的具体元素，一般由2开始，因为2是最小的质数
				 *  @注意  此方法因为是探测使用被isExisteUnitArray(long var, int num)调用
				 *  所以不必有返回值，而是直接改变参数arr。并且注意这个方法是探测解的元素不重复的最多解个数，因为如果可重复
				 *  则将大量存在2,2,2,2....这样的解们没有太大意义   
				 * */

				public static void isExisteUnitArray(long var,int num, long[] arr,int n){
					if(arr.length==0){
						return;
					}
					if(arr[0]!=0){
						return;
					}			
					if(num==1&&isPremier(var)&&(arr.length==1||var!=arr[1])){
						arr[0] = var;
						
						return;
					}
							if(num>1){
								
									for(int i=n;i<=var/num;i++){
										if(isPremier(i)){
											arr[num-1]=i;
											
											isExisteUnitArray(var-i, num-1,arr,i+1);							
										}
									}
				
					 }
						
				}
				
				/**
				 * @说明  求var最多可分为多少不同质数相加结果
				 *        只是在使用decomposerTrop(var, initial)时对
				 *        initial参数做了修正。
				 *        @param var 要求解的数字
				 *        @return int 解的不相同参数个数      
				 * **/
				public static int newdecomposerTrop(int var){
					int sum = 0,count=0;
					int[] a = getArray(getPremierChiffre(var));
					for(int i:a){
						 sum+=i;
						 if(sum>var){
							break;
						 }
						 count++;
					}
					return	decomposerTrop(var, count);	
				}
				
				/**
				 * @说明  力求以最快的方法求一个给定大数的一个素数相加解
				 *     之所以说Random 1就是只要求一个解，2解的参数的个数不确定，
				 *     3 可以有相同的解参数。 4 只要求每个参数都是素数即可。
				 *     5程序里使用random类，所以有不确定性
				 *     @param var 要求解的数
				 *     @return 一个解
				 * */
				
 }

import java.math.BigDecimal;

public class SomeBigDecimal {
	public static void main(String[] args){
		String s1 = "0.005";
		String s2 = "0.00007";
		String s3 = "-0.012";
		BigDecimal bd1 = new BigDecimal(s1);
		BigDecimal bd2 = new BigDecimal(s2);
		BigDecimal bd3 = new BigDecimal(s3);
		
		System.out.println(bd3.abs()); //絕對值
		System.out.println(bd2.negate()); //取負數
		System.out.println(bd2.scale());//位數
		System.out.println(bd2.setScale(4, BigDecimal.ROUND_HALF_UP)); //將bd2四捨五入至小數第4位
		System.out.println(bd1.precision()); //精度
		
		//基本四則運算
		BigDecimal sum = bd1.add(bd3); //加
		System.out.println(sum);
		BigDecimal difference = bd1.subtract(bd2); //減
		System.out.println(difference);
		BigDecimal product = bd1.multiply(bd2); //乘
		System.out.println(product.doubleValue());
		//除
		//BigDecimal quotient = bd1.divide(bd2);//若無法除盡會拋Non-terminating decimal expansion,故需指定位數及進位方式
		BigDecimal quotient = bd1.divide(bd2, 5, BigDecimal.ROUND_HALF_UP);//將商四捨五入至小數第5位
		//BigDecimal quotient = bd1.divide(bd2, 5, BigDecimal.ROUND_CEILING);//正數無條件進位 負數無條件捨去
		//BigDecimal quotient = bd1.divide(bd2, 5, BigDecimal.ROUND_DOWN);//無條件捨去
		//BigDecimal quotient = bd1.divide(bd2, 5, BigDecimal.ROUND_FLOOR);//正數無條件捨去 負數無條件進位
		//BigDecimal quotient = bd1.divide(bd2, 5, BigDecimal.ROUND_UP);//無條件進位
		System.out.println(quotient);
		//餘數
		BigDecimal remainder = bd1.remainder(bd2);
		System.out.println(remainder);
		
		//n次方
		System.out.println(bd1.pow(2));
		
		BigDecimal bd4 = new BigDecimal("17");
		BigDecimal bd5 = new BigDecimal("13");
		//比大小，a.compareTo(b)
		//a<b : -1; a=b : 0; a>b: 1
		System.out.println(bd4.compareTo(bd5));
		

	}
}
package security;

import java.security.SecureRandom;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.SecretKey;
import javax.crypto.Cipher;

/**
 * 实现了DES对称加密算法，要求密钥长度大于等于8位
 * 
 */
public class DES
{
	public DES()
	{
	}
	//测试
	public static void main(String args[])
	{
		//待加密内容
		String str = "caitao";
		//密码，长度要是8的倍数
		//String sharedKey = "9588028820109132570743325311898426347857298773549468758875018579537757772163084478873699447306034466200616411960574122434059469100235892702736860872901247123456";
		String sharedKey = "12345678";
		byte[] databyte = str.getBytes();
		
		byte[] result = DES.encrypt(databyte, sharedKey);
		
		System.out.println("篡改之前");
		for(int i = 0; i < result.length; i++) System.out.print(result[i] + " ");
		System.out.println();
		
		result[2] = 'a';
		
		System.out.println("篡改之后");
		for(int i = 0; i < result.length; i++) System.out.print(result[i] + " ");
		System.out.println();
		
		//直接将如上内容解密
		try
		{
			byte[] decryResult = DES.decrypt(result, sharedKey);
			System.out.println("解密后：\n" + new String(decryResult));
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}

	}

	/**
	* 加密
	* @param datasource byte[]
	* @param sharedKey String
	* @return byte[]
	*/
	public static byte[] encrypt(byte[] datasource, String sharedKey)
	{
		try
		{
			SecureRandom random = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(sharedKey.getBytes());
			//创建一个密匙工厂，然后用它把DESKeySpec转换成
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			//Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance("DES");
			//用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
			//现在，获取数据并加密
			//正式执行加密操作
			return cipher.doFinal(datasource);
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
		return null;
	}
	/**
	* 解密
	* @param src byte[]
	* @param sharedKey String
	* @return byte[]
	* @throws Exception
	*/
	public static byte[] decrypt(byte[] src, String sharedKey) throws Exception
	{
		// DES算法要求有一个可信任的随机数源
		SecureRandom random = new SecureRandom();
		// 创建一个DESKeySpec对象
		DESKeySpec desKey = new DESKeySpec(sharedKey.getBytes());
		// 创建一个密匙工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		// 将DESKeySpec对象转换成SecretKey对象
		SecretKey securekey = keyFactory.generateSecret(desKey);
		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance("DES");
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, random);
		// 真正开始解密操作
		return cipher.doFinal(src);
	}

}

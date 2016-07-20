package com.zhumeng.dream.util.spring;
import java.util.Properties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import com.zhumeng.dream.util.encode.AESUtil;
/**
 * 重写读取系统配置文件方法，对数据库开头的相关信息进行解密
 * @author jerry
 *
 */
public class EncryptablePropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	private static final String key = "0002000200020002";

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactory,
			Properties props) throws BeansException {

		try {
			String driverClassName = props.getProperty("jdbc.driverClassName");
			if (driverClassName != null && !"".equals(driverClassName)) {
				props.setProperty("jdbc.driverClassName", AESUtil.decrypt(key, driverClassName));
			}
			
			String url = props.getProperty("jdbc.url");
			if (url != null && !"".equals(url)) {
				props.setProperty("jdbc.url", AESUtil.decrypt(key, url));
			}
			
			String username = props.getProperty("jdbc.username");
			if (username != null && !"".equals(username)) {
				props.setProperty("jdbc.username", AESUtil.decrypt(key, username));
			}
			
			String password = props.getProperty("jdbc.password");
			if(password != null && !"".equals(password)){
				props.setProperty("jdbc.password", AESUtil.decrypt(key, password));
			}
			
			//解析第二个数据源，如果没有设置，则设置为同第一个数据源一样
			String url2 = props.getProperty("jdbc.url.two");
			if (url2 == null || url2.isEmpty()) {
				props.setProperty("jdbc.url.two", AESUtil.decrypt(key, url));
			}
			else {
				props.setProperty("jdbc.url.two", AESUtil.decrypt(key, url2));
			}
			String username2 = props.getProperty("jdbc.username.two");
			if (username2 == null || username2.isEmpty()) {
				props.setProperty("jdbc.username.two", AESUtil.decrypt(key, username));
			}
			else {
				props.setProperty("jdbc.username.two", AESUtil.decrypt(key, username2));
			}
			String password2 = props.getProperty("jdbc.password.two");
			if (password2 == null || password2.isEmpty()) {
				props.setProperty("jdbc.password.two", AESUtil.decrypt(key, password));
			}
			else {
				props.setProperty("jdbc.password.two", AESUtil.decrypt(key, password2));
			}
			
			super.processProperties(beanFactory, props);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BeanInitializationException(e.getMessage());
		}
	}

	
	public  static void main(String[] args) throws Exception{
//				String jdbc.url=jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8
//				jdbc.username=root
//				jdbc.password=zdh
		
		String url ="jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8";
		String username ="root";
		String password ="zdh";
		String newUrl = AESUtil.encrypt(key,url);
		String newRoot = AESUtil.encrypt(key,username);
		String newPassword = AESUtil.encrypt(key,password);
		
		System.out.println("newUrl="+newUrl+";     newRoot="+newRoot+";  newPassword="+newPassword);
	}

}
	
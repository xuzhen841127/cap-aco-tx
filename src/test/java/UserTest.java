import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.yonyou.aco.Application;
import com.yonyou.aco.user.service.IUserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserTest {
	@Autowired
	private IUserService userService;

	@Test
	public void contextLoads() {
		try {
			/*
			 * List<UserEntity> userList = userService.findAllUser(); userList.forEach(user
			 * -> System.out.println(user.getName()));
			 */
			boolean result = userService.updateUser("13957372211", "123456");
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
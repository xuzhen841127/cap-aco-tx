import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.yonyou.aco.Application;
import com.yonyou.aco.realpay.service.IRealpayService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RealpayTest {
	@Autowired
	IRealpayService realpayService;

	@Test
	public void contextLoads() {
		/*
		 * String title = "指标拨款单批"; long pageIndex = 1; long pageSize = 5;
		 * IPage<RealpayEntity> page = new Page<RealpayEntity>(pageIndex, pageSize);
		 * QueryWrapper<RealpayEntity> queryWrapper = new QueryWrapper<RealpayEntity>();
		 * realpayMapper.selectPage(page, queryWrapper); List<RealpayEntity> list =
		 * page.getRecords(); for (RealpayEntity entity : list) {
		 * System.out.println(entity.getMenuName()); }
		 */

		/*
		 * QueryWrapper<RealpayEntity> queryWrapper = new QueryWrapper<RealpayEntity>();
		 * queryWrapper.like("menu_name", title); queryWrapper.or();
		 * queryWrapper.like("remark", title); IPage<RealpayEntity> realpayIPage = new
		 * Page<RealpayEntity>(pageIndex, pageSize);
		 */

		int result = realpayService.findRealpayTotal("18611123594");
		System.out.println(result);
	}
}
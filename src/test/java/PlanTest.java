import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.yonyou.aco.Application;
import com.yonyou.aco.plan.service.IPlanService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class PlanTest {
	@Autowired
	private IPlanService planService;

	@Test
	public void contextLoads() {
		try {
			/*
			 * PlanEntity planEntity = planService.getById(100005);
			 * planEntity.setStatus(Double.valueOf(2)); TimeZone time =
			 * TimeZone.getTimeZone("ETC/GMT-8"); TimeZone.setDefault(time);
			 * planEntity.setOutdate(DateUtil.formatDate(new Date()));
			 * planEntity.setTaskOpinion("统一"); boolean result =
			 * planService.saveOrUpdate(planEntity);
			 */

			int result = planService.findPlanTotal("13806715255");
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
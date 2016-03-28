import com.zhw.UserEntity;
import com.zhw.service.IUserService;
import com.zhw.service.IUserService2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by zhw on 16/3/24.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestUserService {

    @Resource
    IUserService userService;


    @Test
    public void testFun1() throws Exception {
        userService.fun1();
    }

    @Test
    public void testFun2() throws Exception {
        userService.fun2();
    }

    @Test
    public void testFun3() throws Exception {
        userService.fun3();
    }

    @Test
    public void testFun4() throws Exception {
        userService.fun4();
    }

    @Test
    public void testFun5() throws Exception {
        userService.fun5();
    }

    @Test
    public void testFun6() throws Exception {
        userService.fun6();
    }

    @Test
    public void testFun7() throws Exception {
        userService.fun7();
    }

    @Test
    public void testFun8() throws Exception {
        userService.fun8();
    }


}

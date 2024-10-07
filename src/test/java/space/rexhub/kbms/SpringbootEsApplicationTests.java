package space.rexhub.kbms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import space.rexhub.kbms.bussiness.es_dao.EsDocDao;


/**
 * Description: 测试
 *
 * @author Rex
 * @date 2024-04-20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootEsApplicationTests {

    @Autowired
    EsDocDao esDocDao;
    @Test
    public void test(){
//        Iterable<EsDocEntity> all = esDocDao.findAll();
//        Iterator<EsDocEntity> iterator = all.iterator();
//        System.out.println(iterator.hasNext());
    }
    @Test
    public void contextLoads() {
    }
}

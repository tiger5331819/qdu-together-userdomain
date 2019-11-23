package qdu.together.main;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import qdu.together.userdomin.main.DemoApplication;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DemoApplication.class)
class DemoApplicationTests {
	@Test
	void contextLoads()  {
		/*String resource = "qdu/properties/MybatisConfig.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		System.out.println("hello");
		try (SqlSession session = sqlSessionFactory.openSession()) {
			TestMapper mapper=session.getMapper(TestMapper.class);
			qdu.together.dao.Test test = mapper.findbyid(1);
			mapper.deleteTest(3);

			/* test.setPassword("su");
			mapper.updateTest(test); */

			/* Test test2=new Test(3, "test", "test", "test");
			mapper.insertTest(test2); */

			/* est test = (Test) session.selectOne("qdu.mapping.TestMapper.findbyid", 1);
			Test test2=new Test(2,"taijianya","123","Hello suhuyuan!");
			test.setPassword("123");
			session.insert("qdu.mapping.TestMapper.insertTest",test2);
			session.update("qdu.mapping.TestMapper.updateTest",test );
			System.out.println(session.delete("qdu.mapping.TestMapper.deleteTest", 2)); 
			
			session.commit();
			System.out.println(test); 
		}*/
			
		//Test test= (Test) mapper.findid(1);
		//System.out.println(test);
	}
}

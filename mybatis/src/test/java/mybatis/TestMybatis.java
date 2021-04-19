package mybatis;

import java.util.List;

import com.panzhanpeng.mybatis.mapper.TuserMapper;
import com.panzhanpeng.mybatis.object.Tuser;
import com.panzhanpeng.mybatis.session.SqlSession;
import com.panzhanpeng.mybatis.session.SqlsessionFactory;

public class TestMybatis {

	public static void main(String[] args) {
		SqlsessionFactory sqlsessionFactory = new SqlsessionFactory();
		SqlSession session = sqlsessionFactory.openSession();
		TuserMapper tuserMapper = session.getMapper(TuserMapper.class);
		Tuser user = tuserMapper.selectByPrimaryKey("3");
		System.out.println(user.getId() + user.getName() + user.getEmail());
		
		List<Tuser> list = (List<Tuser>) tuserMapper.selectAll();
		for (Tuser tuser : list) {
			System.out.println(tuser.getId() + tuser.getName() + tuser.getEmail());
		}
	}

}

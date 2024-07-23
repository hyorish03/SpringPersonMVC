package com.ureca.person.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ureca.person.dto.Person;
import com.ureca.util.DBUtil;

//스프링컨테이너의 객체관리를 받고 싶어요!!  ==> @Component
@Repository
public class PersonDAOImpl implements PersonDAO {

	@Autowired //스프링 컨테이너에 등록된 객체들중 일치하는 자료형을 통해 Injection
	DBUtil dbUtil;
	
	@Override
	public int insert(Person person) throws SQLException {
		Connection conn = dbUtil.getConnection();
		String sql="insert into person (name,age,job) values (?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);//DB서버에 sql문을 먼저 전송
		   //물음표 수만큼의 데이터 설정!!
//		   pstmt.setString(param인덱스번호,설정데이터);
		   pstmt.setString(1, person.getName());
		   pstmt.setInt   (2, person.getAge());
		   pstmt.setString(3, person.getJob());
		return pstmt.executeUpdate();//DB서버에게 sql문 실행요청 (실행시점)
	}

	@Override
	public int update(Person person) throws SQLException {
		Connection conn = dbUtil.getConnection();
		String sql="update person set age=?, job=? where no=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);//DB서버에 sql문을 먼저 전송
		   //물음표 수만큼의 데이터 설정!!
//		   pstmt.setString(param인덱스번호,설정데이터);
			pstmt.setInt   (1, person.getAge());
			pstmt.setString(2, person.getJob());
		    pstmt.setInt   (3, person.getNo());
		return pstmt.executeUpdate();//DB서버에게 sql문 실행요청 (실행시점)
	}

	@Override
	public int delete(int no) throws SQLException {
		Connection conn = dbUtil.getConnection();
		String sql="delete from person where no=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);//DB서버에 sql문을 먼저 전송
		   //물음표 수만큼의 데이터 설정!!
//		   pstmt.setString(param인덱스번호,설정데이터);
		   pstmt.setInt(1, no);
		return pstmt.executeUpdate();//DB서버에게 sql문 실행요청 (실행시점)
	}

	@Override
	public Person select(int no) throws SQLException {
		Connection conn = dbUtil.getConnection();
		String sql="select no,name,age,job from person where no=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);//DB서버에 sql문을 먼저 전송
		   //물음표 수만큼의 데이터 설정!!
//		   pstmt.setString(param인덱스번호,설정데이터);
		   pstmt.setInt(1, no);
		   
		ResultSet rs = pstmt.executeQuery();//DB서버에게 sql문 실행요청 (실행시점)
		//rs사용법==>1.행 얻어오기  2.행안의 열데이터 얻어오기
		
		Person person=null;
		if(rs.next()) {   //얻어올 행이 존재한다면 => 1.
			
			person = new Person(
										rs.getInt("no"),
										rs.getString("name"),
										rs.getInt("age"),
										rs.getString("job")
					                   );
		}
		
		
		return person;
	}

	@Override
	public List<Person> selectAll() throws SQLException {
		Connection conn = dbUtil.getConnection();
		String sql="select no,name,age,job from person";
		PreparedStatement pstmt = conn.prepareStatement(sql);//DB서버에 sql문을 먼저 전송
		   //물음표 수만큼의 데이터 설정!!
//		   pstmt.setString(param인덱스번호,설정데이터);
		   
		ResultSet rs = pstmt.executeQuery();//DB서버에게 sql문 실행요청 (실행시점)
		//rs사용법==>1.행 얻어오기  2.행안의 열데이터 얻어오기
		
		List<Person> list= new ArrayList<>();
		
		while(rs.next()) {   //얻어올 행이 존재한다면 => 1.
//			list.add(new Person());
			list.add(new Person(rs.getInt("no"),
								rs.getString("name"),
								rs.getInt("age"),
								rs.getString("job")));
		}
		
		
		return list;
	}

}




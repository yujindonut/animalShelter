package model.dao.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import model.Animal;
import model.dao.mybatis.mapper.AnimalMapper;

/**
 * 사용자 관리를 위해 데이터베이스 작업을 전담하는 DAO 클래스
 * Animal 테이블에서 커뮤니티 정보를 추가, 수정, 삭제, 검색 수행 
 */
public class AnimalDAO {
	private SqlSessionFactory sqlSessionFactory;
	
	public AnimalDAO() {
		String resource = "mybatis-config.xml";
		
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}System.out.println("dlrjsehlsk,,,,,,,,");
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		System.out.println("myba@tis,,,,,,,,생성,,,,,,ehoTwl,,,");
	}
	
	/**
	 * animal 테이블에 새로운 행 생성 (PK 값은 Sequence를 이용하여 자동 생성)
	 */
	public Animal create(Animal animal) {
		System.out.println("여긴왔누11");
		SqlSession sqlSession = sqlSessionFactory.openSession();
		System.out.println("여긴왔누");
		try {
			int result = sqlSession.getMapper(AnimalMapper.class).insertAnimal(animal);
			if (result > 0) {
				sqlSession.commit();
			} 
			return animal;
		} finally {
			sqlSession.close();
		}
	}

	/**
	 * 기존의 animal 정보를 수정
	 */
	public int update(Animal comm) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			int result = sqlSession.getMapper(AnimalMapper.class).updateAnimal(comm);
			if (result > 0) {
				sqlSession.commit();
			} 
			return result;
		} finally {
			sqlSession.close();
		}
	}



	/**
	 * 주어진 ID에 해당하는 animal객체를 삭제.
	 */
	public int remove(int commId) {		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			int result = sqlSession.getMapper(AnimalMapper.class).deleteAnimal(commId);
			if (result > 0) {
				sqlSession.commit();
			} 
			return result;	
		} finally {
			sqlSession.close();
		}
	}

	/**
	 * 주어진  ID에 해당하는 동물 정보를 데이터베이스에서 찾아 Animal 도메인 클래스에 
	 * 저장하여 반환.
	 */
	public Animal findAnimal(int animal_id) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.getMapper(AnimalMapper.class).findAnimal(animal_id);			
		} finally {
			sqlSession.close();
		}
	}


	/**
	 * 전체 동물 정보를 검색하여 List에 저장 및 반환*/
		public List<Animal> findAnimalList() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.getMapper(AnimalMapper.class).findAnimalList();			
		} finally {
			sqlSession.close();
		}
	} 

		/**
		 * 동물 정보를 검색하여 List에 저장 및 반환*/
		public List<Animal> searchAnimalList(Animal animal){
			Map<String,String> paramString=new HashMap<String,String>(2);
			paramString.put("animal_type",animal.getAnimal_type());
			paramString.put("location",animal.getLocation());
			Map<String,Integer> paramInt=new HashMap<String,Integer>(2);
			paramInt.put("category_id",animal.getCategory_id());
			paramInt.put("matched",animal.getMatched());
			 System.out.println("paramString"+paramString);
			System.out.println("animal_type"+animal.getAnimal_type());
		      System.out.println("category_id"+String.valueOf(animal.getCategory_id()));
		      System.out.println("matched"+String.valueOf(animal.getMatched()));
		      System.out.println("location"+animal.getLocation());
		     
			SqlSession sqlSession = sqlSessionFactory.openSession();
			try {
				return sqlSession.getMapper(AnimalMapper.class).searchAnimalList(paramString,paramInt);			
			} finally {
				sqlSession.close();
			}
		} 
	
}

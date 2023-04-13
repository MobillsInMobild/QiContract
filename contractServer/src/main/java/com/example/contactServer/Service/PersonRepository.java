package com.example.contactServer.Service;

import org.hibernate.annotations.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.contactServer.Model.Person;

import net.sf.json.JSONObject;

import java.util.Date;
import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Integer>
{
	
	//user.java
	@Modifying
	@Transactional
	@Query(value = "insert into user(address,privateKey) "
			+ "select :address,:privateKey from dual where NOT EXISTS (SELECT * FROM user WHERE address=:address)",nativeQuery = true)
	void addPerson(@Param("address") String address,@Param("privateKey") String privateKey);
	@Query(value="select address,phone,email,name,tips from user where address=:address",nativeQuery = true)
	List<Object[]> personDetails(@Param("address") String address);
	@Modifying
	@Transactional
	@Query(value ="UPDATE user SET privateKey=:privateKey,name=:name,email=:email,phone=:phone,tips=:tips where address=:address",nativeQuery = true)
	void updatePersonDetails(@Param("address")String address,@Param("privateKey")String privateKey,@Param("name")String name,@Param("email")String email,@Param("phone")String phone,@Param("tips")String tips);
	@Query(value = "select contracts from user where address=:address",nativeQuery = true)
	List<JSONObject[]> personContracts(@Param("address") String address);
	@Query(value="select contractId,content,contractAddress,target from contract where contractId=:contractId",nativeQuery = true)
	List<Object[]> getContract(@Param("contractId") Integer contractId);
	
	//contract.java
	@Modifying
	@Transactional
	@Query(value = "UPDATE user SET contracts=:contracts where address=:address",nativeQuery = true)
	void newContractStatus(@Param("address")String address,@Param("contracts")String contracts);
	@Query(value = "select target from contract where contractId=:contractId",nativeQuery = true)
	List<Object[]> getPDF(@Param("contractId")String contractId);
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO contract(contractId,user,contractAddress,content,userCount,userGroup) VALUES "
			+ "(:contractId,:user,:contractAddress,:content,:userCount,:userGroup)",nativeQuery = true)
	void newContract(@Param("contractId")String contractId,@Param("user")String user,@Param("contractAddress") String contractAddress,@Param("content")String content,@Param("userCount")Integer userCount,@Param("userGroup")String userGroup);
	@Query(value = "select pictures from contract where contractId=:contractId",nativeQuery = true)
	List<JSONObject[]> getPicture(@Param("contractId")String contractId);
	@Modifying
	@Transactional
	@Query(value = "UPDATE contract SET pictures=:pictures where contractId=:contractId",nativeQuery = true)
	void setPicture(@Param("pictures")String pictures,@Param("contractId")String contractId);
	@Query(value = "select contractAddress,userCount,content,userGroup from contract where contractId=:contractId",nativeQuery = true)
	List<Object[]> getContractInfo(@Param("contractId")String contractId);
	
	//ProcessingContract.java
	@Query(value = "select pictures,content from contract where contractAddress=:contractAddress",nativeQuery = true)
	List<Object[]> getPictureByAddress(@Param("contractAddress")String contractAddress);
	@Modifying
	@Transactional
	@Query(value = "UPDATE contract SET target=:pdf WHERE contractAddress=:contractAddress",nativeQuery = true)
	void setPDF(@Param("pdf")String pdf,@Param("contractAddress")String contractAddress);
}
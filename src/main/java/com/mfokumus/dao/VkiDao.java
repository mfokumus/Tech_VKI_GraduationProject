package com.mfokumus.dao;

import com.mfokumus.dto.VkiDto;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VkiDao implements IDaoGenerics<VkiDto> , Serializable {

    @Override
    public String speedData(Long id) {
        return null;
    }

    // ALL DELETE
    @Override
    public String allDelete() {
        return null;
    }

    //CREATE
    @Override
    public VkiDto create(VkiDto vkiDto) {
        try(Connection connection = getInterfaceConnection()){
            // Manipulation : executeUpdate() [create, delete, update]
            // Sorgularda   : executeQuery()  [list, find, update]
            //TRANSACTION YAPISINI YAZALIM
            connection.setAutoCommit(false); // Default:true
            String sql ="INSERT INTO `cars`.`vki` (`boy`, `kilo`, `vucut_kitle_index`, `user_id`)\n" +
                    " VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, vkiDto.getBoy());
            preparedStatement.setDouble(2,vkiDto.getKilo());
            preparedStatement.setDouble(3,vkiDto.getVucutKitleIndex());
            preparedStatement.setLong(4,vkiDto.getUserId());
            // executeUpdate: create, delete, update için kullanılır.
            int rowsEffected = preparedStatement.executeUpdate();
            // eğer ekleme yapılmamışsa -1 değerini döner
            if (rowsEffected > 0) {
                System.out.println("Basarili bir şekilde eklendi.");
                connection.commit();
            }else {
                System.err.println(VkiDao.class+ "!!! Ekleme gerceklestirilemedi.");
                connection.rollback();
            }
        }catch (SQLException sql){
            sql.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }// end create

    // FIND BY ID
    @Override
    public VkiDto findById(Long id) {
        return null;
    }

    @Override
    public VkiDto findByEmail(String email) {
        return null;
    }

    // LIST
    @Override
    public ArrayList<VkiDto> list() {
        ArrayList<VkiDto> list = new ArrayList<>();
        VkiDto vkiDto;
        try(Connection connection = getInterfaceConnection()){
            // Manipulation : executeUpdate() [create, delete, update]
            // Sorgularda   : executeQuery()  [list, find, update]
            String sql = "SELECT * FROM cars.vki";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                // id, boy, kilo, vucutkitleindex, userID
                vkiDto = new VkiDto();
                vkiDto.setId(resultSet.getLong("id"));
                vkiDto.setBoy(resultSet.getDouble("boy"));
                vkiDto.setKilo(resultSet.getDouble("kilo"));
                vkiDto.setVucutKitleIndex(resultSet.getDouble("vucut_kitle_index"));
                vkiDto.setUserId(resultSet.getLong("user_id"));
                vkiDto.setSystemCreatedDate(resultSet.getDate("system_created_date"));
                list.add(vkiDto);
            }
            return list; // eğer başarılı ise return registerDto
        }catch (SQLException sql){
            sql.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }// end list

    // UPDATE
    @Override
    public VkiDto update(Long id, VkiDto vkiDto) {
        try(Connection connection = getInterfaceConnection()){
            // Manipulation : executeUpdate() [create, delete, update]
            // Sorgularda   : executeQuery()  [list, find, update]
            //TRANSACTION YAPISINI YAZALIM
            connection.setAutoCommit(false); // Default:true
            String sql ="UPDATE `cars`.`vki` SET `boy`=?, `kilo`=?, `vucut_kitle_index` =?,  `user_id` =?" +
                    " WHERE `id` =?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, vkiDto.getBoy());
            preparedStatement.setDouble(2,vkiDto.getKilo());
            preparedStatement.setDouble(3,vkiDto.getVucutKitleIndex());
            preparedStatement.setLong(4, vkiDto.getUserId());
            preparedStatement.setLong(5,id);
            // executeUpdate: create, delete, update için kullanılır.
            int rowsEffected = preparedStatement.executeUpdate();
            // eğer ekleme yapılmamışsa -1 değerini döner
            if (rowsEffected > 0) {
                System.out.println(VkiDao.class+ " Basarili Güncelleme Tamamdir.");
                connection.commit(); // Basarili
            }else {
                System.err.println(VkiDao.class+ "!!! Basarisiz Güncelleme Tamamdir");
                connection.rollback(); // Basarisiz
            }
        }catch (SQLException sql){
            sql.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }// end update

    @Override
    public VkiDto updateRemaing(Long id, VkiDto vkiDto) {
        return null;
    }

    // DELETE
    @Override
    public VkiDto deleteById(VkiDto vkiDto) {
        return null;
    }

    // FIND BY USER_ID
    public VkiDto findByUserId(Long id) {
        VkiDto vkiDto = new VkiDto();
        try(Connection connection = getInterfaceConnection()){
            // Manipulation : executeUpdate() [create, delete, update]
            // Sorgularda   : executeQuery()  [list, find, update]
            String sql ="SELECT * FROM cars.vki where user_id="+id;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                // id, boy, kilo, user_id, remaining_number, is_passive
                vkiDto = new VkiDto();
                vkiDto.setId(resultSet.getLong("id"));
                vkiDto.setBoy(resultSet.getDouble("boy"));
                vkiDto.setKilo(resultSet.getDouble("kilo"));
                vkiDto.setUserId(resultSet.getLong("user_id"));
                vkiDto.setSystemCreatedDate(resultSet.getDate("system_created_date"));
            }
            return vkiDto; // eğer başarılı ise return vkiDto
        }catch (SQLException sql){
            sql.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }// end class findByUserId

    //*****************************************************************************************
    public Long deleteByUserId(Long id) {
        try(Connection connection = getInterfaceConnection()){
            // executeUpdate() [create, delete, update]
            // Sorgularda   : executeQuery()  [list, find, update]
            //TRANSACTION YAPISINI YAZALIM
            connection.setAutoCommit(false); // Default:true
            String sql ="DELETE FROM `cars`.`vki` WHERE `user_id` = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,id);
            // executeUpdate: create, delete, update için kullanılır.
            int rowsEffected = preparedStatement.executeUpdate();
            // eğer güncelleme yapılmamışsa -1 değerini döner
            if (rowsEffected > 0) {
                System.out.println(VkiDao.class+ " \nBasarili Silme Tamamdir.");
                connection.commit();
            }else {
                System.err.println(VkiDao.class+ " \n!!! Basarisiz Silme Tamamdir");
                connection.rollback();
            }
            return id; // eğer başarılı ise return registerDto
        }catch (SQLException sql){
            sql.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    //*****************************************************************************************

}// end class


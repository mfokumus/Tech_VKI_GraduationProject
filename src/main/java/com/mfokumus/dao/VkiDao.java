package com.mfokumus.dao;

import com.mfokumus.dto.RegisterDto;
import com.mfokumus.dto.VkiDto;
import com.mfokumus.roles.ERoles;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class VkiDao implements IDaoGenerics<VkiDto> , Serializable {

    // SPEED DATA
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
            String sql ="INSERT INTO `cars`.`vki` (`boy`,`kilo`,`vucut_kitle_index`)\n" +
                    " VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setFloat(1, vkiDto.getBoy());
            preparedStatement.setFloat(2,vkiDto.getKilo());
            preparedStatement.setFloat(3,vkiDto.getVucut_kitle_index());
            // executeUpdate: create, delete, update için kullanılır.
            int rowsEffected = preparedStatement.executeUpdate();
            // eğer ekleme yapılmamışsa -1 değerini döner
            if (rowsEffected > 0) {
                System.out.println(VkiDao.class+ " Basarili Ekleme Tamamdir.");
                connection.commit();
            }else {
                System.err.println(VkiDao.class+ "!!! Basarisiz Ekleme Tamamdir");
                connection.rollback();
            }
        }catch (SQLException sql){
            sql.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    // FIND BY ID
    @Override
    public VkiDto findById(Long id) {
        VkiDto vkiDto = new VkiDto();
        try(Connection connection = getInterfaceConnection()){
            // Manipulation : executeUpdate() [create, delete, update]
            // Sorgularda   : executeQuery()  [list, find, update]
            String sql ="SELECT * FROM cars.vki where id="+id;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                // nick_name, email_address, password, roles, remaining_number, is_passive
                vkiDto = new VkiDto();
                vkiDto.setId(resultSet.getLong("id"));
                vkiDto.setBoy(resultSet.getFloat("boy"));
                vkiDto.setKilo(resultSet.getFloat("kilo"));
                vkiDto.setSystemCreatedDate(resultSet.getDate("system_created_date"));
            }
            return vkiDto; // eğer başarılı ise return registerDto
        }catch (SQLException sql){
            sql.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public VkiDto findByEmail(String email) {
        return null;
    }

    // LIST
    @Override
    public ArrayList<VkiDto> list() {
        return null;
    }

    // UPDATE
    @Override
    public VkiDto update(Long id, VkiDto vkiDto) {
        try(Connection connection = getInterfaceConnection()){
            // Manipulation : executeUpdate() [create, delete, update]
            // Sorgularda   : executeQuery()  [list, find, update]
            //TRANSACTION YAPISINI YAZALIM
            connection.setAutoCommit(false); // Default:true
            String sql ="UPDATE `cars`.`vki` SET `boy`=?, `kilo`=?, `vucut_kitle_index`=?" +
                    " WHERE `id` = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setFloat(1, vkiDto.getBoy());
            preparedStatement.setFloat(2,vkiDto.getKilo());
            preparedStatement.setFloat(3,vkiDto.getVucut_kitle_index());
            // executeUpdate: create, delete, update için kullanılır.
            int rowsEffected = preparedStatement.executeUpdate();
            // eğer ekleme yapılmamışsa -1 değerini döner
            if (rowsEffected > 0) {
                System.out.println(VkiDao.class+ " Basarili Güncelleme Tamamdir.");
                connection.commit();
            }else {
                System.err.println(VkiDao.class+ "!!! Basarisiz Güncelleme Tamamdir");
                connection.rollback();
            }
        }catch (SQLException sql){
            sql.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public RegisterDto updateRemaing(Long id, VkiDto vkiDto) {
        return null;
    }

    // DELETE
    @Override
    public VkiDto deleteById(VkiDto vkiDto) {
        try(Connection connection = getInterfaceConnection()){
            // Manipulation : executeUpdate() [create, delete, update]
            // Sorgularda   : executeQuery()  [list, find, update]
            //TRANSACTION YAPISINI YAZALIM
            connection.setAutoCommit(false); // Default:true
            String sql ="DELETE FROM `cars`.`vki` WHERE (`id` = ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, vkiDto.getId());
            // executeUpdate: create, delete, update için kullanılır.
            int rowsEffected = preparedStatement.executeUpdate();
            // eğer ekleme yapılmamışsa -1 değerini döner
            if (rowsEffected > 0) {
                System.out.println(VkiDao.class+ " Basarili Güncelleme Tamamdir.");
                connection.commit();
            }else {
                System.err.println(VkiDao.class+ "!!! Basarisiz Güncelleme Tamamdir");
                connection.rollback();
            }
        }catch (SQLException sql){
            sql.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}

package com.mfokumus.dao;

import com.mfokumus.dto.RegisterDto;
import com.mfokumus.roles.ERoles;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

/*
   Transaction: Create, Delete, Update
   connection.setAutoCommit(false) => Default:True
   connection.commit(); => basarili
   connection.rollback(); => basarisiz
*/

public class RegisterDao implements IDaoGenerics<RegisterDto> , Serializable {

    // SPEED DATA
    @Override
    public String speedData(Long id) {
        for (int i = 1 ; i<= id ; i++){
            try(Connection connection = getInterfaceConnection()){
                // Manipulation : executeUpdate() [create, delete, update]
                // Sorgularda   : executeQuery()  [list, find, update]
                //TRANSACTION YAPISINI YAZALIM
                connection.setAutoCommit(false); // Default:true
                String sql ="INSERT INTO `cars`.`register` (`nick_name`,`email_address`,`password`,`roles`,`remaining_number`,`is_passive`)\n" +
                        " VALUES (?, ?, ?,?,?,?)";
                String rnd = UUID.randomUUID().toString();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,"nickname() "+rnd);
                preparedStatement.setString(2,"emailAddress() "+rnd);
                preparedStatement.setString(3,"password() "+rnd);
                preparedStatement.setString(4, ERoles.USER.getValue());
                preparedStatement.setInt(5,5);
                preparedStatement.setBoolean(6, true);
                // executeUpdate: create, delete, update için kullanılır.
                int rowsEffected = preparedStatement.executeUpdate();
                // eğer ekleme yapılmamışsa -1 değerini döner
                if (rowsEffected > 0) {
                    System.out.println(RegisterDao.class+ " Basarili Ekleme Tamamdir.");
                    connection.commit();
                }else {
                    System.err.println(RegisterDao.class+ "!!! Basarisiz Ekleme Tamamdir");
                    connection.rollback();
                }
            }catch (SQLException sql){
                sql.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        System.out.println(id+" tane veri eklendi");
        return id + " tane veri eklendi";
    }
    // ALL DELETE
    @Override
    public String allDelete() {
        try(Connection connection = getInterfaceConnection()){
            // executeUpdate() [create, delete, update]
            // Sorgularda   : executeQuery()  [list, find, update]
            //TRANSACTION YAPISINI YAZALIM
            connection.setAutoCommit(false); // Default:true
            String sql ="DELETE FROM `cars`.`register`";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // executeUpdate: create, delete, update için kullanılır.
            int rowsEffected = preparedStatement.executeUpdate();
            // eğer güncelleme yapılmamışsa -1 değerini döner
            if (rowsEffected > 0) {
                System.out.println(RegisterDao.class+ " Basarili Butun Veriler Silme Tamamdir.");
                connection.commit();
            }else {
                System.err.println(RegisterDao.class+ "!!! Basarisiz Butun Veriler Silme Tamamdir");
                connection.rollback();
            }
        }catch (SQLException sql){
            sql.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list().size() + " tane veri silindi";
    }

    ////////////////////////////////////////////////////////

    // Sifreleme olustur (Encoder)
    public String generatebCryptPasswordEncoder(String value){
        // Sifrelemeyi olusturmak
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String rawPassword = bCryptPasswordEncoder.encode(value);
        return rawPassword;
    }

    // Sifre karsilastir (Match)
    public Boolean matchbCryptPassword(String fistValue, String rawPassword){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean isMatch=bCryptPasswordEncoder.matches(fistValue,rawPassword);
        return isMatch;
    }

    public static void main(String[] args) {
        RegisterDao registerDao=new RegisterDao();
        String firstValue="123456";
        String rawPassword=registerDao.generatebCryptPasswordEncoder(firstValue);
        boolean result=registerDao.matchbCryptPassword(firstValue,rawPassword);
        System.out.println(result);
    }

    ///////////////////////////////////////////////////////
    // CREATE
    //INSERT INTO `cars`.`register` (`nick_name`,`email_address`,`password`,`roles`,`remaining_number`,`is_passive`)
    // VALUES ('computer', 'hamitmizrak@gmail.com', '123456','admin','5','1');
    @Override
    public RegisterDto create(RegisterDto registerDto) {
        try(Connection connection = getInterfaceConnection()){
            // Manipulation : executeUpdate() [create, delete, update]
            // Sorgularda   : executeQuery()  [list, find, update]
            //TRANSACTION YAPISINI YAZALIM
            connection.setAutoCommit(false); // Default:true
            String sql ="INSERT INTO `cars`.`register` (`nick_name`,`email_address`,`password`,`roles`,`remaining_number`,`is_passive`)\n" +
                    " VALUES (?, ?, ?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,registerDto.getuNickName());
            preparedStatement.setString(2,registerDto.getuEmailAddress());

            //registerDto.setuPassword(resultSet.getString("password"));
            preparedStatement.setString(3, generatebCryptPasswordEncoder(registerDto.getuPassword()));

            preparedStatement.setString(4,registerDto.getRolles());
            preparedStatement.setLong(5,registerDto.getRemainingNumber());
            preparedStatement.setBoolean(6,registerDto.getPassive());
            // executeUpdate: create, delete, update için kullanılır.
            int rowsEffected = preparedStatement.executeUpdate();
            // eğer ekleme yapılmamışsa -1 değerini döner
            if (rowsEffected > 0) {
                System.out.println(RegisterDao.class+ " Basarili Ekleme Tamamdir.");
                connection.commit();
            }else {
                System.err.println(RegisterDao.class+ "!!! Basarisiz Ekleme Tamamdir");
                connection.rollback();
            }
            return registerDto; // eğer başarılı ise return registerDto
        }catch (SQLException sql){
            sql.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    // FIND ID
    @Override
    public RegisterDto findById(Long id) {
        RegisterDto registerDto = null;
        try(Connection connection = getInterfaceConnection()){
            //DİKKAT: id long olduğu için tırnak içinde yazmıyoruz   örneğin: id=1
            String sql ="SELECT * FROM cars.register where id="+id;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // executeUpdate() [create, delete, update]
            // Sorgularda   : executeQuery()  [list, find, update]
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                // nick_name, email_address, password, roles, remaining_number, is_passive
                registerDto = new RegisterDto();
                registerDto.setId(resultSet.getLong("id"));
                registerDto.setuNickName(resultSet.getString("nick_name"));
                registerDto.setuEmailAddress(resultSet.getString("email_address"));
                registerDto.setuPassword(resultSet.getString("password"));
                registerDto.setRolles(resultSet.getString("roles"));
                registerDto.setRemainingNumber(resultSet.getLong("remaining_number"));
                registerDto.setPassive(resultSet.getBoolean("is_passive"));
                registerDto.setSystemCreatedDate(resultSet.getDate("system_created_date"));
            }
            return registerDto; // eğer başarılı ise return registerDto
        }catch (SQLException sql){
            sql.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    // FIND EMAIL
    @Override
    public RegisterDto findByEmail(String email) {
        RegisterDto registerDto = null;
        try(Connection connection = getInterfaceConnection()){
            //DİKKAT: email_address String olduğu için tırnak içinde yazıyoruz   örneğin: email="weqwe@gmail.com"
            String sql ="SELECT * FROM cars.register where email_address='"+email+"'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // executeUpdate() [create, delete, update]
            // Sorgularda   : executeQuery()  [list, find, update]
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                // nick_name, email_address, password, roles, remaining_number, is_passive
                registerDto = new RegisterDto();
                registerDto.setId(resultSet.getLong("id"));
                registerDto.setuNickName(resultSet.getString("nick_name"));
                registerDto.setuEmailAddress(resultSet.getString("email_address"));
                registerDto.setuPassword(resultSet.getString("password"));
                registerDto.setRolles(resultSet.getString("roles"));
                registerDto.setRemainingNumber(resultSet.getLong("remaining_number"));
                registerDto.setPassive(resultSet.getBoolean("is_passive"));
                registerDto.setSystemCreatedDate(resultSet.getDate("system_created_date"));
            }
            return registerDto; // eğer başarılı ise return registerDto
        }catch (SQLException sql){
            sql.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    // LIST
    @Override
    public ArrayList<RegisterDto> list() {
        ArrayList <RegisterDto> list = new ArrayList<>();
        RegisterDto registerDto;
        try(Connection connection = getInterfaceConnection()){
            //DİKKAT: email_address String olduğu için tırnak içinde yazıyoruz   örneğin: email="weqwe@gmail.com"
            String sql ="SELECT * FROM cars.register";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // executeUpdate() [create, delete, update]
            // Sorgularda   : executeQuery()  [list, find, update]
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                // nick_name, email_address, password, roles, remaining_number, is_passive
                registerDto = new RegisterDto();
                registerDto.setId(resultSet.getLong("id"));
                registerDto.setuNickName(resultSet.getString("nick_name"));
                registerDto.setuEmailAddress(resultSet.getString("email_address"));
                registerDto.setuPassword(resultSet.getString("password"));
                registerDto.setRolles(resultSet.getString("roles"));
                registerDto.setRemainingNumber(resultSet.getLong("remaining_number"));
                registerDto.setPassive(resultSet.getBoolean("is_passive"));
                registerDto.setSystemCreatedDate(resultSet.getDate("system_created_date"));
                list.add(registerDto);
            }
            return list; // eğer başarılı ise return registerDto
        }catch (SQLException sql){
            sql.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    // UPDATE
    @Override
    public RegisterDto update(Long id, RegisterDto registerDto) {
        try(Connection connection = getInterfaceConnection()){
            // Manipulation : executeUpdate() [create, delete, update]
            // Sorgularda   : executeQuery()  [list, find, update]
            //TRANSACTION YAPISINI YAZALIM
            connection.setAutoCommit(false); // Default:true
            String sql ="UPDATE `cars`.`register` SET  `remaining_number`=?" +
                    " WHERE `id` =?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,registerDto.getRemainingNumber());
            preparedStatement.setLong(2,registerDto.getId());
            // executeUpdate: create, delete, update için kullanılır.
            int rowsEffected = preparedStatement.executeUpdate();
            // eğer güncelleme yapılmamışsa -1 değerini döner
            if (rowsEffected > 0) {
                System.out.println(RegisterDao.class+ " Basarili Guncelleme Tamamdir.");
                connection.commit();
            }else {
                System.err.println(RegisterDao.class+ "!!! Basarisiz Guncelleme Tamamdir");
                connection.rollback();
            }
            return registerDto; // eğer başarılı ise return registerDto
        }catch (SQLException sql){
            sql.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    // UPDATE (REMAING NUMBER)
    @Override
    public RegisterDto updateRemaing(Long id, RegisterDto registerDto) {
        // Bu ID ile ilgili kullanıcı varmı ?
        RegisterDto find = findById(id);
        if (find != null) {
            try (Connection connection = getInterfaceConnection()) {
                // Manipulation: executeUpdate() [create, delete, update]
                // Sorgularda  : executeQuery [list, find]
                // Transaction:
                connection.setAutoCommit(false); //default:true
                String sql = "UPDATE `register` SET  `remaining_number`=?" +
                        " WHERE `id` =?;";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setLong(1, registerDto.getRemainingNumber());
                preparedStatement.setLong(2, registerDto.getId());
                // executeUpdate: create, delete, update için kullanılır.
                int rowsEffected = preparedStatement.executeUpdate();
                // eğer güncelle yapılmamışsa -1 değerini döner
                if (rowsEffected > 0) {
                    System.out.println(RegisterDao.class + " Başarılı Kalan Hak Güncelleme Tamamdır");
                    connection.commit(); // başarılı
                } else {
                    System.err.println(RegisterDao.class + " !!! Başarısız Kalan Hak Güncelleme Tamamdır");
                    connection.rollback(); // başarısız
                }
                return registerDto; // eğer başarılı ise return registerDto
            } catch (SQLException sql) {
                sql.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Böyle bir kullanıcı yoktur");
        }
        return null;
    }

    // DELETE
    @Override
    public RegisterDto deleteById(RegisterDto registerDto) {
        try(Connection connection = getInterfaceConnection()){
            // executeUpdate() [create, delete, update]
            // Sorgularda   : executeQuery()  [list, find, update]
            //TRANSACTION YAPISINI YAZALIM
            connection.setAutoCommit(false); // Default:true
            String sql ="DELETE FROM `cars`.`register` WHERE `id` = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,registerDto.getId());
            // executeUpdate: create, delete, update için kullanılır.
            int rowsEffected = preparedStatement.executeUpdate();
            // eğer güncelleme yapılmamışsa -1 değerini döner
            if (rowsEffected > 0) {
                System.out.println(RegisterDao.class+ " Basarili Guncelleme Tamamdir.");
                connection.commit();
            }else {
                System.err.println(RegisterDao.class+ "!!! Basarisiz Guncelleme Tamamdir");
                connection.rollback();
            }
            return registerDto; // eğer başarılı ise return registerDto
        }catch (SQLException sql){
            sql.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}// end class RegisterDao

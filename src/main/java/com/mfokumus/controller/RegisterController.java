package com.mfokumus.controller;

import com.mfokumus.dao.IDaoGenerics;
import com.mfokumus.dao.RegisterDao;
import com.mfokumus.dto.RegisterDto;
import com.mfokumus.files.FilePathData;

import java.util.ArrayList;

public class RegisterController implements IDaoGenerics<RegisterDto> {

    // Injection
    private FilePathData filePathData = new FilePathData();
    private RegisterDao registerDao = new RegisterDao();

    ////////////////////////////////////////////////////////

    // SPEED DATA
    @Override
    public String speedData(Long id) {
        return registerDao.speedData(id);
    }

    // ALL DELETE
    @Override
    public String allDelete() {
        return registerDao.allDelete();
    }

    // CREATE
    @Override
    public RegisterDto create(RegisterDto registerDto) {
        return registerDao.create(registerDto);
    }

    // FIND BY ID
    @Override
    public RegisterDto findById(Long id) {
        return registerDao.findById(id);
    }

    // FIND BY EMAIL
    @Override
    public RegisterDto findByEmail(String email) {
        return registerDao.findByEmail(email);
    }

    //LIST
    @Override
    public ArrayList<RegisterDto> list() {
        return registerDao.list();
    }

    // UPDATE
    @Override
    public RegisterDto update(Long id, RegisterDto registerDto) {
        return registerDao.update(id,registerDto);
    }

    @Override
    public RegisterDto updateRemaing(Long id, RegisterDto registerDto) {
        return registerDao.updateRemaing(id, registerDto);
    }

    // DELETE BY ID
    @Override
    public RegisterDto deleteById(RegisterDto registerDto) {
        return registerDao.deleteById(registerDto);
    }
}// end class RegisterController


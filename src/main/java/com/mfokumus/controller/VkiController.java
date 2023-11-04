package com.mfokumus.controller;

import com.mfokumus.dao.IDaoGenerics;
import com.mfokumus.dao.RegisterDao;
import com.mfokumus.dao.VkiDao;
import com.mfokumus.dto.RegisterDto;
import com.mfokumus.dto.VkiDto;
import com.mfokumus.files.FilePathData;

import java.util.ArrayList;

public class VkiController implements IDaoGenerics<VkiDto> {

    // Injection
    private FilePathData filePathData = new FilePathData(); // for log
    private VkiDao vkiDao = new VkiDao();

////////////////////////////////////////////////////////////////////////

    // SPEED DATA
    @Override
    public String speedData(Long id) {
        return vkiDao.speedData(id);
    }

    // ALL DELETE
    @Override
    public String allDelete() {
        return vkiDao.allDelete();
    }

    // CREATE
    @Override
    public VkiDto create(VkiDto vkiDto) {
        return vkiDao.create(vkiDto);
    }

    // FIND BY ID
    @Override
    public VkiDto findById(Long id) {
        return vkiDao.findById(id);
    }

    @Override
    public VkiDto findByEmail(String email) {
        return null;
    }

    // LIST
    @Override
    public ArrayList<VkiDto> list() {
        return vkiDao.list();
    }

    // UPDATE
    @Override
    public VkiDto update(Long id, VkiDto vkiDto) {
        return vkiDao.update(id,vkiDto);
    }

    @Override
    public RegisterDto updateRemaing(Long id, VkiDto vkiDto) {
        return null;
    }

    // DELETE BY ID
    @Override
    public VkiDto deleteById(VkiDto vkiDto) {
        return vkiDao.deleteById(vkiDto);
    }
}

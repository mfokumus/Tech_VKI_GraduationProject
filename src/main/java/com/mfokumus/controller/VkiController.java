package com.mfokumus.controller;

import com.mfokumus.dao.IDaoGenerics;
import com.mfokumus.dao.VkiDao;
import com.mfokumus.dto.RegisterDto;
import com.mfokumus.dto.VkiDto;

import java.util.ArrayList;

public class VkiController implements IDaoGenerics<VkiDto> {

    // Injection
    private VkiDao vkiDao = new VkiDao();

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
    //////////////////////////////////////////////////////////////////////////
    // CREATE
    @Override
    public VkiDto create(VkiDto vkiDto) {
        return vkiDao.create(vkiDto);
    }
    //////////////////////////////////////////////////////////////////////////
    // FIND BY USER ID
    public VkiDto findByUserId(Long user_id){
        return vkiDao.findByUserId(user_id);
    }
    //////////////////////////////////////////////////////////////////////////
    // DELETE BY USER ID
    public Long deleteByUserID(Long id){
        return vkiDao.deleteByUserId(id);
    }
    //////////////////////////////////////////////////////////////////////////
    // FIND BY ID
    @Override
    public VkiDto findById(Long id) {
        return null;
    }

    @Override
    public VkiDto findByEmail(String email) {
        return null;
    }
    //////////////////////////////////////////////////////////////////////////
    // LIST
    @Override
    public ArrayList<VkiDto> list() {
        return vkiDao.list();
    }
    //////////////////////////////////////////////////////////////////////////
    // UPDATE
    @Override
    public VkiDto update(Long id, VkiDto vkiDto) {
        return vkiDao.update(id,vkiDto);
    }
    //////////////////////////////////////////////////////////////////////////
    @Override
    public VkiDto updateRemaing(Long id, VkiDto vkiDto) {
        return null;
    }

    // DELETE BY ID
    @Override
    public VkiDto deleteById(VkiDto vkiDto) {
        return null;
    }
}

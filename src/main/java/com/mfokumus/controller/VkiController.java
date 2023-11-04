package com.mfokumus.controller;

import com.mfokumus.dao.IDaoGenerics;
import com.mfokumus.dao.RegisterDao;
import com.mfokumus.dto.RegisterDto;
import com.mfokumus.dto.VkiDto;
import com.mfokumus.files.FilePathData;

import java.util.ArrayList;

public class VkiController implements IDaoGenerics<VkiDto> {

    private FilePathData filePathData = new FilePathData();
    //private VkiDao registerDao = new RegisterDao();



    @Override
    public String speedData(Long id) {
        return null;
    }

    @Override
    public String allDelete() {
        return null;
    }

    @Override
    public VkiDto create(VkiDto vkiDto) {
        return null;
    }

    @Override
    public VkiDto findById(Long id) {
        return null;
    }

    @Override
    public VkiDto findByEmail(String email) {
        return null;
    }

    @Override
    public ArrayList<VkiDto> list() {
        return null;
    }

    @Override
    public VkiDto update(Long id, VkiDto vkiDto) {
        return null;
    }

    @Override
    public RegisterDto updateRemaing(Long id, VkiDto vkiDto) {
        return null;
    }

    @Override
    public VkiDto deleteById(VkiDto vkiDto) {
        return null;
    }
}

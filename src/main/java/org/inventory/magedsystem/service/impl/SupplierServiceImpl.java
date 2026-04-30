package org.inventory.magedsystem.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.inventory.magedsystem.dto.*;
import org.inventory.magedsystem.entity.Category;
import org.inventory.magedsystem.entity.Supplier;
import org.inventory.magedsystem.entity.User;
import org.inventory.magedsystem.enums.UserRole;
import org.inventory.magedsystem.exceptions.InvalidCarednitalsException;
import org.inventory.magedsystem.exceptions.NotFoundException;
import org.inventory.magedsystem.repository.SupplierRepository;
import org.inventory.magedsystem.repository.UserRepository;
import org.inventory.magedsystem.security.JWTUtils;
import org.inventory.magedsystem.service.SupplierService;
import org.inventory.magedsystem.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository ;
    private final ModelMapper modelMapper;



    @Override
    public Response addSupplier(SupplierDto supplierDto) {
        Supplier supplierToSave=modelMapper.map(supplierDto,Supplier.class);
        supplierRepository.save(supplierToSave);
        return Response.builder()
                .status(200)
                .message("supplier created successfully")
                .build();
    }

    @Override
    public Response updateSupplier(Long id, SupplierDto supplierDto) {
        Supplier exitingSupplier=supplierRepository.findById(id)
                .orElseThrow(()->new NotFoundException("supplier not found"));
        if(supplierDto.getName()!=null)exitingSupplier.setName(supplierDto.getName());
        if(supplierDto.getAddress()!=null)exitingSupplier.setAddress(supplierDto.getAddress());
        supplierRepository.save(exitingSupplier);
        return Response.builder()
                .status(200)
                .message("supplier updated successfully")
                .build();
    }

    @Override
    public Response getAllSuppliers() {
        List<Supplier> suppliers=supplierRepository.findAll(Sort.by(Sort.Direction.DESC,"Id"));
        List<SupplierDto> supplierDtos = suppliers.stream()
                .map(supplier -> modelMapper.map(supplier, SupplierDto.class))
                .toList();


        return Response.builder()
                .status(200)
                .message("success")
                .supplierDtos(supplierDtos)
                .build();
    }

    @Override
    public Response getSupplierById(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("supplier not found"));
        SupplierDto supplierDto=modelMapper.map(supplier,SupplierDto.class);
        return Response.builder()
                .status(200)
                .message("success")
                .supplierDto(supplierDto)
                .build();
    }

    @Override
    public Response deleteSupplier(Long id) {
        supplierRepository.findById(id)
                .orElseThrow(()->new NotFoundException("supplier not found"));
        supplierRepository.deleteById(id);
        return Response.builder()
                .status(200)
                .message("supplier deleted successfully")
                .build();
    }
}


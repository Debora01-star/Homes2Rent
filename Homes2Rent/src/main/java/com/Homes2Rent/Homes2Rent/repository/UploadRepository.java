package com.Homes2Rent.Homes2Rent.repository;


import com.Homes2Rent.Homes2Rent.model.Upload;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface UploadRepository extends CrudRepository<Upload, Long> {

    List<Upload> findAll();

    List<Upload> findAllUploadsEqualsIgnoreCase(String foto);
}




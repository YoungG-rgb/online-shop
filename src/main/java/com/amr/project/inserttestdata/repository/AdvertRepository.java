package com.amr.project.inserttestdata.repository;

import com.amr.project.model.entity.Advert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface AdvertRepository extends JpaRepository<Advert, Long> {

}

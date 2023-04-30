package com.eatclipseV2.repository;

import com.eatclipseV2.entity.Rider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiderRepository extends JpaRepository<Rider, Long> {

    Rider findByNickName(String nickName);
}

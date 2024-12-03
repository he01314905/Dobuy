package com.storecarousel.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface storecarouselRepository extends JpaRepository<storeCarouselVO, Integer> {
    @Query(value = "SELECT * FROM StoreCarousel WHERE Counter_No = ?1 ORDER BY Carousel_Time DESC LIMIT 3", nativeQuery = true)
    List<storeCarouselVO> findNewest3(Integer counterNo);
}

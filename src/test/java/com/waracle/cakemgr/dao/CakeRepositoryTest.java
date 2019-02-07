package com.waracle.cakemgr.dao;

import com.waracle.cakemgr.entity.CakeEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CakeRepositoryTest {

    @Autowired
    private CakeRepository cakeRepository;

    private CakeEntity cakeEntity;

    private Integer cakeId;

    @Before
    public void setUp(){

        this.cakeEntity = new CakeEntity();

        cakeEntity.setTitle("Strawberry cake");
        cakeEntity.setDescription("Super cake");
        cakeEntity.setImage("https://www.pexels.com/photo/chocolate-cake-with-white-icing-and-strawberry-on-top-with-chocolate-69817/");

        cakeRepository.saveAndFlush(cakeEntity);

        this.cakeId = ((CakeEntity)cakeRepository.findAll().get(0)).getCakeId();
    }

    @Test
    public void testFindByAllReturnsCakes() {

        assertEquals(cakeRepository.findAll().size(), 1);

    }

    @Test
    public void testFindByIdReturnsCake() {
        // given
        CakeEntity expectedCakeEntity = new CakeEntity();

        expectedCakeEntity.setTitle("Strawberry cake");
        expectedCakeEntity.setDescription("Super cake");
        expectedCakeEntity.setImage("https://www.pexels.com/photo/chocolate-cake-with-white-icing-and-strawberry-on-top-with-chocolate-69817/");

        List<CakeEntity> cakes = cakeRepository.findAll();

        // when
        CakeEntity cakeEntity = new CakeEntity();
        Optional<CakeEntity> found = cakeRepository.findById(cakeId);

        if(found.isPresent()){
            cakeEntity = found.get();
        }

        // then
        assertEquals(expectedCakeEntity.getTitle(), cakeEntity.getTitle());
        assertEquals(expectedCakeEntity.getDescription(), cakeEntity.getDescription());
        assertEquals(expectedCakeEntity.getImage(), cakeEntity.getImage());
    }

    @Test
    public void testReturnsUpdatedCake() {
        // given
        String expectedCakeDescription = "Strawberry cakes forever";

        List<CakeEntity> cakes = cakeRepository.findAll();

        // when
        CakeEntity updatedCakeEntity = new CakeEntity();
        Optional<CakeEntity> found = cakeRepository.findById(cakeId);

        if(found.isPresent()){
            updatedCakeEntity = found.get();
            updatedCakeEntity.setDescription("Strawberry cakes forever");
            cakeRepository.save(updatedCakeEntity);
        }

        // then
        assertEquals(expectedCakeDescription, updatedCakeEntity.getDescription());
    }

    @Test
    public void testInsertsNewCake() {
        // given
        CakeEntity secondCakeEntity = new CakeEntity();

        secondCakeEntity.setTitle("Cheese cake");
        secondCakeEntity.setDescription("Super cheese cake");
        secondCakeEntity.setImage("https://www.pexels.com/photo/chocolate-cake-with-white-icing-and-strawberry-on-top-with-chocolate-69817/");

        //when
        cakeRepository.saveAndFlush(secondCakeEntity);
        List<CakeEntity> cakes = cakeRepository.findAll();

        // then
        assertEquals(cakeRepository.findAll().size(), 2);
    }

    @Test
    public void testDeletesCake() {

        //when
        cakeRepository.deleteById(cakeId);
        cakeRepository.flush();

        // then
        List<CakeEntity> cakes = cakeRepository.findAll();
        assertEquals(cakeRepository.findAll().size(), 0);
    }
}

package com.waracle.cakemgr.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.entity.CakeEntity;
import com.waracle.cakemgr.service.CakeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

public class CakeRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    CakeService cakeService;

    @InjectMocks
    CakeRestController cakeRestController;

    private List<CakeEntity> cakes;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cakeRestController).build();
    }

    @Test
    public void testReturnsJsonArray() throws Exception {
        CakeEntity cakeEntity = new CakeEntity();
        cakeEntity.setTitle("Strawberry cake");
        cakeEntity.setDescription("Super cake");
        cakeEntity.setImage("https://www.pexels.com/photo/chocolate-cake-with-white-icing-and-strawberry-on-top-with-chocolate-69817/");

        this.cakes = Arrays.asList(cakeEntity);

        when(cakeService.findAll()).thenReturn(cakes);

        mockMvc.perform(get("/cakes")
                            .contentType(MediaType.APPLICATION_JSON))
                            .andExpect(status().isOk())
                            .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testReturnsCakeById() throws Exception {
        CakeEntity cakeEntity = new CakeEntity();
        cakeEntity.setCakeId(1);
        cakeEntity.setTitle("Strawberry cake");
        cakeEntity.setDescription("Super cake");
        cakeEntity.setImage("https://www.pexels.com/photo/chocolate-cake-with-white-icing-and-strawberry-on-top-with-chocolate-69817/");

        given(cakeService.findCakeById(1)).willReturn(cakeEntity);

        mockMvc.perform(get("/cakes/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{'cakeId': 1,'title': 'Strawberry cake','description': 'Super cake','image' : 'https://www.pexels.com/photo/chocolate-cake-with-white-icing-and-strawberry-on-top-with-chocolate-69817/'}"));

    }

    @Test
    public void testAddCake() throws Exception {
        CakeEntity cakeEntity = new CakeEntity();

        cakeEntity.setTitle("Strawberry cake");
        cakeEntity.setDescription("Super cake");
        cakeEntity.setImage("https://www.pexels.com/photo/chocolate-cake-with-white-icing-and-strawberry-on-top-with-chocolate-69817/");

        mockMvc.perform(post("/cakes")
                .content(mapToJson(cakeEntity))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{'cakeId': 0,'title': 'Strawberry cake','description': 'Super cake','image' : 'https://www.pexels.com/photo/chocolate-cake-with-white-icing-and-strawberry-on-top-with-chocolate-69817/'}"));
    }

    @Test
    public void testUpdateCake() throws Exception {
        CakeEntity cakeEntity = new CakeEntity();
        cakeEntity.setCakeId(2);
        cakeEntity.setTitle("Strawberry cake");
        cakeEntity.setDescription("Super cake");
        cakeEntity.setImage("https://www.pexels.com/photo/chocolate-cake-with-white-icing-and-strawberry-on-top-with-chocolate-69817/");

        given(cakeService.findCakeById(2)).willReturn(cakeEntity);

        mockMvc.perform(put("/cakes")
                .content(mapToJson(cakeEntity))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{'cakeId': 2,'title': 'Strawberry cake','description': 'Super cake','image' : 'https://www.pexels.com/photo/chocolate-cake-with-white-icing-and-strawberry-on-top-with-chocolate-69817/'}"));
    }

    @Test
    public void testDeleteCakeById() throws Exception {
        CakeEntity cakeEntity = new CakeEntity();
        cakeEntity.setCakeId(1);
        cakeEntity.setTitle("Strawberry cake");
        cakeEntity.setDescription("Super cake");
        cakeEntity.setImage("https://www.pexels.com/photo/chocolate-cake-with-white-icing-and-strawberry-on-top-with-chocolate-69817/");

        given(cakeService.findCakeById(1)).willReturn(cakeEntity);

        mockMvc.perform(delete("/cakes/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Cake 1 deleted !"));
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}

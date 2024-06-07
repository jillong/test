package com.example.springai.entity;

import lombok.Data;

import java.util.List;

/**
 * @author jillong
 * @date 2024/6/7
 */

@Data
public class ActorsFilms {

    private String actor;

    private List<String> movies;
}

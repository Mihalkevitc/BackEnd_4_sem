package com.example.services;

import com.example.entities.Cours;
import com.example.repositories.CoursRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CoursService
{
    private CoursRepository coursRepository;

    public Iterable<Cours> findAll()
    {
        return coursRepository.findAll();
    }
}

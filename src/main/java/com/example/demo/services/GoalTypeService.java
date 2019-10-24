package com.example.demo.services;

import com.example.demo.exceptions.ElementBadRequestException;
import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.models.GoalTypeModel;
import com.example.demo.repositories.GoalTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoalTypeService {

    @Autowired
    GoalTypeRepository goalTypeRepository;


    public GoalTypeModel save(GoalTypeModel goalType) {
        Optional<GoalTypeModel> type = findByName(goalType.getTypeName());
        if (type.isPresent())
            return type.get(); // Unique names...
        return goalTypeRepository.save(goalType);
    }

    public GoalTypeModel update(Integer id, GoalTypeModel goalType) throws ElementNotFoundException, ElementBadRequestException {

        GoalTypeModel data = findById(id).orElseThrow(() -> new ElementNotFoundException("Could not find goal type with ID=" + id));

        Optional<GoalTypeModel> duplicate = findByName(goalType.getTypeName());
        if (duplicate.isPresent())
            throw new ElementBadRequestException("Goal type with name<" + goalType.getTypeName() + "> already exists! Cannot change goal type with ID=" + id);

        data.setTypeName(goalType.getTypeName());

        return save(data);
    }

    public Optional<GoalTypeModel> findById(Integer id) {
        return goalTypeRepository.findById(id);
    }

    public Optional<GoalTypeModel> findByName(String name) {
        return goalTypeRepository.findByTypeName(name);
    }

    public List<GoalTypeModel> findAll() {
        return goalTypeRepository.findAll();
    }

    public GoalTypeModel deleteById(Integer id) {
        GoalTypeModel goalType = findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Could not find goal type with ID=" + id));

        goalTypeRepository.deleteById(id);
        return goalType;
    }

    public GoalTypeModel deleteByName(String name) {
        GoalTypeModel goalType = findByName(name)
                .orElseThrow(() -> new ElementNotFoundException("Could not find goal type with name=" + name));

        goalTypeRepository.deleteByTypeName(name);
        return goalType;
    }

}

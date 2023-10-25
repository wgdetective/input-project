package com.wgdetective.input.project.controller;

import java.util.List;
import java.util.Optional;

import com.wgdetective.input.project.controller.dto.CreateNodeDto;
import com.wgdetective.input.project.controller.dto.NodeDto;
import com.wgdetective.input.project.controller.mapper.NodeDtoMapper;
import com.wgdetective.input.project.service.NodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * NodeController.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/node")
public class NodeController {

    private final NodeService service;

    private final NodeDtoMapper mapper;

    @GetMapping("/{id}")
    public Optional<NodeDto> get(@PathVariable final Long id) {
        return service.getById(id).map(mapper::map);
    }

    @GetMapping
    public List<NodeDto> getAll() {
        return service.getAll().stream().map(mapper::map).toList();
    }

    @PutMapping
    public NodeDto save(@RequestBody CreateNodeDto node) {
        return mapper.map(service.save(mapper.map(node)));
    }

    @PostMapping
    public NodeDto update(@RequestBody NodeDto node) {
        return mapper.map(service.update(mapper.map(node)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable final Long id) {
        service.delete(id);
    }

}

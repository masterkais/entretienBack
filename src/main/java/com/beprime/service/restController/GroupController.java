package com.beprime.service.restController;

import com.beprime.persistance.entities.Group;
import com.beprime.service.services.interfaceService.IGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/group")
public class GroupController {
    private final IGroupService groupService;

    @Autowired
    public GroupController(IGroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping(value = "/groups")
    public List<Group> findAll() {
        return groupService.findAllEGroupe();
    }

    @GetMapping(value = "/{id}")
    public Group getGroup(@PathVariable Long id) {
        return groupService.findById(id);
    }

    @GetMapping(value = "/search/{privileged}")
    public List<Group> getGroupByPrivileged(@PathVariable String privileged) {
        List<Group> groupDtos = new ArrayList<>();
        groupDtos.add(groupService.findByPrivileged(privileged));
        return groupDtos;
    }

    @PostMapping()
    public Group save(@RequestBody @Valid Group groupDto) {
        return groupService.save(groupDto);
    }

    @PutMapping()
    public Group update(@RequestBody @Valid Group groupDto) {
        return groupService.update(groupDto);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        groupService.delete(id);
        return true;
    }

}

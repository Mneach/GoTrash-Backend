package com.gotrash.api.v1;

import com.gotrash.api.response.MessageResponse;
import com.gotrash.api.v1.model.Reward;
import com.gotrash.api.v1.model.Role;
import com.gotrash.api.v1.request.RoleRequest;
import com.gotrash.api.v1.response.RewardResponse;
import com.gotrash.api.v1.response.RoleResponse;
import com.gotrash.api.v1.transformer.RewardTransformer;
import com.gotrash.api.v1.transformer.RoleTransformer;
import com.gotrash.constant.RoleName;
import com.gotrash.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1")
@Tag(name = "Role", description = "API for Role")
public class RoleAPI {
  
  private final RoleService roleService;
  
  @PostMapping("/roles")
  @Operation(summary = "API to create a new role")
  public ResponseEntity<RoleResponse> save(@RequestBody RoleRequest roleRequest) {
    Role role = RoleTransformer.transformRequestToModel(roleRequest);
    RoleResponse roleResponse = RoleTransformer.transformModelToResponse(roleService.save(role));
    return new ResponseEntity<>(roleResponse, HttpStatus.CREATED);
  }

  @GetMapping("/roles/{role_id}")
  @Operation(summary = "API to get role by role_id")
  public ResponseEntity<RoleResponse> getRoleByRoleId(@PathVariable("role_id") String roleId) {
    RoleResponse roleResponse = RoleTransformer.transformModelToResponse(
        roleService.getRoleByRoleId(roleId)
    );
    return new ResponseEntity<>(roleResponse, HttpStatus.OK);
  }

  @GetMapping("/roles/{role_name}")
  @Operation(summary = "API to get role by role_name")
  public ResponseEntity<RoleResponse> getRoleByRoleName(@PathVariable("role_name") RoleName roleName) {
    RoleResponse roleResponse = RoleTransformer.transformModelToResponse(
        roleService.getRoleByRoleName(roleName)
    );
    return new ResponseEntity<>(roleResponse, HttpStatus.OK);
  }

  @GetMapping("/roles")
  @Operation(summary = "API to get all role data")
  public ResponseEntity<List<RoleResponse>> getExchanges() {
    List<Role> roles = roleService.getRoles();
    List<RoleResponse> roleResponses = roles.stream()
        .map(RoleTransformer::transformModelToResponse)
        .toList();
    return new ResponseEntity<>(roleResponses, HttpStatus.OK);
  }

  @PatchMapping("/roles")
  @Operation(summary = "API to update role")
  public ResponseEntity<RoleResponse> update(@RequestBody RoleRequest roleRequest) {
    Role role = RoleTransformer.transformRequestToModel(roleRequest);
    RoleResponse roleResponse = RoleTransformer.transformModelToResponse(roleService.update(role));
    return new ResponseEntity<>(roleResponse, HttpStatus.OK);
  }

  @DeleteMapping("/roles/{role_id}")
  @Operation(summary = "API to delete role by role_id")
  public ResponseEntity<MessageResponse> delete(@PathVariable("role_id") String roleId) {
    roleService.delete(roleId);
    String message = "Successfully delete role with id " + roleId;
    MessageResponse messageResponse = new MessageResponse(message);
    return new ResponseEntity<>(messageResponse, HttpStatus.OK);
  }
}

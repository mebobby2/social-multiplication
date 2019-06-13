package numbersco.mathswiz.gamification.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import numbersco.mathswiz.gamification.service.AdminService;

/**
 * AdminController
 */
@Profile("test")
@RestController
@RequestMapping("/gamification/admin")
public class AdminController {
  private final AdminService adminService;

  public AdminController(final AdminService adminService) {
    this.adminService = adminService;
  }

  @PostMapping("/delete-db")
  public ResponseEntity<String> deleteDatabase() {
    adminService.deleteDatabaseContents();
    return ResponseEntity.ok().build();
  }

}

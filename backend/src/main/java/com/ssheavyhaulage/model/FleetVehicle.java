package com.ssheavyhaulage.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Table(name = "fleet_vehicles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
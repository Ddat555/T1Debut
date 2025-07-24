package com.example.CommandService.models;

import com.example.CommandService.enums.Priority;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommandModel {
    @Size(max = 1000, min = 3, message = "Max description length is 1000")
    private String description;
    @NotNull
    private Priority priority;
    @Size(max = 100, min = 3, message = "Max author length is 100")
    private String author;
    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[0-2])$",
            message = "Time invalid format")
    private String time;
}

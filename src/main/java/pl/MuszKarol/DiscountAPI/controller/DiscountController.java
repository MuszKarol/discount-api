package pl.MuszKarol.DiscountAPI.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.MuszKarol.DiscountAPI.dto.DiscountDTORequest;
import pl.MuszKarol.DiscountAPI.dto.DiscountDTOResponse;
import pl.MuszKarol.DiscountAPI.service.DiscountService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/discounts")
public class DiscountController {

    private final DiscountService discountService;

    @Operation(summary = "Get a list of discounts published last month")
    @ApiResponse(
            responseCode = "200",
            description = "Discounts posted last month were found",
            content = {@Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = DiscountDTOResponse.class))
            )}
    )
    @GetMapping(value = "/month")
    public ResponseEntity<List<DiscountDTOResponse>> getAllDiscountsNoOlderThanOneMonth() {
        return ResponseEntity.ok().body(discountService.getAllDiscountsOfTheCurrentMonth());
    }


    @Operation(summary = "Get a list of discounts published last week")
    @ApiResponse(
            responseCode = "200",
            description = "Discounts posted last week were found",
            content = {@Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = DiscountDTOResponse.class))
            )}
    )
    @GetMapping(value = "/week")
    public ResponseEntity<List<DiscountDTOResponse>> getAllDiscountsNoOlderThanOneWeek() {
        return ResponseEntity.ok().body(discountService.getAllDiscountsOfTheCurrentWeek());
    }


    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DiscountDTOResponse.class)
                    )}
            )
    })
    @PostMapping(value = "/new")
    public ResponseEntity<DiscountDTOResponse> saveDiscount(@RequestBody DiscountDTORequest discountDTORequest) {
        return ResponseEntity.ok().body(discountService.saveDiscount(discountDTORequest));
    }

}

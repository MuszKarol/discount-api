package pl.musz.karol.discountapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.musz.karol.discountapi.dto.DiscountDTOResponse;
import pl.musz.karol.discountapi.dto.ImageDTOResponse;
import pl.musz.karol.discountapi.exception.ImageNotFoundException;
import pl.musz.karol.discountapi.exception.InvalidImageExtensionException;
import pl.musz.karol.discountapi.service.ImageService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    @Operation(summary = "Download the image using the passed parameters")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "An image with the specified parameters was found",
                    content = {
                            @Content(
                                mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE,
                                schema = @Schema(implementation = Resource.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Cannot find an image with the specified name",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "406",
                    description = "The image file extension is invalid",
                    content = @Content
            )
    })
    @GetMapping
    public ResponseEntity<Resource> downloadImage(
            @Parameter(
                    name = "imageName",
                    schema = @Schema(implementation = String.class),
                    description = "Use the image name received from the response"
            )
            @RequestParam String imageName,
            @Parameter(
                    name = "extension",
                    schema = @Schema(type = "string", allowableValues = {"jpeg", "png", "gif"}),
                    description = "Only allowed image file extensions are supported"
            )
            @RequestParam String extension
    ) {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(imageService.getImageAsResource(imageName, extension));

        } catch (ImageNotFoundException e) {
            return ResponseEntity.badRequest().build();
        } catch (InvalidImageExtensionException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }

    @Operation(summary = "Upload multiple images with discount id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Returns a list of images details",
                    content = {
                            @Content(
                                mediaType = MediaType.APPLICATION_JSON_VALUE,
                                array = @ArraySchema(schema = @Schema(implementation = DiscountDTOResponse.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Cannot find images by specified discount id",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "406",
                    description = "The image file extension is invalid",
                    content = @Content
            )
    })
    @PostMapping(value = "/new", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<List<ImageDTOResponse>> saveImages(
            @RequestParam("images") List<MultipartFile> multipartFileList,
            @RequestParam("discountId") UUID discountId) {
        try {
            return ResponseEntity.ok().body(imageService.saveAllImages(multipartFileList, discountId));
        } catch (InvalidImageExtensionException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

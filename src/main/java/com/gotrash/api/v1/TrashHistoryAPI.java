package com.gotrash.api.v1;

import com.gotrash.api.response.ApiResponse;
import com.gotrash.api.response.MessageResponse;
import com.gotrash.api.v1.model.trashhistory.TrashHistory;
import com.gotrash.api.v1.model.trashhistory.TrashHistoryIoT;
import com.gotrash.api.v1.model.trashhistory.TrashHistoryManual;
import com.gotrash.api.v1.model.trashhistory.TrashHistoryWasteBank;
import com.gotrash.api.v1.request.trashhistory.TrashHistoryIoTRequest;
import com.gotrash.api.v1.request.trashhistory.TrashHistoryManualRequest;
import com.gotrash.api.v1.request.trashhistory.TrashHistoryRequest;
import com.gotrash.api.v1.response.trashhistory.TrashHistoryIoTResponse;
import com.gotrash.api.v1.response.trashhistory.TrashHistoryResponse;
import com.gotrash.api.v1.response.trashhistory.TrashHistoryWasteBankResponse;
import com.gotrash.api.v1.transformer.trashhistory.TrashHistoryIoTTransformer;
import com.gotrash.api.v1.transformer.trashhistory.TrashHistoryManualTransformer;
import com.gotrash.api.v1.transformer.trashhistory.TrashHistoryTransformer;
import com.gotrash.api.v1.transformer.trashhistory.TrashHistoryWasteBankTransformer;
import com.gotrash.service.TrashHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Trash History API", description = "API for Trash History")
public class TrashHistoryAPI {
    
    private final TrashHistoryService trashHistoryService;

    @PostMapping("/trash-histories")
    @Operation(summary = "API to create a new trash history")
    public ApiResponse<TrashHistoryResponse> save(@RequestBody TrashHistoryRequest trashHistoryRequest) {
        TrashHistory trashHistory = TrashHistoryTransformer.transformRequestToModel(trashHistoryRequest);
        TrashHistoryResponse trashHistoryResponse = TrashHistoryTransformer.transformModelToResponse(trashHistoryService.save(trashHistory));
        return new ApiResponse<>(HttpStatus.CREATED.value(), trashHistoryResponse);
    }

    @PostMapping("/trash-histories/manual")
    @Operation(summary = "API to manually create a new trash history by waste bank officer")
    public ApiResponse<TrashHistoryResponse> storeTrashManually(@RequestBody TrashHistoryManualRequest trashHistoryManualRequest) {
        TrashHistoryManual trashHistoryManual = TrashHistoryManualTransformer.transformRequestToModel(trashHistoryManualRequest);
        TrashHistoryResponse trashHistoryResponse = TrashHistoryTransformer.transformModelToResponse(trashHistoryService.storeTrashManually(trashHistoryManual));
        return new ApiResponse<>(HttpStatus.CREATED.value(), trashHistoryResponse);
    }

    @GetMapping("/trash-histories/iot/{ble_id}")
    @Operation(summary = "API to get trash histories by trash history ble_id")
    public ApiResponse<TrashHistoryResponse> storeTrashFromIoT(@PathVariable("ble_id") BigInteger bleId) {
        TrashHistoryResponse trashHistoryResponse = TrashHistoryTransformer.transformModelToResponse(trashHistoryService.getTrashHistoryByTrashHistoryBleId(bleId));
        return new ApiResponse<>(HttpStatus.CREATED.value(), trashHistoryResponse);
    }

    @PostMapping("/trash-histories/iot")
    @Operation(summary = "API to create a new trash history from IoT")
    public ApiResponse<TrashHistoryIoTResponse> storeTrashFromIoT(@RequestBody TrashHistoryIoTRequest trashHistoryIoTRequest) {
        TrashHistoryIoT trashHistoryIoT = TrashHistoryIoTTransformer.transformRequestToModel(trashHistoryIoTRequest);
        TrashHistory trashHistory = trashHistoryService.storeTrashFromIoT(trashHistoryIoT);
        TrashHistoryIoTResponse trashHistoryIoTResponse = new TrashHistoryIoTResponse(trashHistory.getBleId());
        return new ApiResponse<>(HttpStatus.CREATED.value(), trashHistoryIoTResponse);
    }

    @GetMapping("/trash-histories")
    @Operation(summary = "API to get all trash history data")
    public ApiResponse<List<TrashHistoryResponse>> getTrashHistories() {
        List<TrashHistory> trashHistories = trashHistoryService.getTrashHistories();
        List<TrashHistoryResponse> trashHistoryResponses = trashHistories.stream()
            .map(TrashHistoryTransformer::transformModelToResponse)
            .toList();
        return new ApiResponse<>(HttpStatus.OK.value(), trashHistoryResponses);
    }

    @GetMapping("/trash-histories/{trash_history_id}")
    @Operation(summary = "API to get trash history by trash history id")
    public ApiResponse<TrashHistoryResponse> getTrashByTrashId(@PathVariable("trash_history_id") String trashHistoryId) {
        TrashHistoryResponse trashHistoryResponse = TrashHistoryTransformer.transformModelToResponse(
                trashHistoryService.getTrashHistoryByTrashHistoryId(trashHistoryId)
        );
        return new ApiResponse<>(HttpStatus.OK.value(), trashHistoryResponse);
    }

    @GetMapping("/trash-histories/wastebank/{wastebank_id}")
    @Operation(summary = "API to get trash history by wastebank id")
    public ApiResponse<List<TrashHistoryWasteBankResponse>> getTrashHistoriesByWasteBankId(@PathVariable("wastebank_id") String wasteBankId) {
        List<TrashHistoryWasteBank> trashHistoryWasteBanks = trashHistoryService.getTrashHistoriesByWasteBankId(wasteBankId);
        List<TrashHistoryWasteBankResponse> trashHistoryWasteBankResponses = trashHistoryWasteBanks.stream()
            .map(TrashHistoryWasteBankTransformer::transformModelToResponse)
            .toList();
        return new ApiResponse<>(HttpStatus.OK.value(), trashHistoryWasteBankResponses);
    }


    @GetMapping("/trash-histories/citizen/{citizen_id}")
    @Operation(summary = "API to get trash history by citizen id")
    public ApiResponse<List<TrashHistoryResponse>> getTrashHistoriesByUserId(@PathVariable("citizen_id") String citizenId) {
        List<TrashHistory> trashHistories = trashHistoryService.getTrashHistoryByUserId(citizenId);
        List<TrashHistoryResponse> trashHistoryResponses = trashHistories.stream()
                .map(TrashHistoryTransformer::transformModelToResponse)
                .toList();
        return new ApiResponse<>(HttpStatus.OK.value(), trashHistoryResponses);
    }

    @PatchMapping("/trash-histories/{trash_history_id}")
    @Operation(summary = "API to update trash history")
    public ApiResponse<TrashHistoryResponse> update(@PathVariable("trash_history_id") String trashHistoryId,
                                                       @RequestBody TrashHistoryRequest trashHistoryRequest) {
        TrashHistory trashHistory = TrashHistoryTransformer.transformRequestToModel(trashHistoryId, trashHistoryRequest);
        TrashHistoryResponse trashHistoryResponse = TrashHistoryTransformer.transformModelToResponse(trashHistoryService.update(trashHistory));
        return new ApiResponse<>(HttpStatus.OK.value(), trashHistoryResponse);
    }

    @DeleteMapping("/trash-histories/{trash_history_id}")
    @Operation(summary = "API to delete trash history by trash history id")
    public ApiResponse<MessageResponse> delete(@PathVariable("trash_history_id") String trashHistoryId) {
        trashHistoryService.delete(trashHistoryId);
        String message = "Successfully delete trashHistory with id " + trashHistoryId;
        return new ApiResponse<>(HttpStatus.OK.value(), message);
    }
}
